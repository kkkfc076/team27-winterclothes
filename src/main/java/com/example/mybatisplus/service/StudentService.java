package com.example.mybatisplus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Batch;
import com.example.mybatisplus.model.domain.Manager;
import com.example.mybatisplus.model.domain.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplus.model.dto.PageDTO;

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

    Page<Student> pageList(PageDTO pageDTO);
}
