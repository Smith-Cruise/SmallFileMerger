package org.inlighting;

import org.inlighting.conf.Configuration;

public class FileMergerFactory {
    private FileMergerFactory() {

    }

    public static FileMerger getInstance() {
        long blockSize = 10 * 1024 * 1024;
        long maxMemory = 512 * 1024 * 1024;
        return getInstance("output/", "index/", blockSize, maxMemory);
    }

    public static FileMerger getInstance(String dataOutputFolder, String indexOutputFolder, long blockSize, long maxMemory) {
        Configuration configuration = new Configuration(dataOutputFolder, indexOutputFolder ,blockSize, maxMemory);
        return getInstance(configuration);
    }

    public static FileMerger getInstance(Configuration configuration) {
        try {
            FileMerger fileMerger = new FileMerger();
            fileMerger.initialize(configuration);
            return fileMerger;
        } catch (SFMException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
