/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author k
 */
public class DatesUtils {

     public static String parseCreationDate(String str_date) {
        try {

            String aux = str_date.substring(0, 10);
            DateFormat formatter;
            Date date;
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = (Date) formatter.parse(aux);
            String formattedDate = new SimpleDateFormat("yyyyMMdd").format(date);
            return formattedDate;

        } catch (ParseException e) {
            System.out.println("Exception :" + e);
            return null;
        }

    }
    public static String parseDateEntry(String str_date) {
        try {
            DateFormat formatter;
            Date date;
            formatter = new SimpleDateFormat("dd/MM/yy");
            date = (Date) formatter.parse(str_date);
            String formattedDate = new SimpleDateFormat("yyyyMMdd").format(date);
            return formattedDate;

        } catch (ParseException e) {
            System.out.println("La fecha es invalida" + e);
            return null;
        }

    }

     public static String parseModificationDate(Date date) {
        try {
            String formattedDate = new SimpleDateFormat("yyyyMMdd").format(date);
            return formattedDate;

        } catch (Exception e) {
            System.out.println("Exception :" + e);
            return null;
        }

    }

}