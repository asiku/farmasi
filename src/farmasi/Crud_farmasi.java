/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farmasi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import tools.Utilitas;

/**
 *
 * @author jengcool
 */
public class Crud_farmasi extends DBkoneksi {

    public Crud_farmasi() throws Exception {
        ConDb();
    }

    
    public static String kddokterfisio="";
    public static String nmdokterfisio="";
    
    // public static int rcount;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    String[] reg_title = new String[]{"No.", "No. RM", "Nama Pasien", "Tanggal"};
   
    String[] reg_titleralan = new String[]{"No.", "No. RM", "Nama Pasien", "Tanggal","Kode Dokter","Nama Dokter","Kode Poli","Poli","Kode PJ","Status Bayar","No Rawat"};
    
    String[] reg_titleralanfarmasi = new String[]{"No.", "No. RM", "Nama Pasien", "Tanggal","Kode Dokter","Nama Dokter","Kode Poli","Poli","Kode PJ","Status Bayar","No Rawat","No. SEP"};
  
    String[] reg_titleralankasir = new String[]{"No.", "No. RM", "Nama Pasien", "Tanggal","Kode Dokter","Nama Dokter","Kode Poli","Poli","Kode PJ","Status Bayar","No Rawat","Status Lanjut","No. SEP"};

    String[] brg_title = new String[]{"Kode Barang", "Nama", "Harga Jual", "Harga Jual Karyawan","Harga Beli", "Kategori"}; 

    String[] petugas_title = new String[]{"Nip", "Nama Petugas"};
    
    String[] pegawai_title = new String[]{"Nip", "Nama Pegawai","Jabatan"};

    String[] kamarinap_title = new String[]{"No. RM", "Nama Pasien", "No. Rawat","Tgl Masuk","Kamar Inap","Kelas","Kode Status","Status"};
    
    String[] kamarinap_titlefarmasi = new String[]{"No. RM", "Nama Pasien", "No. Rawat","Tgl Masuk","Kamar Inap","Kelas","Kode Status","Status","No SEP"};
    
    String[] kamarinap_title_biaya = new String[]{"No. RM", "Nama Pasien", "No. Rawat","Tgl Masuk","Kamar Inap","Kelas","Kode Status","Status","Tarif"};
    
    String[] kamarinap_title_biayakasir = new String[]{"Kamar","Tarif","Tgl Masuk","Jam Masuk","Tgl Keluar","Jam Keluar","Lama","Total","No. Rawat","Kode"};
    

    
            
    public DefaultTableModel modelkamarinapbiayakasir = new DefaultTableModel(kamarinap_title_biayakasir, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };
    
    public DefaultTableModel modelkamarinapbiaya = new DefaultTableModel(kamarinap_title_biaya, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };

    public DefaultTableModel modelkamarinapfarmasi = new DefaultTableModel(kamarinap_titlefarmasi, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };
    
    public DefaultTableModel modelkamarinap = new DefaultTableModel(kamarinap_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };

     public DefaultTableModel modelpegawai = new DefaultTableModel(pegawai_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
     };
    
    public DefaultTableModel modeltugas = new DefaultTableModel(petugas_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };

    public DefaultTableModel modelbrg = new DefaultTableModel(brg_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };

    
    public DefaultTableModel modelregralankasir = new DefaultTableModel(reg_titleralankasir, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };
    
    public DefaultTableModel modelregralanfarmasi = new DefaultTableModel(reg_titleralanfarmasi, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };
    
    public DefaultTableModel modelregralan = new DefaultTableModel(reg_titleralan, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };
    
