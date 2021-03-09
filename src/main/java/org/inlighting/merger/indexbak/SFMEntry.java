package org.inlighting.merger.indexbak;

import java.io.Serializable;

public class SFMEntry implements Serializable {
    private static final long serialVersionUID = 110111L;

    private char[] filename;

    private int offset;

    private int length;

    public SFMEntry(int keyLength, String filename, int offset, int length) {
        this.filename = new char[keyLength];
        char[] temp = filename.toCharArray();
        System.arraycopy(temp, 0, this.filename, 0, temp.length);
        this.offset = offset;
        this.length = length;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFilename() {
        return String.valueOf(filename);
    }

    public int getOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }
}
