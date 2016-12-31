/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit_poli_perinatology;

import unit_poli.*;
import farmasi.Crud_farmasi;
import farmasi.Crud_local;
import static farmasi.Crud_local.cekvalpilih;
import static farmasi.Crud_local.cekvalpilihtemplatepilih;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

    private int irowtindakandetail = 0;
    private int irowhistory = 0;

    
    private ScheduledExecutorService executor;

    private Crud_farmasi dat;

    private Crud_local datl;

    private int pilihcari;

    String[] template_title = new String[]{"Nama Template", "Petugas", "Status", "Tanggal"};
    String[] template_detail = new String[]{"Nama Template", "Kode Tindakan", "Tindakan"};

    String[] unit_detail = new String[]{"No Rawat", "Kode Tindakan", "Tindakan", "Petugas", "Tgl Tindakan", "Nip Petugas"};

    DefaultTableModel modelunitdetail = new DefaultTableModel(unit_detail, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };

    DefaultTableModel modeltemplate = new DefaultTableModel(template_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };

    DefaultTableModel modeltemplatedetail = new DefaultTableModel(template_detail, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };

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

        txt_cari_tindakan_pilih.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

                filterceklistTemplatepilih();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterceklistTemplatepilih();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterceklistTemplatepilih();
            }
        });

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

        //settbpilih();
        txt_no_rawat.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
