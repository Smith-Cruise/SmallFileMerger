package org.inlighting;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Test {

    static final Logger LOGGER = LogManager.getLogger(Test.class);

    public static void main(String[] args) throws IOException {
        byte[] content = new byte[4];
        change(content);
        System.out.println(content[0]);
    }

    private static void change(byte[] content) {
        content[0] = 'a';
    }
}
