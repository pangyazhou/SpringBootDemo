package com.shy.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * ES索引操作
 */
public class ElasticIndexUtil {
    private static final String ES_URL = "http://localhost:9200";

    /**
     * 删除指定名称的Elasticsearch索引
     *
     * @param indexName 要删除的索引名称
     * @return boolean 删除操作是否成功（HTTP状态码200-299为成功）
     *
     * 函数通过构造DELETE请求到ES服务端，根据HTTP响应状态判断是否删除成功。
     * 注意：实际使用时需要确保ES_URL已正确配置且服务可达。
     */
    public static boolean deleteIndex(String indexName) {
        HttpResponse response = HttpRequest.delete(ES_URL.concat("/").concat(indexName)).execute();
        return response.isOk();
    }

    /**
     * 创建Elasticsearch索引
     *
     * @param indexName 索引名称
     * @param mapping 索引的映射定义，用于描述字段及其数据类型
     * @param setting 索引的设置，如分片数和副本数
     * @return 如果创建成功，返回true；否则返回false
     */
    public static boolean createIndex(String indexName, JSONObject mapping, JSONObject setting) {
        // 创建一个空的JSON对象，用于存储请求体
        JSONObject body = new JSONObject();

        // 如果映射定义不为空，则将其添加到请求体中
        if (mapping != null){
            body.put("mappings", mapping);
        }

        // 如果索引设置不为空，则将其添加到请求体中
        if (setting != null){
            body.put("settings", setting);
        }

        // 执行HTTP PUT请求，以创建索引
        HttpResponse response = HttpRequest.put(ES_URL.concat("/").concat(indexName))
                .header(Header.CONTENT_TYPE, "application/json")
                .body(body.toJSONString()).execute();

        // 根据HTTP响应判断索引是否创建成功
        return response.isOk();
    }

    /**
     * 向Elasticsearch中插入文档
     *
     * @param indexName 索引名称，表示文档应插入到哪个索引中
     * @param docId 文档ID，用于唯一标识索引中的文档
     * @param doc 包含文档数据的JSON对象，表示要插入的文档内容
     * @return boolean 表示文档插入是否成功如果返回true，则表示插入成功；如果返回false，则表示插入失败
     */
    public static boolean insertDocument(String indexName, String docId, JSONObject doc) {
        // 构建请求URL
        String url = ES_URL.concat("/").concat(indexName).concat("/_doc/").concat(docId);

        // 执行HTTP POST请求，将文档数据插入到指定索引中
        HttpResponse response = HttpRequest.post(url)
                .header(Header.CONTENT_TYPE, "application/json")
                .body(doc.toJSONString()).execute();

        // 根据HTTP响应判断文档是否插入成功
        return response.isOk();
    }

    /**
     * 批量插入文档到Elasticsearch
     * 该方法读取指定文件路径中的数据，并通过HTTP请求将其作为批量操作插入到Elasticsearch中
     *
     * @param filePath 文件路径，包含要插入Elasticsearch的文档数据
     * @return 插入操作是否成功如果HTTP响应状态码为200，则返回true，否则返回false
     */
    public static boolean insertBulkDocument(String filePath) throws IOException {
        // 构建批量操作的URL
        String url = ES_URL.concat("/_bulk");
        // 读取 JSON 文件内容
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        // 发送HTTP POST请求，包含请求头和请求体
        HttpResponse response = HttpRequest.post(url)
                .header(Header.CONTENT_TYPE, "application/json")
                .body(jsonContent).execute();

        // 根据HTTP响应状态判断插入操作是否成功
        return response.isOk();
    }
}
