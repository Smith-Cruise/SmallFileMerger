package org.inlighting.stream;

import org.inlighting.merger.LocalFileMerger;
import org.inlighting.merger.FileMergerFactory;
import org.inlighting.conf.Configuration;

import java.io.*;

public class LocalSFMOutputStream extends FilterSFMOutputStream {

    private boolean append;

    private LocalFileMerger localFileMerger;

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
        localFileMerger = FileMergerFactory.getInstance(configuration);
    }

    @Override
    public void writeOut(byte[] buf, int off, int len) {

    }
}