    public DefaultTableModel modelreg = new DefaultTableModel(reg_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };

    
    
    
     
    
    
    
    public double readRec_JasaPelayanan(String kodekamar){
        
       double biaya =0;
        
       try {
            preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_jasa_pelayanan.TB_NAME + " WHERE "
                    + helper_jasa_pelayanan.KEY_KODE_KAMAR + " =?");
            
            preparedStatement.setString(1,kodekamar);
        
            ResultSet resultSet = preparedStatement.executeQuery();

           
            while (resultSet.next()) {
                
                 biaya = resultSet.getDouble(helper_jasa_pelayanan.KEY_BESAR_BIAYA);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Crud_farmasi.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
       return biaya;
    }
    
    public void readRec_BiayaRanapKasir(String noraw){
        
        String tglkeluar="";
        String jamkeluar="";
       
        
        int lama=0;
        double tot=0.0;
        
      try {
            preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_kamar_inap.TB_NAMEVBIAYA + " WHERE "
                    + helper_kamar_inap.KEY_NO_RAWAT + " =?");
            
            preparedStatement.setString(1,  noraw );
            
           

            ResultSet resultSet = preparedStatement.executeQuery();

//        int i = 0;
           // System.out.println("No RM" + resultSet.getFetchSize());

           
           
            while (resultSet.next()) {
                
                String norawat = resultSet.getString(helper_kamar_inap.KEY_NO_RAWAT);
                String kode = resultSet.getString(helper_kamar_inap.KEY_KODE_KAMAR);
                double tarif=resultSet.getDouble(helper_kamar_inap.KEY_TRF_KAMAR);
                String nmbangsal= resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL);
                 String tglmasuk= resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK);
                String jammasuk= resultSet.getString(helper_kamar_inap.KEY_JAM_MASUK);
                 
                
                
               if(resultSet.getString(helper_kamar_inap.KEY_TGL_KELUAR)!=null){
                   
                   tglkeluar= resultSet.getDate(helper_kamar_inap.KEY_TGL_KELUAR).toString();
                   
                   lama=resultSet.getInt(helper_kamar_inap.KEY_LAMA);
                   
                   jamkeluar= resultSet.getString(helper_kamar_inap.KEY_JAM_KELUAR);
                   
                   if(lama>=6){
                       tot=tarif*(lama/24);
                       
                   }
                   
                }
                else{
                  
                   tglkeluar="-";
                   lama=0;
                   jamkeluar="-";
                }
             
                  lama=resultSet.getInt(helper_kamar_inap.KEY_LAMA)/24;
            
//                    int lama=resultSet.getInt(helper_kamar_inap.KEY_LAMA);
                
               modelkamarinapbiayakasir.addRow(new Object[]{nmbangsal,tarif,tglmasuk,jammasuk,tglkeluar,jamkeluar,lama,tot,norawat,kode});
               tot=0;
               lama=0;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Crud_farmasi.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    
    }
    
    
     public void readRec_kamarinapFisioFarmasi(String[] bangsal,String txtcari,String tgl,String tglserver) {

        try {
            
            preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_kamar_inap.TB_NAMEVF + " WHERE "
                    + helper_kamar_inap.KEY_NO_RM + " like ? OR "
                    + helper_kamar_inap.KEY_NM_PASIEN + " like ?");
            
            preparedStatement.setString(1, "%"+ txtcari +"%");
            preparedStatement.setString(2, "%"+ txtcari +"%");
           

            ResultSet resultSet = preparedStatement.executeQuery();

//        int i = 0;
           // System.out.println("No RM" + resultSet.getFetchSize());

            while (resultSet.next()) {

//            i++;
//            String no = String.valueOf(i);
//             if((resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[0])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[1])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[2])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[3])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[4])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[5])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[6])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[7])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[8])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[9])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[10])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[11])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[12])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[13])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[14])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[15])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[16])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[17])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[18])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[19])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[20])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[21])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[22])
//                     
//                     ))
//                             
//                 
//              {
                  // && resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK).substring(5, resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK).length() - 3).equals(tgl)
                  
                 try {
                     
               if(Utilitas.Hitungtgl(tglserver,Utilitas.Jam(),resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK), Utilitas.Jam())<=744){  
                String norm = resultSet.getString(helper_kamar_inap.KEY_NO_RM);
                String nmp = resultSet.getString(helper_kamar_inap.KEY_NM_PASIEN);
                String norawat = resultSet.getString(helper_kamar_inap.KEY_NO_RAWAT);
                String tglmasuk= resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK);
                String nmbangsal= resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL);
                String kelas= resultSet.getString(helper_kamar_inap.KEY_KELAS);
                String kdpj= resultSet.getString(helper_kamar_inap.KEY_KODE_STATUS_BAYAR);
                String pj= resultSet.getString(helper_kamar_inap.KEY_STATUS_BAYAR);
                String nosep= resultSet.getString(helper_kamar_inap.KEY_NO_SEP);
                modelkamarinapfarmasi.addRow(new Object[]{norm, nmp, norawat,tglmasuk,nmbangsal,kelas,kdpj,pj,nosep});
               }
                 } catch (ParseException ex) {
                     Logger.getLogger(Crud_farmasi.class.getName()).log(Level.SEVERE, null, ex);
                 }
                
               
