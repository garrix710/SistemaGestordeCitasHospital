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
import modelo.Persona;
import modelo.Expediente;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Cita;
import modelo.Secretaria;

/**
 *
 * @author abiga
 */
public class SecretariaJpaController implements Serializable {

    public SecretariaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Secretaria secretaria) {
        if (secretaria.getExpedienteCollection() == null) {
            secretaria.setExpedienteCollection(new ArrayList<Expediente>());
        }
        if (secretaria.getCitaCollection() == null) {
            secretaria.setCitaCollection(new ArrayList<Cita>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona personaId = secretaria.getPersonaId();
            if (personaId != null) {
                personaId = em.getReference(personaId.getClass(), personaId.getId());
                secretaria.setPersonaId(personaId);
            }
            Collection<Expediente> attachedExpedienteCollection = new ArrayList<Expediente>();
            for (Expediente expedienteCollectionExpedienteToAttach : secretaria.getExpedienteCollection()) {
                expedienteCollectionExpedienteToAttach = em.getReference(expedienteCollectionExpedienteToAttach.getClass(), expedienteCollectionExpedienteToAttach.getIdexpediente());
                attachedExpedienteCollection.add(expedienteCollectionExpedienteToAttach);
            }
            secretaria.setExpedienteCollection(attachedExpedienteCollection);
            Collection<Cita> attachedCitaCollection = new ArrayList<Cita>();
            for (Cita citaCollectionCitaToAttach : secretaria.getCitaCollection()) {
                citaCollectionCitaToAttach = em.getReference(citaCollectionCitaToAttach.getClass(), citaCollectionCitaToAttach.getIdcita());
                attachedCitaCollection.add(citaCollectionCitaToAttach);
            }
            secretaria.setCitaCollection(attachedCitaCollection);
            em.persist(secretaria);
            if (personaId != null) {
                personaId.getSecretariaCollection().add(secretaria);
                personaId = em.merge(personaId);
            }
            for (Expediente expedienteCollectionExpediente : secretaria.getExpedienteCollection()) {
                Secretaria oldSecretariaIdOfExpedienteCollectionExpediente = expedienteCollectionExpediente.getSecretariaId();
                expedienteCollectionExpediente.setSecretariaId(secretaria);
                expedienteCollectionExpediente = em.merge(expedienteCollectionExpediente);
                if (oldSecretariaIdOfExpedienteCollectionExpediente != null) {
                    oldSecretariaIdOfExpedienteCollectionExpediente.getExpedienteCollection().remove(expedienteCollectionExpediente);
                    oldSecretariaIdOfExpedienteCollectionExpediente = em.merge(oldSecretariaIdOfExpedienteCollectionExpediente);
                }
            }
            for (Cita citaCollectionCita : secretaria.getCitaCollection()) {
                Secretaria oldSecretariaIdOfCitaCollectionCita = citaCollectionCita.getSecretariaId();
                citaCollectionCita.setSecretariaId(secretaria);
                citaCollectionCita = em.merge(citaCollectionCita);
                if (oldSecretariaIdOfCitaCollectionCita != null) {
                    oldSecretariaIdOfCitaCollectionCita.getCitaCollection().remove(citaCollectionCita);
                    oldSecretariaIdOfCitaCollectionCita = em.merge(oldSecretariaIdOfCitaCollectionCita);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Secretaria secretaria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Secretaria persistentSecretaria = em.find(Secretaria.class, secretaria.getId());
            Persona personaIdOld = persistentSecretaria.getPersonaId();
            Persona personaIdNew = secretaria.getPersonaId();
            Collection<Expediente> expedienteCollectionOld = persistentSecretaria.getExpedienteCollection();
            Collection<Expediente> expedienteCollectionNew = secretaria.getExpedienteCollection();
            Collection<Cita> citaCollectionOld = persistentSecretaria.getCitaCollection();
            Collection<Cita> citaCollectionNew = secretaria.getCitaCollection();
            if (personaIdNew != null) {
                personaIdNew = em.getReference(personaIdNew.getClass(), personaIdNew.getId());
                secretaria.setPersonaId(personaIdNew);
            }
            Collection<Expediente> attachedExpedienteCollectionNew = new ArrayList<Expediente>();
            for (Expediente expedienteCollectionNewExpedienteToAttach : expedienteCollectionNew) {
                expedienteCollectionNewExpedienteToAttach = em.getReference(expedienteCollectionNewExpedienteToAttach.getClass(), expedienteCollectionNewExpedienteToAttach.getIdexpediente());
                attachedExpedienteCollectionNew.add(expedienteCollectionNewExpedienteToAttach);
            }
            expedienteCollectionNew = attachedExpedienteCollectionNew;
            secretaria.setExpedienteCollection(expedienteCollectionNew);
            Collection<Cita> attachedCitaCollectionNew = new ArrayList<Cita>();
            for (Cita citaCollectionNewCitaToAttach : citaCollectionNew) {
                citaCollectionNewCitaToAttach = em.getReference(citaCollectionNewCitaToAttach.getClass(), citaCollectionNewCitaToAttach.getIdcita());
                attachedCitaCollectionNew.add(citaCollectionNewCitaToAttach);
            }
            citaCollectionNew = attachedCitaCollectionNew;
            secretaria.setCitaCollection(citaCollectionNew);
            secretaria = em.merge(secretaria);
            if (personaIdOld != null && !personaIdOld.equals(personaIdNew)) {
                personaIdOld.getSecretariaCollection().remove(secretaria);
                personaIdOld = em.merge(personaIdOld);
            }
            if (personaIdNew != null && !personaIdNew.equals(personaIdOld)) {
                personaIdNew.getSecretariaCollection().add(secretaria);
                personaIdNew = em.merge(personaIdNew);
            }
            for (Expediente expedienteCollectionOldExpediente : expedienteCollectionOld) {
                if (!expedienteCollectionNew.contains(expedienteCollectionOldExpediente)) {
                    expedienteCollectionOldExpediente.setSecretariaId(null);
                    expedienteCollectionOldExpediente = em.merge(expedienteCollectionOldExpediente);
                }
            }
            for (Expediente expedienteCollectionNewExpediente : expedienteCollectionNew) {
                if (!expedienteCollectionOld.contains(expedienteCollectionNewExpediente)) {
                    Secretaria oldSecretariaIdOfExpedienteCollectionNewExpediente = expedienteCollectionNewExpediente.getSecretariaId();
                    expedienteCollectionNewExpediente.setSecretariaId(secretaria);
                    expedienteCollectionNewExpediente = em.merge(expedienteCollectionNewExpediente);
                    if (oldSecretariaIdOfExpedienteCollectionNewExpediente != null && !oldSecretariaIdOfExpedienteCollectionNewExpediente.equals(secretaria)) {
                        oldSecretariaIdOfExpedienteCollectionNewExpediente.getExpedienteCollection().remove(expedienteCollectionNewExpediente);
                        oldSecretariaIdOfExpedienteCollectionNewExpediente = em.merge(oldSecretariaIdOfExpedienteCollectionNewExpediente);
                    }
                }
            }
            for (Cita citaCollectionOldCita : citaCollectionOld) {
                if (!citaCollectionNew.contains(citaCollectionOldCita)) {
                    citaCollectionOldCita.setSecretariaId(null);
                    citaCollectionOldCita = em.merge(citaCollectionOldCita);
                }
            }
            for (Cita citaCollectionNewCita : citaCollectionNew) {
                if (!citaCollectionOld.contains(citaCollectionNewCita)) {
                    Secretaria oldSecretariaIdOfCitaCollectionNewCita = citaCollectionNewCita.getSecretariaId();
                    citaCollectionNewCita.setSecretariaId(secretaria);
                    citaCollectionNewCita = em.merge(citaCollectionNewCita);
                    if (oldSecretariaIdOfCitaCollectionNewCita != null && !oldSecretariaIdOfCitaCollectionNewCita.equals(secretaria)) {
                        oldSecretariaIdOfCitaCollectionNewCita.getCitaCollection().remove(citaCollectionNewCita);
                        oldSecretariaIdOfCitaCollectionNewCita = em.merge(oldSecretariaIdOfCitaCollectionNewCita);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = secretaria.getId();
                if (findSecretaria(id) == null) {
                    throw new NonexistentEntityException("The secretaria with id " + id + " no longer exists.");
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
            Secretaria secretaria;
            try {
                secretaria = em.getReference(Secretaria.class, id);
                secretaria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The secretaria with id " + id + " no longer exists.", enfe);
            }
            Persona personaId = secretaria.getPersonaId();
            if (personaId != null) {
                personaId.getSecretariaCollection().remove(secretaria);
                personaId = em.merge(personaId);
            }
            Collection<Expediente> expedienteCollection = secretaria.getExpedienteCollection();
            for (Expediente expedienteCollectionExpediente : expedienteCollection) {
                expedienteCollectionExpediente.setSecretariaId(null);
                expedienteCollectionExpediente = em.merge(expedienteCollectionExpediente);
            }
            Collection<Cita> citaCollection = secretaria.getCitaCollection();
            for (Cita citaCollectionCita : citaCollection) {
                citaCollectionCita.setSecretariaId(null);
                citaCollectionCita = em.merge(citaCollectionCita);
            }
            em.remove(secretaria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Secretaria> findSecretariaEntities() {
        return findSecretariaEntities(true, -1, -1);
    }

    public List<Secretaria> findSecretariaEntities(int maxResults, int firstResult) {
        return findSecretariaEntities(false, maxResults, firstResult);
    }

    private List<Secretaria> findSecretariaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Secretaria.class));
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

    public Secretaria findSecretaria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Secretaria.class, id);
        } finally {
            em.close();
        }
    }

    public int getSecretariaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Secretaria> rt = cq.from(Secretaria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
