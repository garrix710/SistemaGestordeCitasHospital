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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Administrador;
import modelo.Paciente;
import modelo.Persona;
import modelo.Secretaria;

/**
 *
 * @author abiga
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) {
        if (persona.getDoctorCollection() == null) {
            persona.setDoctorCollection(new ArrayList<Doctor>());
        }
        if (persona.getAdministradorCollection() == null) {
            persona.setAdministradorCollection(new ArrayList<Administrador>());
        }
        if (persona.getPacienteCollection() == null) {
            persona.setPacienteCollection(new ArrayList<Paciente>());
        }
        if (persona.getSecretariaCollection() == null) {
            persona.setSecretariaCollection(new ArrayList<Secretaria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Doctor> attachedDoctorCollection = new ArrayList<Doctor>();
            for (Doctor doctorCollectionDoctorToAttach : persona.getDoctorCollection()) {
                doctorCollectionDoctorToAttach = em.getReference(doctorCollectionDoctorToAttach.getClass(), doctorCollectionDoctorToAttach.getId());
                attachedDoctorCollection.add(doctorCollectionDoctorToAttach);
            }
            persona.setDoctorCollection(attachedDoctorCollection);
            Collection<Administrador> attachedAdministradorCollection = new ArrayList<Administrador>();
            for (Administrador administradorCollectionAdministradorToAttach : persona.getAdministradorCollection()) {
                administradorCollectionAdministradorToAttach = em.getReference(administradorCollectionAdministradorToAttach.getClass(), administradorCollectionAdministradorToAttach.getId());
                attachedAdministradorCollection.add(administradorCollectionAdministradorToAttach);
            }
            persona.setAdministradorCollection(attachedAdministradorCollection);
            Collection<Paciente> attachedPacienteCollection = new ArrayList<Paciente>();
            for (Paciente pacienteCollectionPacienteToAttach : persona.getPacienteCollection()) {
                pacienteCollectionPacienteToAttach = em.getReference(pacienteCollectionPacienteToAttach.getClass(), pacienteCollectionPacienteToAttach.getId());
                attachedPacienteCollection.add(pacienteCollectionPacienteToAttach);
            }
            persona.setPacienteCollection(attachedPacienteCollection);
            Collection<Secretaria> attachedSecretariaCollection = new ArrayList<Secretaria>();
            for (Secretaria secretariaCollectionSecretariaToAttach : persona.getSecretariaCollection()) {
                secretariaCollectionSecretariaToAttach = em.getReference(secretariaCollectionSecretariaToAttach.getClass(), secretariaCollectionSecretariaToAttach.getId());
                attachedSecretariaCollection.add(secretariaCollectionSecretariaToAttach);
            }
            persona.setSecretariaCollection(attachedSecretariaCollection);
            em.persist(persona);
            for (Doctor doctorCollectionDoctor : persona.getDoctorCollection()) {
                Persona oldPersonaIdOfDoctorCollectionDoctor = doctorCollectionDoctor.getPersonaId();
                doctorCollectionDoctor.setPersonaId(persona);
                doctorCollectionDoctor = em.merge(doctorCollectionDoctor);
                if (oldPersonaIdOfDoctorCollectionDoctor != null) {
                    oldPersonaIdOfDoctorCollectionDoctor.getDoctorCollection().remove(doctorCollectionDoctor);
                    oldPersonaIdOfDoctorCollectionDoctor = em.merge(oldPersonaIdOfDoctorCollectionDoctor);
                }
            }
            for (Administrador administradorCollectionAdministrador : persona.getAdministradorCollection()) {
                Persona oldPersonaIdOfAdministradorCollectionAdministrador = administradorCollectionAdministrador.getPersonaId();
                administradorCollectionAdministrador.setPersonaId(persona);
                administradorCollectionAdministrador = em.merge(administradorCollectionAdministrador);
                if (oldPersonaIdOfAdministradorCollectionAdministrador != null) {
                    oldPersonaIdOfAdministradorCollectionAdministrador.getAdministradorCollection().remove(administradorCollectionAdministrador);
                    oldPersonaIdOfAdministradorCollectionAdministrador = em.merge(oldPersonaIdOfAdministradorCollectionAdministrador);
                }
            }
            for (Paciente pacienteCollectionPaciente : persona.getPacienteCollection()) {
                Persona oldPersonaIdOfPacienteCollectionPaciente = pacienteCollectionPaciente.getPersonaId();
                pacienteCollectionPaciente.setPersonaId(persona);
                pacienteCollectionPaciente = em.merge(pacienteCollectionPaciente);
                if (oldPersonaIdOfPacienteCollectionPaciente != null) {
                    oldPersonaIdOfPacienteCollectionPaciente.getPacienteCollection().remove(pacienteCollectionPaciente);
                    oldPersonaIdOfPacienteCollectionPaciente = em.merge(oldPersonaIdOfPacienteCollectionPaciente);
                }
            }
            for (Secretaria secretariaCollectionSecretaria : persona.getSecretariaCollection()) {
                Persona oldPersonaIdOfSecretariaCollectionSecretaria = secretariaCollectionSecretaria.getPersonaId();
                secretariaCollectionSecretaria.setPersonaId(persona);
                secretariaCollectionSecretaria = em.merge(secretariaCollectionSecretaria);
                if (oldPersonaIdOfSecretariaCollectionSecretaria != null) {
                    oldPersonaIdOfSecretariaCollectionSecretaria.getSecretariaCollection().remove(secretariaCollectionSecretaria);
                    oldPersonaIdOfSecretariaCollectionSecretaria = em.merge(oldPersonaIdOfSecretariaCollectionSecretaria);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getId());
            Collection<Doctor> doctorCollectionOld = persistentPersona.getDoctorCollection();
            Collection<Doctor> doctorCollectionNew = persona.getDoctorCollection();
            Collection<Administrador> administradorCollectionOld = persistentPersona.getAdministradorCollection();
            Collection<Administrador> administradorCollectionNew = persona.getAdministradorCollection();
            Collection<Paciente> pacienteCollectionOld = persistentPersona.getPacienteCollection();
            Collection<Paciente> pacienteCollectionNew = persona.getPacienteCollection();
            Collection<Secretaria> secretariaCollectionOld = persistentPersona.getSecretariaCollection();
            Collection<Secretaria> secretariaCollectionNew = persona.getSecretariaCollection();
            Collection<Doctor> attachedDoctorCollectionNew = new ArrayList<Doctor>();
            for (Doctor doctorCollectionNewDoctorToAttach : doctorCollectionNew) {
                doctorCollectionNewDoctorToAttach = em.getReference(doctorCollectionNewDoctorToAttach.getClass(), doctorCollectionNewDoctorToAttach.getId());
                attachedDoctorCollectionNew.add(doctorCollectionNewDoctorToAttach);
            }
            doctorCollectionNew = attachedDoctorCollectionNew;
            persona.setDoctorCollection(doctorCollectionNew);
            Collection<Administrador> attachedAdministradorCollectionNew = new ArrayList<Administrador>();
            for (Administrador administradorCollectionNewAdministradorToAttach : administradorCollectionNew) {
                administradorCollectionNewAdministradorToAttach = em.getReference(administradorCollectionNewAdministradorToAttach.getClass(), administradorCollectionNewAdministradorToAttach.getId());
                attachedAdministradorCollectionNew.add(administradorCollectionNewAdministradorToAttach);
            }
            administradorCollectionNew = attachedAdministradorCollectionNew;
            persona.setAdministradorCollection(administradorCollectionNew);
            Collection<Paciente> attachedPacienteCollectionNew = new ArrayList<Paciente>();
            for (Paciente pacienteCollectionNewPacienteToAttach : pacienteCollectionNew) {
                pacienteCollectionNewPacienteToAttach = em.getReference(pacienteCollectionNewPacienteToAttach.getClass(), pacienteCollectionNewPacienteToAttach.getId());
                attachedPacienteCollectionNew.add(pacienteCollectionNewPacienteToAttach);
            }
            pacienteCollectionNew = attachedPacienteCollectionNew;
            persona.setPacienteCollection(pacienteCollectionNew);
            Collection<Secretaria> attachedSecretariaCollectionNew = new ArrayList<Secretaria>();
            for (Secretaria secretariaCollectionNewSecretariaToAttach : secretariaCollectionNew) {
                secretariaCollectionNewSecretariaToAttach = em.getReference(secretariaCollectionNewSecretariaToAttach.getClass(), secretariaCollectionNewSecretariaToAttach.getId());
                attachedSecretariaCollectionNew.add(secretariaCollectionNewSecretariaToAttach);
            }
            secretariaCollectionNew = attachedSecretariaCollectionNew;
            persona.setSecretariaCollection(secretariaCollectionNew);
            persona = em.merge(persona);
            for (Doctor doctorCollectionOldDoctor : doctorCollectionOld) {
                if (!doctorCollectionNew.contains(doctorCollectionOldDoctor)) {
                    doctorCollectionOldDoctor.setPersonaId(null);
                    doctorCollectionOldDoctor = em.merge(doctorCollectionOldDoctor);
                }
            }
            for (Doctor doctorCollectionNewDoctor : doctorCollectionNew) {
                if (!doctorCollectionOld.contains(doctorCollectionNewDoctor)) {
                    Persona oldPersonaIdOfDoctorCollectionNewDoctor = doctorCollectionNewDoctor.getPersonaId();
                    doctorCollectionNewDoctor.setPersonaId(persona);
                    doctorCollectionNewDoctor = em.merge(doctorCollectionNewDoctor);
                    if (oldPersonaIdOfDoctorCollectionNewDoctor != null && !oldPersonaIdOfDoctorCollectionNewDoctor.equals(persona)) {
                        oldPersonaIdOfDoctorCollectionNewDoctor.getDoctorCollection().remove(doctorCollectionNewDoctor);
                        oldPersonaIdOfDoctorCollectionNewDoctor = em.merge(oldPersonaIdOfDoctorCollectionNewDoctor);
                    }
                }
            }
            for (Administrador administradorCollectionOldAdministrador : administradorCollectionOld) {
                if (!administradorCollectionNew.contains(administradorCollectionOldAdministrador)) {
                    administradorCollectionOldAdministrador.setPersonaId(null);
                    administradorCollectionOldAdministrador = em.merge(administradorCollectionOldAdministrador);
                }
            }
            for (Administrador administradorCollectionNewAdministrador : administradorCollectionNew) {
                if (!administradorCollectionOld.contains(administradorCollectionNewAdministrador)) {
                    Persona oldPersonaIdOfAdministradorCollectionNewAdministrador = administradorCollectionNewAdministrador.getPersonaId();
                    administradorCollectionNewAdministrador.setPersonaId(persona);
                    administradorCollectionNewAdministrador = em.merge(administradorCollectionNewAdministrador);
                    if (oldPersonaIdOfAdministradorCollectionNewAdministrador != null && !oldPersonaIdOfAdministradorCollectionNewAdministrador.equals(persona)) {
                        oldPersonaIdOfAdministradorCollectionNewAdministrador.getAdministradorCollection().remove(administradorCollectionNewAdministrador);
                        oldPersonaIdOfAdministradorCollectionNewAdministrador = em.merge(oldPersonaIdOfAdministradorCollectionNewAdministrador);
                    }
                }
            }
            for (Paciente pacienteCollectionOldPaciente : pacienteCollectionOld) {
                if (!pacienteCollectionNew.contains(pacienteCollectionOldPaciente)) {
                    pacienteCollectionOldPaciente.setPersonaId(null);
                    pacienteCollectionOldPaciente = em.merge(pacienteCollectionOldPaciente);
                }
            }
            for (Paciente pacienteCollectionNewPaciente : pacienteCollectionNew) {
                if (!pacienteCollectionOld.contains(pacienteCollectionNewPaciente)) {
                    Persona oldPersonaIdOfPacienteCollectionNewPaciente = pacienteCollectionNewPaciente.getPersonaId();
                    pacienteCollectionNewPaciente.setPersonaId(persona);
                    pacienteCollectionNewPaciente = em.merge(pacienteCollectionNewPaciente);
                    if (oldPersonaIdOfPacienteCollectionNewPaciente != null && !oldPersonaIdOfPacienteCollectionNewPaciente.equals(persona)) {
                        oldPersonaIdOfPacienteCollectionNewPaciente.getPacienteCollection().remove(pacienteCollectionNewPaciente);
                        oldPersonaIdOfPacienteCollectionNewPaciente = em.merge(oldPersonaIdOfPacienteCollectionNewPaciente);
                    }
                }
            }
            for (Secretaria secretariaCollectionOldSecretaria : secretariaCollectionOld) {
                if (!secretariaCollectionNew.contains(secretariaCollectionOldSecretaria)) {
                    secretariaCollectionOldSecretaria.setPersonaId(null);
                    secretariaCollectionOldSecretaria = em.merge(secretariaCollectionOldSecretaria);
                }
            }
            for (Secretaria secretariaCollectionNewSecretaria : secretariaCollectionNew) {
                if (!secretariaCollectionOld.contains(secretariaCollectionNewSecretaria)) {
                    Persona oldPersonaIdOfSecretariaCollectionNewSecretaria = secretariaCollectionNewSecretaria.getPersonaId();
                    secretariaCollectionNewSecretaria.setPersonaId(persona);
                    secretariaCollectionNewSecretaria = em.merge(secretariaCollectionNewSecretaria);
                    if (oldPersonaIdOfSecretariaCollectionNewSecretaria != null && !oldPersonaIdOfSecretariaCollectionNewSecretaria.equals(persona)) {
                        oldPersonaIdOfSecretariaCollectionNewSecretaria.getSecretariaCollection().remove(secretariaCollectionNewSecretaria);
                        oldPersonaIdOfSecretariaCollectionNewSecretaria = em.merge(oldPersonaIdOfSecretariaCollectionNewSecretaria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = persona.getId();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            Collection<Doctor> doctorCollection = persona.getDoctorCollection();
            for (Doctor doctorCollectionDoctor : doctorCollection) {
                doctorCollectionDoctor.setPersonaId(null);
                doctorCollectionDoctor = em.merge(doctorCollectionDoctor);
            }
            Collection<Administrador> administradorCollection = persona.getAdministradorCollection();
            for (Administrador administradorCollectionAdministrador : administradorCollection) {
                administradorCollectionAdministrador.setPersonaId(null);
                administradorCollectionAdministrador = em.merge(administradorCollectionAdministrador);
            }
            Collection<Paciente> pacienteCollection = persona.getPacienteCollection();
            for (Paciente pacienteCollectionPaciente : pacienteCollection) {
                pacienteCollectionPaciente.setPersonaId(null);
                pacienteCollectionPaciente = em.merge(pacienteCollectionPaciente);
            }
            Collection<Secretaria> secretariaCollection = persona.getSecretariaCollection();
            for (Secretaria secretariaCollectionSecretaria : secretariaCollection) {
                secretariaCollectionSecretaria.setPersonaId(null);
                secretariaCollectionSecretaria = em.merge(secretariaCollectionSecretaria);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
