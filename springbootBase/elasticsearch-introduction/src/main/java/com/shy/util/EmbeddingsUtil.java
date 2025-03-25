package com.shy.util;

import cn.hutool.http.Header;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * 嵌入模型相关工具接口
 */
public class EmbeddingsUtil {
    private static final String URL = "http://192.168.1.247:9983/v1/embeddings";

    /**
     * 文本向量化
     * @param text 输入文本
     * @return 文本向量
     */
    public static double[] getEmbeddings(String text) {
        JSONObject body = new JSONObject();
        body.put("model", "bge-small-zh-v1.5");
        body.put("input", text);
        body.put("encoding_format", "float");
        HttpResponse response = HttpUtil.createPost(URL)
                .header("Content-Type", "application/json")
                .body(body.toJSONString())
                .execute();
        if (response.isOk()) {
            JSONObject jsonObject = JSONObject.parseObject(response.body());
            JSONObject data = jsonObject.getJSONArray("data").getJSONObject(0);
            Object embedding = data.get("embedding");
            return JSONObject.parseObject(embedding.toString(), double[].class);
        }
        return null;
    }
}
