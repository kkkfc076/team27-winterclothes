package com.example.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.mapper.ManagerApplicationMapper;
import com.example.mybatisplus.mapper.StudentMapper;
import com.example.mybatisplus.model.domain.Applicationform;
import com.example.mybatisplus.model.domain.ManagerApplication;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.service.ManagerApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//
@SpringBootTest//加载ioc容器
class MybatisplusApplicationTests {

    @Autowired
    StudentMapper studentMapper;
    @Autowired
    ManagerApplicationMapper managerApplicationMapper;

    @Test
    public void get() {
        String pwd = studentMapper.getPwd(706);
    }

    @Test
    public void ceshi() {
        ManagerApplication mapp=new ManagerApplication();
        mapp=managerApplicationMapper.selectById(1);
        System.out.println(mapp);
    }
}
