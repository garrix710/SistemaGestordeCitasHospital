/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vistas;

import Tablas_Model.AdmDatos;
import Tablas_Model.DatosTablaHorario;
import controlador.CitaJpaController;
import controlador.DoctorJpaController;
import controlador.PacienteJpaController;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.Cita;
import modelo.Doctor;
import modelo.Paciente;
import modelo.Persona;

/**
 *
 * @author abiga
 */
public class rCita extends javax.swing.JPanel {

    private AdmDatos adm; 
    
    private List<Paciente> pacientes;
    private PacienteJpaController cPaciente;

    private List<Doctor> doctores;
    private DoctorJpaController cDoctor;

    private List<Cita> citas;
    private CitaJpaController cCita;
    
    /**
     * Creates new form rCita
     */
    public rCita() {
        initComponents();
        adm = new AdmDatos();

        cPaciente = new PacienteJpaController(adm.getEmf());
        pacientes = cPaciente.findPacienteEntities();

        cDoctor = new DoctorJpaController(adm.getEmf());
        doctores = cDoctor.findDoctorEntities();

        cCita = new CitaJpaController(adm.getEmf());
        citas = cCita.findCitaEntities();

        lPaciente.setEnabled(true);
        lDoctor.setEnabled(true);
        txtMotivo.setEnabled(true);

        DatosTablaHorario model = new DatosTablaHorario();
        tableHORARIOS.setModel(model);

        configurarRenderersTabla();
        cargarPacientesDisponibles();
        cargarDoctoresDisponibles();
        generarHorarios();
    }
    
    private void cargarPacientesDisponibles() {
        lPaciente.removeAllItems();
        lPaciente.addItem("Selecciona un paciente");

        for (Paciente p : pacientes) {
            boolean tieneCitaActiva = false;
            for (Cita c : citas) {
                if (c.getPacienteId() != null && c.getPacienteId().equals(p) &&
                        (c.getEstado().equals("0") || c.getEstado().equals("1"))) {
                    tieneCitaActiva = true;
                    break;
                }
            }
            if (!tieneCitaActiva) {
                Persona per = p.getPersonaId();
                if (per != null) {
                    lPaciente.addItem(per.getNombre() + " " + per.getApellido());
                }
            }
        }
    }
    
    private void cargarDoctoresDisponibles() {
        lDoctor.removeAllItems();
        lDoctor.addItem("Selecciona un doctor");

        for (Doctor d : doctores) {
            boolean tieneCitaActiva = false;
            for (Cita c : citas) {
                if (c.getDoctorId() != null && c.getDoctorId().equals(d) &&
                        (c.getEstado().equals("0") || c.getEstado().equals("1"))) {
                    tieneCitaActiva = true;
                    break;
                }
            }
            if (!tieneCitaActiva) {
                Persona per = d.getPersonaId();
                if (per != null) {
                    lDoctor.addItem(per.getNombre() + " " + per.getApellido());
                }
            }
        }
    }
    
