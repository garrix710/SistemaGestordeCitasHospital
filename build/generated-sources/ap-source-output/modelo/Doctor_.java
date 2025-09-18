package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Cita;
import modelo.Persona;
import modelo.Receta;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-09-17T23:37:26")
@StaticMetamodel(Doctor.class)
public class Doctor_ { 

    public static volatile CollectionAttribute<Doctor, Cita> citaCollection;
    public static volatile SingularAttribute<Doctor, String> usuario;
    public static volatile SingularAttribute<Doctor, String> contrasena;
    public static volatile CollectionAttribute<Doctor, Receta> recetaCollection;
    public static volatile SingularAttribute<Doctor, Integer> id;
    public static volatile SingularAttribute<Doctor, String> especialidad;
    public static volatile SingularAttribute<Doctor, Persona> personaId;
    public static volatile SingularAttribute<Doctor, String> licenciamedica;

}