package org.inlighting.merger;

import org.inlighting.SFMException;

public interface FileMerger {

    // 由具体的形式实现，用于检查是否能够正常启动 FileMerger.
    void checkSFM() throws SFMException;

    void checkClosed() throws SFMException;

    void upload(String src, byte[] contents) throws SFMException;

//    void write(String src, byte[] contents) throws SFMException;

    void merge(String indexName) throws SFMException;

    void mergeSmallestQueue() throws SFMException;

    void close() throws SFMException;
}
