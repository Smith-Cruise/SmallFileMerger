package org.inlighting.stream;

import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

abstract class FilterSFMOutputStream extends OutputStream {

    protected File srcFile;

    protected boolean append;

    private byte buf[];

    private int count;

    public FilterSFMOutputStream() {
        buf = new byte[8192];
    }

    private void flushBuffer() {
        if (count > 0) {
            writeOut(buf, 0, count);
            count = 0;
        }
    }

    public abstract void writeOut(byte[] buf, int off, int len);

    @Override
    public synchronized void write(int b) throws IOException {
        if (count >= buf.length) {
            flushBuffer();
        }
        buf[count++] = (byte)b;
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if (len >= buf.length) {
            /* If the request length exceeds the size of the output buffer,
               flush the output buffer and then write the data directly.
               In this way buffered streams will cascade harmlessly. */
            flushBuffer();
            writeOut(b, off, len);
            return;
        }
        if (len > buf.length - count) {
            flushBuffer();
        }
        System.arraycopy(b, off, buf, count, len);
        count += len;
    }

    @Override
    public void flush() throws IOException {
        flushBuffer();
    }

    @Override
    public void close() throws IOException {
        flush();
    }
}
