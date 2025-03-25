package com.shy.util;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

import java.util.*;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/14 下午3:58
 **/
public class EsQueryUtil {

    /**
     * 创建一个基于向量查询的QueryBuilder对象
     * 该查询使用向量相似度来评分文档
     *
     * @param field 字段名，指定文档中的向量字段
     * @param vector 查询向量，用于比较文档中相应字段的向量
     * @return Builder对象，用于执行向量查询
     */
    public static QueryBuilder vectorQuery(String field, double[] vector) {
        // 使用ScriptScoreQuery包装MatchAllQuery，以向量相似度评分所有文档
        return QueryBuilders.scriptScoreQuery(
                QueryBuilders.matchAllQuery(),
                new Script(
                        ScriptType.INLINE,
                        "painless",
                        "cosineSimilarity(params.query_vector, doc['" + field + "']) + 1.0",
                        Collections.singletonMap("query_vector", vector)
                )
        );
    }

/*    public static QueryBuilder vectorsQuery(List<String> fields, List<double[]> vectors) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder idOrCode = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            idOrCode.append("double score_").append(i + 1)
                    .append(" = cosineSimilarity(params.query_vector").append(i + 1)
                    .append(", doc['").append(fields.get(i)).append("'])").append(" + 1.0; ");
        }
        idOrCode.append("return ");
        for (int i = 0; i < fields.size(); i++) {
            idOrCode.append("score_").append(i + 1).append(" + ");
        }
        idOrCode.deleteCharAt(idOrCode.length() - 2).append(";");
        for (int i = 0; i < vectors.size(); i++) {
            params.put("query_vector" + (i + 1), vectors.get(i));
        }
        return QueryBuilders.scriptScoreQuery(
                QueryBuilders.matchAllQuery(),
                new Script(
                        ScriptType.INLINE,
                        "painless",
                        idOrCode.toString(),
                        params
                )
        );
    }*/

    /**
     * 创建一个查询构建器，用于根据向量字段和向量值进行查询
     * 该方法使用脚本分数查询来计算文档的相关性分数，基于向量的余弦相似度
     *
     * @param fields 向量字段列表，表示文档中的向量字段
     * @param vectors 查询向量列表，表示要查询的向量值
     * @return 返回一个查询构建器，用于执行向量查询
     */
    public static QueryBuilder vectorsQuery(List<String> fields, List<double[]> vectors) {
        // 创建一个映射，用于存储查询参数
        Map<String, Object> params = new HashMap<>();
        // 将向量字段列表转换为数组并存储到参数映射中
        params.put("vector_fields", fields.toArray());
        // 将查询向量列表转换为数组并存储到参数映射中
        params.put("query_vectors", vectors.toArray());

        // 定义一个字符串，包含脚本代码用于计算文档的相关性分数
        String idOrCode = "double max_score = -Double.MAX_VALUE; " +
                          "          for (int i = 0; i < params.query_vectors.size(); i++) { " +
                          "            max_score = Math.max(max_score, cosineSimilarity(params.query_vectors[i], doc[params.vector_fields[i]]) + 1.0); " +
                          "          } " +
                          "          return max_score * 50;";

        // 返回一个脚本分数查询构建器，使用匹配所有文档的查询和定义的脚本
        // 脚本根据向量的余弦相似度计算文档的相关性分数
        return QueryBuilders.scriptScoreQuery(
                QueryBuilders.matchAllQuery(),
                new Script(
                        ScriptType.INLINE,
                        "painless",
                        idOrCode,
                        params
                )
        );
    }


}
