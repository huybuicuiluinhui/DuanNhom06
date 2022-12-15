/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Huy
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("YYYY-MM-DD");
        String ngaySinh_ = "2022-12-12";
        String ngaySinh_2 = "2023-12-12";
        java.util.Date d1;
        java.util.Date d2;
        try {
            d1 = sdf.parse(ngaySinh_);
            d2 = sdf.parse(ngaySinh_2);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Sai định dạng ngày");
            e.printStackTrace();
            return;
        }
        System.out.println(d1 + "ahihih");
        long diff = d2.getTime() - d1.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        System.out.print(diffDays + " days, ");
        System.out.print(diffHours + " hours, ");
        System.out.print(diffMinutes + " minutes, ");
        System.out.print(diffSeconds + " seconds.");
    }

}
