package com.shy;

import com.shy.service.ModelService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
class ModelServiceTest {
    @Resource
    private ModelService modelService;

    @Test
    void getModelMap() {
        modelService.getModelMap();
    }
}