//             filterNorawat();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
//                filterNorawat();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
//                 filterNorawat();
            }
        });

        
        txt_cari_nama_tindakan.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTindakanhistory();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTindakanhistory();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTindakanhistory();
            }
        });
        
    }

    private void filterNorawat() {
        Utilitas.filtertb(txt_no_rawat.getText(), tb_unit_detail, 0, 2);

    }

    private void filterceklistTemplatepilih() {
        Utilitas.filtertb(txt_cari_tindakan_pilih.getText(), tb_tindakan_pilih, 1, 1);

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

                String[] b = {"PERINATOLOGI", "PERINATOLOGI BPJS"};

                String t = lbl_tgl_server.getText().toString().substring(5, lbl_tgl_server.getText().length() - 3);

                dat.readRec_kamarinapPerina(b, this.txt_cari_reg.getText(), t, this.lbl_tgl_server.getText());

                this.tb_reg.setModel(dat.modelkamarinap);

                setukurantbReg();

                //             DefaultTableModel dm = (DefaultTableModel) tb_reg.getModel();
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

    private void setukurantbcaripetugas() {
        tb_cari_petugas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tr = this.tb_cari_petugas.getColumnModel();

        tr.getColumn(0).setPreferredWidth(80);
        tr.getColumn(1).setPreferredWidth(280);
        tr.getColumn(2).setPreferredWidth(180);

    }

    
    private void setukurantbulunitHistory() {

        this.tb_unit_detail_history.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        tb_unit_detail_history.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tr = this.tb_unit_detail_history.getColumnModel();

        tr.getColumn(0).setPreferredWidth(120);
        tr.getColumn(1).setPreferredWidth(100);
        tr.getColumn(2).setPreferredWidth(310);
        tr.getColumn(3).setPreferredWidth(220);
        tr.getColumn(4).setPreferredWidth(150);

    }
    
    private void setukurantbulunitdetail() {

        this.tb_unit_detail.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        tb_unit_detail.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tr = this.tb_unit_detail.getColumnModel();

        tr.getColumn(0).setPreferredWidth(120);
        tr.getColumn(1).setPreferredWidth(100);
        tr.getColumn(2).setPreferredWidth(310);
        tr.getColumn(3).setPreferredWidth(220);
        tr.getColumn(4).setPreferredWidth(150);

    }

    private void setukurantbtindakanpilih() {
        this.tb_tindakan_pilih.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        tb_tindakan_pilih.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel tr = tb_tindakan_pilih.getColumnModel();

        tr.getColumn(0).setPreferredWidth(75);
        tr.getColumn(1).setPreferredWidth(280);
        tr.getColumn(2).setPreferredWidth(50);

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
        Popup_hapus = new javax.swing.JPopupMenu();
        mnu_item_hapus_tindakan = new javax.swing.JMenuItem();
        Popup_history = new javax.swing.JPopupMenu();
        item_hapus = new javax.swing.JMenuItem();
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
        jPanel13 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tb_tindakan_pilih = new javax.swing.JTable();
        txt_cari_tindakan_pilih = new javax.swing.JTextField();
        bt_cari_tindakan_pilih = new javax.swing.JButton();
        bt_add_tindakan = new javax.swing.JButton();
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
        jLabel5 = new javax.swing.JLabel();
        lbl_nip_petugas_pilih = new javax.swing.JLabel();
        txt_petugas_pilih = new javax.swing.JTextField();
        bt_cari_tindakan = new javax.swing.JButton();
        lbl_kelas = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbl_nm_status = new javax.swing.JLabel();
        lbl_status = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        bt_save = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        r_membaik = new javax.swing.JRadioButton();
        r_menurun = new javax.swing.JRadioButton();
        r_kritis = new javax.swing.JRadioButton();
        r_sembuh = new javax.swing.JRadioButton();
        r_belum = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        r_pulang = new javax.swing.JRadioButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_unit_detail = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_unit_detail_history = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        Dttgl1 = new uz.ncipro.calendar.JDateTimePicker();
        Dttgl2 = new uz.ncipro.calendar.JDateTimePicker();
        bt_cari_tgl_tindakan = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txt_cari_nama_tindakan = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        lbl_petugas_history = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lbl_petugas_input_history = new javax.swing.JLabel();

        dlg_dpjp.setModal(true);
        dlg_dpjp.setResizable(false);
        dlg_dpjp.setSize(new java.awt.Dimension(550, 391));

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
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                    .addComponent(txt_cari_petugas))
                .addContainerGap())
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

        mnu_item_hapus_tindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/farmasi/hps_ico.png"))); // NOI18N
        mnu_item_hapus_tindakan.setText("Hapus");
        mnu_item_hapus_tindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnu_item_hapus_tindakanActionPerformed(evt);
            }
        });
        Popup_hapus.add(mnu_item_hapus_tindakan);

        item_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/delete_ic.png"))); // NOI18N
        item_hapus.setText("Hapus");
        item_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_hapusActionPerformed(evt);
            }
        });
        Popup_history.add(item_hapus);

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
                .addContainerGap(854, Short.MAX_VALUE))
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

        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jPanel12.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 56, 420, 540));

        txt_cari_reg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cari_regKeyPressed(evt);
            }
        });
        jPanel12.add(txt_cari_reg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 370, 32));

        lbl_cari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/carik_ico.png"))); // NOI18N
        lbl_cari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_cariMouseClicked(evt);
            }
        });
        jPanel12.add(lbl_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, -1, -1));

        jTabbedPane2.addTab("Data Pasien Inap", jPanel12);

        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tb_tindakan_pilih.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        tb_tindakan_pilih.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_tindakan_pilih.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_tindakan_pilihMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_tindakan_pilihMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tb_tindakan_pilih);

        jPanel13.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 420, 470));
        jPanel13.add(txt_cari_tindakan_pilih, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 320, 33));

        bt_cari_tindakan_pilih.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/carik_ico.png"))); // NOI18N
        bt_cari_tindakan_pilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cari_tindakan_pilihActionPerformed(evt);
            }
        });
        jPanel13.add(bt_cari_tindakan_pilih, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 90, 33));

        bt_add_tindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/tick_ico.png"))); // NOI18N
        bt_add_tindakan.setText("Proses");
        bt_add_tindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_add_tindakanActionPerformed(evt);
            }
        });
        jPanel13.add(bt_add_tindakan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 420, 29));

        jTabbedPane2.addTab("Data Tindakan Inap", jPanel13);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_nm_pasien.setEditable(false);
        jPanel4.add(txt_nm_pasien, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 131, 236, -1));

        txt_no_rm.setEditable(false);
        jPanel4.add(txt_no_rm, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 81, 189, -1));

        txt_no_rawat.setEditable(false);
        jPanel4.add(txt_no_rawat, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 189, -1));

        jLabel2.setText("No Rawat");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel3.setText("No RM");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 61, -1, -1));

        jLabel4.setText("Nama Pasien");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 111, -1, -1));

        jLabel6.setText("DPJP");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 161, -1, -1));

        txt_nip_dpjp.setEditable(false);
        jPanel4.add(txt_nip_dpjp, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 181, 191, -1));

        jLabel7.setText("PPJP");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, -1, -1));

        txt_nip_ppjp.setEditable(false);
        jPanel4.add(txt_nip_ppjp, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 33, 200, -1));

        jLabel10.setText("Kamar Inap");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 143, 108, -1));

        jLabel11.setText("Tgl Masuk Inap");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 92, 108, -1));

        lbl_tgl_masuk.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_tgl_masuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 110, 132, 19));

        lbl_kamar_inap.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_kamar_inap, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 163, 228, 19));

        bt_cari_dpjp.setText("jButton5");
        bt_cari_dpjp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cari_dpjpActionPerformed(evt);
            }
        });
        jPanel4.add(bt_cari_dpjp, new org.netbeans.lib.awtextra.AbsoluteConstraints(217, 178, 36, -1));

        bt_cari_ppjp.setText("jButton5");
        bt_cari_ppjp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cari_ppjpActionPerformed(evt);
            }
        });
        jPanel4.add(bt_cari_ppjp, new org.netbeans.lib.awtextra.AbsoluteConstraints(532, 30, 32, -1));

        txt_dpjp.setEditable(false);
        jPanel4.add(txt_dpjp, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 208, 191, -1));

        txt_ppjp.setEditable(false);
        jPanel4.add(txt_ppjp, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 61, 200, -1));

        jLabel5.setText("Status");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 60, -1));

        lbl_nip_petugas_pilih.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_nip_petugas_pilih, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, 110, 25));
        jPanel4.add(txt_petugas_pilih, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 250, 220, 30));

        bt_cari_tindakan.setText("jButton8");
        bt_cari_tindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cari_tindakanActionPerformed(evt);
            }
        });
        jPanel4.add(bt_cari_tindakan, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 220, 42, -1));

        lbl_kelas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_kelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 190, 160, 19));

        jLabel8.setText("Petugas");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 220, 60, -1));

        jLabel9.setText("Kelas");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 190, 60, -1));

        lbl_nm_status.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_nm_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, 170, 19));

        lbl_status.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 60, 19));

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bt_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/save_ico.png"))); // NOI18N
        bt_save.setText("Save");
        bt_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_saveActionPerformed(evt);
            }
        });
        jPanel8.add(bt_save, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 14, 280, -1));

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonGroup1.add(r_membaik);
        r_membaik.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        r_membaik.setText("Membaik");
        jPanel7.add(r_membaik, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        buttonGroup1.add(r_menurun);
        r_menurun.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        r_menurun.setText("Menurun");
        jPanel7.add(r_menurun, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        buttonGroup1.add(r_kritis);
        r_kritis.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        r_kritis.setText("Kritis");
        jPanel7.add(r_kritis, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        buttonGroup1.add(r_sembuh);
        r_sembuh.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        r_sembuh.setText("Sembuh");
        jPanel7.add(r_sembuh, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        buttonGroup1.add(r_belum);
        r_belum.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        r_belum.setText("Belum Ada Perkembangan");
        jPanel7.add(r_belum, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Status Perkembangan Pasien");
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 46));

        jPanel7.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 60));

        buttonGroup2.add(r_pulang);
        r_pulang.setText("Pulang");
        jPanel7.add(r_pulang, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jTabbedPane3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane3StateChanged(evt);
            }
        });

        tb_unit_detail.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        tb_unit_detail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tb_unit_detail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tb_unit_detailMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_unit_detailMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tb_unit_detail);

        jTabbedPane3.addTab("Input Data Tindakan", jScrollPane2);

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tb_unit_detail_history.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        tb_unit_detail_history.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_unit_detail_history.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tb_unit_detail_historyMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_unit_detail_historyMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_unit_detail_historyMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_unit_detail_history);

        jPanel6.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 880, 180));

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Dttgl1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Dttgl1.setDisplayFormat("yyyy/MM/dd");
        jPanel9.add(Dttgl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        Dttgl2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Dttgl2.setDisplayFormat("yyyy/MM/dd");
        jPanel9.add(Dttgl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, -1, -1));

        bt_cari_tgl_tindakan.setText("Cari");
        bt_cari_tgl_tindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cari_tgl_tindakanActionPerformed(evt);
            }
        });
        jPanel9.add(bt_cari_tgl_tindakan, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, -1, 30));

        jLabel12.setText("S/d");
        jPanel9.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, -1, -1));
        jPanel9.add(txt_cari_nama_tindakan, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 260, 30));

        jLabel13.setText("Cari Nama Tindakan");
        jPanel9.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, 150, -1));

        jPanel6.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 880, 50));

        lbl_petugas_history.setText("Petugas");
        jPanel6.add(lbl_petugas_history, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 330, 20));

        jLabel16.setText("Penginput Data :");
        jPanel6.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 50, 120, 20));

        jLabel15.setText("Petugas   :");
        jPanel6.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 100, 20));

        lbl_petugas_input_history.setText("Petugas Input");
        jPanel6.add(lbl_petugas_input_history, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 50, 290, 20));

        jTabbedPane3.addTab("History Tindakan Pasien", jPanel6);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(214, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Input Tindakan", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1))
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

    private void tb_cari_petugasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_cari_petugasMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = this.tb_cari_petugas.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {

                if (pilihcari == 1) {
                    setPJ(1, row);
                } else if (pilihcari == 2) {
                    setPJ(2, row);
                } // bt_cari_poli.requestFocus();

                if (pilihcari == 3) {
                    setPJTemplate(1, row);
                } else if (pilihcari == 4) {
                    setPJTemplate(1, row);
                } // bt
            }

            this.dlg_dpjp.setVisible(false);
        }
    }//GEN-LAST:event_tb_cari_petugasMouseReleased

    private void setPJ(int i, int row) {

//        txt_nip_template_dpjp.setText("");
//        this.txt_nama_dpjp_template.setText("");
//        txt_nip_template_ppjp.setText("");
//        this.txt_nama_ppjp_template.setText("");
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

            r_pulang.requestFocus();
        }

    }


    private void tb_cari_petugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_cari_petugasKeyPressed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int row = this.tb_cari_petugas.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {

                if (pilihcari == 1) {
                    setPJ(1, row);
                } else if (pilihcari == 2) {
                    setPJ(2, row);
                } // bt_cari_poli.requestFocus();

                if (pilihcari == 3) {
                    setPJTemplate(1, row);
                } else if (pilihcari == 4) {
                    setPJTemplate(1, row);
                } // bt_cari

            }

            this.dlg_dpjp.setVisible(false);
        }

    }//GEN-LAST:event_tb_cari_petugasKeyPressed

    private void setPJTemplate(int i, int row) {

//            txt_nip_dpjp.setText("");
//            this.txt_dpjp.setText("");
//         
//            txt_nip_ppjp.setText("");
//            this.txt_ppjp.setText("");
        if (i == 1) {
            lbl_nip_petugas_pilih.setText(this.tb_cari_petugas.getModel().getValueAt(row, 0).toString());
            txt_petugas_pilih.setText(this.tb_cari_petugas.getModel().getValueAt(row, 1).toString());
        }

    }


    private void txt_cari_petugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cari_petugasKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tb_cari_petugas.requestFocus();
            tb_cari_petugas.setRowSelectionInterval(0, 0);
        }
    }//GEN-LAST:event_txt_cari_petugasKeyPressed

    private void bt_cari_dpjpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_dpjpActionPerformed
        // TODO add your handling code here:

        pilihcari = 1;

        this.dlg_dpjp.setLocationRelativeTo(this);
        this.dlg_dpjp.setVisible(true);


    }//GEN-LAST:event_bt_cari_dpjpActionPerformed

    private void bt_cari_ppjpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_ppjpActionPerformed
        // TODO add your handling code here:

        pilihcari = 2;

        this.dlg_dpjp.setLocationRelativeTo(this);
        this.dlg_dpjp.setVisible(true);
        txt_cari_petugas.requestFocus();


    }//GEN-LAST:event_bt_cari_ppjpActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        // TODO add your handling code here:

        txt_cari_reg.requestFocus();

    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void hapusmodelunitdetail() {
        DefaultTableModel dm = (DefaultTableModel) tb_unit_detail.getModel();
        int rowCount = dm.getRowCount();
        if (rowCount != 0) {
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }
        }
    }

    
    private void cls(){
      
        Utilitas.HapusText(jPanel4);
        hapusmodelunitdetail();
        
    }
    private void saveTindakan() {
        DefaultTableModel dm = (DefaultTableModel) tb_unit_detail.getModel();
        int rowCount = dm.getRowCount();
        if (rowCount != 0) {

            try {
                datl = new Crud_local();
           
                if(tb_unit_detail_history.getModel().getRowCount()==0){
                   datl.Save_inapanakmaster(txt_no_rawat.getText(), txt_nip_dpjp.getText(), txt_nip_ppjp.getText(), r_belum.isSelected(), kondisipasien(),"perinatologi");
                }
                else{
                   
                   datl.Update_inapanakmaster(txt_no_rawat.getText(), txt_nip_dpjp.getText(), txt_nip_ppjp.getText(), r_belum.isSelected(), kondisipasien());
                   
                }
                 
            } catch (Exception ex) {
                Logger.getLogger(frm_poli.class.getName()).log(Level.SEVERE, null, ex);
            }
              

            //cek apa sudah di billing
              
            try {
                datl = new Crud_local();
                for (int i = rowCount - 1; i >= 0; i--) {
                datl.Save_inapanakDetail(dm.getValueAt(i, 0).toString(), dm.getValueAt(i, 1).toString()
                        , this.lbl_nip_petugas_pilih.getText(), lbl_petugas.getText());
                 }
                
                hapusmodelunitdetail();
            } catch (Exception ex) {
                Logger.getLogger(frm_poli.class.getName()).log(Level.SEVERE, null, ex);
            }
                
             
           
            
        }

    }

    private String kondisipasien() {
           
          String k = "";
        
                if (r_belum.isSelected()) {
                    k = r_belum.getText();
                } else if (r_membaik.isSelected()) {
                    k = r_membaik.getText();
                } else if (r_menurun.isSelected()) {
                    k = r_menurun.getText();
                }
                else if (r_kritis.isSelected()) {
                    k = r_kritis.getText();
                }
                else if (r_sembuh.isSelected()) {
                    k = r_sembuh.getText();
                }
            
            return k;
        }
    
    private void bt_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_saveActionPerformed
        // TODO add your handling code here:
        if (!(txt_no_rawat.getText().isEmpty() || txt_nip_dpjp.getText().isEmpty()
                || txt_nip_ppjp.getText().isEmpty())) {
//            if ( r_pulang.isSelected()) {
                if (r_belum.isSelected() || r_membaik.isSelected() || r_menurun.isSelected()
                        || r_kritis.isSelected() || r_sembuh.isSelected()) {
                    saveTindakan();
                }
//            } else {
//                JOptionPane.showMessageDialog(null, "Status Pasien Sembuh atau pulang Belum di Pilih!");
//            }
        } else {
            JOptionPane.showMessageDialog(null, "Data ada yang Kosong!");
        }
    }//GEN-LAST:event_bt_saveActionPerformed

    private void settbpilih() {
        try {
            datl = new Crud_local();

            datl.readRec_cariTarifTemplate("", false, 2, lbl_kelas.getText());

            this.tb_tindakan_pilih.setModel(datl.modeltariftemplate);

            setukurantbtindakanpilih();

        } catch (Exception ex) {
            Logger.getLogger(frm_poli.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void bt_cari_tindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_tindakanActionPerformed
        // TODO add your handling code here:
        pilihcari = 4;

        this.dlg_dpjp.setLocationRelativeTo(this);
        this.dlg_dpjp.setVisible(true);


    }//GEN-LAST:event_bt_cari_tindakanActionPerformed

    private void clsceklist(){
          for (int i = 0; i < this.tb_tindakan_pilih.getRowCount(); i++) {
                   tb_tindakan_pilih.setValueAt(false, i, 2);
          }
    }
    
    private void bt_add_tindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_add_tindakanActionPerformed
        // TODO add your handling code here:
        if(cekkasir()>0){
                  JOptionPane.showMessageDialog(null, "Tindakan Sudah Billing Tidak Bisa Di Proses!");
              }
           else{
            
        if (!this.lbl_nip_petugas_pilih.getText().isEmpty()) {
            tb_tindakan_pilih.requestFocus();
            tb_tindakan_pilih.setRowSelectionInterval(0, 0);

            int row = this.tb_tindakan_pilih.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {

                hapusmodelunitdetail();

                for (int i = 0; i < this.tb_tindakan_pilih.getRowCount(); i++) {

                    if (Boolean.valueOf(tb_tindakan_pilih.getValueAt(i, 2).toString()) == true) {

                        //sesuaiin row index pas udah sorter
                        int srow = tb_tindakan_pilih.convertRowIndexToModel(i);

                        modelunitdetail.addRow(new Object[]{this.txt_no_rawat.getText(),
                            tb_tindakan_pilih.getModel().getValueAt(srow, 0).toString(),
                            tb_tindakan_pilih.getModel().getValueAt(srow, 1).toString(),
                            txt_petugas_pilih.getText(), lbl_tgl_server.getText() + " " + lbl_jamnow.getText(),
                            lbl_nip_petugas_pilih.getText()});

                        tb_unit_detail.setModel(modelunitdetail);

                    }
                }

                clsceklist();
                
            } //end if

        } else {
            JOptionPane.showMessageDialog(null, "Petugas Belum di Input!");
        }
        setukurantbulunitdetail();
      }
    }//GEN-LAST:event_bt_add_tindakanActionPerformed

    private void bt_cari_tindakan_pilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_tindakan_pilihActionPerformed
        // TODO add your handling code here:
        filterceklistTemplatepilih();

    }//GEN-LAST:event_bt_cari_tindakan_pilihActionPerformed

    private void tb_tindakan_pilihMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_tindakan_pilihMouseClicked
        // TODO add your handling code here:
        if (txt_no_rawat.getText().isEmpty()) {
            int row = this.tb_tindakan_pilih.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                this.tb_tindakan_pilih.getModel().setValueAt(false, row, 2);
            }

            JOptionPane.showMessageDialog(null, "Data Pasien Belum di Pilih!");
            jTabbedPane2.setSelectedIndex(0);

        }

    }//GEN-LAST:event_tb_tindakan_pilihMouseClicked

    private void tb_tindakan_pilihMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_tindakan_pilihMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {

        }
    }//GEN-LAST:event_tb_tindakan_pilihMouseReleased

    private void lbl_cariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_cariMouseClicked
        // TODO add your handling code here:
        filterReg();
        this.filtertxt();
    }//GEN-LAST:event_lbl_cariMouseClicked

    private void txt_cari_regKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cari_regKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.filtertxt();
            this.tb_reg.requestFocus();
            //jtb_barang.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            tb_reg.setRowSelectionInterval(0, 0);

        }
    }//GEN-LAST:event_txt_cari_regKeyPressed

    private void setMasukdatInput(int row) {
        txt_no_rm.setText(this.tb_reg.getModel().getValueAt(row, 0).toString());
        this.txt_nm_pasien.setText(this.tb_reg.getModel().getValueAt(row, 1).toString());
        txt_no_rawat.setText(this.tb_reg.getModel().getValueAt(row, 2).toString());
        this.lbl_tgl_masuk.setText(this.tb_reg.getModel().getValueAt(row, 3).toString());
        lbl_kamar_inap.setText(this.tb_reg.getModel().getValueAt(row, 4).toString());
        this.lbl_kelas.setText(this.tb_reg.getModel().getValueAt(row, 5).toString());
        this.lbl_status.setText(this.tb_reg.getModel().getValueAt(row, 6).toString());
        this.lbl_nm_status.setText(this.tb_reg.getModel().getValueAt(row, 7).toString());
    }
 
  private void filterTindakanhistory(){
   if(!this.txt_no_rawat.getText().isEmpty()){   
     filterhistorytindakan(txt_cari_nama_tindakan.getText(),3);
   }
  }
    
  private void filterhistorytindakan(String txt,int i){
    
        try {
            datl=new Crud_local();
             String datePattern = "yyyy-MM-dd";
            SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
             datl.tgl1=dateFormatter.format(Dttgl1.getDate());
            datl.tgl2=dateFormatter.format(Dttgl2.getDate());
            
            datl.readRec_cariUnitDetailInapanak(txt, i);
        
            tb_unit_detail_history.setModel(datl.modelunitdetail);
            setukurantbulunitHistory();
        } catch (Exception ex) {
            Logger.getLogger(frm_poli.class.getName()).log(Level.SEVERE, null, ex);
        }
    
       
    }
    
    
    public void setnmnip(){
        try {
            datl=new Crud_local();
            datl.readRec_cariUnitMaster(txt_no_rawat.getText(), 3);
            txt_nip_dpjp.setText(datl.nipdpjp);
            txt_nip_ppjp.setText(datl.nippjp);
        } catch (Exception ex) {
            Logger.getLogger(frm_poli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //edit 26
    public void cariStatDPJPPPJP(){
        try {
            datl=new Crud_local();
            datl.readRec_cariUnitMaster(txt_no_rawat.getText(), 2);
      
             if(!datl.modelunitanakmaster.getValueAt(0, 8).toString().isEmpty()){  
            r_pulang.setSelected(Boolean.parseBoolean(datl.modelunitanakmaster.getValueAt(0, 8).toString()));
             }
          if(!datl.modelunitanakmaster.getValueAt(0, 9).toString().isEmpty()){  
            
            if(r_belum.getText().equals(datl.modelunitanakmaster.getValueAt(0, 9).toString())){
               r_belum.setSelected(true);
            }
            else if(r_membaik.getText().equals(datl.modelunitanakmaster.getValueAt(0, 9).toString())){
               r_membaik.setSelected(true);
            }
             else if(r_menurun.getText().equals(datl.modelunitanakmaster.getValueAt(0, 9).toString())){
               r_menurun.setSelected(true);
            }
            else if(r_kritis.getText().equals(datl.modelunitanakmaster.getValueAt(0, 9).toString())){
               r_kritis.setSelected(true);
            }
             else if(r_sembuh.getText().equals(datl.modelunitanakmaster.getValueAt(0, 9).toString())){
               r_sembuh.setSelected(true);
            }
          }
            
        } catch (Exception ex) {
            r_belum.setSelected(false);
            r_sembuh.setSelected(false);
            r_membaik.setSelected(false);
            r_menurun.setSelected(false);
            r_kritis.setSelected(false);
            r_sembuh.setSelected(false);
            
            Logger.getLogger(frm_poli.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        
        
        setnmnip();

       
        try {
            dat=new Crud_farmasi();
           
            txt_dpjp.setText( dat.readRec_CariPegawaiNM(txt_nip_dpjp.getText()));
            
        } catch (Exception ex) {
            Logger.getLogger(frm_poli.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        try {
            dat=new Crud_farmasi();
           
            txt_ppjp.setText( dat.readRec_CariPegawaiNM(txt_nip_ppjp.getText()));
            
        } catch (Exception ex) {
            Logger.getLogger(frm_poli.class.getName()).log(Level.SEVERE, null, ex);
        }
    
       
            
        
        
    }
    
    private void tb_regKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_regKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int row = this.tb_reg.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {

                setMasukdatInput(row);

                //txt_tarif.requestFocus();
                this.txt_cari_reg.requestFocus();
                settbpilih();

                if (tb_unit_detail.getModel().getRowCount() != 0) {

                    this.filterNorawat();
                }

                 filterhistorytindakan(txt_no_rawat.getText(),2);
                 
                 
                 cariStatDPJPPPJP();
                 
                 setukurantbulunitHistory();
            }

        }
    }//GEN-LAST:event_tb_regKeyPressed

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

    private void tb_regMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_regMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = this.tb_reg.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {

                setMasukdatInput(row);

                //txt_tarif.requestFocus();
                this.txt_cari_reg.requestFocus();

                settbpilih();

                if (tb_unit_detail.getModel().getRowCount() != 0) {

                    this.filterNorawat();
                }
                
                 filterhistorytindakan(txt_no_rawat.getText(),2);
                 
                 cariStatDPJPPPJP();
                 setukurantbulunitHistory();
            }
        }
    }//GEN-LAST:event_tb_regMouseReleased

    private void tb_unit_detailMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_unit_detailMouseReleased
        // TODO add your handling code here:

        if (evt.isPopupTrigger()) {

            int row = tb_unit_detail.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                this.Popup_hapus.show(tb_unit_detail, evt.getX(), evt.getY());
//                idhapusmaster = (jTable1.getModel().getValueAt(row, 0).toString());
                irowtindakandetail = row;
            }

        }


    }//GEN-LAST:event_tb_unit_detailMouseReleased

    private void tb_unit_detailMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_unit_detailMousePressed
        // TODO add your handling code here:
        if (evt.isPopupTrigger()) {

            int row = tb_unit_detail.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                this.Popup_hapus.show(tb_unit_detail, evt.getX(), evt.getY());
                irowtindakandetail = row;

            }

        }


    }//GEN-LAST:event_tb_unit_detailMousePressed

    private int cekkasir(){
        int a=0;
        try {
            datl=new Crud_local();
            
            
            a=datl.readRec_Hitkasir(this.txt_no_rawat.getText());
            System.out.println(a);
        } catch (Exception ex) {
            Logger.getLogger(frm_poli.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     return a;
    }
    
    private void HapusRowHistory(int i){
    
        DefaultTableModel dm = (DefaultTableModel) tb_unit_detail_history.getModel();
        int rowCount = dm.getRowCount();
        if (rowCount != 0) {
            try {
                if(cekkasir()>0){
                  JOptionPane.showMessageDialog(null, "Tindakan Sudah Billing Tidak Bisa DiHapus!");
                }
                else{
                datl=new Crud_local();
                datl.DelRecHistory(dm.getValueAt(i, 1).toString(),dm.getValueAt(i, 4).toString());
                dm.removeRow(i);
               
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Gagal Hapus!");
                Logger.getLogger(frm_poli.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    
    }
    
    private void HapusRowTindakan(int i) {
        DefaultTableModel dm = (DefaultTableModel) tb_unit_detail.getModel();
        int rowCount = dm.getRowCount();
        if (rowCount != 0) {

            dm.removeRow(i);

        }
    }

    private void mnu_item_hapus_tindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnu_item_hapus_tindakanActionPerformed
        // TODO add your handling code here:
        HapusRowTindakan(irowtindakandetail);

    }//GEN-LAST:event_mnu_item_hapus_tindakanActionPerformed

    
    
    private void tb_unit_detail_historyMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_unit_detail_historyMouseReleased
       if (evt.isPopupTrigger()) {

            int row = tb_unit_detail_history.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                this.Popup_history.show(tb_unit_detail_history, evt.getX(), evt.getY());
                irowhistory = row;

            }

        }
    }//GEN-LAST:event_tb_unit_detail_historyMouseReleased

    private void jTabbedPane3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane3StateChanged
        // TODO add your handling code here:
        
        if(!txt_no_rawat.getText().isEmpty())
        {
             filterhistorytindakan(txt_no_rawat.getText(),2);
           
                 setukurantbulunitHistory();
        }
     
    }//GEN-LAST:event_jTabbedPane3StateChanged

    private void tb_unit_detail_historyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_unit_detail_historyMouseClicked
        // TODO add your handling code here:
         int row = this.tb_unit_detail_history.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
               lbl_petugas_history.setText(tb_unit_detail_history.getModel().getValueAt(row, 3).toString());
               lbl_petugas_input_history.setText(tb_unit_detail_history.getModel().getValueAt(row, 5).toString());
               
            }
    }//GEN-LAST:event_tb_unit_detail_historyMouseClicked

    private void bt_cari_tgl_tindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_tgl_tindakanActionPerformed
        // TODO add your handling code here:
        filterhistorytindakan(txt_no_rawat.getText(),4);
    }//GEN-LAST:event_bt_cari_tgl_tindakanActionPerformed

    private void tb_unit_detail_historyMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_unit_detail_historyMousePressed
        // TODO add your handling code here:
         if (evt.isPopupTrigger()) {

            int row = tb_unit_detail_history.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                this.Popup_history.show(tb_unit_detail_history, evt.getX(), evt.getY());
                irowhistory = row;

            }

        }


    }//GEN-LAST:event_tb_unit_detail_historyMousePressed

    private void item_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_hapusActionPerformed
        // TODO add your handling code here:
        int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah Akan di Hapus Tindakan :  "+ tb_unit_detail_history.getModel().getValueAt(irowhistory, 2),"Warning ",
         JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
   
        if(dialogResult == JOptionPane.YES_OPTION){  
              this.HapusRowHistory(irowhistory);
              
        }
    }//GEN-LAST:event_item_hapusActionPerformed

    private void filterPegawai() {

        try {
            dat = new Crud_farmasi();

            try {
                dat.readRec_pegawaiF(txt_cari_petugas.getText());
            } catch (SQLException ex) {
                Logger.getLogger(frm_petugas_poli.class.getName()).log(Level.SEVERE, null, ex);
            }

            tb_cari_petugas.setModel(dat.modelpegawai);
            this.setukurantbcaripetugas();
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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_poli().setVisible(true);
            }
        });

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private uz.ncipro.calendar.JDateTimePicker Dttgl1;
    private uz.ncipro.calendar.JDateTimePicker Dttgl2;
    private javax.swing.JPopupMenu Popup_hapus;
    private javax.swing.JPopupMenu Popup_history;
    private javax.swing.JToolBar ToolBar;
    private javax.swing.JButton bt_add_tindakan;
    private javax.swing.JButton bt_cari_dpjp;
    private javax.swing.JButton bt_cari_ppjp;
    private javax.swing.JButton bt_cari_tgl_tindakan;
    private javax.swing.JButton bt_cari_tindakan;
    private javax.swing.JButton bt_cari_tindakan_pilih;
    private javax.swing.JButton bt_save;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JDialog dlg_dpjp;
    private javax.swing.JMenuItem item_hapus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JLabel lbl_cari;
    private javax.swing.JLabel lbl_jam;
    private javax.swing.JLabel lbl_jamnow;
    private javax.swing.JLabel lbl_kamar_inap;
    private javax.swing.JLabel lbl_kelas;
    private javax.swing.JLabel lbl_news;
    private javax.swing.JLabel lbl_nip_petugas_pilih;
    private javax.swing.JLabel lbl_nm_status;
    private javax.swing.JLabel lbl_petugas;
    private javax.swing.JLabel lbl_petugas_history;
    private javax.swing.JLabel lbl_petugas_input_history;
    private javax.swing.JLabel lbl_poli;
    private javax.swing.JLabel lbl_poli1;
    private javax.swing.JLabel lbl_status;
    private javax.swing.JLabel lbl_tgl_masuk;
    private javax.swing.JLabel lbl_tgl_server;
    private javax.swing.JMenuItem mnu_item_hapus_tindakan;
    private javax.swing.JRadioButton r_belum;
    private javax.swing.JRadioButton r_kritis;
    private javax.swing.JRadioButton r_membaik;
    private javax.swing.JRadioButton r_menurun;
    private javax.swing.JRadioButton r_pulang;
    private javax.swing.JRadioButton r_sembuh;
    private javax.swing.JTable tb_cari_petugas;
    private javax.swing.JTable tb_reg;
    private javax.swing.JTable tb_tindakan_pilih;
    private javax.swing.JTable tb_unit_detail;
    private javax.swing.JTable tb_unit_detail_history;
    private javax.swing.JTextField txt_cari_nama_tindakan;
    private javax.swing.JTextField txt_cari_petugas;
    private javax.swing.JTextField txt_cari_reg;
    private javax.swing.JTextField txt_cari_tindakan_pilih;
    private javax.swing.JTextField txt_dpjp;
    private javax.swing.JTextField txt_nip_dpjp;
    private javax.swing.JTextField txt_nip_ppjp;
    private javax.swing.JTextField txt_nm_pasien;
    private javax.swing.JTextField txt_no_rawat;
    private javax.swing.JTextField txt_no_rm;
    private javax.swing.JTextField txt_petugas_pilih;
    private javax.swing.JTextField txt_ppjp;
    // End of variables declaration//GEN-END:variables
}
