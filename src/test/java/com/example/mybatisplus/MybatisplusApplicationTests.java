package com.example.mybatisplus;

import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.mapper.ManagerMapper;
import com.example.mybatisplus.mapper.StudentMapper;
import com.example.mybatisplus.model.domain.Manager;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;

@SpringBootTest//加载ioc容器
class MybatisplusApplicationTests {

@Autowired
    StudentMapper studentMapper;
    ManagerMapper managerMapper;
@Test
    public void get(){
        String pwd=studentMapper.getPwd(706);
    }

@Test
    public void update(){
    Manager manager=new Manager();
    int i=managerMapper.modifyP(manager);
    }

}
