package com.shy.controller;

import com.shy.entity.Student;
import com.shy.service.IStudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Author: yzpang
 * Desc:
 * Date: 2024/11/18 下午3:23
 **/
@RestController
@RequestMapping("student")
public class StudentController {

    @Resource
    private IStudentService studentService;

    @PostMapping("/save")
    public String save(@RequestBody Student student) {
        studentService.save(student);
        return "success";
    }
}
