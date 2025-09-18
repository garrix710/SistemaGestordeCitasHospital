package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Persona;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-09-17T23:37:26")
@StaticMetamodel(Administrador.class)
public class Administrador_ { 

    public static volatile SingularAttribute<Administrador, String> usuario;
    public static volatile SingularAttribute<Administrador, String> contrasena;
    public static volatile SingularAttribute<Administrador, Integer> id;
    public static volatile SingularAttribute<Administrador, Persona> personaId;

}