//              }
             
            }

        } catch (SQLException ex) {
            Logger.getLogger(Crud_farmasi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void readRec_kamarinapFisio(String[] bangsal,String txtcari,String tgl,String tglserver) {

        try {
            preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_kamar_inap.TB_NAMEV + " WHERE "
                    + helper_kamar_inap.KEY_NO_RM + " like ? OR "
                    + helper_kamar_inap.KEY_NM_PASIEN + " like ?");
            
            preparedStatement.setString(1, "%"+ txtcari +"%");
            preparedStatement.setString(2, "%"+ txtcari +"%");
           

            ResultSet resultSet = preparedStatement.executeQuery();

//        int i = 0;
           // System.out.println("No RM" + resultSet.getFetchSize());

            while (resultSet.next()) {

//            i++;
//            String no = String.valueOf(i);
//             if((resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[0])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[1])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[2])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[3])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[4])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[5])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[6])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[7])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[8])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[9])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[10])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[11])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[12])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[13])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[14])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[15])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[16])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[17])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[18])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[19])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[20])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[21])||
//                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[22])
//                     
//                     ))
//                             
//                 
//              {
                  // && resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK).substring(5, resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK).length() - 3).equals(tgl)
                  
                 try {
                     
               if(Utilitas.Hitungtgl(tglserver,Utilitas.Jam(),resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK), Utilitas.Jam())<=744){  
                String norm = resultSet.getString(helper_kamar_inap.KEY_NO_RM);
                String nmp = resultSet.getString(helper_kamar_inap.KEY_NM_PASIEN);
                String norawat = resultSet.getString(helper_kamar_inap.KEY_NO_RAWAT);
                String tglmasuk= resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK);
                String nmbangsal= resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL);
                String kelas= resultSet.getString(helper_kamar_inap.KEY_KELAS);
                String kdpj= resultSet.getString(helper_kamar_inap.KEY_KODE_STATUS_BAYAR);
                String pj= resultSet.getString(helper_kamar_inap.KEY_STATUS_BAYAR);
                modelkamarinap.addRow(new Object[]{norm, nmp, norawat,tglmasuk,nmbangsal,kelas,kdpj,pj});
               }
                 } catch (ParseException ex) {
                     Logger.getLogger(Crud_farmasi.class.getName()).log(Level.SEVERE, null, ex);
                 }
                
               
//              }
             
            }

        } catch (SQLException ex) {
            Logger.getLogger(Crud_farmasi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void readRec_kamarinapDewasa(String[] bangsal,String txtcari,String tgl,String tglserver) {

        try {
            preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_kamar_inap.TB_NAMEV + " WHERE "
                    + helper_kamar_inap.KEY_NO_RM + " like ? OR "
                    + helper_kamar_inap.KEY_NM_PASIEN + " like ?");
            
            preparedStatement.setString(1, "%"+ txtcari +"%");
            preparedStatement.setString(2, "%"+ txtcari +"%");
           

            ResultSet resultSet = preparedStatement.executeQuery();

//        int i = 0;
           // System.out.println("No RM" + resultSet.getFetchSize());

            while (resultSet.next()) {

//            i++;
//            String no = String.valueOf(i);
             if((resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[0])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[1])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[2])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[3])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[4])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[5])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[6])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[7])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[8])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[9])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[10])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[11])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[12])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[13])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[14])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[15])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[16])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[17])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[18])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[19])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[20])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[21])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[22])
                     
                     ))
                             
                 
              {
                  // && resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK).substring(5, resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK).length() - 3).equals(tgl)
                  
                 try {
                     
               if(Utilitas.Hitungtgl(tglserver,Utilitas.Jam(),resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK), Utilitas.Jam())<=744){  
                String norm = resultSet.getString(helper_kamar_inap.KEY_NO_RM);
                String nmp = resultSet.getString(helper_kamar_inap.KEY_NM_PASIEN);
                String norawat = resultSet.getString(helper_kamar_inap.KEY_NO_RAWAT);
                String tglmasuk= resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK);
                String nmbangsal= resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL);
                String kelas= resultSet.getString(helper_kamar_inap.KEY_KELAS);
                String kdpj= resultSet.getString(helper_kamar_inap.KEY_KODE_STATUS_BAYAR);
                String pj= resultSet.getString(helper_kamar_inap.KEY_STATUS_BAYAR);
                modelkamarinap.addRow(new Object[]{norm, nmp, norawat,tglmasuk,nmbangsal,kelas,kdpj,pj});
               }
                 } catch (ParseException ex) {
                     Logger.getLogger(Crud_farmasi.class.getName()).log(Level.SEVERE, null, ex);
                 }
                
               
              }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Crud_farmasi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void readRec_kamarinapPerina(String[] bangsal,String txtcari,String tgl,String tglserver) {

        try {
            preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_kamar_inap.TB_NAMEV + " WHERE "
                    + helper_kamar_inap.KEY_NO_RM + " like ? OR "
                    + helper_kamar_inap.KEY_NM_PASIEN + " like ?");
            
            preparedStatement.setString(1, "%"+ txtcari +"%");
            preparedStatement.setString(2, "%"+ txtcari +"%");
           

            ResultSet resultSet = preparedStatement.executeQuery();

//        int i = 0;
           // System.out.println("No RM" + resultSet.getFetchSize());

            while (resultSet.next()) {

//            i++;
//            String no = String.valueOf(i);
             if((resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[0])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[1])
                             ))
                 
              {
                  // && resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK).substring(5, resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK).length() - 3).equals(tgl)
                  
                 try {
                     
               if(Utilitas.Hitungtgl(tglserver,Utilitas.Jam(),resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK), Utilitas.Jam())<=744){  
                String norm = resultSet.getString(helper_kamar_inap.KEY_NO_RM);
                String nmp = resultSet.getString(helper_kamar_inap.KEY_NM_PASIEN);
                String norawat = resultSet.getString(helper_kamar_inap.KEY_NO_RAWAT);
                String tglmasuk= resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK);
                String nmbangsal= resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL);
                String kelas= resultSet.getString(helper_kamar_inap.KEY_KELAS);
                String kdpj= resultSet.getString(helper_kamar_inap.KEY_KODE_STATUS_BAYAR);
                String pj= resultSet.getString(helper_kamar_inap.KEY_STATUS_BAYAR);
                modelkamarinap.addRow(new Object[]{norm, nmp, norawat,tglmasuk,nmbangsal,kelas,kdpj,pj});
               }
                 } catch (ParseException ex) {
                     Logger.getLogger(Crud_farmasi.class.getName()).log(Level.SEVERE, null, ex);
                 }
                
               
              }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Crud_farmasi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  public void readRec_BiayatindakankasirInap(String noraw,String tglserver){
        try {
            preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_kamar_inap.TB_NAMEV + " WHERE "
                    + helper_kamar_inap.KEY_NO_RAWAT + " =?");
            
            preparedStatement.setString(1,  noraw );
            
           

            ResultSet resultSet = preparedStatement.executeQuery();

//        int i = 0;
           // System.out.println("No RM" + resultSet.getFetchSize());

            while (resultSet.next()) {

//            i++;
//            String no = String.valueOf(i);
          
                  // && resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK).substring(5, resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK).length() - 3).equals(tgl)
                  
             try {
                     
               if(Utilitas.Hitungtgl(tglserver,Utilitas.Jam(),resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK), Utilitas.Jam())<=744){  
                String norm = resultSet.getString(helper_kamar_inap.KEY_NO_RM);
                String nmp = resultSet.getString(helper_kamar_inap.KEY_NM_PASIEN);
                String norawat = resultSet.getString(helper_kamar_inap.KEY_NO_RAWAT);
                String tglmasuk= resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK);
                String nmbangsal= resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL);
                String kelas= resultSet.getString(helper_kamar_inap.KEY_KELAS);
                String kdpj= resultSet.getString(helper_kamar_inap.KEY_KODE_STATUS_BAYAR);
                String pj= resultSet.getString(helper_kamar_inap.KEY_STATUS_BAYAR);
                Double trf=resultSet.getDouble(helper_kamar_inap.KEY_TRF_KAMAR);
                
                modelkamarinapbiaya.addRow(new Object[]{norm, nmp, norawat,tglmasuk,nmbangsal,kelas,kdpj,pj,trf});
               }
                 } catch (ParseException ex) {
                     Logger.getLogger(Crud_farmasi.class.getName()).log(Level.SEVERE, null, ex);
                 }
                
               
              
            }

        } catch (SQLException ex) {
            Logger.getLogger(Crud_farmasi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void readRec_kamarinap(String[] bangsal,String txtcari,String tgl,String tglserver) {

        try {
            preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_kamar_inap.TB_NAMEV + " WHERE "
                    + helper_kamar_inap.KEY_NO_RM + " like ? OR "
                    + helper_kamar_inap.KEY_NM_PASIEN + " like ?");
            
            preparedStatement.setString(1, "%"+ txtcari +"%");
            preparedStatement.setString(2, "%"+ txtcari +"%");
           

            ResultSet resultSet = preparedStatement.executeQuery();

//        int i = 0;
           // System.out.println("No RM" + resultSet.getFetchSize());

            while (resultSet.next()) {

//            i++;
//            String no = String.valueOf(i);
             if((resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[0])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[1])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[2])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[3])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[4])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[5])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[6])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[7])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[8])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[9])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[10])||
                     resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL).equalsIgnoreCase(bangsal[11])
                             ))
                 
              {
                  // && resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK).substring(5, resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK).length() - 3).equals(tgl)
                  
                 try {
                     
               if(Utilitas.Hitungtgl(tglserver,Utilitas.Jam(),resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK), Utilitas.Jam())<=744){  
                String norm = resultSet.getString(helper_kamar_inap.KEY_NO_RM);
                String nmp = resultSet.getString(helper_kamar_inap.KEY_NM_PASIEN);
                String norawat = resultSet.getString(helper_kamar_inap.KEY_NO_RAWAT);
                String tglmasuk= resultSet.getString(helper_kamar_inap.KEY_TGL_MASUK);
                String nmbangsal= resultSet.getString(helper_kamar_inap.KEY_NM_BANGSAL);
                String kelas= resultSet.getString(helper_kamar_inap.KEY_KELAS);
                String kdpj= resultSet.getString(helper_kamar_inap.KEY_KODE_STATUS_BAYAR);
                String pj= resultSet.getString(helper_kamar_inap.KEY_STATUS_BAYAR);
                modelkamarinap.addRow(new Object[]{norm, nmp, norawat,tglmasuk,nmbangsal,kelas,kdpj,pj});
               }
                 } catch (ParseException ex) {
                     Logger.getLogger(Crud_farmasi.class.getName()).log(Level.SEVERE, null, ex);
                 }
                
               
              }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Crud_farmasi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String readRec_pasien(String norm) throws SQLException {

        String nmp = "";

        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_pasien.TB_NAME + " WHERE "
                + helper_pasien.KEY_NO_RM + " =?");

        preparedStatement.setString(1, norm);

        ResultSet resultSet = preparedStatement.executeQuery();

        int i = 0;

        while (resultSet.next()) {

            String rm = resultSet.getString(helper_pasien.KEY_NO_RM);
            nmp = resultSet.getString(helper_pasien.KEY_NM_PASIEN);

        }

        return nmp;

    }

    public void readRec_brgF(String namabrg) throws SQLException {

        double hrgjmlralan=0.0;
        double hrgjmlkry=0.0;
        double hrgbeli=0.0;
        double hrgralan=0.0;
        double hrgkary=0.0;
        double hrgralanalk=0.0;
        double hrgkaryalk=0.0;
        
        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_brg.TB_NAME + " WHERE "
                + helper_brg.KEY_NAMA_BRG + " like ?");

        preparedStatement.setString(1, "%" + namabrg + "%");

        ResultSet resultSet = preparedStatement.executeQuery();

