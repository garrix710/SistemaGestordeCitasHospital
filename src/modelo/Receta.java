/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author abiga
 */
@Entity
@Table(name = "receta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Receta.findAll", query = "SELECT r FROM Receta r")
    , @NamedQuery(name = "Receta.findByIdreceta", query = "SELECT r FROM Receta r WHERE r.idreceta = :idreceta")
    , @NamedQuery(name = "Receta.findByFecha", query = "SELECT r FROM Receta r WHERE r.fecha = :fecha")
    , @NamedQuery(name = "Receta.findBySignosvitales", query = "SELECT r FROM Receta r WHERE r.signosvitales = :signosvitales")
    , @NamedQuery(name = "Receta.findByParametrosantropometricos", query = "SELECT r FROM Receta r WHERE r.parametrosantropometricos = :parametrosantropometricos")
    , @NamedQuery(name = "Receta.findByMedicamentos", query = "SELECT r FROM Receta r WHERE r.medicamentos = :medicamentos")
    , @NamedQuery(name = "Receta.findByIndicaciones", query = "SELECT r FROM Receta r WHERE r.indicaciones = :indicaciones")})
public class Receta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idreceta")
    private Integer idreceta;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "signosvitales")
    private String signosvitales;
    @Column(name = "parametrosantropometricos")
    private String parametrosantropometricos;
    @Column(name = "medicamentos")
    private String medicamentos;
    @Column(name = "indicaciones")
    private String indicaciones;
    @JoinColumn(name = "consulta_id", referencedColumnName = "idconsulta")
    @ManyToOne
    private Consulta consultaId;
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @ManyToOne
    private Doctor doctorId;
    @JoinColumn(name = "paciente_id", referencedColumnName = "id")
    @ManyToOne
    private Paciente pacienteId;

    public Receta() {
    }

    public Receta(Integer idreceta) {
        this.idreceta = idreceta;
    }

    public Integer getIdreceta() {
        return idreceta;
    }

    public void setIdreceta(Integer idreceta) {
        this.idreceta = idreceta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSignosvitales() {
        return signosvitales;
    }

    public void setSignosvitales(String signosvitales) {
        this.signosvitales = signosvitales;
    }

    public String getParametrosantropometricos() {
        return parametrosantropometricos;
    }

    public void setParametrosantropometricos(String parametrosantropometricos) {
        this.parametrosantropometricos = parametrosantropometricos;
    }

    public String getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public Consulta getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(Consulta consultaId) {
        this.consultaId = consultaId;
    }

    public Doctor getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Doctor doctorId) {
        this.doctorId = doctorId;
    }

    public Paciente getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Paciente pacienteId) {
        this.pacienteId = pacienteId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idreceta != null ? idreceta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Receta)) {
            return false;
        }
        Receta other = (Receta) object;
        if ((this.idreceta == null && other.idreceta != null) || (this.idreceta != null && !this.idreceta.equals(other.idreceta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Receta[ idreceta=" + idreceta + " ]";
    }
    
}
