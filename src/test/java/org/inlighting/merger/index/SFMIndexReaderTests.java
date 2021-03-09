package org.inlighting.merger.index;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

public class SFMIndexReaderTests {

    @Test
    void read() throws IOException {
        FileInputStream inputStream = new FileInputStream("index.data");
        SFMIndexReader reader = SFMIndexReader.create(inputStream);
        reader.get("5555");
    }
}
