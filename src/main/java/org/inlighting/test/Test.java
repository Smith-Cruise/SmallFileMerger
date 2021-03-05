package org.inlighting.test;

import org.inlighting.proto.BloomFilterProtos;
import org.inlighting.util.bloomfilter.BloomFilter;
import org.inlighting.util.bloomfilter.BloomFilterIO;
import org.inlighting.util.bloomfilter.Murmur3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Test {
    public static void main(String[] args) throws IOException {
        byte[] bytes = "Hello".getBytes(StandardCharsets.UTF_8);
        BloomFilter bloomFilter = new BloomFilter(128*1024);
        bloomFilter.addString("hello");
        bloomFilter.addLong(1);
        bloomFilter.addLong(3);

        BloomFilterProtos.BloomFilter.Builder builder = BloomFilterProtos.BloomFilter.newBuilder();
        BloomFilterIO.serialize(builder, bloomFilter);
        FileOutputStream out = new FileOutputStream("out");
        builder.build().writeTo(out);
        out.close();

        FileInputStream in = new FileInputStream("out");
        BloomFilterProtos.BloomFilter bloomFilter1 = BloomFilterProtos.BloomFilter.parseFrom(in);
        BloomFilter bloomFilter2 = BloomFilterIO.deserialize(bloomFilter1);
        System.out.println(bloomFilter2.testLong(2));
        System.out.println(bloomFilter2.testLong(1));
        System.out.println(bloomFilter2.testString("hello"));
        in.close();
    }
}
