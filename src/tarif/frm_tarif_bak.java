/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarif;

import farmasi.Crud_local;
import farmasi.NewJFrame;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.SwingUtilities.paintComponent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import tools.ScreenImage;
import tools.Utilitas;
import org.apache.commons.lang3.StringUtils;
/**
 *
 * @author jengcool
 */
public class frm_tarif_bak extends javax.swing.JFrame {

    /**
     * Creates new form frm_tarif
     */
  
  private String pathFile="";
  
  private int pos=0;
  
  private int posv=0;
  
  private Graphics2D ig2;
  private BufferedImage bi;
  
  
  private Graphics2D ig2v;
  private BufferedImage biv;
  
  // Mouse koordinatnya
  private int currentX, currentY, oldX, oldY;
  
  private int currentXv, currentYv, oldXv, oldYv;
  
  private   HashMap<String,Integer > points = new HashMap<String,Integer>();
  
  private   HashMap<String,Integer > pointsv = new HashMap<String,Integer>();
    
  private Crud_local datl;
     
    
   private void setukurantbtarif(){
    tb_tarif.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
    TableColumnModel tr = tb_tarif.getColumnModel(); 
    
    tr.getColumn(0).setPreferredWidth(100);
    tr.getColumn(1).setPreferredWidth(300);
    tr.getColumn(2).setPreferredWidth(100);
    tr.getColumn(3).setPreferredWidth(50);
    tr.getColumn(4).setPreferredWidth(50);
    tr.getColumn(5).setPreferredWidth(80);
    tr.getColumn(6).setPreferredWidth(80);
    tr.getColumn(7).setPreferredWidth(100);
    tr.getColumn(8).setPreferredWidth(100);
    tr.getColumn(9).setPreferredWidth(50);
    tr.getColumn(10).setPreferredWidth(50);
    tr.getColumn(11).setPreferredWidth(120);
    tr.getColumn(12).setPreferredWidth(100);
    tr.getColumn(13).setPreferredWidth(50); 
   }
  
  
     
