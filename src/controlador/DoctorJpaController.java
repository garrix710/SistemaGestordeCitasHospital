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
import modelo.Cita;
import modelo.Doctor;

/**
 *
 * @author abiga
 */
public class DoctorJpaController implements Serializable {

    public DoctorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Doctor doctor) {
        if (doctor.getRecetaCollection() == null) {
            doctor.setRecetaCollection(new ArrayList<Receta>());
        }
        if (doctor.getCitaCollection() == null) {
            doctor.setCitaCollection(new ArrayList<Cita>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona personaId = doctor.getPersonaId();
            if (personaId != null) {
                personaId = em.getReference(personaId.getClass(), personaId.getId());
                doctor.setPersonaId(personaId);
            }
            Collection<Receta> attachedRecetaCollection = new ArrayList<Receta>();
            for (Receta recetaCollectionRecetaToAttach : doctor.getRecetaCollection()) {
                recetaCollectionRecetaToAttach = em.getReference(recetaCollectionRecetaToAttach.getClass(), recetaCollectionRecetaToAttach.getIdreceta());
                attachedRecetaCollection.add(recetaCollectionRecetaToAttach);
            }
            doctor.setRecetaCollection(attachedRecetaCollection);
            Collection<Cita> attachedCitaCollection = new ArrayList<Cita>();
            for (Cita citaCollectionCitaToAttach : doctor.getCitaCollection()) {
                citaCollectionCitaToAttach = em.getReference(citaCollectionCitaToAttach.getClass(), citaCollectionCitaToAttach.getIdcita());
                attachedCitaCollection.add(citaCollectionCitaToAttach);
            }
            doctor.setCitaCollection(attachedCitaCollection);
            em.persist(doctor);
            if (personaId != null) {
                personaId.getDoctorCollection().add(doctor);
                personaId = em.merge(personaId);
            }
            for (Receta recetaCollectionReceta : doctor.getRecetaCollection()) {
                Doctor oldDoctorIdOfRecetaCollectionReceta = recetaCollectionReceta.getDoctorId();
                recetaCollectionReceta.setDoctorId(doctor);
                recetaCollectionReceta = em.merge(recetaCollectionReceta);
                if (oldDoctorIdOfRecetaCollectionReceta != null) {
                    oldDoctorIdOfRecetaCollectionReceta.getRecetaCollection().remove(recetaCollectionReceta);
                    oldDoctorIdOfRecetaCollectionReceta = em.merge(oldDoctorIdOfRecetaCollectionReceta);
                }
            }
            for (Cita citaCollectionCita : doctor.getCitaCollection()) {
                Doctor oldDoctorIdOfCitaCollectionCita = citaCollectionCita.getDoctorId();
                citaCollectionCita.setDoctorId(doctor);
                citaCollectionCita = em.merge(citaCollectionCita);
                if (oldDoctorIdOfCitaCollectionCita != null) {
                    oldDoctorIdOfCitaCollectionCita.getCitaCollection().remove(citaCollectionCita);
                    oldDoctorIdOfCitaCollectionCita = em.merge(oldDoctorIdOfCitaCollectionCita);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Doctor doctor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Doctor persistentDoctor = em.find(Doctor.class, doctor.getId());
            Persona personaIdOld = persistentDoctor.getPersonaId();
            Persona personaIdNew = doctor.getPersonaId();
            Collection<Receta> recetaCollectionOld = persistentDoctor.getRecetaCollection();
            Collection<Receta> recetaCollectionNew = doctor.getRecetaCollection();
            Collection<Cita> citaCollectionOld = persistentDoctor.getCitaCollection();
            Collection<Cita> citaCollectionNew = doctor.getCitaCollection();
            if (personaIdNew != null) {
                personaIdNew = em.getReference(personaIdNew.getClass(), personaIdNew.getId());
                doctor.setPersonaId(personaIdNew);
            }
            Collection<Receta> attachedRecetaCollectionNew = new ArrayList<Receta>();
            for (Receta recetaCollectionNewRecetaToAttach : recetaCollectionNew) {
                recetaCollectionNewRecetaToAttach = em.getReference(recetaCollectionNewRecetaToAttach.getClass(), recetaCollectionNewRecetaToAttach.getIdreceta());
                attachedRecetaCollectionNew.add(recetaCollectionNewRecetaToAttach);
            }
            recetaCollectionNew = attachedRecetaCollectionNew;
            doctor.setRecetaCollection(recetaCollectionNew);
            Collection<Cita> attachedCitaCollectionNew = new ArrayList<Cita>();
            for (Cita citaCollectionNewCitaToAttach : citaCollectionNew) {
                citaCollectionNewCitaToAttach = em.getReference(citaCollectionNewCitaToAttach.getClass(), citaCollectionNewCitaToAttach.getIdcita());
                attachedCitaCollectionNew.add(citaCollectionNewCitaToAttach);
            }
            citaCollectionNew = attachedCitaCollectionNew;
            doctor.setCitaCollection(citaCollectionNew);
            doctor = em.merge(doctor);
            if (personaIdOld != null && !personaIdOld.equals(personaIdNew)) {
                personaIdOld.getDoctorCollection().remove(doctor);
                personaIdOld = em.merge(personaIdOld);
            }
            if (personaIdNew != null && !personaIdNew.equals(personaIdOld)) {
                personaIdNew.getDoctorCollection().add(doctor);
                personaIdNew = em.merge(personaIdNew);
            }
            for (Receta recetaCollectionOldReceta : recetaCollectionOld) {
                if (!recetaCollectionNew.contains(recetaCollectionOldReceta)) {
                    recetaCollectionOldReceta.setDoctorId(null);
                    recetaCollectionOldReceta = em.merge(recetaCollectionOldReceta);
                }
            }
            for (Receta recetaCollectionNewReceta : recetaCollectionNew) {
                if (!recetaCollectionOld.contains(recetaCollectionNewReceta)) {
                    Doctor oldDoctorIdOfRecetaCollectionNewReceta = recetaCollectionNewReceta.getDoctorId();
                    recetaCollectionNewReceta.setDoctorId(doctor);
                    recetaCollectionNewReceta = em.merge(recetaCollectionNewReceta);
                    if (oldDoctorIdOfRecetaCollectionNewReceta != null && !oldDoctorIdOfRecetaCollectionNewReceta.equals(doctor)) {
                        oldDoctorIdOfRecetaCollectionNewReceta.getRecetaCollection().remove(recetaCollectionNewReceta);
                        oldDoctorIdOfRecetaCollectionNewReceta = em.merge(oldDoctorIdOfRecetaCollectionNewReceta);
                    }
                }
            }
            for (Cita citaCollectionOldCita : citaCollectionOld) {
                if (!citaCollectionNew.contains(citaCollectionOldCita)) {
                    citaCollectionOldCita.setDoctorId(null);
                    citaCollectionOldCita = em.merge(citaCollectionOldCita);
                }
            }
            for (Cita citaCollectionNewCita : citaCollectionNew) {
                if (!citaCollectionOld.contains(citaCollectionNewCita)) {
                    Doctor oldDoctorIdOfCitaCollectionNewCita = citaCollectionNewCita.getDoctorId();
                    citaCollectionNewCita.setDoctorId(doctor);
                    citaCollectionNewCita = em.merge(citaCollectionNewCita);
                    if (oldDoctorIdOfCitaCollectionNewCita != null && !oldDoctorIdOfCitaCollectionNewCita.equals(doctor)) {
                        oldDoctorIdOfCitaCollectionNewCita.getCitaCollection().remove(citaCollectionNewCita);
                        oldDoctorIdOfCitaCollectionNewCita = em.merge(oldDoctorIdOfCitaCollectionNewCita);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = doctor.getId();
                if (findDoctor(id) == null) {
                    throw new NonexistentEntityException("The doctor with id " + id + " no longer exists.");
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
            Doctor doctor;
            try {
                doctor = em.getReference(Doctor.class, id);
                doctor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The doctor with id " + id + " no longer exists.", enfe);
            }
            Persona personaId = doctor.getPersonaId();
            if (personaId != null) {
                personaId.getDoctorCollection().remove(doctor);
                personaId = em.merge(personaId);
            }
            Collection<Receta> recetaCollection = doctor.getRecetaCollection();
            for (Receta recetaCollectionReceta : recetaCollection) {
                recetaCollectionReceta.setDoctorId(null);
                recetaCollectionReceta = em.merge(recetaCollectionReceta);
            }
            Collection<Cita> citaCollection = doctor.getCitaCollection();
            for (Cita citaCollectionCita : citaCollection) {
                citaCollectionCita.setDoctorId(null);
                citaCollectionCita = em.merge(citaCollectionCita);
            }
            em.remove(doctor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Doctor> findDoctorEntities() {
        return findDoctorEntities(true, -1, -1);
    }

    public List<Doctor> findDoctorEntities(int maxResults, int firstResult) {
        return findDoctorEntities(false, maxResults, firstResult);
    }

    private List<Doctor> findDoctorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Doctor.class));
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

    public Doctor findDoctor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Doctor.class, id);
        } finally {
            em.close();
        }
    }

    public int getDoctorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Doctor> rt = cq.from(Doctor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
