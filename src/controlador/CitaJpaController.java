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
import modelo.Doctor;
import modelo.Expediente;
import modelo.Paciente;
import modelo.Secretaria;
import modelo.Consulta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Cita;

/**
 *
 * @author abiga
 */
public class CitaJpaController implements Serializable {

    public CitaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cita cita) {
        if (cita.getConsultaCollection() == null) {
            cita.setConsultaCollection(new ArrayList<Consulta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Doctor doctorId = cita.getDoctorId();
            if (doctorId != null) {
                doctorId = em.getReference(doctorId.getClass(), doctorId.getId());
                cita.setDoctorId(doctorId);
            }
            Expediente expedienteId = cita.getExpedienteId();
            if (expedienteId != null) {
                expedienteId = em.getReference(expedienteId.getClass(), expedienteId.getIdexpediente());
                cita.setExpedienteId(expedienteId);
            }
            Paciente pacienteId = cita.getPacienteId();
            if (pacienteId != null) {
                pacienteId = em.getReference(pacienteId.getClass(), pacienteId.getId());
                cita.setPacienteId(pacienteId);
            }
            Secretaria secretariaId = cita.getSecretariaId();
            if (secretariaId != null) {
                secretariaId = em.getReference(secretariaId.getClass(), secretariaId.getId());
                cita.setSecretariaId(secretariaId);
            }
            Collection<Consulta> attachedConsultaCollection = new ArrayList<Consulta>();
            for (Consulta consultaCollectionConsultaToAttach : cita.getConsultaCollection()) {
                consultaCollectionConsultaToAttach = em.getReference(consultaCollectionConsultaToAttach.getClass(), consultaCollectionConsultaToAttach.getIdconsulta());
                attachedConsultaCollection.add(consultaCollectionConsultaToAttach);
            }
            cita.setConsultaCollection(attachedConsultaCollection);
            em.persist(cita);
            if (doctorId != null) {
                doctorId.getCitaCollection().add(cita);
                doctorId = em.merge(doctorId);
            }
            if (expedienteId != null) {
                expedienteId.getCitaCollection().add(cita);
                expedienteId = em.merge(expedienteId);
            }
            if (pacienteId != null) {
                pacienteId.getCitaCollection().add(cita);
                pacienteId = em.merge(pacienteId);
            }
            if (secretariaId != null) {
                secretariaId.getCitaCollection().add(cita);
                secretariaId = em.merge(secretariaId);
            }
            for (Consulta consultaCollectionConsulta : cita.getConsultaCollection()) {
                Cita oldCitaIdOfConsultaCollectionConsulta = consultaCollectionConsulta.getCitaId();
                consultaCollectionConsulta.setCitaId(cita);
                consultaCollectionConsulta = em.merge(consultaCollectionConsulta);
                if (oldCitaIdOfConsultaCollectionConsulta != null) {
                    oldCitaIdOfConsultaCollectionConsulta.getConsultaCollection().remove(consultaCollectionConsulta);
                    oldCitaIdOfConsultaCollectionConsulta = em.merge(oldCitaIdOfConsultaCollectionConsulta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cita cita) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cita persistentCita = em.find(Cita.class, cita.getIdcita());
            Doctor doctorIdOld = persistentCita.getDoctorId();
            Doctor doctorIdNew = cita.getDoctorId();
            Expediente expedienteIdOld = persistentCita.getExpedienteId();
            Expediente expedienteIdNew = cita.getExpedienteId();
            Paciente pacienteIdOld = persistentCita.getPacienteId();
            Paciente pacienteIdNew = cita.getPacienteId();
            Secretaria secretariaIdOld = persistentCita.getSecretariaId();
            Secretaria secretariaIdNew = cita.getSecretariaId();
            Collection<Consulta> consultaCollectionOld = persistentCita.getConsultaCollection();
            Collection<Consulta> consultaCollectionNew = cita.getConsultaCollection();
            if (doctorIdNew != null) {
                doctorIdNew = em.getReference(doctorIdNew.getClass(), doctorIdNew.getId());
                cita.setDoctorId(doctorIdNew);
            }
            if (expedienteIdNew != null) {
                expedienteIdNew = em.getReference(expedienteIdNew.getClass(), expedienteIdNew.getIdexpediente());
                cita.setExpedienteId(expedienteIdNew);
            }
            if (pacienteIdNew != null) {
                pacienteIdNew = em.getReference(pacienteIdNew.getClass(), pacienteIdNew.getId());
                cita.setPacienteId(pacienteIdNew);
            }
            if (secretariaIdNew != null) {
                secretariaIdNew = em.getReference(secretariaIdNew.getClass(), secretariaIdNew.getId());
                cita.setSecretariaId(secretariaIdNew);
            }
            Collection<Consulta> attachedConsultaCollectionNew = new ArrayList<Consulta>();
            for (Consulta consultaCollectionNewConsultaToAttach : consultaCollectionNew) {
                consultaCollectionNewConsultaToAttach = em.getReference(consultaCollectionNewConsultaToAttach.getClass(), consultaCollectionNewConsultaToAttach.getIdconsulta());
                attachedConsultaCollectionNew.add(consultaCollectionNewConsultaToAttach);
            }
            consultaCollectionNew = attachedConsultaCollectionNew;
            cita.setConsultaCollection(consultaCollectionNew);
            cita = em.merge(cita);
            if (doctorIdOld != null && !doctorIdOld.equals(doctorIdNew)) {
                doctorIdOld.getCitaCollection().remove(cita);
                doctorIdOld = em.merge(doctorIdOld);
            }
            if (doctorIdNew != null && !doctorIdNew.equals(doctorIdOld)) {
                doctorIdNew.getCitaCollection().add(cita);
                doctorIdNew = em.merge(doctorIdNew);
            }
            if (expedienteIdOld != null && !expedienteIdOld.equals(expedienteIdNew)) {
                expedienteIdOld.getCitaCollection().remove(cita);
                expedienteIdOld = em.merge(expedienteIdOld);
            }
            if (expedienteIdNew != null && !expedienteIdNew.equals(expedienteIdOld)) {
                expedienteIdNew.getCitaCollection().add(cita);
                expedienteIdNew = em.merge(expedienteIdNew);
            }
            if (pacienteIdOld != null && !pacienteIdOld.equals(pacienteIdNew)) {
                pacienteIdOld.getCitaCollection().remove(cita);
                pacienteIdOld = em.merge(pacienteIdOld);
            }
            if (pacienteIdNew != null && !pacienteIdNew.equals(pacienteIdOld)) {
                pacienteIdNew.getCitaCollection().add(cita);
                pacienteIdNew = em.merge(pacienteIdNew);
            }
            if (secretariaIdOld != null && !secretariaIdOld.equals(secretariaIdNew)) {
                secretariaIdOld.getCitaCollection().remove(cita);
                secretariaIdOld = em.merge(secretariaIdOld);
            }
            if (secretariaIdNew != null && !secretariaIdNew.equals(secretariaIdOld)) {
                secretariaIdNew.getCitaCollection().add(cita);
                secretariaIdNew = em.merge(secretariaIdNew);
            }
            for (Consulta consultaCollectionOldConsulta : consultaCollectionOld) {
                if (!consultaCollectionNew.contains(consultaCollectionOldConsulta)) {
                    consultaCollectionOldConsulta.setCitaId(null);
                    consultaCollectionOldConsulta = em.merge(consultaCollectionOldConsulta);
                }
            }
            for (Consulta consultaCollectionNewConsulta : consultaCollectionNew) {
                if (!consultaCollectionOld.contains(consultaCollectionNewConsulta)) {
                    Cita oldCitaIdOfConsultaCollectionNewConsulta = consultaCollectionNewConsulta.getCitaId();
                    consultaCollectionNewConsulta.setCitaId(cita);
                    consultaCollectionNewConsulta = em.merge(consultaCollectionNewConsulta);
                    if (oldCitaIdOfConsultaCollectionNewConsulta != null && !oldCitaIdOfConsultaCollectionNewConsulta.equals(cita)) {
                        oldCitaIdOfConsultaCollectionNewConsulta.getConsultaCollection().remove(consultaCollectionNewConsulta);
                        oldCitaIdOfConsultaCollectionNewConsulta = em.merge(oldCitaIdOfConsultaCollectionNewConsulta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cita.getIdcita();
                if (findCita(id) == null) {
                    throw new NonexistentEntityException("The cita with id " + id + " no longer exists.");
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
            Cita cita;
            try {
                cita = em.getReference(Cita.class, id);
                cita.getIdcita();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cita with id " + id + " no longer exists.", enfe);
            }
            Doctor doctorId = cita.getDoctorId();
            if (doctorId != null) {
                doctorId.getCitaCollection().remove(cita);
                doctorId = em.merge(doctorId);
            }
            Expediente expedienteId = cita.getExpedienteId();
            if (expedienteId != null) {
                expedienteId.getCitaCollection().remove(cita);
                expedienteId = em.merge(expedienteId);
            }
            Paciente pacienteId = cita.getPacienteId();
            if (pacienteId != null) {
                pacienteId.getCitaCollection().remove(cita);
                pacienteId = em.merge(pacienteId);
            }
            Secretaria secretariaId = cita.getSecretariaId();
            if (secretariaId != null) {
                secretariaId.getCitaCollection().remove(cita);
                secretariaId = em.merge(secretariaId);
            }
            Collection<Consulta> consultaCollection = cita.getConsultaCollection();
            for (Consulta consultaCollectionConsulta : consultaCollection) {
                consultaCollectionConsulta.setCitaId(null);
                consultaCollectionConsulta = em.merge(consultaCollectionConsulta);
            }
            em.remove(cita);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cita> findCitaEntities() {
        return findCitaEntities(true, -1, -1);
    }

    public List<Cita> findCitaEntities(int maxResults, int firstResult) {
        return findCitaEntities(false, maxResults, firstResult);
    }

    private List<Cita> findCitaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cita.class));
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

    public Cita findCita(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cita.class, id);
        } finally {
            em.close();
        }
    }

    public int getCitaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cita> rt = cq.from(Cita.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