//        int i = 0;

        while (resultSet.next()) {

//            i++;

//            String no = String.valueOf(i);
            String kodeobat = resultSet.getString(helper_brg.KEY_KODE_BRG);
            String nmbrg = resultSet.getString(helper_brg.KEY_NAMA_BRG);
            hrgbeli = resultSet.getDouble(helper_brg.KEY_HARGA_BELI);
            String kat = resultSet.getString(helper_brg.KEY_KATEGORI);
             
       if(resultSet.getString(helper_brg.KEY_KATEGORI).equals("OBR")){
            hrgralan = hrgbeli*1.1*1.5;
            hrgkary = hrgralan/1.2;
           
       }
       else if(resultSet.getString(helper_brg.KEY_KATEGORI).equals("ALK")){
            hrgralan = hrgbeli*1.8;
            hrgkary = hrgralan/1.2;
       
       }
       else if(resultSet.getString(helper_brg.KEY_KATEGORI).equals("resep")){
       hrgralan = resultSet.getDouble(helper_brg.KEY_HARGA_BELI);
       hrgkary=resultSet.getDouble(helper_brg.KEY_HARGA_BELI);
       }
             DecimalFormat df2 = new DecimalFormat(".##");
       
            modelbrg.addRow(new Object[]{kodeobat, nmbrg, df2.format(hrgralan),df2.format(hrgkary),df2.format(hrgbeli),kat});
        }
    }

    
    
    
    //edit here 26
    public String readRec_CariPegawaiNM(String nip) throws SQLException{
          String nama="";
          preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_pegawai.TB_NAME + " WHERE "
                + helper_pegawai.KEY_NIP + " = ?");

        preparedStatement.setString(1,  nip );

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

