/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author abiga
 */
package Tablas_Model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Paciente;

public class MTPaciente extends AbstractTableModel {
    
    private List<Paciente> lp;
    private String[] encabezado = {"ID", "Persona ID", "NSS", "Tipo de Sangre", "Alergias"};
    
    public MTPaciente(List<Paciente> pacientes) {
        this.lp = pacientes;
    }

    @Override
    public int getRowCount() {
        return (lp != null) ? lp.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return encabezado.length;
    }
    
    @Override
    public String getColumnName(int c) {
        return encabezado[c];
    }

    @Override
    public Object getValueAt(int r, int c) {
        Paciente p = lp.get(r);
        switch (c) {
            case 0: return p.getId();
            case 1: return p.getPersonaId();
            case 2: return p.getNumerosegurosocial();
            case 3: return p.getTiposangre();
            case 4: return p.getAlergias();
            default: return null;
        }
    }
}

