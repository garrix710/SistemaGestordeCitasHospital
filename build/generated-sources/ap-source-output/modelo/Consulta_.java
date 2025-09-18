package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Cita;
import modelo.Receta;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-09-17T23:37:26")
@StaticMetamodel(Consulta.class)
public class Consulta_ { 

    public static volatile SingularAttribute<Consulta, Integer> idconsulta;
    public static volatile SingularAttribute<Consulta, Cita> citaId;
    public static volatile SingularAttribute<Consulta, String> diagnostico;
    public static volatile SingularAttribute<Consulta, String> observaciones;
    public static volatile SingularAttribute<Consulta, Date> fechaconsulta;
    public static volatile CollectionAttribute<Consulta, Receta> recetaCollection;
    public static volatile SingularAttribute<Consulta, String> sintomas;
    public static volatile SingularAttribute<Consulta, String> tratamiento;

}