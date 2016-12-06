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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jengcool
 */
public class Crud_farmasi extends DBkoneksi {

    public Crud_farmasi() throws Exception {
        ConDb();
    }

   // public static int rcount;
    
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    String[] reg_title = new String[]{"No.", "No. RM", "Nama Pasien", "Tanggal"};

    String[] brg_title = new String[]{ "Kode obat", "Nama Obat", "Satuan"};
    
    String[] petugas_title = new String[]{"Nip", "Nama Petugas"};
    

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

    public DefaultTableModel modelreg = new DefaultTableModel(reg_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };
    
    
 
    public String readRec_pasien(String norm) throws SQLException {

         String nmp="";
       
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

    preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_brg.TB_NAME + " WHERE "
                    + helper_brg.KEY_NAMA_BRG + " like ?");

            preparedStatement.setString(1, "%" + namabrg + "%");
        

        ResultSet resultSet = preparedStatement.executeQuery();

        int i = 0;

        while (resultSet.next()) {

            i++;

            String no = String.valueOf(i);
            String kodeobat = resultSet.getString(helper_brg.KEY_KODE_BRG);
            String nm_pasien = resultSet.getString(helper_brg.KEY_NAMA_BRG);
            String satuan = resultSet.getString(helper_brg.KEY_SATUAN);

            modelbrg.addRow(new Object[]{ kodeobat, nm_pasien, satuan});
        }
    }
    

  public void readRec_petugasF(String nm) throws SQLException{
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

    
    public void readRec_brg(String namabrg) throws SQLException {

        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_brg.TB_NAME);

     
        ResultSet resultSet = preparedStatement.executeQuery();

        int i = 0;

        while (resultSet.next()) {

            i++;

            String no = String.valueOf(i);
            String kodeobat = resultSet.getString(helper_brg.KEY_KODE_BRG);
            String nm_pasien = resultSet.getString(helper_brg.KEY_NAMA_BRG);
            String satuan = resultSet.getString(helper_brg.KEY_SATUAN);

            modelbrg.addRow(new Object[]{ kodeobat, nm_pasien, satuan});
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
    
}
