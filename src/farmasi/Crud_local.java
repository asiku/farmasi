/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farmasi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.view.JasperViewer;
import tarif.frm_tarif;

/**
 *
 * @author jengcool
 */
public class Crud_local extends DBKoneksi_local {
    
    public static String tgl1="";
    public static String tgl2="";
    
    public static HashMap<Integer,Integer> cekvalpilih=new HashMap<Integer,Integer>();
    
    public static HashMap<Integer,Integer> cekvalpilihBPJS=new HashMap<Integer,Integer>();
    
    public static HashMap<Integer,Integer> cekvalpilihtemplate=new HashMap<Integer,Integer>();
     public static HashMap<Integer,Integer> cekvalpilihtemplatepilih=new HashMap<Integer,Integer>();
    
    public static String usm = "";
    public static String psm = "";
    
     public static String namapetugaslogin = "";
     public static String namapoli = "";
    
     public static String nipdpjp="";
     public static String nippjp="";
 
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
     String[] caritrans_title = new String[]{"No.","No. Nota","No. RM","Nama Pasien","Petugas","Tgl","Nama Barang","Jml", "Harga Satuan", "Total"};
    
     String[] tarif_title= new String[]{"Id", "Nama Tindakan","Tarif Tindakan","% RS.","% Dr.",
         "% Sarana","Nama Poli","Status","Keterangan","id poli","id status","Status Pengesah","Status Verif","Kelas","Tarif Tindakan BPJS","% RS. BPJS","% Dr. BPJS",
         "% Sarana BPJS","Status BPJS","Kode BPJS","status_pengesah_bpjs","status_verif_bpjs"};
     
      String[] tarif_title_log= new String[]{"Id", "Nama Tindakan","Tarif Tindakan","% RS.","% Dr.",
         "% Sarana","Nama Poli","Status","Pengesah","Verif","pilih"};
      
      String[] tarif_title_logBPJS= new String[]{"Id", "Nama Tindakan","Tarif Tindakan","% RS.","% Dr.",
         "% Sarana","Nama Poli","Status","Pengesah","Verif","pilih"};
      
      
      String[] unit_detail = new String[]{"No Rawat", "Kode Tindakan", "Tindakan", "Petugas", "Tgl Tindakan", "Username"};

    public DefaultTableModel modelunitdetail = new DefaultTableModel(unit_detail, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };
    
      String[] unit_anakmaster = new String[]{"No Rawat", "No. RM", "Nama", "Kode Bayar", "Cara Bayar", "Tgl Masuk","Kamar","Kelas","Status Inap","Perkembangan"};

    public DefaultTableModel modelunitanakmaster = new DefaultTableModel(unit_anakmaster, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };

      
     final Class[] columnClass = new Class[] {
    String.class, String.class, Double.class, Double.class, Double.class, Double.class, String.class
             , String.class, String.class, String.class, Boolean.class
};
   
      final Class[] columnClassBPJS = new Class[] {
    String.class, String.class, Double.class, Double.class, Double.class, Double.class, String.class
             , String.class, String.class, String.class, Boolean.class
};
     
     
      final Class[] columnClasstemplate = new Class[]{
            String.class, String.class, Boolean.class
        };
     
     String[] poli_title= new String[]{"Id", "Nama Poli"}; 
     
     String[] status_title= new String[]{"Id", "Nama Status"}; 
     
     String[] petugas_title= new String[]{"Nip", "Nama Petugas","Id Poli","Poli","User Name"}; 
     
     String[] tarif_titletemplate = new String[]{"Id", "Nama Tindakan", "Pilih"};
    
     String[] tarif_mastertemplate = new String[]{"Nama Template","Nip" ,"Petugas", "Status"};
     
     String[] tarif_mastertemplatedetail = new String[]{"Nama Template","Kode Tarif" ,"Nama Tarif"};
     
     
      public DefaultTableModel modeltemplatemasterdetail = new DefaultTableModel(tarif_mastertemplatedetail, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };
     
     public DefaultTableModel modeltemplatemaster = new DefaultTableModel(tarif_mastertemplate, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };
     
     
     public DefaultTableModel modeltariftemplate = new DefaultTableModel(tarif_titletemplate, 0) {
            public boolean isCellEditable(int row, int column) {
                if (column == 2) {
                    return true;
                } else {
                    return false;

                }

            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnClasstemplate[columnIndex];
            }

            @Override
            public void setValueAt(Object value, int row, int col) {
                super.setValueAt(value, row, col);
                if (col == 2) {
                    if ((Boolean) this.getValueAt(row, col) == true) {
                        //code goes here
                        cekvalpilihtemplate.put(col, row);
                        cekvalpilihtemplatepilih.put(row, row);
                    } else if ((Boolean) this.getValueAt(row, col) == false) {
                        //code goes here
                        cekvalpilihtemplate.remove(col, row);
                        cekvalpilihtemplatepilih.remove(row, row);
                    }
                }
            }

        };
     
     public DefaultTableModel modeltariflogBPJS = new DefaultTableModel(tarif_title_logBPJS, 0) {
        public boolean isCellEditable(int row, int column) {
            if (column == 10) {
                return true;
            } else {
                return false;

            }

        }
        
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnClassBPJS[columnIndex];
    }
    
