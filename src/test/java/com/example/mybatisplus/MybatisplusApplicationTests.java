package com.example.mybatisplus;

import com.example.mybatisplus.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest//加载ioc容器
class MybatisplusApplicationTests {

@Autowired
    StudentMapper studentMapper;

@Test
    public void get(){
        String pwd=studentMapper.getPwd(706);
    }


}
