/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasir;
import java.util.Calendar;
import java.util.Date;

import farmasi.Crud_farmasi;
import farmasi.Crud_local;
import farmasi.helper_kamar_inap;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Timer;
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
public class frm_poli_ralan extends javax.swing.JFrame {

    private  int nilai_jam = 0;
    private  int nilai_menit = 0;
    private  int nilai_detik = 0;
    
    
    private int irowtindakandetail = 0;
    private int irowhistory = 0;

    private int hitcol=0;   
     
    private ScheduledExecutorService executor;

    private Crud_farmasi dat;

    private Crud_local datl;

    private int pilihcari;

    
    String[] biaya_title = new String[]{"Nama Tagihan", "Tarif"};
    
    String[] biaya_titlelab = new String[]{"Kode Lab","Tagihan", "Tarif"};
    
    
    String[] template_title = new String[]{"Nama Template", "Petugas", "Status", "Tanggal"};
    String[] template_detail = new String[]{"Nama Template", "Kode Tindakan", "Tindakan"};

    String[] unit_detail = new String[]{"No Rawat", "Kode Tindakan", "Tindakan", "Petugas", "Tgl Tindakan", "Nip Petugas"};

    
    String[] template_pelayanan = new String[]{"Pelayanan Kamar", "Tarif","lama", "Total"};
    
    
      
    DefaultTableModel modelpelayanan = new DefaultTableModel(template_pelayanan, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };
    
    
    DefaultTableModel modelbiayalab = new DefaultTableModel(biaya_titlelab, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };
    
    DefaultTableModel modelbiaya = new DefaultTableModel(biaya_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };
    
    
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
    public frm_poli_ralan() {
        initComponents();

    }

    public frm_poli_ralan(String nmp, String poli) {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        jLabel23.setVisible(false);
        
        lbl_stat_pasien.setVisible(false);
//        jLabel7.setVisible(false);
        
        jLabel10.setVisible(false);
        lbl_nip_petugas_pilih.setVisible(false);
        txt_petugas_pilih.setVisible(false);
        
        bt_cari_tindakan.setVisible(false);
        
        this.lbl_petugas.setText(nmp);
        this.lbl_poli.setText(poli);

        jPanel2.setPreferredSize(new Dimension(this.ToolBar.getWidth() + 200, 72));
        LoopTgl();

        filterReg();

        filterRegInap();

        txt_cari_rm.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtertxtRM();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtertxtRM();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtertxtRM();
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

        txt_cari_reg_ranap.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               filtertxtRanap();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               filtertxtRanap();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               
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
//    t        Utilitas.filtertb(txt_no_rawat.getText(), tb_biaya_tindakan, 0, 2);

    }

    private void filterceklistTemplatepilih() {
//      Utilitas.filtertb(txt_cari_tindakan_pilih.getText(), tb_tindakan_pilih, 1, 1);
      
      

    }

    private void filtertxtRM() {

      Utilitas.filtertb(txt_cari_rm.getText(), tb_reg, 1,1);
    }
    
   private void filtertxtRanap() {

      Utilitas.filtertb(txt_cari_reg_ranap.getText(), tb_reg_inap, 1,1);
    } 
    
    private void filtertxt() {

// try {
//            //                   Utilitas.filtertb(txt_cari_reg.getText(), tb_reg, 2,2);
//
//            dat = new Crud_farmasi();
//            dat.readRec_registrasiRalanFisio(this.lbl_tgl_server.getText(),txt_cari_reg.getText(),2);
//            
//            this.tb_reg.setModel(dat.modelregralan);
//
//            setukurantbReg();
//            
//        } catch (Exception ex) {
//            Logger.getLogger(unit_fisio.frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
//        }

 try {

                dat = new Crud_farmasi();

                String datePattern = "yyyy-MM-dd";
                SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
                //dateFormatter.format(dt_tgl_reg.getDate())
                dat.readRec_registrasiKasir(this.lbl_tgl_server.getText(),txt_cari_reg.getText(),2);
                
                this.tb_reg.setModel(dat.modelregralankasir);

                setukurantbReg();
                lbl_stat_tgl.setText("Hasil Pencarian Pasien TGl: "+this.lbl_tgl_server.getText());
                
                dat.CloseCon();
                
    
            } catch (SQLException ex) {
                Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
            }
       
    }

