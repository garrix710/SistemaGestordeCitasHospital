package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Cita;
import modelo.Expediente;
import modelo.Persona;
import modelo.Receta;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-09-17T23:37:26")
@StaticMetamodel(Paciente.class)
public class Paciente_ { 

    public static volatile CollectionAttribute<Paciente, Cita> citaCollection;
    public static volatile CollectionAttribute<Paciente, Expediente> expedienteCollection;
    public static volatile SingularAttribute<Paciente, String> numerosegurosocial;
    public static volatile SingularAttribute<Paciente, String> tiposangre;
    public static volatile CollectionAttribute<Paciente, Receta> recetaCollection;
    public static volatile SingularAttribute<Paciente, Integer> id;
    public static volatile SingularAttribute<Paciente, Persona> personaId;
    public static volatile SingularAttribute<Paciente, String> alergias;

}