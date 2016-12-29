/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarif;

import farmasi.Crud_local;
import farmasi.NewJFrame;
import java.awt.Component;
import java.awt.Cursor;
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
public class frm_tarif extends javax.swing.JFrame {

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
    
    tr.getColumn(14).setPreferredWidth(100);
    tr.getColumn(15).setPreferredWidth(50);
    tr.getColumn(16).setPreferredWidth(50);
    tr.getColumn(17).setPreferredWidth(80);
    tr.getColumn(18).setPreferredWidth(80);
    tr.getColumn(19).setPreferredWidth(80);
   }
  
  
     
    public frm_tarif() {
        initComponents();
        
      txt_stat.setVisible(false);
      txt_stat_bpjs.setVisible(false);
        
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
       
      txt_tarif1.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
              if(txt_tarif1.getText().isEmpty()){
              
                   txt_tarif1.setText("0");
              
              }else{
                
                cekpersen1();
                
              }
            }

            @Override
            public void focusLost(FocusEvent e) {
                
                if(txt_tarif1.getText().isEmpty()){
              
                   txt_tarif1.setText("0");
              
              }
               else{
               
                cekpersen1();
                
                }
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
       
       
       txt_sarana1.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if(txt_tarif1.getText().isEmpty()){
              
                   txt_tarif1.setText("0");
                
                }
                
                
              if(txt_sarana1.getText().isEmpty()){
              
                   txt_sarana1.setText("0");
              
              }else{
                
                cekpersen1();
                
              }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(txt_tarif1.getText().isEmpty()){
              
                   txt_tarif1.setText("0");
                
                }
                if(txt_sarana1.getText().isEmpty()){
              
                   txt_sarana1.setText("0");
              
              }
               else{
               
                cekpersen1();
                
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
       
       
       txt_dr1.addFocusListener(new FocusListener() {

            
            @Override
            public void focusGained(FocusEvent e) {
                if(txt_tarif1.getText().isEmpty()){
              
                   txt_tarif1.setText("0");
                
                }
                
              if(txt_dr1.getText().isEmpty()){
              
                   txt_dr1.setText("0");
              
              }else{
                  int i=0;
               System.out.println("DR"+i++);
                  
                cekpersen1();
                
              }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(txt_tarif1.getText().isEmpty()){
              
                   txt_tarif1.setText("0");
                
                }
                if(txt_dr1.getText().isEmpty()){
              
                   txt_dr1.setText("0");
              
              }
               else{
                
                  
                cekpersen1();
                
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
        
        txt_rs1.addFocusListener(new FocusListener() {

           @Override
            public void focusGained(FocusEvent e) {
                
                if(txt_tarif1.getText().isEmpty()){
              
                   txt_tarif1.setText("0");
                
                }
              if(txt_rs1.getText().isEmpty()){
              
                   txt_rs1.setText("0");
              
              }else{
                
                cekpersen1();
                
              }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(txt_tarif1.getText().isEmpty()){
              
                   txt_tarif1.setText("0");
                
                }
                if(txt_rs1.getText().isEmpty()){
              
                   txt_rs1.setText("0");
              
              }
               else{
               
                cekpersen1();
                
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
        
         txt_sarana1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
              
               hitungpresentasDR1();
               hitungpresentaseRS1();
               hitungpresentaseSR1();
               
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                
                
                if(!txt_sarana1.getText().isEmpty()){
                       
                    hitungpresentasDR1();
                    hitungpresentaseRS1();
                    hitungpresentaseSR1();
                    
                }
                
              
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                   
               hitungpresentasDR1();
               hitungpresentaseRS1();
               hitungpresentaseSR1();
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
          
        
        
         txt_dr1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                   
               hitungpresentasDR1();
               hitungpresentaseRS1();
               hitungpresentaseSR1();
               
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               if(!txt_dr1.getText().isEmpty()){
                   
                    hitungpresentasDR1();
                    hitungpresentaseRS1();
                    hitungpresentaseSR1();
                    
                     
                }
                
              
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                    
               hitungpresentasDR1();
               hitungpresentaseRS1();
               hitungpresentaseSR1();
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
         
          txt_rs1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                    
               hitungpresentasDR1();
               hitungpresentaseRS1();
               hitungpresentaseSR1();
               
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                 if(!txt_rs1.getText().isEmpty()){
                      
                    hitungpresentasDR1();
                    hitungpresentaseRS1();
                    hitungpresentaseSR1();
                    
                   
                }
               
                
              
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                    
               hitungpresentasDR1();
               hitungpresentaseRS1();
               hitungpresentaseSR1();
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
         
        
         txt_tarif1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               if(txt_rs1.getText().isEmpty()){
                
                   txt_rs1.setText("0");
                }
                
                if(txt_dr1.getText().isEmpty()){
                
                 txt_dr1.setText("0");
                }
                
                if(txt_sarana1.getText().isEmpty()){
                
                  txt_sarana1.setText("0");
                }
               hitungpresentasDR1();
               hitungpresentaseRS1();
               hitungpresentaseSR1();
               
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                
                if(txt_rs1.getText().isEmpty()){
                
                   txt_rs1.setText("0");
                }
                
                if(txt_dr1.getText().isEmpty()){
                
                   txt_dr1.setText("0");
                }
                
                 if(txt_sarana1.getText().isEmpty()){
                
                   txt_sarana1.setText("0");
                }
                
                
                if(!txt_tarif1.getText().isEmpty()){
                      
                    hitungpresentasDR1();
                    hitungpresentaseRS1();
                    hitungpresentaseSR1();
                }
                
                
              
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               if(txt_rs1.getText().isEmpty()){
                
                txt_rs1.setText("0");
                }
                
                if(txt_dr1.getText().isEmpty()){
                
                  txt_dr1.setText("0");
                }
                
                 if(txt_sarana1.getText().isEmpty()){
                
                  txt_sarana1.setText("0");
                }
               hitungpresentasDR1();
               hitungpresentaseRS1();
               hitungpresentaseSR1();
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
        //end tarif 
         
         
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
             Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         try {
             datl=new Crud_local();
             datl.readRec_cariStatus();
             tb_status.setModel(datl.modelstatus);
             
         } catch (Exception ex) {
             Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         try {
             datl=new Crud_local();
             datl.readRec_cariTarif();
             tb_tarif.setModel(datl.modeltarif);
             
         } catch (Exception ex) {
             Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         
         setukurantbtarif();
    }

    private void refreshtbtarif(){
     try {
             datl=new Crud_local();
             datl.readRec_cariTarif();
             tb_tarif.setModel(datl.modeltarif);
             
         } catch (Exception ex) {
             Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
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
        jLabel9 = new javax.swing.JLabel();
        cmb_kelas = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        ck_bpjs = new javax.swing.JCheckBox();
        bt_proses = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
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
        jPanel10 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txt_tarif1 = new javax.swing.JTextField();
        lbl_rs1 = new javax.swing.JLabel();
        txt_rs1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lbl_cepe1 = new javax.swing.JLabel();
        txt_dr1 = new javax.swing.JTextField();
        txt_sarana1 = new javax.swing.JTextField();
        lbl_sarana1 = new javax.swing.JLabel();
        lbl_dokter1 = new javax.swing.JLabel();
        bt_edit_bpjs = new javax.swing.JButton();
        bt_hapus_bpjs = new javax.swing.JButton();
        txt_stat = new javax.swing.JTextField();
        txt_stat_bpjs = new javax.swing.JTextField();
        lbl_status_non_bpjs = new javax.swing.JLabel();
        lbl_status_bpjs = new javax.swing.JLabel();
        bt_proses1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lbl_verif = new javax.swing.JLabel();
        lbl_nama_pengesah = new javax.swing.JLabel();
        bt_pengesah = new javax.swing.JButton();
        bt_verif = new javax.swing.JButton();
        bt_hapus_ttd = new javax.swing.JButton();
        txt_cari_tarif_pengesah = new javax.swing.JTextField();
        bt_cari_tarif1 = new javax.swing.JButton();
        ck_verif = new javax.swing.JCheckBox();
        ck_pengesah = new javax.swing.JCheckBox();
        bt_hapus_ttd1 = new javax.swing.JButton();
        lbl_ttd_cek = new javax.swing.JLabel();
        lbl_ttd_cek1 = new javax.swing.JLabel();
        lbl_ttd_cek2 = new javax.swing.JLabel();
        lbl_ttd_cek3 = new javax.swing.JLabel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_log_publish = new javax.swing.JTable();
        lbl_tarif = new javax.swing.JLabel();
        lbl_nm_tindakan = new javax.swing.JLabel();
        lbl_jasa = new javax.swing.JLabel();
        ck_pilih_semua = new javax.swing.JCheckBox();
        jPanel12 = new javax.swing.JPanel();
        lbl_jasa1 = new javax.swing.JLabel();
        lbl_tarif1 = new javax.swing.JLabel();
        lbl_nm_tindakan1 = new javax.swing.JLabel();
        ck_pilih_semua1 = new javax.swing.JCheckBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        tb_log_publishBPJS = new javax.swing.JTable();
        bt_pengesah1 = new javax.swing.JButton();
        bt_verif1 = new javax.swing.JButton();
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
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(956, 38, -1, -1));

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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_tarifMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_tarif);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 950, 210));
        jPanel1.add(txt_cari_tarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 400, 390, 30));

        bt_cari_tarif.setText("Cari");
        jPanel1.add(bt_cari_tarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 400, 70, -1));

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

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 270, -1, -1));

        jLabel10.setText("Keterangan");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 112, -1));

        txt_keterangan.setColumns(20);
        txt_keterangan.setRows(5);
        txt_keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_keteranganKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(txt_keterangan);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 460, 50));

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

        jLabel9.setText("Kelas");

        cmb_kelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Kelas 1", "Kelas 2", "Kelas 3", "Kelas VIP" }));
        cmb_kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmb_kelasKeyPressed(evt);
            }
        });

        jLabel16.setText("BPJS");

        ck_bpjs.setText("Tarif BPJS");
        ck_bpjs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ck_bpjsActionPerformed(evt);
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
                            .addComponent(txt_kode_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_nama_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_inputanLayout.createSequentialGroup()
                        .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel11))
                        .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_inputanLayout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_inputanLayout.createSequentialGroup()
                                        .addComponent(lbl_kode_status, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_status, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_inputanLayout.createSequentialGroup()
                                        .addComponent(lbl_kode_poli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_poli, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_inputanLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmb_kelas, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ck_bpjs, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(82, 82, 82)))
                        .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bt_cari_poli, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_cari_status, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29))
                    .addGroup(panel_inputanLayout.createSequentialGroup()
                        .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel16))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panel_inputanLayout.setVerticalGroup(
            panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_inputanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_kode_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bt_cari_poli)
                            .addComponent(txt_poli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(lbl_kode_poli, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cmb_kelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel_inputanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(ck_bpjs))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel1.add(panel_inputan, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 23, -1, 240));

        bt_proses.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/tick_ico.png"))); // NOI18N
        bt_proses.setText("Proses Verifikasi");
        bt_proses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_prosesActionPerformed(evt);
            }
        });
        jPanel1.add(bt_proses, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 210, 30));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_cepe)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Tarif Non BPJS", jPanel5);

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setText("Tarif Tindakan                         Rp.");
        jPanel10.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 15, 217, 19));

        txt_tarif1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_tarif1KeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_tarif1KeyPressed(evt);
            }
        });
        jPanel10.add(txt_tarif1, new org.netbeans.lib.awtextra.AbsoluteConstraints(251, 15, 173, -1));

        lbl_rs1.setText("Rp.");
        jPanel10.add(lbl_rs1, new org.netbeans.lib.awtextra.AbsoluteConstraints(206, 45, 240, 19));

        txt_rs1.setText("0");
        txt_rs1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_rs1KeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_rs1KeyPressed(evt);
            }
        });
        jPanel10.add(txt_rs1, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 45, 89, -1));

        jLabel19.setText("RS.   %");
        jPanel10.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 45, 73, -1));

        jLabel20.setText("Dokter %");
        jPanel10.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 75, 73, -1));

        jLabel21.setText("Sarana %");
        jPanel10.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 105, 73, -1));

        lbl_cepe1.setText("              %");
        jPanel10.add(lbl_cepe1, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 136, 89, -1));

        txt_dr1.setText("0");
        txt_dr1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dr1KeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dr1KeyPressed(evt);
            }
        });
        jPanel10.add(txt_dr1, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 75, 89, -1));

        txt_sarana1.setText("0");
        txt_sarana1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_sarana1KeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_sarana1KeyPressed(evt);
            }
        });
        jPanel10.add(txt_sarana1, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 105, 89, -1));

        lbl_sarana1.setText("Rp.");
        jPanel10.add(lbl_sarana1, new org.netbeans.lib.awtextra.AbsoluteConstraints(206, 105, 240, 19));

        lbl_dokter1.setText("Rp.");
        jPanel10.add(lbl_dokter1, new org.netbeans.lib.awtextra.AbsoluteConstraints(206, 75, 240, 19));

        bt_edit_bpjs.setText("Edit BPJS");
        bt_edit_bpjs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_edit_bpjsActionPerformed(evt);
            }
        });
        jPanel10.add(bt_edit_bpjs, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, -1, -1));

        bt_hapus_bpjs.setText("Delete BPJS");
        bt_hapus_bpjs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_hapus_bpjsActionPerformed(evt);
            }
        });
        jPanel10.add(bt_hapus_bpjs, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 150, 125, -1));

        jTabbedPane2.addTab("Tarif BPJS", jPanel10);

        jPanel1.add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 23, 464, 240));

        txt_stat.setEditable(false);
        txt_stat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_statKeyPressed(evt);
            }
        });
        jPanel1.add(txt_stat, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 300, 90, -1));

        txt_stat_bpjs.setEditable(false);
        txt_stat_bpjs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_stat_bpjsKeyPressed(evt);
            }
        });
        jPanel1.add(txt_stat_bpjs, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 300, 90, -1));

        lbl_status_non_bpjs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/proces_ico.png"))); // NOI18N
        jPanel1.add(lbl_status_non_bpjs, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, 30));

        lbl_status_bpjs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/proces_ico.png"))); // NOI18N
        jPanel1.add(lbl_status_bpjs, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 270, -1, 30));

        bt_proses1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unit_poli/tick_ico.png"))); // NOI18N
        bt_proses1.setText("Proses Verifikasi BPJS");
        bt_proses1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_proses1ActionPerformed(evt);
            }
        });
        jPanel1.add(bt_proses1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 400, 220, 30));

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

        bt_hapus_ttd.setText("Hapus TTD");
        bt_hapus_ttd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_hapus_ttdActionPerformed(evt);
            }
        });
        jPanel4.add(bt_hapus_ttd, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 430, 20));

        txt_cari_tarif_pengesah.setPreferredSize(new java.awt.Dimension(4, 25));
        jPanel4.add(txt_cari_tarif_pengesah, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 310, 280, 30));

        bt_cari_tarif1.setText("Cari");
        bt_cari_tarif1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cari_tarif1ActionPerformed(evt);
            }
        });
        jPanel4.add(bt_cari_tarif1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 310, 140, -1));

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

        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tb_log_publish.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        tb_log_publish.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tb_log_publish.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_log_publishMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tb_log_publish);

        jPanel11.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 940, 240));

        lbl_tarif.setText("Tarif");
        jPanel11.add(lbl_tarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 220, -1));

        lbl_nm_tindakan.setText("Nama Tindakan");
        jPanel11.add(lbl_nm_tindakan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 640, 20));

        lbl_jasa.setText("Jasa");
        jPanel11.add(lbl_jasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 640, -1));

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
        jPanel11.add(ck_pilih_semua, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 10, -1, -1));

        jTabbedPane3.addTab("Non BPJS", jPanel11);

        lbl_jasa1.setText("Jasa");

        lbl_tarif1.setText("Tarif");

        lbl_nm_tindakan1.setText("Nama Tindakan");

        ck_pilih_semua1.setText("Pilih Semua");
        ck_pilih_semua1.setEnabled(false);
        ck_pilih_semua1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ck_pilih_semua1ItemStateChanged(evt);
            }
        });
        ck_pilih_semua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ck_pilih_semua1ActionPerformed(evt);
            }
        });
        ck_pilih_semua1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ck_pilih_semua1KeyPressed(evt);
            }
        });

        tb_log_publishBPJS.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        tb_log_publishBPJS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tb_log_publishBPJS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_log_publishBPJSMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tb_log_publishBPJS);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(lbl_nm_tindakan1, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 219, Short.MAX_VALUE)
                        .addComponent(ck_pilih_semua1))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(lbl_tarif1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(lbl_jasa1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 945, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_nm_tindakan1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ck_pilih_semua1))
                .addGap(8, 8, 8)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_tarif1)
                    .addComponent(lbl_jasa1))
                .addContainerGap(246, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel12Layout.createSequentialGroup()
                    .addGap(0, 59, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane3.addTab("BPJS", jPanel12);

        jPanel4.add(jTabbedPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 950, 330));

        bt_pengesah1.setText("Publish Tarif Pengesah BPJS");
        bt_pengesah1.setEnabled(false);
        bt_pengesah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_pengesah1ActionPerformed(evt);
            }
        });
        jPanel4.add(bt_pengesah1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 430, -1));

        bt_verif1.setText("Publish Tarif Verifikasi BPJS");
        bt_verif1.setEnabled(false);
        bt_verif1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_verif1ActionPerformed(evt);
            }
        });
        jPanel4.add(bt_verif1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 270, 430, -1));

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

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 40, 300, 690));

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

    private void savenonbpjs(){
    
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
                 
                             
                             datl.Save_tarif(txt_kode_tarif.getText(), txt_nama_tarif.getText(),Double.valueOf(txt_tarif.getText()),Integer.valueOf(txt_dr.getText()), Integer.valueOf(txt_rs.getText()), 
                                     Integer.valueOf(txt_sarana.getText()), Integer.valueOf(lbl_kode_poli.getText()) ,
                                     Integer.valueOf(lbl_kode_status.getText()) , "add", "add", txt_keterangan.getText(),this.cmb_kelas.getSelectedItem().toString(),0,0,0,0,"","","","");
                           
                             listModel.addElement("Tersimpan:"+this.txt_kode_tarif.getText()+" "+Utilitas.tglsekarangJam());
                             
                             lst_save_tarif.setModel(listModel);
                             
                             refreshtbtarif();
                             
                             //cls
                             cls(); 
                             
                         } catch (Exception ex) {
                             Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
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
    }
    
  
  private void savebpjs_fix(){
  
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
                            savebpjs();
                             
                         } catch (Exception ex) {
                             listModel.addElement("Gagal Simpan:"+this.txt_kode_tarif.getText()+" "+Utilitas.tglsekarangJam());
                             
                             lst_save_tarif.setModel(listModel);
                             Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
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
  
  
  }  
  

  private void cls(){
        Utilitas.HapusText(panel_inputan);
        Utilitas.HapusText(jPanel5);
        Utilitas.HapusText(jPanel10);
        
        lbl_kode_status.setText("");
        lbl_kode_poli.setText("");
        lbl_cepe.setText("");
        cmb_kelas.setSelectedIndex(0);
        ck_bpjs.setSelected(false);
        
  }  
  private void savebpjs(){
  
       DefaultListModel<String> listModel = new DefaultListModel<>();
        
       int pr= Integer.valueOf(this.txt_rs1.getText())+Integer.valueOf(this.txt_dr1.getText())+Integer.valueOf(this.txt_sarana1.getText());
     
       lbl_cepe1.setText(" "+pr +" %");
    
    if((Integer.valueOf(this.txt_rs1.getText())+Integer.valueOf(this.txt_dr1.getText())+Integer.valueOf(this.txt_sarana1.getText()))>100){
            JOptionPane.showMessageDialog(null, "Jumlah Presentase Melebihi 100 % ");
            this.txt_rs1.setText("0");
            this.txt_dr1.setText("0");  
            this.txt_sarana1.setText("0");
            this.txt_rs1.requestFocus();
        }
        else{
                if(this.txt_kode_tarif.getText().isEmpty()||txt_nama_tarif.getText().isEmpty()||
                        txt_poli.getText().isEmpty()||txt_tarif.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Maaf Data Inputan Ada yg Kosong!");
                    this.txt_kode_tarif.requestFocus();
                }
                else{
                  if(txt_tarif1.getText().equals("0")){
                      JOptionPane.showMessageDialog(null, "Maaf Tarif/Presentase Tidak Boleh 0!");
                      this.txt_tarif1.requestFocus();
                  }
                  else if((Integer.valueOf(this.txt_rs1.getText())+Integer.valueOf(this.txt_dr1.getText())+Integer.valueOf(this.txt_sarana1.getText()))<100){
                   JOptionPane.showMessageDialog(null, "Maaf Presentase kurang dari 100 !");
                    this.txt_rs1.requestFocus();
                  }
                  else{
                     if(!this.txt_status.getText().isEmpty()){
                        if(!cmb_kelas.getSelectedItem().toString().isEmpty()){
                        // 
                         try {
                             //save
                             datl= new Crud_local();
                             
                             datl.Save_tarif(txt_kode_tarif.getText(), txt_nama_tarif.getText(),Double.valueOf(txt_tarif.getText()),Integer.valueOf(txt_dr.getText()), Integer.valueOf(txt_rs.getText()), 
                                     Integer.valueOf(txt_sarana.getText()), Integer.valueOf(lbl_kode_poli.getText()) ,
                                     Integer.valueOf(lbl_kode_status.getText()) , "add", "add", txt_keterangan.getText(),this.cmb_kelas.getSelectedItem().toString(),Double.valueOf(txt_tarif1.getText()),Integer.valueOf(txt_dr1.getText()), Integer.valueOf(txt_rs1.getText()), 
                                     Integer.valueOf(txt_sarana1.getText()),String.valueOf(this.ck_bpjs.isSelected()),"add","add",txt_kode_tarif.getText()+"-BPJS");
                           
                             
                             listModel.addElement("Tersimpan:"+this.txt_kode_tarif.getText()+" "+Utilitas.tglsekarangJam());
                             
                             lst_save_tarif.setModel(listModel);
                             
                             refreshtbtarif();
                             
                             //cls
                             cls();   
                                
                         } catch (Exception ex) {
                              listModel.addElement("Gagal Simpan:"+this.txt_kode_tarif.getText()+" "+Utilitas.tglsekarangJam());
                             
                             lst_save_tarif.setModel(listModel);
                             Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
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
    
  }
    
    private void bt_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_saveActionPerformed
        // TODO add your handling code here:
        
          if(this.ck_bpjs.isSelected()){
              this.savebpjs();
          }
          else{
              this.savenonbpjs();
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
      
      int pr1= Integer.valueOf(rep_titiknol(this.txt_rs1.getText()))+Integer.valueOf(rep_titiknol(this.txt_dr1.getText()))+Integer.valueOf(rep_titiknol(this.txt_sarana1.getText()));
     
      lbl_cepe1.setText(" "+pr1 +" %");
      
      
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
        
         this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
          saveImage();
        this.setCursor(Cursor.getDefaultCursor());
        
        } catch (IOException ex) {
           Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
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
           //=====================
           
           this.txt_tarif1.setText(tb_tarif.getModel().getValueAt(row,14).toString()); 
           this.txt_rs1.setText(rep_titiknol(tb_tarif.getModel().getValueAt(row,15).toString())); 
           this.txt_dr1.setText(rep_titiknol(tb_tarif.getModel().getValueAt(row,16).toString())); 
           this.txt_sarana1.setText(rep_titiknol(tb_tarif.getModel().getValueAt(row,17).toString())); 
           
           
           try{
            
               this.ck_bpjs.setSelected(Boolean.parseBoolean(tb_tarif.getModel().getValueAt(row,18).toString())); 
          
           }
           catch(Exception e){
           this.ck_bpjs.setSelected(false);
           }
           
           txt_stat_bpjs.setText(tb_tarif.getModel().getValueAt(row,20).toString());
           
           
           if(!txt_stat.getText().equals("add")){
                lbl_status_non_bpjs.setText("Tarif Non BPJS Tidak Bisa Di Edit");
           }
           else{
           
           lbl_status_non_bpjs.setText("Tarif Non BPJS Bisa Di Edit");
          
           
           }
           
           if(!txt_stat_bpjs.getText().equals("add")){
               if(!txt_stat_bpjs.getText().isEmpty()){
                   lbl_status_bpjs.setText("Tarif BPJS Tidak Bisa Di Edit");
                
               }
               else{
               lbl_status_bpjs.setText("Tarif BPJS Belum diset");
               }
               
           }
           else{
                lbl_status_bpjs.setText("Tarif BPJS Bisa Di Edit");
          
           
           }
           
           //JOptionPane.showMessageDialog(null, tb_tarif.getModel().getValueAt(row,18).toString());
        }     
    }
    
    private void setNolBPJS(){
      txt_tarif1.setText("0");
      txt_rs1.setText("0");
      txt_dr1.setText("0");
      this.txt_sarana1.setText("0");
    }
    
    private void tb_tarifMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_tarifMouseReleased
        // TODO add your handling code here:
         if (evt.getClickCount() == 2) {
            
           
        }
    }//GEN-LAST:event_tb_tarifMouseReleased

    private void bt_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_addActionPerformed
        // TODO add your handling code here:
        Utilitas.HapusText(panel_inputan);
        Utilitas.HapusText(jPanel5);
        Utilitas.HapusText(jPanel10);
        
        lbl_kode_status.setText("");
        lbl_kode_poli.setText("");
        lbl_cepe.setText("");
        cmb_kelas.setSelectedIndex(0);
        ck_bpjs.setSelected(false);
        
    }//GEN-LAST:event_bt_addActionPerformed

    private void updatebpjs(){
    
       DefaultListModel<String> listModel = new DefaultListModel<>();
        
       int pr= Integer.valueOf(this.txt_rs1.getText())+Integer.valueOf(this.txt_dr1.getText())+Integer.valueOf(this.txt_sarana1.getText());
     
       lbl_cepe1.setText(" "+pr +" %");
    
    if((Integer.valueOf(this.txt_rs1.getText())+Integer.valueOf(this.txt_dr1.getText())+Integer.valueOf(this.txt_sarana1.getText()))>100){
            JOptionPane.showMessageDialog(null, "Jumlah Presentase Melebihi 100 % ");
            this.txt_rs1.setText("0");
            this.txt_dr1.setText("0");  
            this.txt_sarana1.setText("0");
            this.txt_rs1.requestFocus();
        }
        else{
                if(this.txt_kode_tarif.getText().isEmpty()||txt_nama_tarif.getText().isEmpty()||
                        txt_poli.getText().isEmpty()||txt_tarif1.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Maaf Data Inputan Ada yg Kosong!");
                    this.txt_kode_tarif.requestFocus();
                }
                else{
                  if(txt_tarif1.getText().equals("0")){
                      JOptionPane.showMessageDialog(null, "Maaf Tarif/Presentase Tidak Boleh 0!");
                      this.txt_tarif1.requestFocus();
                  }
                  else if((Integer.valueOf(this.txt_rs1.getText())+Integer.valueOf(this.txt_dr1.getText())+Integer.valueOf(this.txt_sarana1.getText()))<100){
                   JOptionPane.showMessageDialog(null, "Maaf Presentase kurang dari 100 !");
                    this.txt_rs1.requestFocus();
                  }
                  else{
                     if(!this.txt_status.getText().isEmpty()){
                        if(!cmb_kelas.getSelectedItem().toString().isEmpty()){
                        // 
                         try {
                             //save
                             datl= new Crud_local();
                           
                            if(this.txt_stat_bpjs.getText().equals("ok")||this.txt_stat_bpjs.getText().equals("edit")) {
                              JOptionPane.showMessageDialog(null,"Maaf BPJS Tidak Bisa di Edit Sudah di Verif");  
                              
                            }
                            else{
                                datl.updateTarifBPJS(txt_kode_tarif.getText(), txt_nama_tarif.getText(),Double.valueOf(txt_tarif.getText()),Integer.valueOf(txt_dr.getText()), Integer.valueOf(txt_rs.getText()), 
                                     Integer.valueOf(txt_sarana.getText()), Integer.valueOf(lbl_kode_poli.getText()) ,
                                     Integer.valueOf(lbl_kode_status.getText()) , "add", "add", txt_keterangan.getText(),this.cmb_kelas.getSelectedItem().toString(),Double.valueOf(txt_tarif1.getText()),Integer.valueOf(txt_dr1.getText()), Integer.valueOf(txt_rs1.getText())
                                        ,Integer.valueOf(txt_sarana1.getText()),String.valueOf(this.ck_bpjs.isSelected()),"add","add",2);
                             
                             refreshtbtarif();
                                
                            }
                            
                            
                            
                             
                         } catch (Exception ex) {
                             Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
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
    }
    
    private void updateNonbpjs(){
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
                         
                          
//                    if(this.ck_bpjs.isSelected()){
//                      
//                              this.updatebpjs();
//                         }
//                    else{
                       try {
                           
                           
                             //save
                             datl= new Crud_local();
                           
                            if(this.txt_stat.getText().equals("ok")||this.txt_stat.getText().equals("edit")) {
                              
                                JOptionPane.showMessageDialog(null, "Tidak bisa Edit BPJS atau Non BPJS Karena sudah di Verif!");
                                
                            }
                            else{
                                
                                this.setNolBPJS();
                                
                                 datl.updateTarifBPJS(txt_kode_tarif.getText(), txt_nama_tarif.getText(),Double.valueOf(txt_tarif.getText()),Integer.valueOf(txt_dr.getText()), Integer.valueOf(txt_rs.getText()), 
                                     Integer.valueOf(txt_sarana.getText()), Integer.valueOf(lbl_kode_poli.getText()) ,
                                     Integer.valueOf(lbl_kode_status.getText()) , "add", "add", txt_keterangan.getText(),this.cmb_kelas.getSelectedItem().toString(),Double.valueOf(txt_tarif1.getText()),Integer.valueOf(txt_dr1.getText()), Integer.valueOf(txt_rs1.getText())
                                        ,Integer.valueOf(txt_sarana1.getText()),String.valueOf(this.ck_bpjs.isSelected()),"add","add",1);
                             
                                
                                
//                                datl.updateTarif(txt_kode_tarif.getText(), txt_nama_tarif.getText(),Double.valueOf(txt_tarif.getText()),Integer.valueOf(txt_dr.getText()), Integer.valueOf(txt_rs.getText()), 
//                                     Integer.valueOf(txt_sarana.getText()), Integer.valueOf(lbl_kode_poli.getText()) ,
//                                     Integer.valueOf(lbl_kode_status.getText()) , "add", "add", txt_keterangan.getText(),this.cmb_kelas.getSelectedItem().toString(),Double.valueOf(txt_tarif1.getText()),Integer.valueOf(txt_dr1.getText()), Integer.valueOf(txt_rs1.getText());
//                             
                             refreshtbtarif();
                                
                                
                            }
                             
                         } catch (Exception ex) {
                             Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
                         }
//          }  //end ceklist bpjs  
                             
                         
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

    }
    
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
//                                datl.updateTarif(txt_kode_tarif.getText(), txt_nama_tarif.getText(),Double.valueOf(txt_tarif.getText()),Integer.valueOf(txt_dr.getText()), Integer.valueOf(txt_rs.getText()), 
//                                     Integer.valueOf(txt_sarana.getText()), Integer.valueOf(lbl_kode_poli.getText()) ,
//                                     Integer.valueOf(lbl_kode_status.getText()) , "add", "add", txt_keterangan.getText(),this.cmb_kelas.getSelectedItem().toString());

                                   datl.updateTarifBPJS(txt_kode_tarif.getText(), txt_nama_tarif.getText(),Double.valueOf(txt_tarif.getText()),Integer.valueOf(txt_dr.getText()), Integer.valueOf(txt_rs.getText()), 
                                     Integer.valueOf(txt_sarana.getText()), Integer.valueOf(lbl_kode_poli.getText()) ,
                                     Integer.valueOf(lbl_kode_status.getText()) , "add", "add", txt_keterangan.getText(),this.cmb_kelas.getSelectedItem().toString(),Double.valueOf(txt_tarif1.getText()),Integer.valueOf(txt_dr1.getText()), Integer.valueOf(txt_rs1.getText())
                                        ,Integer.valueOf(txt_sarana1.getText()),String.valueOf(this.ck_bpjs.isSelected()),"add","add",1);
              

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
          
          
          this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
          this.saveImagev();
          this.setCursor(Cursor.getDefaultCursor());
      } catch (IOException ex) {
          Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
      }
    }//GEN-LAST:event_bt_verifActionPerformed

    private void bt_prosesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_prosesActionPerformed
        // TODO add your handling code here:
        
        int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah Akan di Proses Untuk Verifikasi Pastikan Data Sudah Benar Data Yg Sudah di Proses Verif tidak bisa di Hapus atau Edit?","Warning ",
           JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
   
       if(dialogResult == JOptionPane.YES_OPTION){   

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
           
         this.refreshtbtarif();
        
          DefaultListModel<String> listModel = new DefaultListModel<>();
         
        for ( int i = 0; i < tb_tarif.getRowCount(); i++) {
            String sp=tb_tarif.getModel().getValueAt(i, 11).toString();
            String sv=tb_tarif.getModel().getValueAt(i, 12).toString();
            
            if(!(sv.equals("ok")||sp.equals("pending"))){
             
              if(sv.equals("add")||sp.equals("add")){    
              datl.updateTarifStatus(this.tb_tarif.getModel().getValueAt(i, 0).toString(), "edit", "edit");
              listModel.addElement("Proses Untuk Verif:"+this.tb_tarif.getModel().getValueAt(i, 0).toString()+" "+Utilitas.tglsekarangJam());
                             
              lst_save_tarif.setModel(listModel);
              }
              else
              {
              listModel.addElement("Tidak Bisa di Proses Verif:"+this.tb_tarif.getModel().getValueAt(i, 0).toString()+" "+Utilitas.tglsekarangJam());
                             
               lst_save_tarif.setModel(listModel);
              }
              
            }
            else{
               listModel.addElement("Tidak Bisa di Proses Verif:"+this.tb_tarif.getModel().getValueAt(i, 0).toString()+" "+Utilitas.tglsekarangJam());
                             
               lst_save_tarif.setModel(listModel);
            }
            
           }
        
        this.refreshtbtarif();
         this.setCursor(Cursor.getDefaultCursor());
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
         if(this.txt_stat.getText().equals("ok")||this.txt_stat.getText().equals("edit")||this.txt_stat_bpjs.getText().equals("ok")||this.txt_stat_bpjs.getText().equals("edit")) {
                                
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
                 
                 Utilitas.HapusText(panel_inputan);
        Utilitas.HapusText(jPanel5);
        
        lbl_kode_status.setText("");
        lbl_kode_poli.setText("");
        lbl_cepe.setText("");
        ck_bpjs.setSelected(false);
        cmb_kelas.setSelectedIndex(0);
    
             } catch (SQLException ex) {
                 Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
             } catch (Exception ex) {
                 Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
             }
                                
          }                      
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

    private void txt_tarif1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_tarif1KeyTyped
        // TODO add your handling code here:
        char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txt_tarif1KeyTyped

    private void txt_tarif1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_tarif1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_rs1.requestFocus();
        }
    }//GEN-LAST:event_txt_tarif1KeyPressed

    private void txt_rs1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rs1KeyTyped
        // TODO add your handling code here:
         char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txt_rs1KeyTyped

    private void txt_rs1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rs1KeyPressed
        // TODO add your handling code here:
          if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_dr1.requestFocus();
        }
    }//GEN-LAST:event_txt_rs1KeyPressed

    private void txt_dr1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dr1KeyTyped
        // TODO add your handling code here:
         char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txt_dr1KeyTyped

    private void txt_dr1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dr1KeyPressed
        // TODO add your handling code here:
         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_sarana1.requestFocus();
        }
    }//GEN-LAST:event_txt_dr1KeyPressed

    private void txt_sarana1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_sarana1KeyTyped
        // TODO add your handling code here:
          char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txt_sarana1KeyTyped

    private void txt_sarana1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_sarana1KeyPressed
        // TODO add your handling code here:
          if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.bt_edit_bpjs.requestFocus();
        }
    }//GEN-LAST:event_txt_sarana1KeyPressed

    private void ck_bpjsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ck_bpjsActionPerformed
        // TODO add your handling code here:
        
       if(this.txt_stat_bpjs.getText().equals("ok")||this.txt_stat_bpjs.getText().equals("edit")) {
                                
           JOptionPane.showMessageDialog(null, "BPJS Tidak bisa Edit Karena sudah di Verif!");
        }
      else{ 
        if(!this.ck_bpjs.isSelected()){
         if(!txt_kode_tarif.getText().isEmpty()){   
           int dialogResult = JOptionPane.showConfirmDialog(null, "Nilai BPJS akan Di Nolkan?","Warning ",
           JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(dialogResult == JOptionPane.YES_OPTION){  
              setNolBPJS();
            }
         } 
        }
      }    
        
    }//GEN-LAST:event_ck_bpjsActionPerformed

    private void ck_pilih_semua1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ck_pilih_semua1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ck_pilih_semua1ItemStateChanged

    private void ck_pilih_semua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ck_pilih_semua1ActionPerformed
        // TODO add your handling code here:
         if(ck_pilih_semua1.isSelected()) {//checkbox has been selected
           
            
           for(int i=0;i<this.tb_log_publishBPJS.getModel().getRowCount();i++){ 
            this.tb_log_publishBPJS.getModel().setValueAt(true,i,10);
           }
            
            
        } else {
           for(int i=0;i<this.tb_log_publishBPJS.getModel().getRowCount();i++){ 
            this.tb_log_publishBPJS.getModel().setValueAt(false,i,10);
           }

        }
        
    }//GEN-LAST:event_ck_pilih_semua1ActionPerformed

    private void ck_pilih_semua1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ck_pilih_semua1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ck_pilih_semua1KeyPressed

    private void tb_log_publishBPJSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_log_publishBPJSMouseClicked
        // TODO add your handling code here:
        int row = this.tb_log_publishBPJS.getSelectedRow();

        if (row == -1) {
            // No row selected
        } else {  
          lbl_nm_tindakan1.setText("Nama Tindakan : "+tb_log_publishBPJS.getModel().getValueAt(row, 1).toString());
          lbl_tarif1.setText("Tarif BPJS"+ Utilitas.formatuang(Double.valueOf(tb_log_publishBPJS.getModel().getValueAt(row, 2).toString())));
          lbl_jasa1.setText("RS BPJS:"+tb_log_publishBPJS.getModel().getValueAt(row, 3).toString()
                  +"      Dr BPJS:"+tb_log_publishBPJS.getModel().getValueAt(row, 4).toString()+"      Sarana :"+tb_log_publishBPJS.getModel().getValueAt(row, 5).toString());
          
        }
    }//GEN-LAST:event_tb_log_publishBPJSMouseClicked

    private void txt_stat_bpjsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_stat_bpjsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_stat_bpjsKeyPressed

    private void bt_edit_bpjsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_edit_bpjsActionPerformed
       if(this.ck_bpjs.isSelected()) {
           this.updatebpjs();
         
        }
       else{         // TODO add your handling code here:
         JOptionPane.showMessageDialog(null, "BPJS belum di Ceklist!");
       }
    }//GEN-LAST:event_bt_edit_bpjsActionPerformed

    private void bt_hapus_bpjsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_hapus_bpjsActionPerformed