    private void setukurantbRegInap() {

        this.tb_reg_inap.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        tb_reg_inap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tri = this.tb_reg_inap.getColumnModel();

        tri.getColumn(0).setPreferredWidth(70);
        tri.getColumn(1).setPreferredWidth(285);
        tri.getColumn(2).setPreferredWidth(100);
        tri.getColumn(3).setPreferredWidth(220);
        tri.getColumn(4).setPreferredWidth(150);
    }

    
    public void hitung_lamainap(){
    
      
//    Utilitas.Hitungtgl(tglserver,Utilitas.Jam(),resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK), Utilitas.Jam())<=744
    
    }
    
    
      private void filterRegInap() {

        this.lbl_tgl_server.setText(Utilitas.getDateServer());

        if (!this.lbl_tgl_server.getText().isEmpty()) {
            try {

                dat = new Crud_farmasi();

                String[] b = {"BROMO", "CIREMAI", "KERINCI", "KRAKATAU", "MALABAR","PAPANDAYAN","RAKATA","RINJANI","SEMERU","CADANGAN","BROMO BPJS", "CIREMAI BPJS", "KERINCI BPJS", "KRAKATAU BPJS", "MALABAR BPJS","PAPANDAYAN BPJS","RAKATA BPJS","RINJANI BPJS","SEMERU BPJS","CADANGAN BPJS","KAMAR ODC IBU","RUANG BERSALIN","RUANG BERSALIN BPJS"};

                String t = lbl_tgl_server.getText().toString().substring(5, lbl_tgl_server.getText().length() - 3);

                dat.readRec_kamarinapFisio(b, this.txt_cari_reg.getText(), t, this.lbl_tgl_server.getText());

                this.tb_reg_inap.setModel(dat.modelkamarinap);

                setukurantbRegInap();

                //             DefaultTableModel dm = (DefaultTableModel) tb_reg.getModel();
//                int rowCount = dm.getRowCount();
//
//                for (int i = rowCount - 1; i >= 0; i--) {
//                    if (!dm.getValueAt(i, 3).toString().substring(5, dm.getValueAt(i, 3).toString().length() - 3).equals(t)) {
//                        dm.removeRow(i);
//                    }
//                }
            } catch (SQLException ex) {
                Logger.getLogger(unit_poli_dewasa.frm_poli.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(unit_poli_dewasa.frm_poli.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "SerVer tidak Terkoneksi");

        }
    }

    
    private void filterReg() {

        this.lbl_tgl_server.setText(Utilitas.getDateServer());

        if (!this.lbl_tgl_server.getText().isEmpty()) {
            try {

                dat = new Crud_farmasi();

                String datePattern = "yyyy-MM-dd";
                SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
                //dateFormatter.format(dt_tgl_reg.getDate())
                dat.readRec_registrasiKasir(this.lbl_tgl_server.getText(),txt_cari_reg.getText(),1);
                
                this.tb_reg.setModel(dat.modelregralankasir);

                setukurantbReg();
                lbl_stat_tgl.setText("Hasil Pencarian Pasien TGl: "+this.lbl_tgl_server.getText());
                dat.CloseCon();
    
            } catch (SQLException ex) {
                Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
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

    
    private void setukurantb_biaya_inap() {

        this.tb_biaya_inap.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        tb_biaya_inap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tr = this.tb_biaya_inap.getColumnModel();

        tr.getColumn(0).setPreferredWidth(140);
        tr.getColumn(1).setPreferredWidth(70);
        tr.getColumn(2).setPreferredWidth(70);
        tr.getColumn(3).setPreferredWidth(80);
        tr.getColumn(4).setPreferredWidth(70);
        tr.getColumn(5).setPreferredWidth(80);
        tr.getColumn(6).setPreferredWidth(70);
        tr.getColumn(7).setPreferredWidth(70);
        tr.getColumn(8).setPreferredWidth(0);
        tr.getColumn(9).setPreferredWidth(0);

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

        this.tb_biaya_tindakan.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        tb_biaya_tindakan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tr = this.tb_biaya_tindakan.getColumnModel();

        tr.getColumn(0).setPreferredWidth(120);
        tr.getColumn(1).setPreferredWidth(100);
        tr.getColumn(2).setPreferredWidth(310);
        tr.getColumn(3).setPreferredWidth(220);
        tr.getColumn(4).setPreferredWidth(150);

    }

    

    private void setukurantbReg() {
        this.tb_reg.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        tb_reg.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel tr = tb_reg.getColumnModel();

        tr.getColumn(0).setPreferredWidth(50);
        tr.getColumn(1).setPreferredWidth(71);
        tr.getColumn(2).setPreferredWidth(328);
        tr.getColumn(3).setPreferredWidth(100);
        tr.getColumn(4).setPreferredWidth(100);
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
        bt_save = new javax.swing.JButton();
        bt_save2 = new javax.swing.JButton();
        lbl_nota = new javax.swing.JLabel();
        bt_nota = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tb_reg = new javax.swing.JTable();
        txt_cari_reg = new javax.swing.JTextField();
        lbl_cari = new javax.swing.JLabel();
        dt_tgl_reg = new uz.ncipro.calendar.JDateTimePicker();
        bt_cari_tgl = new javax.swing.JButton();
        lbl_stat_tgl = new javax.swing.JLabel();
        dt_tgl_reg1 = new uz.ncipro.calendar.JDateTimePicker();
        jLabel1 = new javax.swing.JLabel();
        txt_cari_rm = new javax.swing.JTextField();
        lbl_cari1 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tb_reg_inap = new javax.swing.JTable();
        txt_cari_reg_ranap = new javax.swing.JTextField();
        lbl_cari2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txt_nm_pasien = new javax.swing.JTextField();
        txt_no_rm = new javax.swing.JTextField();
        txt_no_rawat = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbl_tgl_masuk = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbl_nm_status = new javax.swing.JLabel();
        lbl_status = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbl_kode_poli = new javax.swing.JLabel();
        lbl_nm_poli = new javax.swing.JLabel();
        lbl_no_sep = new javax.swing.JLabel();
        lbl = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbl_kelas = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lbl_kamar_inap = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lbl_biaya_reg = new javax.swing.JLabel();
        lbl_total_tagihan2 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbl_biaya_reg1 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lbl_biaya_reg2 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txt_bayar1 = new javax.swing.JTextField();
        txt_bayar2 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txt_bayar3 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        lbl_total_tagihan3 = new javax.swing.JLabel();
        txt_bayar4 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tb_biaya_tindakan = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        lbl_tot_tindakan = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_biaya_inap = new javax.swing.JTable();
        CmbJam = new javax.swing.JComboBox<>();
        CmbMenit = new javax.swing.JComboBox<>();
        CmbDetik = new javax.swing.JComboBox<>();
        dt_tgl_reg2 = new uz.ncipro.calendar.JDateTimePicker();
        ChkJln = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        bt_proses = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tb_jasa_pelayanan = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tb_lab = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        lbl_tot_lab = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        lbl_tot_lab1 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tb_lab1 = new javax.swing.JTable();
        txt_plafon_bpjs1 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txt_plafon_bpjs = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        bt_save_deposit_plafon_bpjs = new javax.swing.JButton();
        bt_save1 = new javax.swing.JButton();
        lbl_total_tagihan4 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
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
        jLabel10 = new javax.swing.JLabel();
        lbl_nip_petugas_pilih = new javax.swing.JLabel();
        txt_petugas_pilih = new javax.swing.JLabel();
        lbl_stat_pasien = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        bt_cari_tindakan = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();

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

        bt_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kasir/kasir_kecil_ico.png"))); // NOI18N
        bt_save.setText("Print Kwitansi");
        bt_save.setBorder(null);
        bt_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_saveActionPerformed(evt);
            }
        });

        bt_save2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kasir/kasir_kecil_ico.png"))); // NOI18N
        bt_save2.setText("Print Rincian");
        bt_save2.setBorder(null);
        bt_save2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_save2ActionPerformed(evt);
            }
        });

        lbl_nota.setBackground(new java.awt.Color(102, 102, 102));

