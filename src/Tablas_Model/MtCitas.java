/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author abiga
 */
package Tablas_Model;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Cita;

public class MtCitas extends AbstractTableModel {

    private List<Cita> citas;
    private String[] columnas = {
        "ID", "Fecha", "Hora", "Estado", "Motivo", "Paciente", "Doctor", "Secretaria"
    };

    public MtCitas(List<Cita> citas) {
        this.citas = citas;
    }

    @Override
    public int getRowCount() {
        return (citas != null) ? citas.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cita c = citas.get(rowIndex);
        SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");

        switch (columnIndex) {
            case 0:
                return c.getIdcita();
            case 1:
                return (c.getFecha() != null) ? sdfFecha.format(c.getFecha()) : "";
            case 2:
                return (c.getHora() != null) ? sdfHora.format(c.getHora()) : "";
            case 3:
                return traducirEstado(c.getEstado());
            case 4:
                return c.getMotivo();
            case 5:
                return (c.getPacienteId() != null) ? c.getPacienteId().getPersonaId().getNombre() : "";
            case 6:
                return (c.getDoctorId() != null) ? c.getDoctorId().getPersonaId().getNombre() : "";
            case 7:
                return (c.getSecretariaId() != null) ? c.getSecretariaId().getPersonaId().getNombre() : "";
            default:
                return null;
        }
    }

    private String traducirEstado(String estado) {
        if (estado == null) return "Desconocido";
        switch (estado) {
            case "0": return "Programada";
            case "1": return "Activa";
            case "2": return "Cancelada";
            case "3": return "Realizada";
            default: return estado;
        }
    }

    public void setLista(List<Cita> nuevas) {
        this.citas = nuevas;
        fireTableDataChanged();
    }

    public Cita getCitaAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < citas.size()) {
            return citas.get(rowIndex);
        }
        return null;
    }
}
