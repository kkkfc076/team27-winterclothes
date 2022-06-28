package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Manager;
import com.example.mybatisplus.model.domain.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * ????ѧ? Mapper 接口
 * </p>
 *
 * @author team27
 * @since 2022-06-23
 */
@Repository
public interface StudentMapper extends BaseMapper<Student> {

    String getPwd(@Param("sid") int sid);
    Integer modifyP(Student stu);

    String getBySid(@Param("sid") Integer sid);
}