        bt_nota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli_dewasa/edit_ic.png"))); // NOI18N
        bt_nota.setText("Add Nota");
        bt_nota.setBorder(null);
        bt_nota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_notaActionPerformed(evt);
            }
        });

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
                .addGap(42, 42, 42)
                .addComponent(lbl_nota, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_nota, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bt_save, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_save2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(918, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_tgl_server)
                    .addComponent(lbl_jamnow))
                .addGap(22, 22, 22))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bt_save, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bt_save2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_nota, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bt_nota, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lbl_news)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_petugas)
                            .addComponent(lbl_jam)
                            .addComponent(lbl_poli)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(lbl_poli1)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ToolBar.add(jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tb_reg.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_regMouseClicked(evt);
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

        jPanel12.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 360, 480));

        txt_cari_reg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cari_regActionPerformed(evt);
            }
        });
        txt_cari_reg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cari_regKeyPressed(evt);
            }
        });
        jPanel12.add(txt_cari_reg, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 140, 32));

        lbl_cari.setText("Cari Nama");
        lbl_cari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_cariMouseClicked(evt);
            }
        });
        jPanel12.add(lbl_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 80, -1));

        dt_tgl_reg.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dt_tgl_reg.setDisplayFormat("yyyy/MM/dd");
        dt_tgl_reg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dt_tgl_regActionPerformed(evt);
            }
        });
        jPanel12.add(dt_tgl_reg, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 120, 20));

        bt_cari_tgl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/carik_ico.png"))); // NOI18N
        bt_cari_tgl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cari_tglActionPerformed(evt);
            }
        });
        jPanel12.add(bt_cari_tgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 60, 40));

        lbl_stat_tgl.setText("Hasil Pencarian pasien TGL :");
        jPanel12.add(lbl_stat_tgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 400, -1));

        dt_tgl_reg1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dt_tgl_reg1.setDisplayFormat("yyyy/MM/dd");
        dt_tgl_reg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dt_tgl_reg1ActionPerformed(evt);
            }
        });
        jPanel12.add(dt_tgl_reg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 120, 20));

        jLabel1.setText("S/d");
        jPanel12.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, -1, -1));

        txt_cari_rm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cari_rmActionPerformed(evt);
            }
        });
        jPanel12.add(txt_cari_rm, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 60, 30));

        lbl_cari1.setText("Cari RM");
        lbl_cari1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_cari1MouseClicked(evt);
            }
        });
        jPanel12.add(lbl_cari1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 60, -1));

        jTabbedPane2.addTab("Data Pasien Ralan", jPanel12);

        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tb_reg_inap.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        tb_reg_inap.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_reg_inap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_reg_inapMouseReleased(evt);
            }
        });
        tb_reg_inap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tb_reg_inapKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tb_reg_inapKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tb_reg_inap);

        jPanel13.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 56, 360, 520));

        txt_cari_reg_ranap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cari_reg_ranapKeyPressed(evt);
            }
        });
        jPanel13.add(txt_cari_reg_ranap, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 310, 32));

        lbl_cari2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/carik_ico.png"))); // NOI18N
        lbl_cari2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_cari2MouseClicked(evt);
            }
        });
        jPanel13.add(lbl_cari2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 40, -1));

        jTabbedPane2.addTab("Data Pasien Ranap", jPanel13);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
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
        jPanel4.add(txt_nm_pasien, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 131, 290, -1));

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

        lbl_tgl_masuk.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_tgl_masuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 132, 19));

        jLabel5.setText("Status");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 60, -1));

        lbl_nm_status.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_nm_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 170, 19));

        lbl_status.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 60, 19));

        jLabel6.setText("Poli");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 60, -1));

        lbl_kode_poli.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_kode_poli, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, 60, 19));

        lbl_nm_poli.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_nm_poli, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, 210, 19));

        lbl_no_sep.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_no_sep, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 150, 170, 19));

        lbl.setText("No. SEP");
        jPanel4.add(lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 130, 130, -1));

        jLabel14.setText("Kelas");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 180, 60, -1));

        lbl_kelas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_kelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 180, 150, 19));

        jLabel17.setText("Kamar");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 210, 60, -1));

        lbl_kamar_inap.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_kamar_inap, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 210, 180, 19));

        jLabel21.setText("Tgl Registrasi");
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 108, -1));

        jLabel18.setText("Biaya Regist.");
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, -1, -1));

        lbl_biaya_reg.setText("0");
        jPanel4.add(lbl_biaya_reg, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, 90, 30));

        lbl_total_tagihan2.setText("0");
        jPanel4.add(lbl_total_tagihan2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 30, 110, 30));

        jLabel19.setText("Jasa Pelayanan");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, -1, -1));

        jLabel9.setText("Tagihan Kamar");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 10, -1, -1));

        lbl_biaya_reg1.setText("0");
        jPanel4.add(lbl_biaya_reg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 30, 120, 30));

        jLabel28.setText("Biaya Adm. Ranap");
        jPanel4.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, -1, -1));

        lbl_biaya_reg2.setText("0");
        jPanel4.add(lbl_biaya_reg2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 80, 80, 30));

        jLabel29.setText("Cash");
        jPanel4.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 60, -1, -1));
        jPanel4.add(txt_bayar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 80, 180, 30));
        jPanel4.add(txt_bayar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 130, 170, 30));

        jLabel30.setText("Kartu Kredit");
        jPanel4.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 110, 90, -1));

        jLabel31.setText("Kartu Debet");
        jPanel4.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 110, -1, -1));
        jPanel4.add(txt_bayar3, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 130, 180, 30));

        jLabel20.setText("Total Tagihan");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 170, 130, -1));

        lbl_total_tagihan3.setText("0");
        jPanel4.add(lbl_total_tagihan3, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 190, 180, 30));
        jPanel4.add(txt_bayar4, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 190, 170, 30));

        jLabel33.setText("Asuransi");
        jPanel4.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 170, 90, -1));

        jTabbedPane3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane3StateChanged(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tb_biaya_tindakan.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        tb_biaya_tindakan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jScrollPane9.setViewportView(tb_biaya_tindakan);

        jLabel25.setText("Total          Rp.");

        lbl_tot_tindakan.setText("0");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(0, 354, Short.MAX_VALUE)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_tot_tindakan, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(lbl_tot_tindakan))
                .addContainerGap(232, Short.MAX_VALUE))
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                    .addContainerGap(40, Short.MAX_VALUE)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane4.addTab("Tagihan Tindakan", jPanel14);

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tb_biaya_inap.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        tb_biaya_inap.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tb_biaya_inap);

        jPanel10.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 690, 80));

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jPanel10.add(CmbJam, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, -1, -1));

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jPanel10.add(CmbMenit, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, -1, -1));

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jPanel10.add(CmbDetik, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, -1, -1));

        dt_tgl_reg2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dt_tgl_reg2.setDisplayFormat("yyyy/MM/dd");
        dt_tgl_reg2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dt_tgl_reg2ActionPerformed(evt);
            }
        });
        jPanel10.add(dt_tgl_reg2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 140, 20));

        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        jPanel10.add(ChkJln, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, -1, -1));

        jLabel11.setText("Tgl Pulang dan Jam Pulang Ranap");
        jPanel10.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 290, -1));

        bt_proses.setText("Proses");
        bt_proses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_prosesActionPerformed(evt);
            }
        });
        jPanel10.add(bt_proses, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, 340, 30));

        tb_jasa_pelayanan.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        tb_jasa_pelayanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane7.setViewportView(tb_jasa_pelayanan);

        jPanel10.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 690, 90));

        jTabbedPane4.addTab("Tagihan Inap", jPanel10);

        tb_lab.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(tb_lab);

        jLabel24.setText("Total          Rp.");

        lbl_tot_lab.setText("0");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_tot_lab, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(lbl_tot_lab))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Tagihan Lab", jPanel11);

        jLabel26.setText("Total          Rp.");

        lbl_tot_lab1.setText("0");

        tb_lab1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane10.setViewportView(tb_lab1);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_tot_lab1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(lbl_tot_lab1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Tagihan Obat", jPanel16);

        jPanel7.add(jTabbedPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 700, 300));
        jPanel7.add(txt_plafon_bpjs1, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 90, 200, 30));

        jLabel27.setText("Deposit");
        jPanel7.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 70, 90, -1));
        jPanel7.add(txt_plafon_bpjs, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 150, 200, 30));

        jLabel7.setText("Plafon BPJS");
        jPanel7.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 130, 130, 20));

        bt_save_deposit_plafon_bpjs.setFont(new java.awt.Font("Dialog", 1, 9)); // NOI18N
        bt_save_deposit_plafon_bpjs.setText("Save Plafon BPJS & Deposit");
        bt_save_deposit_plafon_bpjs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_save_deposit_plafon_bpjsActionPerformed(evt);
            }
        });
        jPanel7.add(bt_save_deposit_plafon_bpjs, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 190, 200, 40));

        bt_save1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/save_ico.png"))); // NOI18N
        bt_save1.setText("Proses Pembayaran");
        bt_save1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_save1ActionPerformed(evt);
            }
        });
        jPanel7.add(bt_save1, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 240, 200, 40));

        lbl_total_tagihan4.setText("0");
        jPanel7.add(lbl_total_tagihan4, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 30, 210, 30));

        jLabel32.setText("Sisa Tagihan");
        jPanel7.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, 130, -1));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Tagihan Pasien", jPanel5);

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

        jPanel6.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 880, 200));

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

        jPanel6.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 880, 50));

        lbl_petugas_history.setText("Petugas");
        jPanel6.add(lbl_petugas_history, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 330, 20));

        jLabel16.setText("Penginput Data :");
        jPanel6.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 70, 120, 20));

        jLabel15.setText("Petugas   :");
        jPanel6.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 100, 20));

        lbl_petugas_input_history.setText("Petugas Input");
        jPanel6.add(lbl_petugas_input_history, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 70, 290, 20));

        jTabbedPane3.addTab("History Tindakan Pasien", jPanel6);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane3)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(126, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Input Tindakan", jPanel1);

        jLabel10.setText("Dokter yang Dituju");

        lbl_nip_petugas_pilih.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txt_petugas_pilih.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbl_stat_pasien.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel23.setText("Status Pasien");

        bt_cari_tindakan.setText("jButton8");
        bt_cari_tindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cari_tindakanActionPerformed(evt);
            }
        });

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1055, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_nip_petugas_pilih, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_petugas_pilih, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_stat_pasien, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_cari_tindakan, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTabbedPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_nip_petugas_pilih, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_petugas_pilih, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_stat_pasien, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bt_cari_tindakan)
                                .addGap(99, 99, 99))
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
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

