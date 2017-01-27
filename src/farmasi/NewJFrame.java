/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farmasi;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JRException;
import tools.Utilitas;
import unit_fisio.frm_poli_ralan;
import unit_poli.frm_petugas_poli;
import unit_poli.frm_poli;


/**
 *
 * @author jengcool
 */
public class NewJFrame extends javax.swing.JFrame {

    private int irowhistory = 0;
    private int irowhistoryDetail = 0;
     
    private Double gr=0.0;
    //frm_detail formdetail=new frm_detail();
    String aX="";
    
   private int rows=0;
   
    private String tampilrpt="";
    
    private boolean cek=true;
    
    HashMap<String, String> cbrg = new HashMap<String, String>();
    
    private Crud_farmasi dat;
    
    private Crud_local datl;

    private String formatuang(Double hrg) {

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        //  System.out.printf("Harga Rupiah: %s %n", kursIndonesia.format(harga));

        return kursIndonesia.format(hrg);

    }

   
    String[] brg_title = new String[]{"Kode obat", "Nama Obat", "Satuan"};

    String[] trans_title = new String[]{"No.", "Jml", "Nama Barang", "Harga Satuan", "Total", "Hapus"};

    
    String[] jualbebas_title = new String[]{"No. Nota", "Nama Jual Bebas", "Tgl","Catatan","Petugas"};
    
     public DefaultTableModel modeljualbebashistory = new DefaultTableModel(jualbebas_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };
    
    
    public DefaultTableModel modeltrans = new DefaultTableModel(trans_title, 0) {

        public boolean isCellEditable(int row, int column) {
            if (column == 1 ) {
                return true; //|| column == 3
            } else {
                return false;

            }

        }
    };

    public DefaultTableModel modelbrg = new DefaultTableModel(brg_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };

    private void filterPegawai(String nama) {

        try {
            dat = new Crud_farmasi();

            try {
                dat.readRec_pegawaiF(nama);
                dat.CloseCon();
                
            } catch (SQLException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            tb_cari_petugas.setModel(dat.modelpegawai);
            
            this.setukurantbcaripetugas();
            
        } catch (Exception ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private void filterPegawai() {

        try {
            dat = new Crud_farmasi();

            try {
                dat.readRec_pegawaiF("");
                dat.CloseCon();
                
            } catch (SQLException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            tb_cari_petugas.setModel(dat.modelpegawai);
            
            this.setukurantbcaripetugas();
            
        } catch (Exception ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setukurantbcaripetugas() {
        tb_cari_petugas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tr = this.tb_cari_petugas.getColumnModel();

        tr.getColumn(0).setPreferredWidth(80);
        tr.getColumn(1).setPreferredWidth(280);
        tr.getColumn(2).setPreferredWidth(180);

    } 
     
     
    private void set_pasien(){
    
        int row = this.jtb_registrasi.getSelectedRow();

        if (row == -1) {
            // No row selected
        } else {
            
            this.txt_no_rm.setText(jtb_registrasi.getValueAt(row, 1).toString());
            this.txt_nama_pasien.setText(jtb_registrasi.getValueAt(row, 2).toString());
            this.txt_no_rawat.setText(jtb_registrasi.getValueAt(row, 10).toString());
            this.txt_status_pasien.setText(this.jtb_registrasi.getModel().getValueAt(row, 8).toString());
            this.lbl_nm_status.setText(this.jtb_registrasi.getModel().getValueAt(row, 9).toString());
            this.txt_no_sep.setText(this.jtb_registrasi.getModel().getValueAt(row, 11).toString());
            
            if(!this.txt_no_rawat.getText().isEmpty()){
               lbl_cara_beli.setText("Ralan");
            }
            
             Set_Nonota();
              this.Hapussemua();
              tab_trans.setSelectedIndex(0);
           // jp_rm.setVisible(false);
        }
    
    }
    
    
    private void set_trans() {

//        int row = this.jtb_barang.getSelectedRow();
        int row = rows;
        
//        JOptionPane.showMessageDialog(null, row);
        
        if (row == -1) {
            // No row selected
        } else {

          int i = modeltrans.getRowCount() + 1;   
            
          if(cbrg.size()==0 ) { 

              modeltrans.addRow(new Object[]{i,
                this.txt_hit_jml.getText(),
                jtb_barang.getModel().getValueAt(row, 1).toString(),
                this.txt_hit_harga.getText(),
                hitung()});

            this.jtb_transaksi.setModel(modeltrans);
          
            cbrg.put(jtb_barang.getModel().getValueAt(row, 1).toString(),  jtb_barang.getModel().getValueAt(row, 1).toString());
            
          }
          else{
//              JOptionPane.showMessageDialog(null, cbrg.containsKey(jtb_barang.getModel().getValueAt(row, 1))+" "+jtb_barang.getModel().getValueAt(row, 1));
              
              if(!cbrg.containsKey(jtb_barang.getModel().getValueAt(row, 1)))
              {       
                  modeltrans.addRow(new Object[]{i,
              this.txt_hit_jml.getText(),
                jtb_barang.getModel().getValueAt(row, 1).toString(),
                this.txt_hit_harga.getText(),
                hitung()});

            this.jtb_transaksi.setModel(modeltrans);
          
            cbrg.put(jtb_barang.getModel().getValueAt(row, 1).toString(),  jtb_barang.getModel().getValueAt(row, 1).toString());
              }
              else{
                  JOptionPane.showMessageDialog(null, "Data Obat Ada Yang Sama!");
              }
          
          }
//                
//                lbl_job_desc.setText(jTable2.getModel().getValueAt(row, 1).toString());
//                lbl_pic.setText(jTable2.getModel().getValueAt(row, 2).toString());
//                lbl_req.setText(jTable2.getModel().getValueAt(row, 3).toString());
//                lbl_target.setText(jTable2.getModel().getValueAt(row, 4).toString());
//                lbl_finish.setText(jTable2.getModel().getValueAt(row, 5).toString());
//                lbl_priorty.setText(jTable2.getModel().getValueAt(row, 6).toString());
//                lbl_remark.setText(jTable2.getModel().getValueAt(row, 7).toString());
//              
//               ck_aprove.setSelected(setsel(jTable2.getModel().getValueAt(row, 8).toString()));
//               ck_selesai.setSelected(setsel(jTable2.getModel().getValueAt(row, 9).toString()));
//           jobnum=jTable2.getModel().getValueAt(row, 7).toString();
        }

        
        
        
    }

    public Double hitung() {
        
      Double tot=0.0;

        if ((txt_hit_jml.getText().isEmpty() || txt_hit_harga.getText().isEmpty())) {
            //Double tot=Double.valueOf(this.txt_jml.getText())*Double.valueOf(this.txt_harga_satuan.getText());
            //this.lbl_total.setText(Double.valueOf(tot).toString());
            this.lbl_hit_total.setText("0");
        } else {
            //this.lbl_total.setText("isi");
             tot = Double.valueOf(this.txt_hit_jml.getText()) * Double.valueOf(this.txt_hit_harga.getText());

            String fr = formatuang(tot);
            this.lbl_hit_total.setText(fr);
            
        }
        
//        lbl_grand_tot.setText(formatuang(tot));
        return tot;
    }

    private String tglsekarang() {

        LocalDate localDate = LocalDate.now();

        return DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate);
    }
 
    private void Set_Nonota() {
         //datl = new Crud_local();

        //try {
           // this.txt_nota.setText("PJ-"+LocalDateTime.now().getSecond()+"-"+String.valueOf(datl.readRec_count()+1));
            
        //} catch (SQLException ex) {
          
        if(lbl_cara_beli.getText().equals("Jual Bebas")){
           this.txt_nota.setText("JB"+"/"+ this.lbl_kode.getText()+String.valueOf(LocalDateTime.now().getSecond()+1));
        }
        
        else if(lbl_cara_beli.getText().equals("Karyawan")){
                this.txt_nota.setText("KRY"+"/"+ this.lbl_kode.getText()+String.valueOf(LocalDateTime.now().getSecond()+1));
         }
        else{
            this.txt_nota.setText(this.txt_no_rawat.getText()+"/"+ this.lbl_kode.getText()+String.valueOf(LocalDateTime.now().getSecond()+1));
        }
        
    }
    
    public NewJFrame(){
     initComponents();
    }
    
    public NewJFrame(String nmp,String unit,String kode) throws Exception {
        initComponents();

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
      //  this.setUndecorated(true);
      
      jLabel10.setVisible(false);
      
      lbl_jamnow.setVisible(false);
      txt_nip.setVisible(false);
      jcmb_catatan.setVisible(false);
//      jLabel3.setVisible(false);
//      txt_tgl.setVisible(false);

      this.lbl_kode.setVisible(false);
      
      txt_petugas.setText(nmp);
      lbl_poli.setText(unit);
      lbl_kode.setText(kode);
      
        bt_cari_rm.setVisible(false);

        this.txt_no_rm.requestFocus();

        
//        txt_tgl.setText(tglsekarang());

        //this.jp_barang.setVisible(false);
//        this.jp_rm.setVisible(false); 

        //set tampil report
//        this.jck_rpt.setSelected(true);
        
       //set No nota
      
       
       txt_cari_karyawan_jual_bebas.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
              carihistorykaryawanjualbebas();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               carihistorykaryawanjualbebas();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              carihistorykaryawanjualbebas();
            }
        });
       
       
       txt_cari_petugas.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
              filterPegawai(txt_cari_petugas.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               filterPegawai(txt_cari_petugas.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               filterPegawai(txt_cari_petugas.getText());
            }
        });
       
       
        txt_hit_harga.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               hitung();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               hitung();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               hitung();
            }
        });
       
       txt_hit_jml.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               hitung();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               hitung();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               hitung();
            }
        });
       
       txt_history.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterhistory();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterhistory();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterhistory();
            }

        });
       
       
        txt_cari_rm.getDocument().addDocumentListener(new DocumentListener() {
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
        
        this.txt_barang.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterbrg();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterbrg();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterbrg();
            }

        });

        txt_cari_ralan_ranap.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                carihistoryralanranap();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                carihistoryralanranap();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                carihistoryralanranap();
            }

        });
        
        
        this.txt_harga_satuan.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

                hitung();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                hitung();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                hitung();
            }

        });

        this.txt_jml.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

                hitung();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                hitung();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                hitung();
            }

        });

