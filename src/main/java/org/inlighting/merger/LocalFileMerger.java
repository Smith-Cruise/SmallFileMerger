package org.inlighting.merger;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inlighting.SFMException;
import org.inlighting.conf.Configuration;
import org.inlighting.util.Utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

public class LocalFileMerger extends AbstractFileMerger {

    static final Logger LOGGER = LogManager.getLogger(LocalFileMerger.class);

    public LocalFileMerger(Configuration configuration) throws SFMException {
        super(configuration);
    }


    @Override
    public void checkSFM() throws SFMException {
        // check sfm data output folder
        File data = new File(DATA_OUTPUT_FOLDER);
        if (! data.exists())
            throw new SFMException("SFM data output folder didn't exists.");
        if (! data.isDirectory())
            throw new SFMException("SFM data output folder isn't a directory.");
        // check sfm index output folder
        File index = new File(INDEX_OUTPUT_FOLDER);
        if (! index.exists())
            throw new SFMException("SFM index output folder didn't exists.");
        if (! index.isDirectory())
            throw new SFMException("SFM index output folder isn't a directory.");
    }

    @Override
    public void merge(String indexName) throws SFMException {
        try {
            String dataFilename = getStoreName();
            LOGGER.debug(String.format("Write data output %s, merge index %s", dataFilename, indexName));
            FileChannel output = new FileOutputStream(DATA_OUTPUT_FOLDER + dataFilename).getChannel();
            List<SmallFile> list = MERGE_MAP.get(indexName);
            Collections.sort(list);
            for (SmallFile smallFile: list) {
                ByteBuffer contents = MEMORY_MANAGER.getFile(smallFile.getDestPath());
                output.write(contents);
                MEMORY_MANAGER.rmFile(smallFile.getDestPath());
            }
            output.close();
        } catch (IOException e) {
            throw new SFMException(e.getCause());
        }

        MERGE_MAP.remove(indexName);
        MERGE_MAP_SIZE.remove(indexName);
    }

    //    static final Logger LOGGER = LogManager.getLogger(LocalFileMerger.class);
//
//    private Map<String, List<SmallFile>> mergedMap;
//
//    private Map<String, Long> mergedMapSize;
//
//    private String dataOutputFolder;
//
//    private MemoryManager memoryManager;
//
//    private long blockSize;
//
//    private boolean isInitialize = false;
//
//    private void checkInitialize() throws SFMException {
//        if (! isInitialize) {
//            throw new SFMException("File merger didn't initialized");
//        }
//    }
//
//    public void initialize(Configuration configuration) throws SFMException {
//        if (isInitialize) {
//            throw new SFMException("File merger is already initialized");
//        }
//        dataOutputFolder = configuration.getDataOutputFolder();
//        File file = new File(dataOutputFolder);
//        Utils.deleteIfExist(file);
//        file.mkdir();
//        blockSize = configuration.getBlockSize();
//        memoryManager = new MemoryManager(configuration, this);
//        mergedMap = new HashMap<>(5);
//        mergedMapSize = new HashMap<>(5);
//        isInitialize = true;
//    }
//
//    // upload file from local disk
//    public void upload(String srcPath, String destPath) throws SFMException {
//        checkInitialize();
//        SmallFile smallFile = new SmallFile(srcPath, destPath);
//        LOGGER.debug("Upload a file path:" + srcPath);
//        putFileInQueue(smallFile);
//    }
//
//    // store in memory
//    public void upload(String destPath, byte[] content) throws SFMException {
//        SmallFile smallFile = new SmallFile(destPath, content.length);
//        memoryManager.putFile(destPath, content);
//        LOGGER.debug("Upload a file in memory " + destPath);
//        putFileInQueue(smallFile);
//    }
//
//
//    private void mergeIfQueueFull(String indexName, long expectSize) {
//        if (mergedMapSize.containsKey(indexName)) {
//            long beforeSize = mergedMapSize.get(indexName);
//            long nowSize = beforeSize + expectSize;
//            LOGGER.debug("Before size "+beforeSize+", now size "+nowSize);
//            if (nowSize > blockSize) {
//                LOGGER.debug("Need to merge file: " + indexName);
//                List<SmallFile> list = mergedMap.get(indexName);
//                Collections.sort(list);
//                try {
//                    String fileName = getStoreName();
//                    FileChannel output = new FileOutputStream(dataOutputFolder + fileName).getChannel();
//                    for (SmallFile smallFile: list) {
//                        if (smallFile.isInMemory()) {
//                            ByteBuffer content = memoryManager.getFile(smallFile.getDestPath());
//
//                            LOGGER.debug("write file "+ fileName);
//                            output.write(content);
////                            ByteBuffer b = ByteBuffer.allocateDirect(10);
////                            b.putChar('a');
////                            output.write(b);
//                            memoryManager.rmFile(smallFile.getDestPath());
//                        } else {
//                            FileChannel input = new FileInputStream(smallFile.getSrcPath()).getChannel();
//                            input.transferTo(0, input.size(), output);
//                            input.close();
//                        }
//
//                    }
//                    output.close();
//                    mergedMap.remove(indexName);
//                    mergedMapSize.remove(indexName);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//    }
//
//    private void putFileInQueue(SmallFile smallFile) throws SFMException {
//        String indexName = smallFile.getIndexName();
//        long fileSize = smallFile.getFileSize();
//        mergeIfQueueFull(indexName, fileSize);
//        if (mergedMapSize.containsKey(indexName)) {
//            long currentSize = mergedMapSize.get(indexName);
//            mergedMapSize.replace(indexName, currentSize, currentSize + fileSize);
//        } else {
//            mergedMapSize.put(indexName, fileSize);
//        }
//
//        if (mergedMap.containsKey(indexName)) {
//            List<SmallFile> list = mergedMap.get(indexName);
//            list.add(smallFile);
//        } else {
//            List<SmallFile> list = new ArrayList<>();
//            list.add(smallFile);
//            mergedMap.put(indexName, list);
//        }
//    }
//
//    public void mergeSmallestQueue() throws SFMException {
//        // todo
//        LOGGER.debug("merge smallest queue");
//    }
//
//    private void mergeFile(String indexName) throws SFMException {
//
//    }
//
//    private String getStoreName() {
//        return String.valueOf(System.currentTimeMillis()) + ".data";
//    }
//
//
//    public boolean uploadFolder(String srcPath) throws SFMException {
//        return false;
//    }
//
//    // upload all files before close
//    public void close() {
//        isInitialize = false;
//    }
}


