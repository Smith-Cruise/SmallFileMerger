package org.inlighting.client;

import org.inlighting.SFMException;
import org.inlighting.stream.LocalSFMOutputStream;
import org.inlighting.util.LocalFileSystemUtils;
import org.inlighting.util.LocalFileSystemUtilsTests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class LocalSFMClientTests {

    private static LocalSFMClient localSFMClient;

    private static final String DATA_OUTPUT_FOLDER = "sfm/data";

    private static final String INDEX_OUTPUT_FOLDER = "sfm/index";

    @BeforeEach
    void createClient() throws IOException {
        localSFMClient = new LocalSFMClient();
        LocalFileSystemUtils.delete(DATA_OUTPUT_FOLDER, true);
        LocalFileSystemUtils.delete(INDEX_OUTPUT_FOLDER, true);
        localSFMClient.mkdir(DATA_OUTPUT_FOLDER);
        localSFMClient.mkdir(INDEX_OUTPUT_FOLDER);
    }

    @AfterEach
    void closeClient() throws IOException {
        localSFMClient.close();
    }

    @Test
    void uploadSimpleFile() throws IOException {
        OutputStream output = localSFMClient.create("/hello.txt");
        output.write("Hello World".getBytes(StandardCharsets.UTF_8));
        output.close();
    }

    @Test
    void uploadMultiFiles() throws IOException {
        write("/a/a.txt", "/a/a.txt");
        write("/a/b.txt", "/a/b.txt");
        write("/a/c.txt", "/a/c.txt");
        write("/a.txt", "/a.txt");
        write("/b.txt", "/b.txt");
        write("/b/a.txt", "/b/a.txt");
        write("/b/b.txt", "/b/b.txt");
    }

    private void write(String path, String contents) throws IOException {
        OutputStream output = localSFMClient.create(path);
        output.write(contents.getBytes(StandardCharsets.UTF_8));
        output.close();
    }
}
