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
import java.text.DecimalFormat;

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
    
    
     String[] caritrans_title = new String[]{"No.","No. Nota","Nama Barang","Jml", "Harga Satuan", "Total","Kategori","No. RM","Nama Pasien","Karyawan","Jual Bebas","Tgl","Petugas"};
    
     String[] tarif_title= new String[]{"Id", "Nama Tindakan","Tarif Tindakan","% RS.","% Dr.",
         "% Sarana","Nama Poli","Status","Keterangan","id poli","id status","Status Pengesah","Status Verif","Kelas","Tarif Tindakan BPJS","% RS. BPJS","% Dr. BPJS",
         "% Sarana BPJS","Status BPJS","Kode BPJS","status_pengesah_bpjs","status_verif_bpjs","Status Tindakan"};
     
      String[] tarif_title_log= new String[]{"Id", "Nama Tindakan","Tarif Tindakan","% RS.","% Dr.",
         "% Sarana","Nama Poli","Status","Pengesah","Verif","pilih"};
      
      String[] tarif_title_logBPJS= new String[]{"Id", "Nama Tindakan","Tarif Tindakan","% RS.","% Dr.",
         "% Sarana","Nama Poli","Status","Pengesah","Verif","pilih"};
      
    
      
    String[] unit_detail = new String[]{"No Rawat", "Kode Tindakan", "Tindakan", "Petugas", "Tgl Tindakan", "Username"};
    
            
    String[] biaya_tindakan_titlebpjs = new String[]{"No Rawat", "Kode Tarif", "Nama Tindakan", "Tarif Tindakan BPJS","rsbpjs","drbpjs","saranabpjs","nip"}; 
    
    String[] biaya_tindakan_title = new String[]{"No Rawat", "Kode Tarif", "Nama Tindakan", "Tarif Tindakan", "Tarif Tindakan BPJS","rs","dr","sarana","rsbpjs","drbpjs","saranabpjs","nip","Biaya Reg"};

    String[] periksa_lab_title = new String[]{"No Rawat","kode tarif","Nama Tindakan","Tarif","Tarif BPJS"
                                            ,"rs","dr","sarana","rsbpjs","drbpjs","saranabpjs","nip","status_pengesah","Status verif","Status_pengesah bpjs","Status verif bpjs"};
   
     String[] periksa_lab_titlebpjs = new String[]{"No Rawat","kode tarif","Nama Tindakan","Tarif BPJS",
                                                   "rsbpjs","drbpjs","saranabpjs","Status verif bpjs","nip","status_pengesah","Status verif","Status_pengesah bpjs"};
    
     
     public DefaultTableModel modelperiksalabbpjs = new DefaultTableModel(periksa_lab_titlebpjs, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };         
    
   String[] brg_title = new String[]{"Kode Barang", "Nama", "Harga Jual", "Harga Jual Karyawan","Harga Beli", "Kategori"}; 
  
   String[] transfarmasi_title = new String[]{"No RM", 
       "Nama Pasien", "Cara beli","No rawat", "Nama Jual Bebas","Nik","Nama Karyawan","Status Cetak","Petugas","Catatan","No Nota",
   "kd pj","nama pj","No Sep","tgl"}; 
   
  
    String[] transfarmasikaryawanjualbebas_title = new String[]{"No Nota","Nik","Nama Karyawan"
            ,"Cara beli","Status Cetak","Petugas","Catatan","tgl","Nama Jual Bebas"}; 
   
   
     String[] trans_title = new String[]{"No.", "Jml", "Nama Barang", "Harga Satuan", "Total"};
    
