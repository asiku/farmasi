/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farmasi;

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

/**
 *
 * @author jengcool
 */
public class Crud_local extends DBKoneksi_local {
    
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
     String[] caritrans_title = new String[]{"No.","No. Nota","No. RM","Nama Pasien","Petugas","Tgl","Nama Barang","Jml", "Harga Satuan", "Total"};
    
     String[] poli_title= new String[]{"Id", "Nama Poli"}; 
     
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
            Logger.getLogger(Crud_farmasi.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Crud_farmasi.class.getName()).log(Level.SEVERE, null, ex);
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
