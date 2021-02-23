package org.inlighting.merger.index;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;

public class SFMIndexBuilderTests {

    @Test
    void create() throws IOException {
        FileOutputStream outputStream = new FileOutputStream("index");
        SFMIndexBuilder builder = SFMIndexBuilder.create("index", outputStream);
        builder.append("a", 0, 5);
        builder.append("b", 5, 1);
        builder.finish();
        outputStream.close();
    }
}
