package org.inlighting.util;

import java.io.File;

public class LocalFileSystemUtils {

    public static boolean delete(String src, boolean recursive) {
        File file = new File(src);
        return delete(file, recursive);
    }

    public static boolean delete(File file, boolean recursive) {
        if (! file.exists())
            return false;

        if (recursive) {
            File[] files = file.listFiles();
            if (files == null)
                return false;
            for (File f: files) {
                delete(f, false);
            }
            return file.delete();
        } else {
            if (file.isFile() || file.list() == null) {
                return file.delete();
            } else {
                return false;
            }
        }

    }
}
