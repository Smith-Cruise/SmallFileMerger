package org.inlighting;

import org.inlighting.conf.Configuration;
import org.inlighting.merger.MemoryManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class MemoryManagerTests {

    private static Configuration configuration;

    private static MemoryManager memoryManager;

    @BeforeAll
    static void  initialize() {
        configuration = new Configuration(null, null, 0, 512 * 1024 * 1024);
        memoryManager = new MemoryManager(configuration);
    }

//    @Test
//    void putAndGetFile() throws SFMException {
//        byte[] content = "Hello World".getBytes(StandardCharsets.UTF_8);
//        final String DEST_PATH = "test";
//        memoryManager.putFile(DEST_PATH, content);
//        ByteBuffer get = memoryManager.getFile(DEST_PATH);
//        assertEquals(11, get.remaining());
//        memoryManager.rmFile(DEST_PATH);
//        assertThrows(SFMException.class, () -> memoryManager.getFile(DEST_PATH));
//    }
}
