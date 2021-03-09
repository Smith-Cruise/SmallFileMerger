package org.inlighting.merger.indexbak;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Test {
    public static void main(String[] args) {
        Trailer trailer1 = new Trailer("hello1234", 3, 5, 1, "miaefawfaefaen", "maathbtyjyukuykmyukjutyjyjtyfhtrgrthtrherx");
        Trailer trailer2 = new Trailer("hello", 255, 300,2, "miafeefn", "max");
        try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("a"));
            stream.writeObject(trailer1);
            stream.close();
            ObjectOutputStream stream1 = new ObjectOutputStream(new FileOutputStream("b"));
            stream1.writeObject(trailer2);
            stream1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        char[] contents = new char[100];
//        contents[0] = 'a';
//        contents[1] = 'b';
//        contents[2] = '\0';
//        contents[3] = 'c';
//        System.out.println(String.valueOf(contents));
//        String a = "Hello World";
//        for (char c: a.toCharArray()) {
//            System.out.println((int)c);
//        }
//        char[] temp = a.toCharArray();
//        System.arraycopy(temp, 0, contents, 0, temp.length);
//        System.out.println(String.valueOf(contents));
    }
}