//        dat = new Crud_farmasi();
//
//        try {
//            dat.readRec_registrasi(tglsekarang());
//        } catch (SQLException ex) {
//            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        jtb_registrasi.setModel(dat.modelreg);


        filterReg();

        filterRegInap();
        
        refreshdatatb();

        caridata();
        
        this.jtb_transaksi.setModel(modeltrans);

        setlebartbl();

        modeltrans.addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {

               
                
                int row = e.getFirstRow();
                int column = e.getColumn();

                if (column == 1 || column == 3) {

                    TableModel model = (TableModel) e.getSource();

                    if (model.getValueAt(row, 1).toString().isEmpty()) {

                        model.setValueAt(0, row, 1);

                    } else if (model.getValueAt(row, 3).toString().isEmpty()) {
                        model.setValueAt(0, row, 3);
                    }

                    int quantity = Integer.valueOf(model.getValueAt(row, 1).toString());
                    Double price = Double.valueOf(model.getValueAt(row, 3).toString());
                    Double value = quantity * price;
                    
                    Double tot=0.0;
                    
                    model.setValueAt(value, row, 4);
                    
                    for(int i=0;i<model.getRowCount();i++)
                    {
                       tot = tot + Double.valueOf(model.getValueAt(i, 4).toString());
                    }
                    lbl_grand_tot.setText(formatuang(tot));
                    
                }

            }
        });

        this.jtb_transaksi.getColumnModel().getColumn(5).setCellRenderer(new ImageRenderer());

         txt_cari_reg_inap.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

               filtertxtInap();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               filtertxtInap();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtertxtInap();
            }
        });
         
         
         
      txt_tgl.setText(lbl_tgl_server.getText().toString());

    }

   
    private void carihistorykaryawanjualbebas(){
      Utilitas.filtertb(txt_cari_karyawan_jual_bebas.getText(), tb_karyawan_jual_bebas, 2, 2);
    }
    
    private void carihistoryralanranap(){
      Utilitas.filtertb(txt_cari_ralan_ranap.getText(), tb_ralan_ranap, 1, 1);
    }
    
    
    private void refreshdatatb(){
        
        try {
            
            datl = new Crud_local();
            datl.readRec_brg();
            datl.CloseCon();
            this.jtb_barang.setModel(datl.modelbrg);
             setukurantbbarang();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
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
                dat.readRec_registrasiRalanFarmasi(this.lbl_tgl_server.getText(),1);
                
                dat.CloseCon();
                
                this.jtb_registrasi.setModel(dat.modelregralanfarmasi);

              setukurantbReg();
//                lbl_stat_tgl.setText("Hasil Pencarian Pasien TGl: "+this.lbl_tgl_server.getText());
    
            } catch (SQLException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "SerVer tidak Terkoneksi");

        }
    }

    
    
    
    private void setukurantbbarang() {
        this.jtb_barang.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        jtb_barang.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel tr = jtb_barang.getColumnModel();

        tr.getColumn(0).setPreferredWidth(100);
        tr.getColumn(1).setPreferredWidth(325);
        tr.getColumn(2).setPreferredWidth(150);
        tr.getColumn(3).setPreferredWidth(150);
        tr.getColumn(4).setPreferredWidth(0);
    }
    
    
    private void setukurantbralanranap() {
        this.tb_ralan_ranap.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        tb_ralan_ranap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel tr = tb_ralan_ranap.getColumnModel();

        tr.getColumn(0).setPreferredWidth(80);
        tr.getColumn(1).setPreferredWidth(208);
        tr.getColumn(2).setPreferredWidth(150);
        tr.getColumn(3).setPreferredWidth(150);
//        tr.getColumn(4).setPreferredWidth(0);
    }
    
     private void setukurantbReg() {
        this.jtb_registrasi.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        jtb_registrasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel tr = jtb_registrasi.getColumnModel();

        tr.getColumn(0).setPreferredWidth(50);
        tr.getColumn(1).setPreferredWidth(80);
        tr.getColumn(2).setPreferredWidth(210);
        tr.getColumn(3).setPreferredWidth(100);
        tr.getColumn(4).setPreferredWidth(100);
    }
     
    private void setTimernota(){
        
        final long timeInterval = 1000;
     
         Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    NewJFrame m=new NewJFrame();
                    m.Set_Nonota();
                } catch (Exception ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    
          Thread thread = new Thread(runnable);
          thread.start();
    }
    
    private void setlebartbldetail() {
        TableColumnModel colm = this.jtb_transaksi.getColumnModel();
        colm.getColumn(0).setPreferredWidth(1);
        colm.getColumn(1).setPreferredWidth(1);
        colm.getColumn(2).setPreferredWidth(150);
        colm.getColumn(3).setPreferredWidth(1);

    }
    
    private void setlebartbl() {
        TableColumnModel colm = this.jtb_transaksi.getColumnModel();
        colm.getColumn(0).setPreferredWidth(1);
        colm.getColumn(1).setPreferredWidth(1);
        colm.getColumn(2).setPreferredWidth(150);
        colm.getColumn(5).setPreferredWidth(1);

    }

    private void filterbrg(String kt) {
        try {
            dat = new Crud_farmasi();
            try {
                
                dat.readRec_brgF(kt);
                
                dat.CloseCon();
                
                this.jtb_barang.setModel(dat.modelbrg);
                
                setukurantbbarang();
                
            } catch (SQLException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

           
        } catch (Exception ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void filterbrg() {
        try {
            datl = new Crud_local();
            try {
                datl.readRec_brgF(this.txt_barang.getText().trim());
                datl.CloseCon();
                this.jtb_barang.setModel(datl.modelbrg);
                setukurantbbarang();
                
            } catch (SQLException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            
        } catch (Exception ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.t
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDlg_itung = new javax.swing.JDialog();
        txt_hit_jml = new javax.swing.JTextField();
        lbl_barang = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_hit_harga = new javax.swing.JTextField();
        bt_det_proses = new javax.swing.JButton();
        lbl_hit_total = new javax.swing.JLabel();
        dlg_dpjp = new javax.swing.JDialog();
        jScrollPane7 = new javax.swing.JScrollPane();
        tb_cari_petugas = new javax.swing.JTable();
        txt_cari_petugas = new javax.swing.JTextField();
        Popup_history = new javax.swing.JPopupMenu();
        item_hapus = new javax.swing.JMenuItem();
        Popup_detail_trans = new javax.swing.JPopupMenu();
        item_edit_detail_trans = new javax.swing.JMenuItem();
        item_hapus_detail_trans = new javax.swing.JMenuItem();
        dlg_edit_trans = new javax.swing.JDialog();
        lbl_barang1 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txt_hit_harga1 = new javax.swing.JTextField();
        bt_det_proses1 = new javax.swing.JButton();
        lbl_hit_total1 = new javax.swing.JLabel();
        txt_hit_jml1 = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        bt_cari_rm = new javax.swing.JButton();
        txt_no_rm = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txt_nama_pasien = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_nota = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jp_barang = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtb_barang = new javax.swing.JTable();
        txt_barang = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_jml = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_harga_satuan = new javax.swing.JTextField();
        lbl_total = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ck_jual_karyawan_bebas = new javax.swing.JCheckBox();
        ck_jual_bebas = new javax.swing.JCheckBox();
        txt_tgl = new javax.swing.JLabel();
        lbl_petugas_input = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        bt_hapus = new javax.swing.JButton();
        bt_simpan = new javax.swing.JButton();
        jck_rpt = new javax.swing.JCheckBox();
        lbl_grand_tot = new javax.swing.JLabel();
        bt_print_ulang = new javax.swing.JButton();
        tab_trans = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtb_transaksi = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jtb_transaksi1 = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        txt_no_sep = new javax.swing.JTextField();
        txt_catatan = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txt_status_pasien = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jcmb_catatan = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        txt_no_rawat = new javax.swing.JTextField();
        lbl_nm_status = new javax.swing.JTextField();
        lbl_cara_beli = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_nip = new javax.swing.JTextField();
        txt_nama_pegawai = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txt_nama_jual_bebas = new javax.swing.JTextField();
        ck_jual_karyawan = new javax.swing.JCheckBox();
        ck_kasbon = new javax.swing.JCheckBox();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtb_history = new javax.swing.JTable();
        jtgl_history = new uz.ncipro.calendar.JDateTimePicker();
        txt_history = new javax.swing.JTextField();
        bt_cari_history = new javax.swing.JButton();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        txt_cari_rm = new javax.swing.JTextField();
        bt_proses_cari_registrasi = new javax.swing.JButton();
        jtgl = new uz.ncipro.calendar.JDateTimePicker();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtb_registrasi = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        txt_cari_reg_inap = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tb_reg_inap = new javax.swing.JTable();
        lbl_cari1 = new javax.swing.JLabel();
        lbl_kamar_inap = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        txt_cari_ralan_ranap = new javax.swing.JTextField();
        lbl_cari2 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tb_ralan_ranap = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tb_karyawan_jual_bebas = new javax.swing.JTable();
        txt_cari_karyawan_jual_bebas = new javax.swing.JTextField();
        lbl_cari3 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tb_jual_bebas = new javax.swing.JTable();
        txt_cari_reg_inap3 = new javax.swing.JTextField();
        lbl_cari4 = new javax.swing.JLabel();
        lbl_kode = new javax.swing.JLabel();
        ToolBar = new javax.swing.JToolBar();
        jPanel6 = new javax.swing.JPanel();
        lbl_jam = new javax.swing.JLabel();
        lbl_poli3 = new javax.swing.JLabel();
        lbl_news = new javax.swing.JLabel();
        lbl_tgl_server = new javax.swing.JLabel();
        lbl_jamnow = new javax.swing.JLabel();
        txt_petugas = new javax.swing.JLabel();
        lbl_poli = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        jDlg_itung.setModal(true);
        jDlg_itung.setSize(new java.awt.Dimension(400, 250));

        txt_hit_jml.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_hit_jml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hit_jmlActionPerformed(evt);
            }
        });
        txt_hit_jml.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_hit_jmlKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_hit_jmlKeyPressed(evt);
            }
        });

        jLabel12.setText("Jml");

        jLabel13.setText("Harga");

        txt_hit_harga.setEditable(false);
        txt_hit_harga.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_hit_harga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hit_hargaActionPerformed(evt);
            }
        });
        txt_hit_harga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_hit_hargaKeyPressed(evt);
            }
        });

        bt_det_proses.setText("Proses");
        bt_det_proses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_det_prosesActionPerformed(evt);
            }
        });
        bt_det_proses.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bt_det_prosesKeyPressed(evt);
            }
        });

        lbl_hit_total.setText("RP.");

        javax.swing.GroupLayout jDlg_itungLayout = new javax.swing.GroupLayout(jDlg_itung.getContentPane());
        jDlg_itung.getContentPane().setLayout(jDlg_itungLayout);
        jDlg_itungLayout.setHorizontalGroup(
            jDlg_itungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDlg_itungLayout.createSequentialGroup()
                .addGroup(jDlg_itungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jDlg_itungLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jDlg_itungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addGroup(jDlg_itungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_hit_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_hit_jml, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jDlg_itungLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jDlg_itungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bt_det_proses, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                            .addComponent(lbl_hit_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDlg_itungLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lbl_barang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jDlg_itungLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txt_hit_harga, txt_hit_jml});

        jDlg_itungLayout.setVerticalGroup(
            jDlg_itungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDlg_itungLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lbl_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDlg_itungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_hit_jml, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jDlg_itungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_hit_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_hit_total)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(bt_det_proses)
                .addGap(20, 20, 20))
        );

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
        jScrollPane7.setViewportView(tb_cari_petugas);

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
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                    .addComponent(txt_cari_petugas))
                .addContainerGap())
        );
        dlg_dpjpLayout.setVerticalGroup(
            dlg_dpjpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dlg_dpjpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_cari_petugas, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        item_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/delete_ic.png"))); // NOI18N
        item_hapus.setText("Hapus");
        item_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_hapusActionPerformed(evt);
            }
        });
        Popup_history.add(item_hapus);

        item_edit_detail_trans.setIcon(new javax.swing.ImageIcon(getClass().getResource("/farmasi/edit_ic.png"))); // NOI18N
        item_edit_detail_trans.setText("Edit");
        item_edit_detail_trans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_edit_detail_transActionPerformed(evt);
            }
        });
        Popup_detail_trans.add(item_edit_detail_trans);

        item_hapus_detail_trans.setIcon(new javax.swing.ImageIcon(getClass().getResource("/farmasi/hps_ico.png"))); // NOI18N
        item_hapus_detail_trans.setText("Hapus");
        item_hapus_detail_trans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_hapus_detail_transActionPerformed(evt);
            }
        });
        Popup_detail_trans.add(item_hapus_detail_trans);

        dlg_edit_trans.setPreferredSize(new java.awt.Dimension(418, 222));
        dlg_edit_trans.setSize(new java.awt.Dimension(400, 250));

        jLabel20.setText("Jml");

        jLabel21.setText("Harga");

        txt_hit_harga1.setEditable(false);
        txt_hit_harga1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_hit_harga1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hit_harga1ActionPerformed(evt);
            }
        });
        txt_hit_harga1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_hit_harga1KeyPressed(evt);
            }
        });

        bt_det_proses1.setText("Proses");
        bt_det_proses1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_det_proses1ActionPerformed(evt);
            }
        });
        bt_det_proses1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bt_det_proses1KeyPressed(evt);
            }
        });

        lbl_hit_total1.setText("RP.");

        txt_hit_jml1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_hit_jml1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hit_jml1ActionPerformed(evt);
            }
        });
        txt_hit_jml1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_hit_jml1KeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_hit_jml1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout dlg_edit_transLayout = new javax.swing.GroupLayout(dlg_edit_trans.getContentPane());
        dlg_edit_trans.getContentPane().setLayout(dlg_edit_transLayout);
        dlg_edit_transLayout.setHorizontalGroup(
            dlg_edit_transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlg_edit_transLayout.createSequentialGroup()
                .addGroup(dlg_edit_transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(dlg_edit_transLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(dlg_edit_transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addGroup(dlg_edit_transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_hit_harga1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_hit_jml1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(dlg_edit_transLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(dlg_edit_transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bt_det_proses1, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                            .addComponent(lbl_hit_total1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dlg_edit_transLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lbl_barang1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dlg_edit_transLayout.setVerticalGroup(
            dlg_edit_transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlg_edit_transLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lbl_barang1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dlg_edit_transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_hit_jml1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(dlg_edit_transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_hit_harga1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_hit_total1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(bt_det_proses1)
                .addGap(20, 20, 20))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jTabbedPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabbedPane1KeyPressed(evt);
            }
        });

        jPanel3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel3KeyPressed(evt);
            }
        });
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bt_cari_rm.setText("...");
        bt_cari_rm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cari_rmActionPerformed(evt);
            }
        });
        jPanel3.add(bt_cari_rm, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 50, 31, -1));

        txt_no_rm.setEditable(false);
        txt_no_rm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_no_rmKeyPressed(evt);
            }
        });
        jPanel3.add(txt_no_rm, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 239, -1));

        jLabel1.setText("No. RM");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        txt_nama_pasien.setEditable(false);
        txt_nama_pasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nama_pasienKeyPressed(evt);
            }
        });
        jPanel3.add(txt_nama_pasien, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 239, -1));

        jLabel2.setText("Nama ");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 117, 19));

        txt_nota.setEditable(false);
        txt_nota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_notaKeyPressed(evt);
            }
        });
        jPanel3.add(txt_nota, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 170, -1));

        jLabel4.setText("Nama Karyawan");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 110, 117, 19));

        jLabel5.setText("Cara beli");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 140, -1, -1));

        jp_barang.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jp_barang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtb_barang.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        jtb_barang.setModel(new javax.swing.table.DefaultTableModel(
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
        jtb_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jtb_barangMouseReleased(evt);
            }
        });
        jtb_barang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtb_barangKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtb_barangKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(jtb_barang);

        jp_barang.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 68, 840, 110));

        txt_barang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_barang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_barangKeyPressed(evt);
            }
        });
        jp_barang.add(txt_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 32, 839, 30));

        jLabel8.setText("x");
        jLabel8.setVisible(false);
        jp_barang.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1024, 29, -1, -1));

        txt_jml.setVisible(false);
        txt_jml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jmlActionPerformed(evt);
            }
        });
        txt_jml.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_jmlKeyPressed(evt);
            }
        });
        jp_barang.add(txt_jml, new org.netbeans.lib.awtextra.AbsoluteConstraints(913, 29, 93, 27));

        jLabel7.setText("Jml");
        jLabel7.setVisible(false);
        jp_barang.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(865, 29, 30, 19));

        jLabel9.setText("Harga Satuan");
        jLabel9.setVisible(false);
        jp_barang.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1043, 29, 100, -1));

        txt_harga_satuan.setVisible(false);
        txt_harga_satuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_harga_satuanKeyPressed(evt);
            }
        });
        jp_barang.add(txt_harga_satuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1155, 29, 133, 29));

        lbl_total.setVisible(false);
        lbl_total.setText("Rp.");
        jp_barang.add(lbl_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(1292, 34, -1, 19));

        jLabel6.setText("Nama Barang");
        jp_barang.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 117, 19));

        ck_jual_karyawan_bebas.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        ck_jual_karyawan_bebas.setText("Karyawan Jual Bebas");
        ck_jual_karyawan_bebas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ck_jual_karyawan_bebasStateChanged(evt);
            }
        });
        ck_jual_karyawan_bebas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ck_jual_karyawan_bebasActionPerformed(evt);
            }
        });
        jp_barang.add(ck_jual_karyawan_bebas, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, -1, -1));

        ck_jual_bebas.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        ck_jual_bebas.setText("Jual Bebas");
        ck_jual_bebas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ck_jual_bebasActionPerformed(evt);
            }
        });
        jp_barang.add(ck_jual_bebas, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, -1, -1));

        txt_tgl.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jp_barang.add(txt_tgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 120, 20));

        lbl_petugas_input.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        lbl_petugas_input.setText("Petugas Input:");
        jp_barang.add(lbl_petugas_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 200, 19));

        jLabel19.setText("tgl");
        jp_barang.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 30, 19));

        jPanel3.add(jp_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 860, 190));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bt_hapus.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        bt_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/farmasi/hps_ico.png"))); // NOI18N
        bt_hapus.setText("Hapus Semua");
        bt_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_hapusActionPerformed(evt);
            }
        });
        jPanel1.add(bt_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(693, 15, 150, -1));

        bt_simpan.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        bt_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/farmasi/save_ico.png"))); // NOI18N
        bt_simpan.setText("Save & Print");
        bt_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_simpanActionPerformed(evt);
            }
        });
        jPanel1.add(bt_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 15, 150, -1));

        jck_rpt.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jck_rpt.setText(" Tampilkan Report");
        jck_rpt.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jck_rptItemStateChanged(evt);
            }
        });
        jPanel1.add(jck_rpt, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, 130, -1));

        lbl_grand_tot.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_grand_tot.setText("Rp.");
        jPanel1.add(lbl_grand_tot, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 220, 20));

        bt_print_ulang.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        bt_print_ulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/farmasi/print_ico.png"))); // NOI18N
        bt_print_ulang.setText("Print Ulang");
        bt_print_ulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_print_ulangActionPerformed(evt);
            }
        });
        jPanel1.add(bt_print_ulang, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 15, 160, 30));

        jtb_transaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jtb_transaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jtb_transaksiMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jtb_transaksiMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtb_transaksiMouseClicked(evt);
            }
        });
        jtb_transaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtb_transaksiKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jtb_transaksi);

        tab_trans.addTab("Input Data", jScrollPane2);

        jtb_transaksi1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jtb_transaksi1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jtb_transaksi1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jtb_transaksi1MouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtb_transaksi1MouseClicked(evt);
            }
        });
        jtb_transaksi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtb_transaksi1KeyPressed(evt);
            }
        });
        jScrollPane11.setViewportView(jtb_transaksi1);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 825, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 825, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 163, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        tab_trans.addTab("Data History Obat", jPanel9);

        jPanel1.add(tab_trans, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 830, 190));

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 860, 260));

        jLabel11.setText("Keterangan");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, -1, -1));

        txt_no_sep.setEditable(false);
        txt_no_sep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_no_sepKeyPressed(evt);
            }
        });
        jPanel3.add(txt_no_sep, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 50, 170, -1));

        txt_catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_catatanKeyPressed(evt);
            }
        });
        jPanel3.add(txt_catatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, 170, -1));

        jLabel14.setText("No SEP");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 50, -1, -1));

        txt_status_pasien.setEditable(false);
        txt_status_pasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_status_pasienKeyPressed(evt);
            }
        });
        jPanel3.add(txt_status_pasien, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 60, -1));

        jLabel15.setText("Status");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        jcmb_catatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rawat Jalan", "Rawat Inap", "Penjualan Bebas", "BPJS" }));
        jcmb_catatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmb_catatanActionPerformed(evt);
            }
        });
        jPanel3.add(jcmb_catatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 140, 130, -1));

        jLabel16.setText("No. Rawat");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        txt_no_rawat.setEditable(false);
        txt_no_rawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_no_rawatKeyPressed(evt);
            }
        });
        jPanel3.add(txt_no_rawat, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 239, -1));

        lbl_nm_status.setEditable(false);
        lbl_nm_status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lbl_nm_statusKeyPressed(evt);
            }
        });
        jPanel3.add(lbl_nm_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 170, -1));

        lbl_cara_beli.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        lbl_cara_beli.setText("       ");
        jPanel3.add(lbl_cara_beli, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 140, 120, -1));

        jLabel17.setText("No. Nota");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 80, 117, 19));

        txt_nip.setEditable(false);
        txt_nip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nipKeyPressed(evt);
            }
        });
        jPanel3.add(txt_nip, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 110, 60, -1));

        txt_nama_pegawai.setEditable(false);
        txt_nama_pegawai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nama_pegawaiKeyPressed(evt);
            }
        });
        jPanel3.add(txt_nama_pegawai, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 110, 270, -1));

        jLabel18.setText("Nama Jual Bebas");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 140, 19));

        txt_nama_jual_bebas.setEditable(false);
        txt_nama_jual_bebas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nama_jual_bebasKeyPressed(evt);
            }
        });
        jPanel3.add(txt_nama_jual_bebas, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 239, -1));

        ck_jual_karyawan.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        ck_jual_karyawan.setText("Penjualan Karyawan");
        ck_jual_karyawan.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ck_jual_karyawanStateChanged(evt);
            }
        });
        ck_jual_karyawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ck_jual_karyawanActionPerformed(evt);
            }
        });
        jPanel3.add(ck_jual_karyawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(739, 140, 150, -1));

        ck_kasbon.setText("Kasbon");
        jPanel3.add(ck_kasbon, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 20, -1, -1));

        jTabbedPane1.addTab("Penjualan", jPanel3);

        jLayeredPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jtb_history.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(jtb_history);

        jtgl_history.setDisplayFormat("yyyy/MM/dd");

        bt_cari_history.setText("Cari RM");
        bt_cari_history.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cari_historyActionPerformed(evt);
            }
        });

        jLayeredPane2.setLayer(jScrollPane5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jtgl_history, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(txt_history, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(bt_cari_history, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(jtgl_history, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_history, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bt_cari_history, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 357, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtgl_history, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bt_cari_history))
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(txt_history)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLayeredPane1.setLayer(jLayeredPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane2)
                .addContainerGap())
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(379, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("History Penjualan Obat", jLayeredPane1);

        jLayeredPane3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLayeredPane4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_cari_rm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cari_rmKeyPressed(evt);
            }
        });
        jLayeredPane4.add(txt_cari_rm, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 7, 220, 30));

        bt_proses_cari_registrasi.setText("cari");
        bt_proses_cari_registrasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_proses_cari_registrasiActionPerformed(evt);
            }
        });
        jLayeredPane4.add(bt_proses_cari_registrasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 360, 30));

        jtgl.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jtgl.setDisplayFormat("yyyy/MM/dd");
        jLayeredPane4.add(jtgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 121, -1));

        jtb_registrasi.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        jtb_registrasi.setModel(new javax.swing.table.DefaultTableModel(
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
        jtb_registrasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jtb_registrasiMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jtb_registrasi);

        jLayeredPane4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 360, 500));

        jTabbedPane2.addTab("Ralan", jLayeredPane4);

        txt_cari_reg_inap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cari_reg_inapKeyPressed(evt);
            }
        });

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

        lbl_cari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/carik_ico.png"))); // NOI18N
        lbl_cari1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_cari1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_kamar_inap, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txt_cari_reg_inap, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(lbl_cari1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_cari_reg_inap, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_cari1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_kamar_inap, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Ranap", jPanel4);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane3.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_cari_ralan_ranap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cari_ralan_ranapKeyPressed(evt);
            }
        });
        jPanel5.add(txt_cari_ralan_ranap, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 12, 310, 32));

        lbl_cari2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/carik_ico.png"))); // NOI18N
        lbl_cari2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_cari2MouseClicked(evt);
            }
        });
        jPanel5.add(lbl_cari2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 40, -1));

        tb_ralan_ranap.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        tb_ralan_ranap.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_ralan_ranap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tb_ralan_ranapMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_ralan_ranapMouseReleased(evt);
            }
        });
        tb_ralan_ranap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tb_ralan_ranapKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tb_ralan_ranapKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(tb_ralan_ranap);

        jPanel5.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 350, 500));

        jTabbedPane3.addTab("Ralan/Ranap", jPanel5);

        tb_karyawan_jual_bebas.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        tb_karyawan_jual_bebas.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_karyawan_jual_bebas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tb_karyawan_jual_bebasMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_karyawan_jual_bebasMouseReleased(evt);
            }
        });
        tb_karyawan_jual_bebas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tb_karyawan_jual_bebasKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tb_karyawan_jual_bebasKeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(tb_karyawan_jual_bebas);

        txt_cari_karyawan_jual_bebas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cari_karyawan_jual_bebasKeyPressed(evt);
            }
        });

        lbl_cari3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/carik_ico.png"))); // NOI18N
        lbl_cari3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_cari3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txt_cari_karyawan_jual_bebas, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(lbl_cari3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txt_cari_karyawan_jual_bebas, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_cari3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane3.addTab("Karyawan Jual Bebas", jPanel7);

        tb_jual_bebas.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
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
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tb_jual_bebasMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_jual_bebasMouseReleased(evt);
            }
        });
        tb_jual_bebas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tb_jual_bebasKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tb_jual_bebasKeyPressed(evt);
            }
        });
        jScrollPane10.setViewportView(tb_jual_bebas);

        txt_cari_reg_inap3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cari_reg_inap3KeyPressed(evt);
            }
        });

        lbl_cari4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/carik_ico.png"))); // NOI18N
        lbl_cari4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_cari4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 5, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txt_cari_reg_inap3, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(lbl_cari4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txt_cari_reg_inap3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_cari4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Jual Bebas", jPanel8);

        jPanel2.add(jTabbedPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 370, 590));

        jTabbedPane2.addTab("History Penjualan", jPanel2);

        jLayeredPane3.setLayer(jTabbedPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(lbl_kode, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane3Layout = new javax.swing.GroupLayout(jLayeredPane3);
        jLayeredPane3.setLayout(jLayeredPane3Layout);
        jLayeredPane3Layout.setHorizontalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane3Layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119)
                .addComponent(lbl_kode)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane3Layout.setVerticalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane3Layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(lbl_kode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(525, 525, 525))
            .addGroup(jLayeredPane3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ToolBar.setBackground(new java.awt.Color(255, 255, 255));
        ToolBar.setFloatable(false);
        ToolBar.setRollover(true);
        ToolBar.setPreferredSize(new java.awt.Dimension(100, 60));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        lbl_jam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/jamk_ico.png"))); // NOI18N

        lbl_poli3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/log_out.png"))); // NOI18N
        lbl_poli3.setText("Logout");
        lbl_poli3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_poli3MouseClicked(evt);
            }
        });

        lbl_news.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/news_ico.png"))); // NOI18N
        lbl_news.setText("Info");

        lbl_jamnow.setText("sssss");

        txt_petugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/farmasi/pegawai_ico.png"))); // NOI18N

        lbl_poli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/hospital_ico.png"))); // NOI18N

        jLabel10.setText("F1 : Print  dan Save");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_jam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_tgl_server, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_jamnow, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_petugas)
                .addGap(42, 42, 42)
                .addComponent(lbl_news)
                .addGap(18, 18, 18)
                .addComponent(lbl_poli3)
                .addGap(18, 18, 18)
                .addComponent(lbl_poli, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(573, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_petugas, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_news)
                        .addComponent(lbl_poli3))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbl_jam)
                        .addComponent(lbl_poli)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_tgl_server, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_jamnow))
                        .addGap(15, 15, 15)))
                .addGap(186, 186, 186))
        );

        ToolBar.add(jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 927, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(446, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(ToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane3)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_notaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_notaKeyPressed
        // TODO add your handling code here:
        
         if (evt.getKeyCode() == KeyEvent.VK_F1) {
            savePrint();
        }
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.txt_catatan.requestFocus();

        }
    }//GEN-LAST:event_txt_notaKeyPressed

    private void txt_nama_pasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nama_pasienKeyPressed
        // TODO add your handling code here:
         if (evt.getKeyCode() == KeyEvent.VK_F1) {
            savePrint();
        }
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.txt_nota.requestFocus();

        }
    }//GEN-LAST:event_txt_nama_pasienKeyPressed

    
    
    private void txt_no_rmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_no_rmKeyPressed

        
        // TODO add your handling code here:
        
        if (evt.getKeyCode() == KeyEvent.VK_F1) {
            savePrint();
        }

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            
           if(this.txt_no_rm.getText().isEmpty()){ 
            this.txt_nama_pasien.requestFocus();
           }
           else{
           
                try {
                dat = new Crud_farmasi();
                dat.readRec_registrasiRM(this.txt_no_rm.getText(), tglsekarang());
                
              if(dat.modelreg.getRowCount()>0){  
                    this.txt_no_rm.setText(dat.modelreg.getValueAt(0, 1).toString());
                    this.txt_nama_pasien.setText(dat.modelreg.getValueAt(0, 2).toString());
                    this.txt_nama_pasien.requestFocus();
              }
              else{
                      this.txt_nama_pasien.setText("");
                      this.txt_no_rm.requestFocus();
              }
                // ga ketemu cari di data pasien
                if(this.txt_nama_pasien.getText().isEmpty()){
                     dat = new Crud_farmasi();
                     this.txt_nama_pasien.setText(dat.readRec_pasien(this.txt_no_rm.getText()));
                     if(this.txt_nama_pasien.getText().isEmpty()){
                     JOptionPane.showMessageDialog(null, "Data Tidak di Temukan!");
                      this.txt_nama_pasien.setText("");
                      this.txt_no_rm.requestFocus();
                     }
                     else{
                     this.txt_nama_pasien.requestFocus();
                     }
                }
                
                
              
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(null, "Data Tidak di Temukan!");
                 this.txt_nama_pasien.setText("");
                 this.txt_no_rm.requestFocus();
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
           }  

        }

        if ((evt.getKeyCode() == KeyEvent.VK_C) && ((evt.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
           // this.jp_rm.setVisible(true);
            txt_cari_rm.requestFocus();

        }

    }//GEN-LAST:event_txt_no_rmKeyPressed

    private void bt_cari_rmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_rmActionPerformed
        // TODO add your handling code here:
//        if (jp_rm.isVisible() == false) {
//            jp_rm.setVisible(true);
//        } else {
//            jp_rm.setVisible(false);
//        }

    }//GEN-LAST:event_bt_cari_rmActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        
         if (evt.getKeyCode() == KeyEvent.VK_F1) {
            savePrint();
        }
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //   this.jp_barang.setVisible(false);
            // this.jp_rm.setVisible(false);
            // System.out.print("tess");

        }
    }//GEN-LAST:event_formKeyPressed

    private void jTabbedPane1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabbedPane1KeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_jTabbedPane1KeyPressed

    private void jPanel3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel3KeyPressed

    private void txt_barangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_barangKeyPressed
        // TODO add your handling code here:
         if (evt.getKeyCode() == KeyEvent.VK_F1) {
            savePrint();
        }
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.jtb_barang.requestFocus();
            //jtb_barang.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            jtb_barang.setRowSelectionInterval(0, 0);
            

        }
    }//GEN-LAST:event_txt_barangKeyPressed

    private void txt_jmlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jmlActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_jmlActionPerformed

    private void txt_jmlKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jmlKeyPressed
        // TODO add your handling code here:

