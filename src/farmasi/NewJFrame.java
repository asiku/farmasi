/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farmasi;

import java.awt.Color;
import java.awt.Component;
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

/**
 *
 * @author jengcool
 */
public class NewJFrame extends javax.swing.JFrame {

    //frm_detail formdetail=new frm_detail();
    String aX="";
    
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

    public DefaultTableModel modeltrans = new DefaultTableModel(trans_title, 0) {

        public boolean isCellEditable(int row, int column) {
            if (column == 1 || column == 3) {
                return true;
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

    private void set_pasien(){
    
        int row = this.jtb_registrasi.getSelectedRow();

        if (row == -1) {
            // No row selected
        } else {
            this.txt_no_rm.setText(jtb_registrasi.getValueAt(row, 1).toString());
            this.txt_nama_pasien.setText(jtb_registrasi.getValueAt(row, 2).toString());
           // jp_rm.setVisible(false);
        }
    
    }
    
    
    private void set_trans() {

        int row = this.jtb_barang.getSelectedRow();

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
          
        this.txt_nota.setText("PJ-"+LocalDateTime.now().getSecond()+"-"+String.valueOf(LocalDateTime.now().getSecond()+1));
    }
    
    public NewJFrame() throws Exception {
        initComponents();

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
      //  this.setUndecorated(true);
        

        bt_cari_rm.setVisible(false);

        this.txt_no_rm.requestFocus();

        txt_tgl.setText(tglsekarang());

        //this.jp_barang.setVisible(false);
//        this.jp_rm.setVisible(false); 

        //set tampil report
        this.jck_rpt.setSelected(true);
        
       //set No nota
       Set_Nonota();
       
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
                filterreg();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterreg();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterreg();
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

        dat = new Crud_farmasi();

        try {
            dat.readRec_registrasi(tglsekarang());
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        jtb_registrasi.setModel(dat.modelreg);

        dat = new Crud_farmasi();

        try {
            dat.readRec_brg("");
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.jtb_barang.setModel(dat.modelbrg);

        this.jtb_transaksi.setModel(modeltrans);

        setlebartbl();

        modeltrans.addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {

                Double tot=0.0;
                
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

        //set timer
        //setTimernota();
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
            } catch (SQLException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            this.jtb_barang.setModel(dat.modelbrg);
        } catch (Exception ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void filterbrg() {
        try {
            dat = new Crud_farmasi();
            try {
                dat.readRec_brgF(this.txt_barang.getText().toString());
            } catch (SQLException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            this.jtb_barang.setModel(dat.modelbrg);
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        bt_cari_rm = new javax.swing.JButton();
        txt_no_rm = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txt_nama_pasien = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_nota = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_catatan = new javax.swing.JTextArea();
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
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtb_transaksi = new javax.swing.JTable();
        bt_hapus = new javax.swing.JButton();
        bt_simpan = new javax.swing.JButton();
        jck_rpt = new javax.swing.JCheckBox();
        lbl_grand_tot = new javax.swing.JLabel();
        txt_tgl = new javax.swing.JLabel();
        jcmb_catatan = new javax.swing.JComboBox<>();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        txt_cari_rm = new javax.swing.JTextField();
        bt_proses_cari_registrasi = new javax.swing.JButton();
        jtgl = new uz.ncipro.calendar.JDateTimePicker();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtb_registrasi = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_petugas = new javax.swing.JTextField();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtb_history = new javax.swing.JTable();
        jtgl_history = new uz.ncipro.calendar.JDateTimePicker();
        txt_history = new javax.swing.JTextField();
        bt_cari_history = new javax.swing.JButton();

        jDlg_itung.setModal(true);
        jDlg_itung.setSize(new java.awt.Dimension(400, 250));

        txt_hit_jml.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_hit_jml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hit_jmlActionPerformed(evt);
            }
        });
        txt_hit_jml.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_hit_jmlKeyPressed(evt);
            }
        });

        jLabel12.setText("Jml");

        jLabel13.setText("Harga");

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
        jPanel3.add(bt_cari_rm, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, 31, -1));

        txt_no_rm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_no_rmKeyPressed(evt);
            }
        });
        jPanel3.add(txt_no_rm, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 239, -1));

        jLabel1.setText("No. RM");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        txt_nama_pasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nama_pasienKeyPressed(evt);
            }
        });
        jPanel3.add(txt_nama_pasien, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 239, -1));

        jLabel2.setText("Nama Pasien");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 117, 19));

        jLabel3.setText("tgl");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 117, 19));

        txt_nota.setEditable(false);
        txt_nota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_notaKeyPressed(evt);
            }
        });
        jPanel3.add(txt_nota, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 239, -1));

        jLabel4.setText("No. Nota");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 117, 19));

        txt_catatan.setColumns(5);
        txt_catatan.setLineWrap(true);
        txt_catatan.setRows(5);
        txt_catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_catatanKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txt_catatan);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 50, 386, 60));

        jLabel5.setText("Catatan");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, -1, -1));

        jp_barang.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jp_barang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jp_barang.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 68, 840, 128));

        txt_barang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_barang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_barangKeyPressed(evt);
            }
        });
        jp_barang.add(txt_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 27, 839, 35));

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
        jp_barang.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 2, 117, 19));

        jPanel3.add(jp_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 860, 210));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtb_transaksi.setModel(new javax.swing.table.DefaultTableModel(
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

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 50, 832, 201));

        bt_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/farmasi/hps_ico.png"))); // NOI18N
        bt_hapus.setText("Hapus Semua");
        bt_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_hapusActionPerformed(evt);
            }
        });
        jPanel1.add(bt_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(693, 15, -1, -1));

        bt_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/farmasi/save_ico.png"))); // NOI18N
        bt_simpan.setText("Save & Print");
        bt_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_simpanActionPerformed(evt);
            }
        });
        jPanel1.add(bt_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(537, 14, -1, -1));

        jck_rpt.setText(" Tampilkan Report");
        jck_rpt.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jck_rptItemStateChanged(evt);
            }
        });
        jPanel1.add(jck_rpt, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 170, -1));

        lbl_grand_tot.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_grand_tot.setText("Rp.");
        jPanel1.add(lbl_grand_tot, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 300, 20));

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 860, 260));
        jPanel3.add(txt_tgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 220, 20));

        jcmb_catatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rawat Jalan", "Rawat Inap", "Penjualan Bebas", "BPJS" }));
        jcmb_catatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmb_catatanActionPerformed(evt);
            }
        });
        jPanel3.add(jcmb_catatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 240, -1));

        jLayeredPane3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLayeredPane4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_cari_rm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cari_rmKeyPressed(evt);
            }
        });
        jLayeredPane4.add(txt_cari_rm, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 18, 220, -1));

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

        jLayeredPane4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 360, 510));

        jTabbedPane2.addTab("Data Registrasi Pasien", jLayeredPane4);

        jLayeredPane3.setLayer(jTabbedPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane3Layout = new javax.swing.GroupLayout(jLayeredPane3);
        jLayeredPane3.setLayout(jLayeredPane3Layout);
        jLayeredPane3Layout.setHorizontalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane3Layout.setVerticalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane3Layout.createSequentialGroup()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        jPanel3.add(jLayeredPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 10, 410, 650));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel10.setText("F1 : Print   [ untuk mencari No. RM ketik di inputan No. RM Enter ]");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 120, 390, 40));

        jLabel11.setText("Petugas");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        txt_petugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_petugasKeyPressed(evt);
            }
        });
        jPanel3.add(txt_petugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 239, -1));

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
                        .addGap(0, 1185, Short.MAX_VALUE)))
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
                .addContainerGap(356, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("History Penjualan", jLayeredPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_catatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_catatanKeyPressed
        // TODO add your handling code here:
         if (evt.getKeyCode() == KeyEvent.VK_F1) {
            savePrint();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.txt_barang.requestFocus();

        }
    }//GEN-LAST:event_txt_catatanKeyPressed

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

    private void bt_proses_cari_registrasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_proses_cari_registrasiActionPerformed
        // TODO add your handling code here:

        String datePattern = "yyyy-MM-dd";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        if (this.txt_cari_rm.getText().isEmpty()) {

            // System.out.print("No "+ dateFormatter.format(this.jtgl.getDate()));
            try {
                dat = new Crud_farmasi();

                try {
                    dat.readRec_registrasi(dateFormatter.format(this.jtgl.getDate()));
                } catch (SQLException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

                jtb_registrasi.setModel(dat.modelreg);

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
                    
                    
                }
            }

        }

    }//GEN-LAST:event_jtb_transaksiMouseReleased

    private void jtb_barangMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtb_barangMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            set_trans();
        }
    }//GEN-LAST:event_jtb_barangMouseReleased

    private void Hapussemua(){
     DefaultTableModel dm = (DefaultTableModel) jtb_transaksi.getModel();
        int rowCount = dm.getRowCount();
        if (rowCount != 0) {
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }
            
         cbrg.clear();
       
         lbl_grand_tot.setText("");
         this.txt_catatan.setText("");
         
         JTextField temp=null;

        for(Component c:jPanel3.getComponents()){
           if(c.getClass().toString().contains("javax.swing.JTextField")){
              temp=(JTextField)c;
             
              temp.setText("");
        }
    
  }
        }
    }
    
    private void bt_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_hapusActionPerformed
        // TODO add your handling code here:
    int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah Akan di Hapus?","Warning ",
   JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
   
  if(dialogResult == JOptionPane.YES_OPTION){  
        Hapussemua();
  }
       
    }//GEN-LAST:event_bt_hapusActionPerformed

    
    private void savePrint(){
       
   int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah Data Sudah Benar?","Warning ",
   JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
   
  if(dialogResult == JOptionPane.YES_OPTION){
   
        
   if(!(this.txt_nota.getText().isEmpty()||this.txt_no_rm.getText().isEmpty()||this.txt_nama_pasien.getText().isEmpty()||this.txt_catatan.getText().isEmpty())){       
  
       jtb_transaksi.requestFocus();
       
      try {
  
        datl = new Crud_local();
            
       DefaultTableModel dm = (DefaultTableModel) jtb_transaksi.getModel();
        
        int rowCount = dm.getRowCount();
        
       if(rowCount != 0){
               datl.Save_trans(this.txt_nota.getText(), this.txt_no_rm.getText(), this.txt_nama_pasien.getText()
                , this.txt_catatan.getText(), this.txt_petugas.getText());
                   
      
            for (int i = rowCount - 1; i >= 0; i--) {
                datl.Save_detail_trans(this.txt_nota.getText(), Integer.valueOf(dm.getValueAt(i, 1).toString()), "-", 
                        Double.valueOf(dm.getValueAt(i, 3).toString()),dm.getValueAt(i, 2).toString(),Double.valueOf(dm.getValueAt(i, 4).toString()));
            }
      
             datl.CetakNota(this.txt_nota.getText(),tampilrpt);
            
             this.Hapussemua();
             
             this.txt_no_rm.requestFocus();
             Set_Nonota();
         }
         else{
            JOptionPane.showMessageDialog(null, "Data  transaksi Obat Belum ada!");
         }
            
        } catch (Exception ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
        }
    
        else{
                JOptionPane.showMessageDialog(null, "Data ada Yang Belum diisi");
                this.txt_no_rm.requestFocus();
         }
       
       }    

    }
    
    private void bt_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_simpanActionPerformed
        savePrint();
    }//GEN-LAST:event_bt_simpanActionPerformed

    private void txt_petugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_petugasKeyPressed
        // TODO add your handling code here:
         if (evt.getKeyCode() == KeyEvent.VK_F1) {
            savePrint();
        }
    }//GEN-LAST:event_txt_petugasKeyPressed

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
                lbl_barang.setText(jtb_barang.getModel().getValueAt(row, 1).toString());
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
           txt_hit_harga.requestFocus();
       }
       
       if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
           this.jDlg_itung.setVisible(false);
       }
       
    }//GEN-LAST:event_txt_hit_jmlKeyPressed

    private void txt_hit_hargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hit_hargaKeyPressed
        // TODO add your handling code here:
       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
           set_trans();
           this.Set_Nonota();
           this.jDlg_itung.setVisible(false);
           this.txt_hit_harga.setText("0");
           this.txt_hit_jml.setText("0");
           this.lbl_hit_total.setText("0");
       }
       
       if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
           this.jDlg_itung.setVisible(false);
       }
       
        
    }//GEN-LAST:event_txt_hit_hargaKeyPressed

    private void bt_det_prosesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_det_prosesActionPerformed
        // TODO add your handling code here:
        set_trans();
           
           this.jDlg_itung.setVisible(false);
           this.txt_hit_harga.setText("0");
           this.txt_hit_jml.setText("0");
           this.lbl_hit_total.setText("0");
    }//GEN-LAST:event_bt_det_prosesActionPerformed

    private void jtb_barangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtb_barangKeyTyped
        // TODO add your handling code here
        char keyChar = evt.getKeyChar();
        
        this.txt_barang.requestFocus();
        this.txt_barang.setText(String.valueOf(keyChar));
      
            jtb_barang.setRowSelectionInterval(0, 0);
    }//GEN-LAST:event_jtb_barangKeyTyped

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
    
    private void filterreg(){
         try {
                dat = new Crud_farmasi();

                try {
                    dat.readRec_registrasiRMNama(txt_cari_rm.getText(),tglsekarang());
                } catch (SQLException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

                jtb_registrasi.setModel(dat.modelreg);

            } catch (Exception ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    private javax.swing.JButton bt_cari_history;
    private javax.swing.JButton bt_cari_rm;
    private javax.swing.JButton bt_det_proses;
    private javax.swing.JButton bt_hapus;
    private javax.swing.JButton bt_proses_cari_registrasi;
    private javax.swing.JButton bt_simpan;
    private javax.swing.JDialog jDlg_itung;
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JCheckBox jck_rpt;
    private javax.swing.JComboBox<String> jcmb_catatan;
    private javax.swing.JPanel jp_barang;
    private javax.swing.JTable jtb_barang;
    private javax.swing.JTable jtb_history;
    private javax.swing.JTable jtb_registrasi;
    private javax.swing.JTable jtb_transaksi;
    private uz.ncipro.calendar.JDateTimePicker jtgl;
    private uz.ncipro.calendar.JDateTimePicker jtgl_history;
    private javax.swing.JLabel lbl_barang;
    private javax.swing.JLabel lbl_grand_tot;
    private javax.swing.JLabel lbl_hit_total;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JTextField txt_barang;
    private javax.swing.JTextField txt_cari_rm;
    private javax.swing.JTextArea txt_catatan;
    private javax.swing.JTextField txt_harga_satuan;
    private javax.swing.JTextField txt_history;
    private javax.swing.JTextField txt_hit_harga;
    private javax.swing.JTextField txt_hit_jml;
    private javax.swing.JTextField txt_jml;
    private javax.swing.JTextField txt_nama_pasien;
    private javax.swing.JTextField txt_no_rm;
    private javax.swing.JTextField txt_nota;
    private javax.swing.JTextField txt_petugas;
    private javax.swing.JLabel txt_tgl;
    // End of variables declaration//GEN-END:variables
}