int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah Akan di Hapus?","Warning ",
           JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
   
       if(dialogResult == JOptionPane.YES_OPTION){             // TODO add your handling code here:
        
        if(this.txt_stat_bpjs.getText().equals("ok")||this.txt_stat_bpjs.getText().equals("edit")) {
                              JOptionPane.showMessageDialog(null,"Maaf BPJS Tidak Bisa di Edit Sudah di Verif");  
                              
                            }
                            else{
            
                                this.setNolBPJS();
                                
                                datl.updateTarifBPJS(txt_kode_tarif.getText(), txt_nama_tarif.getText(),Double.valueOf(txt_tarif.getText()),Integer.valueOf(txt_dr.getText()), Integer.valueOf(txt_rs.getText()), 
                                     Integer.valueOf(txt_sarana.getText()), Integer.valueOf(lbl_kode_poli.getText()) ,
                                     Integer.valueOf(lbl_kode_status.getText()) , "add", "add", txt_keterangan.getText(),this.cmb_kelas.getSelectedItem().toString(),Double.valueOf(txt_tarif1.getText()),Integer.valueOf(txt_dr1.getText()), Integer.valueOf(txt_rs1.getText())
                                        ,Integer.valueOf(txt_sarana1.getText()),"","add","add",2);
                             
                             refreshtbtarif();
                                this.ck_bpjs.setSelected(false);
                            }
        
       }
    }//GEN-LAST:event_bt_hapus_bpjsActionPerformed

    private void tb_tarifMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_tarifMouseClicked
        // TODO add your handling code here:
        settarif();
    }//GEN-LAST:event_tb_tarifMouseClicked

    private void bt_proses1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_proses1ActionPerformed
        // TODO add your handling code here:
                int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah Akan di Proses Untuk Verifikasi BPJS Pastikan Data Sudah Benar Data Yg Sudah di Proses Verif tidak bisa di Hapus atau Edit?","Warning ",
           JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
   
       if(dialogResult == JOptionPane.YES_OPTION){   
        
         this.refreshtbtarif();
        
          DefaultListModel<String> listModel = new DefaultListModel<>();
         
           this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
          
        for ( int i = 0; i < tb_tarif.getRowCount(); i++) {
            String sp=tb_tarif.getModel().getValueAt(i, 20).toString();
            String sv=tb_tarif.getModel().getValueAt(i, 21).toString();
            
            if(!(sv.equals("ok")||sp.equals("pending"))){
                 // JOptionPane.showMessageDialog(null, "tessss: "+sv+" "+sp+" "+tb_tarif.getModel().getValueAt(i, 19).toString().isEmpty());
             if(!tb_tarif.getModel().getValueAt(i, 19).toString().isEmpty()){  
                
             if(sv.equals("add")||sp.equals("add")){  
                
              datl.updateTarifStatusBPJS(this.tb_tarif.getModel().getValueAt(i, 0).toString(), "edit", "edit");
              listModel.addElement("Proses Verif BPJS:"+this.tb_tarif.getModel().getValueAt(i, 0).toString()+" "+Utilitas.tglsekarangJam());
                             
              lst_save_tarif.setModel(listModel);
             }
             else
              {
                 listModel.addElement("Tidak Bisa di Proses Verif:"+this.tb_tarif.getModel().getValueAt(i, 0).toString()+" "+Utilitas.tglsekarangJam());
                             
               lst_save_tarif.setModel(listModel);
              }
              
             }
              
            }
            else{
               listModel.addElement("Tidak Bisa di Proses Verif:"+this.tb_tarif.getModel().getValueAt(i, 0).toString()+" "+Utilitas.tglsekarangJam());
                             
               lst_save_tarif.setModel(listModel);
            }
            
           }
        
        this.refreshtbtarif();
        
        
          this.setCursor( Cursor.getDefaultCursor());
       }  

    }//GEN-LAST:event_bt_proses1ActionPerformed

    private void bt_pengesah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_pengesah1ActionPerformed
        // TODO add your handling code here:
         try {
                // TODO add your handling code here:
              this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));      
                 saveImageBPJS();
              this.setCursor(Cursor.getDefaultCursor());
              
                 } catch (IOException ex) {
                    Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
                 }
    }//GEN-LAST:event_bt_pengesah1ActionPerformed

    private void bt_verif1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_verif1ActionPerformed
        // TODO add your handling code here:
       try {
          this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
          this.saveImagevBPJS();
          this.setCursor(Cursor.getDefaultCursor());
          
      } catch (IOException ex) {
          Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
      }
    }//GEN-LAST:event_bt_verif1ActionPerformed

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
    
    private void cekpersen1(){
      
      
      int pr= Integer.valueOf(this.txt_rs1.getText())+Integer.valueOf(this.txt_dr1.getText())+Integer.valueOf(this.txt_sarana1.getText());
     
      lbl_cepe1.setText(" "+pr +" %");
      

      
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
  
     if(Integer.valueOf(this.txt_rs1.getText())>100){
     
         this.txt_rs1.setText("0");
         
         
     
     }
     else if(Integer.valueOf(this.txt_dr1.getText())>100){
     this.txt_dr1.setText("0");
     
     }
     else if(Integer.valueOf(this.txt_sarana1.getText())>100){
     this.txt_sarana1.setText("0");
     
     }
    
     else if((Integer.valueOf(this.txt_rs1.getText())+Integer.valueOf(this.txt_dr1.getText()))>100){
     
         
     
     
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
    
    private void hitungpresentasDR1(){
        
      if(txt_dr1.getText().isEmpty()){
         txt_dr1.setText("0");
      }
      else{
        
        Double totdr=(Double.parseDouble(txt_tarif1.getText().toString()) * Double.parseDouble(this.txt_dr1.getText().toString()))/100;
      
       lbl_dokter1.setText(Utilitas.formatuang(totdr));
          
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
    
    
    private void hitungpresentaseRS1(){
           
      if(txt_rs1.getText().isEmpty()){
      txt_rs1.setText("0");
      }
      else{
         
            Double totrs=(Double.parseDouble(txt_tarif1.getText().toString()) * Double.parseDouble(this.txt_rs1.getText().toString()))/100;
      
            lbl_rs1.setText(Utilitas.formatuang(totrs));
          
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
     
    
    
  private void hitungpresentaseSR1(){
        
   if(txt_sarana1.getText().isEmpty()){
      txt_sarana1.setText("0");
    }
    else{  
          // cekpersen();
           
         
             this.txt_sarana1.setEditable(true);
            Double totsr=(Double.parseDouble(txt_tarif1.getText().toString()) * Double.parseDouble(this.txt_sarana1.getText().toString()))/100;
      
            lbl_sarana1.setText(Utilitas.formatuang(totsr));
         
            
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
               
               
//               this.txt_username.setText("");
               this.txt_pwd.setText("");
//               this.txt_usernamev.setText("");
               this.txt_pwdv.setText("");
               
               pointsv.clear();
               biv.flush();
               
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
              Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
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
  
    public void saveImagevBPJS() throws IOException {
     
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
         
            if(datl.cekvalpilihBPJS.size()>0){  
              
              for ( i = 0; i < tb_log_publishBPJS.getRowCount(); i++) {
		    Boolean chked = Boolean.valueOf(tb_log_publishBPJS.getValueAt(i, 10)
							.toString());
		   
		    if (chked) {
                        
		       datl.Save_log_tarifBPJS("",txt_usernamev.getText(), this.tb_log_publishBPJS.getModel().getValueAt(i, 0).toString(), "pending", "ok", pathFile);
                       datl.updateTarifStatusBPJS(this.tb_log_publishBPJS.getModel().getValueAt(i, 0).toString(), "pending", "ok");
		       listModel.addElement("Tersimpan BPJS:"+this.tb_log_publishBPJS.getModel().getValueAt(i, 0).toString()+" "+Utilitas.tglsekarangJam());
                    }
		}
              
               lst_save_publish.setModel(listModel);
              
                posv=0;
               
//              this.txt_username.setText("");
               this.txt_pwd.setText("");
//               this.txt_usernamev.setText("");
               this.txt_pwdv.setText("");
               
               pointsv.clear();
               biv.flush();
               
               lbl_ttd_cek.setText("0");
               this.jPanel2.repaint();
               
               refreshTblogBPJS();
                
             }
             else{
                JOptionPane.showMessageDialog(null, "Tarif Belum ada yang di ceklist");
             }
          
          
          } catch (Exception ex) {
              //JOptionPane.showMessageDialog(null, "Gagal Simpan");
               listModel.addElement("Gagal Simpan:"+this.tb_log_publishBPJS.getModel().getValueAt(i, 0).toString()+" "+Utilitas.tglsekarangJam());
              Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void saveImageBPJS() throws IOException {
         
      
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
         
            if(datl.cekvalpilihBPJS.size()>0){  
              
              for ( i = 0; i < tb_log_publishBPJS.getRowCount(); i++) {
		    Boolean chked = Boolean.valueOf(tb_log_publishBPJS.getValueAt(i, 10)
							.toString());
		   
		    if (chked) {
		       datl.updateTarifStatusLogPengesahBPJS(this.tb_log_publishBPJS.getModel().getValueAt(i, 0).toString(), "ok",
                               txt_username.getText(),pathFile);
                       datl.updateTarifStatusBPJS(this.tb_log_publishBPJS.getModel().getValueAt(i, 0).toString(), "ok", "ok");
		       listModel.addElement("Tersimpan Pengesah:"+this.tb_log_publishBPJS.getModel().getValueAt(i, 0).toString()+" "+Utilitas.tglsekarangJam());
                    }
		}
              
               lst_save_publish.setModel(listModel);
              
                 pos=0;
                 
               this.txt_username.setText("");
//               this.txt_pwd.setText("");
               this.txt_usernamev.setText("");
//               this.txt_pwdv.setText("");
               points.clear();
               bi.flush();
               lbl_ttd_cek1.setText("0");
               this.jPanel3.repaint();
               
               refreshTblogPengesahBPJS();
                
             }
             else{
                JOptionPane.showMessageDialog(null, "Tarif Belum ada yang di ceklist");
             }
          
          
          } catch (Exception ex) {
              //JOptionPane.showMessageDialog(null, "Gagal Simpan");
               listModel.addElement("Gagal Simpan:"+this.tb_log_publish.getModel().getValueAt(i, 0).toString()+" "+Utilitas.tglsekarangJam());
              Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
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
                 
//               this.txt_username.setText("");
               this.txt_pwd.setText("");
//             this.txt_usernamev.setText("");
               this.txt_pwdv.setText("");
               points.clear();
               bi.flush();
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
              Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void setukurantbLogBPJS(){
        
     this.tb_log_publishBPJS.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 11));
     tb_log_publishBPJS.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
    TableColumnModel tr = tb_log_publishBPJS.getColumnModel(); 
    
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
          Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
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
          Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
      }
            
          
            
    }
    
    private void refreshTblogPengesahBPJS(){
            
          try {
              datl=new Crud_local();
               try {
              datl.readRec_cariTariflogPengesahBPJS();
              
               
               
          } catch (SQLException ex) {
              Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null, "Gagal Login!");
          }
          } catch (Exception ex) {
              Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null, "Gagal Login!");
          }
         
              tb_log_publishBPJS.setModel(datl.modeltariflogBPJS);
               setukurantbLogBPJS();
    }
    
     private void refreshTblogPengesah(){
            
          try {
              datl=new Crud_local();
               try {
              datl.readRec_cariTariflogPengesah();
              
               
               
          } catch (SQLException ex) {
              Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null, "Gagal Login!");
          }
          } catch (Exception ex) {
              Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null, "Gagal Login!");
          }
         
              tb_log_publish.setModel(datl.modeltariflog);
               setukurantbLog();
    }
    
    
      private void refreshTblogBPJS(){
            
          try {
              datl=new Crud_local();
               try {
              datl.readRec_cariTariflogBPJS();
              
               
               
          } catch (SQLException ex) {
              Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null, "Gagal Login!");
          }
          } catch (Exception ex) {
              Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null, "Gagal Login!");
          }
         
              tb_log_publishBPJS.setModel(datl.modeltariflogBPJS);
               setukurantbLogBPJS();
    }
     
    private void refreshTblog(){
            
          try {
              datl=new Crud_local();
               try {
              datl.readRec_cariTariflog();
              
               
               
          } catch (SQLException ex) {
              Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null, "Gagal Login!");
          }
          } catch (Exception ex) {
              Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null, "Gagal Login!");
          }
         
              tb_log_publish.setModel(datl.modeltariflog);
               setukurantbLog();
    }
    
    private void setloginv(){
    
      if (Crud_local.usm.equals(txt_usernamev.getText()) && Crud_local.psm.equals(txt_pwdv.getText())) {
                
         
          try {
              datl=new Crud_local();
              datl.readRec_cariTariflog();
              tb_log_publish.setModel(datl.modeltariflog);
          } catch (Exception ex) {
              Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
          }
         
          try {  
              datl=new Crud_local();
              datl.readRec_cariTariflogBPJS();
              tb_log_publishBPJS.setModel(datl.modeltariflogBPJS);
          }
              catch (Exception ex) {
              Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
          }
             
             
              if(this.ck_pengesah.isSelected()==true){
                  lbl_nama_pengesah.setText("<"+Crud_local.usm+">");
                  
                  if(tb_log_publish.getModel().getRowCount()!=0)  {
                      this.bt_pengesah.setEnabled(true);
                  }
                  if(tb_log_publishBPJS.getModel().getRowCount()!=0)  {
                      this.bt_pengesah1.setEnabled(true);
                  }
              }
              else if(this.ck_verif.isSelected()==true){
                  lbl_verif.setText("<"+Crud_local.usm+">");
                 
                  if(tb_log_publish.getModel().getRowCount()!=0)  {
                      
                      this.bt_verif.setEnabled(true);
                  }
                  if(tb_log_publishBPJS.getModel().getRowCount()!=0)  { 
                      this.bt_verif1.setEnabled(true);
                  }
              }
              
              
              
              setukurantbLog();
              setukurantbLogBPJS();
              
              this.dlg_loginv.setVisible(false);
              
              this.ck_pilih_semua.setEnabled(true);
              this.ck_pilih_semua1.setEnabled(true);
          
                
            }else{
              
                JOptionPane.showMessageDialog(null, "Login salah!");
            
            }
            
    }
    
    
    private void setloginp(){
    
      if (Crud_local.usm.equals(txt_username.getText()) && Crud_local.psm.equals(txt_pwd.getText())) {
                

          try {              
              datl=new Crud_local();
              datl.readRec_cariTariflogPengesah();
              tb_log_publish.setModel(datl.modeltariflog);
         } catch (Exception ex) {
              Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
          }
          
          try {              
          
              datl=new Crud_local();
              datl.readRec_cariTariflogPengesahBPJS();
              tb_log_publishBPJS.setModel(datl.modeltariflogBPJS);
              } catch (Exception ex) {
              Logger.getLogger(frm_tarif.class.getName()).log(Level.SEVERE, null, ex);
          }
              if(this.ck_pengesah.isSelected()==true){
                  lbl_nama_pengesah.setText("<"+Crud_local.usm+">");
                  
                  if(tb_log_publish.getModel().getRowCount()!=0)  { 
                      this.bt_pengesah.setEnabled(true);
                  }
                  
                  if(tb_log_publishBPJS.getModel().getRowCount()!=0)  { 
                      this.bt_pengesah1.setEnabled(true);
                  }
                  
              }
              else if(this.ck_verif.isSelected()==true){
                  lbl_verif.setText("<"+Crud_local.usm+">");
                   
                  
                  if(tb_log_publish.getModel().getRowCount()!=0)  {
                      this.bt_verif.setEnabled(true);
                  }
                  
                  if(tb_log_publishBPJS.getModel().getRowCount()!=0)  {
                      this.bt_verif1.setEnabled(true);
                  }
                  
                  
                  
                  
                  
              }     
              
              
              
              
              
              setukurantbLog();
              setukurantbLogBPJS();
              this.dlg_login.setVisible(false);
              
              this.ck_pilih_semua.setEnabled(true);
              this.ck_pilih_semua1.setEnabled(true);
          
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
    private javax.swing.JButton bt_add;
    private javax.swing.JButton bt_add1;
    private javax.swing.JButton bt_cari_poli;
    private javax.swing.JButton bt_cari_status;
    private javax.swing.JButton bt_cari_tarif;
    private javax.swing.JButton bt_cari_tarif1;
    private javax.swing.JButton bt_cetak;
    private javax.swing.JButton bt_delete;
    private javax.swing.JButton bt_edit_bpjs;
    private javax.swing.JButton bt_hapus_bpjs;
    private javax.swing.JButton bt_hapus_ttd;
    private javax.swing.JButton bt_hapus_ttd1;
    private javax.swing.JButton bt_pengesah;
    private javax.swing.JButton bt_pengesah1;
    private javax.swing.JButton bt_proses;
    private javax.swing.JButton bt_proses1;
    private javax.swing.JButton bt_save;
    private javax.swing.JButton bt_verif;
    private javax.swing.JButton bt_verif1;
    private javax.swing.JCheckBox ck_bpjs;
    private javax.swing.JCheckBox ck_pengesah;
    private javax.swing.JCheckBox ck_pilih_semua;
    private javax.swing.JCheckBox ck_pilih_semua1;
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
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JLabel lbl_cepe;
    private javax.swing.JLabel lbl_cepe1;
    private javax.swing.JLabel lbl_dokter;
    private javax.swing.JLabel lbl_dokter1;
    private javax.swing.JLabel lbl_jasa;
    private javax.swing.JLabel lbl_jasa1;
    private javax.swing.JLabel lbl_kode_poli;
    private javax.swing.JLabel lbl_kode_status;
    private javax.swing.JLabel lbl_login;
    private javax.swing.JLabel lbl_loginv;
    private javax.swing.JLabel lbl_nama_pengesah;
    private javax.swing.JLabel lbl_nm_tindakan;
    private javax.swing.JLabel lbl_nm_tindakan1;
    private javax.swing.JLabel lbl_rs;
    private javax.swing.JLabel lbl_rs1;
    private javax.swing.JLabel lbl_sarana;
    private javax.swing.JLabel lbl_sarana1;
    private javax.swing.JLabel lbl_status_bpjs;
    private javax.swing.JLabel lbl_status_non_bpjs;
    private javax.swing.JLabel lbl_tarif;
    private javax.swing.JLabel lbl_tarif1;
    private javax.swing.JLabel lbl_ttd_cek;
    private javax.swing.JLabel lbl_ttd_cek1;
    private javax.swing.JLabel lbl_ttd_cek2;
    private javax.swing.JLabel lbl_ttd_cek3;
    private javax.swing.JLabel lbl_verif;
    private javax.swing.JList<String> lst_save_publish;
    private javax.swing.JList<String> lst_save_tarif;
    private javax.swing.JPanel panel_inputan;
    private javax.swing.JTable tb_log_publish;
    private javax.swing.JTable tb_log_publishBPJS;
    private javax.swing.JTable tb_poli;
    private javax.swing.JTable tb_status;
    private javax.swing.JTable tb_tarif;
    private javax.swing.JTextField txt_cari_poli;
    private javax.swing.JTextField txt_cari_status;
    private javax.swing.JTextField txt_cari_tarif;
    private javax.swing.JTextField txt_cari_tarif_pengesah;
    private javax.swing.JTextField txt_dr;
    private javax.swing.JTextField txt_dr1;
    private javax.swing.JTextArea txt_keterangan;
    private javax.swing.JTextField txt_kode_tarif;
    private javax.swing.JTextField txt_nama_tarif;
    private javax.swing.JTextField txt_poli;
    private javax.swing.JPasswordField txt_pwd;
    private javax.swing.JPasswordField txt_pwdv;
    private javax.swing.JTextField txt_rs;
    private javax.swing.JTextField txt_rs1;
    private javax.swing.JTextField txt_sarana;
    private javax.swing.JTextField txt_sarana1;
    private javax.swing.JTextField txt_stat;
    private javax.swing.JTextField txt_stat_bpjs;
    private javax.swing.JTextField txt_status;
    private javax.swing.JTextField txt_tarif;
    private javax.swing.JTextField txt_tarif1;
    private javax.swing.JTextField txt_username;
    private javax.swing.JTextField txt_usernamev;
    // End of variables declaration//GEN-END:variables
}
