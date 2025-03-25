package com.shy.entity;

import com.shy.mapper.FaultInstanceMapper;
import com.shy.util.EmbeddingsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FaultInstanceIndexTest {
    @Resource
    private FaultInstanceMapper faultInstanceMapper;

    @Test
    void deleteIndex() {
        Boolean result = faultInstanceMapper.deleteIndex("fault_instance_index");
        Assertions.assertTrue(result);
    }

    @Test
    void createIndex() {
        Boolean result = faultInstanceMapper.createIndex("fault_instance_index");
        Assertions.assertTrue(result);
    }

    @Test
    void insert() {
        FaultInstanceIndex faultInstanceIndex = new FaultInstanceIndex();
        List<String> descriptions = Arrays.asList("车内的冷气系统在高温天气下无法正常制冷，风扇也出现了持续的噪音。",
                "空调不制冷", "空调不制冷", "电气系统故障1");
        List<String> reasons = Arrays.asList("空调冷媒不足或压缩机及风扇故障",
                "维修空调123-1234", "空调不制冷", "制冷剂不足1");
        List<String> repairs = Arrays.asList("检查并补充冷媒，必要时更换压缩机和/或风扇",
                "空调故障", "空调不制冷", "添加制冷剂1");
        for (int i = 0; i < descriptions.size(); i++) {
            String faultPhenomenonDescription = descriptions.get(i);
            String faultReasonAnalyze = reasons.get(i);
            String faultRepairContent = repairs.get(i);
            faultInstanceIndex.setId("1");
            faultInstanceIndex.setFaultPhenomenonDescription(faultPhenomenonDescription);
            faultInstanceIndex.setFaultReasonAnalyze(faultReasonAnalyze);
            faultInstanceIndex.setFaultRepairContent(faultRepairContent);
            faultInstanceIndex.setFaultPhenomenonDescriptionVector(EmbeddingsUtil.getEmbeddings(faultPhenomenonDescription));
            faultInstanceIndex.setFaultReasonAnalyzeVector(EmbeddingsUtil.getEmbeddings(faultReasonAnalyze));
            faultInstanceIndex.setFaultRepairContentVector(EmbeddingsUtil.getEmbeddings(faultRepairContent));
            int result = faultInstanceMapper.insert(faultInstanceIndex);
            Assertions.assertEquals(1, result);
        }
    }
}