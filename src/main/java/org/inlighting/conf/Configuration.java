package org.inlighting.conf;

public class Configuration {
    private String dataOutputFolder;

    private long blockSize;

    private long maxMemory;

    public Configuration(String dataOutputFolder, long blockSize, long maxMemory) {
        this.dataOutputFolder = dataOutputFolder;
        this.blockSize = blockSize;
        this.maxMemory = maxMemory;
    }

    public String getDataOutputFolder() {
        return dataOutputFolder;
    }

    public long getBlockSize() {
        return blockSize;
    }

    public long getMaxMemory() {
        return maxMemory;
    }
}
