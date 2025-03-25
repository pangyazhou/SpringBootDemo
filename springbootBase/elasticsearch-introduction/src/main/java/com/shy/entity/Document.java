package com.shy.entity;

import lombok.Data;
import org.dromara.easyes.annotation.IndexField;
import org.dromara.easyes.annotation.IndexName;
import org.dromara.easyes.annotation.rely.FieldType;

@Data
@IndexName(value = "document")
public class Document {
    /**
     * Es 中的ID
     */
    private String id;

    /**
     * 文档标题
     */
    @IndexField(fieldType = FieldType.TEXT, analyzer = "ik_smart")
    private String title;

    /**
     * 文档内容
     */
    @IndexField(fieldType = FieldType.KEYWORD)
    private String content;

    /**
     * 文档描述
     */
    @IndexField(fieldType = FieldType.TEXT, analyzer = "ik_smart")
    private String description;

    /**
     * 文档向量
     */
    @IndexField(fieldType = FieldType.DENSE_VECTOR, dims = 512)
    private double[] titleVector;

    /**
     * 文档向量
     */
    @IndexField(fieldType = FieldType.DENSE_VECTOR, dims = 512)
    private double[] contentVector;

}
