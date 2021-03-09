package org.inlighting.merger.indexbak;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class Trailer implements Serializable {
    private static final long serialVersionUID = 110110L;

    private char[] mergedFile = new char[255];

    private short keyLength;

    private short valueLength;

//    private transient byte[] bloomFilter = new byte[16384];

    private short version;

    char[] minKey = new char[100];

    char[] maxKey = new char[100];

    public Trailer(String mergedFile, int keyLength, int valueLength, int version, String minKey, String maxKey) {
        setMergedFile(mergedFile);
        this.keyLength = (short) keyLength;
        this.valueLength = (short) valueLength;
//        setBloomFilter(bloomFilter);
        this.version = (short) version;
        setMinKey(minKey);
        setMaxKey(maxKey);
    }

    public String getMergedFile() {
        return String.valueOf(mergedFile);
    }

    public void setMergedFile(String mergedFile) {
        char[] array = mergedFile.toCharArray();
        System.arraycopy(array, 0, this.mergedFile, 0, array.length);
    }

    public int getKeyLength() {
        return keyLength;
    }

    public void setKeyLength(int keyLength) {
        this.keyLength = (short) keyLength;
    }

    public int getValueLength() {
        return valueLength;
    }

    public void setValueLength(int valueLength) {
        this.valueLength = (short) valueLength;
    }

//    public byte[] getBloomFilter() {
//        return bloomFilter;
//    }
//
//    public void setBloomFilter(byte[] bloomFilter) {
//        System.arraycopy(bloomFilter, 0, this.bloomFilter, 0, bloomFilter.length);
//    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = (short) version;
    }

    public String getMinKey() {
        return String.valueOf(minKey);
    }

    public void setMinKey(String minKey) {
        char[] array = minKey.toCharArray();
        System.arraycopy(array, 0, this.minKey, 0, array.length);
    }

    public String getMaxKey() {
        return String.valueOf(maxKey);
    }

    public void setMaxKey(String maxKey) {
        char[] array = maxKey.toCharArray();
        System.arraycopy(array, 0, this.maxKey, 0, array.length);
    }

    public void write(DataOutputStream outputStream) throws IOException {
        for (char c: mergedFile) {
            outputStream.write(c);
        }

        outputStream.writeShort(keyLength);
        outputStream.writeShort(valueLength);
        outputStream.writeShort(version);
        for (char c: minKey) {
            outputStream.writeChar(c);
        }
        for (char c: maxKey) {
            outputStream.writeChar(c);
        }
    }
}
