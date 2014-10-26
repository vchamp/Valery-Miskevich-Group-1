package by.gravity.client.utils;

/**
 * Created by ilya.shknaj on 25.10.14.
 */
public class StringUtils {

    public static boolean isEmpty(String text) {

        return text == null || text.length() == 0;
    }

    public static boolean isNumeric(String str) {

        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}
