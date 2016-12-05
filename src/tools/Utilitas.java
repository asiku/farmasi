/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.awt.Component;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author jengcool
 */
public class Utilitas {
    
    public static String formatuang(Double hrg) {

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        //  System.out.printf("Harga Rupiah: %s %n", kursIndonesia.format(harga));

        return kursIndonesia.format(hrg);

    }
    
     public static String tglsekarang() {

        LocalDate localDate = LocalDate.now();

        return DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate);
    }
    
      public static String tglsekarangJam() {

        LocalDate localDate = LocalDate.now();
        LocalTime  localTime= LocalTime.now();

        LocalDateTime ldt = LocalDateTime.of(localDate, localTime);

        return DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss").format(ldt);
    }
     
    public static void  HapusText(JPanel panel){
     JTextField temp=null;
    
     
        for(Component c:panel.getComponents()){
           if(c.getClass().toString().contains("javax.swing.JTextField")){
              temp=(JTextField)c;
             
              temp.setText("");
        }
        
      }
    }
}
