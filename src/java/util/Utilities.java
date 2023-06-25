/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ptd
 */
public class Utilities {

    public static boolean isPositiveNumber(String str) {
        Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)?$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
