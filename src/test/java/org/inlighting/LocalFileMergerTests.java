package org.inlighting;

import org.inlighting.merger.LocalFileMerger;
import org.inlighting.merger.FileMergerFactory;
import org.inlighting.util.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Random;

public class LocalFileMergerTests {

    private static String DiskFilesFolder = "generated_files/";

    private static LocalFileMerger localFileMerger;

//    @BeforeEach
//    void initialize() {
//        localFileMerger = FileMergerFactory.getInstance();
//    }
//
//    @AfterEach
//    void clean() {
////        localFileMerger.close();
//    }
//
//    void generateDiskFiles() {
//        File file = new File(DiskFilesFolder);
//        Utils.deleteIfExist(file);
//        file.mkdir();
//        for (int i=0; i<10; i++) {
//            UtilsTests.generateFile(DiskFilesFolder + i + ".txt", (char) i, 2048);
//        }
//        localFileMerger = FileMergerFactory.getInstance();
//    }
//
//    void deleteGeneratedDiskFiles() {
//        File file = new File(DiskFilesFolder);
//        Utils.deleteIfExist(file);
//    }
//
//    @Test
//    void uploadBatch10MFiles() {
////        LocalFileMerger localFileMerger = new LocalFileMerger("output", 5);
////        UtilsTests.generateFile("a", 'a', 2048);
////        UtilsTests.generateFile("b", 'b', 2048);
////        UtilsTests.generateFile("c", 'c', 2048);
////        localFileMerger.upload("a", "/a");
////        localFileMerger.upload("b", "/b");
////        localFileMerger.upload("c", "/c");
//    }
//
//    @Test
//    void uploadInMemoryFiles() {
//        Random random = new Random();
//        try {
//            for (int i=0; i<=10; i++) {
//                char c = (char) random.nextInt(128);
//                long size = random.nextInt(10);
//                size = size * 1024 * 1024;
//                byte[] content = UtilsTests.generateContent(c, size);
//                localFileMerger.upload("/hello/" + i, content);
//            }
//        } catch (SFMException e) {
//            e.printStackTrace();
//        }
//    }
}
