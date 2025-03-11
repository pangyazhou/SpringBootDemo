package com.shy.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElasticSearchUtilTest {
    private static final String indexName = "vector-index";

    @Test
    void search() {
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"script_score\": {\n" +
                "      \"query\": {},\n" +
                "      \"script\": {\n" +
                "        \"source\": \"cosineSimilarity(params.query_vector, 'my_vector') + 1.0\",\n" +
                "        \"params\": {\n" +
                "          \"query_vector\": []\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"_source\": [\"my_text\"]\n" +
                "}";
        JSONObject queryJson = JSONObject.parseObject(query);
        String text = "贸易顺差";
        double[] embeddings = EmbeddingsUtil.getEmbeddings(text);
        queryJson.getJSONObject("query").getJSONObject("script_score").getJSONObject("query").put("match_all", new JSONObject());
        queryJson.getJSONObject("query").getJSONObject("script_score").getJSONObject("script").getJSONObject("params").put("query_vector", embeddings);
        String query_str = queryJson.toJSONString();
        System.out.println(query_str);
        String result = ElasticSearchUtil.search(indexName, queryJson.toJSONString());
        System.out.println(JSONObject.toJSONString(JSONObject.parseObject(result), SerializerFeature.PrettyFormat));
    }
}