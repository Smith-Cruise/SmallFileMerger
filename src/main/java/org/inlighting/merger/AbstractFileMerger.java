package org.inlighting.merger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inlighting.SFMException;
import org.inlighting.conf.Configuration;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractFileMerger implements FileMerger{

    static final Logger LOGGER = LogManager.getLogger(AbstractFileMerger.class);

    protected boolean isClosed = true;

    protected final Map<String, List<SmallFile>> MERGE_MAP;

    protected final Map<String, Long> MERGE_MAP_SIZE;

    protected final MemoryManager MEMORY_MANAGER;

    protected final String INDEX_OUTPUT_FOLDER;

    protected final String DATA_OUTPUT_FOLDER;

    protected final long BLOCK_SIZE;

    public AbstractFileMerger(Configuration configuration) throws SFMException {
        INDEX_OUTPUT_FOLDER = configuration.getIndexOutputFolder();
        DATA_OUTPUT_FOLDER = configuration.getDataOutputFolder();
        MEMORY_MANAGER = new MemoryManager(configuration, this);
        BLOCK_SIZE = configuration.getBlockSize();
        MERGE_MAP = new ConcurrentHashMap<>(5);
        MERGE_MAP_SIZE = new ConcurrentHashMap<>(5);
        isClosed = false;
        checkSFM();
    }

    @Override
    public void upload(String src, byte[] contents) throws SFMException {
        checkClosed();
        SmallFile smallFile = new SmallFile(src, contents.length);
        MEMORY_MANAGER.storeFile(src, contents);
        LOGGER.debug(String.format("Upload a file to: %s", src));
        putFileInQueue(smallFile);
    }

    protected void putFileInQueue(SmallFile smallFile) throws SFMException {
        String indexName = smallFile.getIndexName();
        long fileSize = smallFile.getFileSize();
        checkQueueFull(indexName, fileSize);
        if (MERGE_MAP_SIZE.containsKey(indexName)) {
            long currentSize = MERGE_MAP_SIZE.get(indexName);
            MERGE_MAP_SIZE.replace(indexName, currentSize, currentSize + fileSize);
        } else {
            MERGE_MAP_SIZE.put(indexName, fileSize);
        }

        if (MERGE_MAP.containsKey(indexName)) {
            List<SmallFile> list = MERGE_MAP.get(indexName);
            list.add(smallFile);
        } else {
            List<SmallFile> list = new ArrayList<>(5);
            list.add(smallFile);
            MERGE_MAP.put(indexName, list);
        }
    }

    // merge if queue will become full.
    protected void checkQueueFull(String indexName, long expectSize) throws SFMException {
        if (! MERGE_MAP_SIZE.containsKey(indexName)) {
            return;
        }
        long beforeSize = MERGE_MAP_SIZE.get(indexName);
        long futureSize = beforeSize + expectSize;
        if (futureSize >= BLOCK_SIZE) {
            LOGGER.debug(String.format("(Before %d -> Future %d), need to merge %s", beforeSize, futureSize, indexName));
            merge(indexName);
        }
    }

    protected String getStoreName() {
        return UUID.randomUUID().toString() + ".data";
    }

    @Override
    public void mergeSmallestQueue() throws SFMException {
        long smallestSize = BLOCK_SIZE * 2 * 1024 * 1024;
        String smallestKey = null;
        for (String key: MERGE_MAP_SIZE.keySet()) {
            long size = MERGE_MAP_SIZE.get(key);
            if (size < smallestSize) {
                smallestSize = size;
                smallestKey = key;
            }
        }
        merge(smallestKey);
    }

    @Override
    public void close() throws SFMException {
        LOGGER.debug("Start to close FileMerger");
        for (String key: MERGE_MAP_SIZE.keySet()) {
            merge(key);
        }
        isClosed = true;
    }

    public void checkClosed() throws SFMException {
        if (isClosed) {
            throw new SFMException("SFM is already closed.");
        }
    }
}
