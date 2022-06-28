package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Batch;
import com.example.mybatisplus.model.domain.Clothes;
import com.example.mybatisplus.model.domain.Student;
import com.example.mybatisplus.mapper.StudentMapper;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ????ѧ? 服务实现类
 * </p>
 *
 * @author team27
 * @since 2022-06-23
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    /*
    *
    * 学生登录
    *
    * */
    @Override
    public Student stulogin(Student student) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("sid",student.getSid()).eq("pwd",student.getPwd());

        Student student1 = studentMapper.selectOne(wrapper);
        return student1;
    }
    @Override
    public Integer modifyP(Student stu) {
        return studentMapper.modifyP(stu);
    }

    @Override
    public String getBySid(Integer stuKey) {
        return studentMapper.getBySid(stuKey);
    }


    @Override
    public Page<Student> pageList(PageDTO pageDTO) {
        Page<Student> page=new Page<>(pageDTO.getPageNo(),pageDTO.getPageSize());
        QueryWrapper<Student> wrapper=new QueryWrapper<>();
         //   wrapper.eq("batch",batch.getBid());

        page=super.page(page, wrapper);
        return page;
    }
}
