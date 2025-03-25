package com.shy.entity;

import lombok.Data;
import org.dromara.easyes.annotation.IndexField;
import org.dromara.easyes.annotation.IndexName;
import org.dromara.easyes.annotation.rely.FieldType;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/17 上午10:22
 **/
@Data
@IndexName(value = "fault_instance_index")
public class FaultInstanceIndex {
    private String id;

    @IndexField(fieldType = FieldType.TEXT, analyzer = "ik_smart")
    private String faultPhenomenonDescription;

    @IndexField(fieldType = FieldType.TEXT, analyzer = "ik_smart")
    private String faultReasonAnalyze;

    @IndexField(fieldType = FieldType.TEXT, analyzer = "ik_smart")
    private String faultRepairContent;

    @IndexField(fieldType = FieldType.DENSE_VECTOR, dims = 512)
    private double[] faultPhenomenonDescriptionVector;

    @IndexField(fieldType = FieldType.DENSE_VECTOR, dims = 512)
    private double[] faultReasonAnalyzeVector;

    @IndexField(fieldType = FieldType.DENSE_VECTOR, dims = 512)
    private double[] faultRepairContentVector;
}
