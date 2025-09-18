package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Cita;
import modelo.Expediente;
import modelo.Persona;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-09-17T23:37:26")
@StaticMetamodel(Secretaria.class)
public class Secretaria_ { 

    public static volatile CollectionAttribute<Secretaria, Cita> citaCollection;
    public static volatile CollectionAttribute<Secretaria, Expediente> expedienteCollection;
    public static volatile SingularAttribute<Secretaria, String> usuario;
    public static volatile SingularAttribute<Secretaria, String> contrasena;
    public static volatile SingularAttribute<Secretaria, Integer> id;
    public static volatile SingularAttribute<Secretaria, Persona> personaId;
    public static volatile SingularAttribute<Secretaria, String> idempleado;

}