     @Override
    public void setValueAt(Object value, int row, int col) {
    super.setValueAt(value, row, col);
    if (col == 10) {
        if ((Boolean) this.getValueAt(row, col) == true) {
            //code goes here
            cekvalpilihBPJS.put(col, row);
        }
        else if ((Boolean) this.getValueAt(row, col) == false) {
            //code goes here
            cekvalpilihBPJS.remove(col,row);
        }
       }   
    }
    
    };
     
     public DefaultTableModel modeltariflog = new DefaultTableModel(tarif_title_log, 0) {
        public boolean isCellEditable(int row, int column) {
            if (column == 10) {
                return true;
            } else {
                return false;

            }

        }
        
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnClass[columnIndex];
    }
    
     @Override
    public void setValueAt(Object value, int row, int col) {
    super.setValueAt(value, row, col);
    if (col == 10) {
        if ((Boolean) this.getValueAt(row, col) == true) {
            //code goes here
            cekvalpilih.put(col, row);
        }
        else if ((Boolean) this.getValueAt(row, col) == false) {
            //code goes here
            cekvalpilih.remove(col,row);
        }
       }   
    }
    
    };
     
     public DefaultTableModel modeltarif = new DefaultTableModel(tarif_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };
     
     public DefaultTableModel modelstatus = new DefaultTableModel(status_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };
     
     public DefaultTableModel modelpoli = new DefaultTableModel(poli_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };
     
      public DefaultTableModel modelctrans = new DefaultTableModel(caritrans_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };
      
    public DefaultTableModel modelpetugas = new DefaultTableModel(petugas_title, 0) {
         public boolean isCellEditable(int row, int column) {
            return false;

        }
     };   
      
      
    public Crud_local() throws Exception{
      ConDb();
    }
    
    
    public void Save_unitAnak(String nmtmp,String nip ,String ptug, String stat)  {

        try {
            preparedStatement = connect.prepareStatement("insert into " + helper_template_tindakan.TB_NAME + " (" 
                    + helper_template_tindakan.KEY_NAMA_TEMPLATE + "," 
                    + helper_template_tindakan.KEY_NIP_PETUGAS + ","
                    + helper_template_tindakan.KEY_PETUGAS  + ","
                    + helper_template_tindakan.KEY_STATUS + ") "
                    + " values (?,?,?,?)");
         
            preparedStatement.setString(1, nmtmp);
            preparedStatement.setString(2, nip);
            preparedStatement.setString(3, ptug);
            preparedStatement.setString(4, stat);
           
            preparedStatement.execute();
          //  JOptionPane.showMessageDialog(null, "Data Tersimpan");
          
          
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Gagal Tersimpan");
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public void Save_mastertemplatedetail(String nmtmp, String trf, String nmtf)  {

        try {
            preparedStatement = connect.prepareStatement("insert into " + helper_template_tindakan_detail.TB_NAME + " (" 
                    + helper_template_tindakan_detail.KEY_NAMA_TEMPLATE + "," 
                    + helper_template_tindakan_detail.KEY_KODE_TARIF
                    + "," + helper_template_tindakan_detail.KEY_NAMA_TARIF + ") "
                    + " values (?,?,?)");
         
            preparedStatement.setString(1, nmtmp);
            preparedStatement.setString(2, trf);
            preparedStatement.setString(3, nmtf);
           
            preparedStatement.execute();
          //  JOptionPane.showMessageDialog(null, "Data Tersimpan");
          
          
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Gagal Tersimpan");
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
   

     public void  Update_inapanakmaster(String norawat,String nipdpjp ,String nipppjp
            , boolean statusinap,String perkembangan) {
    
         
         
        try {
            preparedStatement = connect.prepareStatement("update " + helper_unit.TB_NAME + " set " 
                    + helper_unit.KEY_NIP_DPJP+"=?," 
                    + helper_unit.KEY_NIP_PPJP+"=?,"
                    + helper_unit.KEY_STATUSINAP  + "=?,"
                    + helper_unit.KEY_PERKEMBANGAN  + "=?"
                    +" where "+ helper_unit.KEY_NO_RAWAT + "=?");
            
           
           preparedStatement.setString(5, norawat);
            preparedStatement.setString(1, nipdpjp);
            preparedStatement.setString(2, nipppjp);
            preparedStatement.setBoolean(3, statusinap);
            preparedStatement.setString(4, perkembangan);
             
            preparedStatement.executeUpdate();
             //JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");
        } catch (SQLException ex) {
//             if(ex.getErrorCode() == 1062 ){
//            //duplicate primary key 
//             JOptionPane.showMessageDialog(null, "Gagal Update : Kode " + kode_tarif + " sudah pernah di input");
//            }
//            else{
//            JOptionPane.showMessageDialog(null, "Gagal Update");
//            }
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
  
     public void Save_inapanakDetail(String norawat,String kodetarif,String nip
           ,String username)  {

        try {
            preparedStatement = connect.prepareStatement("insert into " + helper_unit_detail.TB_NAME + " (" 
                    + helper_unit_detail.KEY_NO_RAWAT + "," 
                    + helper_unit_detail.KEY_KODE_TARIF + ","
                    + helper_unit_detail.KEY_NIP_PETUGAS  + ","
                    + helper_unit_detail.KEY_USERNAME + ") "
                    + " values (?,?,?,?)");
         
            preparedStatement.setString(1, norawat);
            preparedStatement.setString(2, kodetarif);
            preparedStatement.setString(3, nip);
            preparedStatement.setString(4, username);
          
            
            preparedStatement.execute();
          //  JOptionPane.showMessageDialog(null, "Data Tersimpan");
          
          
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Gagal Tersimpan");
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    public void Save_inapanakmaster(String norawat,String nipdpjp ,String nipppjp
            , boolean statusinap,String perkembangan,String statkamar)  {

        try {
            preparedStatement = connect.prepareStatement("insert into " + helper_unit.TB_NAME + " (" 
                    + helper_unit.KEY_NO_RAWAT + "," 
                    + helper_unit.KEY_NIP_DPJP + ","
                    + helper_unit.KEY_NIP_PPJP  + ","
                    + helper_unit.KEY_STATUSINAP  + ","
                    + helper_unit.KEY_PERKEMBANGAN + ","
                    + helper_unit.KEY_STATUS_KAMAR + ") "
                    + " values (?,?,?,?,?,?)");
         
            preparedStatement.setString(1, norawat);
            preparedStatement.setString(2, nipdpjp);
            preparedStatement.setString(3, nipppjp);
            preparedStatement.setBoolean(4, statusinap);
            preparedStatement.setString(5, perkembangan);
             preparedStatement.setString(6, statkamar);
            
           
            preparedStatement.execute();
          JOptionPane.showMessageDialog(null, "Data Tersimpan");
          
          
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal Tersimpan");
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    public void Save_mastertemplate(String nmtmp,String nip ,String ptug, String stat)  {

        try {
            preparedStatement = connect.prepareStatement("insert into " + helper_template_tindakan.TB_NAME + " (" 
                    + helper_template_tindakan.KEY_NAMA_TEMPLATE + "," 
                    + helper_template_tindakan.KEY_NIP_PETUGAS + ","
                    + helper_template_tindakan.KEY_PETUGAS  + ","
                    + helper_template_tindakan.KEY_STATUS + ") "
                    + " values (?,?,?,?)");
         
            preparedStatement.setString(1, nmtmp);
            preparedStatement.setString(2, nip);
            preparedStatement.setString(3, ptug);
            preparedStatement.setString(4, stat);
           
            preparedStatement.execute();
          //  JOptionPane.showMessageDialog(null, "Data Tersimpan");
          
          
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Gagal Tersimpan");
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void readRec_cariMasterTemplateDetail(String nm) throws SQLException {
  
       preparedStatement = connect.prepareStatement("SELECT * FROM " 
               + helper_template_tindakan_detail.TB_NAME+ " WHERE " 
      + helper_template_tindakan_detail.KEY_NAMA_TEMPLATE + " =? ");
      
        preparedStatement.setString(1, nm);
    
       ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
         
            String namatmp = resultSet.getString(helper_template_tindakan_detail.KEY_NAMA_TEMPLATE);
            String kdt = resultSet.getString(helper_template_tindakan_detail.KEY_KODE_TARIF);
            String nmt = resultSet.getString(helper_template_tindakan_detail.KEY_NAMA_TARIF);
          
         
            modeltemplatemasterdetail.addRow(new Object[]{namatmp,kdt,nmt});
            
        }
        
        
    }
    
    public void readRec_cariUnitDetailInapanak(String nm,int i) throws SQLException {
  
    if(i==1){    
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_unit_detail.TB_VNAME + " WHERE " 
      + helper_unit_detail.KEY_NAMA_TINDAKAN + " like ? ");

      preparedStatement.setString(1, "%" + nm + "%");
    }
    else if(i==2){    
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_unit_detail.TB_VNAME + " WHERE " 
      + helper_unit_detail.KEY_NO_RAWAT + " =? ");

      preparedStatement.setString(1,  nm );
    }
    else if(i==4){    
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_unit_detail.TB_VNAME + " WHERE DATE(" 
      + helper_unit_detail.KEY_TGL + ") BETWEEN ? AND ? AND "
      + helper_unit_detail.KEY_NO_RAWAT+"=?");

      preparedStatement.setString(1,  tgl1 );
      preparedStatement.setString(2,  tgl2 );
      preparedStatement.setString(3,  nm );
    }
    else if(i==3){    
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_unit_detail.TB_VNAME + " WHERE " 
      + helper_unit_detail.KEY_NAMA + " like ? or "
      + helper_unit_detail.KEY_USERNAME + " like ? or "
      + helper_unit_detail.KEY_NAMA_TINDAKAN + " like ? ");

      preparedStatement.setString(1,  "%" + nm + "%");
      preparedStatement.setString(2,  "%" + nm + "%");
      preparedStatement.setString(3,  "%" + nm + "%");
      
       ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
         
            String norawat = resultSet.getString(helper_unit_detail.KEY_NO_RAWAT);
            String kdtarif = resultSet.getString(helper_unit_detail.KEY_KODE_TARIF);
            String namatindakan = resultSet.getString(helper_unit_detail.KEY_NAMA_TINDAKAN);
            String nama = resultSet.getString(helper_unit_detail.KEY_NAMA);
            String tgl = resultSet.getString(helper_unit_detail.KEY_TGL);
            String username = resultSet.getString(helper_unit_detail.KEY_USERNAME);
            modelunitdetail.addRow(new Object[]{norawat,kdtarif,namatindakan,nama,tgl,username});
            
        }
        
    }
    else{
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_unit_detail.TB_VNAME);
    }
        
    
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
         
            String norawat = resultSet.getString(helper_unit_detail.KEY_NO_RAWAT);
            String kdtarif = resultSet.getString(helper_unit_detail.KEY_KODE_TARIF);
            String namatindakan = resultSet.getString(helper_unit_detail.KEY_NAMA_TINDAKAN);
            String nama = resultSet.getString(helper_unit_detail.KEY_NAMA);
            String tgl = resultSet.getString(helper_unit_detail.KEY_TGL);
            String username = resultSet.getString(helper_unit_detail.KEY_USERNAME);
            modelunitdetail.addRow(new Object[]{norawat,kdtarif,namatindakan,nama,tgl,username});
            
        }
        
        
    }
    
    
    public void readRec_cariMasterTemplate(String nm,int i) throws SQLException {
  
    if(i==1){    
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_template_tindakan.TB_NAME + " WHERE " 
      + helper_template_tindakan.KEY_NAMA_TEMPLATE + " like ? ");

      preparedStatement.setString(1, "%" + nm + "%");
    }
    else{
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_template_tindakan.TB_NAME);
    }
        
    
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
         
            String namatmp = resultSet.getString(helper_template_tindakan.KEY_NAMA_TEMPLATE);
            String nip = resultSet.getString(helper_template_tindakan.KEY_NIP_PETUGAS);
            String petug = resultSet.getString(helper_template_tindakan.KEY_PETUGAS);
            String stat = resultSet.getString(helper_template_tindakan.KEY_STATUS);
         
            modeltemplatemaster.addRow(new Object[]{namatmp,nip,petug,stat});
            
        }
        
        
    }
    
     public void readRec_cariTarifTemplateP(String nm,boolean i,int idpoli) throws SQLException {
    
     if(i){    
        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_tarif.V_NAME + " WHERE " 
        + helper_tarif.KEY_STATUS_PENGESAH + " =? AND "
        + helper_tarif.KEY_STATUS_VERIF + " =? AND "        
        + helper_tarif.KEY_NAMA_TINDAKAN + " like ? ");
        
        preparedStatement.setString(1, "ok");
        preparedStatement.setString(2, "ok");
        preparedStatement.setString(3, "%" + nm + "%");
     }
     else{
         preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_tarif.V_NAME + " WHERE "
         + helper_tarif.KEY_STATUS_PENGESAH + " =? AND "
         + helper_tarif.KEY_STATUS_VERIF + " =? AND "        
         + helper_tarif.KEY_ID_POLI + " =? ");
          
         preparedStatement.setString(1, "ok");
          preparedStatement.setString(2, "ok");
          preparedStatement.setInt(3, idpoli);
     }
        ResultSet resultSet = preparedStatement.executeQuery();

        String kodetarif="";
        String nmtindakan="";
        
        while (resultSet.next()) {
         
             kodetarif = resultSet.getString(helper_tarif.KEY_KODE_TARIF);
             nmtindakan = resultSet.getString(helper_tarif.KEY_NAMA_TINDAKAN);
         
//             double tarif = resultSet.getDouble(helper_tarif.KEY_TARIF_TINDAKAN);
//              int presrs = resultSet.getInt(helper_tarif.KEY_PRESENTASE_RS);
//              int presdr = resultSet.getInt(helper_tarif.KEY_PRESENTASE_DR);
//              int pressarana = resultSet.getInt(helper_tarif.KEY_PRESENTASE_SARANA);
//              String poli = resultSet.getString(helper_tarif.KEY_POLI);
//              String status = resultSet.getString(helper_tarif.KEY_STATUS);
//              String ket = resultSet.getString(helper_tarif.KEY_KETERANGAN);
//              int id_poli = resultSet.getInt(helper_tarif.KEY_ID_POLI);
//              int id_status= resultSet.getInt(helper_tarif.KEY_ID_STATUS);
//              String p = resultSet.getString(helper_tarif.KEY_STATUS_PENGESAH);
//              String v = resultSet.getString(helper_tarif.KEY_STATUS_VERIF);
          
            boolean pilih=false;
            //modeltariftemplate.addRow(new Object[]{kodetarif, nmtindakan,tarif,presrs,presdr,pressarana,poli,status,ket,id_poli,id_status,p,v});
            modeltariftemplate.addRow(new Object[]{kodetarif, nmtindakan,pilih});
        }
     
    }
    
    public void readRec_cariTarifTemplate(String nm,boolean i,int idpoli,String kelas) throws SQLException {
    
     if(i){    
        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_tarif.V_NAME + " WHERE " 
        + helper_tarif.KEY_STATUS_PENGESAH + " =? AND "
        + helper_tarif.KEY_STATUS_VERIF + " =? AND "        
        + helper_tarif.KEY_NAMA_TINDAKAN + " like ? ");
        
        preparedStatement.setString(1, "ok");
        preparedStatement.setString(2, "ok");
        preparedStatement.setString(3, "%" + nm + "%");
     }
     else{
         preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_tarif.V_NAME + " WHERE "
         + helper_tarif.KEY_STATUS_PENGESAH + " =? AND "
         + helper_tarif.KEY_STATUS_VERIF + " =? AND "        
         + helper_tarif.KEY_ID_POLI + " =? ");
          
         preparedStatement.setString(1, "ok");
          preparedStatement.setString(2, "ok");
          preparedStatement.setInt(3, idpoli);
     }
        ResultSet resultSet = preparedStatement.executeQuery();

        String kodetarif="";
        String nmtindakan="";
        
        while (resultSet.next()) {
         if(resultSet.getString(helper_tarif.KEY_KELAS).equalsIgnoreCase(kelas)||resultSet.getString(helper_tarif.KEY_KELAS).equalsIgnoreCase("-")){
             kodetarif = resultSet.getString(helper_tarif.KEY_KODE_TARIF);
             nmtindakan = resultSet.getString(helper_tarif.KEY_NAMA_TINDAKAN);
             
             boolean pilih=false;
            //modeltariftemplate.addRow(new Object[]{kodetarif, nmtindakan,tarif,presrs,presdr,pressarana,poli,status,ket,id_poli,id_status,p,v});
            modeltariftemplate.addRow(new Object[]{kodetarif, nmtindakan,pilih});
         }
//             double tarif = resultSet.getDouble(helper_tarif.KEY_TARIF_TINDAKAN);
//              int presrs = resultSet.getInt(helper_tarif.KEY_PRESENTASE_RS);
//              int presdr = resultSet.getInt(helper_tarif.KEY_PRESENTASE_DR);
//              int pressarana = resultSet.getInt(helper_tarif.KEY_PRESENTASE_SARANA);
//              String poli = resultSet.getString(helper_tarif.KEY_POLI);
//              String status = resultSet.getString(helper_tarif.KEY_STATUS);
//              String ket = resultSet.getString(helper_tarif.KEY_KETERANGAN);
//              int id_poli = resultSet.getInt(helper_tarif.KEY_ID_POLI);
//              int id_status= resultSet.getInt(helper_tarif.KEY_ID_STATUS);
//              String p = resultSet.getString(helper_tarif.KEY_STATUS_PENGESAH);
//              String v = resultSet.getString(helper_tarif.KEY_STATUS_VERIF);
          
            
        }
     
    }
    
    
     public void readRec_cariTarif(String nm) throws SQLException {
  
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_tarif.V_NAME + " WHERE " 
      + helper_tarif.KEY_NAMA_TINDAKAN + " like ? ");

        preparedStatement.setString(1, "%" + nm + "%");
        
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
         
            String kodetarif = resultSet.getString(helper_tarif.KEY_KODE_TARIF);
            String nmtindakan = resultSet.getString(helper_tarif.KEY_NAMA_TINDAKAN);
             double tarif = resultSet.getDouble(helper_tarif.KEY_TARIF_TINDAKAN);
              int presrs = resultSet.getInt(helper_tarif.KEY_PRESENTASE_RS);
              int presdr = resultSet.getInt(helper_tarif.KEY_PRESENTASE_DR);
              int pressarana = resultSet.getInt(helper_tarif.KEY_PRESENTASE_SARANA);
              String poli = resultSet.getString(helper_tarif.KEY_POLI);
              String status = resultSet.getString(helper_tarif.KEY_STATUS);
              String ket = resultSet.getString(helper_tarif.KEY_KETERANGAN);
              int id_poli = resultSet.getInt(helper_tarif.KEY_ID_POLI);
              int id_status= resultSet.getInt(helper_tarif.KEY_ID_STATUS);
              String p = resultSet.getString(helper_tarif.KEY_STATUS_PENGESAH);
              String v = resultSet.getString(helper_tarif.KEY_STATUS_VERIF);
              String kelas = resultSet.getString(helper_tarif.KEY_KELAS);
          
            modeltarif.addRow(new Object[]{kodetarif, nmtindakan,tarif,presrs,presdr,pressarana,poli,status,ket,id_poli,id_status,p,v,kelas});
            
        }
        
        
    }
     
     