//     String[] breg_title = new String[]{"No Rawat", "Biaya Reg"};
//     
//     public DefaultTableModel  modelbiayareg= new DefaultTableModel(breg_title, 0) {
//         public boolean isCellEditable(int row, int column) {
//            return false;
//
//        }
//     };
     
     public DefaultTableModel modeltrans = new DefaultTableModel(trans_title, 0) {
         public boolean isCellEditable(int row, int column) {
            return false;

        }
     };
     
   public DefaultTableModel modeltransfarmasikaryawanjualbebas = new DefaultTableModel(transfarmasikaryawanjualbebas_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };         
   
                                                   
   public DefaultTableModel modeltransfarmasi = new DefaultTableModel(transfarmasi_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };         
           
           
   public DefaultTableModel modelbrg = new DefaultTableModel(brg_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };         
            
   public DefaultTableModel modelperiksalab = new DefaultTableModel(periksa_lab_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };         
   
   
    public DefaultTableModel modelbiayatindakanbpjs = new DefaultTableModel(biaya_tindakan_titlebpjs, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };  
   
   
    public DefaultTableModel modelbiayatindakan = new DefaultTableModel(biaya_tindakan_title, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };  
      
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
    
    public void readRec_transfarmasidetail(String nota) throws SQLException {

        
//        public static String TB_NAME = "v_trans";
//    public static String KEY_NO_NOTA = "no_nota";
//    public static String KEY_NO_RM = "no_rm";
//    public static String KEY_NM_PASIEN = "nama_pasien";
//    public static String KEY_PETUGAS = "petugas";
//    public static String KEY_JML = "jml";
//    public static String KEY_SATUAN = "satuan";
//    public static String KEY_HARGA_SATUAN = "harga_satuan";
//    public static String KEY_NAMA_BRG = "nama_barang";
//    public static String KEY_TOTAL = "total";
//    public static String KEY_TGL = "tgl";
        
         
        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_v_trans.TB_NAME + " WHERE "
                + helper_v_trans.KEY_NO_NOTA + " = ? ");

        preparedStatement.setString(1, nota);    
          
       
    
        ResultSet resultSet = preparedStatement.executeQuery();

       int i=0;
     
        while (resultSet.next()) {

            i++;
//{"No.", "Jml", "Nama Barang", "Harga Satuan", "Total"}
            String no = String.valueOf(i);
//            String nonota = resultSet.getString(helper_v_trans.KEY_NO_NOTA);
//            String rm = resultSet.getString(helper_v_trans.KEY_NO_RM);
//            String nmp = resultSet.getString(helper_v_trans.KEY_NM_PASIEN);
//            String petugas = resultSet.getString(helper_v_trans.KEY_PETUGAS);
//            String tglc = resultSet.getString(helper_v_trans.KEY_TGL);
            String brg = resultSet.getString(helper_v_trans.KEY_NAMA_BRG);
            int jml = resultSet.getInt(helper_v_trans.KEY_JML);
            Double hargasat = resultSet.getDouble(helper_v_trans.KEY_HARGA_SATUAN);
            Double totalc = resultSet.getDouble(helper_v_trans.KEY_TOTAL);

            modeltrans.addRow(new Object[]{no,jml,brg,hargasat,totalc});     
            
        }
        
    }

    
    public void readRec_periksa_lab(String noraw) throws SQLException {

         Double rs=0.0,dr=0.0,sarana=0.0;
         Double rsbpjs=0.0,drbpjs=0.0,saranabpjs=0.0;
         
         DecimalFormat df2 = new DecimalFormat(".##");

         
        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_periksa_lab.TB_VNAME + " WHERE "
                                                      + helper_periksa_lab.KEY_NO_RAWAT + " =?");

        preparedStatement.setString(1,noraw);
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        
        while (resultSet.next()) {
//helper_periksa_lab
            String norawat = resultSet.getString(helper_periksa_lab.KEY_NO_RAWAT);
            String kodetarif = resultSet.getString(helper_periksa_lab.KEY_KODE_TARIF);
            String namatindakan = resultSet.getString(helper_periksa_lab.KEY_NAMA_TINDAKAN);
            double tarif = resultSet.getDouble(helper_periksa_lab.KEY_TARIF_TINDAKAN);
            double tarifbpjs = resultSet.getDouble(helper_periksa_lab.KEY_TARIF_TINDAKAN_BPJS);
            String status_pengesah = resultSet.getString(helper_periksa_lab.KEY_STATP);
            String status_verif = resultSet.getString(helper_periksa_lab.KEY_STATV);
            String status_pengesah_bpjs = resultSet.getString(helper_periksa_lab.KEY_STATP_BPJS);
            String status_verif_bpjs = resultSet.getString(helper_periksa_lab.KEY_STATV_BPJS);
            String nip = resultSet.getString(helper_periksa_lab.KEY_TARIF_NIP);
            
             if(resultSet.getDouble(helper_periksa_lab.KEY_TARIF_RS)!=0.0)
            {    
              rs =(resultSet.getDouble(helper_periksa_lab.KEY_TARIF_TINDAKAN)) * (resultSet.getDouble(helper_periksa_lab.KEY_TARIF_RS)/100);
            }
            else{
              rs=0.0;
            }
            
            if(resultSet.getDouble(helper_periksa_lab.KEY_TARIF_DR)!=0.0)
            {    
              dr = (resultSet.getDouble(helper_periksa_lab.KEY_TARIF_TINDAKAN)) * ((resultSet.getDouble(helper_periksa_lab.KEY_TARIF_DR)/100));
            }
            else{
              dr=0.0;
            }
            
            if(resultSet.getDouble(helper_periksa_lab.KEY_TARIF_SARANA)!=0.0)
            {    
              sarana = (resultSet.getDouble(helper_periksa_lab.KEY_TARIF_TINDAKAN)) * (resultSet.getDouble(helper_periksa_lab.KEY_TARIF_SARANA)/100);             
            }
            else{
              sarana=0.0;
            } 
             
             
            if(resultSet.getDouble(helper_periksa_lab.KEY_TARIF_RSBPJS)!=0.0)
            {    
              rsbpjs =(resultSet.getDouble(helper_periksa_lab.KEY_TARIF_TINDAKAN_BPJS)) * (resultSet.getDouble(helper_periksa_lab.KEY_TARIF_RSBPJS)/100);
            }
            else{
              rsbpjs=0.0;
            }
            
            if(resultSet.getDouble(helper_periksa_lab.KEY_TARIF_DRBPJS)!=0.0)
            {    
              drbpjs = (resultSet.getDouble(helper_periksa_lab.KEY_TARIF_TINDAKAN_BPJS)) * (resultSet.getDouble(helper_periksa_lab.KEY_TARIF_DRBPJS)/100);
            }
             else{
              drbpjs=0.0;
            }
            
            if(resultSet.getDouble(helper_periksa_lab.KEY_TARIF_SARANABPJS)!=0.0)
            {    
              saranabpjs = (resultSet.getDouble(helper_periksa_lab.KEY_TARIF_TINDAKAN_BPJS)) * (resultSet.getDouble(helper_periksa_lab.KEY_TARIF_SARANABPJS)/100);             
            }
             else{
              saranabpjs=0.0;
            }


                        //                                              "No Rawat","kode tarif","Nama Tindakan","Tarif","Tarif BPJS"
                        //                                            ,"rs","dr","sarana","rsbpjs","drbpjs","saranabpjs","nip","status_pengesah","Status verif","Status_pengesah bpjs","Status verif bpjs"};
                        //   
                        //                                          "No Rawat","kode tarif","Nama Tindakan","Tarif BPJS",
                        //                                                   "rsbpjs","drbpjs","saranabpjs","Status verif bpjs","nip","status_pengesah","Status verif","Status_pengesah bpjs"};
                

             modelperiksalabbpjs.addRow(new Object[]{norawat,kodetarif,namatindakan,tarifbpjs,df2.format(rsbpjs),df2.format(drbpjs),df2.format(saranabpjs),status_verif_bpjs,nip,status_pengesah,status_verif,status_pengesah_bpjs});
             modelperiksalab.addRow(new Object[]{norawat,kodetarif,namatindakan,tarif,tarifbpjs,df2.format(rs),df2.format(dr),df2.format(sarana),df2.format(rsbpjs),df2.format(drbpjs),df2.format(saranabpjs),nip,status_pengesah,status_verif,status_pengesah_bpjs,status_verif_bpjs});
             
        }
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
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Disimpan");
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

    public void Save_tindakanralan(String norawat)  {

//         public void Save_tindakanralan(String norawat,String nipdpjp ,String nipppjp
//            , boolean statusinap,String perkembangan,String statkamar)
        
        try {
            preparedStatement = connect.prepareStatement("insert into " + helper_unit.TB_NAME + " (" 
                                 + helper_unit.KEY_NO_RAWAT + "," 
//                                 + helper_unit.KEY_NIP_DPJP + ","
//                                 + helper_unit.KEY_NIP_PPJP  + ","
//                                 + helper_unit.KEY_STATUSINAP  + ","
//                                 + helper_unit.KEY_PERKEMBANGAN + ","
                                 + helper_unit.KEY_STATUS_KAMAR + ") "
                    + " values (?,?)");
                 
         
            preparedStatement.setString(1, norawat);
//            preparedStatement.setString(2, nipdpjp);
//            preparedStatement.setString(3, nipppjp);
//            preparedStatement.setBoolean(4, statusinap);
//            preparedStatement.setString(5, perkembangan);
             preparedStatement.setString(2, "fisio");
            
           
            preparedStatement.execute();
          JOptionPane.showMessageDialog(null, "Data Tersimpan");
          
          
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal Tersimpan");
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
    
    
    public void readRec_cariUnitDetailInapanakFisio(String nm,int i,String usn,String noraw) throws SQLException {
  
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
    else if(i==5){    
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_unit_detail.TB_VNAME + " WHERE " 
      + helper_unit_detail.KEY_NO_RAWAT + " =? AND "
      + helper_unit_detail.KEY_USERNAME + " =?"        
      );

      preparedStatement.setString(1,  nm );
      preparedStatement.setString(2,  usn );
      
    }
    
    else if(i==4){    
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_unit_detail.TB_VNAME + " WHERE DATE(" 
      + helper_unit_detail.KEY_TGL + ") BETWEEN ? AND ? AND "
      + helper_unit_detail.KEY_NO_RAWAT+"=? AND "
      + helper_unit_detail.KEY_USERNAME + " =?"
      );

      preparedStatement.setString(1,  tgl1 );
      preparedStatement.setString(2,  tgl2 );
      preparedStatement.setString(3,  nm );
      preparedStatement.setString(4,  usn );
    }
    else if(i==3){    
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_unit_detail.TB_VNAME + " WHERE " 
      + helper_unit_detail.KEY_NAMA + " like ? or "
      + helper_unit_detail.KEY_USERNAME + " = ? AND "
      + helper_unit_detail.KEY_NO_RAWAT + " = ? AND "        
      + helper_unit_detail.KEY_NAMA_TINDAKAN + " like ? ");

      preparedStatement.setString(1,  "%" + nm + "%");
      preparedStatement.setString(2,   usn );
      preparedStatement.setString(3,   noraw );
      preparedStatement.setString(4,  "%" + nm + "%");
      
//       ResultSet resultSet = preparedStatement.executeQuery();
//
//        while (resultSet.next()) {
//         
//            String norawat = resultSet.getString(helper_unit_detail.KEY_NO_RAWAT);
//            String kdtarif = resultSet.getString(helper_unit_detail.KEY_KODE_TARIF);
//            String namatindakan = resultSet.getString(helper_unit_detail.KEY_NAMA_TINDAKAN);
//            String nama = resultSet.getString(helper_unit_detail.KEY_NAMA);
//            String tgl = resultSet.getString(helper_unit_detail.KEY_TGL);
//            String username = resultSet.getString(helper_unit_detail.KEY_USERNAME);
//            modelunitdetail.addRow(new Object[]{norawat,kdtarif,namatindakan,nama,tgl,username});
//            
//        }
//        
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
      
//       ResultSet resultSet = preparedStatement.executeQuery();
//
//        while (resultSet.next()) {
//         
//            String norawat = resultSet.getString(helper_unit_detail.KEY_NO_RAWAT);
//            String kdtarif = resultSet.getString(helper_unit_detail.KEY_KODE_TARIF);
//            String namatindakan = resultSet.getString(helper_unit_detail.KEY_NAMA_TINDAKAN);
//            String nama = resultSet.getString(helper_unit_detail.KEY_NAMA);
//            String tgl = resultSet.getString(helper_unit_detail.KEY_TGL);
//            String username = resultSet.getString(helper_unit_detail.KEY_USERNAME);
//            modelunitdetail.addRow(new Object[]{norawat,kdtarif,namatindakan,nama,tgl,username});
//            
//        }
//        
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
    
     
     public void readRec_cariTarifTemplateFisio(String nm,boolean i,int idpoli,String kelas) throws SQLException {
    
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
         + helper_tarif.KEY_ID_POLI + " =? AND "                  
         + helper_tarif.KEY_ID_STATUS + " =? ");
          
         preparedStatement.setString(1, "ok");
         preparedStatement.setString(2, "ok");
         preparedStatement.setInt(3, 2);
         preparedStatement.setInt(4, idpoli);
     
         
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
         + helper_tarif.KEY_ID_STATUS + " =? OR "                 
         + helper_tarif.KEY_ID_STATUS + " =? ");
          
         preparedStatement.setString(1, "ok");
         preparedStatement.setString(2, "ok");
         preparedStatement.setInt(3, idpoli);
         preparedStatement.setInt(4, 5);
         
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
  
         //edit 29 12 2016
         
      preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_tarif.V_NAME + " WHERE " 
      + helper_tarif.KEY_NAMA_TINDAKAN + " like ? ");

        preparedStatement.setString(1, "%" + nm + "%");
        
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
              
              
              String stattindakan = resultSet.getString(helper_tarif.KEY_STATUS_KATEGORI);
              
              modeltarif.addRow(new Object[]{kodetarif, nmtindakan,tarif,presrs,presdr,pressarana,poli,status,ket,id_poli,id_status,p,v,kelas,
                                           tarifbpjs,presrsbpjs,presdrbpjs,pressaranabpjs,statusbpjs,kodebpjs,pbpjs,vbpjs,stattindakan});
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
              
              String stattindakan = resultSet.getString(helper_tarif.KEY_STATUS_KATEGORI);
              
              modeltarif.addRow(new Object[]{kodetarif, nmtindakan,tarif,presrs,presdr,pressarana,poli,status,ket,id_poli,id_status,p,v,kelas,
                                           tarifbpjs,presrsbpjs,presdrbpjs,pressaranabpjs,statusbpjs,kodebpjs,pbpjs,vbpjs,stattindakan});
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
    
    public void readRec_transfarmasiKaryawanJualbebas(String nm) throws SQLException {
      
        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_trans.TB_NAME + " WHERE "
                + helper_trans.KEY_NM_PASIEN + " like ? or "
                + helper_trans.KEY_NO_RM + " like ? or "
                + helper_trans.KEY_NO_NOTA + " like ? or "
                + helper_trans.KEY_NAMA_KARYAWAN + " like ? or "
                + helper_trans.KEY_NAMA_JUAL_BEBAS + " like ?"
                );

         preparedStatement.setString(1, "%" + nm + "%");    
         preparedStatement.setString(2, "%" + nm + "%");  
         preparedStatement.setString(3, "%" + nm + "%");  
         preparedStatement.setString(4, "%" + nm + "%");  
         preparedStatement.setString(5, "%" + nm + "%");  
       
          
