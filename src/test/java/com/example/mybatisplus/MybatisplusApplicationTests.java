package com.example.mybatisplus;

import com.example.mybatisplus.model.domain.Admin;
import com.example.mybatisplus.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@SpringBootTest
class MybatisplusApplicationTests {

    //生成1-36的随机排列
    @Test
    public void hh(){
        List<Integer> collect = IntStream.range(1, 37).boxed().collect(Collectors.toList());
        Collections.shuffle(collect);
        System.out.println(collect);
    }



}
