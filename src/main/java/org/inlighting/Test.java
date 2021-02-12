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
        FileChannel out = new FileOutputStream("out.txt").getChannel();
        FileChannel ina = new FileInputStream("a").getChannel();
        FileChannel inb = new FileInputStream("b").getChannel();
        ina.transferTo(0, 2048, out);
        inb.transferTo(0, 2048, out);
        out.close();
    }
}
