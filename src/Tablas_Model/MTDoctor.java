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
import modelo.Doctor;

public class MTDoctor extends AbstractTableModel {
    
    private List<Doctor> ld;
    private String[] encabezado = {"ID", "Persona ID", "Licencia Médica", "Usuario", "Contraseña", "Especialidad"};
    
    public MTDoctor(List<Doctor> doctores) {
        this.ld = doctores;
    }

    @Override
    public int getRowCount() {
        return (ld != null) ? ld.size() : 0;
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
        Doctor d = ld.get(r);
        switch (c) {
            case 0: return d.getId();
            case 1: return d.getPersonaId();
            case 2: return d.getLicenciamedica();
            case 3: return d.getUsuario();
            case 4: return d.getContrasena();
            case 5: return d.getEspecialidad();
            default: return null;
        }
    }
}

