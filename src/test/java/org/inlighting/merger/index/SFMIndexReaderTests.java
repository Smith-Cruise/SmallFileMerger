package org.inlighting.merger.index;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

public class SFMIndexReaderTests {

    @Test
    void read() throws IOException {
        FileInputStream inputStream = new FileInputStream("index");
        SFMIndexReader reader = SFMIndexReader.create(inputStream);
        KV kv = reader.get("5555");
        System.out.println(kv.toString());
    }
}
