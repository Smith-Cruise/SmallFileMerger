package org.inlighting;

import org.inlighting.conf.Configuration;

public class FileMergerFactory {
    private FileMergerFactory() {

    }

    public static FileMerger getInstance() {
        try {
            long blockSize = 10 * 1024 * 1024;
            long maxMemory = 512 * 1024 * 1024;
            Configuration configuration = new Configuration("output/", blockSize, maxMemory);
            FileMerger fileMerger = new FileMerger();
            fileMerger.initialize(configuration);
            return fileMerger;
        } catch (SFMException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
