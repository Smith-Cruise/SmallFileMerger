package org.inlighting;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SmallFileTests {

    private static final String PATH_NAME = "file.txt";

    private static File file = new File(PATH_NAME);

    @BeforeAll
    static void writeFile() {
        try {
            if (file.exists()) {
                throw new IOException(PATH_NAME + "is already exists");
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write("Hello World".getBytes(StandardCharsets.UTF_8));
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void fileSizeTest() {
        SmallFile smallFile = new SmallFile(PATH_NAME, "/hello");
        assertEquals(11, smallFile.getFileSize());
    }

    @Test
    void nameTest() {
        SmallFile smallFile1 = new SmallFile(PATH_NAME, "/hello");
        assertEquals("/", smallFile1.getIndexName());
        assertEquals("hello", smallFile1.getFileName());
        assertEquals("/index", smallFile1.getRealIndexName());

        SmallFile smallFile2 = new SmallFile(PATH_NAME, "/hello/a");
        assertEquals("/hello", smallFile2.getIndexName());
        assertEquals("a", smallFile2.getFileName());
        assertEquals("/hello/index", smallFile2.getRealIndexName());
    }

    @AfterAll
    static void deleteFile() {
        file.deleteOnExit();
    }
}
