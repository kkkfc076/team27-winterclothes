package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.mapper.ClothesMapper;
import com.example.mybatisplus.model.domain.Student;
import com.example.mybatisplus.model.dto.PageDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.ClothesService;
import com.example.mybatisplus.model.domain.Clothes;

import java.util.List;

import static com.example.mybatisplus.common.utls.SessionUtils.getCurstu;


/**
 *
 *  前端控制器
 *
 *
 * @author team27
 * @since 2022-06-26
 * @version v1.0
 */
@Controller
@RequestMapping("/api/clothes")
public class ClothesController {

    private final Logger logger = LoggerFactory.getLogger(ClothesController.class);

    @Autowired
    private ClothesService clothesService;
    private ClothesMapper clothesMapper;

    /**
     * 描述：根据Id 查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id) throws Exception {
        Clothes clothes = clothesService.getById(id);
        return JsonResponse.success(clothes);
    }

    /**
     * 描述：根据Id删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        clothesService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
     * 描述：根据Id 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateClothes(Clothes clothes) throws Exception {
        clothesService.updateById(clothes);
        return JsonResponse.success(null);
    }


    /**
     * 描述:创建Clothes
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Clothes clothes) throws Exception {
        clothesService.save(clothes);
        return JsonResponse.success(null);
    }

    /**
     * 获得寒衣款式
     */
    @ResponseBody
    @GetMapping("/styles")
    public JsonResponse clothes() {
        Student student1 = getCurstu();
        Page<Clothes> page= clothesService.pageList(student1);
        return JsonResponse.success(page) ;
    }

}
