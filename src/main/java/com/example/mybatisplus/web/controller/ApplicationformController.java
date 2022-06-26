package com.example.mybatisplus.web.controller;

import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.mapper.ApplicationformMapper;
import com.example.mybatisplus.model.domain.Batch;
import com.example.mybatisplus.model.domain.Student;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.ApplicationformService;
import com.example.mybatisplus.model.domain.Applicationform;


/**
 *
 *  前端控制器
 *
 *
 * @author team27
 * @since 2022-06-24
 * @version v1.0
 */
@Controller
@RequestMapping("/api/applicationform")
public class ApplicationformController {

    private final Logger logger = LoggerFactory.getLogger( ApplicationformController.class );

    @Autowired
    private ApplicationformService applicationformService;

    @Autowired
    private ApplicationformMapper applicationformMapper;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Applicationform  applicationform =  applicationformService.getById(id);
        return JsonResponse.success(applicationform);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        applicationformService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateApplicationform(Applicationform  applicationform) throws Exception {
        applicationformService.updateById(applicationform);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Applicationform
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Applicationform  applicationform) throws Exception {
        applicationformService.save(applicationform);
        return JsonResponse.success(null);
    }

    /*
    *
    * 修改reason
    *
    *
    * */
    @PostMapping ("/updateReason")
    @ResponseBody
    public JsonResponse updateReason(String reason){
        Integer flag=-1;
        JSONObject json = new JSONObject();
        Student student1= SessionUtils.getCurSUser();
        Integer bid=2019;
        Applicationform applicationform=applicationformService.getByStukey(student1.getSid(),bid);
        applicationform.setReason(reason);
        if(applicationform.getReason()!=null){
            flag=1;
        }else {
            flag=0;
        }
        json.put("flag",flag);
        return JsonResponse.success(json);
    }

    //查看申请状态
    @GetMapping ("/getAPInfo")
    @ResponseBody
    public JsonResponse getAPIngo() {
        Student student1= SessionUtils.getCurSUser();
        Integer bid=2019;
        Applicationform applicationform=applicationformService.getByStukey(student1.getSid(),bid);
        return JsonResponse.success(applicationform);
    }

    //申请理由
    @PostMapping ("/addReason")
    @ResponseBody
    public JsonResponse addReason(String reason){
        Integer flag=-1;
        JSONObject json = new JSONObject();
        Student student1= SessionUtils.getCurSUser();
        Applicationform applicationform=applicationformService.addStukey(student1.getSid(),reason);
        if(applicationform.getReason()!=null){
            flag=1;
        }else {
            flag=0;
        }
        json.put("flag",flag);
        return JsonResponse.success(json);
    }

}

