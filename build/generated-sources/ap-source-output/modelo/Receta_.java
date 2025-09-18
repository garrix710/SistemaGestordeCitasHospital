package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Consulta;
import modelo.Doctor;
import modelo.Paciente;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-09-17T23:37:26")
@StaticMetamodel(Receta.class)
public class Receta_ { 

    public static volatile SingularAttribute<Receta, Date> fecha;
    public static volatile SingularAttribute<Receta, String> signosvitales;
    public static volatile SingularAttribute<Receta, String> parametrosantropometricos;
    public static volatile SingularAttribute<Receta, String> indicaciones;
    public static volatile SingularAttribute<Receta, Doctor> doctorId;
    public static volatile SingularAttribute<Receta, Paciente> pacienteId;
    public static volatile SingularAttribute<Receta, Integer> idreceta;
    public static volatile SingularAttribute<Receta, String> medicamentos;
    public static volatile SingularAttribute<Receta, Consulta> consultaId;

}