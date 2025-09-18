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
import modelo.Secretaria;

public class MTSecretaria extends AbstractTableModel {
    
    private List<Secretaria> ls;
    private String[] encabezado = {"ID", "Persona ID", "ID Empleado", "Usuario", "Contraseña"};
    
    public MTSecretaria(List<Secretaria> secretarias) {
        this.ls = secretarias;
    }

    @Override
    public int getRowCount() {
        return (ls != null) ? ls.size() : 0;
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
        Secretaria s = ls.get(r);
        switch (c) {
            case 0: return s.getId();
            case 1: return s.getPersonaId();
            case 2: return s.getIdempleado();
            case 3: return s.getUsuario();
            case 4: return s.getContrasena();
            default: return null;
        }
    }
}

