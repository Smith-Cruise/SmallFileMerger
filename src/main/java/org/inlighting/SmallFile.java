package org.inlighting;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class SmallFile implements Comparable<SmallFile> {
    private String srcPath;

    private String destPath;

    private String indexName;

    private String fileName;

    private long fileSize;

    public SmallFile(String srcPath, String destPath) throws RuntimeException {
        this.srcPath = srcPath;
        if (! Utils.isValidDestPath(destPath)) {
            throw new RuntimeException("Invalid dest path.");
        }
        this.destPath = destPath;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public String getDestPath() {
        return destPath;
    }

    public long getFileSize() {
        if (fileSize == 0) {
            File file = new File(srcPath);
            fileSize = file.length();
        }
        return fileSize;
    }

    public String getIndexName() {
        if (this.indexName == null) {
            String index = StringUtils.substringBeforeLast(destPath, "/");
            if (index.length() == 0)
                this.indexName = "/";
            else
                this.indexName = index;
        }
        return this.indexName;
    }

    public String getFileName() {
        if (this.fileName == null) {
            this.fileName = StringUtils.substringAfterLast(destPath, "/");
        }
        return this.fileName;
    }

    public String getRealIndexName() {
        if (getIndexName().length() == 1) {
            return getIndexName() + "index";
        } else {
            return indexName + "/index";
        }
    }

    @Override
    public int compareTo(org.inlighting.SmallFile o) {
        return destPath.compareTo(o.destPath);
    }
}
