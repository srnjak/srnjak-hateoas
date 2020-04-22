package com.srnjak.hateoas.test.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    public static final void write(
            String path, String filename, String content) {

        File file = new File(System.getProperty("examplesPath") + path);
        //noinspection ResultOfMethodCallIgnored
        file.mkdirs();

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(
                    new FileWriter(file.getAbsolutePath() + "/" + filename));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
