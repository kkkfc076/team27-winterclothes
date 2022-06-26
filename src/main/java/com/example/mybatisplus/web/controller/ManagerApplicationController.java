package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.model.domain.Applicationform;
import com.example.mybatisplus.model.domain.Manager;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.service.ManagerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.ManagerApplicationService;
import com.example.mybatisplus.model.domain.ManagerApplication;


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
@RequestMapping("/api/managerApplication")
public class ManagerApplicationController {

    private final Logger logger = LoggerFactory.getLogger( ManagerApplicationController.class );

    @Autowired
    private ManagerApplicationService managerApplicationService;
    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        ManagerApplication  managerApplication =  managerApplicationService.getById(id);
        return JsonResponse.success(managerApplication);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        managerApplicationService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateManagerApplication(ManagerApplication  managerApplication) throws Exception {
        managerApplicationService.updateById(managerApplication);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建ManagerApplication
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(ManagerApplication  managerApplication) throws Exception {
        managerApplicationService.save(managerApplication);
        return JsonResponse.success(null);
    }
    /**
     * *M
     */
    //xxxDTo：数据传输层，专门用来当参数
    //待我审核：分页查看
    @GetMapping("/pageList")
    @ResponseBody
    public JsonResponse pageList( PageDTO pageDTO, ManagerApplication mApp){
        Manager manager1= SessionUtils.getCurUser();
        QueryWrapper<ManagerApplication> wrapper=new QueryWrapper();
        wrapper.eq("man_key",manager1.getMid());
        if(manager1.getMid()!=null) {
          mApp=mApp.setManKey(manager1.getMid());
        }
        Page<ManagerApplication> page =managerApplicationService.pagelist(pageDTO,mApp);
        return JsonResponse.success(page);

    }
}

