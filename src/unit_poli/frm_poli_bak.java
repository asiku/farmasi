/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit_poli;

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
public class frm_poli_bak extends javax.swing.JFrame {

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
    public frm_poli_bak() {
        initComponents();

    }

    public frm_poli_bak(String nmp, String poli) {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        refreshtbtemplatepilih("");

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

        txt_cari_tindakan_template.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

                filterceklistTemplate();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterceklistTemplate();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterceklistTemplate();
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

        settbTindakan();

        //settbpilih();
    }

    private void filterceklistTemplatepilih() {
        Utilitas.filtertb(txt_cari_tindakan_pilih.getText(), tb_tindakan_pilih, 1, 2);

    }

    private void filterceklistTemplate() {
        Utilitas.filtertb(txt_cari_tindakan_template.getText(), tb_tindakan, 1, 2);

    }

    private void settbTindakan() {

        try {
            datl = new Crud_local();

            datl.readRec_cariTarifTemplateP(txt_cari_tindakan_template.getText(), false, 1);

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
            Logger.getLogger(frm_poli_bak.class.getName()).log(Level.SEVERE, null, ex);
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

    private void hapuscolumntbilihtemplate() {

        for (int i = 3; i > 0; i--) {
            TableColumn col = tb_pilih_template.getColumnModel().getColumn(i);
            tb_pilih_template.getColumnModel().removeColumn(col);
        }

    }

    private void filterReg() {

        this.lbl_tgl_server.setText(Utilitas.getDateServer());

        if (!this.lbl_tgl_server.getText().isEmpty()) {
            try {

                dat = new Crud_farmasi();

                String[] b = {"ARJUNO", "MAHAMERU", "LEUSER", "RANAI", "PANGRANGO", "CADANGAN"};

                String t = lbl_tgl_server.getText().toString().substring(5, lbl_tgl_server.getText().length() - 3);

                dat.readRec_kamarinap(b, this.txt_cari_reg.getText(), t,this.lbl_tgl_server.getText());

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
                Logger.getLogger(frm_poli_bak.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(frm_poli_bak.class.getName()).log(Level.SEVERE, null, ex);
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
        tb_tindakan_pilih.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel tr = tb_tindakan_pilih.getColumnModel();

        tr.getColumn(0).setPreferredWidth(70);
        tr.getColumn(1).setPreferredWidth(280);
        tr.getColumn(2).setPreferredWidth(50);

    }

    private void setukurantbtindakan() {
        tb_tindakan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel tr = tb_tindakan.getColumnModel();

        tr.getColumn(0).setPreferredWidth(70);
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
        jPanel10 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tb_pilih_template = new javax.swing.JTable();
        txt_cari_tindakan_pilih1 = new javax.swing.JTextField();
        bt_cari_tindakan_pilih1 = new javax.swing.JButton();
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
        jLabel5 = new javax.swing.JLabel();
        lbl_nip_petugas_pilih = new javax.swing.JLabel();
        txt_petugas_pilih = new javax.swing.JTextField();
        bt_cari_tindakan = new javax.swing.JButton();
        lbl_kelas = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_unit_detail = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        bt_save = new javax.swing.JButton();
        bt_add = new javax.swing.JButton();
        bt_edit = new javax.swing.JButton();
        bt_hapus = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        txt_cari_tindakan_template = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tb_tindakan = new javax.swing.JTable();
        bt_cari_tindakan_template = new javax.swing.JButton();
        bt_clear_template = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_template = new javax.swing.JTable();
        bt_cek_satu = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txt_nama_template = new javax.swing.JTextField();
        bt_save_template = new javax.swing.JButton();
        txt_nip_template_dpjp = new javax.swing.JTextField();
        txt_nama_dpjp_template = new javax.swing.JTextField();
        Petugas = new javax.swing.JLabel();
        bt_cari_template_dpjp = new javax.swing.JButton();

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
                .addContainerGap(869, Short.MAX_VALUE))
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

        jPanel12.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 56, 420, 520));

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

        tb_pilih_template.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_pilih_template.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_pilih_templateMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tb_pilih_template);

        bt_cari_tindakan_pilih1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/carik_ico.png"))); // NOI18N
        bt_cari_tindakan_pilih1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cari_tindakan_pilih1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(txt_cari_tindakan_pilih1)
                .addGap(18, 18, 18)
                .addComponent(bt_cari_tindakan_pilih1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_cari_tindakan_pilih1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_cari_tindakan_pilih1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Data Template", jPanel10);

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

        buttonGroup2.add(r_perawatan);
        r_perawatan.setText("Perawatan");
        jPanel4.add(r_perawatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, -1, -1));

        buttonGroup2.add(r_pulang);
        r_pulang.setText("Pulang");
        jPanel4.add(r_pulang, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        jLabel5.setText("Kelas");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 190, 60, -1));

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

        tb_unit_detail.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        tb_unit_detail.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tb_unit_detail);

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bt_save.setText("Save");
        bt_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_saveActionPerformed(evt);
            }
        });
        jPanel8.add(bt_save, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 14, -1, -1));

        bt_add.setText("Add");
        jPanel8.add(bt_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(88, 14, -1, -1));

        bt_edit.setText("Edit");
        jPanel8.add(bt_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 14, -1, -1));

        bt_hapus.setText("Hapus");
        jPanel8.add(bt_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(228, 14, -1, -1));

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jRadioButton2.setText("Membaik");
        jPanel7.add(jRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 99, -1, -1));

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jRadioButton3.setText("Menurun");
        jPanel7.add(jRadioButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 124, -1, -1));

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jRadioButton4.setText("Kritis");
        jPanel7.add(jRadioButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 149, -1, -1));

        buttonGroup1.add(jRadioButton5);
        jRadioButton5.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jRadioButton5.setText("Sembuh");
        jPanel7.add(jRadioButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 174, -1, -1));

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jRadioButton1.setText("Belum Ada Perkembangan");
        jPanel7.add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 74, -1, -1));

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Status Perkembangan Pasien");
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 46));

        jPanel7.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 60));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(56, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Input Tindakan", jPanel1);

        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel9.add(txt_cari_tindakan_template, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 345, 33));

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

        jPanel9.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 407, 370));

        bt_cari_tindakan_template.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/carik_ico.png"))); // NOI18N
        bt_cari_tindakan_template.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cari_tindakan_templateActionPerformed(evt);
            }
        });
        jPanel9.add(bt_cari_tindakan_template, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 140, 46, 33));

        bt_clear_template.setText("x");
        bt_clear_template.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_clear_templateActionPerformed(evt);
            }
        });
        jPanel9.add(bt_clear_template, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 270, 54, -1));

        tb_template.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tb_template);

        jPanel9.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 170, 390, 390));

        bt_cek_satu.setText(">");
        bt_cek_satu.setPreferredSize(new java.awt.Dimension(54, 25));
        bt_cek_satu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cek_satuActionPerformed(evt);
            }
        });
        jPanel9.add(bt_cek_satu, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 230, -1, -1));

        jLabel9.setText("Nama Template");
        jPanel9.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 115, -1));

        txt_nama_template.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nama_templateKeyPressed(evt);
            }
        });
        jPanel9.add(txt_nama_template, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 295, 27));

        bt_save_template.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/save_ico.png"))); // NOI18N
        bt_save_template.setText("Save");
        bt_save_template.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_save_templateActionPerformed(evt);
            }
        });
        jPanel9.add(bt_save_template, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 110, 390, 40));

        txt_nip_template_dpjp.setEditable(false);
        txt_nip_template_dpjp.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jPanel9.add(txt_nip_template_dpjp, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 93, 27));

        txt_nama_dpjp_template.setEditable(false);
        txt_nama_dpjp_template.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jPanel9.add(txt_nama_dpjp_template, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 190, 27));

        Petugas.setText("Petugas");
        jPanel9.add(Petugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 96, -1));

        bt_cari_template_dpjp.setText("...");
        bt_cari_template_dpjp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cari_template_dpjpActionPerformed(evt);
            }
        });
        jPanel9.add(bt_cari_template_dpjp, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, 41, -1));

        jTabbedPane1.addTab("Buat Template Tindakan", jPanel9);

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
                this.lbl_kelas.setText(this.tb_reg.getModel().getValueAt(row, 5).toString());
                //txt_tarif.requestFocus();
                this.txt_cari_reg.requestFocus();
                 settbpilih();
            }

        }
    }//GEN-LAST:event_tb_regKeyPressed

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
                    setPJTemplate(2, row);
                } // bt_cari
            }

            this.dlg_dpjp.setVisible(false);
        }
    }//GEN-LAST:event_tb_cari_petugasMouseReleased

    private void setPJTemplate(int i, int row) {

//            txt_nip_dpjp.setText("");
//            this.txt_dpjp.setText("");
//         
//            txt_nip_ppjp.setText("");
//            this.txt_ppjp.setText("");
        if (i == 1) {
            txt_nip_template_dpjp.setText(this.tb_cari_petugas.getModel().getValueAt(row, 0).toString());
            this.txt_nama_dpjp_template.setText(this.tb_cari_petugas.getModel().getValueAt(row, 1).toString());
            this.dlg_dpjp.setVisible(false);

            txt_cari_petugas.setText("");
        } else {
            lbl_nip_petugas_pilih.setText(this.tb_cari_petugas.getModel().getValueAt(row, 0).toString());
            txt_petugas_pilih.setText(this.tb_cari_petugas.getModel().getValueAt(row, 1).toString());

        }

    }

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

            r_perawatan.requestFocus();
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
                    setPJTemplate(2, row);
                } // bt_cari

            }

            this.dlg_dpjp.setVisible(false);
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

        pilihcari = 1;

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
                this.lbl_kelas.setText(this.tb_reg.getModel().getValueAt(row, 5).toString());
                //txt_tarif.requestFocus();
                this.txt_cari_reg.requestFocus();
                
                   settbpilih();

            }
        }
    }//GEN-LAST:event_tb_regMouseReleased

    private void bt_cari_ppjpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_ppjpActionPerformed
        // TODO add your handling code here:

        pilihcari = 2;

        this.dlg_dpjp.setLocationRelativeTo(this);
        this.dlg_dpjp.setVisible(true);
        txt_cari_petugas.requestFocus();


    }//GEN-LAST:event_bt_cari_ppjpActionPerformed

    private void bt_cari_tindakan_templateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_tindakan_templateActionPerformed
        // TODO add your handling code here:
        Utilitas.filtertb(txt_cari_tindakan_template.getText(), tb_tindakan, 1, 2);
    }//GEN-LAST:event_bt_cari_tindakan_templateActionPerformed

    private void bt_cari_template_dpjpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_template_dpjpActionPerformed
        // TODO add your handling code here:
        pilihcari = 3;

        this.dlg_dpjp.setLocationRelativeTo(this);
        this.dlg_dpjp.setVisible(true);
    }//GEN-LAST:event_bt_cari_template_dpjpActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        // TODO add your handling code here:
        refreshtbtemplatepilih("");

        if (jTabbedPane1.getSelectedIndex() == 1) {
            txt_nama_template.requestFocus();
        } else {
            txt_cari_reg.requestFocus();
        }
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void txt_nama_templateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nama_templateKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.bt_cari_template_dpjp.requestFocus();
        }
    }//GEN-LAST:event_txt_nama_templateKeyPressed

    private void hapusmodeltemplate() {
        DefaultTableModel dm = (DefaultTableModel) tb_template.getModel();
        int rowCount = dm.getRowCount();
        if (rowCount != 0) {
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }
        }
    }

    private void hapusmodelunitdetail() {
        DefaultTableModel dm = (DefaultTableModel) tb_unit_detail.getModel();
        int rowCount = dm.getRowCount();
        if (rowCount != 0) {
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }
        }
    }


    private void bt_cek_satuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cek_satuActionPerformed
        // TODO add your handling code here:

        if (!this.txt_nama_template.getText().isEmpty()) {

            hapusmodeltemplate();

            for (int i = 0; i < this.tb_tindakan.getModel().getRowCount(); i++) {

                if (this.tb_tindakan.getModel().getValueAt(i, 2).equals(true)) {
                    this.modeltemplatedetail.addRow(new Object[]{txt_nama_template.getText(),
                        tb_tindakan.getModel().getValueAt(i, 0).toString(), tb_tindakan.getModel().getValueAt(i, 1).toString()
                    });
                }
                this.tb_template.setModel(modeltemplatedetail);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Inputan Template Kosong!");
            txt_nama_template.requestFocus();
        }
    }//GEN-LAST:event_bt_cek_satuActionPerformed

    private void bt_clear_templateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_clear_templateActionPerformed
        // TODO add your handling code here:
        hapusmodeltemplate();
    }//GEN-LAST:event_bt_clear_templateActionPerformed

    private void SaveMastertemplate() {
        try {
            datl = new Crud_local();
            if (!txt_nip_template_dpjp.getText().isEmpty()) {
                datl.Save_mastertemplate(txt_nama_template.getText(), txt_nip_template_dpjp.getText(), txt_nama_dpjp_template.getText(), "Dokter");
            } else {
                datl.Save_mastertemplate(txt_nama_template.getText(), txt_nip_template_dpjp.getText(), txt_nama_dpjp_template.getText(), "Petugas");
            }
            for (int i = 0; i < this.tb_template.getModel().getRowCount(); i++) {
                datl.Save_mastertemplatedetail(txt_nama_template.getText(), this.tb_template.getModel().getValueAt(i, 1).toString(), this.tb_template.getModel().getValueAt(i, 2).toString());
            }
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_bak.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void refreshtbtemplatepilih(String txt) {
        try {
            datl = new Crud_local();
            datl.readRec_cariMasterTemplate(txt, 1);

            tb_pilih_template.setModel(datl.modeltemplatemaster);

            this.hapuscolumntbilihtemplate();

        } catch (Exception ex) {
            Logger.getLogger(frm_poli_bak.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private void refreshtbtemplate(String txt){
//        try {
//            datl=new Crud_local();
//            datl.readRec_cariMasterTemplate(txt, 2);
//           // tb_master_template.setModel(datl.modeltemplatemaster);
//            
//        } catch (Exception ex) {
//            Logger.getLogger(frm_poli.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    private void bt_save_templateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_save_templateActionPerformed
        // TODO add your handling code here:
        if (!this.txt_nama_template.getText().isEmpty()) {
            if (!this.txt_nip_template_dpjp.getText().isEmpty()) {

                if (tb_template.getModel().getRowCount() != 0) {
                    //save laaaah
                    SaveMastertemplate();

                    refreshtbtemplatepilih("");

                } else {
                    JOptionPane.showMessageDialog(null, "Inputan Table Tindakan Kosong!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Inputan Dokter/Petugas  Kosong!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Inputan Nama Template Kosong!");
        }
    }//GEN-LAST:event_bt_save_templateActionPerformed

    private void bt_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_saveActionPerformed
        // TODO add your handling code here:
        if (!(txt_no_rawat.getText().isEmpty() || txt_nip_dpjp.getText().isEmpty()
                || txt_nip_ppjp.getText().isEmpty())) {
            if (r_perawatan.isSelected() || r_pulang.isSelected()) {

            } else {
                JOptionPane.showMessageDialog(null, "Status Pasien Sembuh atau pulang Belum di Pilih!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Data ada yang Kosong!");
        }
    }//GEN-LAST:event_bt_saveActionPerformed

    private void pilitemplate() {

        hapusmodelunitdetail();

        int row = this.tb_pilih_template.getSelectedRow();

        if (row == -1) {
            // No row selected
        } else {
            try {
                lbl_nip_petugas_pilih.setText(tb_pilih_template.getModel().getValueAt(row, 1).toString());
                txt_petugas_pilih.setText(tb_pilih_template.getModel().getValueAt(row, 2).toString());

                datl = new Crud_local();
                datl.readRec_cariMasterTemplateDetail(tb_pilih_template.getModel().getValueAt(row, 0).toString());
                //readRec_cariMasterTemplateDetail

                for (int i = 0; i < datl.modeltemplatemasterdetail.getRowCount(); i++) {
                    modelunitdetail.addRow(new Object[]{this.txt_no_rawat.getText(),
                        datl.modeltemplatemasterdetail.getValueAt(i, 1).toString(),
                        datl.modeltemplatemasterdetail.getValueAt(i, 2).toString(),
                        txt_petugas_pilih.getText(), lbl_tgl_server.getText() + " " + lbl_jamnow.getText(),
                        lbl_nip_petugas_pilih.getText()
                    });
                }

                tb_unit_detail.setModel(modelunitdetail);

            } catch (Exception ex) {
                Logger.getLogger(frm_poli_bak.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void tb_pilih_templateMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_pilih_templateMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            if (!txt_no_rawat.getText().isEmpty()) {
                pilitemplate();
            } else {
                JOptionPane.showMessageDialog(null, "Maaf anda Belum Pilih Pasien!");
            }
        }
    }//GEN-LAST:event_tb_pilih_templateMouseReleased

    private void settbpilih() {
        try {
            datl = new Crud_local();

            datl.readRec_cariTarifTemplate("", false, 1,lbl_kelas.getText());

            this.tb_tindakan_pilih.setModel(datl.modeltariftemplate);

            setukurantbtindakanpilih();

        } catch (Exception ex) {
            Logger.getLogger(frm_poli_bak.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void bt_cari_tindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_tindakanActionPerformed
        // TODO add your handling code here:
        pilihcari = 4;

        this.dlg_dpjp.setLocationRelativeTo(this);
        this.dlg_dpjp.setVisible(true);


    }//GEN-LAST:event_bt_cari_tindakanActionPerformed

    private void bt_cari_tindakan_pilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_tindakan_pilihActionPerformed
        // TODO add your handling code here:
        filterceklistTemplatepilih();


    }//GEN-LAST:event_bt_cari_tindakan_pilihActionPerformed

    private void tb_tindakan_pilihMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_tindakan_pilihMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {

        }
    }//GEN-LAST:event_tb_tindakan_pilihMouseReleased

    private void tb_tindakan_pilihMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_tindakan_pilihMouseClicked
        // TODO add your handling code here:
        if(txt_no_rawat.getText().isEmpty()){
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

    private void bt_cari_tindakan_pilih1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_tindakan_pilih1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_cari_tindakan_pilih1ActionPerformed

    private void bt_add_tindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_add_tindakanActionPerformed
        // TODO add your handling code here:

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
       

            } //end if

        } else {
          JOptionPane.showMessageDialog(null, "Petugas Belum di Input!");
        }
        setukurantbulunitdetail();
    }//GEN-LAST:event_bt_add_tindakanActionPerformed

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
            java.util.logging.Logger.getLogger(frm_poli_bak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_poli_bak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_poli_bak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_poli_bak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_poli_bak().setVisible(true);
            }
        });

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Petugas;
    private javax.swing.JToolBar ToolBar;
    private javax.swing.JButton bt_add;
    private javax.swing.JButton bt_add_tindakan;
    private javax.swing.JButton bt_cari_dpjp;
    private javax.swing.JButton bt_cari_ppjp;
    private javax.swing.JButton bt_cari_template_dpjp;
    private javax.swing.JButton bt_cari_tindakan;
    private javax.swing.JButton bt_cari_tindakan_pilih;
    private javax.swing.JButton bt_cari_tindakan_pilih1;
    private javax.swing.JButton bt_cari_tindakan_template;
    private javax.swing.JButton bt_cek_satu;
    private javax.swing.JButton bt_clear_template;
    private javax.swing.JButton bt_edit;
    private javax.swing.JButton bt_hapus;
    private javax.swing.JButton bt_save;
    private javax.swing.JButton bt_save_template;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JDialog dlg_dpjp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
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
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lbl_cari;
    private javax.swing.JLabel lbl_jam;
    private javax.swing.JLabel lbl_jamnow;
    private javax.swing.JLabel lbl_kamar_inap;
    private javax.swing.JLabel lbl_kelas;
    private javax.swing.JLabel lbl_news;
    private javax.swing.JLabel lbl_nip_petugas_pilih;
    private javax.swing.JLabel lbl_petugas;
    private javax.swing.JLabel lbl_poli;
    private javax.swing.JLabel lbl_poli1;
    private javax.swing.JLabel lbl_tgl_masuk;
    private javax.swing.JLabel lbl_tgl_server;
    private javax.swing.JRadioButton r_perawatan;
    private javax.swing.JRadioButton r_pulang;
    private javax.swing.JTable tb_cari_petugas;
    private javax.swing.JTable tb_pilih_template;
    private javax.swing.JTable tb_reg;
    private javax.swing.JTable tb_template;
    private javax.swing.JTable tb_tindakan;
    private javax.swing.JTable tb_tindakan_pilih;
    private javax.swing.JTable tb_unit_detail;
    private javax.swing.JTextField txt_cari_petugas;
    private javax.swing.JTextField txt_cari_reg;
    private javax.swing.JTextField txt_cari_tindakan_pilih;
    private javax.swing.JTextField txt_cari_tindakan_pilih1;
    private javax.swing.JTextField txt_cari_tindakan_template;
    private javax.swing.JTextField txt_dpjp;
    private javax.swing.JTextField txt_nama_dpjp_template;
    private javax.swing.JTextField txt_nama_template;
    private javax.swing.JTextField txt_nip_dpjp;
    private javax.swing.JTextField txt_nip_ppjp;
    private javax.swing.JTextField txt_nip_template_dpjp;
    private javax.swing.JTextField txt_nm_pasien;
    private javax.swing.JTextField txt_no_rawat;
    private javax.swing.JTextField txt_no_rm;
    private javax.swing.JTextField txt_petugas_pilih;
    private javax.swing.JTextField txt_ppjp;
    // End of variables declaration//GEN-END:variables
}
