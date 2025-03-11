package com.shy.util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {
    private static final String indexName = "vector-index";

    @Test
    void readFile() {
        String filePath = "test.txt";
        List<String> content = FileUtils.readFile(filePath, 100);
        assertNotNull(content);
        System.out.println(content.size());
    }

    @Test
    void generateEsBulkFile() {
        String filePath = "test.txt";
        String targetFilePath = "target.json";
        int lineNum = 36045;
        int startIndex = 0;
        int endIndex;
        while (startIndex < lineNum){
            targetFilePath = "target" + startIndex + ".json";
            endIndex = Math.min(lineNum, startIndex + 1000);
            FileUtils.generateEsBulkFile(filePath, targetFilePath, indexName, startIndex, endIndex);
            startIndex = endIndex;
        }
    }
}