/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staf_keuangan;

import farmasi.Crud_local;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumnModel;
import tools.Utilitas;

/**
 *
 * @author jengcool
 */
public class frm_non_cover_bpjs extends javax.swing.JFrame {

    /**
     * Creates new form frm_non_cover_bpjs
     */
    private Crud_local datl;

    public frm_non_cover_bpjs() {
        initComponents();
    }

    public frm_non_cover_bpjs(String nm, String poli) {

        initComponents();
        this.setLocationRelativeTo(null);
        this.lbl_petugas.setText(nm);
        lbl_poli.setText(poli);

        txt_cari_tarif.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                cari_tarif();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                cari_tarif();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                cari_tarif();
            }
        });
        txt_cari.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                carinoncoverbpsj(txt_cari.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                carinoncoverbpsj(txt_cari.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                carinoncoverbpsj(txt_cari.getText());
            }
        });

        carinoncoverbpsj("");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dlg_tarif = new javax.swing.JDialog();
        txt_cari_tarif = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tb_tarif = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lbl_petugas = new javax.swing.JLabel();
        lbl_poli1 = new javax.swing.JLabel();
        lbl_poli = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_kode_tarif = new javax.swing.JTextField();
        bt_cari_tarif = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txt_nm_tarif = new javax.swing.JTextField();
        txt_jml = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        bt_save = new javax.swing.JButton();
        bt_delete = new javax.swing.JButton();
        bt_edit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_non_cover = new javax.swing.JTable();
        txt_cari = new javax.swing.JTextField();

        dlg_tarif.setModal(true);
        dlg_tarif.setSize(new java.awt.Dimension(477, 508));

        tb_tarif.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        tb_tarif.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_tarif.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_tarifMouseReleased(evt);
            }
        });
        tb_tarif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tb_tarifKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tb_tarif);

        javax.swing.GroupLayout dlg_tarifLayout = new javax.swing.GroupLayout(dlg_tarif.getContentPane());
        dlg_tarif.getContentPane().setLayout(dlg_tarifLayout);
        dlg_tarifLayout.setHorizontalGroup(
            dlg_tarifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlg_tarifLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dlg_tarifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_cari_tarif)
                    .addComponent(jScrollPane5))
                .addContainerGap())
        );
        dlg_tarifLayout.setVerticalGroup(
            dlg_tarifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlg_tarifLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(txt_cari_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tarif Non Cover BPJS");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbl_petugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/staf_keuangan/staf_ico.png"))); // NOI18N

        lbl_poli1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/log_out.png"))); // NOI18N
        lbl_poli1.setText("Logout");
        lbl_poli1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_poli1MouseClicked(evt);
            }
        });

        lbl_poli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kasir/kasir_kecil_ico.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_poli)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_petugas, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(lbl_poli1)
                .addGap(49, 49, 49))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_petugas)
                    .addComponent(lbl_poli1)
                    .addComponent(lbl_poli))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Kode Tarif");

        txt_kode_tarif.setEditable(false);

        bt_cari_tarif.setText("...");
        bt_cari_tarif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cari_tarifActionPerformed(evt);
            }
        });

        jLabel3.setText("Nama Tarif");

        txt_nm_tarif.setEditable(false);

        txt_jml.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_jmlKeyTyped(evt);
            }
        });

        jLabel4.setText("Jumlah yg di cover");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        bt_save.setText("Save");
        bt_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_saveActionPerformed(evt);
            }
        });

        bt_delete.setText("Delete");
        bt_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_deleteActionPerformed(evt);
            }
        });

        bt_edit.setText("Edit");
        bt_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_editActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bt_edit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_save, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_save)
                    .addComponent(bt_delete)
                    .addComponent(bt_edit))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tb_non_cover.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        tb_non_cover.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_non_cover.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_non_coverMouseReleased(evt);
            }
        });
        tb_non_cover.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tb_non_coverKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tb_non_cover);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(47, 47, 47)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_kode_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_jml, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_cari_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txt_nm_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(71, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_kode_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_cari_tarif))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_nm_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_jml, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_poli1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_poli1MouseClicked
        // TODO add your handling code here:
        int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah Akan Keluar?", "Warning ",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (dialogResult == JOptionPane.YES_OPTION) {