//    preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_trans.TB_NAME + " WHERE "
//                + helper_trans.KEY_NM_PASIEN + " like ? ");
//
//        preparedStatement.setString(1, "%" + nm + "%");    
          
       
    
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
  
//     if(!resultSet.getString(helper_trans.KEY_NAMA_JUAL_BEBAS).isEmpty()){       
            String nota = resultSet.getString(helper_trans.KEY_NO_NOTA);
//            String nmrm= resultSet.getString(helper_trans.KEY_NO_RM);
//            String nama_pasien= resultSet.getString(helper_trans.KEY_NM_PASIEN);
            String petugas = resultSet.getString(helper_trans.KEY_PETUGAS);
            String carabeli = resultSet.getString(helper_trans.KEY_CARA_BELI);
//            String noraw = resultSet.getString(helper_trans.KEY_NO_RAWAT);
            String jb = resultSet.getString(helper_trans.KEY_NAMA_JUAL_BEBAS);
            String nik = resultSet.getString(helper_trans.KEY_NIK);
            String nmp = resultSet.getString(helper_trans.KEY_NAMA_KARYAWAN);
            String statcetak = resultSet.getString(helper_trans.KEY_STATUS_CETAK);
            String catatan = resultSet.getString(helper_trans.KEY_CATATAN);
