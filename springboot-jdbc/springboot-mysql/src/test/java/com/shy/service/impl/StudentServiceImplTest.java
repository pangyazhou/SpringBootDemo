package com.shy.service.impl;

import com.shy.entity.Student;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
class StudentServiceImplTest {
    @Resource
    private StudentServiceImpl studentService;

    @Test
    public void testInsert(){
        Student student = new Student();
        student.setName("yzpang");
        student.setAge(20);
        boolean save = studentService.save(student);
        Assert.assertTrue(save);
    }

}