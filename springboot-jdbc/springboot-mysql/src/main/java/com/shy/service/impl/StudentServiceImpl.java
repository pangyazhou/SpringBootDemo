package com.shy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shy.entity.Student;
import com.shy.mapper.StudentMapper;
import com.shy.service.IStudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author: yzpang
 * Desc:
 * Date: 2024/11/18 下午2:58
 **/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {
}