//            String kdpj = resultSet.getString(helper_trans.KEY_KODE_PJ);
//            String nmpj = resultSet.getString(helper_trans.KEY_NAMA_PJ);
//            String nosep = resultSet.getString(helper_trans.KEY_NO_SEP);
            String tgl = resultSet.getString(helper_trans.KEY_TGL);
            
            modeltransfarmasikaryawanjualbebas.addRow(new Object[]{nota,nik,nmp
                                                   ,carabeli,statcetak,petugas,catatan,tgl,jb});
//     }
            
        }
    }
  
    
   public void readRec_transfarmasi(String nm) throws SQLException {
      
          
    preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_trans.TB_VNAME + " WHERE "
                + helper_trans.KEY_NM_PASIEN + " like ? or "
                + helper_trans.KEY_NO_RM + " like ? or "
                + helper_trans.KEY_NO_NOTA + " like ? or "
                + helper_trans.KEY_NAMA_KARYAWAN + " like ? or "
                + helper_trans.KEY_NAMA_JUAL_BEBAS + " like ?"
                );

         preparedStatement.setString(1, "%" + nm + "%");    
         preparedStatement.setString(2, "%" + nm + "%");  
         preparedStatement.setString(3, "%" + nm + "%");  
         preparedStatement.setString(4, "%" + nm + "%");  
         preparedStatement.setString(5, "%" + nm + "%");  
       
    
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
  
            
            String nota = resultSet.getString(helper_trans.KEY_NO_NOTA);
            String nmrm= resultSet.getString(helper_trans.KEY_NO_RM);
            String nama_pasien= resultSet.getString(helper_trans.KEY_NM_PASIEN);
            String petugas = resultSet.getString(helper_trans.KEY_PETUGAS);
            String carabeli = resultSet.getString(helper_trans.KEY_CARA_BELI);
            String noraw = resultSet.getString(helper_trans.KEY_NO_RAWAT);
            String jb = resultSet.getString(helper_trans.KEY_NAMA_JUAL_BEBAS);
            String nik = resultSet.getString(helper_trans.KEY_NIK);
            String nmp = resultSet.getString(helper_trans.KEY_NAMA_KARYAWAN);
            String statcetak = resultSet.getString(helper_trans.KEY_STATUS_CETAK);
            String catatan = resultSet.getString(helper_trans.KEY_CATATAN);
            String kdpj = resultSet.getString(helper_trans.KEY_KODE_PJ);
            String nmpj = resultSet.getString(helper_trans.KEY_NAMA_PJ);
            String nosep = resultSet.getString(helper_trans.KEY_NO_SEP);
            String tgl = resultSet.getString(helper_trans.KEY_TGL);
            
            modeltransfarmasi.addRow(new Object[]{nmrm,nama_pasien,carabeli,noraw,jb,nik,nmp
                                                   ,statcetak,petugas,catatan,nota,kdpj,nmpj,nosep,tgl});
           
            
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
     
     
      public int readRec_HitFarmasi(String norw) throws SQLException {

       int hit=0;  
       preparedStatement = connect.prepareStatement("SELECT count(*) as hit FROM " + helper_trans.TB_NAME +" WHERE "
       +helper_trans.KEY_NO_NOTA+" like ?");
    
        preparedStatement.setString(1, "%" + norw + "%");
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
            String kar= resultSet.getString(helper_v_trans.KEY_NAMA_KARYAWAN);
            String jb= resultSet.getString(helper_v_trans.KEY_NAMA_JUAL_BEBAS);
            String petugas = resultSet.getString(helper_v_trans.KEY_PETUGAS);
            String tglc = resultSet.getString(helper_v_trans.KEY_TGL);
            String brg = resultSet.getString(helper_v_trans.KEY_NAMA_BRG);
            String kat=resultSet.getString(helper_v_trans.KEY_KATEGORI);
            int jml = resultSet.getInt(helper_v_trans.KEY_JML);
            Double hargasat = resultSet.getDouble(helper_v_trans.KEY_HARGA_SATUAN);
            Double totalc = resultSet.getDouble(helper_v_trans.KEY_TOTAL);

                 
            
            modelctrans.addRow(new Object[]{no, nonota, rm, nmp,kar,jb,brg,jml,hargasat,totalc,kat,tglc,petugas});
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
     
      
    
     public void readRec_Biayatindakankasir(String noraw) throws SQLException {

         Double rs=0.0,dr=0.0,sarana=0.0;
         Double rsbpjs=0.0,drbpjs=0.0,saranabpjs=0.0;
         
         DecimalFormat df2 = new DecimalFormat(".##");
         
        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_v_biaya_tindakan.TB_VNAME + " WHERE "
                                                     + helper_v_biaya_tindakan.KEY_NO_RAWAT + " =?");

        preparedStatement.setString(1, noraw);
       
        
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {


            String norawat = resultSet.getString(helper_v_biaya_tindakan.KEY_NO_RAWAT);
            String kdtarif = resultSet.getString(helper_v_biaya_tindakan.KEY_KODE_TARIF);
            String nmt = resultSet.getString(helper_v_biaya_tindakan.KEY_NAMA_TINDAKAN);
            Double tarif = resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_TINDAKAN);
            Double tarifbpjs = resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_TINDAKAN_BPJS);
            Double breg = resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_BIAYA_REG);
            
            if(resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_RS)!=0.0)
            {    
              rs =(resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_TINDAKAN)) * (resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_RS)/100);
            }
            else{
              rs=0.0;
            }
            
            if(resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_DR)!=0.0)
            {    
              dr = (resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_TINDAKAN)) * ((resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_DR)/100));
            }
            else{
              dr=0.0;
            }
            
            if(resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_SARANA)!=0.0)
            {    
              sarana = (resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_TINDAKAN)) * (resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_SARANA)/100);             
            }
            else{
              sarana=0.0;
            } 
             
             
            if(resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_RSBPJS)!=0.0)
            {    
              rsbpjs =(resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_TINDAKAN_BPJS)) * (resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_RSBPJS)/100);
            }
            else{
              rsbpjs=0.0;
            }
            
            if(resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_DRBPJS)!=0.0)
            {    
              drbpjs = (resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_TINDAKAN_BPJS)) * (resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_DRBPJS)/100);
            }
             else{
              drbpjs=0.0;
            }
            
            if(resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_SARANABPJS)!=0.0)
            {    
              saranabpjs = (resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_TINDAKAN_BPJS)) * (resultSet.getDouble(helper_v_biaya_tindakan.KEY_TARIF_SARANABPJS)/100);             
            }
             else{
              saranabpjs=0.0;
            }
            
            String nip = resultSet.getString(helper_v_biaya_tindakan.KEY_TARIF_NIP);
