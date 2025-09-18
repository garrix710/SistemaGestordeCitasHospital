/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Consulta;
import modelo.Doctor;
import modelo.Paciente;
import modelo.Receta;

/**
 *
 * @author abiga
 */
public class RecetaJpaController implements Serializable {

    public RecetaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Receta receta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Consulta consultaId = receta.getConsultaId();
            if (consultaId != null) {
                consultaId = em.getReference(consultaId.getClass(), consultaId.getIdconsulta());
                receta.setConsultaId(consultaId);
            }
            Doctor doctorId = receta.getDoctorId();
            if (doctorId != null) {
                doctorId = em.getReference(doctorId.getClass(), doctorId.getId());
                receta.setDoctorId(doctorId);
            }
            Paciente pacienteId = receta.getPacienteId();
            if (pacienteId != null) {
                pacienteId = em.getReference(pacienteId.getClass(), pacienteId.getId());
                receta.setPacienteId(pacienteId);
            }
            em.persist(receta);
            if (consultaId != null) {
                consultaId.getRecetaCollection().add(receta);
                consultaId = em.merge(consultaId);
            }
            if (doctorId != null) {
                doctorId.getRecetaCollection().add(receta);
                doctorId = em.merge(doctorId);
            }
            if (pacienteId != null) {
                pacienteId.getRecetaCollection().add(receta);
                pacienteId = em.merge(pacienteId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Receta receta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Receta persistentReceta = em.find(Receta.class, receta.getIdreceta());
            Consulta consultaIdOld = persistentReceta.getConsultaId();
            Consulta consultaIdNew = receta.getConsultaId();
            Doctor doctorIdOld = persistentReceta.getDoctorId();
            Doctor doctorIdNew = receta.getDoctorId();
            Paciente pacienteIdOld = persistentReceta.getPacienteId();
            Paciente pacienteIdNew = receta.getPacienteId();
            if (consultaIdNew != null) {
                consultaIdNew = em.getReference(consultaIdNew.getClass(), consultaIdNew.getIdconsulta());
                receta.setConsultaId(consultaIdNew);
            }
            if (doctorIdNew != null) {
                doctorIdNew = em.getReference(doctorIdNew.getClass(), doctorIdNew.getId());
                receta.setDoctorId(doctorIdNew);
            }
            if (pacienteIdNew != null) {
                pacienteIdNew = em.getReference(pacienteIdNew.getClass(), pacienteIdNew.getId());
                receta.setPacienteId(pacienteIdNew);
            }
            receta = em.merge(receta);
            if (consultaIdOld != null && !consultaIdOld.equals(consultaIdNew)) {
                consultaIdOld.getRecetaCollection().remove(receta);
                consultaIdOld = em.merge(consultaIdOld);
            }
            if (consultaIdNew != null && !consultaIdNew.equals(consultaIdOld)) {
                consultaIdNew.getRecetaCollection().add(receta);
                consultaIdNew = em.merge(consultaIdNew);
            }
            if (doctorIdOld != null && !doctorIdOld.equals(doctorIdNew)) {
                doctorIdOld.getRecetaCollection().remove(receta);
                doctorIdOld = em.merge(doctorIdOld);
            }
            if (doctorIdNew != null && !doctorIdNew.equals(doctorIdOld)) {
                doctorIdNew.getRecetaCollection().add(receta);
                doctorIdNew = em.merge(doctorIdNew);
            }
            if (pacienteIdOld != null && !pacienteIdOld.equals(pacienteIdNew)) {
                pacienteIdOld.getRecetaCollection().remove(receta);
                pacienteIdOld = em.merge(pacienteIdOld);
            }
            if (pacienteIdNew != null && !pacienteIdNew.equals(pacienteIdOld)) {
                pacienteIdNew.getRecetaCollection().add(receta);
                pacienteIdNew = em.merge(pacienteIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = receta.getIdreceta();
                if (findReceta(id) == null) {
                    throw new NonexistentEntityException("The receta with id " + id + " no longer exists.");
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
            Receta receta;
            try {
                receta = em.getReference(Receta.class, id);
                receta.getIdreceta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The receta with id " + id + " no longer exists.", enfe);
            }
            Consulta consultaId = receta.getConsultaId();
            if (consultaId != null) {
                consultaId.getRecetaCollection().remove(receta);
                consultaId = em.merge(consultaId);
            }
            Doctor doctorId = receta.getDoctorId();
            if (doctorId != null) {
                doctorId.getRecetaCollection().remove(receta);
                doctorId = em.merge(doctorId);
            }
            Paciente pacienteId = receta.getPacienteId();
            if (pacienteId != null) {
                pacienteId.getRecetaCollection().remove(receta);
                pacienteId = em.merge(pacienteId);
            }
            em.remove(receta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Receta> findRecetaEntities() {
        return findRecetaEntities(true, -1, -1);
    }

    public List<Receta> findRecetaEntities(int maxResults, int firstResult) {
        return findRecetaEntities(false, maxResults, firstResult);
    }

    private List<Receta> findRecetaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Receta.class));
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

    public Receta findReceta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Receta.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecetaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Receta> rt = cq.from(Receta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
