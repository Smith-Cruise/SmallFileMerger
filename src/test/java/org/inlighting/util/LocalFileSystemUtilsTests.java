package org.inlighting.util;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

public class LocalFileSystemUtilsTests {

    private static final String TEMP_FOLDER = "tmp_test/";

    @BeforeAll
    static void createFiles() {
        File temp = new File(TEMP_FOLDER);
        if (temp.exists())
            deleteFiles();

        temp.mkdir();
        File dir1 = new File(TEMP_FOLDER + "/dir1");
        dir1.mkdir();
        File dir2 = new File(TEMP_FOLDER + "/dir2");
        dir2.mkdir();
        File dir1dir = new File(TEMP_FOLDER + "/dir1/dir");
        dir1dir.mkdir();
        File dir1dirdir = new File(TEMP_FOLDER + "/dir1/dir/dir");
        dir1dirdir.mkdir();
    }

    @Test
    void simpleDelete() {
        File dir2 = new File(TEMP_FOLDER + "/dir2");
        boolean result = LocalFileSystemUtils.delete(dir2, false);
        assertEquals(true, result);
        assertEquals(false, dir2.exists());
    }

    @Test
    void recursiveDelete() {
        File dir1 = new File(TEMP_FOLDER + "/dir1");
        boolean result = LocalFileSystemUtils.delete(dir1, false);
        assertEquals(false, result);
        assertEquals(true, LocalFileSystemUtils.delete(dir1, true));
    }

    @AfterAll
    static void deleteFiles() {
        File dir1dirdir = new File(TEMP_FOLDER + "/dir1/dir/dir");
        dir1dirdir.delete();

        File dir1dir = new File(TEMP_FOLDER + "/dir1/dir");
        dir1dir.delete();

        File dir2 = new File(TEMP_FOLDER + "/dir2");
        dir2.delete();

        File dir1 = new File(TEMP_FOLDER + "/dir1");
        dir1.delete();

        File temp = new File(TEMP_FOLDER);
        temp.delete();

    }
}
