package com.shy.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class EmbeddingsUtilTest {

    @Test
    void getEmbeddings() {
        String text = "你好";
        double[] embeddings = EmbeddingsUtil.getEmbeddings(text);
        assertNotNull(embeddings);
        System.out.println(embeddings);
    }
}