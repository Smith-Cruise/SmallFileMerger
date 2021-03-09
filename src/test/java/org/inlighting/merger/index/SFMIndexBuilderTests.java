package org.inlighting.merger.index;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class SFMIndexBuilderTests {

    @Test
    void add() throws IOException {
        FileOutputStream outputStream = new FileOutputStream("index.data");
        SFMIndexBuilder builder = SFMIndexBuilder.create("hello", outputStream);
        for (int i=1000; i<=9999; i++) {
            builder.append(String.valueOf(i), i, i);
        }
        builder.finish();
    }
}