//        Double tot = Double.valueOf(this.txt_jml.getText()) * Double.valueOf(this.txt_harga_satuan.getText());
//        this.lbl_total.setText(Double.valueOf(tot).toString());
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.txt_harga_satuan.requestFocus();

        }

    }//GEN-LAST:event_txt_jmlKeyPressed

    private void txt_cari_rmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cari_rmKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

        }
    }//GEN-LAST:event_txt_cari_rmKeyPressed

    private void set_Historyjualbebas(){
    
      
       int row = this.tb_jual_bebas.getSelectedRow();

        if (row == -1) {
            // No row selected
        } else {
            
//{nmrm,nama_pasien,carabeli,noraw,jb,nik,nmp
//                                                   ,statcetak,petugas,catatan,nota,kdpj,nmpj,nosep,tgl});
             int srow =row; //tb_ralan_ranap.convertRowIndexToModel(row);
            
        
//        if(tb_karyawan_jual_bebas.getValueAt(srow, 2)!=null){
//         lbl_cara_beli.setText(tb_ralan_ranap.getValueAt(srow, 2).toString());
//        } 
        
        lbl_cara_beli.setText("Jual Bebas");
      
        if(tb_jual_bebas.getValueAt(srow, 1)!=null){
            txt_nama_jual_bebas.setText(tb_jual_bebas.getValueAt(srow, 1).toString());
        }
        
        if(tb_jual_bebas.getValueAt(srow, 4)!=null){
          lbl_petugas_input.setText("Petugas Input: "+tb_jual_bebas.getValueAt(srow, 4).toString());
        }
        
        if(tb_jual_bebas.getValueAt(srow, 3)!=null){
         txt_catatan.setText(tb_jual_bebas.getValueAt(srow, 3).toString());
        }
        
       if(tb_jual_bebas.getValueAt(srow, 0)!=null){
         txt_nota.setText(tb_jual_bebas.getValueAt(srow, 0).toString());
        }
       
       if(tb_jual_bebas.getValueAt(srow, 2)!=null){
          txt_tgl.setText(tb_jual_bebas.getValueAt(srow, 2).toString());
        }
             set_Historydetail();
             
             ck_jual_karyawan_bebas.setSelected(false);
             ck_jual_bebas.setSelected(false);
             ck_jual_karyawan.setSelected(false);
           // jp_rm.setVisible(false);
            }
     
    
    }
    
    private void set_Historydetail(){
       try {
            
            datl = new Crud_local();
            datl.readRec_transfarmasidetail(txt_nota.getText());
            datl.CloseCon();
            this.jtb_transaksi1.setModel(datl.modeltrans);
            tab_trans.setSelectedIndex(1);
            setlebartbldetail();
            
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
    
    private void set_Historyralanranap(){
     
      
       int row = this.tb_ralan_ranap.getSelectedRow();

        if (row == -1) {
            // No row selected
        } else {
            
//{nmrm,nama_pasien,carabeli,noraw,jb,nik,nmp
//                                                   ,statcetak,petugas,catatan,nota,kdpj,nmpj,nosep,tgl});
             int srow =row; //tb_ralan_ranap.convertRowIndexToModel(row);
            
        if(tb_ralan_ranap.getValueAt(srow, 0)!=null){
           this.txt_no_rm.setText(tb_ralan_ranap.getValueAt(srow, 0).toString());
        }
        
        if(tb_ralan_ranap.getValueAt(srow, 1)!=null){
            this.txt_nama_pasien.setText(tb_ralan_ranap.getValueAt(srow, 1).toString());
        }
        
        if(tb_ralan_ranap.getValueAt(srow, 2)!=null){
         lbl_cara_beli.setText(tb_ralan_ranap.getValueAt(srow, 2).toString());
        } 
        
        if(tb_ralan_ranap.getValueAt(srow, 3)!=null){
         this.txt_no_rawat.setText(tb_ralan_ranap.getValueAt(srow, 3).toString());
        }
        
        if(tb_ralan_ranap.getValueAt(srow, 4)!=null){
            txt_nama_jual_bebas.setText(tb_ralan_ranap.getValueAt(srow, 4).toString());
        }
       
        if(tb_ralan_ranap.getValueAt(srow, 5)!=null){
             txt_nip.setText(tb_ralan_ranap.getValueAt(srow, 5).toString());
        }
        
        if(tb_ralan_ranap.getValueAt(srow, 6)!=null){
         txt_nama_pegawai.setText(tb_ralan_ranap.getValueAt(srow, 6).toString());
        }
        
        if(tb_ralan_ranap.getValueAt(srow, 8)!=null){
          lbl_petugas_input.setText("Petugas Input: "+tb_ralan_ranap.getValueAt(srow, 8).toString());
        }
        
        if(tb_ralan_ranap.getValueAt(srow, 9)!=null){
         txt_catatan.setText(tb_ralan_ranap.getValueAt(srow, 9).toString());
        }
        
       if(tb_ralan_ranap.getValueAt(srow, 10)!=null){
         txt_nota.setText(tb_ralan_ranap.getValueAt(srow, 10).toString());
        }
        
        if(tb_ralan_ranap.getValueAt(srow, 11)!=null){
          this.txt_status_pasien.setText(this.tb_ralan_ranap.getModel().getValueAt(srow, 11).toString());
        }
        
        if(tb_ralan_ranap.getValueAt(srow, 12)!=null){
          this.lbl_nm_status.setText(this.tb_ralan_ranap.getModel().getValueAt(srow, 12).toString());
        }
        
        
       if(tb_ralan_ranap.getValueAt(srow, 13)!=null){
          txt_no_sep.setText(tb_ralan_ranap.getValueAt(srow, 13).toString());
        }
      
       if(tb_ralan_ranap.getValueAt(srow, 14)!=null){
          txt_tgl.setText(tb_ralan_ranap.getValueAt(srow, 14).toString());
        }
            
       
        set_Historydetail();
        ck_jual_karyawan_bebas.setSelected(false);
             ck_jual_bebas.setSelected(false);
             ck_jual_karyawan.setSelected(false);
           // jp_rm.setVisible(false);
            }
        
                                                  
    
    }
    
    
    private void set_HistoryKaryawanjualBebas(){
     
      
       int row = this.tb_karyawan_jual_bebas.getSelectedRow();

        if (row == -1) {
            // No row selected
        } else {
            
//{nmrm,nama_pasien,carabeli,noraw,jb,nik,nmp
//                                                   ,statcetak,petugas,catatan,nota,kdpj,nmpj,nosep,tgl});
        int srow =row; //tb_ralan_ranap.convertRowIndexToModel(row);
            
        
        if(tb_karyawan_jual_bebas.getValueAt(srow, 3)!=null){
         lbl_cara_beli.setText(tb_karyawan_jual_bebas.getValueAt(srow, 3).toString());
        } 
        
       
        if(tb_karyawan_jual_bebas.getValueAt(srow,1)!=null){
             txt_nip.setText(tb_karyawan_jual_bebas.getValueAt(srow, 1).toString());
        }
        
        if(tb_karyawan_jual_bebas.getValueAt(srow, 2)!=null){
         txt_nama_pegawai.setText(tb_karyawan_jual_bebas.getValueAt(srow, 2).toString());
        }
        
        if(tb_karyawan_jual_bebas.getValueAt(srow, 5)!=null){
          lbl_petugas_input.setText("Petugas Input: "+tb_karyawan_jual_bebas.getValueAt(srow, 5).toString());
        }
        
        if(tb_karyawan_jual_bebas.getValueAt(srow, 6)!=null){
         txt_catatan.setText(tb_karyawan_jual_bebas.getValueAt(srow, 6).toString());
        }
        
       if(tb_karyawan_jual_bebas.getValueAt(srow, 0)!=null){
         txt_nota.setText(tb_karyawan_jual_bebas.getValueAt(srow, 0).toString());
        }
        
      
       if(tb_karyawan_jual_bebas.getValueAt(srow, 7)!=null){
          txt_tgl.setText(tb_karyawan_jual_bebas.getValueAt(srow, 7).toString());
        }
          
        set_Historydetail();
        
        ck_jual_karyawan_bebas.setSelected(false);
             ck_jual_bebas.setSelected(false);
             ck_jual_karyawan.setSelected(false);
           // jp_rm.setVisible(false);
            }
        
                                                  
    
    }
    
    
    private void hapuskaryawanjualbebas(){
       DefaultTableModel dmi = (DefaultTableModel) tb_karyawan_jual_bebas.getModel();
        int rowCounti = dmi.getRowCount();
        if (rowCounti != 0) {
            for (int i = rowCounti- 1; i >= 0; i--) {
                
                
                
              if(!dmi.getValueAt(i, 3).toString().equals("Karyawan")) { 
               
                 dmi.removeRow(i);
                
              }
//              
//              if(dmi.getValueAt(i, 3).toString().equals("Ranap")) { 
//               
//                dmi.removeRow(i);
//              }
              
//              if(dmi.getValueAt(i, 2)==null) { 
//               
//                dmi.removeRow(i);
//              }
//              if(dmi.getValueAt(i, 2).toString().isEmpty()) { 
//               
//                dmi.removeRow(i);
//              }
              
//              if(dmi.getValueAt(i, 0).toString().contains("JB")) { 
//               
//                dmi.removeRow(i);
//              }
              
            }
        }
    }
    
    private void hapusjualbebas(){
     
        
        
        DefaultTableModel dm = (DefaultTableModel) tb_jual_bebas.getModel();
        int rowCount = dm.getRowCount();
        if (rowCount != 0) {
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }
        }
        
        
        
        
    }
    
    private void caridata(){
        
        try {
               this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
               
             datl = new Crud_local();
             datl.readRec_transfarmasi("");
             
             datl.CloseCon();
             
             tb_ralan_ranap.setModel(datl.modeltransfarmasi);
             setukurantbralanranap();
             
             
            hapusjualbebas();
            
            
//"No. Nota", "Nama Jual Bebas", "Tgl","Catatan","Petugas"};
    
    datl = new Crud_local();
    datl.readRec_transfarmasiKaryawanJualbebas("");
    datl.CloseCon();
    
 tb_karyawan_jual_bebas.setModel(datl.modeltransfarmasikaryawanjualbebas);
        
 
// nota,nik,nmp
//                                                   ,carabeli,statcetak,petugas,catatan,tgl,jb
//                                                           

//{"No. Nota", "Nama Jual Bebas", "Tgl","Catatan","Petugas"};

            if(datl.modeltransfarmasikaryawanjualbebas.getRowCount()!=0){
             
             for(int i=0;i<datl.modeltransfarmasikaryawanjualbebas.getRowCount();i++){
                 if(!datl.modeltransfarmasikaryawanjualbebas.getValueAt(i, 8).toString().isEmpty())
                 {    
                  modeljualbebashistory.addRow(new Object[]{datl.modeltransfarmasikaryawanjualbebas.getValueAt(i, 0),
                  datl.modeltransfarmasikaryawanjualbebas.getValueAt(i, 8),datl.modeltransfarmasikaryawanjualbebas.getValueAt(i, 7),datl.modeltransfarmasikaryawanjualbebas.getValueAt(i, 6),datl.modeltransfarmasikaryawanjualbebas.getValueAt(i, 5)});
                 }
             }
             
                 tb_jual_bebas.setModel(modeljualbebashistory);
                setukurantb_jual_bebas();
             }
             
             hapuskaryawanjualbebas();
             setukurantb_karyawan_jual_bebas();
             
        } catch (Exception ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        this.setCursor(Cursor.getDefaultCursor());
    
    }
    
    private void bt_proses_cari_registrasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_proses_cari_registrasiActionPerformed

        // TODO add your handling code here:

        if(txt_cari_rm.getText().isEmpty()){
        try {

                dat = new Crud_farmasi();

                String datePattern = "yyyy-MM-dd";
                SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
               
                dat.readRec_registrasiRalanFarmasi( dateFormatter.format(jtgl.getDate()),1);
              
                dat.CloseCon();
              
                this.jtb_registrasi.setModel(dat.modelregralanfarmasi);

                setukurantbReg();
               
//                lbl_stat_tgl.setText("Hasil Pencarian Pasien TGl: "+dateFormatter.format(dt_tgl_reg.getDate()));
    
            } catch (SQLException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        
      
        }
        else{
         try {
            String datePattern = "yyyy-MM-dd";
                SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
                //dateFormatter.format(dt_tgl_reg.getDate())

            dat = new Crud_farmasi();
            dat.readRec_registrasiRalanFisioFarmasi(dateFormatter.format(jtgl.getDate()),txt_cari_rm.getText(),2);
            
            dat.CloseCon();
            
            this.jtb_registrasi.setModel(dat.modelregralanfarmasi);

            setukurantbReg();
            
        } catch (Exception ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        }
       

    }//GEN-LAST:event_bt_proses_cari_registrasiActionPerformed

    private void txt_harga_satuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_harga_satuanKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.txt_jml.requestFocus();

        }
    }//GEN-LAST:event_txt_harga_satuanKeyPressed

    private void jtb_transaksiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtb_transaksiMousePressed
        // TODO add your handling code here:
        
   
    }//GEN-LAST:event_jtb_transaksiMousePressed

    private void jtb_transaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtb_transaksiMouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_jtb_transaksiMouseClicked

    private void jtb_transaksiMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtb_transaksiMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = jtb_transaksi.rowAtPoint(evt.getPoint());
            int col = jtb_transaksi.columnAtPoint(evt.getPoint());

            if (col == 5) {
                int viewIndex = jtb_transaksi.getSelectedRow();
                if (viewIndex != -1) {
                    int modelIndex = jtb_transaksi.convertRowIndexToModel(viewIndex); // converts the row index in the view to the appropriate index in the model
                    DefaultTableModel model = (DefaultTableModel) jtb_transaksi.getModel();
                    
                   //hapus hashmap temp brg
                  if(cbrg.size()!=0){  
                      
                    this.cbrg.remove(model.getValueAt(row, 2));
                    
                  }
                  
                    model.removeRow(modelIndex);
                    gr=0.0;
                    this.sumobat();
                    
                }
            }

        }

         
     
        
    }//GEN-LAST:event_jtb_transaksiMouseReleased

    private void jtb_barangMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtb_barangMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
             int row = this.jtb_barang.getSelectedRow();

        if (row != -1) {
          
          if(!cbrg.containsKey(jtb_barang.getModel().getValueAt(row, 1)))
              {      
                  
                  rows=row;
                  
//                  JOptionPane.showMessageDialog(null, row);
                  
                lbl_barang.setText(jtb_barang.getModel().getValueAt(row, 1).toString());
//                txt_hit_jml.setText(jtb_barang.getModel().getValueAt(row, 1).toString());
                     if(ck_jual_karyawan_bebas.isSelected()||ck_jual_karyawan.isSelected()){
                        txt_hit_harga.setText(jtb_barang.getModel().getValueAt(row, 3).toString());
                    }
                    else
                    {
                        txt_hit_harga.setText(jtb_barang.getModel().getValueAt(row, 2).toString());
                    }
                    
                    
//                    this.refreshdatatb();
                 

 
                jDlg_itung.setLocationRelativeTo(this);
                this.jDlg_itung.setVisible(true);
                
                //
                this.txt_hit_harga.setText("");
                this.txt_hit_jml.setText("");
                this.txt_hit_jml.requestFocus();
              }
              else{
                  JOptionPane.showMessageDialog(null, "Data Obat Ada Yang Sama!");
              }
            
           }
        }
    }//GEN-LAST:event_jtb_barangMouseReleased

    private void Hapussemua(){
     DefaultTableModel dm = (DefaultTableModel) jtb_transaksi.getModel();
        int rowCount = dm.getRowCount();
        if (rowCount != 0) {
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }
         
             lbl_grand_tot.setText("");
         cbrg.clear();
       
         lbl_grand_tot.setText("");
         this.txt_catatan.setText("");
         txt_nama_pegawai.setText("");
         
         JTextField temp=null;

        for(Component c:jPanel3.getComponents()){
           if(c.getClass().toString().contains("javax.swing.JTextField")){
              temp=(JTextField)c;
             
              temp.setText("");
        }
    
  }
        }
        
         DefaultTableModel dm1 = (DefaultTableModel) jtb_transaksi1.getModel();
        int rowCount1 = dm1.getRowCount();
        if (rowCount1!= 0) {
            for (int i = rowCount1 - 1; i >= 0; i--) {
                dm1.removeRow(i);
            }
            
        }   
        gr=0.0;
        lbl_grand_tot.setText("");
    }
    
    private void bt_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_hapusActionPerformed
        // TODO add your handling code here:
    int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah Akan di Hapus?","Warning ",
   JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
   
  if(dialogResult == JOptionPane.YES_OPTION){  
        Hapussemua();
  }
       
    }//GEN-LAST:event_bt_hapusActionPerformed