//            i++;
//            String no = String.valueOf(i);
         
             nama = resultSet.getString(helper_pegawai.KEY_NAMA);
         
            
        }
        
      return nama;
    }
    public void readRec_pegawaiF(String nm) throws SQLException {

        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_pegawai.TB_NAME + " WHERE "
                + helper_pegawai.KEY_NAMA + " like ?");

        preparedStatement.setString(1, "%" + nm + "%");

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

//            i++;
//            String no = String.valueOf(i);
            String nip = resultSet.getString(helper_pegawai.KEY_NIP);
            String nama = resultSet.getString(helper_pegawai.KEY_NAMA);
            String jbtn = resultSet.getString(helper_pegawai.KEY_JABATAN);
            modelpegawai.addRow(new Object[]{nip, nama,jbtn});

        }

    }

    
    public void readRec_petugasF(String nm) throws SQLException {

        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_petugas.TB_NAME + " WHERE "
                + helper_petugas.KEY_NAMA + " like ?");

        preparedStatement.setString(1, "%" + nm + "%");

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

//            i++;
//            String no = String.valueOf(i);
            String nip = resultSet.getString(helper_petugas.KEY_NIP);
            String nama = resultSet.getString(helper_petugas.KEY_NAMA);

            modeltugas.addRow(new Object[]{nip, nama});

        }

    }

    public void readRec_petugas() throws SQLException {

        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_petugas.TB_NAME);

        ResultSet resultSet = preparedStatement.executeQuery();

//        int i = 0;
        while (resultSet.next()) {

//            i++;
//            String no = String.valueOf(i);
            String nip = resultSet.getString(helper_petugas.KEY_NIP);
            String nama = resultSet.getString(helper_petugas.KEY_NAMA);

            modeltugas.addRow(new Object[]{nip, nama});

        }
    }

    public void readRec_brg() throws SQLException {

        double hrgjmlralan=0.0;
        double hrgjmlkry=0.0;
        double hrgbeli=0.0;
        double hrgralan=0.0;
        double hrgkary=0.0;
        double hrgralanalk=0.0;
        double hrgkaryalk=0.0;
        
        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_brg.TB_NAME);

        ResultSet resultSet = preparedStatement.executeQuery();

      
             
      
        while (resultSet.next()) {
 
            String kodeobat = resultSet.getString(helper_brg.KEY_KODE_BRG);
            String nmbrg = resultSet.getString(helper_brg.KEY_NAMA_BRG);
            hrgbeli = resultSet.getDouble(helper_brg.KEY_HARGA_BELI);
            String kat = resultSet.getString(helper_brg.KEY_KATEGORI);
            
       if(resultSet.getString(helper_brg.KEY_KATEGORI).equals("OBR")){
            hrgralan = hrgbeli*1.1*1.5;
            hrgkary = hrgralan/1.2;
           
       }
       else if(resultSet.getString(helper_brg.KEY_KATEGORI).equals("ALK")){
            hrgralan = hrgbeli*1.8;
            hrgkary = hrgralan/1.2;
       
       }
       else if(resultSet.getString(helper_brg.KEY_KATEGORI).equals("resep")){
         hrgralan = resultSet.getDouble(helper_brg.KEY_HARGA_BELI);
         hrgkary=resultSet.getDouble(helper_brg.KEY_HARGA_BELI);
       }
           
            DecimalFormat df2 = new DecimalFormat(".##");
       
            modelbrg.addRow(new Object[]{kodeobat, nmbrg, df2.format(hrgralan),df2.format(hrgkary),df2.format(hrgbeli),kat});
            
        }
    }

    public void readRec_registrasiKasir(String tgl,String nm,int s)  {

    try {    
        
        
     if(s==1){   
         
             //edit 29 des 2016
             
             preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_VNAME + " WHERE "
                     + helper_registrasi.KEY_TGL_REGISTRASI + " =?" );
             
             preparedStatement.setString(1, tgl);
        
     }
     else{
       preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_VNAME + " WHERE "
               + helper_registrasi.KEY_TGL_REGISTRASI + " =? AND " 
               + helper_registrasi.KEY_NM_PASIEN + " like ?" );

        preparedStatement.setString(1, tgl);
        preparedStatement.setString(2, "%"+ nm +"%");
     }
        //kode igd=3
