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



    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
