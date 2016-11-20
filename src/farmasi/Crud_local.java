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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
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
    
    
    public Crud_local() throws Exception{
      ConDb();
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
   
   public void Save_detail_trans(String no_nota, int jml, String satuan, double hargasatuan,String nmbrg) {

        try {
            preparedStatement = connect.prepareStatement("insert into " + helper_detail.TB_NAME + " (" + helper_detail.KEY_NO_NOTA + "," + helper_detail.KEY_JML
                    + "," + helper_detail.KEY_SATUAN + "," + helper_detail.KEY_HARGA_SATUAN + "," + helper_detail.KEY_NAMA_BRG+ ") "
                    + " values (?,?,?,?,?)");

            preparedStatement.setString(1, no_nota);
            preparedStatement.setInt(2, jml);
            preparedStatement.setString(3, satuan);
            preparedStatement.setDouble(4, hargasatuan);
            preparedStatement.setString(5, nmbrg);
           
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
    
  public void CetakNota(String nonota) throws JRException {

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
        jp.setOrientation(OrientationEnum.PORTRAIT);

        JasperViewer.viewReport(jp, false);

    }    
    
}