private void Cetak(){
    try {
        
         this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
    if(lbl_nm_status.getText().equals("BPJS")){
            
       
            datl=new Crud_local();
            datl.CetakNota(this.txt_nota.getText(),tampilrpt,2,lbl_nm_status.getText(),txt_no_sep.getText());
            datl.CloseCon();
       
            
       
            }
            else{
                 if(lbl_cara_beli.getText().equals("Jual Bebas")){
                   datl=new Crud_local();  
                   datl.CetakNota(this.txt_nota.getText(),tampilrpt,4,lbl_nm_status.getText(),txt_no_sep.getText());
                   datl.CloseCon();
                 }
                 
                else if(lbl_cara_beli.getText().equals("Karyawan")){
                 datl=new Crud_local();
                 datl.CetakNota(this.txt_nota.getText(),tampilrpt,3,lbl_nm_status.getText(),txt_no_sep.getText());
                 datl.CloseCon();
                 }
                else{
                  datl=new Crud_local();  
                  datl.CetakNota(this.txt_nota.getText(),tampilrpt,1,lbl_nm_status.getText(),txt_no_sep.getText());
                  datl.CloseCon();
                }       
                                
      }  
            } catch (JRException ex) {
                
            JOptionPane.showMessageDialog(null, "Gagal Print! ");
                
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    catch (Exception ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



private void savePrint(){
       
   int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah Data Sudah Benar?","Warning ",
   JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
   
  if(dialogResult == JOptionPane.YES_OPTION){
   
       jtb_transaksi.requestFocus();
       
      try {
           
       DefaultTableModel dm = (DefaultTableModel) jtb_transaksi.getModel();
        
        int rowCount = dm.getRowCount();
        
       if(rowCount != 0){
           
//           Save_trans(String no_nota, String no_rm, String nama_pasien, String catatan
//           , String petugas, String carabeli, String noraw, String nmjualbebas) {

           
               datl = new Crud_local();
               datl.Save_trans(this.txt_nota.getText(), this.txt_no_rm.getText(), this.txt_nama_pasien.getText()
                , this.txt_catatan.getText(), this.txt_petugas.getText(),lbl_cara_beli.getText()
                       ,txt_no_rawat.getText(),txt_nama_jual_bebas.getText(),txt_tgl.getText()
                       ,txt_nip.getText(),txt_nama_pegawai.getText());
               
                datl.CloseCon();    
      
            for (int i = rowCount - 1; i >= 0; i--) {
                datl = new Crud_local();
                datl.Save_detail_trans(this.txt_nota.getText(), Integer.valueOf(dm.getValueAt(i, 1).toString()), "-", 
                        Double.valueOf(dm.getValueAt(i, 3).toString()),dm.getValueAt(i, 2).toString(),Double.valueOf(dm.getValueAt(i, 4).toString()));
                datl.CloseCon();
            }
      
            Cetak();
            
             this.Hapussemua();
             
             caridata();
//             this.txt_no_rm.requestFocus();
             
             
             
         }
         else{
            JOptionPane.showMessageDialog(null, "Data  transaksi Obat Belum ada!");
         }
            
        } catch (Exception ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
       
       
       }    

    }
    
    private void bt_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_simpanActionPerformed
      
       
        
        if(lbl_cara_beli.getText().equals("Ralan")||lbl_cara_beli.getText().equals("Ranap")){
              if(!(txt_no_rawat.getText().isEmpty()||this.txt_nota.getText().isEmpty()||this.txt_no_rm.getText().isEmpty()||this.txt_nama_pasien.getText().isEmpty())){       
               
                  savePrint();
        
              }//end
    
             else{
                JOptionPane.showMessageDialog(null, "Data ada Yang Belum diisi");
//                this.txt_no_rm.requestFocus();
               } //end
       }
       else  if(lbl_cara_beli.getText().equals("Jual Bebas")){
           if(!txt_nama_jual_bebas.getText().isEmpty()){
              savePrint();
              }//end
    
             else{
                JOptionPane.showMessageDialog(null, "Data ada Yang Belum diisi");
                this.txt_nama_jual_bebas.requestFocus();
               } //end
       }
       else  if(lbl_cara_beli.getText().equals("Karyawan")){
           if(!txt_nama_pegawai.getText().isEmpty()){
              savePrint();
              }//end
    
             else{
                JOptionPane.showMessageDialog(null, "Data ada Yang Belum diisi");
               
               } //end
       } 
        
      this.setCursor(Cursor.getDefaultCursor());   
    }//GEN-LAST:event_bt_simpanActionPerformed

    private void jtb_transaksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtb_transaksiKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_F1) {
            savePrint();
        }
    }//GEN-LAST:event_jtb_transaksiKeyPressed

    private void jck_rptItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jck_rptItemStateChanged
        // TODO add your handling code here:
        if(evt.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
           tampilrpt="ok";
        } else {
            tampilrpt="no";
        }
    }//GEN-LAST:event_jck_rptItemStateChanged

    private void jtb_barangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtb_barangKeyPressed
        // TODO add your handling code here:
        
        
         if (evt.getKeyCode() == KeyEvent.VK_F1) {
            savePrint();
        }
         
         else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            this.formdetail.setVisible(true);
