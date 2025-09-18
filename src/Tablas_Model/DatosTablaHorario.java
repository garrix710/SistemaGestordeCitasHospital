/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tablas_Model;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author abiga
 */

public class DatosTablaHorario extends DefaultTableModel {

    private final Class[] columnTypes = new Class[]{
        Object.class, // Fecha
        Object.class, // Día
        Object.class, // Hora
        Object.class, // Estado
        Boolean.class // Seleccionar
    };

    public DatosTablaHorario() {
        super(new String[]{"Fecha", "Día", "Hora", "Estado", "Seleccionar"}, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnTypes[columnIndex];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        // Solo permitir editar la columna "Seleccionar" si el estado es "Disponible"
        if (column == 4) {
            Object estado = getValueAt(row, 3);
            return estado != null && "Disponible".equalsIgnoreCase(estado.toString());
        }
        return false;
    }
}