    public frm_tarif_bak() {
        initComponents();
        
       txt_stat.setVisible(false);
        
       this.setExtendedState(JFrame.MAXIMIZED_BOTH);
      
    
//      try {
//          datl=new Crud_local();
//          datl.readRec_cariTarif();
//          tb_tarif.setModel(datl.modeltarif);
//          
//      } catch (Exception ex) {
//          Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
//      }
       
      
       this.jPanel2.addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
 
       
        currentXv = e.getX();
        currentYv = e.getY();
          
            ig2v=(Graphics2D) jPanel2.getGraphics();
    
            posv++;
            
            pointsv.put("oldX"+posv, oldXv);
            pointsv.put("oldY"+posv, oldYv);
            pointsv.put("currentX"+posv, currentXv);
            pointsv.put("currentY"+posv, currentYv);
            
            lbl_ttd_cek.setText(String.valueOf(pointsv.size()));
            
          ig2v.drawLine(oldXv, oldYv, currentXv, currentYv);
          
         
          oldXv = currentXv;
          oldYv = currentYv;

       
      }
    });
       
      this.jPanel3.addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
 
       
        currentX = e.getX();
        currentY = e.getY();
          
            ig2=(Graphics2D) jPanel3.getGraphics();
    
            pos++;
            
            points.put("oldX"+pos, oldX);
            points.put("oldY"+pos, oldY);
            points.put("currentX"+pos, currentX);
            points.put("currentY"+pos, currentY);
            
            lbl_ttd_cek1.setText(String.valueOf(points.size()));
            
          ig2.drawLine(oldX, oldY, currentX, currentY);
          
         
          oldX = currentX;
          oldY = currentY;

 // coord x,y when drag mouse
       
      
   
       
      }
    });
       
       
       
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
         
         
         txt_cari_tarif.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               filtertarifcari();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
              filtertarifcari();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              filtertarifcari();
            }
        });
         
         
          txt_cari_status.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               filterstatus();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
              filterstatus();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              filterstatus();
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
         
         
         
         txt_cari_tarif_pengesah.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               cariManualforlog();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
              cariManualforlog();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               cariManualforlog();
            }
        });
         
         this.txt_kode_tarif.requestFocus(); 
         
         try {
             datl=new Crud_local();
             datl.readRec_cariPoli();
             tb_poli.setModel(datl.modelpoli);
             
         } catch (Exception ex) {
             Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         try {
             datl=new Crud_local();
             datl.readRec_cariStatus();
             tb_status.setModel(datl.modelstatus);
             
         } catch (Exception ex) {
             Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         try {
             datl=new Crud_local();
             datl.readRec_cariTarif();
             tb_tarif.setModel(datl.modeltarif);
             
         } catch (Exception ex) {
             Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         
         setukurantbtarif();
    }

    private void refreshtbtarif(){
     try {
             datl=new Crud_local();
             datl.readRec_cariTarif();
             tb_tarif.setModel(datl.modeltarif);
             
         } catch (Exception ex) {
             Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         
         setukurantbtarif();
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
        dlg_cari_status = new javax.swing.JDialog();
        jScrollPane6 = new javax.swing.JScrollPane();
        tb_status = new javax.swing.JTable();
        txt_cari_status = new javax.swing.JTextField();
        dlg_login = new javax.swing.JDialog();
        jLabel12 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        txt_pwd = new javax.swing.JPasswordField();
        lbl_login = new javax.swing.JLabel();
        dlg_loginv = new javax.swing.JDialog();
        jLabel15 = new javax.swing.JLabel();
        txt_usernamev = new javax.swing.JTextField();
        txt_pwdv = new javax.swing.JPasswordField();
        lbl_loginv = new javax.swing.JLabel();
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
        tb_tarif = new javax.swing.JTable();
        txt_cari_tarif = new javax.swing.JTextField();
        bt_cari_tarif = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        bt_save = new javax.swing.JButton();
        bt_add = new javax.swing.JButton();
        bt_delete = new javax.swing.JButton();
        bt_cetak = new javax.swing.JButton();
        bt_add1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txt_keterangan = new javax.swing.JTextArea();
        panel_inputan = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lbl_kode_poli = new javax.swing.JLabel();
        txt_poli = new javax.swing.JTextField();
        bt_cari_poli = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txt_nama_tarif = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_kode_tarif = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lbl_kode_status = new javax.swing.JLabel();
        txt_status = new javax.swing.JTextField();
        bt_cari_status = new javax.swing.JButton();
        txt_stat = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cmb_kelas = new javax.swing.JComboBox<>();
        bt_proses = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lbl_verif = new javax.swing.JLabel();
        lbl_nama_pengesah = new javax.swing.JLabel();
        bt_pengesah = new javax.swing.JButton();
        bt_verif = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_log_publish = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        bt_hapus_ttd = new javax.swing.JButton();
        txt_cari_tarif_pengesah = new javax.swing.JTextField();
        bt_cari_tarif1 = new javax.swing.JButton();
        ck_verif = new javax.swing.JCheckBox();
        ck_pengesah = new javax.swing.JCheckBox();
        ck_pilih_semua = new javax.swing.JCheckBox();
        bt_hapus_ttd1 = new javax.swing.JButton();
        lbl_ttd_cek = new javax.swing.JLabel();
        lbl_ttd_cek1 = new javax.swing.JLabel();
        lbl_ttd_cek2 = new javax.swing.JLabel();
        lbl_ttd_cek3 = new javax.swing.JLabel();
        lbl_nm_tindakan = new javax.swing.JLabel();
        lbl_jasa = new javax.swing.JLabel();
        lbl_tarif = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        lst_save_tarif = new javax.swing.JList<>();
        jLabel5 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        lst_save_publish = new javax.swing.JList<>();
        jLabel7 = new javax.swing.JLabel();

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

        dlg_cari_status.setSize(new java.awt.Dimension(477, 388));
        dlg_cari_status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dlg_cari_statusKeyPressed(evt);
            }
        });

        tb_status.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_status.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_statusMouseReleased(evt);
            }
        });
        tb_status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tb_statusKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tb_status);

        txt_cari_status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cari_statusKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout dlg_cari_statusLayout = new javax.swing.GroupLayout(dlg_cari_status.getContentPane());
        dlg_cari_status.getContentPane().setLayout(dlg_cari_statusLayout);
        dlg_cari_statusLayout.setHorizontalGroup(
            dlg_cari_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlg_cari_statusLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dlg_cari_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_cari_status)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        dlg_cari_statusLayout.setVerticalGroup(
            dlg_cari_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dlg_cari_statusLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(txt_cari_status, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                .addContainerGap())
        );

        dlg_login.setModal(true);
        dlg_login.setResizable(false);
        dlg_login.setSize(new java.awt.Dimension(664, 128));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tarif/usernm.png"))); // NOI18N

        txt_username.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_usernameKeyPressed(evt);
            }
        });

        txt_pwd.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_pwd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_pwdKeyPressed(evt);
            }
        });

        lbl_login.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tarif/gembok_ico.png"))); // NOI18N
        lbl_login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_loginMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout dlg_loginLayout = new javax.swing.GroupLayout(dlg_login.getContentPane());
        dlg_login.getContentPane().setLayout(dlg_loginLayout);
        dlg_loginLayout.setHorizontalGroup(
            dlg_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlg_loginLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(txt_pwd, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(lbl_login, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        dlg_loginLayout.setVerticalGroup(
            dlg_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlg_loginLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(dlg_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_login)
                    .addGroup(dlg_loginLayout.createSequentialGroup()
                        .addGroup(dlg_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(dlg_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_pwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12)))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        dlg_loginLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txt_pwd, txt_username});

        dlg_loginv.setModal(true);
        dlg_loginv.setResizable(false);
        dlg_loginv.setSize(new java.awt.Dimension(664, 128));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tarif/usernm.png"))); // NOI18N

        txt_usernamev.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_usernamev.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_usernamevKeyPressed(evt);
            }
        });

        txt_pwdv.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_pwdv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_pwdvKeyPressed(evt);
            }
        });

        lbl_loginv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tarif/gembok_ico.png"))); // NOI18N
        lbl_loginv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_loginvMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout dlg_loginvLayout = new javax.swing.GroupLayout(dlg_loginv.getContentPane());
        dlg_loginv.getContentPane().setLayout(dlg_loginvLayout);
        dlg_loginvLayout.setHorizontalGroup(
            dlg_loginvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlg_loginvLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_usernamev, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(txt_pwdv, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(lbl_loginv, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dlg_loginvLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txt_pwdv, txt_usernamev});

        dlg_loginvLayout.setVerticalGroup(
            dlg_loginvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlg_loginvLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(dlg_loginvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_loginv)
                    .addGroup(dlg_loginvLayout.createSequentialGroup()
                        .addGroup(dlg_loginvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(dlg_loginvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_pwdv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_usernamev, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12)))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        dlg_loginvLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txt_pwdv, txt_usernamev});

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

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
        jScrollPane1.setViewportView(tb_tarif);

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

        bt_add.setText("Add");
        bt_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_addActionPerformed(evt);
            }
        });

        bt_delete.setText("Delete");
        bt_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_deleteActionPerformed(evt);
            }
        });

        bt_cetak.setText("Print");

        bt_add1.setText("Edit");
        bt_add1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_add1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(bt_save)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_add, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_add1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bt_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(bt_save)
                    .addComponent(bt_add)
                    .addComponent(bt_add1)
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

        lbl_kode_status.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txt_status.setEditable(false);

        bt_cari_status.setText("...");
        bt_cari_status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cari_statusActionPerformed(evt);
            }
        });

        txt_stat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_statKeyPressed(evt);
            }
        });

        jLabel9.setText("Kelas");

        cmb_kelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Kelas 1", "Kelas 2", "Kelas 3", "Kelas VIP" }));
        cmb_kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmb_kelasKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panel_inputanLayout = new javax.swing.GroupLayout(panel_inputan);
        panel_inputan.setLayout(panel_inputanLayout);
        panel_inputanLayout.setHorizontalGroup(
            panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_inputanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_inputanLayout.createSequentialGroup()
                        .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(23, 23, 23)
                        .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_inputanLayout.createSequentialGroup()
                                .addComponent(txt_kode_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_stat, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_nama_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(25, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_inputanLayout.createSequentialGroup()
                        .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel11))
                        .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_inputanLayout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbl_kode_poli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_kode_status, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_status, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_poli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_inputanLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmb_kelas, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(108, 108, 108)))
                        .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bt_cari_poli, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_cari_status, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29))
                    .addGroup(panel_inputanLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panel_inputanLayout.setVerticalGroup(
            panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_inputanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_kode_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_stat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_inputanLayout.createSequentialGroup()
                        .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_nama_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(lbl_kode_status, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bt_cari_status)))
                .addGap(18, 18, 18)
                .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bt_cari_poli)
                        .addComponent(txt_poli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel11)
                    .addComponent(lbl_kode_poli, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cmb_kelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bt_proses.setText("Proses Untuk Verifikasi");
        bt_proses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_prosesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(bt_proses, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panel_inputan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING))
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panel_inputan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_cari_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_cari_tarif)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bt_proses)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        jTabbedPane1.addTab("Master Tarif", jPanel1);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setEnabled(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(390, 100));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel2MousePressed(evt);
            }
        });

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
        jPanel3.setEnabled(false);
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel3MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel3MouseReleased(evt);
            }
        });

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

        lbl_verif.setText("          < TTD Verifikator>");
        jPanel4.add(lbl_verif, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 50, 190, 20));

        lbl_nama_pengesah.setText("          < TTD Pengesah >");
        jPanel4.add(lbl_nama_pengesah, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 220, 20));

        bt_pengesah.setText("Publish Tarif Pengesah");
        bt_pengesah.setEnabled(false);
        bt_pengesah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_pengesahActionPerformed(evt);
            }
        });
        jPanel4.add(bt_pengesah, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 430, -1));

        bt_verif.setText("Publish Tarif Verifikasi");
        bt_verif.setEnabled(false);
        bt_verif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_verifActionPerformed(evt);
            }
        });
        jPanel4.add(bt_verif, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 230, 430, -1));

        tb_log_publish.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        tb_log_publish.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_log_publish.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_log_publishMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tb_log_publish);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 930, 270));

        jLabel8.setText("Log Perubahan Data Publish");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 230, -1));

        bt_hapus_ttd.setText("Hapus TTD");
        bt_hapus_ttd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_hapus_ttdActionPerformed(evt);
            }
        });
        jPanel4.add(bt_hapus_ttd, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 430, 20));

        txt_cari_tarif_pengesah.setPreferredSize(new java.awt.Dimension(4, 25));
        jPanel4.add(txt_cari_tarif_pengesah, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 240, 30));

        bt_cari_tarif1.setText("Cari");
        bt_cari_tarif1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cari_tarif1ActionPerformed(evt);
            }
        });
        jPanel4.add(bt_cari_tarif1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 300, 140, -1));

        ck_verif.setText("Verifikator");
        ck_verif.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ck_verifItemStateChanged(evt);
            }
        });
        ck_verif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ck_verifActionPerformed(evt);
            }
        });
        ck_verif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ck_verifKeyPressed(evt);
            }
        });
        jPanel4.add(ck_verif, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, -1, -1));

        ck_pengesah.setText("Pengesah");
        ck_pengesah.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ck_pengesahItemStateChanged(evt);
            }
        });
        ck_pengesah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ck_pengesahActionPerformed(evt);
            }
        });
        ck_pengesah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ck_pengesahKeyPressed(evt);
            }
        });
        jPanel4.add(ck_pengesah, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        ck_pilih_semua.setText("Pilih Semua");
        ck_pilih_semua.setEnabled(false);
        ck_pilih_semua.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ck_pilih_semuaItemStateChanged(evt);
            }
        });
        ck_pilih_semua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ck_pilih_semuaActionPerformed(evt);
            }
        });
        ck_pilih_semua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ck_pilih_semuaKeyPressed(evt);
            }
        });
        jPanel4.add(ck_pilih_semua, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 300, -1, -1));

        bt_hapus_ttd1.setText("Hapus TTD");
        bt_hapus_ttd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_hapus_ttd1ActionPerformed(evt);
            }
        });
        jPanel4.add(bt_hapus_ttd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 190, 430, 20));

        lbl_ttd_cek.setText("0");
        jPanel4.add(lbl_ttd_cek, new org.netbeans.lib.awtextra.AbsoluteConstraints(891, 60, 50, -1));

        lbl_ttd_cek1.setText("0");
        jPanel4.add(lbl_ttd_cek1, new org.netbeans.lib.awtextra.AbsoluteConstraints(371, 60, 60, -1));

        lbl_ttd_cek2.setText("%");
        jPanel4.add(lbl_ttd_cek2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, -1, -1));

        lbl_ttd_cek3.setText("%");
        jPanel4.add(lbl_ttd_cek3, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 60, -1, -1));

        lbl_nm_tindakan.setText("Nama Tindakan");
        jPanel4.add(lbl_nm_tindakan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 380, 20));

        lbl_jasa.setText("Jasa");
        jPanel4.add(lbl_jasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 370, 670, -1));

        lbl_tarif.setText("Tarif");
        jPanel4.add(lbl_tarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 220, -1));

        jTabbedPane1.addTab("Verifikasi dan Pengesahan", jPanel4);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 990, 700));

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jScrollPane7.setViewportView(lst_save_tarif);

        jLabel5.setText("log save data Tarif");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jScrollPane8.setViewportView(lst_save_publish);

        jLabel7.setText("log save data Publish");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(317, 317, 317))
        );

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 30, 300, 690));

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
          
             //txt_tarif.requestFocus();
           cmb_kelas.requestFocus();
           
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
            this.bt_cari_status.requestFocus();
        }
    }//GEN-LAST:event_txt_nama_tarifKeyPressed

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
        
         DefaultListModel<String> listModel = new DefaultListModel<>();
        
        
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
                     if(!this.txt_status.getText().isEmpty()){
                        if(!cmb_kelas.getSelectedItem().toString().isEmpty()){
                        // 
                         try {
                             //save
                             datl= new Crud_local();
//                             
//                             datl.Save_tarif(txt_kode_tarif.getText(), txt_nama_tarif.getText(),Double.valueOf(txt_tarif.getText()),Integer.valueOf(txt_dr.getText()), Integer.valueOf(txt_rs.getText()), 
//                                     Integer.valueOf(txt_sarana.getText()), Integer.valueOf(lbl_kode_poli.getText()) ,
//                                     Integer.valueOf(lbl_kode_status.getText()) , "add", "add", txt_keterangan.getText(),this.cmb_kelas.getSelectedItem().toString());
//                           
                             listModel.addElement("Tersimpan:"+this.txt_kode_tarif.getText()+" "+Utilitas.tglsekarangJam());
                             
                             lst_save_tarif.setModel(listModel);
                             
                             refreshtbtarif();
                             
                         } catch (Exception ex) {
                                 listModel.addElement("Gagal Simpan:"+this.txt_kode_tarif.getText()+" "+Utilitas.tglsekarangJam());
                             
                             lst_save_tarif.setModel(listModel);
                             Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
                         }
                        
                        } //
                        else{
                           JOptionPane.showMessageDialog(null, "Maaf Kelas Tidak Boleh Kosong !");
                           this.cmb_kelas.requestFocus();
                        }
                     }
                     else{
                      JOptionPane.showMessageDialog(null, "Maaf Status Tidak Boleh Kosong !");
                      this.bt_cari_poli.requestFocus();
                     
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

    private String rep_titiknol(String txt){
       String tmp=txt;
       
       return tmp.replace(".0", "");
    }
    
    private void bt_saveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_saveMouseEntered
        // TODO add your handling code here:
      int pr= Integer.valueOf(rep_titiknol(this.txt_rs.getText()))+Integer.valueOf(rep_titiknol(this.txt_dr.getText()))+Integer.valueOf(rep_titiknol(this.txt_sarana.getText()));
     
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

    
   
    private void jPanel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MousePressed
        // TODO add your handling code here:
       // corat_coret(evt.getX(),evt.getY());
//        System.out.println("X : " +evt.getX());
//        System.out.println("Y : " +evt.getY());
      oldX=evt.getX();
     oldY=evt.getY();

 
    }//GEN-LAST:event_jPanel3MousePressed

    private void bt_hapus_ttdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_hapus_ttdActionPerformed
        // TODO add your handling code here:
        points.clear();
        pos=0;
        this.jPanel3.repaint();
         lbl_ttd_cek1.setText("0");
    }//GEN-LAST:event_bt_hapus_ttdActionPerformed

    private void bt_pengesahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_pengesahActionPerformed
        
        try {
                // TODO add your handling code here:
                    
                 saveImage();
                 } catch (IOException ex) {
                    Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
                 }
        
    }//GEN-LAST:event_bt_pengesahActionPerformed

    private void jPanel3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel3MouseReleased

    private void bt_cari_statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_statusActionPerformed
        // TODO add your handling code here:
        dlg_cari_status.setLocationRelativeTo(this);
        this.dlg_cari_status.setVisible(true);
    }//GEN-LAST:event_bt_cari_statusActionPerformed

    private void tb_statusMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_statusMouseReleased
        // TODO add your handling code here:
        // TODO add your handling code here:
         if (evt.getClickCount() == 2) {
            int row = this.tb_status.getSelectedRow();

           if (row == -1) {
            // No row selected
          } else {   
            lbl_kode_status.setText(this.tb_status.getModel().getValueAt(row, 0).toString());
            this.txt_status.setText(this.tb_status.getModel().getValueAt(row, 1).toString());
            this.dlg_cari_status.setVisible(false);
            this.bt_cari_poli.requestFocus();
         }
       }
    }//GEN-LAST:event_tb_statusMouseReleased

    private void tb_statusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_statusKeyPressed
        // TODO add your handling code here:
          if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
             int row = this.tb_status.getSelectedRow();

        if (row == -1) {
            // No row selected
        } else {
            
           lbl_kode_status.setText(this.tb_status.getModel().getValueAt(row, 0).toString());
           this.txt_status.setText(this.tb_status.getModel().getValueAt(row, 1).toString());
           this.dlg_cari_status.setVisible(false);
          
             this.bt_cari_poli.requestFocus();
         
           
         }
        
        }
    }//GEN-LAST:event_tb_statusKeyPressed

    private void txt_cari_statusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cari_statusKeyPressed
        // TODO add your handling code here:
         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
             tb_status.requestFocus();
             tb_status.setRowSelectionInterval(0, 0);
         }
    }//GEN-LAST:event_txt_cari_statusKeyPressed

    private void dlg_cari_statusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dlg_cari_statusKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_dlg_cari_statusKeyPressed

    private void settarif(){
        int row = this.tb_tarif.getSelectedRow();

        if (row == -1) {
            // No row selected
        } else {
           this.txt_kode_tarif.setText(tb_tarif.getModel().getValueAt(row,0).toString()); 
           this.txt_nama_tarif.setText(tb_tarif.getModel().getValueAt(row,1).toString()); 
           this.txt_status.setText(tb_tarif.getModel().getValueAt(row,7).toString()); 
           this.lbl_kode_status.setText(tb_tarif.getModel().getValueAt(row,10).toString()); 
           this.lbl_kode_poli.setText(tb_tarif.getModel().getValueAt(row,9).toString()); 
           this.txt_poli.setText(tb_tarif.getModel().getValueAt(row,6).toString()); 
           this.txt_tarif.setText(tb_tarif.getModel().getValueAt(row,2).toString()); 
           this.txt_rs.setText(rep_titiknol(tb_tarif.getModel().getValueAt(row,3).toString())); 
           this.txt_dr.setText(rep_titiknol(tb_tarif.getModel().getValueAt(row,4).toString())); 
           this.txt_sarana.setText(rep_titiknol(tb_tarif.getModel().getValueAt(row,5).toString())); 
           txt_stat.setText(tb_tarif.getModel().getValueAt(row,12).toString());
           this.cmb_kelas.setSelectedItem(tb_tarif.getModel().getValueAt(row,13).toString());
           
//-
//1
//2
//3
//VIP
        }     
    }
    private void tb_tarifMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_tarifMouseReleased
        // TODO add your handling code here:
         if (evt.getClickCount() == 2) {
            
           settarif();
        }
    }//GEN-LAST:event_tb_tarifMouseReleased

    private void bt_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_addActionPerformed
        // TODO add your handling code here:
        Utilitas.HapusText(panel_inputan);
        Utilitas.HapusText(jPanel5);
        
        lbl_kode_status.setText("");
        lbl_kode_poli.setText("");
        lbl_cepe.setText("");
        
        txt_kode_tarif.requestFocus();
        
    }//GEN-LAST:event_bt_addActionPerformed

    private void bt_add1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_add1ActionPerformed
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
                     if(!this.txt_status.getText().isEmpty()){
                         if(!cmb_kelas.getSelectedItem().toString().isEmpty()){
                         //
                         try {
                             //save
                             datl= new Crud_local();
                           
                            if(this.txt_stat.getText().equals("ok")||this.txt_stat.getText().equals("edit")) {
                                
                              JOptionPane.showMessageDialog(null, "Tidak bisa Edit Karena sudah di Verif!");
                            }
                            else{
                                datl.updateTarif(txt_kode_tarif.getText(), txt_nama_tarif.getText(),Double.valueOf(txt_tarif.getText()),Integer.valueOf(txt_dr.getText()), Integer.valueOf(txt_rs.getText()), 
                                     Integer.valueOf(txt_sarana.getText()), Integer.valueOf(lbl_kode_poli.getText()) ,
                                     Integer.valueOf(lbl_kode_status.getText()) , "add", "add", txt_keterangan.getText(),this.cmb_kelas.getSelectedItem().toString());
                             
                             refreshtbtarif();
                                
                                
                            }
                             
                         } catch (Exception ex) {
                             Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
                         }
                         }
                         else{
                           JOptionPane.showMessageDialog(null, "Maaf Kelas Tidak Boleh Kosong !");
                           this.cmb_kelas.requestFocus();
                         }
                        
                     }
                     else{
                      JOptionPane.showMessageDialog(null, "Maaf Status Tidak Boleh Kosong !");
                      this.bt_cari_poli.requestFocus();
                     
                     }
                  }
                }
        }

    }//GEN-LAST:event_bt_add1ActionPerformed

    private void lbl_loginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_loginMouseClicked
        // TODO add your handling code here:
        
         this.loginAct(); 
        
    }//GEN-LAST:event_lbl_loginMouseClicked

    private void bt_hapus_ttd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_hapus_ttd1ActionPerformed
        // TODO add your handling code here:
        pointsv.clear();
        posv=0;
        lbl_ttd_cek.setText("0");
        this.jPanel2.repaint();
        
    }//GEN-LAST:event_bt_hapus_ttd1ActionPerformed

    private void ck_pengesahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ck_pengesahActionPerformed
        // TODO add your handling code here:
        ck_pengesah.setSelected(true);
      ck_verif.setSelected(false);
            
           dlg_login.setLocationRelativeTo(this);
          this.dlg_login.setVisible(true);
       
          this.txt_username.requestFocus();
        
      
    }//GEN-LAST:event_ck_pengesahActionPerformed

    private void ck_pengesahItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ck_pengesahItemStateChanged
        // TODO add your handling code here:
        if(ck_pengesah.isSelected()) {//checkbox has been selected
            
           dlg_login.setLocationRelativeTo(this);
          this.dlg_login.setVisible(true);
       
          this.txt_username.requestFocus();
        
        } else {
            
        }
       
    
    }//GEN-LAST:event_ck_pengesahItemStateChanged

    private void txt_usernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_usernameKeyPressed
        // TODO add your handling code here:Kis
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        
        this.txt_pwd.requestFocus();
        
        }
                
    }//GEN-LAST:event_txt_usernameKeyPressed

    private void txt_pwdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pwdKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        
            loginAct();
        }
    }//GEN-LAST:event_txt_pwdKeyPressed

    private void ck_pengesahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ck_pengesahKeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            
             this.ck_pengesah.setSelected(true);
            
        }
    }//GEN-LAST:event_ck_pengesahKeyPressed

    private void ck_pilih_semuaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ck_pilih_semuaItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ck_pilih_semuaItemStateChanged

    private void ck_pilih_semuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ck_pilih_semuaActionPerformed
        // TODO add your handling code here:
        if(ck_pilih_semua.isSelected()) {//checkbox has been selected
           
            
           for(int i=0;i<this.tb_log_publish.getModel().getRowCount();i++){ 
            this.tb_log_publish.getModel().setValueAt(true,i,10);
           }
            
            
        } else {
           for(int i=0;i<this.tb_log_publish.getModel().getRowCount();i++){ 
            this.tb_log_publish.getModel().setValueAt(false,i,10);
           }

        }
    }//GEN-LAST:event_ck_pilih_semuaActionPerformed

    private void ck_pilih_semuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ck_pilih_semuaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ck_pilih_semuaKeyPressed

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed
        // TODO add your handling code here:
              oldXv=evt.getX();
     oldYv=evt.getY();
    }//GEN-LAST:event_jPanel2MousePressed

    private void txt_usernamevKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_usernamevKeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        
        this.txt_pwdv.requestFocus();
        
        }
    }//GEN-LAST:event_txt_usernamevKeyPressed

    private void txt_pwdvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pwdvKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        
            loginAct();
        }
    }//GEN-LAST:event_txt_pwdvKeyPressed

    private void lbl_loginvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_loginvMouseClicked
        // TODO add your handling code here:
         this.loginAct(); 
    }//GEN-LAST:event_lbl_loginvMouseClicked

    private void ck_verifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ck_verifActionPerformed
        // TODO add your handling code here:
          ck_verif.setSelected(true);
      ck_pengesah.setSelected(false);
            
           dlg_loginv.setLocationRelativeTo(this);
          this.dlg_loginv.setVisible(true);
       
          this.txt_usernamev.requestFocus();
    }//GEN-LAST:event_ck_verifActionPerformed

    private void ck_verifItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ck_verifItemStateChanged
        // TODO add your handling code here:
         if(ck_verif.isSelected()) {//checkbox has been selected
            
           dlg_loginv.setLocationRelativeTo(this);
          this.dlg_loginv.setVisible(true);
       
          this.txt_usernamev.requestFocus();
        
        } else {
            
        }
    }//GEN-LAST:event_ck_verifItemStateChanged

    private void ck_verifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ck_verifKeyPressed
        // TODO add your handling code here:
          if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            
             this.ck_verif.setSelected(true);
            
        }
    }//GEN-LAST:event_ck_verifKeyPressed

    private void bt_verifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_verifActionPerformed
      try {
          // TODO add your handling code here:
          this.saveImagev();
      } catch (IOException ex) {
          Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
      }
    }//GEN-LAST:event_bt_verifActionPerformed

    private void bt_prosesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_prosesActionPerformed
        // TODO add your handling code here:
        
        int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah Akan di Proses Untuk Verifikasi Pastikan Data Sudah Benar Data Yg Sudah di Proses Verif tidak bisa di Hapus atau Edit?","Warning ",
           JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
   
       if(dialogResult == JOptionPane.YES_OPTION){   
        
         this.refreshtbtarif();
        
          DefaultListModel<String> listModel = new DefaultListModel<>();
         
        for ( int i = 0; i < tb_tarif.getRowCount(); i++) {
            String sp=tb_tarif.getModel().getValueAt(i, 11).toString();
            String sv=tb_tarif.getModel().getValueAt(i, 12).toString();
            
            if(!(sv.equals("ok")||sp.equals("pending"))){
              datl.updateTarifStatus(this.tb_tarif.getModel().getValueAt(i, 0).toString(), "edit", "edit");
              listModel.addElement("Proses Untuk Verif:"+this.tb_tarif.getModel().getValueAt(i, 0).toString()+" "+Utilitas.tglsekarangJam());
                             
              lst_save_tarif.setModel(listModel);
              
            }
            else{
               listModel.addElement("Tidak Bisa di Proses Verif:"+this.tb_tarif.getModel().getValueAt(i, 0).toString()+" "+Utilitas.tglsekarangJam());
                             
               lst_save_tarif.setModel(listModel);
            }
            
           }
        
        this.refreshtbtarif();
        
       }  
        
    }//GEN-LAST:event_bt_prosesActionPerformed

    private void txt_statKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_statKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_statKeyPressed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        // TODO add your handling code here:
        this.refreshtbtarif();
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void bt_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_deleteActionPerformed
        // TODO add your handling code here:
     if(!txt_kode_tarif.getText().isEmpty()){   
         if(this.txt_stat.getText().equals("ok")||this.txt_stat.getText().equals("edit")) {
                                
             JOptionPane.showMessageDialog(null, "Tidak bisa Delete Karena sudah di Verif!");
             
           }
       else{
          
           int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah Akan di Hapus?","Warning ",
           JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
   
       if(dialogResult == JOptionPane.YES_OPTION){     
             
             try {
                 
                 datl= new Crud_local();
                
                 datl.DelRec(txt_kode_tarif.getText());
                 
                 refreshtbtarif();
                 
                 
             } catch (SQLException ex) {
                 Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
             } catch (Exception ex) {
                 Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
             }
                                
          }                      
        }
     }
     else{
      JOptionPane.showMessageDialog(null, "Pilih dulu di table untuk menghapus Data!");
     }
    }//GEN-LAST:event_bt_deleteActionPerformed

    private void cmb_kelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmb_kelasKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
           txt_tarif.requestFocus();
        }
    }//GEN-LAST:event_cmb_kelasKeyPressed

    private void tb_log_publishMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_log_publishMouseClicked
        // TODO add your handling code here:
        
       int row = this.tb_log_publish.getSelectedRow();

        if (row == -1) {
            // No row selected
        } else {  
          lbl_nm_tindakan.setText("Nama Tindakan : "+tb_log_publish.getModel().getValueAt(row, 1).toString());
          lbl_tarif.setText("Tarif "+ Utilitas.formatuang(Double.valueOf(tb_log_publish.getModel().getValueAt(row, 2).toString())));
          lbl_jasa.setText("RS :"+tb_log_publish.getModel().getValueAt(row, 3).toString()
                  +"      Dr :"+tb_log_publish.getModel().getValueAt(row, 4).toString()+"      Sarana :"+tb_log_publish.getModel().getValueAt(row, 5).toString());
          
        }
    }//GEN-LAST:event_tb_log_publishMouseClicked

    private void bt_cari_tarif1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cari_tarif1ActionPerformed
        // TODO add your handling code here:
       cariManualforlog();
        
    }//GEN-LAST:event_bt_cari_tarif1ActionPerformed

    private void cariManualforlog(){
       
        String s=txt_cari_tarif_pengesah.getText();
        
      if(!s.isEmpty())  
        for(int i=0;i<tb_log_publish.getRowCount();i++){
           if( StringUtils.containsIgnoreCase(tb_log_publish.getValueAt(i, 1).toString(), s)){
             //  tb_log_publish.requestFocus();
               tb_log_publish.setRowSelectionInterval(i, i);
               tb_log_publish.scrollRectToVisible(new Rectangle(tb_log_publish.getCellRect(i, 0, true)));
           }
        }
    
    }
    
    private void filtertarifcari(){
    
          try {
                datl = new Crud_local();

                try {
                    datl.readRec_cariTarif(txt_cari_tarif.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

                tb_tarif.setModel(datl.modeltarif);
                
                setukurantbtarif();
                
            } catch (Exception ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void filtertarif(){
    
          try {
                datl = new Crud_local();

                try {
                    datl.readRec_cariTarif();
                } catch (SQLException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

                tb_tarif.setModel(datl.modeltarif);
                
                setukurantbtarif();
                
            } catch (Exception ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void filterstatus(){
    
          try {
                datl = new Crud_local();

                try {
                    datl.readRec_cariStatus(txt_cari_status.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

                tb_status.setModel(datl.modelstatus);
            } catch (Exception ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
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
    public void saveImagev() throws IOException {
     
        DefaultListModel<String> listModel = new DefaultListModel<>();
        
        int i = 0;
        
    if(Integer.valueOf(lbl_ttd_cek.getText().toString())>1000){      
         
      int w=jPanel2.getWidth(),h=jPanel2.getHeight();
       
      biv=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
       
     ig2v=biv.createGraphics();
     
    
     ig2v.setColor(java.awt.Color.RED);
   
    System.out.println("point "+pointsv.size());
    
    for(int ip=1;ip<posv;ip++){  
         //System.out.println("oldX"+i);
         
          ig2v.drawLine(pointsv.get("oldX"+ip), pointsv.get("oldY"+ip), pointsv.get("currentX"+ip),pointsv.get("currentY"+ip));
    }
      
     
      
        try
        {
            File f=new File("clipv.jpg");
            ImageIO.write(biv, "jpg", f);
            
            pathFile=f.getAbsolutePath();
            
       try {
            
        
              datl=new Crud_local();
         
            if(datl.cekvalpilih.size()>0){  
              
              for ( i = 0; i < tb_log_publish.getRowCount(); i++) {
		    Boolean chked = Boolean.valueOf(tb_log_publish.getValueAt(i, 10)
							.toString());
		   
		    if (chked) {
		       datl.Save_log_tarif("",txt_usernamev.getText(), this.tb_log_publish.getModel().getValueAt(i, 0).toString(), "pending", "ok", pathFile);
                       datl.updateTarifStatus(this.tb_log_publish.getModel().getValueAt(i, 0).toString(), "pending", "ok");
		       listModel.addElement("Tersimpan:"+this.tb_log_publish.getModel().getValueAt(i, 0).toString()+" "+Utilitas.tglsekarangJam());
                    }
		}
              
               lst_save_publish.setModel(listModel);
              
                posv=0;
               
               this.txt_username.setText("");
               this.txt_pwd.setText("");
               this.txt_usernamev.setText("");
               this.txt_pwdv.setText("");
               pointsv.clear();
               lbl_ttd_cek.setText("0");
               this.jPanel2.repaint();
               
               refreshTblog();
                
             }
             else{
                JOptionPane.showMessageDialog(null, "Tarif Belum ada yang di ceklist");
             }
          
          
          } catch (Exception ex) {
              //JOptionPane.showMessageDialog(null, "Gagal Simpan");
               listModel.addElement("Gagal Simpan:"+this.tb_log_publish.getModel().getValueAt(i, 0).toString()+" "+Utilitas.tglsekarangJam());
              Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
          }        }
        catch(IOException ioe)
        {
            System.out.println("Clipv write help: " + ioe.getMessage());
        }
       }//end ttd
         else{ 
               JOptionPane.showMessageDialog(null, "Ttd Belum mencapai 1000 %");
        }//end ttd
    }
    
    public void saveImage() throws IOException {
         
      
    DefaultListModel<String> listModel = new DefaultListModel<>();
        
    int i = 0;
        
    if(Integer.valueOf(lbl_ttd_cek1.getText().toString())>1000){      
         
      int w=jPanel3.getWidth(),h=jPanel3.getHeight();
       
      bi=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
       
     ig2=bi.createGraphics();
     
    
     ig2.setColor(java.awt.Color.RED);
   
        
    for(int ip=1;ip<pos;ip++){  
         //System.out.println("oldX"+i);
         
          ig2.drawLine(points.get("oldX"+ip), points.get("oldY"+ip), points.get("currentX"+ip),points.get("currentY"+ip));
    }
      
    
      
        try
        {
            File f=new File("clip.jpg");
            ImageIO.write(bi, "jpg", f);
            
            pathFile=f.getAbsolutePath();
            
       try {
            
        
              datl=new Crud_local();
         
            if(datl.cekvalpilih.size()>0){  
              
              for ( i = 0; i < tb_log_publish.getRowCount(); i++) {
		    Boolean chked = Boolean.valueOf(tb_log_publish.getValueAt(i, 10)
							.toString());
		   
		    if (chked) {
		       datl.updateTarifStatusLogPengesah(this.tb_log_publish.getModel().getValueAt(i, 0).toString(), "ok",
                               txt_username.getText(),pathFile);
                       datl.updateTarifStatus(this.tb_log_publish.getModel().getValueAt(i, 0).toString(), "ok", "ok");
		       listModel.addElement("Tersimpan Pengesah:"+this.tb_log_publish.getModel().getValueAt(i, 0).toString()+" "+Utilitas.tglsekarangJam());
                    }
		}
              
               lst_save_publish.setModel(listModel);
              
                 pos=0;
                 
               this.txt_username.setText("");
               this.txt_pwd.setText("");
               this.txt_usernamev.setText("");
               this.txt_pwdv.setText("");
               points.clear();
               lbl_ttd_cek1.setText("0");
               this.jPanel3.repaint();
               
               refreshTblogPengesah();
                
             }
             else{
                JOptionPane.showMessageDialog(null, "Tarif Belum ada yang di ceklist");
             }
          
          
          } catch (Exception ex) {
              //JOptionPane.showMessageDialog(null, "Gagal Simpan");
               listModel.addElement("Gagal Simpan:"+this.tb_log_publish.getModel().getValueAt(i, 0).toString()+" "+Utilitas.tglsekarangJam());
              Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
          }        }
        catch(IOException ioe)
        {
            System.out.println("Clip write help: " + ioe.getMessage());
        }
       }//end ttd
         else{ 
               JOptionPane.showMessageDialog(null, "Ttd Belum mencapai 1000 %");
        }//end ttd

            
        
        
    }
      
    private void setukurantbLog(){
        
        this.tb_log_publish.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
     tb_log_publish.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
    TableColumnModel tr = tb_log_publish.getColumnModel(); 
    
    tr.getColumn(0).setPreferredWidth(100);
    tr.getColumn(1).setPreferredWidth(240);
    tr.getColumn(2).setPreferredWidth(100);
    tr.getColumn(3).setPreferredWidth(50);
    tr.getColumn(4).setPreferredWidth(50);
    tr.getColumn(5).setPreferredWidth(70);
    tr.getColumn(6).setPreferredWidth(70);
    tr.getColumn(7).setPreferredWidth(70);
    tr.getColumn(8).setPreferredWidth(70);
    tr.getColumn(9).setPreferredWidth(50);
    tr.getColumn(10).setPreferredWidth(50);
     
    
     
    }
    
    
    private void loginAct(){
    
      try {
          // TODO add your handling code here:
          
          // StrPr c = new StrPr();
          
          
          datl = new Crud_local();
      } catch (Exception ex) {
          Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
      }
            
      try {
         if(this.ck_pengesah.isSelected()==true){ 
          datl.Cek(txt_username.getText(),"p");
           setloginp();
         }
         else if(this.ck_verif.isSelected()==true){ 
          datl.Cek(txt_usernamev.getText(),"v");
          setloginv();
         }
          
      } catch (SQLException ex) {
          Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
      }
            
          
            
    }
    
     private void refreshTblogPengesah(){
            
          try {
              datl=new Crud_local();
               try {
              datl.readRec_cariTariflogPengesah();
              
               
               
          } catch (SQLException ex) {
              Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null, "Gagal Login!");
          }
          } catch (Exception ex) {
              Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null, "Gagal Login!");
          }
         
              tb_log_publish.setModel(datl.modeltariflog);
               setukurantbLog();
    }
    
    
    private void refreshTblog(){
            
          try {
              datl=new Crud_local();
               try {
              datl.readRec_cariTariflog();
              
               
               
          } catch (SQLException ex) {
              Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null, "Gagal Login!");
          }
          } catch (Exception ex) {
              Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null, "Gagal Login!");
          }
         
              tb_log_publish.setModel(datl.modeltariflog);
               setukurantbLog();
    }
    
    private void setloginv(){
    
      if (Crud_local.usm.equals(txt_usernamev.getText()) && Crud_local.psm.equals(txt_pwdv.getText())) {
                
          try {
              datl=new Crud_local();
               try {
              datl.readRec_cariTariflog();
              
               
               
               if(this.ck_pengesah.isSelected()==true){ 
                   lbl_nama_pengesah.setText("<"+Crud_local.usm+">");
                    this.bt_pengesah.setEnabled(true);
                 }
               else if(this.ck_verif.isSelected()==true){ 
                   lbl_verif.setText("<"+Crud_local.usm+">");
                    this.bt_verif.setEnabled(true);
                }
               
               
          } catch (SQLException ex) {
              Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null, "Gagal Login!");
          }
          } catch (Exception ex) {
              Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null, "Gagal Login!");
          }
         
              tb_log_publish.setModel(datl.modeltariflog);
               setukurantbLog();
                this.dlg_loginv.setVisible(false);
               
                this.ck_pilih_semua.setEnabled(true);
                
            }else{
              
                JOptionPane.showMessageDialog(null, "Login salah!");
            
            }
            
    }
    
    
    private void setloginp(){
    
      if (Crud_local.usm.equals(txt_username.getText()) && Crud_local.psm.equals(txt_pwd.getText())) {
                
          try {
              datl=new Crud_local();
               try {
              datl.readRec_cariTariflogPengesah();
              
               
               
               if(this.ck_pengesah.isSelected()==true){ 
                   lbl_nama_pengesah.setText("<"+Crud_local.usm+">");
                    this.bt_pengesah.setEnabled(true);
                 }
               else if(this.ck_verif.isSelected()==true){ 
                   lbl_verif.setText("<"+Crud_local.usm+">");
                    this.bt_verif.setEnabled(true);
                }
               
               
          } catch (SQLException ex) {
              Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null, "Gagal Login!");
          }
          } catch (Exception ex) {
              Logger.getLogger(frm_tarif_bak.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null, "Gagal Login!");
          }
         
              tb_log_publish.setModel(datl.modeltariflog);
               setukurantbLog();
                this.dlg_login.setVisible(false);
                
                this.ck_pilih_semua.setEnabled(true);
                
            }else{
              
                JOptionPane.showMessageDialog(null, "Login salah!");
            
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
            java.util.logging.Logger.getLogger(frm_tarif_bak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_tarif_bak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_tarif_bak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_tarif_bak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_tarif_bak().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_add;
    private javax.swing.JButton bt_add1;
    private javax.swing.JButton bt_cari_poli;
    private javax.swing.JButton bt_cari_status;
    private javax.swing.JButton bt_cari_tarif;
    private javax.swing.JButton bt_cari_tarif1;
    private javax.swing.JButton bt_cetak;
    private javax.swing.JButton bt_delete;
    private javax.swing.JButton bt_hapus_ttd;
    private javax.swing.JButton bt_hapus_ttd1;
    private javax.swing.JButton bt_pengesah;
    private javax.swing.JButton bt_proses;
    private javax.swing.JButton bt_save;
    private javax.swing.JButton bt_verif;
    private javax.swing.JCheckBox ck_pengesah;
    private javax.swing.JCheckBox ck_pilih_semua;
    private javax.swing.JCheckBox ck_verif;
    private javax.swing.JComboBox<String> cmb_kelas;
    private javax.swing.JDialog dlg_cari_poli;
    private javax.swing.JDialog dlg_cari_status;
    private javax.swing.JDialog dlg_login;
    private javax.swing.JDialog dlg_loginv;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbl_cepe;
    private javax.swing.JLabel lbl_dokter;
    private javax.swing.JLabel lbl_jasa;
    private javax.swing.JLabel lbl_kode_poli;
    private javax.swing.JLabel lbl_kode_status;
    private javax.swing.JLabel lbl_login;
    private javax.swing.JLabel lbl_loginv;
    private javax.swing.JLabel lbl_nama_pengesah;
    private javax.swing.JLabel lbl_nm_tindakan;
    private javax.swing.JLabel lbl_rs;
    private javax.swing.JLabel lbl_sarana;
    private javax.swing.JLabel lbl_tarif;
    private javax.swing.JLabel lbl_ttd_cek;
    private javax.swing.JLabel lbl_ttd_cek1;
    private javax.swing.JLabel lbl_ttd_cek2;
    private javax.swing.JLabel lbl_ttd_cek3;
    private javax.swing.JLabel lbl_verif;
    private javax.swing.JList<String> lst_save_publish;
    private javax.swing.JList<String> lst_save_tarif;
    private javax.swing.JPanel panel_inputan;
    private javax.swing.JTable tb_log_publish;
    private javax.swing.JTable tb_poli;
    private javax.swing.JTable tb_status;
    private javax.swing.JTable tb_tarif;
    private javax.swing.JTextField txt_cari_poli;
    private javax.swing.JTextField txt_cari_status;
    private javax.swing.JTextField txt_cari_tarif;
    private javax.swing.JTextField txt_cari_tarif_pengesah;
    private javax.swing.JTextField txt_dr;
    private javax.swing.JTextArea txt_keterangan;
    private javax.swing.JTextField txt_kode_tarif;
    private javax.swing.JTextField txt_nama_tarif;
    private javax.swing.JTextField txt_poli;
    private javax.swing.JPasswordField txt_pwd;
    private javax.swing.JPasswordField txt_pwdv;
    private javax.swing.JTextField txt_rs;
    private javax.swing.JTextField txt_sarana;
    private javax.swing.JTextField txt_stat;
    private javax.swing.JTextField txt_status;
    private javax.swing.JTextField txt_tarif;
    private javax.swing.JTextField txt_username;
    private javax.swing.JTextField txt_usernamev;
    // End of variables declaration//GEN-END:variables
}