//            this.formdetail.pack();
//            //this.formdetail.setAlwaysOnTop(true);
//            this.formdetail.setLocationRelativeTo(null);
//             this.formdetail.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                
  int row = this.jtb_barang.getSelectedRow();

        if (row != -1) {
          
          if(!cbrg.containsKey(jtb_barang.getModel().getValueAt(row, 1)))
              {      
                  
                  rows=row;
                  
//                  JOptionPane.showMessageDialog(null, row);
                  
                lbl_barang.setText(jtb_barang.getModel().getValueAt(row, 1).toString());
//                txt_hit_jml.setText(jtb_barang.getModel().getValueAt(row, 1).toString());
                    if(ck_jual_karyawan_bebas.isSelected()||ck_jual_karyawan.isSelected()){
                        txt_hit_harga.setText(jtb_barang.getModel().getValueAt(row, 3).toString());
                    }
                    else
                    {
                        txt_hit_harga.setText(jtb_barang.getModel().getValueAt(row, 2).toString());
                    }
                    
                    
//                    if(ck_jual_karyawan.isSelected()){
//                        txt_hit_harga.setText(jtb_barang.getModel().getValueAt(row, 3).toString());
//                    }
//                    else
//                    {
//                        txt_hit_harga.setText(jtb_barang.getModel().getValueAt(row, 2).toString());
//                    }
                    
                    
//                    this.refreshdatatb();
                    
                jDlg_itung.setLocationRelativeTo(this);
                this.jDlg_itung.setVisible(true);
                
                //
                this.txt_hit_harga.setText("");
                this.txt_hit_jml.setText("");
                this.txt_hit_jml.requestFocus();
              }
              else{
                  JOptionPane.showMessageDialog(null, "Data Obat Ada Yang Sama!");
              }
            
          
        } 
          
        }