//            {"No Rawat", "Kode Tarif", "Nama Tindakan", "Tarif Tindakan", "Tarif Tindakan BPJS","Biaya Reg","rs","dr","sarana","rsbpjs","drbpjs","saranabpjs"};
//"No Rawat", "Kode Tarif", "Nama Tindakan", "Tarif Tindakan", "Tarif Tindakan BPJS","rs","dr","sarana","rsbpjs","drbpjs","saranabpjs","nip","Biaya Reg"
            modelbiayatindakan.addRow(new Object[]{norawat, kdtarif, nmt, tarif,tarifbpjs,df2.format(rs),df2.format(dr),df2.format(sarana),df2.format(rsbpjs),df2.format(drbpjs),df2.format(saranabpjs),nip,breg});
            
            modelbiayatindakanbpjs.addRow(new Object[]{norawat, kdtarif, nmt,tarifbpjs,df2.format(rsbpjs),df2.format(drbpjs),df2.format(saranabpjs),nip});
            
            
        }
    }
    
       public void readRec_Allhistory(String tgl1,String tgl2,String nm,int pil) throws SQLException {

       if(pil==1){     
         preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_v_trans.TB_NAME 
                                                     + " WHERE " +helper_v_trans.KEY_TGL + " BETWEEN ? AND ?"
                                                               );
         preparedStatement.setString(1, tgl1);
         preparedStatement.setString(2, tgl2);
         
         
       }
       else if(pil==2){
          preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_v_trans.TB_NAME 
                                                     + " WHERE "  
//                                                     + helper_v_trans.KEY_TGL + " =? AND "
                                                     + helper_v_trans.KEY_NM_PASIEN + " like ? OR "
                                                     + helper_v_trans.KEY_NO_RM + " like ? OR "
                                                     + helper_v_trans.KEY_NO_NOTA + " like ? OR " 
                                                     + helper_v_trans.KEY_NAMA_JUAL_BEBAS + " like ? OR " 
                                                     + helper_v_trans.KEY_NAMA_KARYAWAN + " like ? OR " 
                                                     + helper_v_trans.KEY_KATEGORI + " like ? OR "
                                                     + helper_v_trans.KEY_NAMA_BRG + " like ?"   
                                                        );  
                                                               
