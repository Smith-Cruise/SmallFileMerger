package org.inlighting.merger.index;

import org.inlighting.proto.BloomFilterProtos;
import org.inlighting.proto.KVsProtos;
import org.inlighting.proto.TrailerProtos;
import org.inlighting.util.Utils;
import org.inlighting.util.bloomfilter.BloomFilter;
import org.inlighting.util.bloomfilter.BloomFilterIO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

public class SFMIndexReader {

    private final FileChannel FILE_CHANNEL;

    private final int TRAILER_LENGTH;

    private final int PRE_READ_SIZE = 256;

    private final long INDEX_LENGTH;

    private final int BLOOM_FILTER_LENGTH;

    private BloomFilter BLOOM_FILTER;

    private final String MAX_KEY;

    private final String MIN_KEY;

    private final int KVS_LENGTH;

    private List<KVsProtos.KV> KVS;

    public SFMIndexReader(InputStream inputStream) throws IOException {
        if (!(inputStream instanceof FileInputStream)) {
            throw new UnsupportedOperationException("Unsupported input stream.");
        }

        // read trailer length
        FILE_CHANNEL = ((FileInputStream) inputStream).getChannel();
        INDEX_LENGTH = FILE_CHANNEL.size();
        long readPosition = (INDEX_LENGTH - PRE_READ_SIZE) <= 0 ? 0: (INDEX_LENGTH - PRE_READ_SIZE);
        FILE_CHANNEL.position(readPosition);
        int tempReadSize = (int) (INDEX_LENGTH <= PRE_READ_SIZE? INDEX_LENGTH: PRE_READ_SIZE);
        ByteBuffer temp = ByteBuffer.allocateDirect(tempReadSize);
        FILE_CHANNEL.read(temp);
        temp.position(tempReadSize - 1);
        TRAILER_LENGTH = Utils.readUnsignedByte(temp.get());

        // read trailer
        temp.position(tempReadSize - 1 - TRAILER_LENGTH);
        byte[] trailerBytes = new byte[TRAILER_LENGTH];
        temp.get(trailerBytes);
        TrailerProtos.Trailer trailer = TrailerProtos.Trailer.parseFrom(trailerBytes);
        MIN_KEY = trailer.getMinKey();
        MAX_KEY = trailer.getMaxKey();
        BLOOM_FILTER_LENGTH = trailer.getBloomFilterLength();
        KVS_LENGTH = trailer.getKvsLength();

        // If bloom filter length smaller than pre read size, read it. Otherwise, load lazily.
        if ((BLOOM_FILTER_LENGTH + TRAILER_LENGTH + 1) <= tempReadSize) {
            temp.position(tempReadSize - 1 - TRAILER_LENGTH - BLOOM_FILTER_LENGTH);
            byte[] bloomFilterBytes = new byte[BLOOM_FILTER_LENGTH];
            temp.get(bloomFilterBytes);
            BloomFilterProtos.BloomFilter bloomFilter = BloomFilterProtos.BloomFilter.parseFrom(bloomFilterBytes);
            BLOOM_FILTER = BloomFilterIO.deserialize(bloomFilter);
        }

        // release temp
        temp = null;
    }

    public static SFMIndexReader create(InputStream inputStream) {
        try {
            return new SFMIndexReader(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public KV get(String filename) throws IOException {
        if (filename.compareTo(MIN_KEY) < 0 || filename.compareTo(MAX_KEY) > 0) {
            throw new FileNotFoundException();
        }

        if (BLOOM_FILTER == null) {
            loadBloomFilterLazily();
        }

        // read from bloomFilter.
        if (! BLOOM_FILTER.testString(filename)) {
            throw new FileNotFoundException();
        }

        // read KVS
        if (KVS == null) {
            loadKVsLazily();
        }

        KVsProtos.KV kv = binarySearch(filename);
        if (kv == null) {
            throw new FileNotFoundException();
        }

        return new KV(kv.getFilename(), kv.getOffset(), kv.getLength());
    }

    private KVsProtos.KV binarySearch(String key) {
        int low = 0;
        int high = KVS.size() - 1;
        int middle = 0;
        if (key.compareTo(getKey(low)) < 0 || key.compareTo(getKey(high)) > 0) {
            return null;
        }

        while (low <= high) {
            middle = (low + high) / 2;
            if (getKey(middle).equals(key)) {
                return KVS.get(middle);
            } else if (getKey(middle).compareTo(key) < 0) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return null;
    }

    private String getKey(int index) {
        return KVS.get(index).getFilename();
    }

    public void loadBloomFilterLazily() throws IOException {
        ByteBuffer temp = ByteBuffer.allocateDirect(BLOOM_FILTER_LENGTH);
        FILE_CHANNEL.position(INDEX_LENGTH - 1 - TRAILER_LENGTH - BLOOM_FILTER_LENGTH);
        FILE_CHANNEL.read(temp);
        temp.flip();
        byte[] bloomFilterBytes = new byte[BLOOM_FILTER_LENGTH];
        temp.get(bloomFilterBytes);
        BloomFilterProtos.BloomFilter bloomFilter = BloomFilterProtos.BloomFilter.parseFrom(bloomFilterBytes);
        BLOOM_FILTER = BloomFilterIO.deserialize(bloomFilter);

        temp = null;
    }

    public void loadKVsLazily() throws IOException {
        ByteBuffer temp = ByteBuffer.allocateDirect(KVS_LENGTH);
        byte[] kvsBytes = new byte[KVS_LENGTH];
        FILE_CHANNEL.position(0);
        FILE_CHANNEL.read(temp);
        temp.flip();
        temp.get(kvsBytes);
        KVS = KVsProtos.KVs.parseFrom(kvsBytes).getKvList();
    }

    @Override
    public String toString() {
        return "SFMIndexReader{" +
                "TRAILER_LENGTH=" + TRAILER_LENGTH +
                ", INDEX_LENGTH=" + INDEX_LENGTH +
                ", MAX_KEY='" + MAX_KEY + '\'' +
                ", MIN_KEY='" + MIN_KEY + '\'' +
                '}';
    }
}
