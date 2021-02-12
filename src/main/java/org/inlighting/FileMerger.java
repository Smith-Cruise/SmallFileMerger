package org.inlighting;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;

public class FileMerger {

    static final Logger LOGGER = LogManager.getLogger(FileMerger.class);

    private final Map<String, List<SmallFile>> mergedMap;

    private final Map<String, Long> mergedMapSize;

    private final String MERGED_OUTPUT_FOLDER;

    // MB
    private final int BLOCK_SIZE;

    public FileMerger(String mergedOutputFolder, int blockSize) {
        this.MERGED_OUTPUT_FOLDER = mergedOutputFolder;
        this.BLOCK_SIZE = blockSize;
        this.mergedMap = new HashMap<>(5);
        this.mergedMapSize = new HashMap<>(5);
    }

    public void upload(String srcPath, String destPath) {
        SmallFile smallFile = new SmallFile(srcPath, destPath);
        LOGGER.debug("Upload a file path:" + srcPath);
        putFileInQueue(smallFile);
    }

    private void putFileInQueue(SmallFile smallFile) {
        String indexName = smallFile.getIndexName();
        long fileSize = smallFile.getFileSize();

        if (mergedMap.containsKey(indexName)) {
            List<SmallFile> list = mergedMap.get(indexName);
            list.add(smallFile);
        } else {
            List<SmallFile> list = new ArrayList<>();
            list.add(smallFile);
            mergedMap.put(indexName, list);
        }

        if (mergedMapSize.containsKey(indexName)) {
            long beforeSize = mergedMapSize.get(indexName);
            long nowSize = beforeSize + fileSize;
            if (nowSize >= BLOCK_SIZE * 1024) {
                mergeFile(indexName);
            } else {
                mergedMapSize.replace(indexName, beforeSize, nowSize);
            }
        } else {
            mergedMapSize.put(indexName, fileSize);
        }
    }

    private void mergeFile(String indexName) {
        List<SmallFile> list = mergedMap.get(indexName);
        Collections.sort(list);
        try {
            FileChannel output = new FileOutputStream(getStoreName()).getChannel();
            for (SmallFile smallFile: list) {
                FileChannel input = new FileInputStream(smallFile.getSrcPath()).getChannel();
                input.transferTo(0, input.size(), output);
                input.close();
            }
            output.close();
            mergedMap.remove(indexName);
            mergedMapSize.remove(indexName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getStoreName() {
        return String.valueOf(System.currentTimeMillis()) + ".data";
    }


    public boolean uploadFolder(String srcPath) {
        return false;
    }

    // upload all files before close
    public void close() {

    }
}


