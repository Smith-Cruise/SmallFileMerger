package org.inlighting.merger;

import org.inlighting.merger.index.SFMIndexBuilder;

import java.io.DataOutputStream;
import java.io.FileOutputStream;

public class GenerateMergedFile {
    public static void main(String[] args) throws Exception {
        DataOutputStream mergedOutput = new DataOutputStream(new FileOutputStream("merged.data"));
        for (int i=1000; i<=9999; i++) {
            mergedOutput.writeInt(i);
        }
        mergedOutput.close();

        FileOutputStream indexOutput = new FileOutputStream("index");
        SFMIndexBuilder sfmIndex = SFMIndexBuilder.create("merged.data", indexOutput);
        int offset = 0;
        for (int i=1000; i<=9999; i++) {
            sfmIndex.append(String.valueOf(i), offset, 4);
            offset += 4;
        }
        sfmIndex.finish();
        indexOutput.close();
    }
}
