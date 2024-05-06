/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.quintocrud;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author patri
 */
public class frmEstudiante extends javax.swing.JFrame {

    /**
     * Creates new form frmEstudiante
     */
    private StudentApiConsumer apiConsumer;
    private DefaultTableModel modelTable;
    private final String[] titlesTable = new String[]{"Cedula", "Nombre", "Apellido", "Dirección", "Telefono"};

    public frmEstudiante() {
        initComponents();
        this.apiConsumer = new StudentApiConsumer();
        this.modelTable = new DefaultTableModel(titlesTable, 0);
        jtblEstudiantes.setModel(modelTable);
        cargarDatos();
        
        jtblEstudiantes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = jtblEstudiantes.getSelectedRow();
                if (row == -1) {
                    return;
                }

                jtxtCedula.setText(jtblEstudiantes.getValueAt(row, 0).toString());
                jtxtNombre.setText(jtblEstudiantes.getValueAt(row, 1).toString());
                jtxtApellido.setText(jtblEstudiantes.getValueAt(row, 2).toString());
                jtxtDireccion.setText(jtblEstudiantes.getValueAt(row, 3).toString());
                jtxtTelefono.setText(jtblEstudiantes.getValueAt(row, 4).toString());
                
            }
            
        });
    }

    private void cargarDatos() {
        this.modelTable.setNumRows(0);
        List<Student> students = apiConsumer.getAll();
        for (Student student : students) {
            this.modelTable.addRow(new Object[]{student.getID(), student.getFirstName(), student.getLastName(), student.getAddress(), student.getPhone()});
        }
    }

    private void nuevo() {
        String cedula = jtxtCedula.getText();
        String nombre = jtxtNombre.getText();
        String apellido = jtxtApellido.getText();
        String direccion = jtxtDireccion.getText();
        String telefono = jtxtTelefono.getText();

        ArrayList<String> errores = validarCampos(cedula, nombre, apellido, direccion, telefono);
        if (errores.size() > 0) {
            JOptionPane.showMessageDialog(rootPane, String.join("\n", errores), "Error", JOptionPane.ERROR_MESSAGE, null);
            return;
        }

        if (errores.size() > 0) {
            JOptionPane.showMessageDialog(rootPane, String.join("\n", errores), "Error", JOptionPane.ERROR_MESSAGE, null);
            return;
        }

        Student student = new Student(cedula, nombre, apellido, direccion, telefono);

        if (apiConsumer.create(student)) {
            cargarDatos();
            limpiarCampos();
            JOptionPane.showMessageDialog(rootPane, "Estudiante creado correctamente");
        } else {
            JOptionPane.showMessageDialog(rootPane, "Error al crear el estudiante", "Error", JOptionPane.ERROR_MESSAGE, null);
        }

    }

    private ArrayList<String> validarCampos(String cedula, String nombre, String apellido, String direccion, String telefono) {
        ArrayList<String> errores = new ArrayList<>();
        if (cedula.isEmpty() || cedula.isBlank()) {
            errores.add("La cedula es requerida.");
        }
        if (nombre.isEmpty() || nombre.isBlank()) {
            errores.add("El nombre es requerido.");
        }
        if (apellido.isEmpty() || apellido.isBlank()) {
            errores.add("El apellido es requerido.");
        }
        if (direccion.isEmpty() || direccion.isBlank()) {
            errores.add("La direccion es requerida.");
        }
        if (telefono.isEmpty() || telefono.isBlank()) {
            errores.add("El telefono es requerido.");
        }
        
       
        return errores;
    }
    
