package org.inlighting;

public class Utils {

    public static boolean isValidDestPath(String destPath) {
        boolean valid = true;
        if (isEmpty(destPath))
            valid = false;
        if (! destPath.startsWith("/"))
            valid = false;
        if (destPath.split("/").length < 2)
            valid = false;
        if (destPath.endsWith("/"))
            valid = false;
        return valid;
    }

    public static String subStringBeforeLast(String str, String separator) {
        if (!isEmpty(str) && !isEmpty(separator)) {
            int pos = str.lastIndexOf(separator);
            return pos == -1 ? str : str.substring(0, pos);
        } else {
            return str;
        }
    }

    public static String substringAfterLast(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        } else if (isEmpty(separator)) {
            return "";
        } else {
            int pos = str.lastIndexOf(separator);
            return pos != -1 && pos != str.length() - separator.length() ? str.substring(pos + separator.length()) : "";
        }
    }

    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
