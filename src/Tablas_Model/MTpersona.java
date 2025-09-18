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
import modelo.Persona;



/**
 *
 * @author
 */
public class MTpersona extends AbstractTableModel {
    private List<Persona> lp;
    private String encabezado[] = {"ID", "Nombre", "Apellido", "Teléfono", "Email", "Fecha Nacimiento", "Dirección"};

    public MTpersona(List<Persona> personas) {
        lp = personas;
    }

    @Override
    public int getRowCount() {
        if (lp != null) {
            return lp.size();
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
        Persona p = lp.get(r);
        switch (c) {
            case 0: return p.getId();
            case 1: return p.getNombre();
            case 2: return p.getApellido();
            case 3: return p.getTelefono();
            case 4: return p.getEmail();
            case 5: return p.getFechanacimiento();
            case 6: return p.getDireccion();
            default: return null;
        }
    }
    
    public void setLista(List<Persona> nuevasPersonas) {
        this.lp = nuevasPersonas;
        fireTableDataChanged(); // Notifica a la tabla que los datos cambiaron
    }
}

