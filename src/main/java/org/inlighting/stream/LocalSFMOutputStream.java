package org.inlighting.stream;

import org.inlighting.SFMException;
import org.inlighting.merger.LocalFileMerger;
import org.inlighting.merger.FileMergerFactory;
import org.inlighting.conf.Configuration;

import java.io.*;

public class LocalSFMOutputStream extends FilterSFMOutputStream {

    private final LocalFileMerger localFileMerger;

    public LocalSFMOutputStream(String src) throws FileNotFoundException {
        this(src, false);
    }

    public LocalSFMOutputStream(String src, boolean append) throws FileNotFoundException {
        this(new File(src), append);
    }

    public LocalSFMOutputStream(File file) throws FileNotFoundException {
        this(file, false);
    }

    public LocalSFMOutputStream(File file, boolean append) throws FileNotFoundException {
        Configuration configuration = new Configuration("sfm/data", "sfm/index",
                4*1024*1024, 128*1024*1024);
        this.srcFile = file;
        this.append = append;
        localFileMerger = (LocalFileMerger) FileMergerFactory.getInstance(LocalFileMerger.class, configuration);
    }

    @Override
    public void writeOut(byte[] buf, int off, int len) {
        byte[] contents = new byte[len];
        System.arraycopy(buf, off, contents, 0, len);
        try {
            localFileMerger.upload(srcFile.getPath(), contents);
        } catch (SFMException exception) {
            exception.printStackTrace();
        }
    }
}
