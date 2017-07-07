package com.manojkhannakm.materialswing.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manoj Khanna
 */

public class StringUtils {

    private StringUtils() {
    }

    public static String getCamelCase(String string) {
        ObjectUtils.verifyNotNull(string, "string");

        string = string.trim();

        if (string.isEmpty()) {
            return string;
        }

        StringBuffer stringBuffer = new StringBuffer(string.substring(0, 1).toLowerCase());

        Matcher matcher = Pattern.compile(" +(.)").matcher(string.substring(1));

        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, matcher.group(1).toUpperCase());
        }

        matcher.appendTail(stringBuffer);

        return stringBuffer.toString();
    }

    public static String getPascalCase(String string) {
        ObjectUtils.verifyNotNull(string, "string");

        string = string.trim();

        if (string.isEmpty()) {
            return string;
        }

        StringBuffer stringBuffer = new StringBuffer(string.substring(0, 1).toUpperCase());

        Matcher matcher = Pattern.compile(" +(.)").matcher(string.substring(1));

        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, matcher.group(1).toUpperCase());
        }

        matcher.appendTail(stringBuffer);

        return stringBuffer.toString();
    }

}
