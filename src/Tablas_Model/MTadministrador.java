/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tablas_Model;

/**
 *
 * @author abiga
 */


import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Administrador;

public class MTadministrador extends AbstractTableModel {
    
    private List<Administrador> la; // lista de administradores
    private String[] encabezado = {"ID", "Usuario", "Contraseña", "Persona ID"};
    
    public MTadministrador(List<Administrador> administradores) {
        this.la = administradores;
    }

    @Override
    public int getRowCount() {
        if (la != null) {
            return la.size();
        }
        return 0;
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
        Administrador a = la.get(r);
        switch (c) {
            case 0: return a.getId();
            case 1: return a.getUsuario();
            case 2: return a.getContrasena();
            case 3: return a.getPersonaId();
            default: return null;
        }
    }
}

