package com.shy.entity;


import lombok.Data;

import java.io.Serializable;

/**
 * (Student)实体类
 *
 * @author makejava
 * @since 2024-11-18 14:49:26
 */
@Data
public class Student implements Serializable {
    private static final long serialVersionUID = -16305543221629930L;
    /**
     * id
     */
    private Long id;
    /**
     * name
     */
    private String name;
    /**
     * age
     */
    private Integer age;


}

