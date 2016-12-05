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
import java.sql.Statement;
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
    
    public static HashMap<Integer,Integer> cekvalpilih=new HashMap<Integer,Integer>();
    
    public static String usm = "";
    public static String psm = "";
    
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
     String[] caritrans_title = new String[]{"No.","No. Nota","No. RM","Nama Pasien","Petugas","Tgl","Nama Barang","Jml", "Harga Satuan", "Total"};
    
     String[] tarif_title= new String[]{"Id", "Nama Tindakan","Tarif Tindakan","% RS.","% Dr.",
         "% Sarana","Nama Poli","Status","Keterangan","id poli","id status"};
     
      String[] tarif_title_log= new String[]{"Id", "Nama Tindakan","Tarif Tindakan","% RS.","% Dr.",
         "% Sarana","Nama Poli","Status","Pengesah","Verif","pilih"};
      
     final Class[] columnClass = new Class[] {
    String.class, String.class, Double.class, Double.class, Double.class, Double.class, String.class
             , String.class, String.class, String.class, Boolean.class
};
     String[] poli_title= new String[]{"Id", "Nama Poli"}; 
     
     String[] status_title= new String[]{"Id", "Nama Status"}; 
     
     
     
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
    public Crud_local() throws Exception{
      ConDb();
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
          
            modeltarif.addRow(new Object[]{kodetarif, nmtindakan,tarif,presrs,presdr,pressarana,poli,status,ket,id_poli,id_status});
            
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
          
            modeltarif.addRow(new Object[]{kodetarif, nmtindakan,tarif,presrs,presdr,pressarana,poli,status,ket,id_poli,id_status});
            
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
   
   public void updateTarifStatusLogPengesah(String kode_tarif,String status_pengesah,String username) {
    
        try {
            preparedStatement = connect.prepareStatement("update " + helper_log_pengesah.TB_NAME + " set " 
                    + helper_log_pengesah.KEY_STATP+"=?," 
                    + helper_log_pengesah.KEY_USERNAMEP+"=?" 
                    +" where "+ helper_tarif.KEY_KODE_TARIF + "=?");
            
           
            preparedStatement.setString(1, status_pengesah);
            preparedStatement.setString(2, username);
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
   
   
    public void updateTarif(String kode_tarif, String nama_tindakan, double tarif_tindakan, int presentase_dr,int presentase_rs,int presentase_sarana
                         ,int id_poli,int id_status,String status_pengesah,String status_verif,String keterangan) {
    
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
                    + helper_tarif.KEY_KETERANGAN+"=?"
                    +" where "+ helper_tarif.KEY_KODE_TARIF + "=?");
            
            preparedStatement.setString(11, kode_tarif);
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
   
   //save master tarif
   public void Save_tarif(String kode_tarif, String nama_tindakan, double tarif_tindakan, int presentase_dr,int presentase_rs,int presentase_sarana
                         ,int id_poli,int id_status,String status_pengesah,String status_verif,String keterangan) {

        try {
            preparedStatement = connect.prepareStatement("insert into " + helper_tarif.TB_NAME + " (" + helper_tarif.KEY_KODE_TARIF + "," + helper_tarif.KEY_NAMA_TINDAKAN
                    + "," + helper_tarif.KEY_TARIF_TINDAKAN + "," + helper_tarif.KEY_PRESENTASE_DR + "," + helper_tarif.KEY_PRESENTASE_RS 
                    + "," + helper_tarif.KEY_PRESENTASE_SARANA + "," + helper_tarif.KEY_ID_POLI + "," + helper_tarif.KEY_ID_STATUS + ","
                    + helper_tarif.KEY_STATUS_PENGESAH + "," + helper_tarif.KEY_STATUS_VERIF + "," +helper_tarif.KEY_KETERANGAN + ") "
                    + " values (?,?,?,?,?,?,?,?,?,?,?)");

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
