package org.inlighting.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SFMClient {

    void mkdir(String src) throws IOException;

    OutputStream create(String src) throws IOException;

    boolean delete(String src, boolean recursive) throws IOException;

    OutputStream append(String src) throws IOException;

    InputStream open(String src) throws IOException;

    void close() throws IOException;
}
