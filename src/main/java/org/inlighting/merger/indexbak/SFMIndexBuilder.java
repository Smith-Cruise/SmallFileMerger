package org.inlighting.merger.indexbak;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SFMIndexBuilder {
    private final String MERGED_FILE;
    private final int KEY_LENGTH;

    private final int VALUE_LENGTH;

    private final int VERSION;

    private final DataOutputStream OUTPUT;

    private String minKey;

    private String maxKey;

    private char[] tempKey;

    private char[] tempValue;

    public SFMIndexBuilder(String mergedFile, int keyLength, int valueLength, int version, OutputStream outputStream) {
        MERGED_FILE = mergedFile;
        KEY_LENGTH = keyLength;
        VALUE_LENGTH = valueLength;
        VERSION = version;
        OUTPUT = new DataOutputStream(outputStream);
    }

    public void append(String filename, int offset, int length) throws IOException {
        SFMEntry entry = new SFMEntry(KEY_LENGTH, filename, offset, length);
        char[] temp = filename.toCharArray();
        char[] writeFilename = new char[KEY_LENGTH];
        System.arraycopy(temp, 0, writeFilename, 0, temp.length);
        for (char c: writeFilename) {
            OUTPUT.writeChar(c);
        }
        OUTPUT.writeInt(offset);
        OUTPUT.write(length);
    }

    public void finish() throws IOException {
        Trailer trailer = new Trailer(MERGED_FILE, KEY_LENGTH, VALUE_LENGTH, VERSION, minKey, maxKey);
        trailer.write(OUTPUT);
        OUTPUT.close();
    }


    public static SFMIndexBuilder create(String mergedFile, OutputStream outputStream) {
        return new SFMIndexBuilder(mergedFile, 1000, 4, 1, outputStream);
    }
}
