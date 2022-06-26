package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Manager;
import com.example.mybatisplus.model.domain.Student;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * ????ѧ? 服务类
 * </p>
 *
 * @author team27
 * @since 2022-06-23
 */
public interface StudentService extends IService<Student> {

    Student stulogin(Student student);

    Integer modifyP(Student student1);

    Student selectByS(Student student);
}
