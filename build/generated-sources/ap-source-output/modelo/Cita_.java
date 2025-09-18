package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Consulta;
import modelo.Doctor;
import modelo.Expediente;
import modelo.Paciente;
import modelo.Secretaria;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-09-17T23:37:26")
@StaticMetamodel(Cita.class)
public class Cita_ { 

    public static volatile SingularAttribute<Cita, Date> fechahora;
    public static volatile SingularAttribute<Cita, Integer> idcita;
    public static volatile SingularAttribute<Cita, String> estado;
    public static volatile SingularAttribute<Cita, String> motivo;
    public static volatile CollectionAttribute<Cita, Consulta> consultaCollection;
    public static volatile SingularAttribute<Cita, Doctor> doctorId;
    public static volatile SingularAttribute<Cita, Secretaria> secretariaId;
    public static volatile SingularAttribute<Cita, Paciente> pacienteId;
    public static volatile SingularAttribute<Cita, Expediente> expedienteId;

}