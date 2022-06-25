package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.model.domain.Manager;
import com.example.mybatisplus.model.domain.Student;
import com.example.mybatisplus.mapper.StudentMapper;
import com.example.mybatisplus.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.example.mybatisplus.common.utls.SessionUtils.getCurstu;

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

}
