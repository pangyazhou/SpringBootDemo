package com.shy;

import org.dromara.easyes.starter.register.EsMapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EsMapperScan("com.shy.mapper")
public class ElasticApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElasticApplication.class, args);
    }
}