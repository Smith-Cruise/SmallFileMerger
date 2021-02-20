package org.inlighting.merger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inlighting.SFMException;
import org.inlighting.conf.Configuration;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

// <destPath, ByteBuffer>
public class MemoryManager {

    static final Logger LOGGER = LogManager.getLogger(MemoryManager.class);

    private final long MAX_MEMORY;

    private long currentMemory = 0;

    private final Map<String, MemoryEntity> FILE_MAP;

    private final FileMerger FILE_MERGER;

    public MemoryManager(Configuration configuration, FileMerger fileMerger) {
        MAX_MEMORY = configuration.getMaxMemory();
        FILE_MAP = new HashMap<>();
        FILE_MERGER = fileMerger;
    }

    public MemoryManager(Configuration configuration) {
        this(configuration, null);
    }

    public void rmFile(String destPath) throws SFMException {
        FILE_MAP.remove(destPath);
    }

    // todo Big file, the byte array is too big. byte[] should just be a buffer,.
    public void storeFile(String destPath, byte[] content) throws SFMException {
        LOGGER.debug(String.format("Store <%s> in memory.", destPath));
        if (FILE_MAP.containsKey(destPath)) {
            throw new SFMException(String.format("Key %s already exists.", destPath));
        }

        long fileSize = content.length;
        while ((currentMemory + fileSize) >= MAX_MEMORY) {
            LOGGER.debug(String.format("Key %s out of memory", destPath));
            if (FILE_MERGER == null) {
                throw new SFMException("FileMerger is not initialized.");
            }
            FILE_MERGER.mergeSmallestQueue();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // todo long -> int
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(content.length);
        byteBuffer.put(content);
        currentMemory += fileSize;
        FILE_MAP.put(destPath, new MemoryEntity(fileSize, byteBuffer));
    }

    public ByteBuffer getFile(String destPath) throws SFMException {
        if (! FILE_MAP.containsKey(destPath)) {
            throw new SFMException(String.format("Key %s isn't exists", destPath));
        }

        MemoryEntity memoryEntity = FILE_MAP.get(destPath);
        memoryEntity.byteBuffer.flip();
        return memoryEntity.byteBuffer;
    }


    private static class MemoryEntity {
        private long size;

        private ByteBuffer byteBuffer;

        public MemoryEntity(long size, ByteBuffer byteBuffer) {
            this.size = size;
            this.byteBuffer = byteBuffer;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public void setByteBuffer(ByteBuffer byteBuffer) {
            this.byteBuffer = byteBuffer;
        }

        public long getSize() {
            return size;
        }

        public ByteBuffer getByteBuffer() {
            return byteBuffer;
        }
    }

}
