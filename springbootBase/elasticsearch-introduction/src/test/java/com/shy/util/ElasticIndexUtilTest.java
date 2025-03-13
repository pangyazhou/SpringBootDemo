package com.shy.util;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ElasticIndexUtilTest {
    private static final String indexName = "vector-index";

    @Test
    @Order(1)
    void deleteIndex() {
        boolean deleteIndex = ElasticIndexUtil.deleteIndex(indexName);
        assertTrue(deleteIndex);
    }

    @Test
    @Order(2)
    void createIndex() {
        String mapping_str = "{\n" +
                "    \"properties\": {\n" +
                "      \"my_vector\": {\n" +
                "        \"type\": \"dense_vector\",\n" +
                "        \"dims\": 512\n" +
                "      },\n" +
                "      \"my_text\": {\n" +
                "        \"type\": \"text\"\n" +
                "      }\n" +
                "    }\n" +
                "  }";

        JSONObject mapping = JSONObject.parseObject(mapping_str);
        boolean createIndex = ElasticIndexUtil.createIndex(indexName, mapping, null);
        assertTrue(createIndex);
    }

    /**
     * 插入文档测试方法
     * 该方法读取一个文本文件的内容，并将每行内容转换为嵌入向量，然后将这些向量连同原始文本一起插入到Elasticsearch索引中
     * 每个文档都有一个唯一的ID，基于其在文件中的位置
     */
    @Test
    @Order(3)
    @Disabled
    void insertDocument() {
        // 读取文件内容到列表中，每行文本作为一个元素
        List<String> contentList = FileUtils.readFile("test.txt", 200);
        // 遍历文件内容列表
        for (int i = 0; i < contentList.size(); i++) {
            // 获取当前行的文本内容
            String content = contentList.get(i);
            // 将文本内容转换为嵌入向量
            double[] embeddings = EmbeddingsUtil.getEmbeddings(content);
            // 创建一个JSON对象来存储嵌入向量和原始文本
            JSONObject doc = new JSONObject();
            // 将嵌入向量放入JSON对象中
            doc.put("my_vector", embeddings);
            // 将原始文本放入JSON对象中
            doc.put("my_text", content);
            // 将文档插入到Elasticsearch索引中，并验证插入操作是否成功
            boolean insertDocument = ElasticIndexUtil.insertDocument(indexName, String.valueOf(i+1), doc);
            // 断言确保文档插入成功
            assertTrue(insertDocument);
        }
    }

    @Test
    @Order(4)
    void insertBulkDocument() throws IOException {
        File file = new File("data/embedding-docs");
        if (file.exists()){
            File[] files = file.listFiles();
            for (File f : Objects.requireNonNull(files)) {
                String filePath = f.getAbsolutePath();
                boolean insertBulkDocument = ElasticIndexUtil.insertBulkDocument(filePath);
                assertTrue(insertBulkDocument);
            }
        }
    }
}