//     xpublic void readRec_cariTariflogPengesahBPJS() throws SQLException {
//  
//      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_tarif.V_NAME + " WHERE " 
//      + helper_tarif.KEY_STATUS_PENGESAH +"=?"+" AND "+helper_tarif.KEY_STATUS_VERIF +"=?");
//
//       preparedStatement.setString(1,"pending");
//       preparedStatement.setString(2,"ok");
//        
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        while (resultSet.next()) {
//         
//            String kodetarif = resultSet.getString(helper_tarif.KEY_KODE_TARIF);
//            String nmtindakan = resultSet.getString(helper_tarif.KEY_NAMA_TINDAKAN);
//              double tarif = resultSet.getDouble(helper_tarif.KEY_TARIF_TINDAKAN);
//              double presrs = resultSet.getInt(helper_tarif.KEY_PRESENTASE_RS);
//              double presdr = resultSet.getInt(helper_tarif.KEY_PRESENTASE_DR);
//              double pressarana = resultSet.getInt(helper_tarif.KEY_PRESENTASE_SARANA);
//              String poli = resultSet.getString(helper_tarif.KEY_POLI);
//              String status = resultSet.getString(helper_tarif.KEY_STATUS);
//              String p = resultSet.getString(helper_tarif.KEY_STATUS_PENGESAH);
//              String v = resultSet.getString(helper_tarif.KEY_STATUS_VERIF);
//              boolean pilih=false;
//          
//            modeltariflog.addRow(new Object[]{kodetarif, nmtindakan,tarif,presrs,presdr,pressarana,poli,status,p,v,pilih});
//            
//        }
//    }
     
     public void readRec_cariTariflogPengesahBPJS() throws SQLException {
  
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_tarif.V_NAME + " WHERE " 
      + helper_tarif.KEY_STATUS_PENGESAH_BPJS +"=?"+" AND "+helper_tarif.KEY_STATUS_VERIF_BPJS +"=?");

       preparedStatement.setString(1,"pending");
       preparedStatement.setString(2,"ok");
        
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
         
            String kodetarif = resultSet.getString(helper_tarif.KEY_KODE_TARIF);
            String nmtindakan = resultSet.getString(helper_tarif.KEY_NAMA_TINDAKAN);
              double tarif = resultSet.getDouble(helper_tarif.KEY_TARIF_TINDAKAN_BPJS);
              double presrs = resultSet.getInt(helper_tarif.KEY_PRESENTASE_RS_BPJS);
              double presdr = resultSet.getInt(helper_tarif.KEY_PRESENTASE_DR_BPJS);
              double pressarana = resultSet.getInt(helper_tarif.KEY_PRESENTASE_SARANA_BPJS);
              String poli = resultSet.getString(helper_tarif.KEY_POLI);
              String status = resultSet.getString(helper_tarif.KEY_STATUS);
              String p = resultSet.getString(helper_tarif.KEY_STATUS_PENGESAH_BPJS);
              String v = resultSet.getString(helper_tarif.KEY_STATUS_VERIF_BPJS);
              boolean pilih=false;
          
            modeltariflogBPJS.addRow(new Object[]{kodetarif, nmtindakan,tarif,presrs,presdr,pressarana,poli,status,p,v,pilih});
            
        }
    }
     
     public void readRec_cariTariflogPengesah() throws SQLException {
  
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_tarif.V_NAME + " WHERE " 
      + helper_tarif.KEY_STATUS_PENGESAH +"=?"+" AND "+helper_tarif.KEY_STATUS_VERIF +"=?");

       preparedStatement.setString(1,"pending");
       preparedStatement.setString(2,"ok");
        
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
         
            String kodetarif = resultSet.getString(helper_tarif.KEY_KODE_TARIF);
            String nmtindakan = resultSet.getString(helper_tarif.KEY_NAMA_TINDAKAN);
              double tarif = resultSet.getDouble(helper_tarif.KEY_TARIF_TINDAKAN);
              double presrs = resultSet.getInt(helper_tarif.KEY_PRESENTASE_RS);
              double presdr = resultSet.getInt(helper_tarif.KEY_PRESENTASE_DR);
              double pressarana = resultSet.getInt(helper_tarif.KEY_PRESENTASE_SARANA);
              String poli = resultSet.getString(helper_tarif.KEY_POLI);
              String status = resultSet.getString(helper_tarif.KEY_STATUS);
              String p = resultSet.getString(helper_tarif.KEY_STATUS_PENGESAH);
              String v = resultSet.getString(helper_tarif.KEY_STATUS_VERIF);
              boolean pilih=false;
          
            modeltariflog.addRow(new Object[]{kodetarif, nmtindakan,tarif,presrs,presdr,pressarana,poli,status,p,v,pilih});
            
        }
    }
   
      public void readRec_cariTariflogBPJS() throws SQLException {
  
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_tarif.V_NAME + " WHERE " 
      + helper_tarif.KEY_STATUS_PENGESAH_BPJS +"=?"+" AND "+helper_tarif.KEY_STATUS_VERIF_BPJS +"=?");

       preparedStatement.setString(1,"edit");
       preparedStatement.setString(2,"edit");
        
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
         
            String kodetarif = resultSet.getString(helper_tarif.KEY_KODE_TARIF);
            String nmtindakan = resultSet.getString(helper_tarif.KEY_NAMA_TINDAKAN);
              double tarif = resultSet.getDouble(helper_tarif.KEY_TARIF_TINDAKAN);
              double presrs = resultSet.getInt(helper_tarif.KEY_PRESENTASE_RS_BPJS);
              double presdr = resultSet.getInt(helper_tarif.KEY_PRESENTASE_DR_BPJS);
              double pressarana = resultSet.getInt(helper_tarif.KEY_PRESENTASE_SARANA_BPJS);
              String poli = resultSet.getString(helper_tarif.KEY_POLI);
              String status = resultSet.getString(helper_tarif.KEY_STATUS);
              String p = resultSet.getString(helper_tarif.KEY_STATUS_PENGESAH_BPJS);
              String v = resultSet.getString(helper_tarif.KEY_STATUS_VERIF_BPJS);
              boolean pilih=false;
          
            modeltariflogBPJS.addRow(new Object[]{kodetarif, nmtindakan,tarif,presrs,presdr,pressarana,poli,status,p,v,pilih});
            
        }
    }
  
     
    public void readRec_cariTariflog() throws SQLException {
  
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_tarif.V_NAME + " WHERE " 
      + helper_tarif.KEY_STATUS_PENGESAH +"=?"+" AND "+helper_tarif.KEY_STATUS_VERIF +"=?");

       preparedStatement.setString(1,"edit");
       preparedStatement.setString(2,"edit");
        
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
         
            String kodetarif = resultSet.getString(helper_tarif.KEY_KODE_TARIF);
            String nmtindakan = resultSet.getString(helper_tarif.KEY_NAMA_TINDAKAN);
              double tarif = resultSet.getDouble(helper_tarif.KEY_TARIF_TINDAKAN);
              double presrs = resultSet.getInt(helper_tarif.KEY_PRESENTASE_RS);
              double presdr = resultSet.getInt(helper_tarif.KEY_PRESENTASE_DR);
              double pressarana = resultSet.getInt(helper_tarif.KEY_PRESENTASE_SARANA);
              String poli = resultSet.getString(helper_tarif.KEY_POLI);
              String status = resultSet.getString(helper_tarif.KEY_STATUS);
              String p = resultSet.getString(helper_tarif.KEY_STATUS_PENGESAH);
              String v = resultSet.getString(helper_tarif.KEY_STATUS_VERIF);
              boolean pilih=false;
          
            modeltariflog.addRow(new Object[]{kodetarif, nmtindakan,tarif,presrs,presdr,pressarana,poli,status,p,v,pilih});
            
        }
    }
     
     
    public void readRec_cariTarif() throws SQLException {
  
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_tarif.V_NAME );

       
        
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
         
            String kodetarif = resultSet.getString(helper_tarif.KEY_KODE_TARIF);
            String nmtindakan = resultSet.getString(helper_tarif.KEY_NAMA_TINDAKAN);
              double tarif = resultSet.getDouble(helper_tarif.KEY_TARIF_TINDAKAN);
              double presrs = resultSet.getInt(helper_tarif.KEY_PRESENTASE_RS);
              double presdr = resultSet.getInt(helper_tarif.KEY_PRESENTASE_DR);
              double pressarana = resultSet.getInt(helper_tarif.KEY_PRESENTASE_SARANA);
              String poli = resultSet.getString(helper_tarif.KEY_POLI);
              String status = resultSet.getString(helper_tarif.KEY_STATUS);
              String ket = resultSet.getString(helper_tarif.KEY_KETERANGAN);
               int id_poli = resultSet.getInt(helper_tarif.KEY_ID_POLI);
              int id_status= resultSet.getInt(helper_tarif.KEY_ID_STATUS);
              String p = resultSet.getString(helper_tarif.KEY_STATUS_PENGESAH);
              String v = resultSet.getString(helper_tarif.KEY_STATUS_VERIF);
              String kelas = resultSet.getString(helper_tarif.KEY_KELAS);
              double tarifbpjs = resultSet.getDouble(helper_tarif.KEY_TARIF_TINDAKAN_BPJS);
              double presrsbpjs = resultSet.getInt(helper_tarif.KEY_PRESENTASE_RS_BPJS);
              double presdrbpjs = resultSet.getInt(helper_tarif.KEY_PRESENTASE_DR_BPJS);
              double pressaranabpjs = resultSet.getInt(helper_tarif.KEY_PRESENTASE_SARANA_BPJS);
              
              String statusbpjs = resultSet.getString(helper_tarif.KEY_STATUS_BPJS);
              String kodebpjs = resultSet.getString(helper_tarif.KEY_KODE_BPJS);
             
              String pbpjs = resultSet.getString(helper_tarif.KEY_STATUS_PENGESAH_BPJS);
              String vbpjs = resultSet.getString(helper_tarif.KEY_STATUS_VERIF_BPJS);
              
              modeltarif.addRow(new Object[]{kodetarif, nmtindakan,tarif,presrs,presdr,pressarana,poli,status,ket,id_poli,id_status,p,v,kelas,
                                           tarifbpjs,presrsbpjs,presdrbpjs,pressaranabpjs,statusbpjs,kodebpjs,pbpjs,vbpjs});
