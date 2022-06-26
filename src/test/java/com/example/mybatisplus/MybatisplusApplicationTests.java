package com.example.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.mapper.ApplicationformMapper;
import com.example.mybatisplus.mapper.ManagerApplicationMapper;
import com.example.mybatisplus.mapper.ManagerMapper;
import com.example.mybatisplus.mapper.StudentMapper;
import com.example.mybatisplus.model.domain.Applicationform;
import com.example.mybatisplus.model.domain.Manager;
import com.example.mybatisplus.model.domain.ManagerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@SpringBootTest//加载ioc容器
class MybatisplusApplicationTests {

@Autowired
    StudentMapper studentMapper;
@Autowired
    ManagerMapper managerMapper;
@Autowired
    ApplicationformMapper applicationformMapper;
@Autowired
ManagerApplicationMapper managerApplicationMapper;
@Test
    public void get(){
        String pwd=studentMapper.getPwd(706);
    }

@Test
    public void update(){
    Manager manager=new Manager();
    int i=managerMapper.modifyP(manager);
    }
@Test
    public void add(){
    Manager userInfo= SessionUtils.getCurUser();
    System.out.println(userInfo);
    }
    @Test
    public void sl(){
        ManagerApplication mApp=new ManagerApplication();
        QueryWrapper<ManagerApplication> wrapper=new QueryWrapper();
        wrapper.eq("man_key",111);
        List<ManagerApplication> mapp= managerApplicationMapper.selectList(wrapper);
        System.out.println(mapp);
    }
}
