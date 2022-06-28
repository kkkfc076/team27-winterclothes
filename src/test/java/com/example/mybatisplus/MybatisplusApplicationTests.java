package com.example.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.mapper.*;
import com.example.mybatisplus.model.domain.*;
import com.example.mybatisplus.model.dto.SubmitDTO;
import com.example.mybatisplus.service.ManagerApplicationService;
import com.example.mybatisplus.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SpringBootTest//加载ioc容器
class MybatisplusApplicationTests {

    @Autowired
    StudentMapper studentMapper;
    StudentService studentService;
@Autowired
    ManagerMapper managerMapper;
@Autowired
    ApplicationformMapper applicationformMapper;
@Autowired
ManagerApplicationMapper managerApplicationMapper;
@Autowired
ManagerApplicationService managerApplicationService;
@Autowired
    BatchMapper batchMapper;


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
    public void sl1(){
//        ManagerApplication mApp=new ManagerApplication();
//        QueryWrapper<ManagerApplication> wrapper=new QueryWrapper();
//        wrapper.eq("man_key",111);
//        List<ManagerApplication> mapp= managerApplicationMapper.selectList(wrapper);
//        System.out.println(mapp);
//        List<ManagerApplication> list=managerApplicationMapper.selectBatchIds(Arrays.asList(1));
//          managerApplicationService.updateAppform(13);
//        for(ManagerApplication managerApplication:list){
//
//        }
    }
    @Test
    public void getBatch(){
        Batch batch=batchMapper.getBidByTime();
        Batch batch1=batch;
    }

    @Test
    public void sl(){
//        ManagerApplication mApp=new ManagerApplication();
//        QueryWrapper<ManagerApplication> wrapper=new QueryWrapper();
//        wrapper.eq("man_key",111);
//        List<ManagerApplication> mapp= managerApplicationMapper.selectList(wrapper);
//        System.out.println(mapp);
//        List<ManagerApplication> list=managerApplicationMapper.selectBatchIds(Arrays.asList(1));
//          managerApplicationService.updateAppform(13);
//        for(ManagerApplication managerApplication:list){
//        }
        Student student=managerApplicationService.getApperInfo(10);
        System.out.println(student);
    }
}