//        preparedStatement.setInt(2, 3);

        ResultSet resultSet = preparedStatement.executeQuery();

        int i = 0; 

      while (resultSet.next()) {

          
//       if(resultSet.getString(helper_registrasi.KEY_STATUS_LANJUT).equals("Ralan")||resultSet.getString(helper_registrasi.KEY_KODE_POLI).equals("3")){
        
            i++;
            
            String no = String.valueOf(i);
            String rm = resultSet.getString(helper_registrasi.KEY_NO_RM);
            String nmp = resultSet.getString(helper_registrasi.KEY_NM_PASIEN);
            String reg = resultSet.getString(helper_registrasi.KEY_TGL_REGISTRASI);
            String kodedokter = resultSet.getString(helper_registrasi.KEY_KODE_DOKTER);
            String nmdokter = resultSet.getString(helper_registrasi.KEY_NAMA_DOKTER);
            String kdpoli = resultSet.getString(helper_registrasi.KEY_KODE_POLI);
            String poli = resultSet.getString(helper_registrasi.KEY_POLI);
            String kdpj = resultSet.getString(helper_registrasi.KEY_KODE_PJ);
            String nmpj = resultSet.getString(helper_registrasi.KEY_NAMA_PJ);
            String norawat = resultSet.getString(helper_registrasi.KEY_NO_RAWAT);
            String statlanjut = resultSet.getString(helper_registrasi.KEY_STATUS_LANJUT);
            String nosep = resultSet.getString(helper_registrasi.KEY_NO_SEP);
             
            modelregralankasir.addRow(new Object[]{no, rm, nmp, reg,kodedokter,nmdokter,kdpoli,poli,kdpj,nmpj,norawat,statlanjut,nosep});
//         }    
     }
         } catch (SQLException ex) {
             Logger.getLogger(Crud_farmasi.class.getName()).log(Level.SEVERE, null, ex);
         }
    finally{
//        try {
//            preparedStatement.close();
//            resultSet.close();
//            connect.close();
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(Crud_farmasi.class.getName()).log(Level.SEVERE, null, ex);
//        }
       
    }
    
    }
    
    public void readRec_registrasiRalanFisio(String tgl,String name,int s) throws SQLException {

         //edit 05 jan 2016
     if(s==1){   
       
        
        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_VNAME + " WHERE "
                + helper_registrasi.KEY_TGL_REGISTRASI + " =?" );

        preparedStatement.setString(1, tgl);
     }
     if(s==2){   
        
        
        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_VNAME + " WHERE "
                + helper_registrasi.KEY_TGL_REGISTRASI + " =? AND "
                + helper_registrasi.KEY_NM_PASIEN + " like ?" );

        preparedStatement.setString(1, tgl);
        preparedStatement.setString(2, "%" + name + "%");
     }
     
     else{
       preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_VNAME + " WHERE "
                + helper_registrasi.KEY_TGL_REGISTRASI + " =?" );

        preparedStatement.setString(1, tgl);
     }
        //kode igd=3
//        preparedStatement.setInt(2, 3);

        ResultSet resultSet = preparedStatement.executeQuery();

        int i = 0; 

      while (resultSet.next()) {

          
       if(resultSet.getString(helper_registrasi.KEY_STATUS_LANJUT).equals("Ralan")||resultSet.getString(helper_registrasi.KEY_KODE_POLI).equals("3")){
        
           i++;
            String no = String.valueOf(i);
            String rm = resultSet.getString(helper_registrasi.KEY_NO_RM);
            String nmp = resultSet.getString(helper_registrasi.KEY_NM_PASIEN);
            String reg = resultSet.getString(helper_registrasi.KEY_TGL_REGISTRASI);
            String kodedokter = resultSet.getString(helper_registrasi.KEY_KODE_DOKTER);
            String nmdokter = resultSet.getString(helper_registrasi.KEY_NAMA_DOKTER);
            String kdpoli = resultSet.getString(helper_registrasi.KEY_KODE_POLI);
            String poli = resultSet.getString(helper_registrasi.KEY_POLI);
            String kdpj = resultSet.getString(helper_registrasi.KEY_KODE_PJ);
            String nmpj = resultSet.getString(helper_registrasi.KEY_NAMA_PJ);
            String norawat = resultSet.getString(helper_registrasi.KEY_NO_RAWAT);
             
            modelregralan.addRow(new Object[]{no, rm, nmp, reg,kodedokter,nmdokter,kdpoli,poli,kdpj,nmpj,norawat});
         }    
     }
        
    }

    public void readRec_registrasiRalanFisioFarmasi(String tgl,String name,int s) throws SQLException {

         //edit 05 jan 2016
     if(s==1){   
       
        
        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_VNAME + " WHERE "
                + helper_registrasi.KEY_TGL_REGISTRASI + " =?" );

        preparedStatement.setString(1, tgl);
     }
     else if(s==2){   
        
        
        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_VNAME + " WHERE "
                + helper_registrasi.KEY_TGL_REGISTRASI + " =? AND "
                + helper_registrasi.KEY_NM_PASIEN + " like ?" );

        preparedStatement.setString(1, tgl);
        preparedStatement.setString(2, "%" + name + "%");
     }
     else if(s==3){   
        
        
        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_VNAME + " WHERE "
                + helper_registrasi.KEY_TGL_REGISTRASI + " =? AND "
                + helper_registrasi.KEY_NO_RM + " like ?" 
                );

        preparedStatement.setString(1, tgl);
        preparedStatement.setString(2, "%" + name + "%");
        
     }
     
     
     else{
       preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_VNAME + " WHERE "
                + helper_registrasi.KEY_TGL_REGISTRASI + " =?" );

        preparedStatement.setString(1, tgl);
     }
        //kode igd=3