//     if (i == 1) {
//            txt_nip_dpjp.setText(this.tb_cari_petugas.getModel().getValueAt(row, 0).toString());
//            this.txt_dpjp.setText(this.tb_cari_petugas.getModel().getValueAt(row, 1).toString());
//            this.dlg_dpjp.setVisible(false);
//            bt_cari_ppjp.requestFocus();
//            txt_cari_petugas.setText("");
//        } else {
//            txt_nip_ppjp.setText(this.tb_cari_petugas.getModel().getValueAt(row, 0).toString());
//            this.txt_ppjp.setText(this.tb_cari_petugas.getModel().getValueAt(row, 1).toString());
//            this.dlg_dpjp.setVisible(false);
//            txt_cari_petugas.setText("");
//
//            r_pulang.requestFocus();
//        }

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

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        // TODO add your handling code here:

        txt_cari_reg.requestFocus();

    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void hapusmodelunitdetail() {
        DefaultTableModel dm = (DefaultTableModel) tb_biaya_tindakan.getModel();
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
        DefaultTableModel dm = (DefaultTableModel) tb_biaya_tindakan.getModel();
        int rowCount = dm.getRowCount();
        if (rowCount != 0) {

            try {
                datl = new Crud_local();
           
                if(tb_unit_detail_history.getModel().getRowCount()==0){
                   datl.Save_tindakanralan(txt_no_rawat.getText());
                }
                else{
                   
//                   datl.Update_inapanakmaster(txt_no_rawat.getText(), txt_nip_dpjp.getText(), txt_nip_ppjp.getText(), r_belum.isSelected(), kondisipasien());
                   
                }
                
                 
            } catch (Exception ex) {
                Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
            }
                
             
           
            
        }

    }

    private String kondisipasien() {
           
//          String k = "";
//        
//                if (r_belum.isSelected()) {
//                    k = r_belum.getText();
//                } else if (r_membaik.isSelected()) {
//                    k = r_membaik.getText();
//                } else if (r_menurun.isSelected()) {
//                    k = r_menurun.getText();
//                }
//                else if (r_kritis.isSelected()) {
//                    k = r_kritis.getText();
//                }
//                else if (r_sembuh.isSelected()) {
//                    k = r_sembuh.getText();
//                }
            
//            return k;
            return "";
        }
    
    private void bt_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_saveActionPerformed
       
    }//GEN-LAST:event_bt_saveActionPerformed

   

    private void bt_cari_tindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_tindakanActionPerformed
        // TODO add your handling code here:
        pilihcari = 4;

        this.dlg_dpjp.setLocationRelativeTo(this);
        this.dlg_dpjp.setVisible(true);


    }//GEN-LAST:event_bt_cari_tindakanActionPerformed

    
    
    private void lbl_cariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_cariMouseClicked
        // TODO add your handling code here:
//        filterReg();
//        this.filtertxt();

     //edit 31 des 2016
     //cari RM
