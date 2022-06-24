package com.example.mybatisplus.web.controller;

import com.example.mybatisplus.model.domain.Student;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.ManagerService;
import com.example.mybatisplus.model.domain.Manager;


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
@RequestMapping("/api/manager")
public class ManagerController {

    private final Logger logger = LoggerFactory.getLogger( ManagerController.class );

    @Autowired
    private ManagerService managerService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Manager  manager =  managerService.getById(id);
        return JsonResponse.success(manager);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        managerService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateManager(Manager  manager) throws Exception {
        managerService.updateById(manager);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Manager
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Manager  manager) throws Exception {
        managerService.save(manager);
        return JsonResponse.success(null);
    }
    /**
     * 描述:Manager登录
     *
     */
    @PostMapping("/mlogin")
    @ResponseBody
    public JsonResponse mlogin(@RequestBody Manager manager){
        Manager manager1=managerService.manlogin(manager);
        return JsonResponse.success(manager1);
    }
}

