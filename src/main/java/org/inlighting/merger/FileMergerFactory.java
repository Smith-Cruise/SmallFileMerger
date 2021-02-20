package org.inlighting.merger;

import org.inlighting.SFMException;
import org.inlighting.conf.Configuration;

public class FileMergerFactory {

    private static FileMerger fileMerger;

    private FileMergerFactory() {

    }

    public static synchronized FileMerger getInstance(Class<? extends FileMerger> cls, Configuration configuration) {
        try {
            if (fileMerger == null) {
                if (cls == LocalFileMerger.class) {
                    fileMerger = new LocalFileMerger(configuration);
                    return fileMerger;
                } else {
                    // todo
                    return null;
                }
            } else {
                if (fileMerger.getClass() == cls) {
                    return fileMerger;
                } else {
                    // todo
                    return null;
                }
            }
        } catch (SFMException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Configuration getDefaultConfiguration() {
        String dataOutputFolder = "sfm/data";
        String indexOutputFolder = "sfm/index";
        long blockSize = 10 * 1024 * 1024;
        long maxMemory = 512 * 1024 * 1024;
        Configuration configuration = new Configuration(dataOutputFolder, indexOutputFolder ,blockSize, maxMemory);
        return configuration;
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
