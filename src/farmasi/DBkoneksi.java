/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farmasi;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author PCSundaya
 */
public class DBkoneksi {
    public Connection connect = null;
	
	public void ConDb() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB

		String urlcon = "jdbc:mysql://localhost:3306/sik";
		String user = "root";
		String pwd = "";
		
		connect = DriverManager.getConnection(urlcon, user, pwd);
		
	}
}
