package org.inlighting;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UtilsTests {

    @Test
    void generateA_1M_File() {
//        generateFile("hello", 'a', 1024);
    }

    public static void generateFile(String filename, char a, long size) {
        try {
            FileOutputStream outputStream = new FileOutputStream(filename);
            outputStream.write(getRepeatedStrings(a, size));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] getRepeatedStrings(char a, long times) {
        StringBuilder stringBuilder = new StringBuilder();
        for (long i=0; i < times; i++) {
            stringBuilder.append(a);
        }
        return stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
    }
}
