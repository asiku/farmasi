/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit_poli;

import farmasi.Crud_farmasi;
import farmasi.Crud_local;
import static farmasi.Crud_local.cekvalpilih;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import tools.Utilitas;

/**
 *
 * @author jengcool
 */
public class frm_poli extends javax.swing.JFrame {

    private ScheduledExecutorService executor;

    private Crud_farmasi dat;

    private Crud_local datl;

    private boolean pilihcari;

    /**
     * Creates new form frm_poli
     */
    public frm_poli() {
        initComponents();

    }

    public frm_poli(String nmp, String poli) {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

       

        this.lbl_petugas.setText(nmp);
        this.lbl_poli.setText(poli);

        jPanel2.setPreferredSize(new Dimension(this.ToolBar.getWidth() + 200, 72));
        LoopTgl();

        filterReg();

        txt_cari_reg.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtertxt();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtertxt();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtertxt();
            }
        });

        this.txt_cari_petugas.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterPegawai();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterPegawai();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterPegawai();
            }
        });

        filterPegawai();

        settbTindakan();
    }

    private void settbTindakan() {

        try {
            datl = new Crud_local();

            datl.readRec_cariTarifTemplate(txt_cari_tindakan_template.getText(),false,1);

            this.tb_tindakan.setModel(datl.modeltariftemplate);

            setukurantbtindakan();
            
            // System.out.println("count:"+tb_tindakan.getColumnModel().getColumnCount());
//            for (int i = tb_tindakan.getColumnModel().getColumnCount()-1; i >= 0; i--) {
//             if(!(i==0||i==1)){
//                 System.out.println(i);
//               TableColumn col = tb_tindakan.getColumnModel().getColumn(i);
//               tb_tindakan.getColumnModel().removeColumn(col);
//             }
//            }
        } catch (Exception ex) {
            Logger.getLogger(frm_poli.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void filtertxt() {

//            for (int i = 0; i < txt_cari_reg.getText().length(); i++) {
//                if (Character.isDigit(txt_cari_reg.getText().charAt(i))) {
//                    Utilitas.filtertb(txt_cari_reg.getText(), tb_reg, 0);
//                } else {
//                    Utilitas.filtertb(txt_cari_reg.getText(), tb_reg, 1);
//                }
//            }
        filterReg();

    }

    private void filterReg() {

        this.lbl_tgl_server.setText(Utilitas.getDateServer());

        if (!this.lbl_tgl_server.getText().isEmpty()) {
            try {

                dat = new Crud_farmasi();

                String[] b = {"ARJUNO", "MAHAMERU", "LEUSER", "RANAI", "PANGRANGO", "CADANGAN"};

                String t = lbl_tgl_server.getText().toString().substring(5, lbl_tgl_server.getText().length() - 3);

                dat.readRec_kamarinap(b, this.txt_cari_reg.getText(), t);

                this.tb_reg.setModel(dat.modelkamarinap);

                setukurantbReg();

                DefaultTableModel dm = (DefaultTableModel) tb_reg.getModel();

//                int rowCount = dm.getRowCount();
//
//                for (int i = rowCount - 1; i >= 0; i--) {
//                    if (!dm.getValueAt(i, 3).toString().substring(5, dm.getValueAt(i, 3).toString().length() - 3).equals(t)) {
//                        dm.removeRow(i);
//                    }
//                }
            } catch (SQLException ex) {
                Logger.getLogger(frm_poli.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(frm_poli.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "SerVer tidak Terkoneksi");

        }
    }

     private void setukurantbtindakan() {
        tb_reg.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel tr = tb_tindakan.getColumnModel();

        tr.getColumn(0).setPreferredWidth(70);
        tr.getColumn(1).setPreferredWidth(280);
        tr.getColumn(2).setPreferredWidth(80);
        
    }
    
    private void setukurantbReg() {
        tb_reg.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel tr = tb_reg.getColumnModel();

        tr.getColumn(0).setPreferredWidth(80);
        tr.getColumn(1).setPreferredWidth(299);
        tr.getColumn(2).setPreferredWidth(0);
        tr.getColumn(3).setPreferredWidth(0);
        tr.getColumn(4).setPreferredWidth(0);
    }

    private void setwaktu() {
        this.lbl_jamnow.setText(Utilitas.Jam() + "   ");
    }

    private void LoopTgl() {

//     ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
//
//     Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());
//
//     int initialDelay = 0;
//     int period = 1;
//     executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
        executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            //  try {
            //TimeUnit.SECONDS.sleep(2);
            setwaktu();
            // System.out.println("Scheduling: " + System.nanoTime());
//    }
//    catch (InterruptedException e) {
//        System.err.println("task interrupted");
//    }
        };

        executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        dlg_dpjp = new javax.swing.JDialog();
        jScrollPane5 = new javax.swing.JScrollPane();
        tb_cari_petugas = new javax.swing.JTable();
        txt_cari_petugas = new javax.swing.JTextField();
        buttonGroup2 = new javax.swing.ButtonGroup();
        ToolBar = new javax.swing.JToolBar();
        jPanel2 = new javax.swing.JPanel();
        lbl_jam = new javax.swing.JLabel();
        lbl_petugas = new javax.swing.JLabel();
        lbl_poli = new javax.swing.JLabel();
        lbl_poli1 = new javax.swing.JLabel();
        lbl_news = new javax.swing.JLabel();
        lbl_tgl_server = new javax.swing.JLabel();
        lbl_jamnow = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tb_reg = new javax.swing.JTable();
        txt_cari_reg = new javax.swing.JTextField();
        lbl_cari = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txt_nm_pasien = new javax.swing.JTextField();
        txt_no_rm = new javax.swing.JTextField();
        txt_no_rawat = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_nip_dpjp = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_nip_ppjp = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbl_tgl_masuk = new javax.swing.JLabel();
        lbl_kamar_inap = new javax.swing.JLabel();
        bt_cari_dpjp = new javax.swing.JButton();
        bt_cari_ppjp = new javax.swing.JButton();
        txt_dpjp = new javax.swing.JTextField();
        txt_ppjp = new javax.swing.JTextField();
        r_perawatan = new javax.swing.JRadioButton();
        r_pulang = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_dokter = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_dokter1 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        bt_save = new javax.swing.JButton();
        bt_add = new javax.swing.JButton();
        bt_edit = new javax.swing.JButton();
        bt_hapus = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_nama_template = new javax.swing.JTextField();
        txt_nama_template1 = new javax.swing.JTextField();
        bt_cari_template_petugas = new javax.swing.JButton();
        bt_cari_template_petugas1 = new javax.swing.JButton();
        txt_nama_template4 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_cari_tindakan_template = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tb_tindakan = new javax.swing.JTable();
        bt_cari_tindakan_template = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        txt_cari_input_template = new javax.swing.JTextField();
        txt_nama_template2 = new javax.swing.JTextField();
        txt_nama_template5 = new javax.swing.JTextField();
        bt_cari_tindakan_template1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        dlg_dpjp.setModal(true);
        dlg_dpjp.setSize(new java.awt.Dimension(450, 352));

        tb_cari_petugas.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_cari_petugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_cari_petugasMouseReleased(evt);
            }
        });
        tb_cari_petugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tb_cari_petugasKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tb_cari_petugas);

        txt_cari_petugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cari_petugasKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout dlg_dpjpLayout = new javax.swing.GroupLayout(dlg_dpjp.getContentPane());
        dlg_dpjp.getContentPane().setLayout(dlg_dpjpLayout);
        dlg_dpjpLayout.setHorizontalGroup(
            dlg_dpjpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlg_dpjpLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dlg_dpjpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_cari_petugas, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dlg_dpjpLayout.setVerticalGroup(
            dlg_dpjpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dlg_dpjpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_cari_petugas, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ToolBar.setBackground(new java.awt.Color(255, 255, 255));
        ToolBar.setFloatable(false);
        ToolBar.setRollover(true);
        ToolBar.setPreferredSize(new java.awt.Dimension(100, 60));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        lbl_jam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/jamk_ico.png"))); // NOI18N

        lbl_petugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/bidan_ico.png"))); // NOI18N

        lbl_poli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/hospital_ico.png"))); // NOI18N

        lbl_poli1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/log_out.png"))); // NOI18N
        lbl_poli1.setText("Logout");
        lbl_poli1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_poli1MouseClicked(evt);
            }
        });

        lbl_news.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/news_ico.png"))); // NOI18N
        lbl_news.setText("Info");

        lbl_jamnow.setText("sssss");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_jam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_tgl_server, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_jamnow, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_petugas)
                .addGap(26, 26, 26)
                .addComponent(lbl_news)
                .addGap(18, 18, 18)
                .addComponent(lbl_poli1)
                .addGap(25, 25, 25)
                .addComponent(lbl_poli)
                .addContainerGap(786, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_news)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbl_petugas)
                        .addComponent(lbl_jam)
                        .addComponent(lbl_poli)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(8, 8, 8)
                            .addComponent(lbl_poli1))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_tgl_server)
                    .addComponent(lbl_jamnow))
                .addGap(22, 22, 22))
        );

        ToolBar.add(jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tb_reg.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_reg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_regMouseReleased(evt);
            }
        });
        tb_reg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tb_regKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tb_regKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tb_reg);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Data Kamar Inap", jPanel12);

        txt_cari_reg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cari_regKeyPressed(evt);
            }
        });

        lbl_cari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/carik_ico.png"))); // NOI18N
        lbl_cari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_cariMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txt_cari_reg, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_cari)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_cari_reg)
                    .addComponent(lbl_cari))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txt_nm_pasien.setEditable(false);

        txt_no_rm.setEditable(false);

        txt_no_rawat.setEditable(false);

        jLabel2.setText("No Rawat");

        jLabel3.setText("No RM");

        jLabel4.setText("Nama Pasien");

        jLabel6.setText("DPJP");

        txt_nip_dpjp.setEditable(false);

        jLabel7.setText("PPJP");

        txt_nip_ppjp.setEditable(false);

        jLabel10.setText("Kamar Inap");

        jLabel11.setText("Tgl Masuk Inap");

        lbl_tgl_masuk.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbl_kamar_inap.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        bt_cari_dpjp.setText("jButton5");
        bt_cari_dpjp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cari_dpjpActionPerformed(evt);
            }
        });

        bt_cari_ppjp.setText("jButton5");
        bt_cari_ppjp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cari_ppjpActionPerformed(evt);
            }
        });

        txt_dpjp.setEditable(false);

        txt_ppjp.setEditable(false);

        buttonGroup2.add(r_perawatan);
        r_perawatan.setText("Perawatan");

        buttonGroup2.add(r_pulang);
        r_pulang.setText("Pulang");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txt_no_rawat, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txt_no_rm, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txt_nm_pasien, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txt_nip_dpjp, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_dpjp, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_cari_dpjp, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(64, 64, 64)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_tgl_masuk, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ppjp, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_kamar_inap, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(r_perawatan)
                        .addGap(43, 43, 43)
                        .addComponent(r_pulang))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txt_nip_ppjp, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bt_cari_ppjp, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7))
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_nip_ppjp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_cari_ppjp))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_ppjp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel11)
                        .addGap(5, 5, 5)
                        .addComponent(lbl_tgl_masuk, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addGap(5, 5, 5)
                        .addComponent(lbl_kamar_inap, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(r_perawatan)
                            .addComponent(r_pulang)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txt_no_rawat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addGap(5, 5, 5)
                        .addComponent(txt_no_rm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel4)
                        .addGap(5, 5, 5)
                        .addComponent(txt_nm_pasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel6)
                        .addGap(2, 2, 2)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_nip_dpjp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_cari_dpjp))
                        .addGap(2, 2, 2)
                        .addComponent(txt_dpjp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Dokter");

        jLabel5.setText("Petugas");

        jButton8.setText("jButton8");

        jButton9.setText("jButton8");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_dokter, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_dokter1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_dokter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_dokter1)
                        .addComponent(jButton9)
                        .addComponent(jLabel5)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Template Tindakan"));

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

        jButton7.setText("jButton5");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        bt_save.setText("Save");

        bt_add.setText("Add");

        bt_edit.setText("Edit");

        bt_hapus.setText("Hapus");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jRadioButton1.setText("Belum Ada Perkembangan");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jRadioButton2.setText("Membaik");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jRadioButton3.setText("Menurun");

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jRadioButton4.setText("Kritis");

        buttonGroup1.add(jRadioButton5);
        jRadioButton5.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jRadioButton5.setText("Sembuh");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton5)
                .addGap(47, 47, 47)
                .addComponent(bt_save)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_add)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_edit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bt_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_save)
                    .addComponent(bt_add)
                    .addComponent(bt_edit)
                    .addComponent(bt_hapus)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4)
                    .addComponent(jRadioButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79))
        );

        jTabbedPane1.addTab("Input Tindakan", jPanel1);

        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setText("Nama Template");
        jPanel9.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 30, 115, -1));

        jLabel12.setText("DPJP");
        jPanel9.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 64, 115, -1));
        jPanel9.add(txt_nama_template, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 28, 174, -1));
        jPanel9.add(txt_nama_template1, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 62, 174, -1));

        bt_cari_template_petugas.setText("...");
        jPanel9.add(bt_cari_template_petugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(377, 59, 41, -1));

        bt_cari_template_petugas1.setText("...");
        jPanel9.add(bt_cari_template_petugas1, new org.netbeans.lib.awtextra.AbsoluteConstraints(377, 127, 41, -1));
        jPanel9.add(txt_nama_template4, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 130, 174, -1));

        jLabel13.setText("PPJP");
        jPanel9.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 132, 49, -1));
        jPanel9.add(txt_cari_tindakan_template, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 345, 33));

        tb_tindakan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(tb_tindakan);

        jPanel9.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 293, 407, 290));

        bt_cari_tindakan_template.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/carik_ico.png"))); // NOI18N
        jPanel9.add(bt_cari_tindakan_template, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 250, 46, 33));

        jButton1.setText(">");
        jPanel9.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(431, 408, -1, -1));

        jButton2.setText("X");
        jPanel9.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(431, 451, -1, -1));

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

        jPanel9.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(488, 294, 434, 291));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(jTable4);

        jPanel9.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(488, 57, 434, 219));
        jPanel9.add(txt_cari_input_template, new org.netbeans.lib.awtextra.AbsoluteConstraints(488, 12, 380, 33));
        jPanel9.add(txt_nama_template2, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 96, 174, -1));
        jPanel9.add(txt_nama_template5, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 164, 174, -1));

        bt_cari_tindakan_template1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/carik_ico.png"))); // NOI18N
        jPanel9.add(bt_cari_tindakan_template1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 10, 43, 33));

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton5.setText("Hapus");

        jButton4.setText("Edit");

        jButton6.setText("Edit");

        jButton3.setText("Save");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 410, -1));

        jTabbedPane1.addTab("Buat Template Tindakan", jPanel9);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 939, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_poli1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_poli1MouseClicked
        // TODO add your handling code here:
        int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah Akan Keluar?", "Warning ",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (dialogResult == JOptionPane.YES_OPTION) {

            executor.shutdown();

            new frm_login_poli().setVisible(true);
            dispose();
        }

    }//GEN-LAST:event_lbl_poli1MouseClicked

    private void lbl_cariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_cariMouseClicked
        // TODO add your handling code here:
        filterReg();
        this.filtertxt();
    }//GEN-LAST:event_lbl_cariMouseClicked

    private void tb_regKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_regKeyTyped
        // TODO add your handling code here:
        char keyChar = evt.getKeyChar();

        this.txt_cari_reg.requestFocus();

        if (evt.getKeyCode() != KeyEvent.VK_ENTER) {

            this.txt_cari_reg.setText(String.valueOf(keyChar).trim());
        }

        if (!txt_cari_reg.getText().isEmpty()) {
            tb_reg.setRowSelectionInterval(0, 0);
        }
    }//GEN-LAST:event_tb_regKeyTyped

    private void txt_cari_regKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cari_regKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.filtertxt();
            this.tb_reg.requestFocus();
            //jtb_barang.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
                tb_reg.setRowSelectionInterval(0, 0);
            

                

            

        }
    }//GEN-LAST:event_txt_cari_regKeyPressed

    private void tb_regKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_regKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int row = this.tb_reg.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {

                txt_no_rm.setText(this.tb_reg.getModel().getValueAt(row, 0).toString());
                this.txt_nm_pasien.setText(this.tb_reg.getModel().getValueAt(row, 1).toString());
                txt_no_rawat.setText(this.tb_reg.getModel().getValueAt(row, 2).toString());
                this.lbl_tgl_masuk.setText(this.tb_reg.getModel().getValueAt(row, 3).toString());
                lbl_kamar_inap.setText(this.tb_reg.getModel().getValueAt(row, 4).toString());

                //txt_tarif.requestFocus();
                this.txt_cari_reg.requestFocus();

            }

        }
    }//GEN-LAST:event_tb_regKeyPressed

    private void tb_cari_petugasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_cari_petugasMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = this.tb_cari_petugas.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else if (pilihcari) {
                setPJ(1, row);
            } else {
                setPJ(2, row);
            } //                txt_nip_dpjp.setText(this.tb_cari_petugas.getModel().getValueAt(row, 0).toString());
            //                this.txt_dpjp.setText(this.tb_cari_petugas.getModel().getValueAt(row, 1).toString());
            //                this.dlg_dpjp.setVisible(false);
        }
    }//GEN-LAST:event_tb_cari_petugasMouseReleased

    private void setPJ(int i, int row) {

        if (i == 1) {
            txt_nip_dpjp.setText(this.tb_cari_petugas.getModel().getValueAt(row, 0).toString());
            this.txt_dpjp.setText(this.tb_cari_petugas.getModel().getValueAt(row, 1).toString());
            this.dlg_dpjp.setVisible(false);
            bt_cari_ppjp.requestFocus();
            txt_cari_petugas.setText("");
        } else {
            txt_nip_ppjp.setText(this.tb_cari_petugas.getModel().getValueAt(row, 0).toString());
            this.txt_ppjp.setText(this.tb_cari_petugas.getModel().getValueAt(row, 1).toString());
            this.dlg_dpjp.setVisible(false);
             txt_cari_petugas.setText("");

        }

    }
    private void tb_cari_petugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_cari_petugasKeyPressed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int row = this.tb_cari_petugas.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else if (pilihcari) {
                setPJ(1, row);
            } else {
                setPJ(2, row);
            } // bt_cari_poli.requestFocus();

        }

    }//GEN-LAST:event_tb_cari_petugasKeyPressed


    private void txt_cari_petugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cari_petugasKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tb_cari_petugas.requestFocus();
            tb_cari_petugas.setRowSelectionInterval(0, 0);
        }
    }//GEN-LAST:event_txt_cari_petugasKeyPressed

    private void bt_cari_dpjpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_dpjpActionPerformed
        // TODO add your handling code here:

        pilihcari = true;

        this.dlg_dpjp.setLocationRelativeTo(this);
        this.dlg_dpjp.setVisible(true);

    }//GEN-LAST:event_bt_cari_dpjpActionPerformed

    private void tb_regMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_regMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = this.tb_reg.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {

                txt_no_rm.setText(this.tb_reg.getModel().getValueAt(row, 0).toString());
                this.txt_nm_pasien.setText(this.tb_reg.getModel().getValueAt(row, 1).toString());
                txt_no_rawat.setText(this.tb_reg.getModel().getValueAt(row, 2).toString());
                this.lbl_tgl_masuk.setText(this.tb_reg.getModel().getValueAt(row, 3).toString());
                lbl_kamar_inap.setText(this.tb_reg.getModel().getValueAt(row, 4).toString());
                //txt_tarif.requestFocus();
                this.txt_cari_reg.requestFocus();

            }
        }
    }//GEN-LAST:event_tb_regMouseReleased

    private void bt_cari_ppjpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_ppjpActionPerformed
        // TODO add your handling code here:

        pilihcari = false;

        this.dlg_dpjp.setLocationRelativeTo(this);
        this.dlg_dpjp.setVisible(true);
        txt_cari_petugas.requestFocus();

    }//GEN-LAST:event_bt_cari_ppjpActionPerformed

    private void filterPegawai() {

        try {
            dat = new Crud_farmasi();

            try {
                dat.readRec_pegawaiF(txt_cari_petugas.getText());
            } catch (SQLException ex) {
                Logger.getLogger(frm_petugas_poli.class.getName()).log(Level.SEVERE, null, ex);
            }

            tb_cari_petugas.setModel(dat.modelpegawai);
        } catch (Exception ex) {
            Logger.getLogger(frm_petugas_poli.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(frm_poli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_poli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_poli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_poli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_poli().setVisible(true);
            }
        });

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar ToolBar;
    private javax.swing.JButton bt_add;
    private javax.swing.JButton bt_cari_dpjp;
    private javax.swing.JButton bt_cari_ppjp;
    private javax.swing.JButton bt_cari_template_petugas;
    private javax.swing.JButton bt_cari_template_petugas1;
    private javax.swing.JButton bt_cari_tindakan_template;
    private javax.swing.JButton bt_cari_tindakan_template1;
    private javax.swing.JButton bt_edit;
    private javax.swing.JButton bt_hapus;
    private javax.swing.JButton bt_save;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JDialog dlg_dpjp;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lbl_cari;
    private javax.swing.JLabel lbl_jam;
    private javax.swing.JLabel lbl_jamnow;
    private javax.swing.JLabel lbl_kamar_inap;
    private javax.swing.JLabel lbl_news;
    private javax.swing.JLabel lbl_petugas;
    private javax.swing.JLabel lbl_poli;
    private javax.swing.JLabel lbl_poli1;
    private javax.swing.JLabel lbl_tgl_masuk;
    private javax.swing.JLabel lbl_tgl_server;
    private javax.swing.JRadioButton r_perawatan;
    private javax.swing.JRadioButton r_pulang;
    private javax.swing.JTable tb_cari_petugas;
    private javax.swing.JTable tb_reg;
    private javax.swing.JTable tb_tindakan;
    private javax.swing.JTextField txt_cari_input_template;
    private javax.swing.JTextField txt_cari_petugas;
    private javax.swing.JTextField txt_cari_reg;
    private javax.swing.JTextField txt_cari_tindakan_template;
    private javax.swing.JTextField txt_dokter;
    private javax.swing.JTextField txt_dokter1;
    private javax.swing.JTextField txt_dpjp;
    private javax.swing.JTextField txt_nama_template;
    private javax.swing.JTextField txt_nama_template1;
    private javax.swing.JTextField txt_nama_template2;
    private javax.swing.JTextField txt_nama_template4;
    private javax.swing.JTextField txt_nama_template5;
    private javax.swing.JTextField txt_nip_dpjp;
    private javax.swing.JTextField txt_nip_ppjp;
    private javax.swing.JTextField txt_nm_pasien;
    private javax.swing.JTextField txt_no_rawat;
    private javax.swing.JTextField txt_no_rm;
    private javax.swing.JTextField txt_ppjp;
    // End of variables declaration//GEN-END:variables
}
