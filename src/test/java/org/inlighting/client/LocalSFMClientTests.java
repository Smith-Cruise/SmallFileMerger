package org.inlighting.client;

import org.inlighting.util.LocalFileSystemUtils;
import org.inlighting.util.LocalFileSystemUtilsTests;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LocalSFMClientTests {

    private static LocalSFMClient localSFMClient;

    private static final String DATA_OUTPUT_FOLDER = "sfm/data";

    private static final String INDEX_OUTPUT_FOLDER = "sfm/index";

    @BeforeAll
    static void createClient() throws IOException {
        localSFMClient = new LocalSFMClient();
        LocalFileSystemUtils.delete(DATA_OUTPUT_FOLDER, true);
        LocalFileSystemUtils.delete(INDEX_OUTPUT_FOLDER, true);
        localSFMClient.mkdir(DATA_OUTPUT_FOLDER);
        localSFMClient.mkdir(INDEX_OUTPUT_FOLDER);
    }

    @Test
    void hello() {

    }
}
