package org.inlighting.merger.index;

import org.inlighting.proto.BloomFilterProtos;
import org.inlighting.proto.KVsProtos;
import org.inlighting.proto.TrailerProtos;
import org.inlighting.util.Utils;
import org.inlighting.util.bloomfilter.BloomFilter;
import org.inlighting.util.bloomfilter.BloomFilterIO;

import java.io.IOException;
import java.io.OutputStream;

public class SFMIndexBuilder {

    private final String MERGED_FILE;

    private final OutputStream OUT;

    private final BloomFilter BLOOM_FILTER;

    private static final double FPP = 0.05;

    private static final long EXPECTED_ENTRIES = 128 * 1024;

    private String minKey;

    private String maxKey;

    private String lastKey;

    private final KVsProtos.KVs.Builder KVS_BUILDER;

    public SFMIndexBuilder(String mergedFile, OutputStream outputStream, long expectedEntries, double fpp) {
        MERGED_FILE = mergedFile;
        OUT = outputStream;
        BLOOM_FILTER = new BloomFilter(expectedEntries, fpp);
        KVS_BUILDER = KVsProtos.KVs.newBuilder();
    }

    public static SFMIndexBuilder create(String mergedFile, OutputStream outputStream) {
       return new SFMIndexBuilder(mergedFile, outputStream, EXPECTED_ENTRIES, FPP);
    }

    public void append(String filename, int offset, int length) throws IOException {
        checkKey(filename);

        KVsProtos.KV.Builder kvBuilder = KVsProtos.KV.newBuilder();
        kvBuilder.setFilename(filename);
        kvBuilder.setOffset(offset);
        kvBuilder.setLength(length);
        KVsProtos.KV kv = kvBuilder.build();
        KVS_BUILDER.addKv(kv);

        BLOOM_FILTER.addString(filename);
    }

    private void checkKey(String key) {
        if (lastKey == null) {
            lastKey = key;
        }

        if (lastKey.compareTo(key) > 0) {
            throw new IllegalArgumentException("The key should be ordered.");
        }
        lastKey = key;

        if (minKey == null) {
            minKey = key;
        } else {
            if (minKey.compareTo(key) > 0) {
                minKey = key;
            }
        }

        if (maxKey == null) {
            maxKey = key;
        } else {
            if (maxKey.compareTo(key) < 0) {
                maxKey = key;
            }
        }
    }

    public void finish() throws IOException {
        KVsProtos.KVs kvs = KVS_BUILDER.build();
        kvs.writeTo(OUT);

        BloomFilterProtos.BloomFilter.Builder bloomFilterBuilder = BloomFilterProtos.BloomFilter.newBuilder();
        BloomFilterIO.serialize(bloomFilterBuilder, BLOOM_FILTER);
        BloomFilterProtos.BloomFilter bloomFilter = bloomFilterBuilder.build();
        bloomFilter.writeTo(OUT);

        TrailerProtos.Trailer.Builder trailerBuilder = TrailerProtos.Trailer.newBuilder();
        trailerBuilder.setKvsLength(kvs.getSerializedSize());
        trailerBuilder.setMinKey(minKey);
        trailerBuilder.setMaxKey(maxKey);
        trailerBuilder.setBloomFilterLength(bloomFilter.getSerializedSize());
        TrailerProtos.Trailer trailer = trailerBuilder.build();
        trailer.writeTo(OUT);

        if (trailer.getSerializedSize() > 255) {
            throw new IOException("Too large trailer.");
        }

        OUT.write(Utils.getUnsignedByte(trailer.getSerializedSize()));
        OUT.close();
    }
}