//         preparedStatement.setString(1, tgl1);
         preparedStatement.setString(1, "%" + nm + "%");
         preparedStatement.setString(2, "%" + nm + "%");
         preparedStatement.setString(3, "%" + nm + "%");
         preparedStatement.setString(4, "%" + nm + "%");
         preparedStatement.setString(5, "%" + nm + "%");
         preparedStatement.setString(6, "%" + nm + "%");
         preparedStatement.setString(7, "%" + nm + "%");
         
      }
      else if(pil==3){
          preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_v_trans.TB_NAME 
                                                     + " WHERE "  
                                                     + helper_v_trans.KEY_TGL + "=? AND "
                                                     + helper_v_trans.KEY_NM_PASIEN + " like ? OR "
                                                     + helper_v_trans.KEY_NO_RM + " like ? OR "
                                                     + helper_v_trans.KEY_NO_NOTA + " like ? OR " 
                                                     + helper_v_trans.KEY_NAMA_JUAL_BEBAS + " like ? OR " 
                                                     + helper_v_trans.KEY_NAMA_KARYAWAN + " like ? OR " 
                                                    + helper_v_trans.KEY_KATEGORI + " like ? OR "
                                                     + helper_v_trans.KEY_NAMA_BRG + " like ?"   
                                                        );    
                                                               
         preparedStatement.setString(1, tgl1);
         preparedStatement.setString(2, "%" + nm + "%");
         preparedStatement.setString(3, "%" + nm + "%");
         preparedStatement.setString(4, "%" + nm + "%");
         preparedStatement.setString(5, "%" + nm + "%");
         preparedStatement.setString(6, "%" + nm + "%");
         preparedStatement.setString(7, "%" + nm + "%");
         preparedStatement.setString(8, "%" + nm + "%");
         
      }
     else if(pil==4){
          preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_v_trans.TB_NAME 
                                                     + " WHERE " +helper_v_trans.KEY_TGL + " BETWEEN ? AND ? AND "
                                                     + helper_v_trans.KEY_KATEGORI + "=?"
                                                        );  
                                                               
