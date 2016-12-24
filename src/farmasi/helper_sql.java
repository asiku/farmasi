/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farmasi;

/**
 *
 * @author jengcool
 */
public class helper_sql {
    
    public static String SELECT="select * from ";
    public static String INSERT="insert into ";
    public static String UPDATE="update ";
    public static String LIKE=" like ? ";
    public static String WHERE=" where ";
    
    public String cariAll(String tb){
       
    return SELECT + tb;
    }
    
    
}