//       else if(evt.getKeyCode() == evt.getKeyChar())
       
//        {
//           
//             
//                  
//        this.txt_barang.requestFocus();
//        this.txt_barang.setText(String.valueOf(evt.getKeyChar()));
//      
//            jtb_barang.setRowSelectionInterval(0, 0);
//        
//        }   
    }//GEN-LAST:event_jtb_barangKeyPressed

    private void bt_cari_historyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_historyActionPerformed
        // TODO add your handling code here:
        
     if(this.txt_history.getText().isEmpty()){
     
                try {
                    
                    datl = new Crud_local();
                    
                    try {
                        datl.readRec_Allhistory();
                    } catch (SQLException ex) {
                        Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    jtb_history.setModel(datl.modelctrans);
                } catch (Exception ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
     }   
     else{   
        String datePattern = "yyyy-MM-dd";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
        
         try {
                datl = new Crud_local();

                try {
                    datl.readRec_cariTransRM(txt_history.getText(),dateFormatter.format(this.jtgl_history.getDate()));
                } catch (SQLException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

                jtb_history.setModel(datl.modelctrans);

            } catch (Exception ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
     }
    }//GEN-LAST:event_bt_cari_historyActionPerformed

    private void jcmb_catatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmb_catatanActionPerformed
        // TODO add your handling code here:
        this.txt_catatan.setText(jcmb_catatan.getSelectedItem().toString());
    }//GEN-LAST:event_jcmb_catatanActionPerformed

    private void jtb_registrasiMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtb_registrasiMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            set_pasien();
             
            
            txt_nama_pegawai.setText("");
            bt_simpan.setEnabled(true);
        }
    }//GEN-LAST:event_jtb_registrasiMouseReleased

    private void txt_hit_jmlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hit_jmlActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hit_jmlActionPerformed

    private void txt_hit_hargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hit_hargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hit_hargaActionPerformed

    private void txt_hit_jmlKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hit_jmlKeyPressed
        // TODO add your handling code here:
       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
           
           set_trans();
           
          gr=0.0;
        
          sumobat_detail();
           this.jDlg_itung.setVisible(false);
           bt_det_proses.requestFocus();
//           txt_hit_harga.requestFocus();
       }
       
       if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
           this.jDlg_itung.setVisible(false);
       }
       
    }//GEN-LAST:event_txt_hit_jmlKeyPressed

    private void txt_hit_hargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hit_hargaKeyPressed
        // TODO add your handling code here:
       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//           set_trans();
//           this.Set_Nonota();
//           this.jDlg_itung.setVisible(false);
//           this.txt_hit_harga.setText("0");
//           this.txt_hit_jml.setText("0");
//           this.lbl_hit_total.setText("0");
       }
       
       if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
           this.jDlg_itung.setVisible(false);
       }
       
        
    }//GEN-LAST:event_txt_hit_hargaKeyPressed

    private void sumobat_detail(){
     if(jtb_transaksi.getModel().getRowCount()>0){
         
         
          for(int i=0;i<jtb_transaksi.getModel().getRowCount();i++){
               gr=gr+ Double.valueOf(jtb_transaksi.getModel().getValueAt(i, 4).toString());
//               JOptionPane.showMessageDialog(null, gr);
          }
          
          this.lbl_grand_tot.setText(this.formatuang(gr));
      }
      else{
      Double nol=0.0;    
      this.lbl_grand_tot.setText(this.formatuang(nol));
      }
    
    }
    
    
    private void sumobat(){
      if(jtb_transaksi1.getModel().getRowCount()>0){
         
         
          for(int i=0;i<jtb_transaksi1.getModel().getRowCount();i++){
               gr=gr+ Double.valueOf(jtb_transaksi1.getModel().getValueAt(i, 4).toString());
//               JOptionPane.showMessageDialog(null, gr);
          }
          
          this.lbl_grand_tot.setText(this.formatuang(gr));
      }
      else{
      Double nol=0.0;    
      this.lbl_grand_tot.setText(this.formatuang(nol));
      }
    }
    private void bt_det_prosesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_det_prosesActionPerformed
        // TODO add your handling code here:
        set_trans();
           
          gr=0.0;
        
          sumobat_detail();
           this.jDlg_itung.setVisible(false);
//           this.txt_hit_harga.setText("0");
//           this.txt_hit_jml.setText("0");
//           this.lbl_hit_total.setText("0");
    }//GEN-LAST:event_bt_det_prosesActionPerformed

    private void jtb_barangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtb_barangKeyTyped
        // TODO add your handling code here
        char keyChar = evt.getKeyChar();
        
        this.txt_barang.requestFocus();
