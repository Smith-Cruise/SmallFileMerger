package org.inlighting;

import org.inlighting.conf.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MemoryManagerTests {

    private static Configuration configuration;

    private static MemoryManager memoryManager;

    @BeforeAll
    static void  initialize() {
        configuration = new Configuration(null, null, 0, 512 * 1024 * 1024);
        memoryManager = new MemoryManager(configuration);
    }

    @Test
    void putAndGetFile() throws SFMException {
        byte[] content = "Hello World".getBytes(StandardCharsets.UTF_8);
        memoryManager.putFile("test", content);
        ByteBuffer get = memoryManager.getFile("test");
        System.out.println(get.remaining());
        byte[] got = new byte[11];
        get.get(got);
        System.out.println(Arrays.toString(got));
    }
}
