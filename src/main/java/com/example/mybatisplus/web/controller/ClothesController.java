package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.model.domain.*;
import com.example.mybatisplus.mapper.ClothesMapper;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.service.ApplicationformService;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.ClothesService;

import java.util.List;

import static com.example.mybatisplus.common.utls.SessionUtils.getCurstu;
import static com.example.mybatisplus.common.utls.SessionUtils.session;


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
    private ApplicationformService applicationformService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id) throws Exception {
        Clothes clothes = clothesService.getById(id);
        return JsonResponse.success(clothes);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        clothesService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateClothes(Clothes clothes) throws Exception {
        clothesService.updateById(clothes);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Clothes
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Clothes clothes) throws Exception {
        clothesService.save(clothes);
        return JsonResponse.success(11);
    }

    /*
    *
    * 管理员添加寒衣
    *
    * */
    @ResponseBody
    @RequestMapping("/addClothes")
    public JsonResponse addClothes(@RequestBody Clothes clothes){
        Integer flag=-1;
        JSONObject json = new JSONObject();
        clothes.setBatKey(SessionUtils.getCurBatch().getBid());
        boolean temp=clothesService.save(clothes);
        if(temp){
            flag=2;
        }else {
            flag=0;
        }
        json.put("flag",flag);
        return JsonResponse.success(json);
    }
    /*
    *
    * 管理员删除单件款式
    * */
    @ResponseBody
    @RequestMapping("/deleteClothes")
    public JsonResponse deleteClothes(@RequestBody Clothes clothes){
        Integer flag=-1;
        JSONObject json = new JSONObject();
        boolean temp=clothes.deleteById();
        if(temp){
            flag=2;
        }else {
            flag=0;
        }
        json.put("flag",flag);
        return JsonResponse.success(json);
    }
    /*
    *
    * 管理员编辑款式
    *
    * */
    @PostMapping("/update")
    @ResponseBody
    public JsonResponse update(@RequestBody Clothes clothes){
        clothesService.updateById(clothes);
        return JsonResponse.success(111);
    }

    /*
    *
    * 管理员管理所有寒衣
    * */
    @ResponseBody
    @GetMapping ("/getClolist")
    public JsonResponse whiteList(PageDTO pageDTO,Clothes clothes){
        Page<Clothes> page = clothesService.pageListtoM(pageDTO,clothes);
        return JsonResponse.success(page);

    }

    //查看登记
    @GetMapping ("/getCInfo")
    @ResponseBody
    public JsonResponse getCIngo( ) {
        Student student1= SessionUtils.getCurSUser();
        Batch batch1=SessionUtils.getCurBatch();
        Clothes clothes =clothesService.getByCidAndBatKey(student1.getSid(),batch1.getBid());
        return JsonResponse.success(clothes);
    }

    //查看历史记录
    @GetMapping ("/getDInfo")
    @ResponseBody
    public JsonResponse getDInfo(PageDTO pageDTO,Clothes clothes) {
        Page<Clothes> page =clothesService.getDIngo(pageDTO,clothes);
        return JsonResponse.success(page);
    }



    /**
     * 学生获得寒衣款式列表
     */
    @ResponseBody
    @GetMapping("/styles")
    public JsonResponse clothes(PageDTO pageDTO) {
        Student student1 = getCurstu();
        Page<Clothes> page= clothesService.pageList(pageDTO,student1);
        return JsonResponse.success(page) ;
    }


    //查看详情
    @ResponseBody
    @PostMapping("/getDetail")
    public JsonResponse getDetail(@RequestBody Clothes clothes) {
        Clothes clothes1 =clothesService.getByCid(clothes);
        return JsonResponse.success(clothes1);
    }
}

