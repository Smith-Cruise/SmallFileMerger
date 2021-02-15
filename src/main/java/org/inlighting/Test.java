package org.inlighting;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class Test {

    static final Logger LOGGER = LogManager.getLogger(Test.class);

    public static void main(String[] args) {
        try {
            byte[] a = "Hello World".getBytes(StandardCharsets.UTF_8);
            System.out.println(a.length);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(a.length);
            byteBuffer.put(a);
            FileChannel out = new FileOutputStream("aa.txt").getChannel();
            byteBuffer.flip();
            out.write(byteBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
