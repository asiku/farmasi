/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarif;

import farmasi.Crud_local;
import farmasi.NewJFrame;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import tools.Utilitas;

/**
 *
 * @author jengcool
 */
public class frm_tarif extends javax.swing.JFrame {

    /**
     * Creates new form frm_tarif
     */
    
     private Crud_local datl;
     
    
     
    public frm_tarif() {
        initComponents();
      
       this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
       txt_tarif.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
              if(txt_tarif.getText().isEmpty()){
              
                   txt_tarif.setText("0");
              
              }else{
                
                cekpersen();
                
              }
            }

            @Override
            public void focusLost(FocusEvent e) {
                
                if(txt_tarif.getText().isEmpty()){
              
                   txt_tarif.setText("0");
              
              }
               else{
               
                cekpersen();
                
                }
            }
        });
       
       txt_sarana.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if(txt_tarif.getText().isEmpty()){
              
                   txt_tarif.setText("0");
                
                }
                
                
              if(txt_sarana.getText().isEmpty()){
              
                   txt_sarana.setText("0");
              
              }else{
                
                cekpersen();
                
              }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(txt_tarif.getText().isEmpty()){
              
                   txt_tarif.setText("0");
                
                }
                if(txt_sarana.getText().isEmpty()){
              
                   txt_sarana.setText("0");
              
              }
               else{
               
                cekpersen();
                
                }
            }
        });
       
       
        txt_dr.addFocusListener(new FocusListener() {

            
            @Override
            public void focusGained(FocusEvent e) {
                if(txt_tarif.getText().isEmpty()){
              
                   txt_tarif.setText("0");
                
                }
                
              if(txt_dr.getText().isEmpty()){
              
                   txt_dr.setText("0");
              
              }else{
                  int i=0;
               System.out.println("DR"+i++);
                  
                cekpersen();
                
              }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(txt_tarif.getText().isEmpty()){
              
                   txt_tarif.setText("0");
                
                }
                if(txt_dr.getText().isEmpty()){
              
                   txt_dr.setText("0");
              
              }
               else{
                
                  
                cekpersen();
                
                }
            }
        }); 
        
         txt_rs.addFocusListener(new FocusListener() {

           @Override
            public void focusGained(FocusEvent e) {
                
                if(txt_tarif.getText().isEmpty()){
              
                   txt_tarif.setText("0");
                
                }
              if(txt_rs.getText().isEmpty()){
              
                   txt_rs.setText("0");
              
              }else{
                
                cekpersen();
                
              }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(txt_tarif.getText().isEmpty()){
              
                   txt_tarif.setText("0");
                
                }
                if(txt_rs.getText().isEmpty()){
              
                   txt_rs.setText("0");
              
              }
               else{
               
                cekpersen();
                
                }
            }
        }); 
        
        txt_sarana.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
              
                    
                
               hitungpresentasDR();
               hitungpresentaseRS();
               hitungpresentaseSR();
               
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                
                
                if(!txt_sarana.getText().isEmpty()){
                       
                    hitungpresentasDR();
                    hitungpresentaseRS();
                    hitungpresentaseSR();
                    
                 
                    
                }
                
              
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                   
               hitungpresentasDR();
               hitungpresentaseRS();
               hitungpresentaseSR();
            }
        }); 
          
         
         
        txt_dr.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                   
               hitungpresentasDR();
               hitungpresentaseRS();
               hitungpresentaseSR();
               
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               if(!txt_dr.getText().isEmpty()){
                   
                    hitungpresentasDR();
                    hitungpresentaseRS();
                    hitungpresentaseSR();
                    
                     
                }
                
              
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                    
               hitungpresentasDR();
               hitungpresentaseRS();
               hitungpresentaseSR();
            }
        }); 
         
         
        txt_rs.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                    
               hitungpresentasDR();
               hitungpresentaseRS();
               hitungpresentaseSR();
               
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                 if(!txt_rs.getText().isEmpty()){
                      
                    hitungpresentasDR();
                    hitungpresentaseRS();
                    hitungpresentaseSR();
                    
                   
                }
               
                
              
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                    
               hitungpresentasDR();
               hitungpresentaseRS();
               hitungpresentaseSR();
            }
        });
         
         
         txt_tarif.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                     if(txt_rs.getText().isEmpty()){
                
                txt_rs.setText("0");
                }
                
                if(txt_dr.getText().isEmpty()){
                
                txt_dr.setText("0");
                }
                
                 if(txt_sarana.getText().isEmpty()){
                
                txt_sarana.setText("0");
                }
               hitungpresentasDR();
               hitungpresentaseRS();
               hitungpresentaseSR();
               
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                
                if(txt_rs.getText().isEmpty()){
                
                txt_rs.setText("0");
                }
                
                if(txt_dr.getText().isEmpty()){
                
                txt_dr.setText("0");
                }
                
                 if(txt_sarana.getText().isEmpty()){
                
                txt_sarana.setText("0");
                }
                
                
                if(!txt_tarif.getText().isEmpty()){
                      
                    hitungpresentasDR();
                    hitungpresentaseRS();
                    hitungpresentaseSR();
                }
                
                
              
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                     if(txt_rs.getText().isEmpty()){
                
                txt_rs.setText("0");
                }
                
                if(txt_dr.getText().isEmpty()){
                
                txt_dr.setText("0");
                }
                
                 if(txt_sarana.getText().isEmpty()){
                
                txt_sarana.setText("0");
                }
               hitungpresentasDR();
               hitungpresentaseRS();
               hitungpresentaseSR();
            }
        });
         
         
         txt_cari_poli.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               filterpoli();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
              filterpoli();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              filterpoli();
            }
        });
         
         
         
         this.txt_kode_tarif.requestFocus(); 
         
         try {
             datl=new Crud_local();
             datl.readRec_cariPoli();
             tb_poli.setModel(datl.modelpoli);
             
         } catch (Exception ex) {
             Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
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

        dlg_cari_poli = new javax.swing.JDialog();
        jScrollPane5 = new javax.swing.JScrollPane();
        tb_poli = new javax.swing.JTable();
        txt_cari_poli = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbl_dokter = new javax.swing.JLabel();
        lbl_sarana = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_rs = new javax.swing.JTextField();
        txt_dr = new javax.swing.JTextField();
        txt_sarana = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_tarif = new javax.swing.JTextField();
        lbl_rs = new javax.swing.JLabel();
        lbl_cepe = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txt_cari_tarif = new javax.swing.JTextField();
        bt_cari_tarif = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        bt_save = new javax.swing.JButton();
        bt_edit = new javax.swing.JButton();
        bt_delete = new javax.swing.JButton();
        bt_cetak = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txt_keterangan = new javax.swing.JTextArea();
        panel_inputan = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lbl_kode_poli = new javax.swing.JLabel();
        txt_poli = new javax.swing.JTextField();
        bt_cari_poli = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cmb_status = new javax.swing.JComboBox<>();
        txt_nama_tarif = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_kode_tarif = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jDateTimePicker1 = new uz.ncipro.calendar.JDateTimePicker();
        bt_pengesah = new javax.swing.JButton();
        bt_cari_verif = new javax.swing.JButton();
        bt_cari_verif1 = new javax.swing.JButton();
        bt_pengesah1 = new javax.swing.JButton();
        jDateTimePicker2 = new uz.ncipro.calendar.JDateTimePicker();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();

        dlg_cari_poli.setModal(true);
        dlg_cari_poli.setSize(new java.awt.Dimension(477, 388));

        tb_poli.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_poli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_poliMouseReleased(evt);
            }
        });
        tb_poli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tb_poliKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tb_poli);

        txt_cari_poli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cari_poliKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout dlg_cari_poliLayout = new javax.swing.GroupLayout(dlg_cari_poli.getContentPane());
        dlg_cari_poli.getContentPane().setLayout(dlg_cari_poliLayout);
        dlg_cari_poliLayout.setHorizontalGroup(
            dlg_cari_poliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dlg_cari_poliLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(dlg_cari_poliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_cari_poli)
                    .addComponent(jScrollPane5))
                .addContainerGap())
        );
        dlg_cari_poliLayout.setVerticalGroup(
            dlg_cari_poliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dlg_cari_poliLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(txt_cari_poli, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Set Presentase Tarif"));

        jLabel13.setText("RS.   %");

        jLabel14.setText("Dokter %");

        lbl_dokter.setText("Rp.");

        lbl_sarana.setText("Rp.");

        jLabel17.setText("Sarana %");

        txt_rs.setText("0");
        txt_rs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_rsKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_rsKeyPressed(evt);
            }
        });

        txt_dr.setText("0");
        txt_dr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_drKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_drKeyPressed(evt);
            }
        });

        txt_sarana.setText("0");
        txt_sarana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_saranaKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_saranaKeyPressed(evt);
            }
        });

        jLabel3.setText("Tarif Tindakan                         Rp.");

        txt_tarif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_tarifKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_tarifKeyPressed(evt);
            }
        });

        lbl_rs.setText("Rp.");

        lbl_cepe.setText("              %");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(txt_rs, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(lbl_rs, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(txt_dr, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(lbl_dokter, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_cepe, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                            .addComponent(txt_sarana))
                        .addGap(21, 21, 21)
                        .addComponent(lbl_sarana, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_tarif)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(txt_rs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_rs, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(txt_dr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_dokter, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(txt_sarana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_sarana, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_cepe))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        bt_cari_tarif.setText("Cari");

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        bt_save.setText("Save");
        bt_save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bt_saveMouseEntered(evt);
            }
        });
        bt_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_saveActionPerformed(evt);
            }
        });

        bt_edit.setText("Edit");

        bt_delete.setText("Delete");

        bt_cetak.setText("Cetak");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bt_save, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bt_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_save)
                    .addComponent(bt_edit)
                    .addComponent(bt_delete)
                    .addComponent(bt_cetak))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel10.setText("Keterangan");

        txt_keterangan.setColumns(20);
        txt_keterangan.setRows(5);
        txt_keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_keteranganKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(txt_keterangan);

        panel_inputan.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel11.setText("Poli");

        lbl_kode_poli.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txt_poli.setEditable(false);

        bt_cari_poli.setText("...");
        bt_cari_poli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cari_poliActionPerformed(evt);
            }
        });

        jLabel4.setText("Status");

        cmb_status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Rawat Jalan", "Rawat Inap", "BPJS" }));
        cmb_status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmb_statusKeyPressed(evt);
            }
        });

        txt_nama_tarif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nama_tarifKeyPressed(evt);
            }
        });

        jLabel2.setText("Nama Tarif");

        txt_kode_tarif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_kode_tarifKeyPressed(evt);
            }
        });

        jLabel1.setText("Kode Tarif");

        javax.swing.GroupLayout panel_inputanLayout = new javax.swing.GroupLayout(panel_inputan);
        panel_inputan.setLayout(panel_inputanLayout);
        panel_inputanLayout.setHorizontalGroup(
            panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_inputanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_inputanLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_kode_poli, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_poli, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bt_cari_poli, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(panel_inputanLayout.createSequentialGroup()
                        .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_inputanLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(52, 52, 52)
                                .addComponent(cmb_status, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_inputanLayout.createSequentialGroup()
                                .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addGap(23, 23, 23)
                                .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_kode_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_nama_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(31, Short.MAX_VALUE))))
        );
        panel_inputanLayout.setVerticalGroup(
            panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_inputanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_kode_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_nama_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmb_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bt_cari_poli)
                            .addComponent(txt_poli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(lbl_kode_poli, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panel_inputan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_cari_tarif)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_cari_tarif))
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panel_inputan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_cari_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_cari_tarif)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );

        jTabbedPane1.addTab("Master Tarif", jPanel1);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(390, 100));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 426, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 96, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 80, 430, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 426, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 96, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 430, 100));

        jCheckBox1.setText("Di Verfikasi");
        jPanel4.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, 130, -1));

        jCheckBox2.setText("Di sahkan");
        jPanel4.add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 102, -1));

        jLabel5.setText("          < Nama Verifikator>");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 50, 240, 20));

        jLabel7.setText("          < Nama Pengesah >");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 220, 20));
        jPanel4.add(jDateTimePicker1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 160, -1));

        bt_pengesah.setText("Publish Tarif Pengesah");
        jPanel4.add(bt_pengesah, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, 260, -1));

        bt_cari_verif.setText("...");
        jPanel4.add(bt_cari_verif, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 50, 30, -1));

        bt_cari_verif1.setText("...");
        jPanel4.add(bt_cari_verif1, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 50, 30, -1));

        bt_pengesah1.setText("Publish Tarif Verifikasi");
        jPanel4.add(bt_pengesah1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 210, 260, -1));
        jPanel4.add(jDateTimePicker2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 210, 160, -1));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 930, 310));

        jLabel8.setText("Log Perubahan Data Publish");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 230, -1));

        jTabbedPane1.addTab("Verifikasi dan Pengesahan", jPanel4);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 990, 680));

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable3);

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel9.setText("Log Perubahan Data Tarif");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 77, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 20, 300, 690));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_cari_poliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cari_poliKeyPressed
        // TODO add your handling code here:
          if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
             tb_poli.requestFocus();
             tb_poli.setRowSelectionInterval(0, 0);
         }
    }//GEN-LAST:event_txt_cari_poliKeyPressed

    private void tb_poliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_poliKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
             int row = this.tb_poli.getSelectedRow();

        if (row == -1) {
            // No row selected
        } else {
            
           lbl_kode_poli.setText(this.tb_poli.getModel().getValueAt(row, 0).toString());
           this.txt_poli.setText(this.tb_poli.getModel().getValueAt(row, 1).toString());
           this.dlg_cari_poli.setVisible(false);
          
             txt_tarif.requestFocus();
         
           
         }
        
        }
    }//GEN-LAST:event_tb_poliKeyPressed

    private void tb_poliMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_poliMouseReleased
        // TODO add your handling code here:
         if (evt.getClickCount() == 2) {
            int row = this.tb_poli.getSelectedRow();

           if (row == -1) {
            // No row selected
          } else {   
            lbl_kode_poli.setText(this.tb_poli.getModel().getValueAt(row, 0).toString());
            this.txt_poli.setText(this.tb_poli.getModel().getValueAt(row, 1).toString());
            this.dlg_cari_poli.setVisible(false);
         }
       }
    }//GEN-LAST:event_tb_poliMouseReleased

    private void txt_kode_tarifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_kode_tarifKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_nama_tarif.requestFocus();
        }
    }//GEN-LAST:event_txt_kode_tarifKeyPressed

    private void txt_nama_tarifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nama_tarifKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.cmb_status.requestFocus();
        }
    }//GEN-LAST:event_txt_nama_tarifKeyPressed

    private void cmb_statusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmb_statusKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            bt_cari_poli.requestFocus();
            //this.txt_keterangan.requestFocus();

        }

    }//GEN-LAST:event_cmb_statusKeyPressed

    private void bt_cari_poliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_poliActionPerformed
        // TODO add your handling code here:
        dlg_cari_poli.setLocationRelativeTo(this);
        this.dlg_cari_poli.setVisible(true);
    }//GEN-LAST:event_bt_cari_poliActionPerformed

    private void txt_keteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_keteranganKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.txt_rs.requestFocus();
        }
    }//GEN-LAST:event_txt_keteranganKeyPressed

    private void bt_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_saveActionPerformed
        // TODO add your handling code here:
         int pr= Integer.valueOf(this.txt_rs.getText())+Integer.valueOf(this.txt_dr.getText())+Integer.valueOf(this.txt_sarana.getText());
     
      lbl_cepe.setText(" "+pr +" %");
        
        
        if((Integer.valueOf(this.txt_rs.getText())+Integer.valueOf(this.txt_dr.getText())+Integer.valueOf(this.txt_sarana.getText()))>100){
            JOptionPane.showMessageDialog(null, "Jumlah Presentase Melebihi 100 % ");
            this.txt_rs.setText("0");
            this.txt_dr.setText("0");  
            this.txt_sarana.setText("0");
            this.txt_rs.requestFocus();
        }
        else{
                if(this.txt_kode_tarif.getText().isEmpty()||txt_nama_tarif.getText().isEmpty()||
                        txt_poli.getText().isEmpty()||txt_tarif.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Maaf Data Inputan Ada yg Kosong!");
                    this.txt_kode_tarif.requestFocus();
                }
                else{
                  if(txt_tarif.getText().equals("0")){
                      JOptionPane.showMessageDialog(null, "Maaf Tarif/Presentase Tidak Boleh 0!");
                      this.txt_tarif.requestFocus();
                  }
                  else if((Integer.valueOf(this.txt_rs.getText())+Integer.valueOf(this.txt_dr.getText())+Integer.valueOf(this.txt_sarana.getText()))<100){
                   JOptionPane.showMessageDialog(null, "Maaf Presentase kurang dari 100 !");
                    this.txt_rs.requestFocus();
                  }
                  else{
                     if(!this.cmb_status.getSelectedItem().equals(" ")){
                        //save
                        
                         
                     }
                     else{
                      JOptionPane.showMessageDialog(null, "Maaf Status Tidak Boleh Kosong !");
                      this.cmb_status.requestFocus();
                     
                     }
                  }
                }
        }
    }//GEN-LAST:event_bt_saveActionPerformed

    private void hapusinputan(){
    
        JTextField temp=null;

        for(Component c:panel_inputan.getComponents()){
           if(c.getClass().toString().contains("javax.swing.JTextField")){
              temp=(JTextField)c;
             
              temp.setText("");
        }
      }
        
        
    
    }
    
    private void txt_tarifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_tarifKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_rs.requestFocus();
        }
    }//GEN-LAST:event_txt_tarifKeyPressed

    private void txt_tarifKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_tarifKeyTyped
        // TODO add your handling code here:
        char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }

    }//GEN-LAST:event_txt_tarifKeyTyped

    private void txt_saranaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_saranaKeyTyped
        // TODO add your handling code here:
        char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txt_saranaKeyTyped

    private void txt_drKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_drKeyTyped
        // TODO add your handling code here:
        char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txt_drKeyTyped

    private void txt_rsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rsKeyTyped
        // TODO add your handling code here:
        char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txt_rsKeyTyped

    private void bt_saveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_saveMouseEntered
        // TODO add your handling code here:
          int pr= Integer.valueOf(this.txt_rs.getText())+Integer.valueOf(this.txt_dr.getText())+Integer.valueOf(this.txt_sarana.getText());
     
      lbl_cepe.setText(" "+pr +" %");
    }//GEN-LAST:event_bt_saveMouseEntered

    private void txt_rsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rsKeyPressed
        // TODO add your handling code here:
          if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_dr.requestFocus();
        }
    }//GEN-LAST:event_txt_rsKeyPressed

    private void txt_drKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_drKeyPressed
        // TODO add your handling code here:
          if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_sarana.requestFocus();
        }
    }//GEN-LAST:event_txt_drKeyPressed

    private void txt_saranaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_saranaKeyPressed
        // TODO add your handling code here:
          if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.bt_save.requestFocus();
        }
    }//GEN-LAST:event_txt_saranaKeyPressed

    
    private void filterpoli(){
    
          try {
                datl = new Crud_local();

                try {
                    datl.readRec_cariPoli(txt_cari_poli.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

                tb_poli.setModel(datl.modelpoli);
            } catch (Exception ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    
    private void cekpersen(){
      
      
      int pr= Integer.valueOf(this.txt_rs.getText())+Integer.valueOf(this.txt_dr.getText())+Integer.valueOf(this.txt_sarana.getText());
     
      lbl_cepe.setText(" "+pr +" %");
      

      
//     if((Integer.valueOf(this.txt_rs.getText())+Integer.valueOf(this.txt_dr.getText()))==100){
//        
//      txt_sarana.setEditable(false);
//      txt_rs.setEditable(true);
//      txt_dr.setEditable(true);
//     }
//     
//     
//     else if((Integer.valueOf(this.txt_rs.getText())+Integer.valueOf(this.txt_sarana.getText()))==100){
//        
//         txt_dr.setEditable(false);
//     txt_rs.setEditable(true);
//       txt_sarana.setEditable(true);
//     }
//     
//    
//    
//     else if((Integer.valueOf(this.txt_dr.getText())+Integer.valueOf(this.txt_sarana.getText()))==100){
//        
//         txt_rs.setEditable(false);
//      txt_dr.setEditable(true);
//       txt_sarana.setEditable(true);
//     }
//   
  
     if(Integer.valueOf(this.txt_rs.getText())>100){
     
         this.txt_rs.setText("0");
         
         
     
     }
     else if(Integer.valueOf(this.txt_dr.getText())>100){
     this.txt_dr.setText("0");
     
     }
     else if(Integer.valueOf(this.txt_sarana.getText())>100){
     this.txt_sarana.setText("0");
     
     }
    
     else if((Integer.valueOf(this.txt_rs.getText())+Integer.valueOf(this.txt_dr.getText()))>100){
     
         
     
     
     }
    
   
     
  
    }
    
    
    
    private void hitungpresentasDR(){
        
      if(txt_dr.getText().isEmpty()){
         txt_dr.setText("0");
      }
      else{
         
        
         
        Double totdr=(Double.parseDouble(txt_tarif.getText().toString()) * Double.parseDouble(this.txt_dr.getText().toString()))/100;
      
     
       lbl_dokter.setText(Utilitas.formatuang(totdr));
          
      }
       
        
          
    }
    
    private void hitungpresentaseRS(){
           
      if(txt_rs.getText().isEmpty()){
      txt_rs.setText("0");
      }
      else{
         
          
         
         
            Double totrs=(Double.parseDouble(txt_tarif.getText().toString()) * Double.parseDouble(this.txt_rs.getText().toString()))/100;
      
            lbl_rs.setText(Utilitas.formatuang(totrs));
          
      }
    }
    
    private void hitungpresentaseSR(){
        
   if(txt_sarana.getText().isEmpty()){
      txt_sarana.setText("0");
    }
    else{  
          // cekpersen();
           
         
             this.txt_sarana.setEditable(true);
            Double totsr=(Double.parseDouble(txt_tarif.getText().toString()) * Double.parseDouble(this.txt_sarana.getText().toString()))/100;
      
            lbl_sarana.setText(Utilitas.formatuang(totsr));
         
            
     }
   
    }
    
      
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
            java.util.logging.Logger.getLogger(frm_tarif.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_tarif.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_tarif.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_tarif.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_tarif().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_cari_poli;
    private javax.swing.JButton bt_cari_tarif;
    private javax.swing.JButton bt_cari_verif;
    private javax.swing.JButton bt_cari_verif1;
    private javax.swing.JButton bt_cetak;
    private javax.swing.JButton bt_delete;
    private javax.swing.JButton bt_edit;
    private javax.swing.JButton bt_pengesah;
    private javax.swing.JButton bt_pengesah1;
    private javax.swing.JButton bt_save;
    private javax.swing.JComboBox<String> cmb_status;
    private javax.swing.JDialog dlg_cari_poli;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private uz.ncipro.calendar.JDateTimePicker jDateTimePicker1;
    private uz.ncipro.calendar.JDateTimePicker jDateTimePicker2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JLabel lbl_cepe;
    private javax.swing.JLabel lbl_dokter;
    private javax.swing.JLabel lbl_kode_poli;
    private javax.swing.JLabel lbl_rs;
    private javax.swing.JLabel lbl_sarana;
    private javax.swing.JPanel panel_inputan;
    private javax.swing.JTable tb_poli;
    private javax.swing.JTextField txt_cari_poli;
    private javax.swing.JTextField txt_cari_tarif;
    private javax.swing.JTextField txt_dr;
    private javax.swing.JTextArea txt_keterangan;
    private javax.swing.JTextField txt_kode_tarif;
    private javax.swing.JTextField txt_nama_tarif;
    private javax.swing.JTextField txt_poli;
    private javax.swing.JTextField txt_rs;
    private javax.swing.JTextField txt_sarana;
    private javax.swing.JTextField txt_tarif;
    // End of variables declaration//GEN-END:variables
}
