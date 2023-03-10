package com.example.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.mapper.*;
import com.example.mybatisplus.model.domain.Applicationform;
import com.example.mybatisplus.model.domain.Batch;
import com.example.mybatisplus.model.domain.Manager;
import com.example.mybatisplus.model.domain.ManagerApplication;
import com.example.mybatisplus.model.domain.Student;
import com.example.mybatisplus.model.domain.*;
import com.example.mybatisplus.model.dto.SubmitDTO;
import com.example.mybatisplus.service.BatchService;
import com.example.mybatisplus.service.ManagerApplicationService;
import com.example.mybatisplus.service.ManagerService;
import com.example.mybatisplus.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

@SpringBootTest//加载ioc容器
class MybatisplusApplicationTests {

    @Autowired
    StudentMapper studentMapper;
    @Autowired
    ManagerService managerService;
    @Autowired
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
@Autowired
    private ClothesMapper clothesMapper;


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
//    @Test
//    public void sl(){
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
        @Test
        public void file(){
            String name=studentService.getBySid(123);
            System.out.println(name);
        }
    @Test
    public void getBatch(){
        Batch batch=batchMapper.getBidByTime();
        Batch batch1=batch;
    }

    @Test
    public void sl(){
//
        Map<String ,Object> map=managerService.getClo("计算机",2022);
        System.out.println(map);
    }

    @Test
    public void sta(){
        List<Integer> list=clothesMapper.selectCids();
        System.out.println(list);
        for(int i=0;i<list.size();i++){
            clothesMapper.setNums(list.get(i));
        }
        List<Clothes> list1=clothesMapper.All();
        System.out.println(list1);
    }
    @Test
    public void tt(){
        List<Clothes> list=managerService.getSelCol("计算机",2022);
        System.out.println(list);
    }
}

