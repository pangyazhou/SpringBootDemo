package com.shy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: yzpang
 * Desc:
 * Date: 2024/11/22 下午3:26
 **/
@Configuration
@ConfigurationProperties(prefix = "bert")
@Data
public class BertConfig {
    private Map<String, String> modelMap = new HashMap<>();
}
