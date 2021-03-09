package org.inlighting.util;

import java.io.File;

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

    public static void deleteIfExist(File file) {
        if(!file.exists()) return;

        if(file.isFile() || file.list()==null) {
            file.delete();
        }else {
            File[] files = file.listFiles();
            for(File a:files) {
                deleteIfExist(a);
            }
            file.delete();
        }

    }

    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static byte getUnsignedByte (int a) {
        if (a < 0 || a > 255) {
            throw new IllegalArgumentException("Invalid number. Range 0<=x<=255.");
        }
        return (byte) a;
    }

    public static int readUnsignedByte(byte a) {
        return a & 0x0FF;
    }
}
