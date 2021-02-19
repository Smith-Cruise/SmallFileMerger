package org.inlighting.merger;

import org.apache.commons.lang3.StringUtils;
import org.inlighting.SFMException;
import org.inlighting.util.Utils;

import java.io.File;

public class SmallFile implements Comparable<SmallFile> {
    private String srcPath;

    private String destPath;

    private String indexName;

    private String fileName;

    private boolean inMemory;

    private long fileSize;

    public SmallFile(String srcPath, String destPath) throws SFMException {
       this(srcPath, destPath, false, 0);

    }

    public SmallFile(String destPath, long fileSize) throws SFMException {
        this(null, destPath, true, fileSize);
    }

    public SmallFile(String srcPath, String destPath, boolean inMemory, long fileSize) throws SFMException {
        this.inMemory = inMemory;
        this.srcPath = srcPath;
        if (! Utils.isValidDestPath(destPath)) {
            throw new SFMException("Invalid dest path.");
        }
        this.destPath = destPath;
        this.fileSize = fileSize;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public String getDestPath() {
        return destPath;
    }

    public long getFileSize() {
        if (fileSize == 0 && !inMemory) {
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

    public boolean isInMemory() {
        return inMemory;
    }

    public void setInMemory(boolean inMemory) {
        this.inMemory = inMemory;
    }

    @Override
    public int compareTo(SmallFile o) {
        return destPath.compareTo(o.destPath);
    }
}
