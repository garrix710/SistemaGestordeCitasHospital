package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Cita;
import modelo.Paciente;
import modelo.Secretaria;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-09-17T23:37:26")
@StaticMetamodel(Expediente.class)
public class Expediente_ { 

    public static volatile CollectionAttribute<Expediente, Cita> citaCollection;
    public static volatile SingularAttribute<Expediente, Date> fechacreacion;
    public static volatile SingularAttribute<Expediente, Integer> idexpediente;
    public static volatile SingularAttribute<Expediente, Secretaria> secretariaId;
    public static volatile SingularAttribute<Expediente, Paciente> pacienteId;

}