//    private boolean ValidarCampos(String... campos){
//        boolean isValid = true;
//        String[] mensajesError={
//            "ERROR EN CEDULA",
//            "ERROR EN NOMBRE",
//            "ERROR EN APELLIDO",
//            "ERROR EN DIRECCION",
//            "ERROR EN TELEFONO"  
//        };
//                
//        JLabel[] labels = {
//        jlblbErrorCedula,
//        jlblbErrorNombre,
//        jlblbErrorApellido,
//        jlblbErrorDireccion,
//        jlblbErrorTelefono
//        };
//        
//        for(int i=0; i<campos.length;i++){
//            if (campos[i].isEmpty()||campos[i].isBlank()){
//                labels[i].setText(mensajesError[i]);
//                isValid = false;
//            }
//        }
//        return true;
//    }

    
    
    private void limpiarCampos() {
        jtxtCedula.setText("");
        jtxtNombre.setText("");
        jtxtApellido.setText("");
        jtxtDireccion.setText("");
        jtxtTelefono.setText("");
    }

    private void editar() {
        int row = jtblEstudiantes.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione un estudiante", "Error", JOptionPane.ERROR_MESSAGE, null);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(rootPane, "¿Está seguro de editar el estudiante?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.NO_OPTION) {
            return;
        }

        String cedula = jtblEstudiantes.getValueAt(row, 0).toString();
        String nombre = jtxtNombre.getText();
        String apellido = jtxtApellido.getText();
        String direccion = jtxtDireccion.getText();
        String telefono = jtxtTelefono.getText();

        ArrayList<String> errores = validarCampos(cedula, nombre, apellido, direccion, telefono);
        if (errores.size() > 0) {
            JOptionPane.showMessageDialog(rootPane, String.join("\n", errores), "Error", JOptionPane.ERROR_MESSAGE, null);
            return;
        }


        Student student = new Student(cedula, nombre, apellido, direccion, telefono);

        if (apiConsumer.update(student)) {
            cargarDatos();
            limpiarCampos();
            JOptionPane.showMessageDialog(rootPane, "Estudiante actualizado correctamente");
        } else {
            JOptionPane.showMessageDialog(rootPane, "Error al actualizar el estudiante", "Error", JOptionPane.ERROR_MESSAGE, null);
        }
    }

    private void eliminar() {
        int row = jtblEstudiantes.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione un estudiante", "Error", JOptionPane.ERROR_MESSAGE, null);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(rootPane, "¿Está seguro de eliminar el estudiante?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.NO_OPTION) {
            return;
        }
        
        String cedula = jtblEstudiantes.getValueAt(row, 0).toString();
        if (apiConsumer.delete(cedula)) {
            cargarDatos();
            limpiarCampos();
            JOptionPane.showMessageDialog(rootPane, "Estudiante eliminado correctamente");
        } else {
            JOptionPane.showMessageDialog(rootPane, "Error al eliminar el estudiante", "Error", JOptionPane.ERROR_MESSAGE, null);
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

        jpnaFormulario = new javax.swing.JPanel();
        jtxtCedula = new javax.swing.JTextField();
        jtxtNombre = new javax.swing.JTextField();
        jtxtApellido = new javax.swing.JTextField();
        jtxtDireccion = new javax.swing.JTextField();
        jtxtTelefono = new javax.swing.JTextField();
        jlblCedula = new javax.swing.JLabel();
        jlblNombre = new javax.swing.JLabel();
        jlblApellido = new javax.swing.JLabel();
        jlblDireccion = new javax.swing.JLabel();
        jlblTelefono = new javax.swing.JLabel();
        jlblTitleForm = new javax.swing.JLabel();
        jbtnNuevo = new javax.swing.JButton();
        jbtnEditar = new javax.swing.JButton();
        jbtnEliminar = new javax.swing.JButton();
        jbtnLimpiar = new javax.swing.JButton();
        jlblbErrorCedula = new javax.swing.JLabel();
        jlblbErrorNombre = new javax.swing.JLabel();
        jlblbErrorApellido = new javax.swing.JLabel();
        jlblbErrorDireccion = new javax.swing.JLabel();
        jlblbErrorTelefono = new javax.swing.JLabel();
        jpanDatos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblEstudiantes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jtxtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtNombreActionPerformed(evt);
            }
        });

        jlblCedula.setText("Cedula:");

        jlblNombre.setText("Nombre:");

        jlblApellido.setText("Apellido:");

        jlblDireccion.setText("Dirección:");

        jlblTelefono.setText("Telefono:");

        jlblTitleForm.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlblTitleForm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblTitleForm.setText("FORMULARIO ESTUDIANTE");

        jbtnNuevo.setBackground(new java.awt.Color(51, 102, 255));
        jbtnNuevo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jbtnNuevo.setText("Nuevo");
        jbtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNuevoActionPerformed(evt);
            }
        });

        jbtnEditar.setBackground(new java.awt.Color(255, 153, 0));
        jbtnEditar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jbtnEditar.setText("Editar");
        jbtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEditarActionPerformed(evt);
            }
        });

        jbtnEliminar.setBackground(new java.awt.Color(255, 51, 51));
        jbtnEliminar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jbtnEliminar.setText("Eliminar");
        jbtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEliminarActionPerformed(evt);
            }
        });

        jbtnLimpiar.setBackground(new java.awt.Color(153, 153, 0));
        jbtnLimpiar.setText("Limpiar");
        jbtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnaFormularioLayout = new javax.swing.GroupLayout(jpnaFormulario);
        jpnaFormulario.setLayout(jpnaFormularioLayout);
        jpnaFormularioLayout.setHorizontalGroup(
            jpnaFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnaFormularioLayout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addGroup(jpnaFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnaFormularioLayout.createSequentialGroup()
                        .addComponent(jbtnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
                    .addGroup(jpnaFormularioLayout.createSequentialGroup()
                        .addGroup(jpnaFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlblCedula, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jlblNombre, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jlblDireccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblTelefono, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jlblApellido, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnaFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtDireccion, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtxtCedula, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpnaFormularioLayout.createSequentialGroup()
                                .addComponent(jlblbErrorTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jtxtNombre, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jlblbErrorCedula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblbErrorApellido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtxtTelefono)
                            .addComponent(jtxtApellido)
                            .addGroup(jpnaFormularioLayout.createSequentialGroup()
                                .addGap(271, 271, 271)
                                .addGroup(jpnaFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlblbErrorNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jlblbErrorDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(130, 130, 130))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnaFormularioLayout.createSequentialGroup()
                .addComponent(jlblTitleForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnaFormularioLayout.setVerticalGroup(
            jpnaFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnaFormularioLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jlblTitleForm)
                .addGap(18, 18, 18)
                .addGroup(jpnaFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlblCedula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblbErrorCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnaFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpnaFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnaFormularioLayout.createSequentialGroup()
                        .addComponent(jlblbErrorNombre)
                        .addGap(2, 2, 2)
                        .addComponent(jlblApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jtxtApellido, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlblbErrorApellido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnaFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlblDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblbErrorDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnaFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpnaFormularioLayout.createSequentialGroup()
                        .addComponent(jlblTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 26, Short.MAX_VALUE))
                    .addGroup(jpnaFormularioLayout.createSequentialGroup()
                        .addComponent(jtxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlblbErrorTelefono)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnaFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpnaFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );

        jtblEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        jScrollPane1.setViewportView(jtblEstudiantes);

        javax.swing.GroupLayout jpanDatosLayout = new javax.swing.GroupLayout(jpanDatos);
        jpanDatos.setLayout(jpanDatosLayout);
        jpanDatosLayout.setHorizontalGroup(
            jpanDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jpanDatosLayout.setVerticalGroup(
            jpanDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpanDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnaFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnaFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpanDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtNombreActionPerformed

    private void jbtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoActionPerformed
        nuevo();
    }//GEN-LAST:event_jbtnNuevoActionPerformed

    private void jbtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_jbtnEliminarActionPerformed

    private void jbtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditarActionPerformed
        editar();
    }//GEN-LAST:event_jbtnEditarActionPerformed

    private void jbtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_jbtnLimpiarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmEstudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmEstudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmEstudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmEstudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        FlatMacDarkLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmEstudiante().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnEditar;
    private javax.swing.JButton jbtnEliminar;
    private javax.swing.JButton jbtnLimpiar;
    private javax.swing.JButton jbtnNuevo;
    private javax.swing.JLabel jlblApellido;
    private javax.swing.JLabel jlblCedula;
    private javax.swing.JLabel jlblDireccion;
    private javax.swing.JLabel jlblNombre;
    private javax.swing.JLabel jlblTelefono;
    private javax.swing.JLabel jlblTitleForm;
    private javax.swing.JLabel jlblbErrorApellido;
    private javax.swing.JLabel jlblbErrorCedula;
    private javax.swing.JLabel jlblbErrorDireccion;
    private javax.swing.JLabel jlblbErrorNombre;
    private javax.swing.JLabel jlblbErrorTelefono;
    private javax.swing.JPanel jpanDatos;
    private javax.swing.JPanel jpnaFormulario;
    private javax.swing.JTable jtblEstudiantes;
    private javax.swing.JTextField jtxtApellido;
    private javax.swing.JTextField jtxtCedula;
    private javax.swing.JTextField jtxtDireccion;
    private javax.swing.JTextField jtxtNombre;
    private javax.swing.JTextField jtxtTelefono;
    // End of variables declaration//GEN-END:variables
}
