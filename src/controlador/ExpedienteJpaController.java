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
import modelo.Paciente;
import modelo.Secretaria;
import modelo.Cita;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Expediente;

/**
 *
 * @author abiga
 */
public class ExpedienteJpaController implements Serializable {

    public ExpedienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Expediente expediente) {
        if (expediente.getCitaCollection() == null) {
            expediente.setCitaCollection(new ArrayList<Cita>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paciente pacienteId = expediente.getPacienteId();
            if (pacienteId != null) {
                pacienteId = em.getReference(pacienteId.getClass(), pacienteId.getId());
                expediente.setPacienteId(pacienteId);
            }
            Secretaria secretariaId = expediente.getSecretariaId();
            if (secretariaId != null) {
                secretariaId = em.getReference(secretariaId.getClass(), secretariaId.getId());
                expediente.setSecretariaId(secretariaId);
            }
            Collection<Cita> attachedCitaCollection = new ArrayList<Cita>();
            for (Cita citaCollectionCitaToAttach : expediente.getCitaCollection()) {
                citaCollectionCitaToAttach = em.getReference(citaCollectionCitaToAttach.getClass(), citaCollectionCitaToAttach.getIdcita());
                attachedCitaCollection.add(citaCollectionCitaToAttach);
            }
            expediente.setCitaCollection(attachedCitaCollection);
            em.persist(expediente);
            if (pacienteId != null) {
                pacienteId.getExpedienteCollection().add(expediente);
                pacienteId = em.merge(pacienteId);
            }
            if (secretariaId != null) {
                secretariaId.getExpedienteCollection().add(expediente);
                secretariaId = em.merge(secretariaId);
            }
            for (Cita citaCollectionCita : expediente.getCitaCollection()) {
                Expediente oldExpedienteIdOfCitaCollectionCita = citaCollectionCita.getExpedienteId();
                citaCollectionCita.setExpedienteId(expediente);
                citaCollectionCita = em.merge(citaCollectionCita);
                if (oldExpedienteIdOfCitaCollectionCita != null) {
                    oldExpedienteIdOfCitaCollectionCita.getCitaCollection().remove(citaCollectionCita);
                    oldExpedienteIdOfCitaCollectionCita = em.merge(oldExpedienteIdOfCitaCollectionCita);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Expediente expediente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Expediente persistentExpediente = em.find(Expediente.class, expediente.getIdexpediente());
            Paciente pacienteIdOld = persistentExpediente.getPacienteId();
            Paciente pacienteIdNew = expediente.getPacienteId();
            Secretaria secretariaIdOld = persistentExpediente.getSecretariaId();
            Secretaria secretariaIdNew = expediente.getSecretariaId();
            Collection<Cita> citaCollectionOld = persistentExpediente.getCitaCollection();
            Collection<Cita> citaCollectionNew = expediente.getCitaCollection();
            if (pacienteIdNew != null) {
                pacienteIdNew = em.getReference(pacienteIdNew.getClass(), pacienteIdNew.getId());
                expediente.setPacienteId(pacienteIdNew);
            }
            if (secretariaIdNew != null) {
                secretariaIdNew = em.getReference(secretariaIdNew.getClass(), secretariaIdNew.getId());
                expediente.setSecretariaId(secretariaIdNew);
            }
            Collection<Cita> attachedCitaCollectionNew = new ArrayList<Cita>();
            for (Cita citaCollectionNewCitaToAttach : citaCollectionNew) {
                citaCollectionNewCitaToAttach = em.getReference(citaCollectionNewCitaToAttach.getClass(), citaCollectionNewCitaToAttach.getIdcita());
                attachedCitaCollectionNew.add(citaCollectionNewCitaToAttach);
            }
            citaCollectionNew = attachedCitaCollectionNew;
            expediente.setCitaCollection(citaCollectionNew);
            expediente = em.merge(expediente);
            if (pacienteIdOld != null && !pacienteIdOld.equals(pacienteIdNew)) {
                pacienteIdOld.getExpedienteCollection().remove(expediente);
                pacienteIdOld = em.merge(pacienteIdOld);
            }
            if (pacienteIdNew != null && !pacienteIdNew.equals(pacienteIdOld)) {
                pacienteIdNew.getExpedienteCollection().add(expediente);
                pacienteIdNew = em.merge(pacienteIdNew);
            }
            if (secretariaIdOld != null && !secretariaIdOld.equals(secretariaIdNew)) {
                secretariaIdOld.getExpedienteCollection().remove(expediente);
                secretariaIdOld = em.merge(secretariaIdOld);
            }
            if (secretariaIdNew != null && !secretariaIdNew.equals(secretariaIdOld)) {
                secretariaIdNew.getExpedienteCollection().add(expediente);
                secretariaIdNew = em.merge(secretariaIdNew);
            }
            for (Cita citaCollectionOldCita : citaCollectionOld) {
                if (!citaCollectionNew.contains(citaCollectionOldCita)) {
                    citaCollectionOldCita.setExpedienteId(null);
                    citaCollectionOldCita = em.merge(citaCollectionOldCita);
                }
            }
            for (Cita citaCollectionNewCita : citaCollectionNew) {
                if (!citaCollectionOld.contains(citaCollectionNewCita)) {
                    Expediente oldExpedienteIdOfCitaCollectionNewCita = citaCollectionNewCita.getExpedienteId();
                    citaCollectionNewCita.setExpedienteId(expediente);
                    citaCollectionNewCita = em.merge(citaCollectionNewCita);
                    if (oldExpedienteIdOfCitaCollectionNewCita != null && !oldExpedienteIdOfCitaCollectionNewCita.equals(expediente)) {
                        oldExpedienteIdOfCitaCollectionNewCita.getCitaCollection().remove(citaCollectionNewCita);
                        oldExpedienteIdOfCitaCollectionNewCita = em.merge(oldExpedienteIdOfCitaCollectionNewCita);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = expediente.getIdexpediente();
                if (findExpediente(id) == null) {
                    throw new NonexistentEntityException("The expediente with id " + id + " no longer exists.");
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
            Expediente expediente;
            try {
                expediente = em.getReference(Expediente.class, id);
                expediente.getIdexpediente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The expediente with id " + id + " no longer exists.", enfe);
            }
            Paciente pacienteId = expediente.getPacienteId();
            if (pacienteId != null) {
                pacienteId.getExpedienteCollection().remove(expediente);
                pacienteId = em.merge(pacienteId);
            }
            Secretaria secretariaId = expediente.getSecretariaId();
            if (secretariaId != null) {
                secretariaId.getExpedienteCollection().remove(expediente);
                secretariaId = em.merge(secretariaId);
            }
            Collection<Cita> citaCollection = expediente.getCitaCollection();
            for (Cita citaCollectionCita : citaCollection) {
                citaCollectionCita.setExpedienteId(null);
                citaCollectionCita = em.merge(citaCollectionCita);
            }
            em.remove(expediente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Expediente> findExpedienteEntities() {
        return findExpedienteEntities(true, -1, -1);
    }

    public List<Expediente> findExpedienteEntities(int maxResults, int firstResult) {
        return findExpedienteEntities(false, maxResults, firstResult);
    }

    private List<Expediente> findExpedienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Expediente.class));
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

    public Expediente findExpediente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Expediente.class, id);
        } finally {
            em.close();
        }
    }

    public int getExpedienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Expediente> rt = cq.from(Expediente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