//        preparedStatement.setInt(2, 3);

        ResultSet resultSet = preparedStatement.executeQuery();

        int i = 0; 

      while (resultSet.next()) {

          
       if(resultSet.getString(helper_registrasi.KEY_STATUS_LANJUT).equals("Ralan")||resultSet.getString(helper_registrasi.KEY_KODE_POLI).equals("3")){
        
           i++;
            String no = String.valueOf(i);
            String rm = resultSet.getString(helper_registrasi.KEY_NO_RM);
            String nmp = resultSet.getString(helper_registrasi.KEY_NM_PASIEN);
            String reg = resultSet.getString(helper_registrasi.KEY_TGL_REGISTRASI);
            String kodedokter = resultSet.getString(helper_registrasi.KEY_KODE_DOKTER);
            String nmdokter = resultSet.getString(helper_registrasi.KEY_NAMA_DOKTER);
            String kdpoli = resultSet.getString(helper_registrasi.KEY_KODE_POLI);
            String poli = resultSet.getString(helper_registrasi.KEY_POLI);
            String kdpj = resultSet.getString(helper_registrasi.KEY_KODE_PJ);
            String nmpj = resultSet.getString(helper_registrasi.KEY_NAMA_PJ);
            String norawat = resultSet.getString(helper_registrasi.KEY_NO_RAWAT);
             String nosep = resultSet.getString(helper_registrasi.KEY_NO_SEP);
             
            modelregralanfarmasi.addRow(new Object[]{no, rm, nmp, reg,kodedokter,nmdokter,kdpoli,poli,kdpj,nmpj,norawat,nosep});
         }    
     }
        
    }

    public void readRec_registrasiRalanFarmasi(String tgl,int s) throws SQLException {

        
     if(s==1){   
        //edit 29 des 2016
        
        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_VNAME + " WHERE "
                + helper_registrasi.KEY_TGL_REGISTRASI + " =?" );

        preparedStatement.setString(1, tgl);
     }
     if(s==2){   
        //edit 29 des 2016
        
        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_VNAME + " WHERE "
                + helper_registrasi.KEY_TGL_REGISTRASI + " =? AND "
                + helper_registrasi.KEY_NM_PASIEN + " like ?" );

        preparedStatement.setString(1, tgl);
         preparedStatement.setString(1, tgl);
     }
     
     else{
       preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_VNAME + " WHERE "
                + helper_registrasi.KEY_TGL_REGISTRASI + " =?" );

        preparedStatement.setString(1, tgl);
     }
        //kode igd=3
//        preparedStatement.setInt(2, 3);

        ResultSet resultSet = preparedStatement.executeQuery();

        int i = 0; 

      while (resultSet.next()) {

          
       if(resultSet.getString(helper_registrasi.KEY_STATUS_LANJUT).equals("Ralan")||resultSet.getString(helper_registrasi.KEY_KODE_POLI).equals("3")){
        
           i++;
            String no = String.valueOf(i);
            String rm = resultSet.getString(helper_registrasi.KEY_NO_RM);
            String nmp = resultSet.getString(helper_registrasi.KEY_NM_PASIEN);
            String reg = resultSet.getString(helper_registrasi.KEY_TGL_REGISTRASI);
            String kodedokter = resultSet.getString(helper_registrasi.KEY_KODE_DOKTER);
            String nmdokter = resultSet.getString(helper_registrasi.KEY_NAMA_DOKTER);
            String kdpoli = resultSet.getString(helper_registrasi.KEY_KODE_POLI);
            String poli = resultSet.getString(helper_registrasi.KEY_POLI);
            String kdpj = resultSet.getString(helper_registrasi.KEY_KODE_PJ);
            String nmpj = resultSet.getString(helper_registrasi.KEY_NAMA_PJ);
            String norawat = resultSet.getString(helper_registrasi.KEY_NO_RAWAT);
            String nosep = resultSet.getString(helper_registrasi.KEY_NO_SEP);
             
            modelregralanfarmasi.addRow(new Object[]{no, rm, nmp, reg,kodedokter,nmdokter,kdpoli,poli,kdpj,nmpj,norawat,nosep});
         }    
     }
        
    }

    
    public void readRec_registrasiRalan(String tgl,int s) throws SQLException {

        
     if(s==1){   
        //edit 29 des 2016
        
        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_VNAME + " WHERE "
                + helper_registrasi.KEY_TGL_REGISTRASI + " =?" );

        preparedStatement.setString(1, tgl);
     }
     if(s==2){   
        //edit 29 des 2016
        
        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_VNAME + " WHERE "
                + helper_registrasi.KEY_TGL_REGISTRASI + " =? AND "
                + helper_registrasi.KEY_NM_PASIEN + " like ?" );

        preparedStatement.setString(1, tgl);
         preparedStatement.setString(1, tgl);
     }
     
     else{
       preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_VNAME + " WHERE "
                + helper_registrasi.KEY_TGL_REGISTRASI + " =?" );

        preparedStatement.setString(1, tgl);
     }
        //kode igd=3
