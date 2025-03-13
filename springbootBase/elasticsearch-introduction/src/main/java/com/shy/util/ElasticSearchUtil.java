package com.shy.util;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * ES搜索工具类
 */
@Slf4j
public class ElasticSearchUtil {
    private static final String ES_URL = "http://localhost:9200";

    /**
     * 根据指定的索引名称和查询字符串执行搜索操作
     * 该方法通过发送HTTP POST请求到Elasticsearch的_search端点来执行查询，并返回搜索结果
     *
     * @param indexName 索引名称，表示要在哪个索引中执行查询
     * @param query 查询字符串，表示要执行的搜索条件
     * @return 返回搜索结果的字符串表示
     */
    public static String search(String indexName, String query) {
        // 构造HTTP POST请求，以发送查询到Elasticsearch的_search端点
        HttpResponse response = HttpRequest.post(ES_URL.concat("/").concat(indexName).concat("/_search"))
                .header(Header.CONTENT_TYPE, "application/json") // 设置请求头，指定内容类型为JSON
                .body(query) // 设置请求体，即查询字符串
                .execute(); // 执行HTTP请求
        // 返回响应体，即搜索结果的字符串表示
        return response.body();
    }
}
