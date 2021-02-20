package org.inlighting.util;

import java.io.File;

public class LocalFileSystemUtils {

    public static boolean delete(String src, boolean recursive) {
        File file = new File(src);
        return delete(file, recursive);
    }

    // if file is directory and set recursive false, will not delete.
    public static boolean delete(File file, boolean recursive) {
        if (! file.exists())
            return false;

        if (!recursive && file.isDirectory()) {
            if (file.listFiles().length == 0)
                return file.delete();
            else
                return false;
        }

        if (file.isFile() && file.list() == null) {
            return file.delete();
        } else {
            File[] files = file.listFiles();
            if (files == null)
                return false;
            for (File f: files) {
                delete(f, true);
            }
            return file.delete();
        }
    }
}
