/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Cita;
import modelo.Receta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Consulta;

/**
 *
 * @author abiga
 */
public class ConsultaJpaController implements Serializable {

    public ConsultaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Consulta consulta) {
        if (consulta.getRecetaCollection() == null) {
            consulta.setRecetaCollection(new ArrayList<Receta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cita citaId = consulta.getCitaId();
            if (citaId != null) {
                citaId = em.getReference(citaId.getClass(), citaId.getIdcita());
                consulta.setCitaId(citaId);
            }
            Collection<Receta> attachedRecetaCollection = new ArrayList<Receta>();
            for (Receta recetaCollectionRecetaToAttach : consulta.getRecetaCollection()) {
                recetaCollectionRecetaToAttach = em.getReference(recetaCollectionRecetaToAttach.getClass(), recetaCollectionRecetaToAttach.getIdreceta());
                attachedRecetaCollection.add(recetaCollectionRecetaToAttach);
            }
            consulta.setRecetaCollection(attachedRecetaCollection);
            em.persist(consulta);
            if (citaId != null) {
                citaId.getConsultaCollection().add(consulta);
                citaId = em.merge(citaId);
            }
            for (Receta recetaCollectionReceta : consulta.getRecetaCollection()) {
                Consulta oldConsultaIdOfRecetaCollectionReceta = recetaCollectionReceta.getConsultaId();
                recetaCollectionReceta.setConsultaId(consulta);
                recetaCollectionReceta = em.merge(recetaCollectionReceta);
                if (oldConsultaIdOfRecetaCollectionReceta != null) {
                    oldConsultaIdOfRecetaCollectionReceta.getRecetaCollection().remove(recetaCollectionReceta);
                    oldConsultaIdOfRecetaCollectionReceta = em.merge(oldConsultaIdOfRecetaCollectionReceta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Consulta consulta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Consulta persistentConsulta = em.find(Consulta.class, consulta.getIdconsulta());
            Cita citaIdOld = persistentConsulta.getCitaId();
            Cita citaIdNew = consulta.getCitaId();
            Collection<Receta> recetaCollectionOld = persistentConsulta.getRecetaCollection();
            Collection<Receta> recetaCollectionNew = consulta.getRecetaCollection();
            if (citaIdNew != null) {
                citaIdNew = em.getReference(citaIdNew.getClass(), citaIdNew.getIdcita());
                consulta.setCitaId(citaIdNew);
            }
            Collection<Receta> attachedRecetaCollectionNew = new ArrayList<Receta>();
            for (Receta recetaCollectionNewRecetaToAttach : recetaCollectionNew) {
                recetaCollectionNewRecetaToAttach = em.getReference(recetaCollectionNewRecetaToAttach.getClass(), recetaCollectionNewRecetaToAttach.getIdreceta());
                attachedRecetaCollectionNew.add(recetaCollectionNewRecetaToAttach);
            }
            recetaCollectionNew = attachedRecetaCollectionNew;
            consulta.setRecetaCollection(recetaCollectionNew);
            consulta = em.merge(consulta);
            if (citaIdOld != null && !citaIdOld.equals(citaIdNew)) {
                citaIdOld.getConsultaCollection().remove(consulta);
                citaIdOld = em.merge(citaIdOld);
            }
            if (citaIdNew != null && !citaIdNew.equals(citaIdOld)) {
                citaIdNew.getConsultaCollection().add(consulta);
                citaIdNew = em.merge(citaIdNew);
            }
            for (Receta recetaCollectionOldReceta : recetaCollectionOld) {
                if (!recetaCollectionNew.contains(recetaCollectionOldReceta)) {
                    recetaCollectionOldReceta.setConsultaId(null);
                    recetaCollectionOldReceta = em.merge(recetaCollectionOldReceta);
                }
            }
            for (Receta recetaCollectionNewReceta : recetaCollectionNew) {
                if (!recetaCollectionOld.contains(recetaCollectionNewReceta)) {
                    Consulta oldConsultaIdOfRecetaCollectionNewReceta = recetaCollectionNewReceta.getConsultaId();
                    recetaCollectionNewReceta.setConsultaId(consulta);
                    recetaCollectionNewReceta = em.merge(recetaCollectionNewReceta);
                    if (oldConsultaIdOfRecetaCollectionNewReceta != null && !oldConsultaIdOfRecetaCollectionNewReceta.equals(consulta)) {
                        oldConsultaIdOfRecetaCollectionNewReceta.getRecetaCollection().remove(recetaCollectionNewReceta);
                        oldConsultaIdOfRecetaCollectionNewReceta = em.merge(oldConsultaIdOfRecetaCollectionNewReceta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = consulta.getIdconsulta();
                if (findConsulta(id) == null) {
                    throw new NonexistentEntityException("The consulta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Consulta consulta;
            try {
                consulta = em.getReference(Consulta.class, id);
                consulta.getIdconsulta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The consulta with id " + id + " no longer exists.", enfe);
            }
            Cita citaId = consulta.getCitaId();
            if (citaId != null) {
                citaId.getConsultaCollection().remove(consulta);
                citaId = em.merge(citaId);
            }
            Collection<Receta> recetaCollection = consulta.getRecetaCollection();
            for (Receta recetaCollectionReceta : recetaCollection) {
                recetaCollectionReceta.setConsultaId(null);
                recetaCollectionReceta = em.merge(recetaCollectionReceta);
            }
            em.remove(consulta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Consulta> findConsultaEntities() {
        return findConsultaEntities(true, -1, -1);
    }

    public List<Consulta> findConsultaEntities(int maxResults, int firstResult) {
        return findConsultaEntities(false, maxResults, firstResult);
    }

    private List<Consulta> findConsultaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Consulta.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Consulta findConsulta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Consulta.class, id);
        } finally {
            em.close();
        }
    }

    public int getConsultaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Consulta> rt = cq.from(Consulta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
