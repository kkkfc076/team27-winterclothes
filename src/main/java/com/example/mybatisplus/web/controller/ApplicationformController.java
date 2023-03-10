package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.mapper.ApplicationformMapper;
import com.example.mybatisplus.mapper.BatchMapper;
import com.example.mybatisplus.model.domain.*;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.service.BatchService;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.ApplicationformService;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.example.mybatisplus.common.utls.SessionUtils.getCurBatch;
import static com.example.mybatisplus.common.utls.SessionUtils.getCurstu;


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

    @Autowired
    private BatchService batchService;

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
    public JsonResponse updateReason(@RequestBody Applicationform applicationform1){
        Integer flag=-1;
        JSONObject json = new JSONObject();
        Student student1= SessionUtils.getCurSUser();
        Batch batch1=SessionUtils.getCurBatch();
        Applicationform applicationform=applicationformService.getByStukey(student1.getSid(),batch1.getBid());
        applicationform.setReason(applicationform1.getReason());
        flag=applicationformService.updateReason(applicationform);
        if(flag>0){
            flag=2;
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
        Batch batch1=SessionUtils.getCurBatch();
        Applicationform applicationform=applicationformService.getByStukey(student1.getSid(),batch1.getBid());
        return JsonResponse.success(applicationform);
    }

    //增加reason
    @PostMapping("/saveReason")
    @ResponseBody
    public JsonResponse saveReason(@RequestBody Applicationform applicationform){
        Boolean permission=batchService.getPermission();
        Applicationform applicationform1 =new Applicationform();
        Student student1= SessionUtils.getCurSUser();
        applicationform.setStuKey(student1.getSid());
        applicationform.setBatKey(SessionUtils.getCurBatch().getBid());
        if(permission){
            applicationformService.save(applicationform);
            Batch batch1=SessionUtils.getCurBatch();
            applicationform1=applicationformService.getByStukey(student1.getSid(),batch1.getBid());
            applicationformService.updateD(applicationform1.getId());

            applicationformService.addMAform(applicationform1);
        }
        return JsonResponse.success(permission);

    }

    /**
     * 学生选择寒衣
     */
    @RequestMapping("/choose")
    @ResponseBody
    public JsonResponse choose(@RequestBody Clothes clothes){
        Integer flag=-1;
        JSONObject json = new JSONObject();
        Student student1= getCurstu();
        Applicationform applicationform=applicationformService.getApp(student1.getSid(),SessionUtils.getCurBatch().getBid());
        String result = String.valueOf(applicationform.getResult());
        if(result =="true"){
            applicationform.setCid(clothes.getCid());
            flag=applicationformService.updateCid(applicationform);
            if(flag>0){
                flag=2;
            }else {
                flag=0;
            }
            json.put("flag",flag);
            return JsonResponse.success(json);
        }
        json.put("flag",flag);
        return JsonResponse.success(json);
    }
    /**
     * 导出汇总表
     * */
    @PostMapping("/export")
    @ResponseBody
    public void export(HttpServletResponse response){
        applicationformService.export(response);
    }

    /**
     * 导出审核表
     * @return
     */
    @PostMapping("/verify")
    @ResponseBody
    public void verify(HttpServletResponse response){

        applicationformService.verify(response);
    }

    //申请表中有无学生
    @RequestMapping("/match")
    @ResponseBody
    public JsonResponse match( ){
        Integer flag= -1;
        JSONObject json = new JSONObject();
        Student student1= getCurstu();
        Batch batch1=SessionUtils.getCurBatch();
        Applicationform applicationform=applicationformService.getByStukey(student1.getSid(),batch1.getBid());
        if(applicationform==null){
            flag=2;
        }else {
            flag=0;
        }
        json.put("flag",flag);
        return JsonResponse.success(json);
    }

    //查看历史记录
    @GetMapping ("/getDInfo")
    @ResponseBody
    public JsonResponse getDIngo(PageDTO pageDTO) {
        Student student1 = getCurstu();
        Page<Applicationform> page =applicationformService.getDIngo(pageDTO,student1);
        return JsonResponse.success(page);
    }

    /*
     *
     * 数据总览:申请统计
     * */
    @GetMapping("/applyStatistics")
    @ResponseBody
    public JsonResponse dataStatistics(){
        Map<String,Object> map=applicationformService.dataStatistics();
        return JsonResponse.success(map);
    }


}