//        this.txt_barang.setText(String.valueOf(keyChar));
      
            jtb_barang.setRowSelectionInterval(0, 0);
    }//GEN-LAST:event_jtb_barangKeyTyped

    private void txt_no_sepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_no_sepKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_no_sepKeyPressed

    private void txt_catatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_catatanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_catatanKeyPressed

    private void lbl_poli3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_poli3MouseClicked
        // TODO add your handling code here:
        int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah Akan Keluar?", "Warning ",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (dialogResult == JOptionPane.YES_OPTION) {

          dispose();
          

            new frm_login_poli().setVisible(true);
            
        }
    }//GEN-LAST:event_lbl_poli3MouseClicked

    private void txt_status_pasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_status_pasienKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_status_pasienKeyPressed

    private void txt_no_rawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_no_rawatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_no_rawatKeyPressed

    private void txt_cari_reg_inapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cari_reg_inapKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.filtertxtInap();
            this.tb_reg_inap.requestFocus();
            //jtb_barang.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            tb_reg_inap.setRowSelectionInterval(0, 0);

        }
    }//GEN-LAST:event_txt_cari_reg_inapKeyPressed

    private void filtertxtInap(){
       Utilitas.filtertb(txt_cari_reg_inap.getText(), tb_reg_inap, 1, 1);
    }
    private void filterRegInap() {

        this.lbl_tgl_server.setText(Utilitas.getDateServer());

        if (!this.lbl_tgl_server.getText().isEmpty()) {
            try {

                dat = new Crud_farmasi();

                String[] b = {"BROMO", "CIREMAI", "KERINCI", "KRAKATAU", "MALABAR","PAPANDAYAN","RAKATA","RINJANI","SEMERU","CADANGAN","BROMO BPJS", "CIREMAI BPJS", "KERINCI BPJS", "KRAKATAU BPJS", "MALABAR BPJS","PAPANDAYAN BPJS","RAKATA BPJS","RINJANI BPJS","SEMERU BPJS","CADANGAN BPJS","KAMAR ODC IBU","RUANG BERSALIN","RUANG BERSALIN BPJS"};

                String t = lbl_tgl_server.getText().toString().substring(5, lbl_tgl_server.getText().length() - 3);

                dat.readRec_kamarinapFisioFarmasi(b, this.txt_cari_reg_inap.getText(), t, this.lbl_tgl_server.getText());

                dat.CloseCon();
                
                this.tb_reg_inap.setModel(dat.modelkamarinapfarmasi);

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

    
    private void setukurantb_ralan_ranap() {
      this.tb_ralan_ranap.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        tb_ralan_ranap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tri = this.tb_ralan_ranap.getColumnModel();

        tri.getColumn(0).setPreferredWidth(70);
        tri.getColumn(1).setPreferredWidth(285);
        tri.getColumn(2).setPreferredWidth(100);
        tri.getColumn(3).setPreferredWidth(220);
        tri.getColumn(4).setPreferredWidth(150);
    }
    
    
    
    private void setukurantb_jual_bebas() {
      this.tb_jual_bebas.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        tb_jual_bebas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tri = this.tb_jual_bebas.getColumnModel();

        tri.getColumn(0).setPreferredWidth(70);
        tri.getColumn(1).setPreferredWidth(270);
        tri.getColumn(2).setPreferredWidth(50);
        tri.getColumn(3).setPreferredWidth(150);
        tri.getColumn(4).setPreferredWidth(150);
    }        
            
    private void setukurantb_karyawan_jual_bebas() {
      this.tb_karyawan_jual_bebas.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        tb_karyawan_jual_bebas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tri = this.tb_karyawan_jual_bebas.getColumnModel();

        tri.getColumn(0).setPreferredWidth(70);
        tri.getColumn(1).setPreferredWidth(50);
        tri.getColumn(2).setPreferredWidth(220);
        tri.getColumn(3).setPreferredWidth(150);
        tri.getColumn(4).setPreferredWidth(150);
    }        
            
    private void setukurantbRegInap() {
      this.tb_reg_inap.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
        tb_reg_inap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tri = this.tb_reg_inap.getColumnModel();

        tri.getColumn(0).setPreferredWidth(70);
        tri.getColumn(1).setPreferredWidth(282);
        tri.getColumn(2).setPreferredWidth(100);
        tri.getColumn(3).setPreferredWidth(220);
        tri.getColumn(4).setPreferredWidth(150);
    }
    
    private void tb_reg_inapMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_reg_inapMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = this.tb_reg_inap.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                
                int srow = tb_reg_inap.convertRowIndexToModel(row);
               
               
                setMasukdatInputInap(srow);
               Set_Nonota();
               
                tab_trans.setSelectedIndex(0);
                //txt_tarif.requestFocus();
                txt_nama_pegawai.setText("");
          bt_simpan.setEnabled(true);
            }
        }
    }//GEN-LAST:event_tb_reg_inapMouseReleased

    private void tb_reg_inapKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_reg_inapKeyTyped
        // TODO add your handling code here:
        char keyChar = evt.getKeyChar();

        this.txt_cari_reg_inap.requestFocus();

        if (evt.getKeyCode() != KeyEvent.VK_ENTER) {

            this.txt_cari_reg_inap.setText(String.valueOf(keyChar).trim());
        }

        if (!txt_cari_reg_inap.getText().isEmpty()) {
            tb_reg_inap.setRowSelectionInterval(0, 0);
        }
    }//GEN-LAST:event_tb_reg_inapKeyTyped

    private void setMasukdatInputInap(int row) {
        try {
            
//            lbl_kode_poli.setText("");
//            lbl_nm_poli.setText("");
                txt_no_sep.setText("");
                txt_nota.setText("");            

            txt_no_sep.setText(this.tb_reg_inap.getModel().getValueAt(row, 8).toString());    
                
            txt_no_rm.setText(this.tb_reg_inap.getModel().getValueAt(row, 0).toString());
            this.txt_nama_pasien.setText(this.tb_reg_inap.getModel().getValueAt(row, 1).toString());
            txt_no_rawat.setText(this.tb_reg_inap.getModel().getValueAt(row, 2).toString());
            
            if(!this.txt_no_rawat.getText().isEmpty()){
               lbl_cara_beli.setText("Ranap");
            }
//            
//            dat = new Crud_farmasi();
//            
//            dat.readRec_registrasiFisio(txt_no_rawat.getText());
//            dat.CloseCon();
            
//            lbl_nip_petugas_pilih.setText(dat.kddokterfisio);
//            txt_petugas_pilih.setText(dat.nmdokterfisio);
            
            
//            this.lbl_tgl_masuk.setText(this.tb_reg_inap.getModel().getValueAt(row, 3).toString());
             lbl_kamar_inap.setText("Kamar Pasien: "+this.tb_reg_inap.getModel().getValueAt(row, 4).toString());
//            this.lbl_kelas.setText(this.tb_reg_inap.getModel().getValueAt(row, 5).toString());
            this.txt_status_pasien.setText(this.tb_reg_inap.getModel().getValueAt(row, 6).toString());
            this.lbl_nm_status.setText(this.tb_reg_inap.getModel().getValueAt(row, 7).toString());
            
             this.Hapussemua();
             
        } catch (Exception ex) {
            Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
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
                
                setMasukdatInputInap(srow);
                txt_nama_pegawai.setText("");
               Set_Nonota();
               bt_simpan.setEnabled(true);
            }

        }
    }//GEN-LAST:event_tb_reg_inapKeyPressed

    private void lbl_cari1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_cari1MouseClicked
        // TODO add your handling code here:
        filterReg();
        this.filtertxt();
    }//GEN-LAST:event_lbl_cari1MouseClicked

    private void lbl_nm_statusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lbl_nm_statusKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_nm_statusKeyPressed

    private void tb_cari_petugasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_cari_petugasMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = this.tb_cari_petugas.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                this.setPegawai(row);
            }

            this.dlg_dpjp.setVisible(false);
        }
    }//GEN-LAST:event_tb_cari_petugasMouseReleased

    private void setPegawai(int row){
       
       this.txt_nip.setText(this.tb_cari_petugas.getModel().getValueAt(row, 0).toString());
       this.txt_nama_pegawai.setText(this.tb_cari_petugas.getModel().getValueAt(row, 1).toString());
       
    }
    
    private void tb_cari_petugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_cari_petugasKeyPressed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int row = this.tb_cari_petugas.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {

                setPegawai(row);

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

    private void txt_nipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nipKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nipKeyPressed

    private void txt_nama_pegawaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nama_pegawaiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nama_pegawaiKeyPressed

    private void ck_jual_karyawan_bebasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ck_jual_karyawan_bebasActionPerformed
        // TODO add your handling code here:
         if(ck_jual_karyawan_bebas.isSelected()){
               this.Hapussemua();
            lbl_cara_beli.setText("Karyawan");
            Utilitas.HapusText(jPanel3);
            filterPegawai(); 
             
             Set_Nonota();
             bt_simpan.setEnabled(true);
            ck_jual_karyawan_bebas.setSelected(true);
            ck_jual_bebas.setSelected(false);
            ck_jual_karyawan.setSelected(false);
            this.dlg_dpjp.setLocationRelativeTo(this);
            this.dlg_dpjp.setVisible(true);
       }
        
    }//GEN-LAST:event_ck_jual_karyawan_bebasActionPerformed

    private void ck_jual_karyawan_bebasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ck_jual_karyawan_bebasStateChanged
        // TODO add your handling code here:
      
         
    }//GEN-LAST:event_ck_jual_karyawan_bebasStateChanged

    private void ck_jual_bebasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ck_jual_bebasActionPerformed
        // TODO add your handling code here:
         if(ck_jual_bebas.isSelected()){
              this.Hapussemua();
            Utilitas.HapusText(jPanel3);
            bt_simpan.setEnabled(true);
            
            lbl_cara_beli.setText("Jual Bebas");
            
            Set_Nonota();
            ck_jual_bebas.setSelected(true);
            ck_jual_karyawan_bebas.setSelected(false);
            
            txt_nama_jual_bebas.setEditable(true);
            txt_nama_jual_bebas.requestFocus();
           
       }
    }//GEN-LAST:event_ck_jual_bebasActionPerformed

    private void txt_nama_jual_bebasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nama_jual_bebasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nama_jual_bebasKeyPressed

    private void bt_print_ulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_print_ulangActionPerformed
        // TODO add your handling code here:
        jck_rpt.setSelected(true);
       Cetak();
        this.setCursor(Cursor.getDefaultCursor());   
    }//GEN-LAST:event_bt_print_ulangActionPerformed

    private void bt_det_prosesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bt_det_prosesKeyPressed
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//           set_trans();
//           
//          gr=0.0;
//        
//          sumobat_detail();
//           this.jDlg_itung.setVisible(false);
//       }
    }//GEN-LAST:event_bt_det_prosesKeyPressed

    private void txt_hit_jmlKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hit_jmlKeyTyped
        // TODO add your handling code here:
        char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txt_hit_jmlKeyTyped

    private void ck_jual_karyawanStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ck_jual_karyawanStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ck_jual_karyawanStateChanged

    private void ck_jual_karyawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ck_jual_karyawanActionPerformed
        // TODO add your handling code here:
        lbl_cara_beli.setText("Penjualan Karyawan");
        ck_jual_karyawan_bebas.setSelected(false);
        ck_jual_bebas.setSelected(false);
    }//GEN-LAST:event_ck_jual_karyawanActionPerformed

    private void txt_cari_ralan_ranapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cari_ralan_ranapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cari_ralan_ranapKeyPressed

    private void lbl_cari2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_cari2MouseClicked
        // TODO add your handling code here:
        caridata();
    }//GEN-LAST:event_lbl_cari2MouseClicked

    
    private void clstxt(){
     JTextField temp=null;

        for(Component c:jPanel3.getComponents()){
           if(c.getClass().toString().contains("javax.swing.JTextField")){
              temp=(JTextField)c;
             
              temp.setText("");
           }
        }
    }
    
    private void tb_ralan_ranapMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_ralan_ranapMouseReleased
       
        if (evt.getClickCount() == 1) {
            this.clstxt();
           set_Historyralanranap();
           lbl_grand_tot.setText("");
           gr=0.0;
          this.sumobat();
           bt_simpan.setEnabled(false);
        }        
        
        if (evt.isPopupTrigger()) {

            int row = tb_ralan_ranap.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                this.Popup_history.show(tb_ralan_ranap, evt.getX(), evt.getY());
                irowhistory = row;

            }

        }
        
        
        
    }//GEN-LAST:event_tb_ralan_ranapMouseReleased

    private void tb_ralan_ranapKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_ralan_ranapKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_tb_ralan_ranapKeyTyped

    private void tb_ralan_ranapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_ralan_ranapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb_ralan_ranapKeyPressed

    private void set_Historytb_karyawan_jual_bebas(){
     
      
       int row = this.tb_karyawan_jual_bebas.getSelectedRow();

        if (row == -1) {
            // No row selected
        } else {
            
//{nmrm,nama_pasien,carabeli,noraw,jb,nik,nmp
//                                                   ,statcetak,petugas,catatan,nota,kdpj,nmpj,nosep,tgl});
            
       
        
        if(tb_karyawan_jual_bebas.getValueAt(row, 2)!=null){
         lbl_cara_beli.setText(tb_karyawan_jual_bebas.getValueAt(row, 2).toString());
        } 
        
      
        
//        if(tb_ralan_ranap.getValueAt(row, 4)!=null){
//            txt_nama_jual_bebas.setText(tb_ralan_ranap.getValueAt(row, 4).toString());
//        }
       
        if(tb_karyawan_jual_bebas.getValueAt(row, 5)!=null){
             txt_nip.setText(tb_karyawan_jual_bebas.getValueAt(row, 5).toString());
        }
        
        if(tb_karyawan_jual_bebas.getValueAt(row, 6)!=null){
         txt_nama_pegawai.setText(tb_karyawan_jual_bebas.getValueAt(row, 6).toString());
        }
        
        if(tb_karyawan_jual_bebas.getValueAt(row, 8)!=null){
          lbl_petugas_input.setText("Petugas Input: "+tb_karyawan_jual_bebas.getValueAt(row, 8).toString());
        }
        
        if(tb_karyawan_jual_bebas.getValueAt(row, 9)!=null){
         txt_catatan.setText(tb_karyawan_jual_bebas.getValueAt(row, 9).toString());
        }
        
       if(tb_karyawan_jual_bebas.getValueAt(row, 10)!=null){
         txt_nota.setText(tb_karyawan_jual_bebas.getValueAt(row, 10).toString());
        }
       
       if(tb_karyawan_jual_bebas.getValueAt(row, 14)!=null){
          txt_tgl.setText(tb_karyawan_jual_bebas.getValueAt(row, 14).toString());
        }
            
            
            
           // jp_rm.setVisible(false);
            }
        
                                                  
    
    }
    
    private void tb_karyawan_jual_bebasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_karyawan_jual_bebasMouseReleased
        // TODO add your handling code here:
          if (evt.getClickCount() == 1) {
             this.clstxt();
           this.set_HistoryKaryawanjualBebas();
           lbl_grand_tot.setText("");
           gr=0.0;
          this.sumobat();
           bt_simpan.setEnabled(false);
        }        
          
        if (evt.isPopupTrigger()) {

            int row = tb_karyawan_jual_bebas.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                this.Popup_history.show(tb_karyawan_jual_bebas, evt.getX(), evt.getY());
                irowhistory = row;

            }

        }
    }//GEN-LAST:event_tb_karyawan_jual_bebasMouseReleased

    private void tb_karyawan_jual_bebasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_karyawan_jual_bebasKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_tb_karyawan_jual_bebasKeyTyped

    private void tb_karyawan_jual_bebasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_karyawan_jual_bebasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb_karyawan_jual_bebasKeyPressed

    private void txt_cari_karyawan_jual_bebasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cari_karyawan_jual_bebasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cari_karyawan_jual_bebasKeyPressed

    private void lbl_cari3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_cari3MouseClicked
        // TODO add your handling code here:
        caridata();
    }//GEN-LAST:event_lbl_cari3MouseClicked

    private void tb_jual_bebasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_jual_bebasMouseReleased
        // TODO add your handling code here:
         if (evt.getClickCount() == 1) {
            this.clstxt();
           set_Historyjualbebas();
           gr=0.0;
          this.sumobat();
           bt_simpan.setEnabled(false);
        }        
         
         if (evt.isPopupTrigger()) {

            int row = tb_jual_bebas.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                this.Popup_history.show(tb_jual_bebas, evt.getX(), evt.getY());
                irowhistory = row;

            }

        } 
         
    }//GEN-LAST:event_tb_jual_bebasMouseReleased

    private void tb_jual_bebasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_jual_bebasKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_tb_jual_bebasKeyTyped

    private void tb_jual_bebasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_jual_bebasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb_jual_bebasKeyPressed

    private void txt_cari_reg_inap3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cari_reg_inap3KeyPressed
        // TODO add your haulndling code here:
    }//GEN-LAST:event_txt_cari_reg_inap3KeyPressed

    private void lbl_cari4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_cari4MouseClicked
        // TODO add your handling code here:
        caridata();
    }//GEN-LAST:event_lbl_cari4MouseClicked

    private void item_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_hapusActionPerformed
        // TODO add your handling code here:
            
                this.HapusRowHistory(irowhistory);
 
           
        
        
    }//GEN-LAST:event_item_hapusActionPerformed

    private void tb_ralan_ranapMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_ralan_ranapMousePressed
        // TODO add your handling code here:
         if (evt.isPopupTrigger()) {

            int row = tb_ralan_ranap.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                this.Popup_history.show(tb_ralan_ranap, evt.getX(), evt.getY());
                irowhistory = row;

            }

        }
    }//GEN-LAST:event_tb_ralan_ranapMousePressed

    private void tb_karyawan_jual_bebasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_karyawan_jual_bebasMousePressed
        // TODO add your handling code here:
         if (evt.isPopupTrigger()) {

            int row = tb_karyawan_jual_bebas.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                this.Popup_history.show(tb_karyawan_jual_bebas, evt.getX(), evt.getY());
                irowhistory = row;

            }

        }
    }//GEN-LAST:event_tb_karyawan_jual_bebasMousePressed

    private void tb_jual_bebasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_jual_bebasMousePressed
        // TODO add your handling code here:
          if (evt.isPopupTrigger()) {

            int row = tb_jual_bebas.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                this.Popup_history.show(tb_jual_bebas, evt.getX(), evt.getY());
                irowhistory = row;

            }

        }
    }//GEN-LAST:event_tb_jual_bebasMousePressed

    private void item_hapus_detail_transActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_hapus_detail_transActionPerformed
        // TODO add your handling code here:
        this.HapusRowHistoryDetail(irowhistoryDetail);
    }//GEN-LAST:event_item_hapus_detail_transActionPerformed

    private void jtb_transaksi1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtb_transaksi1MousePressed
        // TODO add your handling code here:
         if (evt.isPopupTrigger()) {

            int row = jtb_transaksi1.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                this.Popup_detail_trans.show(jtb_transaksi1, evt.getX(), evt.getY());
                irowhistoryDetail = row;

            }

        }
    }//GEN-LAST:event_jtb_transaksi1MousePressed

    private void jtb_transaksi1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtb_transaksi1MouseReleased
        // TODO add your handling code here:
         if (evt.isPopupTrigger()) {

            int row = jtb_transaksi1.getSelectedRow();

            if (row == -1) {
                // No row selected
            } else {
                this.Popup_detail_trans.show(jtb_transaksi1, evt.getX(), evt.getY());
                irowhistoryDetail = row;

            }

        }
    }//GEN-LAST:event_jtb_transaksi1MouseReleased

    private void jtb_transaksi1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtb_transaksi1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jtb_transaksi1MouseClicked

    private void jtb_transaksi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtb_transaksi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtb_transaksi1KeyPressed

    private void item_edit_detail_transActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_edit_detail_transActionPerformed
        // TODO add your handling code here:
        dlg_edit_trans.setLocationRelativeTo(this);
                this.dlg_edit_trans.setVisible(true);
    }//GEN-LAST:event_item_edit_detail_transActionPerformed

    private void txt_hit_harga1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hit_harga1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hit_harga1ActionPerformed

    private void txt_hit_harga1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hit_harga1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hit_harga1KeyPressed

    private void bt_det_proses1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_det_proses1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_det_proses1ActionPerformed

    private void bt_det_proses1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bt_det_proses1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_det_proses1KeyPressed

    private void txt_hit_jml1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hit_jml1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hit_jml1ActionPerformed

    private void txt_hit_jml1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hit_jml1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hit_jml1KeyTyped

    private void txt_hit_jml1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hit_jml1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hit_jml1KeyPressed

    private void HapusRowHistoryDetail(int i){
      int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah Akan di Hapus No. Nota :  "+ jtb_transaksi1.getModel().getValueAt(irowhistoryDetail, 2),"Warning ",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if(dialogResult == JOptionPane.YES_OPTION){
                  DefaultTableModel dm = (DefaultTableModel) jtb_transaksi1.getModel();
        int rowCount = dm.getRowCount();
        if (rowCount != 0) {
            try {
                
                datl=new Crud_local();
                datl.DelRecDetailTransFarmasi(dm.getValueAt(i, 2).toString());
                datl.CloseCon();
                dm.removeRow(i);
                
                gr=0.0;
                this.sumobat();
                
//                caridata();
//                this.Hapussemua();
               
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Gagal Hapus!");
                Logger.getLogger(frm_poli.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
      }
    }
    private void HapusRowHistory(int i){
    
      if(lbl_cara_beli.getText().equals("Ralan")||lbl_cara_beli.getText().equals("Ranap")){  

       int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah Akan di Hapus Ranap/Ralan No. Nota :  "+ tb_ralan_ranap.getModel().getValueAt(irowhistory, 10),"Warning ",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if(dialogResult == JOptionPane.YES_OPTION){
                  DefaultTableModel dm = (DefaultTableModel) tb_ralan_ranap.getModel();
        int rowCount = dm.getRowCount();
        if (rowCount != 0) {
            try {
                
                datl=new Crud_local();
                datl.DelRecHistoryFarmasi(dm.getValueAt(i, 10).toString());
                datl.CloseCon();
                dm.removeRow(i);
                
                caridata();
                this.Hapussemua();
               
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Gagal Hapus!");
                Logger.getLogger(frm_poli.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
            }   
      
      
      }
      else if(lbl_cara_beli.getText().equals("Karyawan")){
          
        int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah Akan di Hapus Karyawan Jual Bebas No. Nota :  "+ tb_karyawan_jual_bebas.getModel().getValueAt(irowhistory, 0),"Warning ",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if(dialogResult == JOptionPane.YES_OPTION){   
          
        DefaultTableModel dm = (DefaultTableModel) tb_karyawan_jual_bebas.getModel();
        int rowCount = dm.getRowCount();
        if (rowCount != 0) {
            try {
                
                datl=new Crud_local();
                datl.DelRecHistoryFarmasi(dm.getValueAt(i, 0).toString());
                datl.CloseCon();
                dm.removeRow(i);
                
                caridata();
                this.Hapussemua();
               
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Gagal Hapus!");
                Logger.getLogger(frm_poli.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
            }
      }
      else if(lbl_cara_beli.getText().equals("Jual Bebas")){
          
          int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah Akan di Hapus Jual Bebas No. Nota :  "+ tb_jual_bebas.getModel().getValueAt(irowhistory, 0),"Warning ",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if(dialogResult == JOptionPane.YES_OPTION){    
          
          DefaultTableModel dm = (DefaultTableModel) tb_jual_bebas.getModel();
        int rowCount = dm.getRowCount();
        if (rowCount != 0) {
            try {
                
                datl=new Crud_local();
                datl.DelRecHistoryFarmasi(dm.getValueAt(i, 0).toString());
                datl.CloseCon();
                dm.removeRow(i);
                
                caridata();
                this.Hapussemua();
               
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Gagal Hapus!");
                Logger.getLogger(frm_poli.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
          
      }
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
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    NewJFrame frm = new NewJFrame();
                    frm.setLocationRelativeTo(null);
                    frm.setVisible(true);

                    //new NewJFrame().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    
    private boolean caribrgtb(String nmbrg){
    
        
        DefaultTableModel dm = (DefaultTableModel) jtb_transaksi.getModel();
        int rowCount = dm.getRowCount();
        if (rowCount != 0) {
            for (int i = rowCount - 1; i >= 0; i--) {
                if (dm.getValueAt(i, 2).toString().equals(nmbrg)){
                    cek=false;
                        JOptionPane.showMessageDialog(null, "Barang Sama "+nmbrg+" "+cek);
                }
                else
                {
                  // cek=true;
                   JOptionPane.showMessageDialog(null, "kampret");
                }
            }
        }
        
       
        
     
       return cek;
    }
    
    private void filtertxt() {

        try {
            //                   Utilitas.filtertb(txt_cari_reg.getText(), tb_reg, 2,2);

            dat = new Crud_farmasi();
            dat.readRec_registrasiRalanFisioFarmasi(this.lbl_tgl_server.getText(),txt_cari_rm.getText(),2);
            
            dat.CloseCon();
            
            this.jtb_registrasi.setModel(dat.modelregralanfarmasi);

            setukurantbReg();
            
        } catch (Exception ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
                    
    }

    private void filterreg(){
         this.lbl_tgl_server.setText(Utilitas.getDateServer());

        if (!this.lbl_tgl_server.getText().isEmpty()) {
            try {

                dat = new Crud_farmasi();

                String datePattern = "yyyy-MM-dd";
                SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
                //dateFormatter.format(dt_tgl_reg.getDate())
                dat.readRec_registrasiRalanFarmasi(this.lbl_tgl_server.getText(),1);
                
                dat.CloseCon();
                
                this.jtb_registrasi.setModel(dat.modelregralanfarmasi);

                setukurantbReg();
//                lbl_stat_tgl.setText("Hasil Pencarian Pasien TGl: "+this.lbl_tgl_server.getText());
    
            } catch (SQLException ex) {
                Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(frm_poli_ralan.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "SerVer tidak Terkoneksi");

        }
//         try {
//                dat = new Crud_farmasi();
//
//                try {
//                    dat.readRec_registrasiRMNama(txt_cari_rm.getText(),tglsekarang());
//                    dat.CloseCon();
//                } catch (SQLException ex) {
//                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//                jtb_registrasi.setModel(dat.modelreg);
//
//            } catch (Exception ex) {
//                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
//            }
    }
    
    
    private void filterhistory(){
        String datePattern = "yyyy-MM-dd";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
        
         try {
                datl = new Crud_local();

                try {
                    datl.readRec_cariTrans(txt_history.getText(),dateFormatter.format(this.jtgl_history.getDate()));
                } catch (SQLException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

                jtb_history.setModel(datl.modelctrans);

            } catch (Exception ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    class ImageRenderer extends DefaultTableCellRenderer {

        JLabel lbl = new JLabel();

        ImageIcon icon = new ImageIcon(getClass().getResource("hps_ico.png"));

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            lbl.setText((String) value);
            lbl.setIcon(icon);
            return lbl;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu Popup_detail_trans;
    private javax.swing.JPopupMenu Popup_history;
    private javax.swing.JToolBar ToolBar;
    private javax.swing.JButton bt_cari_history;
    private javax.swing.JButton bt_cari_rm;
    private javax.swing.JButton bt_det_proses;
    private javax.swing.JButton bt_det_proses1;
    private javax.swing.JButton bt_hapus;
    private javax.swing.JButton bt_print_ulang;
    private javax.swing.JButton bt_proses_cari_registrasi;
    private javax.swing.JButton bt_simpan;
    private javax.swing.JCheckBox ck_jual_bebas;
    private javax.swing.JCheckBox ck_jual_karyawan;
    private javax.swing.JCheckBox ck_jual_karyawan_bebas;
    private javax.swing.JCheckBox ck_kasbon;
    private javax.swing.JDialog dlg_dpjp;
    private javax.swing.JDialog dlg_edit_trans;
    private javax.swing.JMenuItem item_edit_detail_trans;
    private javax.swing.JMenuItem item_hapus;
    private javax.swing.JMenuItem item_hapus_detail_trans;
    private javax.swing.JDialog jDlg_itung;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
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
    private javax.swing.JCheckBox jck_rpt;
    private javax.swing.JComboBox<String> jcmb_catatan;
    private javax.swing.JPanel jp_barang;
    private javax.swing.JTable jtb_barang;
    private javax.swing.JTable jtb_history;
    private javax.swing.JTable jtb_registrasi;
    private javax.swing.JTable jtb_transaksi;
    private javax.swing.JTable jtb_transaksi1;
    private uz.ncipro.calendar.JDateTimePicker jtgl;
    private uz.ncipro.calendar.JDateTimePicker jtgl_history;
    private javax.swing.JLabel lbl_barang;
    private javax.swing.JLabel lbl_barang1;
    private javax.swing.JLabel lbl_cara_beli;
    private javax.swing.JLabel lbl_cari1;
    private javax.swing.JLabel lbl_cari2;
    private javax.swing.JLabel lbl_cari3;
    private javax.swing.JLabel lbl_cari4;
    private javax.swing.JLabel lbl_grand_tot;
    private javax.swing.JLabel lbl_hit_total;
    private javax.swing.JLabel lbl_hit_total1;
    private javax.swing.JLabel lbl_jam;
    private javax.swing.JLabel lbl_jamnow;
    private javax.swing.JLabel lbl_kamar_inap;
    private javax.swing.JLabel lbl_kode;
    private javax.swing.JLabel lbl_news;
    private javax.swing.JTextField lbl_nm_status;
    private javax.swing.JLabel lbl_petugas_input;
    private javax.swing.JLabel lbl_poli;
    private javax.swing.JLabel lbl_poli3;
    private javax.swing.JLabel lbl_tgl_server;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JTabbedPane tab_trans;
    private javax.swing.JTable tb_cari_petugas;
    private javax.swing.JTable tb_jual_bebas;
    private javax.swing.JTable tb_karyawan_jual_bebas;
    private javax.swing.JTable tb_ralan_ranap;
    private javax.swing.JTable tb_reg_inap;
    private javax.swing.JTextField txt_barang;
    private javax.swing.JTextField txt_cari_karyawan_jual_bebas;
    private javax.swing.JTextField txt_cari_petugas;
    private javax.swing.JTextField txt_cari_ralan_ranap;
    private javax.swing.JTextField txt_cari_reg_inap;
    private javax.swing.JTextField txt_cari_reg_inap3;
    private javax.swing.JTextField txt_cari_rm;
    private javax.swing.JTextField txt_catatan;
    private javax.swing.JTextField txt_harga_satuan;
    private javax.swing.JTextField txt_history;
    private javax.swing.JTextField txt_hit_harga;
    private javax.swing.JTextField txt_hit_harga1;
    private javax.swing.JTextField txt_hit_jml;
    private javax.swing.JTextField txt_hit_jml1;
    private javax.swing.JTextField txt_jml;
    private javax.swing.JTextField txt_nama_jual_bebas;
    private javax.swing.JTextField txt_nama_pasien;
    private javax.swing.JTextField txt_nama_pegawai;
    private javax.swing.JTextField txt_nip;
    private javax.swing.JTextField txt_no_rawat;
    private javax.swing.JTextField txt_no_rm;
    private javax.swing.JTextField txt_no_sep;
    private javax.swing.JTextField txt_nota;
    private javax.swing.JLabel txt_petugas;
    private javax.swing.JTextField txt_status_pasien;
    private javax.swing.JLabel txt_tgl;
    // End of variables declaration//GEN-END:variables
}
