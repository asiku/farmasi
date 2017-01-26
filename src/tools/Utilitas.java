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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

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
   
    
    public static void filtertb(String text, JTable tb, int col, int sortcol) {

        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tb.getModel());
        tb.setRowSorter(sorter);

        if (text.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, col));
        }

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();

        sortKeys.add(new RowSorter.SortKey(sortcol, SortOrder.ASCENDING));

        sorter.setSortKeys(sortKeys);
        sorter.sort();

    }

    public static String getDateServer() {

        String wkt = "";

        try {

            Client client = Client.create();

      WebResource webResource = client.resource("http://localhost/rsjul/timeserver2.php?waktu=mentawaktujang");
//      WebResource webResource = client.resource("http://192.168.1.31/rsjul/timeserver2.php?waktu=mentawaktujang");
//        WebResource webResource = client.resource("http://localhost/rsjul/timeserver.php?waktu=mentawaktujang");
//           WebResource webResource = client.resource("http://192.168.1.31/rsjul/timeserver.php?waktu=mentawaktujang");

            ClientResponse response = webResource.accept("").get(ClientResponse.class);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            String output2 = response.getEntity(String.class);

            ObjectMapper objectMapper = new ObjectMapper();

//read JSON like DOM Parser
            JsonNode rootNode = objectMapper.readTree(output2);

            JsonNode idNode = rootNode.path("tgl");

            wkt = idNode.asText();
            System.out.println("tgl = " + idNode.asText());

            System.out.println("\n============Plain Text Response============");
            System.out.println(output2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return wkt;

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

    public static long Hitungtgl(String tgl1, String jammasuk, String tgl2, String jamsekarang) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
        String dateStart = tgl1 + " " + jammasuk;
        String dateStop = tgl2 + " " + jamsekarang;

//System.out.println(dateStart);
//System.out.println(dateStop);
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(dateStart.replace("-", "/"));
            d2 = format.parse(dateStop.replace("-", "/"));
//    d1 = format.parse(tgl1+" "+jammasuk);
//    d2 = format.parse(tgl2+" "+jamsekarang);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = d1.getTime() - d2.getTime();
        long diffSeconds = diff / 1000 % 60;;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);

        return diffHours;

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

    public static String Jamx() {

        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();

        LocalDateTime ldt = LocalDateTime.of(localDate, localTime);

        return DateTimeFormatter.ofPattern("hh:mm a").format(ldt);
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
