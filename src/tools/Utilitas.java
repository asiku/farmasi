/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 *
 * @author jengcool
 */
public class Utilitas {

// public static void main(String [] args)
// {
// 
// }
// 
    public static String getDateServer() {
        
        String wkt="";
        
        try {

            Client client = Client.create();
            //WebResource webResource = client.resource("http://localhost/rsjul/restfulPHP.php?wonderName=Taj%20Mahal");

            WebResource webResource = client.resource("http://localhost/rsjul/timeserver.php?waktu=mentawaktujang");

            ClientResponse response = webResource.accept("").get(ClientResponse.class);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            String output2 = response.getEntity(String.class);

            ObjectMapper objectMapper = new ObjectMapper();

//read JSON like DOM Parser
            JsonNode rootNode = objectMapper.readTree(output2);

            JsonNode idNode = rootNode.path("tgl");

            wkt=idNode.asText();
            System.out.println("tgl = " + idNode.asText());

            System.out.println("\n============Plain Text Response============");
            System.out.println(output2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
       return  wkt;
       
    }

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
        LocalTime localTime = LocalTime.now();

        LocalDateTime ldt = LocalDateTime.of(localDate, localTime);

        return DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss").format(ldt);
    }
    
    public static String Jam() {

        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();

        LocalDateTime ldt = LocalDateTime.of(localDate, localTime);

        return DateTimeFormatter.ofPattern("HH:mm:ss").format(ldt);
    }

    public static void HapusText(JPanel panel) {
        JTextField temp = null;

        for (Component c : panel.getComponents()) {
            if (c.getClass().toString().contains("javax.swing.JTextField")) {
                temp = (JTextField) c;

                temp.setText("");
            }

        }
    }
}
