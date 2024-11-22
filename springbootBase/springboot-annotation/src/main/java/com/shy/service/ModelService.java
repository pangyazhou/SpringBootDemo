package com.shy.service;

import com.shy.config.BertConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Author: yzpang
 * Desc:
 * Date: 2024/11/22 下午3:23
 **/
@Service
public class ModelService {
    @Resource
    private BertConfig bertConfig;

    public String getModelMap() {
        System.out.println(bertConfig.getModelMap());
        return bertConfig.getModelMap().toString();
    }
}
