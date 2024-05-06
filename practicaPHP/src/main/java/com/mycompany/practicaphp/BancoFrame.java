/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.practicaphp;

import java.util.ArrayList;
import java.util.List;import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class BancoFrame extends javax.swing.JFrame {

    /**
     * Creates new form BancoFrame
     */
    
    private TransaccionesApi apiConsumer;
    private DefaultTableModel modelTable;
    private final String [] titlesTable = new String[]{"Id","Movimiento","Monto","Fecha","Cuenta"};
    
    public BancoFrame() {
        
        initComponents();
        this.apiConsumer = new TransaccionesApi();
        this.modelTable = new DefaultTableModel(titlesTable,0);
        jtblTransacciones.setModel(modelTable);
        cargarDatos();
        
        jtblTransacciones.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = jtblTransacciones.getSelectedRow();
                if (row == -1) {
                    return;
                }
                jtxtId.setText(jtblTransacciones.getValueAt(row, 0).toString());
                jtxtMovimiento.setText(jtblTransacciones.getValueAt(row, 1).toString());
                jtxtMonto.setText(jtblTransacciones.getValueAt(row, 2).toString());
                jtxtFecha.setText(jtblTransacciones.getValueAt(row, 3).toString());
                jtxtCuenta.setText(jtblTransacciones.getValueAt(row, 4).toString());
                
            }
        });
        
    }
    
    private void cargarDatos(){
        this.modelTable.setNumRows(0);
        List<Transacciones> transacciones = apiConsumer.getAll();
        for (Transacciones transaccion : transacciones) {
            this.modelTable.addRow(new Object[]{transaccion.getId_tra(), transaccion.getMov_tra(), transaccion.getMon_tra(), transaccion.getFec_tra(), transaccion.getId_cue_per()});
            
        }
    }
    
    private void nuevo(){
        String movimiento = jtxtMovimiento.getText();
        String monto = jtxtMonto.getText();
        String fecha = jtxtFecha.getText();
        String cuenta = jtxtCuenta.getText();
        
        Transacciones transacciones = new Transacciones(cuenta, movimiento, monto, fecha, cuenta);
        if (apiConsumer.create(transacciones)) {
            cargarDatos();
            limpiarCampos();
            JOptionPane.showMessageDialog(rootPane, "Creado correctamente");
        } else {
            JOptionPane.showMessageDialog(rootPane, "Error al crear", "Error", JOptionPane.ERROR_MESSAGE, null);
        }
    }
    
    private void limpiarCampos(){
        jtxtCuenta.setText("");
        jtxtFecha.setText("");
        jtxtMonto.setText("");
        jtxtMovimiento.setText("");
    }
    
   private void editar() {
        int row = jtblTransacciones.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione un estudiante", "Error", JOptionPane.ERROR_MESSAGE, null);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(rootPane, "¿Está seguro de editar el estudiante?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.NO_OPTION) {
            return;
        }

        String id = jtblTransacciones.getValueAt(row, 0).toString();
        String movimiento = jtxtMovimiento.getText();
        String monto = jtxtMonto.getText();
        String fecha = jtxtFecha.getText();
        String cuenta = jtxtCuenta.getText();

     
        Transacciones transacciones = new Transacciones(id, movimiento, monto, fecha, cuenta);

        if (apiConsumer.update(transacciones)) {
            cargarDatos();
            limpiarCampos();
            JOptionPane.showMessageDialog(rootPane, "Transaccion actualizada correctamente");
        } else {
            JOptionPane.showMessageDialog(rootPane, "Error al actualizar la transaccion", "Error", JOptionPane.ERROR_MESSAGE, null);
        }
    }
   
   private void eliminar() {
        int row = jtblTransacciones.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione una transaccion", "Error", JOptionPane.ERROR_MESSAGE, null);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(rootPane, "¿Está seguro de eliminar esta transaccion?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.NO_OPTION) {
            return;
        }
        
        String id_tra = jtblTransacciones.getValueAt(row, 0).toString();
        if (apiConsumer.delete(id_tra)) {
            cargarDatos();
            limpiarCampos();
            JOptionPane.showMessageDialog(rootPane, "Transaccion eliminada correctamente");
        } else {
            JOptionPane.showMessageDialog(rootPane, "Error al eliminar la transaccion", "Error", JOptionPane.ERROR_MESSAGE, null);
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
        jtxtCuenta = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtxtMovimiento = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtxtMonto = new javax.swing.JTextField();
        jbtnAceptar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblTransacciones = new javax.swing.JTable();
        jbtnEditar = new javax.swing.JButton();
        jbtnEliminar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jtxtFecha = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtxtId = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("N. Cuenta:");

        jLabel2.setText("Movimiento:");

        jLabel3.setText("Monto:");

        jbtnAceptar.setText("GUARDAR");
        jbtnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAceptarActionPerformed(evt);
            }
        });

        jtblTransacciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jtblTransacciones);

        jbtnEditar.setText("EDITAR");
        jbtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEditarActionPerformed(evt);
            }
        });

        jbtnEliminar.setText("ELIMINAR");
        jbtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEliminarActionPerformed(evt);
            }
        });

        jLabel4.setText("Fecha:");

        jLabel5.setText("Id:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtnEliminar)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbtnAceptar, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jbtnEditar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(82, 82, 82))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxtCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jtxtMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jtxtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jtxtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtnAceptar)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnEditar)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jbtnEliminar))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAceptarActionPerformed
        nuevo();
    }//GEN-LAST:event_jbtnAceptarActionPerformed

    private void jbtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditarActionPerformed
        editar();        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnEditarActionPerformed

    private void jbtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEliminarActionPerformed
        eliminar();        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnEliminarActionPerformed

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
            java.util.logging.Logger.getLogger(BancoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BancoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BancoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BancoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BancoFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnAceptar;
    private javax.swing.JButton jbtnEditar;
    private javax.swing.JButton jbtnEliminar;
    private javax.swing.JTable jtblTransacciones;
    private javax.swing.JTextField jtxtCuenta;
    private javax.swing.JTextField jtxtFecha;
    private javax.swing.JTextField jtxtId;
    private javax.swing.JTextField jtxtMonto;
    private javax.swing.JTextField jtxtMovimiento;
    // End of variables declaration//GEN-END:variables
}
