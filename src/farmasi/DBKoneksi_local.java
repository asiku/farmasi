/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farmasi;

import java.awt.Cursor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jengcool
 */
public class DBKoneksi_local {
    public Connection connect = null;
	
	public void ConDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB

            
//            String urlcon = "jdbc:mysql://192.168.1.31:3306/rs_juliana?connectTimeout=5000&socketTimeout=5000";
		String urlcon = "jdbc:mysql://localhost:3306/rs_juliana";
            String user = "root";
            String pwd = "";

            connect = DriverManager.getConnection(urlcon, user, pwd);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Maaf Server Tidak Terkoneksi Periksa Kembali Jaringan Anda!");
            
            System.exit(0);
            
            Logger.getLogger(DBKoneksi_local.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Maaf Server Tidak Terkoneksi Periksa Kembali Jaringan Anda!");
            System.exit(0);
            Logger.getLogger(DBKoneksi_local.class.getName()).log(Level.SEVERE, null, ex);
        }

	
		
	}
}
