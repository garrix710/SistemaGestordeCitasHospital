package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Administrador;
import modelo.Doctor;
import modelo.Paciente;
import modelo.Secretaria;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-09-17T23:37:26")
@StaticMetamodel(Persona.class)
public class Persona_ { 

    public static volatile SingularAttribute<Persona, Date> fechanacimiento;
    public static volatile CollectionAttribute<Persona, Administrador> administradorCollection;
    public static volatile CollectionAttribute<Persona, Secretaria> secretariaCollection;
    public static volatile SingularAttribute<Persona, String> apellido;
    public static volatile SingularAttribute<Persona, String> direccion;
    public static volatile CollectionAttribute<Persona, Doctor> doctorCollection;
    public static volatile CollectionAttribute<Persona, Paciente> pacienteCollection;
    public static volatile SingularAttribute<Persona, Integer> id;
    public static volatile SingularAttribute<Persona, String> telefono;
    public static volatile SingularAttribute<Persona, String> nombre;
    public static volatile SingularAttribute<Persona, String> email;

}