//               modeltarif.addRow(new Object[]{kodetarif, nmtindakan,tarif,presrs,presdr,pressarana,poli,status,ket,id_poli,id_status,p,v,kelas
//                                           });
            
        }
    }
    
    public void readRec_cariStatus() throws SQLException {
  
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_status.TB_NAME);

       
        
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
              
            String idstatus = resultSet.getString(helper_status.KEY_ID_STATUS);
            String nmstatus = resultSet.getString(helper_status.KEY_STATUS);
          
            modelstatus.addRow(new Object[]{idstatus, nmstatus});
            
        }
    }
    
    public void readRec_cariStatus(String nm_status) throws SQLException {
  
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_status.TB_NAME + " WHERE "
                + helper_status.KEY_STATUS + " like ? ");

        preparedStatement.setString(1, "%" + nm_status + "%");
        
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
              
            String idstatus = resultSet.getString(helper_status.KEY_ID_STATUS);
            String nmstatus = resultSet.getString(helper_status.KEY_STATUS);
          
            modelstatus.addRow(new Object[]{idstatus, nmstatus});
            
        }
    }
    
    public void readRec_cariPoli(String nm_poli) throws SQLException {
  
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_poli.TB_NAME + " WHERE "
                + helper_poli.KEY_POLI + " like ? ");

        preparedStatement.setString(1, "%" + nm_poli + "%");
        
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
              
            String idpoli = resultSet.getString(helper_poli.KEY_ID_POLI);
            String nmpoli = resultSet.getString(helper_poli.KEY_POLI);
          
            modelpoli.addRow(new Object[]{idpoli, nmpoli});
            
        }
    }
    
    
    
    public void readRec_cariPoli() throws SQLException {

        
       preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_poli.TB_NAME);
    
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
  
            
            String idpoli = resultSet.getString(helper_poli.KEY_ID_POLI);
            String nmpoli = resultSet.getString(helper_poli.KEY_POLI);
          
            modelpoli.addRow(new Object[]{idpoli, nmpoli});
            
        }
    }
    
      public void readRec_cariPetugasF(String nm_petugas) throws SQLException {

      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_petugas_poli.TB_NAMEV + " WHERE "
                + helper_petugas_poli.KEY_NAMA + " like ? ");

        preparedStatement.setString(1, "%" + nm_petugas + "%");    
          
       
    
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
  
            String nip = resultSet.getString(helper_petugas_poli.KEY_NIP);
            String nama= resultSet.getString(helper_petugas_poli.KEY_NAMA);
            String idpoli = resultSet.getString(helper_petugas_poli.KEY_ID_POLI);
            String nmpoli = resultSet.getString(helper_petugas_poli.KEY_POLI);
            String username = resultSet.getString(helper_petugas_poli.KEY_USERNAME);
          
            modelpetugas.addRow(new Object[]{nip, nama,idpoli,nmpoli,username});
            
            
        }
    }
    
      
        public void readRec_cariPetugasBypoli(String username) throws SQLException {

       preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_petugas_poli.TB_NAMEV + " WHERE "
                + helper_petugas_poli.KEY_USERNAME + "=?");

        preparedStatement.setString(1,username);    
        
        ResultSet resultSet = preparedStatement.executeQuery();
        

        while (resultSet.next()) {
    
            namapoli= resultSet.getString(helper_petugas_poli.KEY_POLI);
               
        }
    }
     
     //edit 26   
     public void readRec_cariUnitMaster(String txtcari,int i) throws SQLException {

      if(i==1){   
          preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_unit.TB_NAME);
      }
      else  if(i==3){
          preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_unit.TB_NAME+ " WHERE "
                                             + helper_unit.KEY_NO_RAWAT + " =?");

          preparedStatement.setString(1, txtcari);
          ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            nipdpjp=resultSet.getString(helper_unit.KEY_NIP_DPJP);
            nippjp=resultSet.getString(helper_unit.KEY_NIP_PPJP);
        }
          
      }
      else  if(i==2){
          preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_unit.TB_VNAME+ " WHERE "
                                             + helper_unit.KEY_NO_RAWAT + " =?");

          preparedStatement.setString(1, txtcari);
           ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
  
            String norawat = resultSet.getString(helper_unit.KEY_NO_RAWAT);
            String norm= resultSet.getString(helper_unit.KEY_NO_RM);
            String nama = resultSet.getString(helper_unit.KEY_NAMA);
            String kdpj = resultSet.getString(helper_unit.KEY_KODE_PJ);
            String nmpj = resultSet.getString(helper_unit.KEY_NAMA_PJ);
            String tglmasuk=resultSet.getString(helper_unit.KEY_TGLMASUK);
            String kamar=resultSet.getString(helper_unit.KEY_KAMAR_INAP);
            String kelas=resultSet.getString(helper_unit.KEY_KELAS);
            boolean statusinap = resultSet.getBoolean(helper_unit.KEY_STATUSINAP);
            String perkembangan = resultSet.getString(helper_unit.KEY_PERKEMBANGAN);
          
          modelunitanakmaster.addRow(new Object[]{norawat, norm,nama,kdpj,nmpj,tglmasuk,kamar,kelas,statusinap,perkembangan});
            
       
      }
      
            
        }
    } 
     
     public int readRec_Hitkasir(String norw) throws SQLException {

       int hit=0;  
       preparedStatement = connect.prepareStatement("SELECT count(*) as hit FROM " + helper_kasir.TB_NAME +" WHERE "
       +helper_kasir.KEY_NO_RAWAT+"=?");
    
        preparedStatement.setString(1, norw);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
  
//            String norawat = resultSet.getString(helper_kasir.KEY_NO_RAWAT);
//            String tgl= resultSet.getString(helper_kasir.KEY_TGL);
//            String username = resultSet.getString(helper_kasir.KEY_USERNAME);
//            String stat = resultSet.getString(helper_kasir.KEY_SET_STATUS);
            hit=resultSet.getInt("hit");
            
        }
        
        return hit;
    }
     
     public void readRec_cariPetugas() throws SQLException {

       preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_petugas_poli.TB_NAMEV);
    
       
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
  
            String nip = resultSet.getString(helper_petugas_poli.KEY_NIP);
            String nama= resultSet.getString(helper_petugas_poli.KEY_NAMA);
            String idpoli = resultSet.getString(helper_petugas_poli.KEY_ID_POLI);
            String nmpoli = resultSet.getString(helper_petugas_poli.KEY_POLI);
            String username = resultSet.getString(helper_petugas_poli.KEY_USERNAME);
          
            modelpetugas.addRow(new Object[]{nip, nama,idpoli,nmpoli,username});
            
            
        }
    }
    
     public void readRec_cariTransRM(String norm, String tgl) throws SQLException {

        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_v_trans.TB_NAME + " WHERE "
                + helper_v_trans.KEY_NO_RM + " =? AND " + helper_v_trans.KEY_TGL + " =?");

        preparedStatement.setString(1, norm);
        preparedStatement.setString(2, tgl);
        
        ResultSet resultSet = preparedStatement.executeQuery();

        int i = 0;

        while (resultSet.next()) {

            i++;

            String no = String.valueOf(i);
            String nonota = resultSet.getString(helper_v_trans.KEY_NO_NOTA);
            String rm = resultSet.getString(helper_v_trans.KEY_NO_RM);
            String nmp = resultSet.getString(helper_v_trans.KEY_NM_PASIEN);
            String petugas = resultSet.getString(helper_v_trans.KEY_PETUGAS);
            String tglc = resultSet.getString(helper_v_trans.KEY_TGL);
            String brg = resultSet.getString(helper_v_trans.KEY_NAMA_BRG);
            int jml = resultSet.getInt(helper_v_trans.KEY_JML);
            Double hargasat = resultSet.getDouble(helper_v_trans.KEY_HARGA_SATUAN);
            Double totalc = resultSet.getDouble(helper_v_trans.KEY_TOTAL);

            modelctrans.addRow(new Object[]{no, nonota, rm, nmp,petugas,tglc,brg,jml,hargasat,totalc});
        }
    }
    
    
     public void readRec_cariTrans(String nm_p, String tgl) throws SQLException {

        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_v_trans.TB_NAME + " WHERE "
                + helper_v_trans.KEY_NM_PASIEN + " like ? AND " + helper_v_trans.KEY_TGL + " =?");

        preparedStatement.setString(1, "%" + nm_p + "%");
        preparedStatement.setString(2, tgl);
        
        ResultSet resultSet = preparedStatement.executeQuery();

        int i = 0;

        while (resultSet.next()) {

            i++;

            String no = String.valueOf(i);
            String nonota = resultSet.getString(helper_v_trans.KEY_NO_NOTA);
            String rm = resultSet.getString(helper_v_trans.KEY_NO_RM);
            String nmp = resultSet.getString(helper_v_trans.KEY_NM_PASIEN);
            String petugas = resultSet.getString(helper_v_trans.KEY_PETUGAS);
            String tglc = resultSet.getString(helper_v_trans.KEY_TGL);
            String brg = resultSet.getString(helper_v_trans.KEY_NAMA_BRG);
            int jml = resultSet.getInt(helper_v_trans.KEY_JML);
            Double hargasat = resultSet.getDouble(helper_v_trans.KEY_HARGA_SATUAN);
            Double totalc = resultSet.getDouble(helper_v_trans.KEY_TOTAL);

            modelctrans.addRow(new Object[]{no, nonota, rm, nmp,petugas,tglc,brg,jml,hargasat,totalc});
        }
    }
    
       public void readRec_Allhistory() throws SQLException {

        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_v_trans.TB_NAME);

     
        ResultSet resultSet = preparedStatement.executeQuery();

        int i=0;
     
        while (resultSet.next()) {

            i++;

            String no = String.valueOf(i);
            String nonota = resultSet.getString(helper_v_trans.KEY_NO_NOTA);
            String rm = resultSet.getString(helper_v_trans.KEY_NO_RM);
            String nmp = resultSet.getString(helper_v_trans.KEY_NM_PASIEN);
            String petugas = resultSet.getString(helper_v_trans.KEY_PETUGAS);
            String tglc = resultSet.getString(helper_v_trans.KEY_TGL);
            String brg = resultSet.getString(helper_v_trans.KEY_NAMA_BRG);
            int jml = resultSet.getInt(helper_v_trans.KEY_JML);
            Double hargasat = resultSet.getDouble(helper_v_trans.KEY_HARGA_SATUAN);
            Double totalc = resultSet.getDouble(helper_v_trans.KEY_TOTAL);

            modelctrans.addRow(new Object[]{no, nonota, rm, nmp,petugas,tglc,brg,jml,hargasat,totalc});
            

        }

          
    } 
    
    
    
      public int readRec_count() throws SQLException {

        preparedStatement = connect.prepareStatement("SELECT COUNT(*) as tot FROM " + helper_trans.TB_NAME);

     
        ResultSet resultSet = preparedStatement.executeQuery();

        int r=0;
     
        while (resultSet.next()) {

            r = resultSet.getInt("tot");
            

        }

      return r;        
    } 
   
   public void Save_detail_trans(String no_nota, int jml, String satuan, double hargasatuan,String nmbrg,double total) {

        try {
            preparedStatement = connect.prepareStatement("insert into " + helper_detail.TB_NAME + " (" + helper_detail.KEY_NO_NOTA + "," + helper_detail.KEY_JML
                    + "," + helper_detail.KEY_SATUAN + "," + helper_detail.KEY_HARGA_SATUAN + "," + helper_detail.KEY_NAMA_BRG + "," + helper_detail.KEY_TOTAL + ") "
                    + " values (?,?,?,?,?,?)");

            preparedStatement.setString(1, no_nota);
            preparedStatement.setInt(2, jml);
            preparedStatement.setString(3, satuan);
            preparedStatement.setDouble(4, hargasatuan);
            preparedStatement.setString(5, nmbrg);
            preparedStatement.setDouble(6, total);
           
            preparedStatement.execute();


           
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Gagal Tersimpan");
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
   
   public void Save_log_tarifBPJS(String usernamep, String usernamev, String kode_tarif, String sp, String sv,String pathgbr) throws FileNotFoundException {

        try {
            preparedStatement = connect.prepareStatement("insert into " + helper_log_pengesahBPJS.TB_NAME + " (" + helper_log_pengesahBPJS.KEY_USERNAMEP + "," + helper_log_pengesahBPJS.KEY_USERNAMEV
                    + "," + helper_log_pengesahBPJS.KEY_KODE_TARIF + "," + helper_log_pengesahBPJS.KEY_STATP + "," + helper_log_pengesahBPJS.KEY_STATV + 
                    "," + helper_log_pengesahBPJS.KEY_TTD +") "
                    + " values (?,?,?,?,?,?)");

            InputStream inputStream = new FileInputStream(new File(pathgbr));
            
            preparedStatement.setString(1, usernamep);
            preparedStatement.setString(2, usernamev);
            preparedStatement.setString(3, kode_tarif);
            preparedStatement.setString(4, sp);
            preparedStatement.setString(5, sv);
            preparedStatement.setBlob(6, inputStream);
            preparedStatement.execute();


          //  JOptionPane.showMessageDialog(null, "Data Tersimpan");
          
          
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Gagal Tersimpan");
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
   
   
   public void Save_log_tarif(String usernamep, String usernamev, String kode_tarif, String sp, String sv,String pathgbr) throws FileNotFoundException {

        try {
            preparedStatement = connect.prepareStatement("insert into " + helper_log_pengesah.TB_NAME + " (" + helper_log_pengesah.KEY_USERNAMEP + "," + helper_log_pengesah.KEY_USERNAMEV
                    + "," + helper_log_pengesah.KEY_KODE_TARIF + "," + helper_log_pengesah.KEY_STATP + "," + helper_log_pengesah.KEY_STATV + 
                    "," + helper_log_pengesah.KEY_TTD +") "
                    + " values (?,?,?,?,?,?)");

            InputStream inputStream = new FileInputStream(new File(pathgbr));
            
            preparedStatement.setString(1, usernamep);
            preparedStatement.setString(2, usernamev);
            preparedStatement.setString(3, kode_tarif);
            preparedStatement.setString(4, sp);
            preparedStatement.setString(5, sv);
            preparedStatement.setBlob(6, inputStream);
            preparedStatement.execute();


          //  JOptionPane.showMessageDialog(null, "Data Tersimpan");
          
          
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Gagal Tersimpan");
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
   
   public void Update_petugas(String nip, String namap, int idpoli, String username){
    
        try {
            preparedStatement = connect.prepareStatement("update " + helper_petugas_poli.TB_NAME + " set "
                    + helper_petugas_poli.KEY_NIP+"=?," 
                    + helper_petugas_poli.KEY_NAMA+"=?," 
                    + helper_petugas_poli.KEY_ID_POLI+"=?," 
                    + helper_petugas_poli.KEY_USERNAME+"=?" 
                    + " where "
                    + helper_petugas_poli.KEY_NIP + "=?");

            String s = "0736";

            preparedStatement.setString(1, nip);
            preparedStatement.setString(2, namap);
            preparedStatement.setInt(3, idpoli);
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, nip);

            preparedStatement.executeUpdate();


            JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");

        } catch (SQLException ex) {
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public void Update_petugaspwd(String nip, String namap, int idpoli, String username, String pass){
    
        try {
            preparedStatement = connect.prepareStatement("update " + helper_petugas_poli.TB_NAME + " set "
                    + helper_petugas_poli.KEY_NIP+"=?," 
                    + helper_petugas_poli.KEY_NAMA+"=?," 
                    + helper_petugas_poli.KEY_ID_POLI+"=?," 
                    + helper_petugas_poli.KEY_USERNAME+"=?," 
                    + helper_petugas_poli.KEY_PASS + "=AES_ENCRYPT(?,?)" + " where "
                    + helper_petugas_poli.KEY_NIP + "=?");

            String s = "0736";

            preparedStatement.setString(1, nip);
            preparedStatement.setString(2, namap);
            preparedStatement.setInt(3, idpoli);
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, pass);
            preparedStatement.setBytes(6, s.getBytes());
            preparedStatement.setString(7, nip);

            preparedStatement.executeUpdate();


            JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");

        } catch (SQLException ex) {
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public void Save_petugas(String nip, String namap, int idpoli, String username, String pass) {

        try {
            preparedStatement = connect.prepareStatement("insert into " + helper_petugas_poli.TB_NAME + " (" + helper_petugas_poli.KEY_NIP + "," + helper_petugas_poli.KEY_NAMA
                    + "," + helper_petugas_poli.KEY_ID_POLI + "," + helper_petugas_poli.KEY_USERNAME + "," + helper_petugas_poli.KEY_PASS + ") "
                    + " values (?,?,?,?,AES_ENCRYPT(?,?))");

            
   
            String s = "0736";
            
            preparedStatement.setString(1, nip);
            preparedStatement.setString(2, namap);
            preparedStatement.setInt(3, idpoli);
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, pass);
            preparedStatement.setBytes(6,s.getBytes());
            preparedStatement.execute();

            JOptionPane.showMessageDialog(null, "Data Tersimpan");
            
        } catch (SQLException ex) {
           if(ex.getErrorCode() == 1062 ){
            //duplicate primary key 
             JOptionPane.showMessageDialog(null, "Gagal Tersimpan : Kode " + nip + " sudah pernah di input");
            }
            else{
            JOptionPane.showMessageDialog(null, "Gagal Tersimpan");
            }
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
   
   
   
   public void Save_trans(String no_nota, String no_rm, String nama_pasien, String catatan, String petugas) {

        try {
            preparedStatement = connect.prepareStatement("insert into " + helper_trans.TB_NAME + " (" + helper_trans.KEY_NO_NOTA + "," + helper_trans.KEY_NO_RM
                    + "," + helper_trans.KEY_NM_PASIEN + "," + helper_trans.KEY_CATATAN + "," + helper_trans.KEY_PETUGAS + ") "
                    + " values (?,?,?,?,?)");

            preparedStatement.setString(1, no_nota);
            preparedStatement.setString(2, no_rm);
            preparedStatement.setString(3, nama_pasien);
            preparedStatement.setString(4, catatan);
            preparedStatement.setString(5, petugas);
            
            preparedStatement.execute();


            JOptionPane.showMessageDialog(null, "Data Tersimpan");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal Tersimpan");
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
   
   public void updateTarifStatusLogPengesahBPJS(String kode_tarif,String status_pengesah,String username,String path) throws FileNotFoundException {
    
        try {
            preparedStatement = connect.prepareStatement("update " + helper_log_pengesahBPJS.TB_NAME + " set " 
                    + helper_log_pengesahBPJS.KEY_STATP+"=?," 
                    + helper_log_pengesahBPJS.KEY_USERNAMEP+"=?,"
                    + helper_log_pengesahBPJS.KEY_TTDP+"=?" 
                    +" where "+ helper_tarif.KEY_KODE_TARIF + "=?"+" AND "+helper_tarif.KEY_STATUS_PENGESAH+"=?");
            
            InputStream inputStream = new FileInputStream(new File(path));
              
            preparedStatement.setString(1, status_pengesah);
            preparedStatement.setString(2, username);
            preparedStatement.setBlob(3, inputStream);
            preparedStatement.setString(4, kode_tarif);
            preparedStatement.setString(5, "pending");
            preparedStatement.executeUpdate();
             //JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");
        } catch (SQLException ex) {
//             if(ex.getErrorCode() == 1062 ){
//            //duplicate primary key 
//             JOptionPane.showMessageDialog(null, "Gagal Update : Kode " + kode_tarif + " sudah pernah di input");
//            }
//            else{
//            JOptionPane.showMessageDialog(null, "Gagal Update");
//            }
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
   public void updateTarifStatusLogPengesah(String kode_tarif,String status_pengesah,String username,String path) throws FileNotFoundException {
    
        try {
            preparedStatement = connect.prepareStatement("update " + helper_log_pengesah.TB_NAME + " set " 
                    + helper_log_pengesah.KEY_STATP+"=?," 
                    + helper_log_pengesah.KEY_USERNAMEP+"=?,"
                    + helper_log_pengesah.KEY_TTDP+"=?" 
                    +" where "+ helper_tarif.KEY_KODE_TARIF + "=?"+" AND "+helper_tarif.KEY_STATUS_PENGESAH+"=?");
            
            InputStream inputStream = new FileInputStream(new File(path));
              
            preparedStatement.setString(1, status_pengesah);
            preparedStatement.setString(2, username);
            preparedStatement.setBlob(3, inputStream);
            preparedStatement.setString(4, kode_tarif);
            preparedStatement.setString(5, "pending");
            preparedStatement.executeUpdate();
             //JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");
        } catch (SQLException ex) {
//             if(ex.getErrorCode() == 1062 ){
//            //duplicate primary key 
//             JOptionPane.showMessageDialog(null, "Gagal Update : Kode " + kode_tarif + " sudah pernah di input");
//            }
//            else{
//            JOptionPane.showMessageDialog(null, "Gagal Update");
//            }
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

   public void updateTarifStatusBPJS(String kode_tarif,String status_pengesah,String status_verif) {
    
        try {
            preparedStatement = connect.prepareStatement("update " + helper_tarif.TB_NAME + " set " 
                    + helper_tarif.KEY_STATUS_PENGESAH_BPJS+"=?," 
                    + helper_tarif.KEY_STATUS_VERIF_BPJS+"=?"
                    +" where "+ helper_tarif.KEY_KODE_TARIF + "=?");
            
           
            preparedStatement.setString(1, status_pengesah);
            preparedStatement.setString(2, status_verif);
            preparedStatement.setString(3, kode_tarif);
             
            preparedStatement.executeUpdate();
             //JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");
        } catch (SQLException ex) {
//             if(ex.getErrorCode() == 1062 ){
//            //duplicate primary key 
//             JOptionPane.showMessageDialog(null, "Gagal Update : Kode " + kode_tarif + " sudah pernah di input");
//            }
//            else{
//            JOptionPane.showMessageDialog(null, "Gagal Update");
//            }
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
   
   public void updateTarifStatus(String kode_tarif,String status_pengesah,String status_verif) {
    
        try {
            preparedStatement = connect.prepareStatement("update " + helper_tarif.TB_NAME + " set " 
                    + helper_tarif.KEY_STATUS_PENGESAH+"=?," 
                    + helper_tarif.KEY_STATUS_VERIF+"=?"
                    +" where "+ helper_tarif.KEY_KODE_TARIF + "=?");
            
           
            preparedStatement.setString(1, status_pengesah);
            preparedStatement.setString(2, status_verif);
            preparedStatement.setString(3, kode_tarif);
             
            preparedStatement.executeUpdate();
             //JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");
        } catch (SQLException ex) {
//             if(ex.getErrorCode() == 1062 ){
//            //duplicate primary key 
//             JOptionPane.showMessageDialog(null, "Gagal Update : Kode " + kode_tarif + " sudah pernah di input");
//            }
//            else{
//            JOptionPane.showMessageDialog(null, "Gagal Update");
//            }
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
   public void updateTarifBPJS(String kode_tarif, String nama_tindakan, double tarif_tindakan, int presentase_dr,int presentase_rs,int presentase_sarana
                         ,int id_poli,int id_status,String status_pengesah,String status_verif,String keterangan,String kelas,double tarif_tindakanbpjs, int presentase_drbpjs,int presentase_rsbpjs,int presentase_saranabpjs,String statbpjs
                        ,String status_pengesahbpjs,String status_verifbpjs,int pilih) {
    
        try {
         
          if(pilih==1){  
            preparedStatement = connect.prepareStatement("update " + helper_tarif.TB_NAME + " set " 
                    + helper_tarif.KEY_NAMA_TINDAKAN+"=?,"
                    + helper_tarif.KEY_TARIF_TINDAKAN+"=?,"
                    + helper_tarif.KEY_PRESENTASE_DR+"=?,"
                    + helper_tarif.KEY_PRESENTASE_RS+"=?," 
                    + helper_tarif.KEY_PRESENTASE_SARANA+"=?," 
                    + helper_tarif.KEY_ID_POLI+"=?," 
                    + helper_tarif.KEY_ID_STATUS+"=?," 
                    + helper_tarif.KEY_STATUS_PENGESAH+"=?," 
                    + helper_tarif.KEY_STATUS_VERIF+"=?,"
                    + helper_tarif.KEY_KETERANGAN+"=?,"
                    + helper_tarif.KEY_KELAS+"=?,"
                    + helper_tarif.KEY_TARIF_TINDAKAN_BPJS+"=?,"
                    + helper_tarif.KEY_PRESENTASE_DR_BPJS+"=?,"
                    + helper_tarif.KEY_PRESENTASE_RS_BPJS+"=?," 
                    + helper_tarif.KEY_PRESENTASE_SARANA_BPJS+"=?,"
                    + helper_tarif.KEY_STATUS_BPJS+"=?,"
                    + helper_tarif.KEY_STATUS_PENGESAH_BPJS+"=?," 
                    + helper_tarif.KEY_STATUS_VERIF_BPJS+"=?"
                    +" where "+ helper_tarif.KEY_KODE_TARIF + "=?");
            
            preparedStatement.setString(19, kode_tarif);
            preparedStatement.setString(1, nama_tindakan);
            preparedStatement.setDouble(2, tarif_tindakan);
            preparedStatement.setInt(3, presentase_dr);
            preparedStatement.setInt(4, presentase_rs);
            preparedStatement.setInt(5, presentase_sarana);
            preparedStatement.setInt(6, id_poli);
            preparedStatement.setInt(7, id_status);
            preparedStatement.setString(8, status_pengesah);
            preparedStatement.setString(9, status_verif);
            preparedStatement.setString(10, keterangan);
            preparedStatement.setString(11, kelas);
            preparedStatement.setDouble(12, tarif_tindakanbpjs);
            preparedStatement.setInt(13, presentase_drbpjs);
            preparedStatement.setInt(14, presentase_rsbpjs);
            preparedStatement.setInt(15, presentase_saranabpjs);
            preparedStatement.setString(16, statbpjs);
            preparedStatement.setString(17, status_pengesahbpjs);
            preparedStatement.setString(18, status_verifbpjs);
          }
          else{
            preparedStatement = connect.prepareStatement("update " + helper_tarif.TB_NAME + " set " 
//                    + helper_tarif.KEY_NAMA_TINDAKAN+"=?,"
//                    + helper_tarif.KEY_TARIF_TINDAKAN+"=?,"
//                    + helper_tarif.KEY_PRESENTASE_DR+"=?,"
//                    + helper_tarif.KEY_PRESENTASE_RS+"=?," 
//                    + helper_tarif.KEY_PRESENTASE_SARANA+"=?," 
//                    + helper_tarif.KEY_ID_POLI+"=?," 
//                    + helper_tarif.KEY_ID_STATUS+"=?," 
//                    + helper_tarif.KEY_STATUS_PENGESAH+"=?," 
//                    + helper_tarif.KEY_STATUS_VERIF+"=?,"
//                    + helper_tarif.KEY_KETERANGAN+"=?,"
//                    + helper_tarif.KEY_KELAS+"=?"
                    + helper_tarif.KEY_TARIF_TINDAKAN_BPJS+"=?,"
                    + helper_tarif.KEY_PRESENTASE_DR_BPJS+"=?,"
                    + helper_tarif.KEY_PRESENTASE_RS_BPJS+"=?," 
                    + helper_tarif.KEY_PRESENTASE_SARANA_BPJS+"=?,"
                    + helper_tarif.KEY_STATUS_BPJS+"=?,"
                    + helper_tarif.KEY_STATUS_PENGESAH_BPJS+"=?," 
                    + helper_tarif.KEY_STATUS_VERIF_BPJS+"=?,"
                    + helper_tarif.KEY_KODE_BPJS+"=?" 
                    +" where "+ helper_tarif.KEY_KODE_TARIF + "=?");
            
            preparedStatement.setString(9, kode_tarif);
//            preparedStatement.setString(1, nama_tindakan);
//            preparedStatement.setDouble(2, tarif_tindakan);
//            preparedStatement.setInt(3, presentase_dr);
//            preparedStatement.setInt(4, presentase_rs);
//            preparedStatement.setInt(5, presentase_sarana);
//            preparedStatement.setInt(6, id_poli);
//            preparedStatement.setInt(7, id_status);
//            preparedStatement.setString(8, status_pengesah);
//            preparedStatement.setString(9, status_verif);
//            preparedStatement.setString(10, keterangan);
//            preparedStatement.setString(11, kelas);
            preparedStatement.setDouble(1, tarif_tindakanbpjs);
            preparedStatement.setInt(2, presentase_drbpjs);
            preparedStatement.setInt(3, presentase_rsbpjs);
            preparedStatement.setInt(4, presentase_saranabpjs);
            preparedStatement.setString(5, statbpjs);
            preparedStatement.setString(6, status_pengesahbpjs);
            preparedStatement.setString(7, status_verifbpjs);
             preparedStatement.setString(8, kode_tarif+"-BPJS");
          }
          
            preparedStatement.executeUpdate();
             JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");
        } catch (SQLException ex) {
             if(ex.getErrorCode() == 1062 ){
            //duplicate primary key 
             JOptionPane.showMessageDialog(null, "Gagal Update : Kode " + kode_tarif + " sudah pernah di input");
            }
            else{
            JOptionPane.showMessageDialog(null, "Gagal Update");
            }
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
   
    public void updateTarif(String kode_tarif, String nama_tindakan, double tarif_tindakan, int presentase_dr,int presentase_rs,int presentase_sarana
                         ,int id_poli,int id_status,String status_pengesah,String status_verif,String keterangan,String kelas) {
    
        try {
            preparedStatement = connect.prepareStatement("update " + helper_tarif.TB_NAME + " set " 
                    + helper_tarif.KEY_NAMA_TINDAKAN+"=?,"
                    + helper_tarif.KEY_TARIF_TINDAKAN+"=?,"
                    + helper_tarif.KEY_PRESENTASE_DR+"=?,"
                    + helper_tarif.KEY_PRESENTASE_RS+"=?," 
                    + helper_tarif.KEY_PRESENTASE_SARANA+"=?," 
                    + helper_tarif.KEY_ID_POLI+"=?," 
                    + helper_tarif.KEY_ID_STATUS+"=?," 
                    + helper_tarif.KEY_STATUS_PENGESAH+"=?," 
                    + helper_tarif.KEY_STATUS_VERIF+"=?,"
                    + helper_tarif.KEY_KETERANGAN+"=?,"
                     + helper_tarif.KEY_KELAS+"=?"
                    +" where "+ helper_tarif.KEY_KODE_TARIF + "=?");
            
            preparedStatement.setString(12, kode_tarif);
            preparedStatement.setString(1, nama_tindakan);
            preparedStatement.setDouble(2, tarif_tindakan);
            preparedStatement.setInt(3, presentase_dr);
            preparedStatement.setInt(4, presentase_rs);
            preparedStatement.setInt(5, presentase_sarana);
            preparedStatement.setInt(6, id_poli);
            preparedStatement.setInt(7, id_status);
            preparedStatement.setString(8, status_pengesah);
            preparedStatement.setString(9, status_verif);
            preparedStatement.setString(10, keterangan);
             preparedStatement.setString(11, kelas);
            preparedStatement.executeUpdate();
             JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");
        } catch (SQLException ex) {
             if(ex.getErrorCode() == 1062 ){
            //duplicate primary key 
             JOptionPane.showMessageDialog(null, "Gagal Update : Kode " + kode_tarif + " sudah pernah di input");
            }
            else{
            JOptionPane.showMessageDialog(null, "Gagal Update");
            }
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
//    //save master tarif
   public void Save_tarif(String kode_tarif, String nama_tindakan, double tarif_tindakan, int presentase_dr,int presentase_rs,int presentase_sarana
                         ,int id_poli,int id_status,String status_pengesah,String status_verif,String keterangan,String kelas,double tarif_tindakan1, 
                          int presentase_dr1,int presentase_rs1,int presentase_sarana1,String statusbpjs,String status_pengesahbpjs,String status_verifbpjs,String kode_tarifbpjs) {

        try {
            preparedStatement = connect.prepareStatement("insert into " + helper_tarif.TB_NAME + " (" + helper_tarif.KEY_KODE_TARIF + "," + helper_tarif.KEY_NAMA_TINDAKAN
                    + "," + helper_tarif.KEY_TARIF_TINDAKAN + "," + helper_tarif.KEY_PRESENTASE_DR + "," + helper_tarif.KEY_PRESENTASE_RS 
                    + "," + helper_tarif.KEY_PRESENTASE_SARANA + "," + helper_tarif.KEY_ID_POLI + "," + helper_tarif.KEY_ID_STATUS + ","
                    + helper_tarif.KEY_STATUS_PENGESAH + "," 
                    + helper_tarif.KEY_STATUS_VERIF + "," 
                    + helper_tarif.KEY_KETERANGAN + ","
                    + helper_tarif.KEY_KELAS + ","
                    + helper_tarif.KEY_TARIF_TINDAKAN_BPJS + ","
                    + helper_tarif.KEY_PRESENTASE_DR_BPJS + ","
                    + helper_tarif.KEY_PRESENTASE_RS_BPJS + ","
                    + helper_tarif.KEY_PRESENTASE_SARANA_BPJS + ","
                    + helper_tarif.KEY_STATUS_BPJS + ","
                    + helper_tarif.KEY_KODE_BPJS + ","
                    + helper_tarif.KEY_STATUS_PENGESAH_BPJS + "," 
                    + helper_tarif.KEY_STATUS_VERIF_BPJS  
                    + ") "
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            preparedStatement.setString(1, kode_tarif);
            preparedStatement.setString(2, nama_tindakan);
            preparedStatement.setDouble(3, tarif_tindakan);
            preparedStatement.setInt(4, presentase_dr);
            preparedStatement.setInt(5, presentase_rs);
            preparedStatement.setInt(6, presentase_sarana);
            preparedStatement.setInt(7, id_poli);
            preparedStatement.setInt(8, id_status);
            preparedStatement.setString(9, status_pengesah);
            preparedStatement.setString(10, status_verif);
            preparedStatement.setString(11, keterangan);
            preparedStatement.setString(12, kelas);
            preparedStatement.setDouble(13, tarif_tindakan1);
            preparedStatement.setInt(14, presentase_dr1);
            preparedStatement.setInt(15, presentase_rs1);
            preparedStatement.setInt(16, presentase_sarana1);
            preparedStatement.setString(17, statusbpjs);
            preparedStatement.setString(18, kode_tarifbpjs);
            preparedStatement.setString(19, status_pengesahbpjs);
            preparedStatement.setString(20, status_verifbpjs);
            
            preparedStatement.execute();


            JOptionPane.showMessageDialog(null, "Data Tersimpan");
        } catch (SQLException ex) {
            if(ex.getErrorCode() == 1062 ){
            //duplicate primary key 
             JOptionPane.showMessageDialog(null, "Gagal Tersimpan : Kode " + kode_tarif + " sudah pernah di input");
            }
            else{
            JOptionPane.showMessageDialog(null, "Gagal Tersimpan");
            }
            
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
    
   //save master tarif
//   public void Save_tarif(String kode_tarif, String nama_tindakan, double tarif_tindakan, int presentase_dr,int presentase_rs,int presentase_sarana
//                         ,int id_poli,int id_status,String status_pengesah,String status_verif,String keterangan,String kelas) {
//
//        try {
//            preparedStatement = connect.prepareStatement("insert into " + helper_tarif.TB_NAME + " (" + helper_tarif.KEY_KODE_TARIF + "," + helper_tarif.KEY_NAMA_TINDAKAN
//                    + "," + helper_tarif.KEY_TARIF_TINDAKAN + "," + helper_tarif.KEY_PRESENTASE_DR + "," + helper_tarif.KEY_PRESENTASE_RS 
//                    + "," + helper_tarif.KEY_PRESENTASE_SARANA + "," + helper_tarif.KEY_ID_POLI + "," + helper_tarif.KEY_ID_STATUS + ","
//                    + helper_tarif.KEY_STATUS_PENGESAH + "," + helper_tarif.KEY_STATUS_VERIF + "," +helper_tarif.KEY_KETERANGAN + "," +helper_tarif.KEY_KELAS + ") "
//                    + " values (?,?,?,?,?,?,?,?,?,?,?,?)");
//
//            preparedStatement.setString(1, kode_tarif);
//            preparedStatement.setString(2, nama_tindakan);
//            preparedStatement.setDouble(3, tarif_tindakan);
//            preparedStatement.setInt(4, presentase_dr);
//            preparedStatement.setInt(5, presentase_rs);
//            preparedStatement.setInt(6, presentase_sarana);
//            preparedStatement.setInt(7, id_poli);
//            preparedStatement.setInt(8, id_status);
//            preparedStatement.setString(9, status_pengesah);
//            preparedStatement.setString(10, status_verif);
//            preparedStatement.setString(11, keterangan);
//            preparedStatement.setString(12, kelas);
////            preparedStatement.setDouble(13, tarif_tindakan);
////            preparedStatement.setInt(14, presentase_dr);
////            preparedStatement.setInt(15, presentase_rs);
////            preparedStatement.setInt(16, presentase_sarana);
//            
//            preparedStatement.execute();
//
//
//            JOptionPane.showMessageDialog(null, "Data Tersimpan");
//        } catch (SQLException ex) {
//            if(ex.getErrorCode() == 1062 ){
//            //duplicate primary key 
//             JOptionPane.showMessageDialog(null, "Gagal Tersimpan : Kode " + kode_tarif + " sudah pernah di input");
//            }
//            else{
//            JOptionPane.showMessageDialog(null, "Gagal Tersimpan");
//            }
//            
//            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//
//
//    }
   
    public void Cek(String pl,String s) throws SQLException {

       if(s.equals("p")) 
       {
        preparedStatement = connect.prepareStatement("select *, CAST(AES_DECRYPT(pass, '0736') AS CHAR(255)) xcd from " + helper_pengesah.TB_NAME + " where "
                + helper_pengesah.KEY_USERNAME + " = ?");
       }
       else if(s.equals("v")){
       preparedStatement = connect.prepareStatement("select *, CAST(AES_DECRYPT(pass, '0736') AS CHAR(255)) xcd from " + helper_verif.TB_NAME + " where "
                + helper_verif.KEY_USERNAME + " = ?");
       }

        preparedStatement.setString(1, pl);


        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            psm = resultSet.getString("xcd");
            System.out.println(psm);
        }

        if (!psm.isEmpty()) {

            usm = pl;

//            StrPr m = new StrPr();
//
//            m.setCredentials(pl, tmp);


        }
    }
     
    
    public void DelRecHistory(String kode,String tgl) throws SQLException  {
  
        
        try {
            preparedStatement = connect.prepareStatement("delete from " + helper_unit_detail.TB_NAME + " where " 
                    + helper_unit_detail.KEY_KODE_TARIF + "=? and "
                    + helper_unit_detail.KEY_TGL + "=?"
                    );
            preparedStatement.setString(1, kode);
            preparedStatement.setString(2, tgl);
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }
    
      
        
       
        
    }
    
     public void DelRec(String kode) throws SQLException  {
  
        
        try {
            preparedStatement = connect.prepareStatement("delete from " + helper_tarif.TB_NAME + " where " + helper_tarif.KEY_KODE_TARIF + "=?");
            preparedStatement.setString(1, kode);
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }
    
      
      
       
        
    }
        
     public void DelRecPetugas(String kode) throws SQLException  {
  
        
        try {
            preparedStatement = connect.prepareStatement("delete from " + helper_petugas_poli.TB_NAME + " where " + helper_petugas_poli.KEY_NIP + "=?");
        
            preparedStatement.setString(1, kode);
            
            preparedStatement.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Hapus Gagal!");
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }
    
      
       
        
    }
   public void CekPetugas(String pl) throws SQLException {


        preparedStatement = connect.prepareStatement("select *, CAST(AES_DECRYPT(pass, '0736') AS CHAR(255)) xcd from " + helper_petugas_poli.TB_NAME + " where "
                + helper_petugas_poli.KEY_USERNAME + " = ?");

        preparedStatement.setString(1, pl);


        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            psm = resultSet.getString("xcd");
            namapetugaslogin=resultSet.getString(helper_petugas_poli.KEY_NAMA);
            //namapoli=resultSet.getString(helper_petugas_poli.KEY_POLI);
            System.out.println(psm);
        }

        if (!psm.isEmpty()) {

            usm = pl;

//            StrPr m = new StrPr();
//
//            m.setCredentials(pl, tmp);


        }

    }
   
  public void CetakNota(String nonota,String tampil) throws JRException {

        InputStream is = null;
        is = getClass().getResourceAsStream("rpt_cetak.jrxml");

        //set parameters
        Map map = new HashMap();
        map.put("nonota", nonota);
       // map.put("Imgpath",null);

        JasperReport jr = JasperCompileManager.compileReport(is);

        JasperPrint jp = JasperFillManager.fillReport(jr, map, connect);
        //jp.setPageWidth(200);
        //jp.setPageHeight(180);
       // jp.setOrientation(OrientationEnum.PORTRAIT);

      
        JasperPrintManager.printReport(jp,false);
       
        if(tampil.equals("ok")){
            JasperViewer.viewReport(jp, false);
        }
       
    }    
    
 
}
