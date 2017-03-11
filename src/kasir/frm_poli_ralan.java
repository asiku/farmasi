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
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import org.apache.commons.lang3.StringUtils;
import tools.Utilitas;
import unit_poli.frm_poli;

/**
 *
 * @author jengcool
 */
public class frm_poli_ralan extends javax.swing.JFrame {

    private Double alltot=0.0;
    
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

    
    String[] biaya_title = new String[]{"Nama Tagihan", "Tarif","Kode Tarif","petugas"};
    
    String[] biaya_titlelab = new String[]{"Kode Lab","Tagihan", "Tarif"};
    
    
    String[] template_title = new String[]{"Nama Template", "Petugas", "Status", "Tanggal"};
    String[] template_detail = new String[]{"Nama Template", "Kode Tindakan", "Tindakan"};

    String[] unit_detail = new String[]{"No Rawat", "Kode Tindakan", "Tindakan", "Petugas", "Tgl Tindakan", "Nip Petugas"};

    
    String[] template_pelayanan = new String[]{"Pelayanan Kamar", "Tarif","lama", "Total"};
    
    
     String[] rinci_title = new String[]{"Nama Tagihan", "Total"};
    
     DefaultTableModel modelrinci = new DefaultTableModel(rinci_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };
      
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

        lbl_biaya_reg.setVisible(false);
        lbl_biaya_adm_ranap.setVisible(false);
        jLabel18.setVisible(false);
        jLabel28.setVisible(false);
        
        txt_cari_nota.setVisible(false);
        jLabel8.setVisible(false);
        
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

        
        txt_jml_deposit.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               lbl_format_jml_deposit.setText(Utilitas.formatuang(Double.parseDouble(txt_jml_deposit.getText())));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              lbl_format_jml_deposit.setText(Utilitas.formatuang(Double.parseDouble(txt_jml_deposit.getText())));
            }
        });
        
        txt_cari_tindakan.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               settbpilih(txt_cari_tindakan.getText(),true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                settbpilih(txt_cari_tindakan.getText(),true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              settbpilih(txt_cari_tindakan.getText(),true);
            }
        });
        
        
        txt_bayar_debet.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               setHitung();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
//               setHitung();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              setHitung();
            }
        });
        
        txt_bayar_kredit.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               setHitung();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
//               setHitung();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              setHitung();
            }
        });
        
        txt_bayar_asuransi.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               setHitung();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
//               setHitung();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              setHitung();
            }
        });
        
        
        txt_bayar_cash.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               setHitung();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