//         preparedStatement.setString(1, tgl1);
         preparedStatement.setString(1, tgl1);
         preparedStatement.setString(2, tgl2);
         preparedStatement.setString(3, nm);
         
         
      }
     
        ResultSet resultSet = preparedStatement.executeQuery();

        int i=0;
     
        while (resultSet.next()) {

            i++;

            String no = String.valueOf(i);
            String nonota = resultSet.getString(helper_v_trans.KEY_NO_NOTA);
            String rm = resultSet.getString(helper_v_trans.KEY_NO_RM);
            String nmp = resultSet.getString(helper_v_trans.KEY_NM_PASIEN);
            String kar= resultSet.getString(helper_v_trans.KEY_NAMA_KARYAWAN);
            String jb= resultSet.getString(helper_v_trans.KEY_NAMA_JUAL_BEBAS);
            String petugas = resultSet.getString(helper_v_trans.KEY_PETUGAS);
            String tglc = resultSet.getString(helper_v_trans.KEY_TGL);
            String brg = resultSet.getString(helper_v_trans.KEY_NAMA_BRG);
            String kat=resultSet.getString(helper_v_trans.KEY_KATEGORI);
            int jml = resultSet.getInt(helper_v_trans.KEY_JML);
            Double hargasat = resultSet.getDouble(helper_v_trans.KEY_HARGA_SATUAN);
            Double totalc = resultSet.getDouble(helper_v_trans.KEY_TOTAL);

            modelctrans.addRow(new Object[]{no, nonota,brg,jml,hargasat,totalc,kat, rm, nmp,kar,jb,tglc,petugas});
            

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
             JOptionPane.showMessageDialog(null, "Gagal Tersimpan "+ex);
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
   
    public void readRec_brgF(String namabrg) throws SQLException {

        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_brg.TB_TBNAME + " WHERE "
                + helper_brg.KEY_NAMA_BRG + " like ?");

        preparedStatement.setString(1, "%" + namabrg + "%");

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            String kodeobat = resultSet.getString(helper_brg.KEY_KODE_BRG);
            String nmbrg = resultSet.getString(helper_brg.KEY_NAMA_BRG);
            double hrgbeli = resultSet.getDouble(helper_brg.KEY_HARGA_BELI);
            double hrgralan = resultSet.getDouble(helper_brg.KEY_RAWAT_JALAN);
            double hrgkary = resultSet.getDouble(helper_brg.KEY_KARYAWAN);
            String kat = resultSet.getString(helper_brg.KEY_KATEGORI);
           
            modelbrg.addRow(new Object[]{kodeobat, nmbrg, hrgralan,hrgkary,hrgbeli,kat});
        }
    }

    public void readRec_DetailFarmasi(String namabrg,String nota) throws SQLException{
      
         
        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_detail.TB_NAME + " WHERE "
                + helper_detail.KEY_NAMA_BRG + "=? AND "
                + helper_detail.KEY_NO_NOTA + "=?" );

        
         preparedStatement.setString(1, namabrg );
          preparedStatement.setString(2, nota );
        ResultSet resultSet = preparedStatement.executeQuery();

      
        while (resultSet.next()) {
           
            
            
        }
         
       
     
     }

    
    
     public int readRec_nmbrg(String namabrg,String nota) throws SQLException{
      int hitkd=0;
         
        preparedStatement = connect.prepareStatement("SELECT count(*) as hitkd FROM " + helper_detail.TB_NAME + " WHERE "
                + helper_detail.KEY_NAMA_BRG + "=? AND "
                + helper_detail.KEY_NO_NOTA + "=?" );

        
         preparedStatement.setString(1, namabrg );
          preparedStatement.setString(2, nota );
        ResultSet resultSet = preparedStatement.executeQuery();

      
        while (resultSet.next()) {
           hitkd=resultSet.getInt("hitkd");
        }
         
         return hitkd;
     
     }
    
     public void readRec_brg() throws SQLException {

        preparedStatement = connect.prepareStatement("SELECT * FROM " + helper_brg.TB_TBNAME);

        ResultSet resultSet = preparedStatement.executeQuery();

      
        while (resultSet.next()) {
 
           
            String kodeobat = resultSet.getString(helper_brg.KEY_KODE_BRG);
            String nmbrg = resultSet.getString(helper_brg.KEY_NAMA_BRG);
            double hrgbeli = resultSet.getDouble(helper_brg.KEY_HARGA_BELI);
            double hrgralan = resultSet.getDouble(helper_brg.KEY_RAWAT_JALAN);
            double hrgkary = resultSet.getDouble(helper_brg.KEY_KARYAWAN);
            String kat = resultSet.getString(helper_brg.KEY_KATEGORI);
           
            modelbrg.addRow(new Object[]{kodeobat, nmbrg, hrgralan,hrgkary,hrgbeli,kat});
        }
    }
    
   
   public void Save_trans(String no_nota, String no_rm, String nama_pasien, String catatan
           , String petugas, String carabeli, String noraw, String nmjualbebas,String tgl,String nik,String namakary) {

        try {
            preparedStatement = connect.prepareStatement("insert into " + helper_trans.TB_NAME + " (" 
                    + helper_trans.KEY_NO_NOTA + "," 
                    + helper_trans.KEY_NO_RM + ","
                    + helper_trans.KEY_NM_PASIEN + "," 
                    + helper_trans.KEY_CATATAN + "," 
                    + helper_trans.KEY_PETUGAS + ","
                    + helper_trans.KEY_CARA_BELI + ","
                    + helper_trans.KEY_NO_RAWAT + ","
                    + helper_trans.KEY_NAMA_JUAL_BEBAS + "," 
//                    + helper_trans.KEY_TGL + "," 
                    + helper_trans.KEY_NIK + "," 
                    + helper_trans.KEY_NAMA_KARYAWAN + ") "
                    + " values (?,?,?,?,?,?,?,?,?,?)");

            preparedStatement.setString(1, no_nota);
            preparedStatement.setString(2, no_rm);
            preparedStatement.setString(3, nama_pasien);
            preparedStatement.setString(4, catatan);
            preparedStatement.setString(5, petugas);
            preparedStatement.setString(6, carabeli);
            preparedStatement.setString(7, noraw);
            preparedStatement.setString(8, nmjualbebas);
//            preparedStatement.setString(9, tgl);
            preparedStatement.setString(9, nik);
            preparedStatement.setString(10, namakary);
            
            preparedStatement.execute();


            JOptionPane.showMessageDialog(null, "Data Tersimpan");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal Tersimpan "+ ex);
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
   
   public void updateTarifkategori(String kode_tarif,String kategori){
       try {
            preparedStatement = connect.prepareStatement("update " + helper_tarif.TB_NAME + " set " 
                    + helper_tarif.KEY_STATUS_KATEGORI+"=?"
                    +" where "+ helper_tarif.KEY_KODE_TARIF + "=?");
            
           
            preparedStatement.setString(1, kategori);
            preparedStatement.setString(2, kode_tarif);
             
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");
        } catch (SQLException ex) {
//             if(ex.getErrorCode() == 1062 ){
//            //duplicate primary key 
            JOptionPane.showMessageDialog(null, "Gagal Update : Kode " + kode_tarif + " ");
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
                        ,String status_pengesahbpjs,String status_verifbpjs,int pilih,String stattindakan) {
    
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
                    + helper_tarif.KEY_STATUS_VERIF_BPJS+"=?,"
                    + helper_tarif.KEY_STATUS_KATEGORI+"=?"
                    +" where "+ helper_tarif.KEY_KODE_TARIF + "=?");
            
            preparedStatement.setString(20, kode_tarif);
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
            preparedStatement.setString(19, stattindakan);
            
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
                    + helper_tarif.KEY_KODE_BPJS+"=?,"
                    + helper_tarif.KEY_STATUS_KATEGORI+"=?"
                    +" where "+ helper_tarif.KEY_KODE_TARIF + "=?");
            
              preparedStatement.setString(10, kode_tarif);
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
             preparedStatement.setString(9, stattindakan);
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
                          int presentase_dr1,int presentase_rs1,int presentase_sarana1,String statusbpjs,String status_pengesahbpjs,String status_verifbpjs,String kode_tarifbpjs,String stattindakan) {

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
                    + helper_tarif.KEY_STATUS_VERIF_BPJS  + "," 
                    + helper_tarif.KEY_STATUS_KATEGORI  
                    + ") "
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

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
            preparedStatement.setString(21, stattindakan);
            
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
    
    
    public void DelRecDetailTransFarmasi(String nmbrg) throws SQLException  {
  
        
        try {
            preparedStatement = connect.prepareStatement("delete from " + helper_detail.TB_NAME + " where " 
                    + helper_detail.KEY_NAMA_BRG + "=?"
                    );
            preparedStatement.setString(1, nmbrg);
           
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
    }
    
    public void DelRecHistoryDetailFarmasi(String nota) throws SQLException  {
  
        
        try {
            preparedStatement = connect.prepareStatement("delete from " + helper_trans.TB_NAME + " where " 
                    + helper_trans.KEY_NO_NOTA + "=?"
                    );
            preparedStatement.setString(1, nota);
           
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
    }
    
     public void DelRecHistoryFarmasi(String nota) throws SQLException  {
  
        
        try {
            preparedStatement = connect.prepareStatement("delete from " + helper_trans.TB_NAME + " where " 
                    + helper_trans.KEY_NO_NOTA + "=?"
                    );
            preparedStatement.setString(1, nota);
           
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
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
   
   
  public void CetakTagihanFisio(String nonota,String username,String tampil,int i,String tgl) throws JRException {

        InputStream is = null;
        
      if(i==1){  
        is = getClass().getResourceAsStream("/unit_fisio/rpt_tagihan_fisio.jrxml");
      }
      else{
        is = getClass().getResourceAsStream("/unit_fisio/rpt_tagihan_fisio_BPJS.jrxml");
      }

        //set parameters
        Map map = new HashMap();
        map.put("noraw", nonota);
        map.put("username", username);
        map.put("tgl", tgl);
     

       JasperReport jr = JasperCompileManager.compileReport(is);
//
        JasperPrint jp = JasperFillManager.fillReport(jr, map, connect);
      

      
        JasperPrintManager.printReport(jp,false);
        JasperViewer.viewReport(jp, false);
        if(tampil.equals("ok")){
            JasperViewer.viewReport(jp, false);
        }
       
    }    
   
   
  public void CetakNota(String nonota,String tampil,int i,String statpasien,String nosep) throws JRException {

        InputStream is = null;
        
          Map map = new HashMap();
        
       if (i==1){
         is = getClass().getResourceAsStream("rpt_cetak.jrxml");
           //set parameters
      
        map.put("nonota", nonota);
        map.put("statuspasien", statpasien);
        
        }
       else if (i==2)
       {
        is = getClass().getResourceAsStream("rpt_cetak_bpjs.jrxml");
          //set parameters
      
        map.put("nonota", nonota);
        map.put("statuspasien", statpasien);
        map.put("nosep", nosep);
        
       }
       else if (i==3)
       {
        is = getClass().getResourceAsStream("rpt_cetak_karyawan.jrxml");
          //set parameters
      
        map.put("nonota", nonota);
        
       }
        else if (i==4)
       {
        is = getClass().getResourceAsStream("rpt_cetak_jual_lepas.jrxml");
          //set parameters
      
        map.put("nonota", nonota);
        
       }
        
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
  
public void CetakTarif() throws JRException {

        InputStream is = null;
        is = getClass().getResourceAsStream("/tarif/report_tarif.jrxml");

        //set parameters
//        Map map = new HashMap();
//        map.put("nonota", nonota);
       // map.put("Imgpath",null);

        JasperReport jr = JasperCompileManager.compileReport(is);

        JasperPrint jp = JasperFillManager.fillReport(jr, null, connect);
        //jp.setPageWidth(200);
        //jp.setPageHeight(180);
       // jp.setOrientation(OrientationEnum.PORTRAIT);

      
//        JasperPrintManager.printReport(jp,false);
       
//        if(tampil.equals("ok")){
            JasperViewer.viewReport(jp, false);
//        }
       
    }    
      

public void cetakpendapatanfarmasi(String tgl1,String tgl2,int i,String kategori) throws JRException{
     InputStream is = null;
        
     Map map = new HashMap();
    if(i==1) 
    {
       is = getClass().getResourceAsStream("rpt_farmasi_pend.jrxml");
           //set parameters
      
        map.put("tgl1", tgl1);
        map.put("tgl2", tgl2);
    }
    else{
       is = getClass().getResourceAsStream("rpt_farmasi_pend_kategori.jrxml");
           //set parameters
      
        map.put("tgl1", tgl1);
        map.put("tgl2", tgl2);
        map.put("kategori", kategori);
        
    }
        
         JasperReport jr = JasperCompileManager.compileReport(is);

        JasperPrint jp = JasperFillManager.fillReport(jr, map, connect);
         JasperViewer.viewReport(jp, false);
}


public void updatehistordetailfarmasi(int jml ,double hargasatuan,double total,String nmbrg,String nota){
       try { 
           
           
           
            preparedStatement = connect.prepareStatement("update " + helper_detail.TB_NAME + " set " 
                    + helper_detail.KEY_JML+"=?,"
                    + helper_detail.KEY_HARGA_SATUAN+"=?,"
                    + helper_detail.KEY_TOTAL+"=?,"
                    + helper_detail.KEY_NAMA_BRG+"=?"
                    +" where "+ helper_detail.KEY_NO_NOTA + "=? AND "
                    + helper_detail.KEY_NAMA_BRG+"=?"
                    );
            
           
            preparedStatement.setInt(1, jml);
            preparedStatement.setDouble(2, hargasatuan);
            preparedStatement.setDouble(3, total);
            preparedStatement.setString(4, nmbrg);
            preparedStatement.setString(5, nota);
            preparedStatement.setString(6, nmbrg);
          
             
             
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");
        } catch (SQLException ex) {
//             if(ex.getErrorCode() == 1062 ){
//            //duplicate primary key 
            JOptionPane.showMessageDialog(null, "Gagal Update ");
//            }
//            else{
//            JOptionPane.showMessageDialog(null, "Gagal Update");
//            }
            Logger.getLogger(Crud_local.class.getName()).log(Level.SEVERE, null, ex);
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