//        preparedStatement.setInt(2, 3);

        ResultSet resultSet = preparedStatement.executeQuery();

        int i = 0; 

      while (resultSet.next()) {

          
       if(resultSet.getString(helper_registrasi.KEY_STATUS_LANJUT).equals("Ralan")||resultSet.getString(helper_registrasi.KEY_KODE_POLI).equals("3")){
        
           i++;
            String no = String.valueOf(i);
            String rm = resultSet.getString(helper_registrasi.KEY_NO_RM);
            String nmp = resultSet.getString(helper_registrasi.KEY_NM_PASIEN);
            String reg = resultSet.getString(helper_registrasi.KEY_TGL_REGISTRASI);
            String kodedokter = resultSet.getString(helper_registrasi.KEY_KODE_DOKTER);
            String nmdokter = resultSet.getString(helper_registrasi.KEY_NAMA_DOKTER);
            String kdpoli = resultSet.getString(helper_registrasi.KEY_KODE_POLI);
            String poli = resultSet.getString(helper_registrasi.KEY_POLI);
            String kdpj = resultSet.getString(helper_registrasi.KEY_KODE_PJ);
            String nmpj = resultSet.getString(helper_registrasi.KEY_NAMA_PJ);
            String norawat = resultSet.getString(helper_registrasi.KEY_NO_RAWAT);
             
            modelregralan.addRow(new Object[]{no, rm, nmp, reg,kodedokter,nmdokter,kdpoli,poli,kdpj,nmpj,norawat});
         }    
     }
        
    }

    
    public void readRec_registrasiFisio(String noraw) throws SQLException {

       
        
        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_VNAME + " WHERE "
                + helper_registrasi.KEY_NO_RAWAT + " =?");

        preparedStatement.setString(1, noraw);

        ResultSet resultSet = preparedStatement.executeQuery();

        

        while (resultSet.next()) {

        
           kddokterfisio = resultSet.getString(helper_registrasi.KEY_KODE_DOKTER);
           nmdokterfisio= resultSet.getString(helper_registrasi.KEY_NAMA_DOKTER);
          
        
        }
        
      
    }

    
    public void readRec_registrasi(String tgl) throws SQLException {

        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_NAME + " WHERE "
                + helper_registrasi.KEY_TGL_REGISTRASI + " =?");

        preparedStatement.setString(1, tgl);

        ResultSet resultSet = preparedStatement.executeQuery();

        int i = 0;

        while (resultSet.next()) {

            i++;

            String no = String.valueOf(i);
            String rm = resultSet.getString(helper_registrasi.KEY_NO_RM);
            String nmp = resultSet.getString(helper_registrasi.KEY_NM_PASIEN);
            String reg = resultSet.getString(helper_registrasi.KEY_TGL_REGISTRASI);

            modelreg.addRow(new Object[]{no, rm, nmp, reg});
        }
    }

    public void readRec_registrasiRM(String norm) throws SQLException {

        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_NAME + " WHERE "
                + helper_registrasi.KEY_NO_RM + " =?");

        preparedStatement.setString(1, norm);

        ResultSet resultSet = preparedStatement.executeQuery();

        int i = 0;

        while (resultSet.next()) {

            i++;

            String no = String.valueOf(i);
            String rm = resultSet.getString(helper_registrasi.KEY_NO_RM);
            String nmp = resultSet.getString(helper_registrasi.KEY_NM_PASIEN);
            String reg = resultSet.getString(helper_registrasi.KEY_TGL_REGISTRASI);

            modelreg.addRow(new Object[]{no, rm, nmp, reg});
        }
    }

    public void readRec_registrasiRM_NMPOLI(String norm, String nm, String tgl) throws SQLException {

        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_NAME + " WHERE "
                + helper_registrasi.KEY_TGL_REGISTRASI + " =? AND "
                + helper_registrasi.KEY_NO_RM + " like ? OR "
                + helper_registrasi.KEY_NM_PASIEN
                + " like ? ");

        preparedStatement.setString(1, tgl);
        preparedStatement.setString(2, "%" + norm + "%");
        preparedStatement.setString(3, "%" + nm + "%");

        ResultSet resultSet = preparedStatement.executeQuery();

        int i = 0;

        while (resultSet.next()) {

            i++;

            String no = String.valueOf(i);
            String rm = resultSet.getString(helper_registrasi.KEY_NO_RM);
            String nmp = resultSet.getString(helper_registrasi.KEY_NM_PASIEN);
            String reg = resultSet.getString(helper_registrasi.KEY_TGL_REGISTRASI);

            modelreg.addRow(new Object[]{no, rm, nmp, reg});
        }
    }

    public void readRec_registrasiRM(String norm, String tgl) throws SQLException {

        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_NAME + " WHERE "
                + helper_registrasi.KEY_NO_RM + " =? AND " + helper_registrasi.KEY_TGL_REGISTRASI + " =?");

        preparedStatement.setString(1, norm);
        preparedStatement.setString(2, tgl);

        ResultSet resultSet = preparedStatement.executeQuery();

        int i = 0;

        while (resultSet.next()) {

            i++;

            String no = String.valueOf(i);
            String rm = resultSet.getString(helper_registrasi.KEY_NO_RM);
            String nmp = resultSet.getString(helper_registrasi.KEY_NM_PASIEN);
            String reg = resultSet.getString(helper_registrasi.KEY_TGL_REGISTRASI);

            modelreg.addRow(new Object[]{no, rm, nmp, reg});

        }
    }

    public void readRec_registrasiRMNama(String nm_p, String tgl) throws SQLException {

        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_registrasi.TB_NAME + " WHERE "
                + helper_registrasi.KEY_NM_PASIEN + " like ? AND " + helper_registrasi.KEY_TGL_REGISTRASI + " =?");

        preparedStatement.setString(1, "%" + nm_p + "%");
        preparedStatement.setString(2, tgl);

        ResultSet resultSet = preparedStatement.executeQuery();

        int i = 0;

        while (resultSet.next()) {

            i++;

            String no = String.valueOf(i);
            String rm = resultSet.getString(helper_registrasi.KEY_NO_RM);
            String nmp = resultSet.getString(helper_registrasi.KEY_NM_PASIEN);
            String reg = resultSet.getString(helper_registrasi.KEY_TGL_REGISTRASI);

            modelreg.addRow(new Object[]{no, rm, nmp, reg});
        }
    }

    
    public void CloseCon(){
     try {
           if(resultSet != null){  
             resultSet.close();
             System.out.println("result close");
           }
         
            if(preparedStatement != null){   
                 preparedStatement.close();
                 System.out.println("prep close");
            }
           
           if(connect != null){ 
            connect.close();
            System.out.println("Con close");
           }
        } catch (SQLException ex) {
            Logger.getLogger(Crud_farmasi.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
}
