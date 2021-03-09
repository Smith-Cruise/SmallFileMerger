package org.inlighting.merger.index;

public class KV {

    private String filename;

    private int offset;

    private int length;

    public KV() {
    }

    public KV(String filename, int offset, int length) {
        this.filename = filename;
        this.offset = offset;
        this.length = length;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "KV{" +
                "filename='" + filename + '\'' +
                ", offset=" + offset +
                ", length=" + length +
                '}';
    }
}