//     Utilitas.filtertb(txt_cari_reg.getText(), tb_reg, 1,1);
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
        txt_no_rm.setText(this.tb_reg.getModel().getValueAt(row, 1).toString());
        this.txt_nm_pasien.setText(this.tb_reg.getModel().getValueAt(row, 2).toString());
        txt_no_rawat.setText(this.tb_reg.getModel().getValueAt(row, 10).toString());
        this.lbl_tgl_masuk.setText(this.tb_reg.getModel().getValueAt(row, 3).toString());
        lbl_nip_petugas_pilih.setText(this.tb_reg.getModel().getValueAt(row, 4).toString());
        this.txt_petugas_pilih.setText(this.tb_reg.getModel().getValueAt(row, 5).toString());
        
        this.lbl_status.setText(this.tb_reg.getModel().getValueAt(row, 8).toString());
        this.lbl_nm_status.setText(this.tb_reg.getModel().getValueAt(row, 9).toString());
        
        lbl_kode_poli.setText(this.tb_reg.getModel().getValueAt(row, 6).toString());
        lbl_nm_poli.setText(this.tb_reg.getModel().getValueAt(row, 7).toString());   
        
        lbl_stat_pasien.setText(this.tb_reg.getModel().getValueAt(row, 11).toString());
//        
        lbl_no_sep.setText(this.tb_reg.getModel().getValueAt(row, 12).toString());
        
        
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
            
            datl.CloseCon();
            
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
    
       
    }
    
    
    public void setnmnip(){
//        try {
//            datl=new Crud_local();
//            datl.readRec_cariUnitMaster(txt_no_rawat.getText(), 3);
//            txt_nip_dpjp.setText(datl.nipdpjp);
//            txt_nip_ppjp.setText(datl.nippjp);
//        } catch (Exception ex) {
//            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    //edit 26
    public void cariStatDPJPPPJP(){
        try {
//            datl=new Crud_local();
//            datl.readRec_cariUnitMaster(txt_no_rawat.getText(), 2);
//      
//            r_pulang.setSelected(Boolean.parseBoolean(datl.modelunitanakmaster.getValueAt(0, 8).toString()));
//            
//            if(r_belum.getText().equals(datl.modelunitanakmaster.getValueAt(0, 9).toString())){
//               r_belum.setSelected(true);
//            }
//            else if(r_membaik.getText().equals(datl.modelunitanakmaster.getValueAt(0, 9).toString())){
//               r_membaik.setSelected(true);
//            }
//             else if(r_menurun.getText().equals(datl.modelunitanakmaster.getValueAt(0, 9).toString())){
//               r_menurun.setSelected(true);
//            }
//            else if(r_kritis.getText().equals(datl.modelunitanakmaster.getValueAt(0, 9).toString())){
//               r_kritis.setSelected(true);
//            }
//             else if(r_sembuh.getText().equals(datl.modelunitanakmaster.getValueAt(0, 9).toString())){
//               r_sembuh.setSelected(true);
//            }
            
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        
        
        setnmnip();

       
//        try {
//            dat=new Crud_farmasi();
//           
//            txt_dpjp.setText( dat.readRec_CariPegawaiNM(txt_nip_dpjp.getText()));
//            
//        } catch (Exception ex) {
//            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
//        }
//      
//        try {
//            dat=new Crud_farmasi();
//           
//            txt_ppjp.setText( dat.readRec_CariPegawaiNM(txt_nip_ppjp.getText()));
//            
//        } catch (Exception ex) {
//            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
//        }
    
       
            
        
        
    }
    
    
   
    
    private void tb_regKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_regKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int row = this.tb_reg.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {

                 int srow = tb_reg.convertRowIndexToModel(row);
                
                hpsTbbiaya();
                    
                 setMasukdatInput(srow);
                 setBiayaTindakan();
                //txt_tarif.requestFocus();
                this.txt_cari_reg.requestFocus();

//                settbpilih();
 

                if (tb_biaya_tindakan.getModel().getRowCount() != 0) {

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

    
    private void hpsTbbiaya(){
    
    DefaultTableModel dm = (DefaultTableModel) tb_biaya_tindakan.getModel();
            int rowCount = dm.getRowCount();
    if(rowCount!=0){
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }
            }
    }
    
    
    private void hpsbiayalab(){
    
    DefaultTableModel dm = (DefaultTableModel) modelbiayalab;
            int rowCount = dm.getRowCount();
    if(rowCount!=0){
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }
            }
    }
    
    private void hpsbiaya(){
    
    DefaultTableModel dm = (DefaultTableModel) datl.modelbiayatindakan;
            int rowCount = dm.getRowCount();
    if(rowCount!=0){
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }
            }
    }
    
  
   private void setBiayaLab(){
       
       try {
            datl=new Crud_local();
            datl.readRec_periksa_lab(txt_no_rawat.getText());
            datl.CloseCon();
            
            for(int i=0;i<datl.modelperiksalab.getRowCount();i++){
                this.modelbiayalab.addRow(new Object[]{datl.modelperiksalab.getValueAt(i, 1),datl.modelperiksalab.getValueAt(i, 2),
                                                         datl.modelperiksalab.getValueAt(i, 3)});
            }
            
            this.tb_lab.setModel(modelbiayalab);
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
       
      
            
   } 
        
   private void setBiayaTindakanRanap(){
     
        
   
        try {
            
//          dat=new Crud_farmasi();
//
//            dat.readRec_BiayatindakankasirInap(txt_no_rawat.getText(),this.lbl_tgl_server.getText());
//            dat.CloseCon();
//            hitcol=dat.modelkamarinapbiaya.getRowCount();
//            
//            if(hitcol>0){
//              hitcol=hitcol-1;   
//            }
//        if(dat.modelkamarinapbiaya.getRowCount()!=0){
//          if(modelbiaya.getRowCount()==0){  
//            for(int i=0;i<dat.modelkamarinapbiaya.getRowCount();i++){
//                this.modelbiaya.addRow(new Object[]{"kamar: "+dat.modelkamarinapbiaya.getValueAt(i, 4).toString(),dat.modelkamarinapbiaya.getValueAt(i, 8).toString()});
//
//            }
//          } 
//            
//        } 
        

        
        datl=new Crud_local();

        datl.readRec_Biayatindakankasir(txt_no_rawat.getText());
                 datl.CloseCon();
                 
//        if(datl.modelbiayatindakan.getRowCount()!=0){
//          if(modelbiaya.getRowCount()!=0){  
            for(int i=0;i<datl.modelbiayatindakan.getRowCount();i++){
             if(lbl_nm_status.getText().equalsIgnoreCase("BPJS")){
//                this.modelbiaya.insertRow(hitcol+i,new Object[]{datl.modelbiayatindakan.getValueAt(i, 2).toString(),datl.modelbiayatindakan.getValueAt(i, 4).toString()}); 
                this.modelbiaya.addRow(new Object[]{datl.modelbiayatindakan.getValueAt(i, 2).toString(),datl.modelbiayatindakan.getValueAt(i, 4).toString()});
                }
             else
               {
//                this.modelbiaya.addRow(hitcol+i,new Object[]{datl.modelbiayatindakan.getValueAt(i, 2).toString(),datl.modelbiayatindakan.getValueAt(i, 3).toString()});   
                this.modelbiaya.addRow(new Object[]{datl.modelbiayatindakan.getValueAt(i, 2).toString(),datl.modelbiayatindakan.getValueAt(i, 3).toString()});
               }
           
             
             lbl_biaya_reg.setText(datl.modelbiayatindakan.getValueAt(i, 5).toString());
//            }
            
          
            
              System.out.println("hiiitt: "+hitcol);
   
            
          } 
//        }
        hpsbiayalab();
          setBiayaLab();
          
          tb_biaya_tindakan.setModel(modelbiaya);
          
          
          
          hpsbiaya();
        
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
     
    }
    
    
    private void setBiayaTindakan(){
      
        try {
            
          datl=new Crud_local();

            datl.readRec_Biayatindakankasir(txt_no_rawat.getText());
        if(datl.modelbiayatindakan.getRowCount()!=0){
          if(modelbiaya.getRowCount()==0){  
            for(int i=0;i<datl.modelbiayatindakan.getRowCount();i++){
             if(lbl_nm_status.getText().equalsIgnoreCase("BPJS")){
                this.modelbiaya.addRow(new Object[]{datl.modelbiayatindakan.getValueAt(i, 2).toString(),datl.modelbiayatindakan.getValueAt(i, 4).toString()});
                }
             else
               {
                this.modelbiaya.addRow(new Object[]{datl.modelbiayatindakan.getValueAt(i, 2).toString(),datl.modelbiayatindakan.getValueAt(i, 3).toString()});
               }
             lbl_biaya_reg.setText(datl.modelbiayatindakan.getValueAt(i, 5).toString());
            }
            
            tb_biaya_tindakan.setModel(modelbiaya);
            
            hpsbiaya();
            
            //close b
            datl.CloseCon();
            
          } 
            
        } 
        
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
     
    }
    
    private void tb_regMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_regMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = this.tb_reg.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                
                int srow = tb_reg.convertRowIndexToModel(row);
                
               hpsTbbiaya();
                    
                 setMasukdatInput(srow);
                 setBiayaTindakan();
                 
                
                //txt_tarif.requestFocus();
                this.txt_cari_reg.requestFocus();

//                settbpilih();
 
           

                if (tb_biaya_tindakan.getModel().getRowCount() != 0) {

                    this.filterNorawat();
                }
                
                 filterhistorytindakan(txt_no_rawat.getText(),2);
                 
                 cariStatDPJPPPJP();
                 setukurantbulunitHistory();
            }
        }
    }//GEN-LAST:event_tb_regMouseReleased

    private int cekkasir(){
        int a=0;
        try {
            datl=new Crud_local();
            
            
            a=datl.readRec_Hitkasir(this.txt_no_rawat.getText());
            System.out.println(a);
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    
    }
    
    private void HapusRowJaspel(){
        
        DefaultTableModel dm = (DefaultTableModel) tb_jasa_pelayanan.getModel();
        int rowCount = dm.getRowCount();
        
        if (rowCount != 0) {
          for(int i=0;i<rowCount;i++){
            dm.removeRow(i);
          }     
        }
    }
    
    private void HapusRowTindakan(int i) {
        DefaultTableModel dm = (DefaultTableModel) tb_biaya_tindakan.getModel();
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
                 //maatiin heula dieumah teu penting
//                this.Popup_history.show(tb_unit_detail_history, evt.getX(), evt.getY());
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
                //maatiin heula dieumah teu penting
//                this.Popup_history.show(tb_unit_detail_history, evt.getX(), evt.getY());
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

    private void bt_cari_tglActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_tglActionPerformed
        // TODO add your handling code here:
        try {

                dat = new Crud_farmasi();

                String datePattern = "yyyy-MM-dd";
                SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
               
              dat.readRec_registrasiKasir( dateFormatter.format(dt_tgl_reg.getDate()),txt_cari_reg.getText(),1);
                
                this.tb_reg.setModel(dat.modelregralankasir);

                setukurantbReg();
               
                lbl_stat_tgl.setText("Hasil Pencarian Pasien TGl: "+dateFormatter.format(dt_tgl_reg.getDate()));
                
                dat.CloseCon();
    
            } catch (SQLException ex) {
                Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_bt_cari_tglActionPerformed

    private void bt_save1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_save1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_save1ActionPerformed

    private void dt_tgl_regActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dt_tgl_regActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dt_tgl_regActionPerformed

    private void dt_tgl_reg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dt_tgl_reg1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dt_tgl_reg1ActionPerformed

    private void txt_cari_regActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cari_regActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cari_regActionPerformed

    private void txt_cari_rmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cari_rmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cari_rmActionPerformed

    private void lbl_cari1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_cari1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_cari1MouseClicked

    private void tb_reg_inapMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_reg_inapMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = this.tb_reg_inap.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {

                int srow = tb_reg_inap.convertRowIndexToModel(row);
                
               hpsTbbiaya();
                setMasukdatInputInap(srow);
//                setMasukdatInput(srow);
               
                    hapusjaspel();
                   setBiayaTindakanRanap();
                 //edit 13 jan 2017
                 setBiayaKamarinap();
                //txt_tarif.requestFocus();
                this.txt_cari_reg.requestFocus();

             

                if (tb_biaya_tindakan.getModel().getRowCount() != 0) {

                    this.filterNorawat();
                }

                filterhistorytindakan(txt_no_rawat.getText(),2);

                cariStatDPJPPPJP();
                setukurantbulunitHistory();
            }
        }
    }//GEN-LAST:event_tb_reg_inapMouseReleased

    private void tb_reg_inapKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_reg_inapKeyTyped
        // TODO add your handling code here:
        char keyChar = evt.getKeyChar();

        this.txt_cari_reg.requestFocus();

        if (evt.getKeyCode() != KeyEvent.VK_ENTER) {

            this.txt_cari_reg.setText(String.valueOf(keyChar).trim());
        }

        if (!txt_cari_reg.getText().isEmpty()) {
            tb_reg.setRowSelectionInterval(0, 0);
        }
    }//GEN-LAST:event_tb_reg_inapKeyTyped

    private void setMasukdatInputInap(int row) {
        try {
            
            lbl_kode_poli.setText("");
            lbl_nm_poli.setText("");
            
            txt_no_rm.setText(this.tb_reg_inap.getModel().getValueAt(row, 0).toString());
            this.txt_nm_pasien.setText(this.tb_reg_inap.getModel().getValueAt(row, 1).toString());
            txt_no_rawat.setText(this.tb_reg_inap.getModel().getValueAt(row, 2).toString());
            
            dat = new Crud_farmasi();
            
            dat.readRec_registrasiFisio(txt_no_rawat.getText());
            
            dat.CloseCon();
            
            lbl_nip_petugas_pilih.setText(dat.kddokterfisio);
            txt_petugas_pilih.setText(dat.nmdokterfisio);
            
            
            this.lbl_tgl_masuk.setText(this.tb_reg_inap.getModel().getValueAt(row, 3).toString());
             lbl_kamar_inap.setText(this.tb_reg_inap.getModel().getValueAt(row, 4).toString());
            this.lbl_kelas.setText(this.tb_reg_inap.getModel().getValueAt(row, 5).toString());
            this.lbl_status.setText(this.tb_reg_inap.getModel().getValueAt(row, 6).toString());
            this.lbl_nm_status.setText(this.tb_reg_inap.getModel().getValueAt(row, 7).toString());
        } catch (Exception ex) {
            Logger.getLogger(unit_fisio.frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void tb_reg_inapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_reg_inapKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int row = this.tb_reg_inap.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                
                  int srow = tb_reg_inap.convertRowIndexToModel(row);

                  hpsTbbiaya();
                setMasukdatInputInap(srow);
//                setMasukdatInput(srow);
               
                    hapusjaspel();
                   setBiayaTindakanRanap();
                 //edit 13 jan 2017
                 setBiayaKamarinap();
                //txt_tarif.requestFocus();
                this.txt_cari_reg.requestFocus();

                if (tb_biaya_tindakan.getModel().getRowCount() != 0) {

                    this.filterNorawat();
                }

                filterhistorytindakan(txt_no_rawat.getText(),2);

                cariStatDPJPPPJP();

                setukurantbulunitHistory();
            }

        }
    }//GEN-LAST:event_tb_reg_inapKeyPressed

    private void txt_cari_reg_ranapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cari_reg_ranapKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.filtertxt();
            this.tb_reg.requestFocus();
            //jtb_barang.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            tb_reg.setRowSelectionInterval(0, 0);

        }
    }//GEN-LAST:event_txt_cari_reg_ranapKeyPressed

    private void lbl_cari2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_cari2MouseClicked
        // TODO add your handling code here:
        filterReg();
        this.filtertxt();
    }//GEN-LAST:event_lbl_cari2MouseClicked

    private void tb_regMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_regMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb_regMouseClicked

    private void dt_tgl_reg2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dt_tgl_reg2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dt_tgl_reg2ActionPerformed

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
        jam();
    }//GEN-LAST:event_ChkJlnActionPerformed

    private void bt_prosesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_prosesActionPerformed
        // TODO add your handling code here:
        
       
        
         String datePattern = "yyyy-MM-dd";
                SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
               
         String jam=CmbJam.getSelectedItem().toString()+":"+CmbMenit.getSelectedItem().toString()+":"+CmbDetik.getSelectedItem().toString();
         
         
      for(int i=0;i<tb_biaya_inap.getModel().getRowCount();i++){
        
        if(tb_biaya_inap.getModel().getValueAt(i, 4).equals("-")&&tb_biaya_inap.getModel().getValueAt(i, 5).equals("-")){
            try {
                tb_biaya_inap.getModel().setValueAt(dateFormatter.format(dt_tgl_reg2.getDate()), i, 4);
                tb_biaya_inap.getModel().setValueAt(jam, i, 5);
              if(Utilitas.Hitungtgl(dateFormatter.format(dt_tgl_reg2.getDate()),jam,tb_biaya_inap.getModel().getValueAt(i, 2).toString(), 
                        tb_biaya_inap.getModel().getValueAt(i, 3).toString())>6)
              {
              
            if(Utilitas.Hitungtgl(dateFormatter.format(dt_tgl_reg2.getDate()),jam,tb_biaya_inap.getModel().getValueAt(i, 2).toString(), 
                        tb_biaya_inap.getModel().getValueAt(i, 3).toString())==6){
            
                tb_biaya_inap.getModel().setValueAt(1, i, 6);
            
            }      
            else{
            tb_biaya_inap.getModel().setValueAt(Utilitas.Hitungtgl(dateFormatter.format(dt_tgl_reg2.getDate()),jam,tb_biaya_inap.getModel().getValueAt(i, 2).toString(), 
                        tb_biaya_inap.getModel().getValueAt(i, 3).toString())/24, i, 6);
            }
                tb_biaya_inap.getModel().setValueAt(Integer.valueOf(tb_biaya_inap.getModel().getValueAt(i, 6).toString())
                                                   * Double.valueOf(tb_biaya_inap.getModel().getValueAt(i, 1).toString()) ,i,7);
              }
                  
                //Utilitas.Hitungtgl(ateFormatter.format(dt_tgl_reg2.getDate()),jam,tb_biaya_inap.getModel().getValueAt(i, 4), tb_biaya_inap.getModel().getValueAt(i, 5))
            } catch (ParseException ex) {
                
                Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      }
       
     
      hapusjaspel();
      setBiayaJaspel();
      
    }//GEN-LAST:event_bt_prosesActionPerformed

    private void bt_save_deposit_plafon_bpjsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_save_deposit_plafon_bpjsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_save_deposit_plafon_bpjsActionPerformed

    private void bt_save2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_save2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_save2ActionPerformed

    private void bt_notaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_notaActionPerformed
        // TODO add your handling code here:
        lbl_nota.setText("KSR/"+this.txt_no_rawat.getText());
    }//GEN-LAST:event_bt_notaActionPerformed

    private void hapusjaspel(){
     if(tb_jasa_pelayanan.getModel().getRowCount()!=0){ 
         HapusRowJaspel();
      }
    }
    
      private void jam(){
      
           
          
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =CmbJam.getSelectedIndex();
                    nilai_menit =CmbMenit.getSelectedIndex();
                    nilai_detik =CmbDetik.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                CmbJam.setSelectedItem(jam);
                CmbMenit.setSelectedItem(menit);
                CmbDetik.setSelectedItem(detik);
            
       
            }
    
    
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

  private void setBiayaJaspel(){
  
//      readRec_JasaPelayanan
      
       try {
        
        for(int i=0;i<tb_biaya_inap.getModel().getRowCount();i++){
        
         dat = new Crud_farmasi();   
         modelpelayanan.addRow(new Object[]{tb_biaya_inap.getModel().getValueAt(i, 0).toString(),dat.readRec_JasaPelayanan(tb_biaya_inap.getModel().getValueAt(i, 9).toString()),
            tb_biaya_inap.getModel().getValueAt(i, 6).toString(),dat.readRec_JasaPelayanan(tb_biaya_inap.getModel().getValueAt(i, 9).toString())* Integer.valueOf(tb_biaya_inap.getModel().getValueAt(i, 6).toString())});
         dat.CloseCon();
         
         } 
        
        tb_jasa_pelayanan.setModel(modelpelayanan);
        
        
       } catch (Exception ex) {
           Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
       }
  }  
    
  private void setBiayaKamarinap(){
        try {
            
            dat = new Crud_farmasi();
            //readRec_BiayaRanapKasir
            dat.readRec_BiayaRanapKasir(txt_no_rawat.getText());
            tb_biaya_inap.setModel(dat.modelkamarinapbiayakasir);
            
//            JOptionPane.showMessageDialog(null, "semuaaaa");
            
            dat.CloseCon();
            
            setukurantb_biaya_inap();
            
          
            
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(frm_poli_ralan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_poli_ralan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_poli_ralan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_poli_ralan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_poli_ralan().setVisible(true);
            }
        });

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox ChkJln;
    private javax.swing.JComboBox<String> CmbDetik;
    private javax.swing.JComboBox<String> CmbJam;
    private javax.swing.JComboBox<String> CmbMenit;
    private uz.ncipro.calendar.JDateTimePicker Dttgl1;
    private uz.ncipro.calendar.JDateTimePicker Dttgl2;
    private javax.swing.JPopupMenu Popup_hapus;
    private javax.swing.JPopupMenu Popup_history;
    private javax.swing.JToolBar ToolBar;
    private javax.swing.JButton bt_cari_tgl;
    private javax.swing.JButton bt_cari_tgl_tindakan;
    private javax.swing.JButton bt_cari_tindakan;
    private javax.swing.JButton bt_nota;
    private javax.swing.JButton bt_proses;
    private javax.swing.JButton bt_save;
    private javax.swing.JButton bt_save1;
    private javax.swing.JButton bt_save2;
    private javax.swing.JButton bt_save_deposit_plafon_bpjs;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JDialog dlg_dpjp;
    private uz.ncipro.calendar.JDateTimePicker dt_tgl_reg;
    private uz.ncipro.calendar.JDateTimePicker dt_tgl_reg1;
    private uz.ncipro.calendar.JDateTimePicker dt_tgl_reg2;
    private javax.swing.JMenuItem item_hapus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JLabel lbl;
    private javax.swing.JLabel lbl_biaya_reg;
    private javax.swing.JLabel lbl_biaya_reg1;
    private javax.swing.JLabel lbl_biaya_reg2;
    private javax.swing.JLabel lbl_cari;
    private javax.swing.JLabel lbl_cari1;
    private javax.swing.JLabel lbl_cari2;
    private javax.swing.JLabel lbl_jam;
    private javax.swing.JLabel lbl_jamnow;
    private javax.swing.JLabel lbl_kamar_inap;
    private javax.swing.JLabel lbl_kelas;
    private javax.swing.JLabel lbl_kode_poli;
    private javax.swing.JLabel lbl_news;
    private javax.swing.JLabel lbl_nip_petugas_pilih;
    private javax.swing.JLabel lbl_nm_poli;
    private javax.swing.JLabel lbl_nm_status;
    private javax.swing.JLabel lbl_no_sep;
    private javax.swing.JLabel lbl_nota;
    private javax.swing.JLabel lbl_petugas;
    private javax.swing.JLabel lbl_petugas_history;
    private javax.swing.JLabel lbl_petugas_input_history;
    private javax.swing.JLabel lbl_poli;
    private javax.swing.JLabel lbl_poli1;
    private javax.swing.JLabel lbl_stat_pasien;
    private javax.swing.JLabel lbl_stat_tgl;
    private javax.swing.JLabel lbl_status;
    private javax.swing.JLabel lbl_tgl_masuk;
    private javax.swing.JLabel lbl_tgl_server;
    private javax.swing.JLabel lbl_tot_lab;
    private javax.swing.JLabel lbl_tot_lab1;
    private javax.swing.JLabel lbl_tot_tindakan;
    private javax.swing.JLabel lbl_total_tagihan2;
    private javax.swing.JLabel lbl_total_tagihan3;
    private javax.swing.JLabel lbl_total_tagihan4;
    private javax.swing.JMenuItem mnu_item_hapus_tindakan;
    private javax.swing.JTable tb_biaya_inap;
    private javax.swing.JTable tb_biaya_tindakan;
    private javax.swing.JTable tb_cari_petugas;
    private javax.swing.JTable tb_jasa_pelayanan;
    private javax.swing.JTable tb_lab;
    private javax.swing.JTable tb_lab1;
    private javax.swing.JTable tb_reg;
    private javax.swing.JTable tb_reg_inap;
    private javax.swing.JTable tb_unit_detail_history;
    private javax.swing.JTextField txt_bayar1;
    private javax.swing.JTextField txt_bayar2;
    private javax.swing.JTextField txt_bayar3;
    private javax.swing.JTextField txt_bayar4;
    private javax.swing.JTextField txt_cari_nama_tindakan;
    private javax.swing.JTextField txt_cari_petugas;
    private javax.swing.JTextField txt_cari_reg;
    private javax.swing.JTextField txt_cari_reg_ranap;
    private javax.swing.JTextField txt_cari_rm;
    private javax.swing.JTextField txt_nm_pasien;
    private javax.swing.JTextField txt_no_rawat;
    private javax.swing.JTextField txt_no_rm;
    private javax.swing.JLabel txt_petugas_pilih;
    private javax.swing.JTextField txt_plafon_bpjs;
    private javax.swing.JTextField txt_plafon_bpjs1;
    // End of variables declaration//GEN-END:variables
}