    private void generarHorarios() {
        DatosTablaHorario modelo = (DatosTablaHorario) tableHORARIOS.getModel();
        modelo.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

        Calendar hoy = Calendar.getInstance();
        Calendar fin = Calendar.getInstance();
        fin.add(Calendar.DAY_OF_MONTH, 30); // próximos 30 días

        while (!hoy.after(fin)) {
            int diaSemana = hoy.get(Calendar.DAY_OF_WEEK);
            if (diaSemana != Calendar.SATURDAY && diaSemana != Calendar.SUNDAY) {
                for (int h = 8; h <= 20; h++) {
                    Calendar now = Calendar.getInstance();
                    if (hoy.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
                        hoy.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR) &&
                        h < now.get(Calendar.HOUR_OF_DAY)) {
                        continue; // no mostrar horas pasadas
                    }

                    Date fecha = hoy.getTime();
                    String fechaFormateada = sdf.format(fecha);
                    String estado = "Disponible";
                    boolean habilitado = true;

                    for (Cita citaExistente : citas) {
                        if (citaExistente.getFecha() != null &&
                            mismaFecha(citaExistente.getFecha(), fecha) &&
                            citaExistente.getHora() != null) {

                            int horaCita = citaExistente.getHora().getHours();
                            if (horaCita == h && (citaExistente.getEstado().equals("0") || citaExistente.getEstado().equals("1"))) {
                                estado = citaExistente.getEstado().equals("0") ? "Pendiente" : "Activa";
                                habilitado = false;
                                break;
                            }
                        }
                    }

                    modelo.addRow(new Object[]{
                        fechaFormateada,
                        diaSemanaNombre(diaSemana),
                        h + ":00",
                        estado,
                        false,
                        habilitado
                    });
                }
            }
            hoy.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
    
    private String diaSemanaNombre(int diaSemana) {
        switch (diaSemana) {
            case Calendar.MONDAY: return "Lunes";
            case Calendar.TUESDAY: return "Martes";
            case Calendar.WEDNESDAY: return "Miércoles";
            case Calendar.THURSDAY: return "Jueves";
            case Calendar.FRIDAY: return "Viernes";
            default: return "";
        }
    }
    
    private boolean mismaFecha(Date fecha1, Date fecha2) {
        if (fecha1 == null || fecha2 == null) return false;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(fecha1);
        cal2.setTime(fecha2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
               cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
    
    public void registrarCita() {
        int filaSeleccionada = -1;
        for (int i = 0; i < tableHORARIOS.getRowCount(); i++) {
            boolean seleccionado = (boolean) tableHORARIOS.getValueAt(i, 4);
            if (seleccionado) {
                filaSeleccionada = i;
                break;
            }
        }

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un horario para registrar la cita.");
            return;
        }

        String fechaStr = (String) tableHORARIOS.getValueAt(filaSeleccionada, 0);
        String horaStr = (String) tableHORARIOS.getValueAt(filaSeleccionada, 2);
        String motivo = txtMotivo.getText().trim();

        if (motivo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe el motivo de la cita.");
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
            Date fecha = sdf.parse(fechaStr);

            String[] horaPartes = horaStr.split(":");
            int horaInt = Integer.parseInt(horaPartes[0]);
            java.sql.Time horaSQL = java.sql.Time.valueOf(String.format("%02d:00:00", horaInt));

            int indexPaciente = lPaciente.getSelectedIndex();
            if (indexPaciente <= 0) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar un paciente válido.");
                return;
            }
            Paciente pacienteSeleccionado = pacientes.get(indexPaciente - 1);

            int indexDoctor = lDoctor.getSelectedIndex();
            if (indexDoctor <= 0) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar un doctor válido.");
                return;
            }
            Doctor doctorSeleccionado = doctores.get(indexDoctor - 1);

            Cita citaNueva = new Cita();
            citaNueva.setFecha(fecha);
            citaNueva.setHora(horaSQL);
            citaNueva.setMotivo(motivo);
            citaNueva.setEstado("Programada"); // Pendiente
            citaNueva.setPacienteId(pacienteSeleccionado);
            citaNueva.setDoctorId(doctorSeleccionado);

            cCita.create(citaNueva);

            JOptionPane.showMessageDialog(this, "Cita registrada exitosamente.");

            // Refrescar datos
            citas = cCita.findCitaEntities();
            generarHorarios();
            cargarPacientesDisponibles();
            cargarDoctoresDisponibles();
            txtMotivo.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al registrar la cita: " + e.getMessage());
        }
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lPaciente = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableHORARIOS = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtMotivo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lDoctor = new javax.swing.JComboBox<>();

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 3, 18)); // NOI18N
        jLabel1.setText("Registrar Cita");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Paciente");

        lPaciente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        lPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lPacienteActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Horarios disponibles");

        tableHORARIOS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Dia", "Hora", "Estado", "Seleccionar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableHORARIOS);

        jButton1.setText("Registrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Motivo:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Doctor");

        lDoctor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        lDoctor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lDoctorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(283, 283, 283)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(265, 265, 265)
                        .addComponent(jLabel1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(72, 72, 72)
                            .addComponent(jLabel2)
                            .addGap(18, 18, 18)
                            .addComponent(lPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6)
                            .addGap(18, 18, 18)
                            .addComponent(lDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(33, 33, 33)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(46, 46, 46)
                                    .addComponent(txtMotivo))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(256, 256, 256)
                        .addComponent(jLabel3)))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(lDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jButton1)
                .addContainerGap(56, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lPacienteActionPerformed

    }//GEN-LAST:event_lPacienteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        registrarCita();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void lDoctorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lDoctorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lDoctorActionPerformed
    
    private void configurarRenderersTabla() {
        tableHORARIOS.setSelectionBackground(tableHORARIOS.getBackground());
        tableHORARIOS.setSelectionForeground(tableHORARIOS.getForeground());

        tableHORARIOS.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, false, false, row, column);
                boolean estaSeleccionado = (Boolean) table.getModel().getValueAt(row, 4);
                if (estaSeleccionado) {
                    c.setBackground(new Color(173, 216, 230));
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else {
                    c.setBackground(table.getBackground());
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                }
                return c;
            }
        });

        tableHORARIOS.setDefaultRenderer(Boolean.class, new DefaultTableCellRenderer() {
            private final JCheckBox checkBox = new JCheckBox();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                checkBox.setSelected(Boolean.TRUE.equals(value));
                checkBox.setEnabled(table.getModel().isCellEditable(row, column));
                checkBox.setHorizontalAlignment(SwingConstants.CENTER);
                boolean estaSeleccionado = (Boolean) table.getModel().getValueAt(row, 4);
                if (estaSeleccionado) {
                    checkBox.setBackground(new Color(173, 216, 230));
                    checkBox.setFont(checkBox.getFont().deriveFont(Font.BOLD));
                } else {
                    checkBox.setBackground(table.getBackground());
                    checkBox.setFont(checkBox.getFont().deriveFont(Font.PLAIN));
                }
                return checkBox;
            }
        });

        tableHORARIOS.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = tableHORARIOS.rowAtPoint(e.getPoint());
                int col = tableHORARIOS.columnAtPoint(e.getPoint());
                if (col == 4) {
                    tableHORARIOS.repaint();
                }
            }
        });
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> lDoctor;
    private javax.swing.JComboBox<String> lPaciente;
    private javax.swing.JTable tableHORARIOS;
    private javax.swing.JTextField txtMotivo;
    // End of variables declaration//GEN-END:variables
}