//            executor.shutdown();
            new frm_login_poli_keu().setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_lbl_poli1MouseClicked

    private void tb_tarifMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_tarifMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = this.tb_tarif.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                this.txt_kode_tarif.setText(tb_tarif.getModel().getValueAt(row, 0).toString());
                txt_nm_tarif.setText(tb_tarif.getModel().getValueAt(row, 1).toString());

            }

            this.dlg_tarif.setVisible(false);
        }
    }//GEN-LAST:event_tb_tarifMouseReleased

    private void tb_tarifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_tarifKeyPressed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int row = this.tb_tarif.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                this.txt_kode_tarif.setText(tb_tarif.getModel().getValueAt(row, 0).toString());
                txt_nm_tarif.setText(tb_tarif.getModel().getValueAt(row, 1).toString());
            }

            this.dlg_tarif.setVisible(false);
        }
    }//GEN-LAST:event_tb_tarifKeyPressed

    private void setukurantb_tarif() {

        this.tb_tarif.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        tb_tarif.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tr = this.tb_tarif.getColumnModel();

        tr.getColumn(0).setPreferredWidth(80);
        tr.getColumn(1).setPreferredWidth(280);
        tr.getColumn(2).setPreferredWidth(100);
        tr.getColumn(3).setPreferredWidth(100);
        tr.getColumn(4).setPreferredWidth(100);
    }
    
    
    private void setukurantb_non_cover() {

        this.tb_non_cover.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        tb_non_cover.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tr = this.tb_non_cover.getColumnModel();

        tr.getColumn(0).setPreferredWidth(80);
        tr.getColumn(1).setPreferredWidth(380);
        tr.getColumn(2).setPreferredWidth(50);
        
    }

    private void cari_tarif() {
        try {
            datl = new Crud_local();
            datl.readRec_cariTarif(this.txt_cari_tarif.getText());
            datl.CloseCon();

            tb_tarif.setModel(datl.modeltarif);

            setukurantb_tarif();

        } catch (Exception ex) {
            Logger.getLogger(frm_non_cover_bpjs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void bt_cari_tarifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_tarifActionPerformed
        // TODO add your handling code here:
        try {

            datl = new Crud_local();
            datl.readRec_cariTarif();
            datl.CloseCon();
            tb_tarif.setModel(datl.modeltarif);

            setukurantb_tarif();

        } catch (Exception ex) {
            Logger.getLogger(frm_non_cover_bpjs.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dlg_tarif.setLocationRelativeTo(this);
        dlg_tarif.setVisible(true);
    }//GEN-LAST:event_bt_cari_tarifActionPerformed

    private void txt_jmlKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jmlKeyTyped
        // TODO add your handling code here:
        char enter = evt.getKeyChar();
        if (!(Character.isDigit(enter))) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_jmlKeyTyped

    private void carinoncoverbpsj(String cr) {
        try {
            datl = new Crud_local();
            datl.readRec_cariNoncoverBPJS(cr);
            datl.CloseCon();

            tb_non_cover.setModel(datl.modelnoncoverbpjs);
            
            setukurantb_non_cover();

        } catch (Exception ex) {
            Logger.getLogger(frm_non_cover_bpjs.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void bt_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_saveActionPerformed
        try {
            // TODO add your handling code here:
            if (!(this.txt_kode_tarif.getText().isEmpty() || this.txt_jml.getText().isEmpty())) {
                datl = new Crud_local();
                datl.Save_non_coverBPJS(this.txt_kode_tarif.getText(), Integer.parseInt(this.txt_jml.getText()), this.lbl_petugas.getText());
                datl.CloseCon();

                Utilitas.HapusText(jPanel2);
                carinoncoverbpsj("");

            } else {
                JOptionPane.showMessageDialog(null, "Inputan Ada yang Kosong!");
            }
        } catch (Exception ex) {
            Logger.getLogger(frm_non_cover_bpjs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_saveActionPerformed

    private void bt_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_editActionPerformed
        // TODO add your handling code here:
        try {
            datl = new Crud_local();
            datl.Update_non_coverbpjs(this.txt_kode_tarif.getText(), Integer.parseInt(this.txt_jml.getText()));
            datl.CloseCon();

            carinoncoverbpsj("");

        } catch (Exception ex) {
            Logger.getLogger(frm_non_cover_bpjs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_editActionPerformed

    private void tb_non_coverMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_non_coverMouseReleased
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = this.tb_non_cover.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                this.txt_kode_tarif.setText(tb_non_cover.getModel().getValueAt(row, 0).toString());
                txt_nm_tarif.setText(tb_non_cover.getModel().getValueAt(row, 1).toString());
                this.txt_jml.setText(tb_non_cover.getModel().getValueAt(row, 2).toString());
            }

        }
    }//GEN-LAST:event_tb_non_coverMouseReleased

    private void tb_non_coverKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_non_coverKeyPressed
        // TODO add your handling code here:

        int row = this.tb_non_cover.getSelectedRow();

        if (row == -1) {
            // No row selected
        } else {
            this.txt_kode_tarif.setText(tb_non_cover.getModel().getValueAt(row, 0).toString());
            txt_nm_tarif.setText(tb_non_cover.getModel().getValueAt(row, 1).toString());
            this.txt_jml.setText(tb_non_cover.getModel().getValueAt(row, 2).toString());
        }


    }//GEN-LAST:event_tb_non_coverKeyPressed

    private void bt_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_deleteActionPerformed
        // TODO add your handling code here:
        int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah Akan Dihapus? "+this.txt_nm_tarif.getText(), "Warning ",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (dialogResult == JOptionPane.YES_OPTION) {
            try {
                datl = new Crud_local();
                datl.DelRecNoncoverBPJS(this.txt_kode_tarif.getText());
                datl.CloseCon();

                carinoncoverbpsj("");

            } catch (Exception ex) {
                Logger.getLogger(frm_non_cover_bpjs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_bt_deleteActionPerformed

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
            java.util.logging.Logger.getLogger(frm_non_cover_bpjs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_non_cover_bpjs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_non_cover_bpjs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_non_cover_bpjs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_non_cover_bpjs().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_cari_tarif;
    private javax.swing.JButton bt_delete;
    private javax.swing.JButton bt_edit;
    private javax.swing.JButton bt_save;
    private javax.swing.JDialog dlg_tarif;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lbl_petugas;
    private javax.swing.JLabel lbl_poli;
    private javax.swing.JLabel lbl_poli1;
    private javax.swing.JTable tb_non_cover;
    private javax.swing.JTable tb_tarif;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_cari_tarif;
    private javax.swing.JTextField txt_jml;
    private javax.swing.JTextField txt_kode_tarif;
    private javax.swing.JTextField txt_nm_tarif;
    // End of variables declaration//GEN-END:variables
}