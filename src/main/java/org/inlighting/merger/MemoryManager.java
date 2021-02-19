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

    private final Map<String, MemoryEntity> fileMap;

    private final FileMerger fileMerger;

    public MemoryManager(Configuration configuration, FileMerger fileMerger) {
        MAX_MEMORY = configuration.getMaxMemory();
        fileMap = new HashMap<>();
        this.fileMerger = fileMerger;
    }

    public MemoryManager(Configuration configuration) {
        this(configuration, null);
    }

    public void rmFile(String destPath) throws SFMException {
        fileMap.remove(destPath);
    }

    // todo Big file, the byte array is too big. byte[] should just be a buffer,.
    public void storeFile(String destPath, byte[] content) throws SFMException {
        LOGGER.debug(String.format("Store <%s> in memory.", destPath));
        if (fileMap.containsKey(destPath)) {
            throw new SFMException("Key "+ destPath +" already exists");
        }

        long fileSize = content.length;
        while ((currentMemory + fileSize) >= MAX_MEMORY) {
            LOGGER.debug("Key "+ destPath +" out of memory");
            if (fileMerger == null) {
                throw new SFMException("LocalFileMerger is not initialized.");
            }
            fileMerger.mergeSmallestQueue();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // todo long -> int
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(content.length);
        byteBuffer.put(content);
        System.out.println(byteBuffer.remaining());
        currentMemory += fileSize;
        fileMap.put(destPath, new MemoryEntity(fileSize, byteBuffer));
    }

    public ByteBuffer getFile(String destPath) throws SFMException {
        if (! fileMap.containsKey(destPath)) {
            throw new SFMException("Key "+ destPath +" isn't exists");
        }

        MemoryEntity memoryEntity = fileMap.get(destPath);
        memoryEntity.byteBuffer.flip();
        return memoryEntity.byteBuffer;
    }


    private class MemoryEntity {
        private long size;

        private ByteBuffer byteBuffer;

        public MemoryEntity(long size, ByteBuffer byteBuffer) {
            this.size = size;
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
