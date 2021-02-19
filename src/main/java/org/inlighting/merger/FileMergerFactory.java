package org.inlighting.merger;

import org.inlighting.SFMException;
import org.inlighting.conf.Configuration;

public class FileMergerFactory {

    private static FileMerger fileMerger;

    private FileMergerFactory() {

    }

    public static synchronized FileMerger getInstance() {
        return fileMerger;
    }

//    public static LocalFileMerger getDefaultInstance() {
//        long blockSize = 10 * 1024 * 1024;
//        long maxMemory = 512 * 1024 * 1024;
//        return getInstance("output/", "index/", blockSize, maxMemory);
//    }
//
//    public static LocalFileMerger getInstance(String dataOutputFolder, String indexOutputFolder, long blockSize, long maxMemory) {
//        Configuration configuration = new Configuration(dataOutputFolder, indexOutputFolder ,blockSize, maxMemory);
//        return getInstance(configuration);
//    }
//
//    public static synchronized LocalFileMerger getInstance(Configuration configuration) {
//        if (localFileMerger == null) {
//            try {
//                localFileMerger = new LocalFileMerger();
//                localFileMerger.initialize(configuration);
//            } catch (SFMException exception) {
//                exception.printStackTrace();
//                return null;
//            }
//        }
//        return localFileMerger;
//    }
//
//    public static synchronized void clean() {
//        // todo
//        // localFileMerger.clean();
//        localFileMerger = null;
//    }
}
