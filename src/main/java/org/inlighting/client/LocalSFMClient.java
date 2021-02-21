package org.inlighting.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inlighting.merger.FileMergerFactory;
import org.inlighting.merger.LocalFileMerger;
import org.inlighting.stream.LocalSFMOutputStream;
import org.inlighting.util.LocalFileSystemUtils;

import java.io.*;

public class LocalSFMClient extends AbstractSFMClient{

    private static final Logger LOGGER = LogManager.getLogger(LocalSFMClient.class);

    @Override
    public void mkdir(String src) throws IOException {
        File file = new File(src);
        checkIsExist(file);
        file.mkdir();
    }

    @Override
    public OutputStream create(String src) throws IOException {
        File file = new File(src);
        checkIsExist(file);
        return new LocalSFMOutputStream(file);
    }

    @Override
    public boolean delete(String src, boolean recursive) throws IOException {
        File file = new File(src);
        checkIsExist(file);
        return LocalFileSystemUtils.delete(src, recursive);
    }

    @Override
    public OutputStream append(String src) throws IOException {
        File file = new File(src);
        checkIsExist(file);
        return new LocalSFMOutputStream(src, true);
    }

    @Override
    public InputStream open(String src) throws IOException {
        return null;
    }

    @Override
    public void close() throws IOException {
        LocalFileMerger localFileMerger = (LocalFileMerger) FileMergerFactory.getInstance(LocalFileMerger.class, null);
        localFileMerger.close();
    }
}
