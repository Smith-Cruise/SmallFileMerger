package org.inlighting.protocol;

import java.io.File;
import java.io.IOException;

abstract class AbstractSFMClient implements SFMClient {

    protected void checkIsExist(String src) throws IOException {
        File file = new File(src);
        checkIsExist(file);
    }

    protected void checkIsExist(File file) throws IOException {
        if (file.exists()) {
            throw new IOException("File already exist.");
        }
    }
}