//               setHitung();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              setHitung();
            }
        });
        
        txt_cari_jual_bebas.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               carijualbebas();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                carijualbebas();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              carijualbebas();
            }
        });
        
        
        txt_cari_nota.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setBiayaObat(txt_cari_nota.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setBiayaObat(txt_cari_nota.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setBiayaObat(txt_cari_nota.getText());
            }
        });
        
        
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
        tri.getColumn(1).setPreferredWidth(270);
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
    
    

    private void setukurantb_deposit() {
        
        this.tb_deposit.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        tb_deposit.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tr = this.tb_deposit.getColumnModel();

        tr.getColumn(0).setPreferredWidth(150);
        tr.getColumn(1).setPreferredWidth(100);
        tr.getColumn(2).setPreferredWidth(100);
        tr.getColumn(3).setPreferredWidth(100);
        
         this.tb_deposit_master.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
         tb_deposit_master.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tr1 = this.tb_deposit_master.getColumnModel();

        tr1.getColumn(0).setPreferredWidth(150);
        tr1.getColumn(1).setPreferredWidth(150);
        tr1.getColumn(2).setPreferredWidth(100);
        tr1.getColumn(3).setPreferredWidth(100);


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
    
     private void setukurantbjual_bebas() {

        this.tb_jual_bebas.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        tb_jual_bebas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tr = this.tb_jual_bebas.getColumnModel();

        tr.getColumn(0).setPreferredWidth(120);
        tr.getColumn(1).setPreferredWidth(150);
        tr.getColumn(2).setPreferredWidth(200);
        tr.getColumn(3).setPreferredWidth(40);
        tr.getColumn(4).setPreferredWidth(90);
        tr.getColumn(5).setPreferredWidth(105);
    }
    
    
    
    private void setukurantbobat() {

        this.tb_obat.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        tb_obat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tr = this.tb_obat.getColumnModel();

        tr.getColumn(0).setPreferredWidth(180);
        tr.getColumn(1).setPreferredWidth(280);
        tr.getColumn(2).setPreferredWidth(40);
        tr.getColumn(3).setPreferredWidth(100);
        tr.getColumn(4).setPreferredWidth(105);

    }
    
    private void setukurantblab() {

        this.tb_lab.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        tb_lab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tr = this.tb_lab.getColumnModel();

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
        tr.getColumn(2).setPreferredWidth(210);
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
        buttonGroup3 = new javax.swing.ButtonGroup();
        dlg_update_naik_kelas = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        lbl_kode_tindakan = new javax.swing.JLabel();
        lbl_nama_tindakan = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        lbl_kode_tindakan_update = new javax.swing.JLabel();
        lbl_nama_tindakan_update = new javax.swing.JLabel();
        lbl_ket = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        lbl_tarif = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        lbl_tarif_update = new javax.swing.JLabel();
        lbl_kelas_dlg = new javax.swing.JLabel();
        lbl_kelas_update = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tb_tindakan_pilih = new javax.swing.JTable();
        txt_cari_tindakan = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        bt_update_tindakan = new javax.swing.JButton();
        bt_batal = new javax.swing.JButton();
        Popup_CostSharing = new javax.swing.JPopupMenu();
        mnu_costsharing = new javax.swing.JMenuItem();
        dlg_deposit = new javax.swing.JDialog();
        jPanel20 = new javax.swing.JPanel();
        bt_save_deposit = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tb_deposit = new javax.swing.JTable();
        bt_keluar = new javax.swing.JButton();
        lbl_tot_deposit = new javax.swing.JLabel();
        bt_edit_deposit = new javax.swing.JButton();
        bt_cetak = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel19 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        txt_kode_deposit = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        txt_nama_pendeposit = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        txt_tlp_deposit = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        txt_jml_deposit = new javax.swing.JTextField();
        lbl_format_jml_deposit = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tb_deposit_master = new javax.swing.JTable();
        Popup_hapus_deposit = new javax.swing.JPopupMenu();
        mnu_hapus_deposit = new javax.swing.JMenuItem();
        mnu_edit_deposit = new javax.swing.JMenuItem();
        Popup_hapus_deposit_detail = new javax.swing.JPopupMenu();
        mnu_item_hapus_detail = new javax.swing.JMenuItem();
        ToolBar = new javax.swing.JToolBar();
        jPanel2 = new javax.swing.JPanel();
        lbl_jam = new javax.swing.JLabel();
        lbl_petugas = new javax.swing.JLabel();
        lbl_poli = new javax.swing.JLabel();
        lbl_poli1 = new javax.swing.JLabel();
        lbl_news = new javax.swing.JLabel();
        lbl_tgl_server = new javax.swing.JLabel();
        lbl_jamnow = new javax.swing.JLabel();
        lbl_nota = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Tab_reg = new javax.swing.JTabbedPane();
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
        jLabel28 = new javax.swing.JLabel();
        lbl_biaya_adm_ranap = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txt_bayar_cash = new javax.swing.JTextField();
        txt_bayar_kredit = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txt_bayar_debet = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        lbl_total_tagihan = new javax.swing.JLabel();
        txt_bayar_asuransi = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        lbl_sisa_tagihan = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        bt_nota = new javax.swing.JButton();
        bt_save = new javax.swing.JButton();
        bt_save2 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        txt_plafon_bpjs1 = new javax.swing.JTextField();
        bt_save_deposit_plafon_bpjs = new javax.swing.JButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tb_biaya_tindakan = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        lbl_tot_tindakan = new javax.swing.JLabel();
        lbl_status_naik_kelas = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_biaya_inap = new javax.swing.JTable();
        CmbJam = new javax.swing.JComboBox<>();
        CmbMenit = new javax.swing.JComboBox<>();
        CmbDetik = new javax.swing.JComboBox<>();
        dt_tgl_reg2 = new uz.ncipro.calendar.JDateTimePicker();
        ChkJln = new javax.swing.JCheckBox();
        bt_proses = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tb_jasa_pelayanan = new javax.swing.JTable();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        lbl_tot_kamar = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        lbl_tot_jaspel = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tb_lab = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        lbl_tot_lab = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        lbl_tot_obat = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tb_obat = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txt_cari_nota = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        lbl_tot_alkes = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        lbl_tot_obat_grand = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tb_jual_bebas = new javax.swing.JTable();
        txt_cari_jual_bebas = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        ck_umum = new javax.swing.JCheckBox();
        ck_kary = new javax.swing.JCheckBox();
        jLabel34 = new javax.swing.JLabel();
        lbl_tot_jual_bebas = new javax.swing.JLabel();
        lbl_tot_jual_bebas_alkes = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        lbl_tot_jual_bebas_grand = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tb_rinci_tagihan = new javax.swing.JTable();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        txt_plafon_bpjs = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        bt_pembayaran = new javax.swing.JButton();
        cmb_cara_bayar = new javax.swing.JComboBox<>();
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

        dlg_update_naik_kelas.setModal(true);
        dlg_update_naik_kelas.setSize(new java.awt.Dimension(614, 496));

        jPanel17.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel19.setText("Kode ");

        lbl_kode_tindakan.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbl_nama_tindakan.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel41.setText("Tindakan");

        jLabel42.setText("Tindakan");

        jLabel43.setText("Kode ");

        lbl_kode_tindakan_update.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbl_nama_tindakan_update.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbl_ket.setForeground(new java.awt.Color(255, 0, 0));
        lbl_ket.setText("Udate Tindakan ============================================");

        jLabel45.setText("Tarif");

        lbl_tarif.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel46.setText("Tarif");

        lbl_tarif_update.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbl_kelas_dlg.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbl_kelas_update.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_ket)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(lbl_nama_tindakan, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_kelas_dlg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_kode_tindakan, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_kode_tindakan_update, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_nama_tindakan_update, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(lbl_tarif_update, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_kelas_update, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_kode_tindakan, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_nama_tindakan, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_kelas_dlg, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_ket, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_kode_tindakan_update, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_nama_tindakan_update, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_tarif_update, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lbl_kelas_update, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tb_tindakan_pilih.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        tb_tindakan_pilih.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        tb_tindakan_pilih.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_tindakan_pilihMouseReleased(evt);
            }
        });
        jScrollPane12.setViewportView(tb_tindakan_pilih);

        jLabel9.setText("Cari Tindakan");

        bt_update_tindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/edit_ic.png"))); // NOI18N
        bt_update_tindakan.setText("Update");
        bt_update_tindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_update_tindakanActionPerformed(evt);
            }
        });

        bt_batal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/delete_ic.png"))); // NOI18N
        bt_batal.setText("Batal");
        bt_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_batalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_cari_tindakan))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bt_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bt_update_tindakan, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel18Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_cari_tindakan, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_update_tindakan)
                    .addComponent(bt_batal))
                .addContainerGap())
            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel18Layout.createSequentialGroup()
                    .addGap(43, 43, 43)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(53, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout dlg_update_naik_kelasLayout = new javax.swing.GroupLayout(dlg_update_naik_kelas.getContentPane());
        dlg_update_naik_kelas.getContentPane().setLayout(dlg_update_naik_kelasLayout);
        dlg_update_naik_kelasLayout.setHorizontalGroup(
            dlg_update_naik_kelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dlg_update_naik_kelasLayout.setVerticalGroup(
            dlg_update_naik_kelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlg_update_naik_kelasLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mnu_costsharing.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kasir/money_ico.png"))); // NOI18N
        mnu_costsharing.setText("Set Cost Sharing");
        mnu_costsharing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnu_costsharingActionPerformed(evt);
            }
        });
        Popup_CostSharing.add(mnu_costsharing);

        dlg_deposit.setModal(true);
        dlg_deposit.setResizable(false);
        dlg_deposit.setSize(new java.awt.Dimension(506, 500));
        dlg_deposit.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel20.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        bt_save_deposit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/save_ico.png"))); // NOI18N
        bt_save_deposit.setText("Save");
        bt_save_deposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_save_depositActionPerformed(evt);
            }
        });

        tb_deposit.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        tb_deposit.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_deposit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tb_depositMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_depositMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tb_deposit);

        bt_keluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/log_out.png"))); // NOI18N
        bt_keluar.setText("Keluar");
        bt_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_keluarActionPerformed(evt);
            }
        });

        bt_edit_deposit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/edit_ic.png"))); // NOI18N
        bt_edit_deposit.setText("Edit");
        bt_edit_deposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_edit_depositActionPerformed(evt);
            }
        });

        bt_cetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/farmasi/printer_ico.png"))); // NOI18N
        bt_cetak.setText("Cetak");
        bt_cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_tot_deposit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                                .addComponent(bt_cetak)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_keluar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bt_edit_deposit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bt_save_deposit)))))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(lbl_tot_deposit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_save_deposit)
                    .addComponent(bt_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_edit_deposit)
                    .addComponent(bt_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        dlg_deposit.getContentPane().add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 490, 260));

        jTabbedPane2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane2StateChanged(evt);
            }
        });

        jPanel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel44.setText("Kode Deposit");

        txt_kode_deposit.setEditable(false);

        jLabel47.setText("Nama Pendeposit");

        txt_nama_pendeposit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nama_pendepositKeyPressed(evt);
            }
        });

        jLabel48.setText("telp");

        txt_tlp_deposit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_tlp_depositKeyPressed(evt);
            }
        });

        jLabel49.setText("Jumlah Deposit");

        txt_jml_deposit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_jml_depositKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47)
                    .addComponent(jLabel48)
                    .addComponent(jLabel44)
                    .addComponent(jLabel49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_nama_pendeposit)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_kode_deposit)
                                .addComponent(txt_tlp_deposit)
                                .addComponent(txt_jml_deposit, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE))
                            .addComponent(lbl_format_jml_deposit, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txt_kode_deposit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(txt_nama_pendeposit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(txt_tlp_deposit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(txt_jml_deposit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_format_jml_deposit, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Input Data Deposit", jPanel19);

        tb_deposit_master.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        tb_deposit_master.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_deposit_master.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tb_deposit_masterMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_deposit_masterMouseReleased(evt);
            }
        });
        jScrollPane13.setViewportView(tb_deposit_master);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("History Deposit", jPanel21);

        dlg_deposit.getContentPane().add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 490, 190));

        mnu_hapus_deposit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/delete_ic.png"))); // NOI18N
        mnu_hapus_deposit.setText("Hapus Data Deposit");
        mnu_hapus_deposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnu_hapus_depositActionPerformed(evt);
            }
        });
        Popup_hapus_deposit.add(mnu_hapus_deposit);

        mnu_edit_deposit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/edit_ic.png"))); // NOI18N
        mnu_edit_deposit.setText("Edit");
        mnu_edit_deposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnu_edit_depositActionPerformed(evt);
            }
        });
        Popup_hapus_deposit.add(mnu_edit_deposit);

        mnu_item_hapus_detail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/edit_ic.png"))); // NOI18N
        mnu_item_hapus_detail.setText("Edit Total");
        mnu_item_hapus_detail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnu_item_hapus_detailActionPerformed(evt);
            }
        });
        Popup_hapus_deposit_detail.add(mnu_item_hapus_detail);

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

        lbl_news.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kasir/money_ico.png"))); // NOI18N
        lbl_news.setText("Tutup Kasir");

        lbl_jamnow.setText("sssss");

        lbl_nota.setBackground(new java.awt.Color(102, 102, 102));

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
                .addContainerGap(1313, Short.MAX_VALUE))
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
                    .addComponent(lbl_nota, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        Tab_reg.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N

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

        Tab_reg.addTab("Data Pasien Ralan", jPanel12);

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

        Tab_reg.addTab("Data Pasien Ranap", jPanel13);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(Tab_reg, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Tab_reg)
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
        jPanel4.add(lbl_tgl_masuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 132, 19));

        jLabel5.setText("Status");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 60, -1));

        lbl_nm_status.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_nm_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 170, 19));

        lbl_status.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 60, 19));

        jLabel6.setText("Poli");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 60, -1));

        lbl_kode_poli.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_kode_poli, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 60, 19));

        lbl_nm_poli.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_nm_poli, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 150, 20));

        lbl_no_sep.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_no_sep, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, 170, 19));

        lbl.setText("No. SEP");
        jPanel4.add(lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 60, -1));

        jLabel14.setText("Kelas");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, 60, -1));

        lbl_kelas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_kelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, 150, 19));

        jLabel17.setText("Kamar");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 120, 60, -1));

        lbl_kamar_inap.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(lbl_kamar_inap, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 120, 180, 19));

        jLabel21.setText("Tgl Registrasi");
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 108, -1));

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel18.setText("Biaya Regist.");
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, -1, -1));

        lbl_biaya_reg.setText("0");
        jPanel4.add(lbl_biaya_reg, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, 90, 30));

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel28.setText("Biaya Adm. Ranap");
        jPanel4.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 10, -1, -1));

        lbl_biaya_adm_ranap.setText("0");
        jPanel4.add(lbl_biaya_adm_ranap, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 30, 170, 30));

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel29.setText("Cash");
        jPanel4.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, -1, -1));
        jPanel4.add(txt_bayar_cash, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, 170, 30));
        jPanel4.add(txt_bayar_kredit, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 90, 170, 30));

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel30.setText("Kartu Kredit");
        jPanel4.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 70, 90, -1));

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel31.setText("Kartu Debet");
        jPanel4.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 10, -1, -1));
        jPanel4.add(txt_bayar_debet, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 30, 180, 30));

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel20.setText("Total Tagihan");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 130, 130, -1));

        lbl_total_tagihan.setText("0");
        jPanel4.add(lbl_total_tagihan, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 150, 180, 30));
        jPanel4.add(txt_bayar_asuransi, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 90, 180, 30));

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel33.setText("Asuransi/Pembayaran BPJS");
        jPanel4.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 70, 180, -1));

        lbl_sisa_tagihan.setText("0");
        jPanel4.add(lbl_sisa_tagihan, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 150, 150, 30));

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel32.setText("Sisa Tagihan");
        jPanel4.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 130, 130, -1));

        bt_nota.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        bt_nota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli_dewasa/edit_ic.png"))); // NOI18N
        bt_nota.setText("Add Nota");
        bt_nota.setBorder(null);
        bt_nota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_notaActionPerformed(evt);
            }
        });
        jPanel4.add(bt_nota, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 150, 110, 30));

        bt_save.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        bt_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kasir/kasir_kecil_ico.png"))); // NOI18N
        bt_save.setText("Print Kwitansi");
        bt_save.setBorder(null);
        bt_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_saveActionPerformed(evt);
            }
        });
        jPanel4.add(bt_save, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 150, 130, 30));

        bt_save2.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        bt_save2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kasir/kasir_kecil_ico.png"))); // NOI18N
        bt_save2.setText("Print Rincian");
        bt_save2.setBorder(null);
        bt_save2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_save2ActionPerformed(evt);
            }
        });
        jPanel4.add(bt_save2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 190, 250, -1));

        jLabel27.setText("Deposit");
        jPanel4.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 190, 70, 20));
        jPanel4.add(txt_plafon_bpjs1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 190, 150, 30));

        bt_save_deposit_plafon_bpjs.setFont(new java.awt.Font("Dialog", 1, 9)); // NOI18N
        bt_save_deposit_plafon_bpjs.setText("Deposit");
        bt_save_deposit_plafon_bpjs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_save_deposit_plafon_bpjsActionPerformed(evt);
            }
        });
        jPanel4.add(bt_save_deposit_plafon_bpjs, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 190, 100, 30));

        jTabbedPane3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane3StateChanged(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane4.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N

        tb_biaya_tindakan.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        tb_biaya_tindakan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        tb_biaya_tindakan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tb_biaya_tindakanMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_biaya_tindakanMouseReleased(evt);
            }
        });
        jScrollPane9.setViewportView(tb_biaya_tindakan);

        jLabel25.setText("Total         ");

        lbl_tot_tindakan.setText("0");

        lbl_status_naik_kelas.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lbl_status_naik_kelas, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addGap(29, 29, 29)
                .addComponent(lbl_tot_tindakan, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_status_naik_kelas, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel25)
                        .addComponent(lbl_tot_tindakan)))
                .addContainerGap(243, Short.MAX_VALUE))
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                    .addContainerGap(35, Short.MAX_VALUE)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane4.addTab("Tagihan Tindakan", jPanel14);

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tb_biaya_inap.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        tb_biaya_inap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tb_biaya_inap);

        jPanel10.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 690, 80));

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jPanel10.add(CmbJam, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, -1, -1));

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jPanel10.add(CmbMenit, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, -1, -1));

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jPanel10.add(CmbDetik, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, -1, -1));

        dt_tgl_reg2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dt_tgl_reg2.setDisplayFormat("yyyy/MM/dd");
        dt_tgl_reg2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dt_tgl_reg2ActionPerformed(evt);
            }
        });
        jPanel10.add(dt_tgl_reg2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 140, 20));

        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        jPanel10.add(ChkJln, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, -1, -1));

        bt_proses.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        bt_proses.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/tick_ico.png"))); // NOI18N
        bt_proses.setText("Proses");
        bt_proses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_prosesActionPerformed(evt);
            }
        });
        jPanel10.add(bt_proses, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 18, 130, -1));

        tb_jasa_pelayanan.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        tb_jasa_pelayanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane7.setViewportView(tb_jasa_pelayanan);

        jPanel10.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 690, 70));

        jLabel50.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel50.setText("JP mengikuti bayi sakit.");
        jPanel10.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 50, 190, -1));

        jLabel51.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel51.setText("Catatan: Untuk perina jika bayi ");
        jPanel10.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 190, -1));

        jLabel52.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel52.setText("sehat maka JP mengikuti ibu, Jika");
        jPanel10.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 22, 190, -1));

        jLabel53.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel53.setText("Pindah kelas dari sehat ke sakit");
        jPanel10.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 35, 190, -1));

        jLabel38.setText("Biaya Kamar ");
        jPanel10.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        lbl_tot_kamar.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        lbl_tot_kamar.setText("0");
        jPanel10.add(lbl_tot_kamar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 100, -1));

        jLabel36.setText("Jasa Pelayanan");
        jPanel10.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        lbl_tot_jaspel.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        lbl_tot_jaspel.setText("0");
        jPanel10.add(lbl_tot_jaspel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 100, -1));

        jTabbedPane4.addTab("Tagihan Inap", jPanel10);

        tb_lab.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
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

        jLabel24.setText("Total ");

        lbl_tot_lab.setText("0");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel24)
                .addGap(18, 18, 18)
                .addComponent(lbl_tot_lab, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_tot_lab)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Tagihan Lab", jPanel11);

        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setText("Grand Total");
        jPanel16.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, -1, -1));

        lbl_tot_obat.setText("0");
        jPanel16.add(lbl_tot_obat, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 120, -1));

        tb_obat.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        tb_obat.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane10.setViewportView(tb_obat);

        jPanel16.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 52, 691, 220));

        jLabel8.setText("No. Nota");
        jPanel16.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, -1, -1));
        jPanel16.add(txt_cari_nota, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, 50, -1));

        jLabel35.setText("Total  Alkes ");
        jPanel16.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 100, -1));

        lbl_tot_alkes.setText("0");
        jPanel16.add(lbl_tot_alkes, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 20, 130, -1));

        jLabel39.setText("Total Obat  ");
        jPanel16.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

        lbl_tot_obat_grand.setText("0");
        jPanel16.add(lbl_tot_obat_grand, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 140, -1));

        jTabbedPane4.addTab("Tagihan Obat", jPanel16);

        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tb_jual_bebas.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        tb_jual_bebas.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_jual_bebas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_jual_bebasMouseReleased(evt);
            }
        });
        jScrollPane11.setViewportView(tb_jual_bebas);

        jPanel15.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 80, 667, 180));
        jPanel15.add(txt_cari_jual_bebas, new org.netbeans.lib.awtextra.AbsoluteConstraints(113, 12, 178, -1));

        jLabel22.setText("No. Nota");
        jPanel15.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 14, 83, -1));

        buttonGroup3.add(ck_umum);
        ck_umum.setText("Umum");
        ck_umum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ck_umumActionPerformed(evt);
            }
        });
        jPanel15.add(ck_umum, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, -1, -1));

        buttonGroup3.add(ck_kary);
        ck_kary.setText("Karyawan");
        ck_kary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ck_karyActionPerformed(evt);
            }
        });
        jPanel15.add(ck_kary, new org.netbeans.lib.awtextra.AbsoluteConstraints(299, 10, -1, -1));

        jLabel34.setText("Total  Obat");
        jPanel15.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        lbl_tot_jual_bebas.setText("0");
        jPanel15.add(lbl_tot_jual_bebas, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 119, -1));

        lbl_tot_jual_bebas_alkes.setText("0");
        jPanel15.add(lbl_tot_jual_bebas_alkes, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, 117, -1));

        jLabel37.setText("Grand Total");
        jPanel15.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, -1, -1));

        jLabel40.setText("Total  Alkes");
        jPanel15.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, -1, -1));

        lbl_tot_jual_bebas_grand.setText("0");
        jPanel15.add(lbl_tot_jual_bebas_grand, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 30, 150, 20));

        jTabbedPane4.addTab("Tagihan Obat Jual Bebas", jPanel15);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel24.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tb_rinci_tagihan.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        tb_rinci_tagihan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane14.setViewportView(tb_rinci_tagihan);

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
        jScrollPane15.setViewportView(jTable2);

        jLabel11.setText("Tagihan yang Tidak di Cover BPJS");

        jLabel54.setText("List Tagihan");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 73, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel54))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, 271, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("Rincian Tagihan", jPanel22);

        jPanel7.add(jTabbedPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 750, 300));
        jPanel7.add(txt_plafon_bpjs, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 100, 150, 30));

        jLabel7.setText("Coding BPJS");
        jPanel7.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 60, 130, 20));

        bt_pembayaran.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        bt_pembayaran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/save_ico.png"))); // NOI18N
        bt_pembayaran.setText("PEMBAYARAN");
        bt_pembayaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_pembayaranActionPerformed(evt);
            }
        });
        jPanel7.add(bt_pembayaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 140, 150, 50));

        cmb_cara_bayar.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cmb_cara_bayar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "===CARA BAYAR===", "LUNAS", "PIUTANG" }));
        jPanel7.add(cmb_cara_bayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 20, -1, -1));

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
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE))
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
    
   private void setBiayaObat(String nota){
       
       try {
            datl=new Crud_local();
            datl.readRec_ObatKasir(txt_no_rawat.getText(),nota);
            datl.CloseCon();
             tb_obat.setModel(datl.modelobatkasir);
//            lbl_tot_lab.setText(Utilitas.formatuang(sumlab()));
//             
            setukurantbobat();
            
            lbl_tot_obat.setText(Utilitas.formatuang(sumobat()));
            
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
       
      
            
   }  
    
   private void setBiayaObat(){
       
       try {
            datl=new Crud_local();
            datl.readRec_ObatKasir(txt_no_rawat.getText());
            datl.CloseCon();
            tb_obat.setModel(datl.modelobatkasir);
//            lbl_tot_lab.setText(Utilitas.formatuang(sumlab()));
//             
            setukurantbobat();
            
            lbl_tot_obat.setText(Utilitas.formatuang(sumobat()));
            
            lbl_tot_alkes.setText(Utilitas.formatuang(sumobatAlkes()));
            
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
       
      
            
   } 
            
   private void setBiayaLab(){
       
       try {
            datl=new Crud_local();
            datl.readRec_periksa_lab(txt_no_rawat.getText());
            datl.CloseCon();
            
//            for(int i=0;i<datl.modelperiksalab.getRowCount();i++){
//                this.modelbiayalab.addRow(new Object[]{datl.modelperiksalab.getValueAt(i, 1),datl.modelperiksalab.getValueAt(i, 2),
//                                                         datl.modelperiksalab.getValueAt(i, 3)});
//            }
             if(lbl_nm_status.getText().equals("BPJS")){
              this.tb_lab.setModel(datl.modelperiksalabbpjs);
             }
             else{
             this.tb_lab.setModel(datl.modelperiksalab);
             
             }
             
            lbl_tot_lab.setText(Utilitas.formatuang(sumlab()));
             
            setukurantblab();
            
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
   
   private Double sumjaspel(){
     double tot=0.0;
       if(tb_jasa_pelayanan.getModel().getRowCount()>0){
          
           for(int i=0;i<tb_jasa_pelayanan.getModel().getRowCount();i++){
            tot=tot+Double.parseDouble(tb_jasa_pelayanan.getModel().getValueAt(i, 3).toString());
          }
       }
     
       return tot;
   }
   
   private Double sumkamarinap(){
    double tot=0.0;
    
     
     
       if(tb_biaya_inap.getModel().getRowCount()>0){
          
           for(int i=0;i<tb_biaya_inap.getModel().getRowCount();i++){
            tot=tot+Double.parseDouble(tb_biaya_inap.getModel().getValueAt(i, 7).toString());
          }
       }
    
    
       return tot;
   }

   private Double sumobatAlkes(){
     double tot=0.0;
     
       if(tb_obat.getModel().getRowCount()>0){
          
           for(int i=0;i<tb_obat.getModel().getRowCount();i++){
            if(tb_obat.getModel().getValueAt(i, 11).toString().equals("ALK")){    
             tot=tot+Double.parseDouble(tb_obat.getModel().getValueAt(i, 4).toString());
            }
            
          }
       }
     
       return tot;
   }

   
   private Double sumobat(){
     double tot=0.0;
     double grandtot=0.0;
     
       if(tb_obat.getModel().getRowCount()>0){
          
           for(int i=0;i<tb_obat.getModel().getRowCount();i++){
            if(tb_obat.getModel().getValueAt(i, 11).toString().equals("OBR")||
                    tb_obat.getModel().getValueAt(i, 11).toString().equals("resep")){    
             tot=tot+Double.parseDouble(tb_obat.getModel().getValueAt(i, 4).toString());
            }
            grandtot=grandtot+Double.parseDouble(tb_obat.getModel().getValueAt(i, 4).toString());
          }
       }
        lbl_tot_obat_grand.setText(Utilitas.formatuang(grandtot));
       return tot;
   }
   
   private Double sumobatjualbebas(){
     double tot=0.0;
       if(tb_jual_bebas.getModel().getRowCount()>0){
          
           for(int i=0;i<tb_jual_bebas.getModel().getRowCount();i++){
            tot=tot+Double.parseDouble(tb_jual_bebas.getModel().getValueAt(i, 5).toString());
          }
       }
     
       return tot;
   }
   
    private Double sumlab(){
     double tot=0.0;
       if(tb_lab.getModel().getRowCount()>0){
          
           for(int i=0;i<tb_lab.getModel().getRowCount();i++){
            tot=tot+Double.parseDouble(tb_lab.getModel().getValueAt(i, 3).toString());
          }
       }
     
       return tot;
   }
   
   private Double sumtindakan(){
     double tot=0.0;
       if(tb_biaya_tindakan.getModel().getRowCount()>0){
          
           for(int i=0;i<tb_biaya_tindakan.getModel().getRowCount();i++){
            tot=tot+Double.parseDouble(tb_biaya_tindakan.getModel().getValueAt(i, 3).toString());
          }
       }
     
       return tot;
   } 
    
   
//   private void cekNaikKelas(){
//      
//     for(int i=0;i<tb_biaya_tindakan.getModel().getRowCount();i++){  
//      if(!lbl_kelas.getText().equals(tb_biaya_tindakan.getModel().getValueAt(i, 8))){
//         lbl_status_naik_kelas.setText("Pasien Naik Kelas dari"+ tb_biaya_tindakan.getModel().getValueAt(i, 8)  + " Ke Kelas " + lbl_kelas.getText());
//       break;
//      }
//      else{
//        lbl_status_naik_kelas.setText("");
//      }
//     }  
//   }
   
    private void setBiayaTindakan(){
      
        try {
            
            datl=new Crud_local();

            datl.readRec_Biayatindakankasir(txt_no_rawat.getText());
            datl.CloseCon();
//        if(datl.modelbiayatindakan.getRowCount()!=0){
//          if(modelbiaya.getRowCount()==0){  
//            for(int i=0;i<datl.modelbiayatindakan.getRowCount();i++){
//             if(lbl_nm_status.getText().equalsIgnoreCase("BPJS")){
//                this.modelbiaya.addRow(new Object[]{datl.modelbiayatindakan.getValueAt(i, 2).toString(),datl.modelbiayatindakan.getValueAt(i, 4).toString()});
//                }
//             else
//               {
//                this.modelbiaya.addRow(new Object[]{datl.modelbiayatindakan.getValueAt(i, 2).toString(),datl.modelbiayatindakan.getValueAt(i, 3).toString()});
//               }
//                if(datl.modelbiayatindakan.getValueAt(0, 5)!=null){
//                             lbl_biaya_reg.setText(datl.modelbiayatindakan.getValueAt(0, 5).toString());
//                }
//            }
            
            dat=new Crud_farmasi();
            Double breg=dat.readRec_BiayaRegkasir(txt_no_rawat.getText());
            dat.CloseCon();
          if(this.Tab_reg.getSelectedIndex()==0){   
            if(breg!=null)
            {
               lbl_biaya_reg.setText(String.valueOf(breg));
               lbl_biaya_adm_ranap.setText("0");
            }
          }
          else{
          lbl_biaya_reg.setText("0");
          
          }
            
//            tb_biaya_tindakan.setModel(modelbiaya);
          if(lbl_nm_status.getText().equals("BPJS")){
            tb_biaya_tindakan.setModel(datl.modelbiayatindakanbpjs);   
          }
          else{
            tb_biaya_tindakan.setModel(datl.modelbiayatindakan);  
          }
          
            lbl_tot_tindakan.setText(Utilitas.formatuang(sumtindakan()));
            setukurantbulunitdetail();
            
//            hpsbiayalab();
            setBiayaLab();
            setBiayaObat();
            
            
            
            lbl_tot_kamar.setText("0");
            
            lbl_tot_jaspel.setText("0");
            
//            hpsbiaya();
            
            //close b
           
            
//          } 
//            
//        } 
        
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
     
    }
    
    private void tb_regMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_regMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
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
 
            setAllTot();

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

    private void bt_pembayaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_pembayaranActionPerformed
        // TODO add your handling code here:
     
     if(!this.cmb_cara_bayar.getSelectedItem().equals("===CARA BAYAR===")){
         
       if(lbl_sisa_tagihan.getText().equals("0")){ 
           JOptionPane.showMessageDialog(null, "Sisa Pembayaran Masih Nol");
         
       }
      else if(lbl_sisa_tagihan.getText().contains("(")){
          JOptionPane.showMessageDialog(null, "Pembayaran Melebihi Tagihan");
      }
      else{
          if(this.lbl_status.getText().equals("A17")||this.lbl_status.getText().equals("2")){
             if(!(txt_bayar_asuransi.getText().equals("")||txt_bayar_asuransi.getText().equals(0)||
                     txt_plafon_bpjs.getText().equals(0)||txt_plafon_bpjs.getText().equals(""))){
                //save
             }
             else{
               JOptionPane.showMessageDialog(null, "Pembayaran Asuransi/Pembayaran BPJS/Coding BPJS masih 0 atau belum diisi!");
             }
          }
          else if(this.lbl_status.getText().equals("A18")||this.lbl_status.getText().equals("7")
                  ||this.lbl_status.getText().equals("8")||this.lbl_status.getText().equals("9")
                  ||this.lbl_status.getText().equals("11")||this.lbl_status.getText().equals("10")){
              if(!(txt_bayar_asuransi.getText().equals("")||txt_bayar_asuransi.getText().equals(0))){
                //save
             }
             else{
               JOptionPane.showMessageDialog(null, "Pembayaran Asuransi/Pembayaran");
             }
          }
          else{
          //save
          }
         
      }
     
     }        
     else
     {
        JOptionPane.showMessageDialog(null, "Anda Belum memilih Cara Pembayaran!");
     }
       
       
    }//GEN-LAST:event_bt_pembayaranActionPerformed

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
        if (evt.getClickCount() == 1) {
            int row = this.tb_reg_inap.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {

                int srow = tb_reg_inap.convertRowIndexToModel(row);
                
               hpsTbbiaya();
                setMasukdatInputInap(srow);
//                setMasukdatInput(srow);
               
                    hapusjaspel();
                    setBiayaTindakan();
//                   setBiayaTindakanRanap();
                 //edit 13 jan 2017
                 setBiayaKamarinap();
                 
                 setAllTot();
                 
                //txt_tarif.requestFocus();
                this.txt_cari_reg.requestFocus();

             

                if (tb_biaya_tindakan.getModel().getRowCount() != 0) {

                    this.filterNorawat();
                }

                filterhistorytindakan(txt_no_rawat.getText(),2);

                cariStatDPJPPPJP();
                setukurantbulunitHistory();
                
               
                ChkJln.setSelected(true);
                jam();
                 setBiayaInap();
                jam();
                
                  setRinci();
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
            
            this.lbl_no_sep.setText(dat.cari_sep(txt_no_rawat.getText()));
            
            dat.CloseCon();
            
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
                
              
                ChkJln.setSelected(true);
                jam();
              
                setBiayaInap();
              
                
                setRinci();
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

    
  private void setBiayaInap(){
    
 
        
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
                long lama=Utilitas.Hitungtgl(dateFormatter.format(dt_tgl_reg2.getDate()),jam,tb_biaya_inap.getModel().getValueAt(i, 2).toString(), 
                        tb_biaya_inap.getModel().getValueAt(i, 3).toString())/24;
                //pasti lebih dari 6 jam
               if(lama!=0){ 
                tb_biaya_inap.getModel().setValueAt(lama, i, 6);
               }
               else{
                  tb_biaya_inap.getModel().setValueAt(1, i, 6);
               }
            
            }
                tb_biaya_inap.getModel().setValueAt(Integer.valueOf(tb_biaya_inap.getModel().getValueAt(i, 6).toString())
                                                   * Double.valueOf(tb_biaya_inap.getModel().getValueAt(i, 1).toString()) ,i,7);
              }
                  
              lbl_tot_kamar.setText(Utilitas.formatuang(sumkamarinap()));
              setAllTot();
                //Utilitas.Hitungtgl(ateFormatter.format(dt_tgl_reg2.getDate()),jam,tb_biaya_inap.getModel().getValueAt(i, 4), tb_biaya_inap.getModel().getValueAt(i, 5))
            } catch (ParseException ex) {
                
                Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      }
       
     
      
      hapusjaspel();
      
      setBiayaJaspel();
       
      setAllTot();
    }
    
    private void bt_prosesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_prosesActionPerformed
        // TODO add your handling code here:
        
        setBiayaInap();
      
    }//GEN-LAST:event_bt_prosesActionPerformed

   
    private int setkode(){
        int i=0;
        try {
            datl=new Crud_local();
             i=datl.readRec_HitDeposit(txt_no_rawat.getText())+1;
            datl.CloseCon();
            
            
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return i;
    }
    
    private Double sumdeposit(){
       
        Double tot=0.0;
        
       for(int i=0;i<this.tb_deposit.getModel().getRowCount();i++){
       
         tot=tot+Double.parseDouble(this.tb_deposit.getModel().getValueAt(i, 1).toString());
       }
        
       this.lbl_tot_deposit.setText(Utilitas.formatuang(tot));
       
       return tot;
    }
    
    private void bt_save_deposit_plafon_bpjsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_save_deposit_plafon_bpjsActionPerformed
        // TODO add your handling code here:
        
       if(!txt_no_rawat.getText().equals("")) 
       {
           try {
               this.txt_kode_deposit.setText("DP-"+setkode()+"/"+this.txt_no_rawat.getText());
               
               datl=new Crud_local();
               datl.readRec_Deposit(txt_no_rawat.getText());
               
               this.tb_deposit_master.setModel(datl.modeldeposit);
               
               datl.CloseCon();
             
               
               this.refreshDeposit();
                
               dlg_deposit.setLocationRelativeTo(this);
               dlg_deposit.setVisible(true);
               
                setukurantb_deposit();
               
           } catch (Exception ex) {
               Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
           }
        
       }
       else{
          JOptionPane.showMessageDialog(null,"Anda Belum Memilih Pasien");
       }
        
    }//GEN-LAST:event_bt_save_deposit_plafon_bpjsActionPerformed

    private void bt_save2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_save2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_save2ActionPerformed

    private void bt_notaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_notaActionPerformed
        // TODO add your handling code here:
        lbl_nota.setText("KSR/"+this.txt_no_rawat.getText());
    }//GEN-LAST:event_bt_notaActionPerformed

    private void carijualbebas(){
    
    try {
            
            datl=new Crud_local();
            datl.readRec_ObatKasirJualBebas(txt_cari_jual_bebas.getText());
            datl.CloseCon();
            
          if(ck_kary.isSelected())  
          { 
            tb_jual_bebas.setModel(datl.modelobatkasirjualbebaskary);
          }
          else{
            tb_jual_bebas.setModel(datl.modelobatkasirjualbebas);  
          }
            setukurantbjual_bebas();
            lbl_tot_jual_bebas.setText(Utilitas.formatuang(sumobatjualbebas()));
            
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setHitung(){
        
        
//        StringUtils.isNullOrEmpty(s)
        
      
//
        if(txt_bayar_cash.getText().trim().equals("")){
           txt_bayar_cash.setText("0");
        }
        
        if(txt_bayar_debet.getText().trim().equals("")){
           txt_bayar_debet.setText("0");
        }
        
        if(txt_bayar_kredit.getText().trim().equals("")){
           txt_bayar_kredit.setText("0");
        }
        
        if(txt_bayar_asuransi.getText().trim().equals("")){
            txt_bayar_asuransi.setText("0");
        }
        
        Double jml=Double.parseDouble(txt_bayar_cash.getText())
                     +Double.parseDouble(txt_bayar_debet.getText())
                     +Double.parseDouble(txt_bayar_kredit.getText())
                     +Double.parseDouble(txt_bayar_asuransi.getText());
        
        Double tot= (alltot-jml);
//                     
//             lbl_total_tagihan.setText(String.valueOf(Double.parseDouble(txt_bayar_cash.getText())
//                     +Double.parseDouble(txt_bayar_debet.getText())
//                     +Double.parseDouble(txt_bayar_kredit.getText())
//                     +Double.parseDouble(txt_bayar_asuransi.getText())));
//             
        if(tot!=jml){     
          lbl_sisa_tagihan.setText(Utilitas.formatuang(tot));
          if(lbl_sisa_tagihan.getText().contains("(")){
             JOptionPane.showMessageDialog(null, "Melebihi Total Pembayaran");
//         txt_bayar_cash.setText("");
//             txt_bayar_debet.setText("");
//             txt_bayar_kredit.setText("");
//             txt_bayar_asuransi.setText("0");
          }
        }
    }
    
    private void ck_karyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ck_karyActionPerformed
        try {
            // TODO add your handling code here:
            datl=new Crud_local();
            datl.readRec_ObatKasirJualBebas("");
            datl.CloseCon();
            tb_jual_bebas.setModel(datl.modelobatkasirjualbebaskary);
            setukurantbjual_bebas();
            lbl_tot_jual_bebas.setText("0");
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_ck_karyActionPerformed

    private void ck_umumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ck_umumActionPerformed
        // TODO add your handling code here:
         try {
            // TODO add your handling code here:
            datl=new Crud_local();
            datl.readRec_ObatKasirJualBebas("");
            datl.CloseCon();
            tb_jual_bebas.setModel(datl.modelobatkasirjualbebas);
            setukurantbjual_bebas();
            
            lbl_tot_jual_bebas.setText("0");
            
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ck_umumActionPerformed

    private void tb_jual_bebasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_jual_bebasMouseReleased
        // TODO add your handling code here:
         if (evt.getClickCount() == 2) {
            int row = this.tb_jual_bebas.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                txt_cari_jual_bebas.setText(tb_jual_bebas.getModel().getValueAt(row, 0).toString());
            }
         }
    }//GEN-LAST:event_tb_jual_bebasMouseReleased

    private void tb_biaya_tindakanMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_biaya_tindakanMouseReleased
        // TODO add your handling code here:
     
        
        if (evt.getClickCount() == 2) {
            int row = this.tb_biaya_tindakan.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                
                //col 13 8
                
              if(!lbl_status_naik_kelas.getText().isEmpty())  {
              
                  settbpilih("",false);
                  
              if(lbl_nm_status.getText().equals("BPJS")){ 
              
               if(!tb_biaya_tindakan.getModel().getValueAt(row, 8).toString().equals(lbl_kelas.getText())){ 
                   
                   
                this.lbl_ket.setText(lbl_status_naik_kelas.getText());
                this.lbl_kode_tindakan.setText(tb_biaya_tindakan.getModel().getValueAt(row, 1).toString());
                this.lbl_nama_tindakan.setText(tb_biaya_tindakan.getModel().getValueAt(row, 2).toString());
                this.lbl_tarif.setText(tb_biaya_tindakan.getModel().getValueAt(row, 3).toString()); 
                lbl_kelas_dlg.setText(tb_biaya_tindakan.getModel().getValueAt(row, 8).toString()); 
                dlg_update_naik_kelas.setLocationRelativeTo(this);
                this.dlg_update_naik_kelas.setVisible(true);
               }
               else{
                 JOptionPane.showMessageDialog(null, "Kelas Sudah Sesuai!");
               }
                
              }
              else{
               
               if(!tb_biaya_tindakan.getModel().getValueAt(row, 13).toString().equals(lbl_kelas.getText())){       
                this.lbl_ket.setText(lbl_status_naik_kelas.getText());
                this.lbl_kode_tindakan.setText(tb_biaya_tindakan.getModel().getValueAt(row, 1).toString());
                this.lbl_nama_tindakan.setText(tb_biaya_tindakan.getModel().getValueAt(row, 2).toString());
                this.lbl_tarif.setText(tb_biaya_tindakan.getModel().getValueAt(row, 3).toString()); 
                lbl_kelas_dlg.setText(tb_biaya_tindakan.getModel().getValueAt(row, 13).toString()); 
                dlg_update_naik_kelas.setLocationRelativeTo(this);
                this.dlg_update_naik_kelas.setVisible(true);
               }
               else{
                 JOptionPane.showMessageDialog(null, "Kelas Sudah Sesuai!");
               }
               
              }
                
              }
                 
            }
        }   
    }//GEN-LAST:event_tb_biaya_tindakanMouseReleased

    private void tb_tindakan_pilihMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_tindakan_pilihMouseReleased
        // TODO add your handling code here:
        
        
               int row = this.tb_tindakan_pilih.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                   try {
                       lbl_kode_tindakan_update.setText(tb_tindakan_pilih.getModel().getValueAt(row, 0).toString());
                       lbl_nama_tindakan_update.setText(tb_tindakan_pilih.getModel().getValueAt(row, 1).toString());
                       
                       datl=new Crud_local();
                       
                       datl.CariKelastindakan(lbl_nama_tindakan_update.getText(), lbl_kelas.getText()
                               , lbl_nm_status.getText());
                       
                       lbl_tarif_update.setText(datl.tarifupdate);
                       lbl_kelas_update.setText(datl.kelasupdate);
                               
                       datl.CloseCon();
                       
                   } catch (Exception ex) {
                       Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
                   }
              
                      
            }
        
    }//GEN-LAST:event_tb_tindakan_pilihMouseReleased

    private void bt_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_batalActionPerformed
        // TODO add your handling code here:
        dlg_update_naik_kelas.setVisible(false);
    }//GEN-LAST:event_bt_batalActionPerformed

    private void tb_biaya_tindakanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_biaya_tindakanMousePressed
        // TODO add your handling code here:
          if (evt.isPopupTrigger()) {

            int row = tb_biaya_tindakan.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                //maatiin heula dieumah teu penting
//                this.Popup_CostSharing.show(tb_biaya_tindakan, evt.getX(), evt.getY());
//                irowhistory = row;

            }

        }
        
    }//GEN-LAST:event_tb_biaya_tindakanMousePressed

    private void mnu_costsharingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnu_costsharingActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_mnu_costsharingActionPerformed

    private void bt_update_tindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_update_tindakanActionPerformed
        try {
            // TODO add your handling code here:
            datl=new Crud_local();
            datl.updateNaikKelas(lbl_kode_tindakan.getText(), lbl_kode_tindakan_update.getText(), txt_no_rawat.getText());
            setBiayaTindakan();
            dlg_update_naik_kelas.setVisible(false);
            
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
                       
       
    }//GEN-LAST:event_bt_update_tindakanActionPerformed

    private void refreshDeposit(){
        try {
            
               datl=new Crud_local();
               datl.readRec_Deposit(txt_no_rawat.getText());
               
               this.tb_deposit_master.setModel(datl.modeldeposit);
            
            
            datl=new Crud_local();
            datl.readRec_Deposit_detail(txt_no_rawat.getText());
            datl.CloseCon();
            
            this.tb_deposit.setModel(datl.modeldetaildeposit);
            
            setukurantb_deposit();
            
            sumdeposit();
            
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void bt_save_depositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_save_depositActionPerformed
        // TODO add your handling code here:
      if(!(this.txt_no_rawat.getText().trim().equals("")||txt_kode_deposit.getText().trim().equals("")||txt_nama_pendeposit.getText().trim().equals("")
              ||txt_tlp_deposit.getText().trim().equals("") ||txt_jml_deposit.getText().trim().equals("")||txt_jml_deposit.getText().trim().equals("0") )){  
            
          try {
              
              datl=new Crud_local();
              datl.Save_Deposit(txt_kode_deposit.getText(), txt_nama_pendeposit.getText(), txt_tlp_deposit.getText(),this.txt_no_rawat.getText());
              datl.CloseCon();
             
              datl=new Crud_local();
              datl.Save_DepositDetail(txt_kode_deposit.getText(), Double.parseDouble(txt_jml_deposit.getText()),  this.lbl_petugas.getText(), "bln");
              datl.CloseCon();
          
              refreshDeposit();
          } catch (Exception ex) {
              Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
      else{
         JOptionPane.showMessageDialog(null, "Data Ada Yg Belum Diisi!");
      }
    }//GEN-LAST:event_bt_save_depositActionPerformed

    private void bt_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_keluarActionPerformed
        // TODO add your handling code here:
        this.dlg_deposit.setVisible(false);
        
    }//GEN-LAST:event_bt_keluarActionPerformed

    private void tb_deposit_masterMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_deposit_masterMouseReleased
        // TODO add your handling code here:
        
         if (evt.isPopupTrigger()) {

            int row = tb_deposit_master.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                
                this.Popup_hapus_deposit.show(tb_deposit_master, evt.getX(), evt.getY());
                irowhistory = row;

            }

        }
        
        
        
               int row = this.tb_deposit_master.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
               try {
            
            datl=new Crud_local();
            datl.readRec_Deposit_detailC(tb_deposit_master.getModel().getValueAt(row, 0).toString());
//            datl.readRec_Deposit_detail(this.txt_no_rawat.getText());
            datl.CloseCon();
            
            this.tb_deposit.setModel(datl.modeldetaildeposit);
            
            setukurantb_deposit();
            
            this.sumdeposit();
            
            
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
                
            }
    }//GEN-LAST:event_tb_deposit_masterMouseReleased

    private void jTabbedPane2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane2StateChanged
        // TODO add your handling code here:
        if(jTabbedPane2.getSelectedIndex()==1){
        
            this.refreshDeposit();
        
        }
    }//GEN-LAST:event_jTabbedPane2StateChanged

    private void mnu_hapus_depositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnu_hapus_depositActionPerformed
        try {
            // TODO add your handling code here:
            datl=new Crud_local();
            
           if (!datl.CekStatus_deposit(txt_kode_deposit.getText()).equals("dpt")){
               
                datl.CloseCon();
               
               int dialogResult = JOptionPane.showConfirmDialog (null, "Apakah Ingin Menghapus Deposit: "+tb_deposit_master.getModel().getValueAt(irowhistory, 1).toString(),"Hapus Data",JOptionPane.YES_NO_OPTION);
            
            if(dialogResult == JOptionPane.YES_OPTION){  
            
             datl=new Crud_local();
             datl.DelRecDeposit(tb_deposit_master.getModel().getValueAt(irowhistory, 0).toString());
            
             datl.CloseCon();
             
             this.refreshDeposit();
             
            }
           }
           else{
               JOptionPane.showMessageDialog(null, "Total Sudah Terdeposit tidak bisa di Edit!");
           }            
          
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_mnu_hapus_depositActionPerformed

    private void tb_deposit_masterMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_deposit_masterMousePressed
        // TODO add your handling code here:
         if (evt.isPopupTrigger()) {

            int row = tb_deposit_master.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                
                this.Popup_hapus_deposit.show(tb_deposit_master, evt.getX(), evt.getY());
                irowhistory = row;

            }

        }
    }//GEN-LAST:event_tb_deposit_masterMousePressed

    private void tb_depositMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_depositMousePressed
        // TODO add your handling code here:
         if (evt.isPopupTrigger()) {

            int row = tb_deposit.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                
                this.Popup_hapus_deposit_detail.show(tb_deposit, evt.getX(), evt.getY());
                irowhistory = row;

            }

        }
    }//GEN-LAST:event_tb_depositMousePressed

    private void tb_depositMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_depositMouseReleased
        // TODO add your handling code here:
          if (evt.isPopupTrigger()) {

            int row = tb_deposit.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                
                this.Popup_hapus_deposit_detail.show(tb_deposit, evt.getX(), evt.getY());
                irowhistory = row;

            }

        }
    }//GEN-LAST:event_tb_depositMouseReleased

    private void mnu_item_hapus_detailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnu_item_hapus_detailActionPerformed
        // TODO add your handling code here:
         try {
            // TODO add your handling code here:
//            int dialogResult = JOptionPane.showConfirmDialog (null, "Apakah Ingin Menghapus Total Deposit?","Hapus Data",JOptionPane.YES_NO_OPTION);
//            
//            if(dialogResult == JOptionPane.YES_OPTION){
            
            txt_jml_deposit.setText(tb_deposit.getModel().getValueAt(irowhistory, 1).toString());
            txt_kode_deposit.setText(tb_deposit.getModel().getValueAt(irowhistory, 0).toString());
            
//            datl=new Crud_local();
//            datl.DelRecDepositdetail(tb_deposit.getModel().getValueAt(irowhistory, 0).toString());
//            
//            datl.CloseCon();
//            
//            this.refreshDeposit();
//            }
    
            
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_mnu_item_hapus_detailActionPerformed

    private void txt_nama_pendepositKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nama_pendepositKeyPressed
        // TODO add your handling code here:
         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_tlp_deposit.requestFocus();
        }
    }//GEN-LAST:event_txt_nama_pendepositKeyPressed

    private void txt_tlp_depositKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_tlp_depositKeyPressed
        // TODO add your handling code here:
          if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_jml_deposit.requestFocus();
        }
    }//GEN-LAST:event_txt_tlp_depositKeyPressed

    private void txt_jml_depositKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jml_depositKeyTyped
        // TODO add your handling code here:
         char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txt_jml_depositKeyTyped

    private void bt_edit_depositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_edit_depositActionPerformed
        try {
            // TODO add your handling code here:
            datl=new Crud_local();
            
            datl.Update_deposit(txt_kode_deposit.getText(), txt_nama_pendeposit.getText(), txt_tlp_deposit.getText());
            
            datl.CloseCon();
            
             datl=new Crud_local();
           
           if (!datl.CekStatus_deposit(txt_kode_deposit.getText()).equals("dpt")){
               
               datl.CloseCon();
               
               datl=new Crud_local();
            
               datl.Update_deposit_total(txt_kode_deposit.getText(),  Double.parseDouble(txt_jml_deposit.getText()));
            
               datl.CloseCon();
           }
           else{
               JOptionPane.showMessageDialog(null, "Total Sudah Terdeposit tidak bisa di Edit!");
           }         
            
            
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_bt_edit_depositActionPerformed

    private void mnu_edit_depositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnu_edit_depositActionPerformed
        try {
            // TODO add your handling code here:
            
            txt_kode_deposit.setText(tb_deposit_master.getModel().getValueAt(irowhistory, 0).toString());
            txt_nama_pendeposit.setText(tb_deposit_master.getModel().getValueAt(irowhistory, 1).toString());
            txt_tlp_deposit.setText(tb_deposit_master.getModel().getValueAt(irowhistory, 2).toString());
            
            datl=new Crud_local();
            
            txt_jml_deposit.setText(String.valueOf(datl.readRec_Deposit_total(tb_deposit_master.getModel().getValueAt(irowhistory, 0).toString())));
            datl.CloseCon();
            
            
             datl=new Crud_local();
            datl.readRec_Deposit_detailC(txt_kode_deposit.getText());
//            datl.readRec_Deposit_detail(this.txt_no_rawat.getText());
            datl.CloseCon();
            
            this.tb_deposit.setModel(datl.modeldetaildeposit);
            
            setukurantb_deposit();
            
            this.sumdeposit();
            
            jTabbedPane2.setSelectedIndex(0);
            
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }//GEN-LAST:event_mnu_edit_depositActionPerformed

    private void bt_cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cetakActionPerformed
        try {
            // TODO add your handling code here:
            datl=new Crud_local();
            datl.CetakDeposit(txt_kode_deposit.getText());
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_cetakActionPerformed

    private void hapusjaspel(){
        
     if(tb_jasa_pelayanan.getModel().getRowCount()!=0){ 
           DefaultTableModel dm = (DefaultTableModel) tb_jasa_pelayanan.getModel();
        int rowCount = dm.getRowCount();
       //x
//        for(int i = rowCount - 1; i >= 0; i--){
        if (rowCount != 0) {
         for(int i = rowCount - 1; i >= 0; i--){
               
            dm.removeRow(i);
          }     
           
        }
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

   private String Carikodekamaribu(){
        
     String kd="";
       
       try {
           datl = new Crud_local();
           String noraw=datl.readRec_cariNoRawatIbu(this.txt_no_rawat.getText());
//           JOptionPane.showMessageDialog(null, "tes: "+noraw);
           datl.CloseCon();
           
            dat = new Crud_farmasi();
             kd=dat.readRec_cariKodekamarIbu(noraw);
//             JOptionPane.showMessageDialog(null, "kmar: "+kd);
            dat.CloseCon();
            
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kd;
   } 
    
  private void setBiayaJaspel(){
  
//      readRec_JasaPelayanan
      this.hapusjaspel();

       try {

           
 if(tb_biaya_inap.getModel().getRowCount()==2&&tb_biaya_inap.getModel().getValueAt(1, 0).toString().contains("BAYI SAKIT")) {
//            JOptionPane.showMessageDialog(null, "skt");
                for(int i=0;i<tb_biaya_inap.getModel().getRowCount();i++){
        
         dat = new Crud_farmasi();   
         
        modelpelayanan.addRow(new Object[]{tb_biaya_inap.getModel().getValueAt(i, 0).toString(),dat.readRec_JasaPelayanan(tb_biaya_inap.getModel().getValueAt(1, 9).toString()),
            tb_biaya_inap.getModel().getValueAt(i, 6).toString(),dat.readRec_JasaPelayanan(tb_biaya_inap.getModel().getValueAt(1, 9).toString())* Integer.valueOf(tb_biaya_inap.getModel().getValueAt(i, 6).toString())});
          dat.CloseCon();  
        
       } 
        
        
        
        
        tb_jasa_pelayanan.setModel(modelpelayanan);
         lbl_tot_jaspel.setText(Utilitas.formatuang(sumjaspel()));
               
           
          
        }            
 else{        
           

           
        for(int i=0;i<tb_biaya_inap.getModel().getRowCount();i++){
        
         dat = new Crud_farmasi();   
         
         //perina jp ngikutin ibunya klo sehat
        if(tb_biaya_inap.getModel().getValueAt(i, 0).toString().contains("BAYI SEHAT")) {
            
          modelpelayanan.addRow(new Object[]{tb_biaya_inap.getModel().getValueAt(i, 0).toString(),dat.readRec_JasaPelayanan(Carikodekamaribu()),
            tb_biaya_inap.getModel().getValueAt(i, 6).toString(),dat.readRec_JasaPelayanan(Carikodekamaribu())* Integer.valueOf(tb_biaya_inap.getModel().getValueAt(i, 6).toString())});
          dat.CloseCon();
          
        }
        
        else{
          modelpelayanan.addRow(new Object[]{tb_biaya_inap.getModel().getValueAt(i, 0).toString(),dat.readRec_JasaPelayanan(tb_biaya_inap.getModel().getValueAt(i, 9).toString()),
            tb_biaya_inap.getModel().getValueAt(i, 6).toString(),dat.readRec_JasaPelayanan(tb_biaya_inap.getModel().getValueAt(i, 9).toString())* Integer.valueOf(tb_biaya_inap.getModel().getValueAt(i, 6).toString())});
          dat.CloseCon();
         }
        
       } 
        
        
        
        
        tb_jasa_pelayanan.setModel(modelpelayanan);
         lbl_tot_jaspel.setText(Utilitas.formatuang(sumjaspel()));
        
 }
       } catch (Exception ex) {
           Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
  }  
  
  
  private void setRinci(){
  
      String[] title=new String[9];
      
      title[0]="Tindakan";
      title[1]="Lab.";
      title[2]="Obat";
      title[3]="Alkes";
      title[4]="Biaya Kamar";
      title[5]="Jasa Pelayanan";
      title[6]="Registrasi";
      title[7]="Registrasi Rawat Inap";
      title[8]="Total Tagihan";
      
      String[] titletot=new String[9];
      
      titletot[0]=lbl_tot_tindakan.getText();
      titletot[1]=lbl_tot_lab.getText();
      titletot[2]=lbl_tot_obat.getText();
      titletot[3]=lbl_tot_alkes.getText();
      titletot[4]=lbl_tot_kamar.getText();
      titletot[5]=lbl_tot_jaspel.getText();
      titletot[6]=lbl_biaya_reg.getText();
      titletot[7]=Utilitas.formatuang(Double.parseDouble(lbl_biaya_adm_ranap.getText()));
      titletot[8]=lbl_total_tagihan.getText();
       
     for(int i=0;i<9;i++){
     
         modelrinci.addRow(new Object[]{title[i],titletot[i]});
     } 
      
     tb_rinci_tagihan.setModel(modelrinci);
      
  
  }
  
  
  private void setAllTot(){
     
     Double totinap= 0.0,totjaspel = 0.0, totlab= 0.0, totobat= 0.0, tottindakan= 0.0;
     
     if(sumkamarinap()!=null){
        totinap=sumkamarinap();
     } 
     
     if(sumjaspel()!=null){
        totjaspel=sumjaspel();
     } 
     
     if(sumlab()!=null){
        totlab=sumlab();
     } 
     
     if(sumjaspel()!=null){
        totobat=sumjaspel();
     }
     
     if(sumtindakan()!=null){
        tottindakan=sumtindakan();
     }
      
     
     Double tot=totjaspel+totinap+totlab+totobat+tottindakan;
    
    if(this.Tab_reg.getSelectedIndex()!=0){ 
     // 5 %
     Double prosentase=tot*0.05;
     
     lbl_biaya_adm_ranap.setText(Utilitas.formatdigit(prosentase));
     
     alltot=tot+prosentase;
    }
    else{
       alltot=tot+Double.parseDouble(lbl_biaya_reg.getText());
    }
     
     lbl_total_tagihan.setText(Utilitas.formatuang(alltot));
     
//     this.setHitung();

 
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
            
          
       if(!lbl_kelas.getText().equals(tb_biaya_inap.getModel().getValueAt(0, 10))){
         lbl_status_naik_kelas.setText("Pasien Naik Kelas dari "+ tb_biaya_inap.getModel().getValueAt(0, 10)  + " Ke " + lbl_kelas.getText());
       }
       else{
         lbl_status_naik_kelas.setText("");
       }
          
            
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
        }
     
  
  }  
  private void setukurantbtindakanpilih() {
        this.tb_tindakan_pilih.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        tb_tindakan_pilih.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel tr = tb_tindakan_pilih.getColumnModel();

        tr.getColumn(0).setPreferredWidth(140);
        tr.getColumn(1).setPreferredWidth(430);
//        tr.getColumn(2).setPreferredWidth(50);

    }  
  
  private void settbpilih(String cari,boolean b) {
        try {
            datl = new Crud_local();

            datl.readRec_cariTarifTemplate(cari, b, 2, lbl_kelas.getText());

            datl.CloseCon();
            
            this.tb_tindakan_pilih.setModel(datl.modeltariftemplate);

//            DefaultTableModel model = (DefaultTableModel)tb_tindakan_pilih.getModel();
          
            
            setukurantbtindakanpilih();
  TableColumn col = tb_tindakan_pilih.getColumnModel().getColumn(2);
            tb_tindakan_pilih.removeColumn(col);
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
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
    private javax.swing.JPopupMenu Popup_CostSharing;
    private javax.swing.JPopupMenu Popup_hapus;
    private javax.swing.JPopupMenu Popup_hapus_deposit;
    private javax.swing.JPopupMenu Popup_hapus_deposit_detail;
    private javax.swing.JPopupMenu Popup_history;
    private javax.swing.JTabbedPane Tab_reg;
    private javax.swing.JToolBar ToolBar;
    private javax.swing.JButton bt_batal;
    private javax.swing.JButton bt_cari_tgl;
    private javax.swing.JButton bt_cari_tgl_tindakan;
    private javax.swing.JButton bt_cari_tindakan;
    private javax.swing.JButton bt_cetak;
    private javax.swing.JButton bt_edit_deposit;
    private javax.swing.JButton bt_keluar;
    private javax.swing.JButton bt_nota;
    private javax.swing.JButton bt_pembayaran;
    private javax.swing.JButton bt_proses;
    private javax.swing.JButton bt_save;
    private javax.swing.JButton bt_save2;
    private javax.swing.JButton bt_save_deposit;
    private javax.swing.JButton bt_save_deposit_plafon_bpjs;
    private javax.swing.JButton bt_update_tindakan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JCheckBox ck_kary;
    private javax.swing.JCheckBox ck_umum;
    private javax.swing.JComboBox<String> cmb_cara_bayar;
    private javax.swing.JDialog dlg_deposit;
    private javax.swing.JDialog dlg_dpjp;
    private javax.swing.JDialog dlg_update_naik_kelas;
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
    private javax.swing.JLabel jLabel22;
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
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
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
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lbl;
    private javax.swing.JLabel lbl_biaya_adm_ranap;
    private javax.swing.JLabel lbl_biaya_reg;
    private javax.swing.JLabel lbl_cari;
    private javax.swing.JLabel lbl_cari1;
    private javax.swing.JLabel lbl_cari2;
    private javax.swing.JLabel lbl_format_jml_deposit;
    private javax.swing.JLabel lbl_jam;
    private javax.swing.JLabel lbl_jamnow;
    private javax.swing.JLabel lbl_kamar_inap;
    private javax.swing.JLabel lbl_kelas;
    private javax.swing.JLabel lbl_kelas_dlg;
    private javax.swing.JLabel lbl_kelas_update;
    private javax.swing.JLabel lbl_ket;
    private javax.swing.JLabel lbl_kode_poli;
    private javax.swing.JLabel lbl_kode_tindakan;
    private javax.swing.JLabel lbl_kode_tindakan_update;
    private javax.swing.JLabel lbl_nama_tindakan;
    private javax.swing.JLabel lbl_nama_tindakan_update;
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
    private javax.swing.JLabel lbl_sisa_tagihan;
    private javax.swing.JLabel lbl_stat_pasien;
    private javax.swing.JLabel lbl_stat_tgl;
    private javax.swing.JLabel lbl_status;
    private javax.swing.JLabel lbl_status_naik_kelas;
    private javax.swing.JLabel lbl_tarif;
    private javax.swing.JLabel lbl_tarif_update;
    private javax.swing.JLabel lbl_tgl_masuk;
    private javax.swing.JLabel lbl_tgl_server;
    private javax.swing.JLabel lbl_tot_alkes;
    private javax.swing.JLabel lbl_tot_deposit;
    private javax.swing.JLabel lbl_tot_jaspel;
    private javax.swing.JLabel lbl_tot_jual_bebas;
    private javax.swing.JLabel lbl_tot_jual_bebas_alkes;
    private javax.swing.JLabel lbl_tot_jual_bebas_grand;
    private javax.swing.JLabel lbl_tot_kamar;
    private javax.swing.JLabel lbl_tot_lab;
    private javax.swing.JLabel lbl_tot_obat;
    private javax.swing.JLabel lbl_tot_obat_grand;
    private javax.swing.JLabel lbl_tot_tindakan;
    private javax.swing.JLabel lbl_total_tagihan;
    private javax.swing.JMenuItem mnu_costsharing;
    private javax.swing.JMenuItem mnu_edit_deposit;
    private javax.swing.JMenuItem mnu_hapus_deposit;
    private javax.swing.JMenuItem mnu_item_hapus_detail;
    private javax.swing.JMenuItem mnu_item_hapus_tindakan;
    private javax.swing.JTable tb_biaya_inap;
    private javax.swing.JTable tb_biaya_tindakan;
    private javax.swing.JTable tb_cari_petugas;
    private javax.swing.JTable tb_deposit;
    private javax.swing.JTable tb_deposit_master;
    private javax.swing.JTable tb_jasa_pelayanan;
    private javax.swing.JTable tb_jual_bebas;
    private javax.swing.JTable tb_lab;
    private javax.swing.JTable tb_obat;
    private javax.swing.JTable tb_reg;
    private javax.swing.JTable tb_reg_inap;
    private javax.swing.JTable tb_rinci_tagihan;
    private javax.swing.JTable tb_tindakan_pilih;
    private javax.swing.JTable tb_unit_detail_history;
    private javax.swing.JTextField txt_bayar_asuransi;
    private javax.swing.JTextField txt_bayar_cash;
    private javax.swing.JTextField txt_bayar_debet;
    private javax.swing.JTextField txt_bayar_kredit;
    private javax.swing.JTextField txt_cari_jual_bebas;
    private javax.swing.JTextField txt_cari_nama_tindakan;
    private javax.swing.JTextField txt_cari_nota;
    private javax.swing.JTextField txt_cari_petugas;
    private javax.swing.JTextField txt_cari_reg;
    private javax.swing.JTextField txt_cari_reg_ranap;
    private javax.swing.JTextField txt_cari_rm;
    private javax.swing.JTextField txt_cari_tindakan;
    private javax.swing.JTextField txt_jml_deposit;
    private javax.swing.JTextField txt_kode_deposit;
    private javax.swing.JTextField txt_nama_pendeposit;
    private javax.swing.JTextField txt_nm_pasien;
    private javax.swing.JTextField txt_no_rawat;
    private javax.swing.JTextField txt_no_rm;
    private javax.swing.JLabel txt_petugas_pilih;
    private javax.swing.JTextField txt_plafon_bpjs;
    private javax.swing.JTextField txt_plafon_bpjs1;
    private javax.swing.JTextField txt_tlp_deposit;
    // End of variables declaration//GEN-END:variables
}
