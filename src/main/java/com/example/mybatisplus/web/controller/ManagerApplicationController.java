package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.mapper.ManagerApplicationMapper;
import com.example.mybatisplus.model.domain.Applicationform;
import com.example.mybatisplus.model.domain.Manager;
import com.example.mybatisplus.model.domain.Student;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.model.dto.SubmitDTO;
import com.example.mybatisplus.service.ApplicationformService;
import com.example.mybatisplus.service.ManagerService;
import com.example.mybatisplus.service.StudentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.ManagerApplicationService;
import com.example.mybatisplus.model.domain.ManagerApplication;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;


/**
 *
 *  前端控制器
 *
 *
 * @author team27:木
 * @since 2022-06-24
 * @version v1.0
 */
@Controller
@RequestMapping("/api/managerApplication")
public class ManagerApplicationController {

    private final Logger logger = LoggerFactory.getLogger( ManagerApplicationController.class );

    @Autowired
    private ManagerApplicationService managerApplicationService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ApplicationformService applicationformService;
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
     * *获取待审核完成的列表
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
    /*
    获取已审核完成的申请表
     */
    @GetMapping("/checkedPageList")
    @ResponseBody
    public JsonResponse checkedPageList( PageDTO pageDTO, ManagerApplication mApp) {
        Manager manager1 = SessionUtils.getCurUser();
        QueryWrapper<ManagerApplication> wrapper = new QueryWrapper();
        wrapper.eq("man_key", manager1.getMid());
        if (manager1.getMid() != null) {
            mApp = mApp.setManKey(manager1.getMid());
        }
            Page<ManagerApplication> page = managerApplicationService.checkedPagelist(pageDTO, mApp);
        return JsonResponse.success(page);
    }
    /*
    批量通过申请
    检查是否到达最高审核等级
     */
    @PostMapping("/submitsApprove")
    @ResponseBody
    public JsonResponse submits(@RequestBody SubmitDTO submitDTO){
        managerApplicationService.updateAppform(submitDTO.getIds());
        return JsonResponse.success(111);
    }
    /*
    批量拒绝申请
    检查是否到达最高审核等级
     */

    @PostMapping("/submitsDisApprove")
    @ResponseBody
    public JsonResponse submitsDis(@RequestBody SubmitDTO submitDTO){
        managerApplicationService.updateAppformDis(submitDTO.getIds());
        return JsonResponse.success(111);
    }
    /*
    根据申请单编号查询学生id
     */
    @GetMapping (value = "/appInfo/{id}")
    @ResponseBody
    public JsonResponse stuInfo(@PathVariable Long id){
        Student student=managerApplicationService.getApperInfo(id);
        return JsonResponse.success(student);
    }
    /*
   根据申请单编号查询学生表的批次和原因等信息
    */
    @GetMapping (value = "/sAppInfo/{id}")
    @ResponseBody
    public JsonResponse sAppInfo(@PathVariable Long id){
        Applicationform applf=managerApplicationService.getsAppInfo(id);
        return JsonResponse.success(applf);
    }
    /*
    保存用户
     */
    @PostMapping(value="/tempSave/{id}")
    @ResponseBody
    public JsonResponse tempsaveResaon(@PathVariable Serializable id, @RequestBody ManagerApplication mApp){
        managerApplicationService.storeReason(id,mApp.getReason());
     return JsonResponse.success(111);
    }
    /*
    审批通过单个用户
     */
    @PostMapping(value="/approve/{id}")
    @ResponseBody
    public JsonResponse approveOne(@PathVariable Serializable id,@RequestBody ManagerApplication mApp){
        managerApplicationService.storeReason(id,mApp.getReason());
        managerApplicationService.updateAppform(Collections.singletonList(id));
        return JsonResponse.success(111);
    }
    /*
    审批拒绝 单个用户
     */
    @PostMapping(value="/disapprove/{id}")
    @ResponseBody
    public JsonResponse disApproveOne(@PathVariable Serializable id,@RequestBody ManagerApplication mApp){
        managerApplicationService.storeReason(id,mApp.getReason());
        managerApplicationService.updateOneDisApp(Collections.singletonList(id));
        return JsonResponse.success(111);
    }


    /*
    *
    * 将新增的申请表连到Manager_applicaion表
    *
    * */
    @RequestMapping()
    @ResponseBody
    public JsonResponse addMAform(Applicationform applicationform){
        applicationformService.addMAform(applicationform);
        return JsonResponse.success(1);
    }


}

