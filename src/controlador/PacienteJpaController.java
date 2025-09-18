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
import modelo.Receta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Expediente;
import modelo.Cita;
import modelo.Paciente;

/**
 *
 * @author abiga
 */
public class PacienteJpaController implements Serializable {

    public PacienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Paciente paciente) {
        if (paciente.getRecetaCollection() == null) {
            paciente.setRecetaCollection(new ArrayList<Receta>());
        }
        if (paciente.getExpedienteCollection() == null) {
            paciente.setExpedienteCollection(new ArrayList<Expediente>());
        }
        if (paciente.getCitaCollection() == null) {
            paciente.setCitaCollection(new ArrayList<Cita>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona personaId = paciente.getPersonaId();
            if (personaId != null) {
                personaId = em.getReference(personaId.getClass(), personaId.getId());
                paciente.setPersonaId(personaId);
            }
            Collection<Receta> attachedRecetaCollection = new ArrayList<Receta>();
            for (Receta recetaCollectionRecetaToAttach : paciente.getRecetaCollection()) {
                recetaCollectionRecetaToAttach = em.getReference(recetaCollectionRecetaToAttach.getClass(), recetaCollectionRecetaToAttach.getIdreceta());
                attachedRecetaCollection.add(recetaCollectionRecetaToAttach);
            }
            paciente.setRecetaCollection(attachedRecetaCollection);
            Collection<Expediente> attachedExpedienteCollection = new ArrayList<Expediente>();
            for (Expediente expedienteCollectionExpedienteToAttach : paciente.getExpedienteCollection()) {
                expedienteCollectionExpedienteToAttach = em.getReference(expedienteCollectionExpedienteToAttach.getClass(), expedienteCollectionExpedienteToAttach.getIdexpediente());
                attachedExpedienteCollection.add(expedienteCollectionExpedienteToAttach);
            }
            paciente.setExpedienteCollection(attachedExpedienteCollection);
            Collection<Cita> attachedCitaCollection = new ArrayList<Cita>();
            for (Cita citaCollectionCitaToAttach : paciente.getCitaCollection()) {
                citaCollectionCitaToAttach = em.getReference(citaCollectionCitaToAttach.getClass(), citaCollectionCitaToAttach.getIdcita());
                attachedCitaCollection.add(citaCollectionCitaToAttach);
            }
            paciente.setCitaCollection(attachedCitaCollection);
            em.persist(paciente);
            if (personaId != null) {
                personaId.getPacienteCollection().add(paciente);
                personaId = em.merge(personaId);
            }
            for (Receta recetaCollectionReceta : paciente.getRecetaCollection()) {
                Paciente oldPacienteIdOfRecetaCollectionReceta = recetaCollectionReceta.getPacienteId();
                recetaCollectionReceta.setPacienteId(paciente);
                recetaCollectionReceta = em.merge(recetaCollectionReceta);
                if (oldPacienteIdOfRecetaCollectionReceta != null) {
                    oldPacienteIdOfRecetaCollectionReceta.getRecetaCollection().remove(recetaCollectionReceta);
                    oldPacienteIdOfRecetaCollectionReceta = em.merge(oldPacienteIdOfRecetaCollectionReceta);
                }
            }
            for (Expediente expedienteCollectionExpediente : paciente.getExpedienteCollection()) {
                Paciente oldPacienteIdOfExpedienteCollectionExpediente = expedienteCollectionExpediente.getPacienteId();
                expedienteCollectionExpediente.setPacienteId(paciente);
                expedienteCollectionExpediente = em.merge(expedienteCollectionExpediente);
                if (oldPacienteIdOfExpedienteCollectionExpediente != null) {
                    oldPacienteIdOfExpedienteCollectionExpediente.getExpedienteCollection().remove(expedienteCollectionExpediente);
                    oldPacienteIdOfExpedienteCollectionExpediente = em.merge(oldPacienteIdOfExpedienteCollectionExpediente);
                }
            }
            for (Cita citaCollectionCita : paciente.getCitaCollection()) {
                Paciente oldPacienteIdOfCitaCollectionCita = citaCollectionCita.getPacienteId();
                citaCollectionCita.setPacienteId(paciente);
                citaCollectionCita = em.merge(citaCollectionCita);
                if (oldPacienteIdOfCitaCollectionCita != null) {
                    oldPacienteIdOfCitaCollectionCita.getCitaCollection().remove(citaCollectionCita);
                    oldPacienteIdOfCitaCollectionCita = em.merge(oldPacienteIdOfCitaCollectionCita);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Paciente paciente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paciente persistentPaciente = em.find(Paciente.class, paciente.getId());
            Persona personaIdOld = persistentPaciente.getPersonaId();
            Persona personaIdNew = paciente.getPersonaId();
            Collection<Receta> recetaCollectionOld = persistentPaciente.getRecetaCollection();
            Collection<Receta> recetaCollectionNew = paciente.getRecetaCollection();
            Collection<Expediente> expedienteCollectionOld = persistentPaciente.getExpedienteCollection();
            Collection<Expediente> expedienteCollectionNew = paciente.getExpedienteCollection();
            Collection<Cita> citaCollectionOld = persistentPaciente.getCitaCollection();
            Collection<Cita> citaCollectionNew = paciente.getCitaCollection();
            if (personaIdNew != null) {
                personaIdNew = em.getReference(personaIdNew.getClass(), personaIdNew.getId());
                paciente.setPersonaId(personaIdNew);
            }
            Collection<Receta> attachedRecetaCollectionNew = new ArrayList<Receta>();
            for (Receta recetaCollectionNewRecetaToAttach : recetaCollectionNew) {
                recetaCollectionNewRecetaToAttach = em.getReference(recetaCollectionNewRecetaToAttach.getClass(), recetaCollectionNewRecetaToAttach.getIdreceta());
                attachedRecetaCollectionNew.add(recetaCollectionNewRecetaToAttach);
            }
            recetaCollectionNew = attachedRecetaCollectionNew;
            paciente.setRecetaCollection(recetaCollectionNew);
            Collection<Expediente> attachedExpedienteCollectionNew = new ArrayList<Expediente>();
            for (Expediente expedienteCollectionNewExpedienteToAttach : expedienteCollectionNew) {
                expedienteCollectionNewExpedienteToAttach = em.getReference(expedienteCollectionNewExpedienteToAttach.getClass(), expedienteCollectionNewExpedienteToAttach.getIdexpediente());
                attachedExpedienteCollectionNew.add(expedienteCollectionNewExpedienteToAttach);
            }
            expedienteCollectionNew = attachedExpedienteCollectionNew;
            paciente.setExpedienteCollection(expedienteCollectionNew);
            Collection<Cita> attachedCitaCollectionNew = new ArrayList<Cita>();
            for (Cita citaCollectionNewCitaToAttach : citaCollectionNew) {
                citaCollectionNewCitaToAttach = em.getReference(citaCollectionNewCitaToAttach.getClass(), citaCollectionNewCitaToAttach.getIdcita());
                attachedCitaCollectionNew.add(citaCollectionNewCitaToAttach);
            }
            citaCollectionNew = attachedCitaCollectionNew;
            paciente.setCitaCollection(citaCollectionNew);
            paciente = em.merge(paciente);
            if (personaIdOld != null && !personaIdOld.equals(personaIdNew)) {
                personaIdOld.getPacienteCollection().remove(paciente);
                personaIdOld = em.merge(personaIdOld);
            }
            if (personaIdNew != null && !personaIdNew.equals(personaIdOld)) {
                personaIdNew.getPacienteCollection().add(paciente);
                personaIdNew = em.merge(personaIdNew);
            }
            for (Receta recetaCollectionOldReceta : recetaCollectionOld) {
                if (!recetaCollectionNew.contains(recetaCollectionOldReceta)) {
                    recetaCollectionOldReceta.setPacienteId(null);
                    recetaCollectionOldReceta = em.merge(recetaCollectionOldReceta);
                }
            }
            for (Receta recetaCollectionNewReceta : recetaCollectionNew) {
                if (!recetaCollectionOld.contains(recetaCollectionNewReceta)) {
                    Paciente oldPacienteIdOfRecetaCollectionNewReceta = recetaCollectionNewReceta.getPacienteId();
                    recetaCollectionNewReceta.setPacienteId(paciente);
                    recetaCollectionNewReceta = em.merge(recetaCollectionNewReceta);
                    if (oldPacienteIdOfRecetaCollectionNewReceta != null && !oldPacienteIdOfRecetaCollectionNewReceta.equals(paciente)) {
                        oldPacienteIdOfRecetaCollectionNewReceta.getRecetaCollection().remove(recetaCollectionNewReceta);
                        oldPacienteIdOfRecetaCollectionNewReceta = em.merge(oldPacienteIdOfRecetaCollectionNewReceta);
                    }
                }
            }
            for (Expediente expedienteCollectionOldExpediente : expedienteCollectionOld) {
                if (!expedienteCollectionNew.contains(expedienteCollectionOldExpediente)) {
                    expedienteCollectionOldExpediente.setPacienteId(null);
                    expedienteCollectionOldExpediente = em.merge(expedienteCollectionOldExpediente);
                }
            }
            for (Expediente expedienteCollectionNewExpediente : expedienteCollectionNew) {
                if (!expedienteCollectionOld.contains(expedienteCollectionNewExpediente)) {
                    Paciente oldPacienteIdOfExpedienteCollectionNewExpediente = expedienteCollectionNewExpediente.getPacienteId();
                    expedienteCollectionNewExpediente.setPacienteId(paciente);
                    expedienteCollectionNewExpediente = em.merge(expedienteCollectionNewExpediente);
                    if (oldPacienteIdOfExpedienteCollectionNewExpediente != null && !oldPacienteIdOfExpedienteCollectionNewExpediente.equals(paciente)) {
                        oldPacienteIdOfExpedienteCollectionNewExpediente.getExpedienteCollection().remove(expedienteCollectionNewExpediente);
                        oldPacienteIdOfExpedienteCollectionNewExpediente = em.merge(oldPacienteIdOfExpedienteCollectionNewExpediente);
                    }
                }
            }
            for (Cita citaCollectionOldCita : citaCollectionOld) {
                if (!citaCollectionNew.contains(citaCollectionOldCita)) {
                    citaCollectionOldCita.setPacienteId(null);
                    citaCollectionOldCita = em.merge(citaCollectionOldCita);
                }
            }
            for (Cita citaCollectionNewCita : citaCollectionNew) {
                if (!citaCollectionOld.contains(citaCollectionNewCita)) {
                    Paciente oldPacienteIdOfCitaCollectionNewCita = citaCollectionNewCita.getPacienteId();
                    citaCollectionNewCita.setPacienteId(paciente);
                    citaCollectionNewCita = em.merge(citaCollectionNewCita);
                    if (oldPacienteIdOfCitaCollectionNewCita != null && !oldPacienteIdOfCitaCollectionNewCita.equals(paciente)) {
                        oldPacienteIdOfCitaCollectionNewCita.getCitaCollection().remove(citaCollectionNewCita);
                        oldPacienteIdOfCitaCollectionNewCita = em.merge(oldPacienteIdOfCitaCollectionNewCita);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = paciente.getId();
                if (findPaciente(id) == null) {
                    throw new NonexistentEntityException("The paciente with id " + id + " no longer exists.");
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
            Paciente paciente;
            try {
                paciente = em.getReference(Paciente.class, id);
                paciente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paciente with id " + id + " no longer exists.", enfe);
            }
            Persona personaId = paciente.getPersonaId();
            if (personaId != null) {
                personaId.getPacienteCollection().remove(paciente);
                personaId = em.merge(personaId);
            }
            Collection<Receta> recetaCollection = paciente.getRecetaCollection();
            for (Receta recetaCollectionReceta : recetaCollection) {
                recetaCollectionReceta.setPacienteId(null);
                recetaCollectionReceta = em.merge(recetaCollectionReceta);
            }
            Collection<Expediente> expedienteCollection = paciente.getExpedienteCollection();
            for (Expediente expedienteCollectionExpediente : expedienteCollection) {
                expedienteCollectionExpediente.setPacienteId(null);
                expedienteCollectionExpediente = em.merge(expedienteCollectionExpediente);
            }
            Collection<Cita> citaCollection = paciente.getCitaCollection();
            for (Cita citaCollectionCita : citaCollection) {
                citaCollectionCita.setPacienteId(null);
                citaCollectionCita = em.merge(citaCollectionCita);
            }
            em.remove(paciente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Paciente> findPacienteEntities() {
        return findPacienteEntities(true, -1, -1);
    }

    public List<Paciente> findPacienteEntities(int maxResults, int firstResult) {
        return findPacienteEntities(false, maxResults, firstResult);
    }

    private List<Paciente> findPacienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Paciente.class));
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

    public Paciente findPaciente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Paciente.class, id);
        } finally {
            em.close();
        }
    }

    public int getPacienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Paciente> rt = cq.from(Paciente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
