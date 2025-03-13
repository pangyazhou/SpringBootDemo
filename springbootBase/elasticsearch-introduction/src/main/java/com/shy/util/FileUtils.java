package com.shy.util;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.List;

public class FileUtils {

    public static List<String> readFile(String filePath, int maxLine) {
        // 读取文件内容
        List<String> lines = FileUtil.readLines(filePath, "UTF-8");
        // 返回文件内容列表
        return lines.subList(0, Math.min(maxLine, lines.size()));
    }

    public static void generateEsBulkFile(String filePath, String targetFilePath, String indexName, int startIndex, int endIndex){
        int count = 0;
        List<String> lines = FileUtil.readLines(filePath, "UTF-8").subList(startIndex, endIndex);
        JSONObject indexJson = JSONObject.parseObject("{\"index\": {\"_index\": \"demo-index\"}}");
        indexJson.getJSONObject("index").put("_index", indexName);
        JSONObject docJson = new JSONObject();
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(indexJson.toJSONString()).append("\n");
            docJson.put("my_text", line);
            docJson.put("my_vector", EmbeddingsUtil.getEmbeddings(line));
            sb.append(docJson.toJSONString()).append("\n");
            count++;
            System.out.println("count = " + count);
        }
        File file = FileUtil.writeUtf8String(sb.toString(), targetFilePath);
        if (!file.exists()) {
            System.out.println("生成文件失败");
            return;
        }
        String absolutePath = file.getAbsolutePath();
        System.out.println("文件路径：" + absolutePath);
        System.out.println("生成文件成功");
    }
}
