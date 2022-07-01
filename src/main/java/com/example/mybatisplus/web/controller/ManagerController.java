package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.mapper.BatchMapper;
import com.example.mybatisplus.model.domain.Batch;
import com.example.mybatisplus.model.domain.ManagerApplication;
import com.example.mybatisplus.model.domain.Student;
//import jdk.vm.ci.meta.Constant;
//import jdk.vm.ci.meta.Constant;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.model.dto.SetpermissionDTO;
import com.example.mybatisplus.service.BatchService;
import com.example.mybatisplus.service.ManagerApplicationService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import net.sf.json.JSONObject;
import org.apache.catalina.connector.Response;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.ManagerService;
import com.example.mybatisplus.model.domain.Manager;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import javax.xml.crypto.Data;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.mybatisplus.common.utls.SessionUtils.getCurUser;
import static com.example.mybatisplus.common.utls.SessionUtils.getCurstu;
import static org.apache.commons.lang3.ObjectUtils.NULL;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;


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
    @Autowired
    private ManagerApplicationService managerApplicationService;
    @Autowired
    private BatchService batchService;
    @Autowired
    private BatchMapper batchMapper;

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
        if(manager1!=null && manager1.getPermission()!=0){
            SessionUtils.saveCurUser(manager1);
            LocalDateTime dateTime = LocalDateTime.now();
            SessionUtils.saveCurTime(dateTime);
            return JsonResponse.success(manager1);
        }else {
            return JsonResponse.failure("用户名或密码错误");
        }
    }
    /*
    *
    * 获取当前时间判断批次
    *
    * */
    @GetMapping ("/getBatch")
    @ResponseBody
    public JsonResponse getBatch(){
        JSONObject json=new JSONObject();
        Integer bid=-1;
        Batch batch = batchMapper.getBidByTime();
        if(batch!=null){
            bid=batch.getBid();
            SessionUtils.saveCurBatch(batch);
        }
        json.put("flag",bid);
        return JsonResponse.success(json);
    }

    /*
    *
    * 修改密码
    * */
    @RequestMapping("/modifyPwd")
    @ResponseBody
    public JsonResponse modifyPwd(@RequestBody Map<String,String> params){
        String oldPassword=params.get("oldPassword");
        String newPassword=params.get("newPassword");
        Integer flag=-1;
        JSONObject json = new JSONObject();
        Manager manager1= getCurUser();
        if (StringUtils.isNotEmpty(oldPassword)) {
            try {
                if (oldPassword.equals(manager1.getPwd())) {
                    Manager man1 = new Manager();
                    man1.setId(manager1.getId());
                    man1.setPwd(newPassword);
                    flag =managerService.modifyP(man1);
                    manager1.setPwd(newPassword);
                    SessionUtils.saveCurUser(manager1);
                    if (flag > 0) {
                        flag = 2;// 修改成功
                    }
                } else {
                    flag = 0;// 旧密码错误
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        json.put("flag",flag);
        return JsonResponse.success(json);
    }
    /*
    *
    * 白名单设置
    *
    * */
    @ResponseBody
    @GetMapping ("/getManlist")
    public JsonResponse whiteList(PageDTO pageDTO,Manager manager){
        Page<Manager> page = managerService.pageList(pageDTO,manager);
        return JsonResponse.success(page);

    }
    /*
    *
    * 批量授权
    *
    * */
    @ResponseBody
    @PostMapping ("/setPermission")
    public JsonResponse setPermission(@RequestBody SetpermissionDTO setpermissionDTO){
        Boolean flag=managerService.setByIds(setpermissionDTO.getIds());
        return JsonResponse.success(flag);
    }

    /*
    *
    * 导入学生用户
    *
    *
    * */
    @PostMapping("/importStu")
    @ResponseBody
    public JsonResponse importStu(MultipartFile file){
        try {
            Map<String,Object> map=managerService.importStu(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonResponse.success(1);
    }
    /*
    *
    * 导入管理员用户
    * 每个管理员用户初始权限都为0
    *
    * */
    @PostMapping("/importMan")
    @ResponseBody
    public JsonResponse importMan(MultipartFile file){
        Map<String,Object> map=managerService.importMan(file);
        return JsonResponse.success(map);
    }

    //manager个人信息
    @GetMapping("/getMan")
    @ResponseBody
    public JsonResponse getMan()throws Exception {
        Manager manager = getCurUser();
        Long id = manager.getId();
        return getById(id);
    }
    /*
    获取所有批次
     */
    @GetMapping("/getAllBatch")
    @ResponseBody
    public JsonResponse getAllBatch() {
       List<Batch> map=managerService.getallBatch();
        return JsonResponse.success(map);
    }

    @GetMapping("/getselecteddata")
    @ResponseBody
    public JsonResponse getSelectedData( String str, Integer batch){
        Map<String,Object> map=managerService.getSelData(str,batch);
        return JsonResponse.success(map);
    }
    @GetMapping("/getClo")
    @ResponseBody
    public JsonResponse getClo( Integer batch){
        Map<String,Object> map=managerService.getClo(batch);
        return JsonResponse.success(map);
    }
}

