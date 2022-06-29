package com.example.mybatisplus.web.controller;

import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.mapper.BatchMapper;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.BatchService;
import com.example.mybatisplus.model.domain.Batch;

import java.time.LocalDateTime;


/**
 *
 *  前端控制器
 *
 *
 * @author team27
 * @since 2022-06-25
 * @version v1.0
 */
@Controller
@RequestMapping("/api/batch")
public class BatchController {

    private final Logger logger = LoggerFactory.getLogger( BatchController.class );

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
        Batch  batch =  batchService.getById(id);
        return JsonResponse.success(batch);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        batchService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateBatch(Batch  batch) throws Exception {
        batchService.updateById(batch);
        return JsonResponse.success(batch);
    }


    /**
    * 描述:创建Batch
    *
    */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Batch  batch) throws Exception {
        batchService.save(batch);
        return JsonResponse.success(null);
    }
    /*
    *
    *
    * 添加批次
    *
    * */
    @RequestMapping(value = "/addBatch", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse newBatch(@RequestBody Batch batch){
        Integer flag=-1;
        JSONObject json = new JSONObject();
        boolean temp=batchService.save(batch);
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
     * 获取当前时间判断批次
     *
     * */
    @GetMapping ("/getBatch")
    @ResponseBody
    public JsonResponse getBatch(){
        Batch batch = batchMapper.getBidByTime();
        if(batch!=null){
            SessionUtils.saveCurBatch(batch);
        }
        return JsonResponse.success(batch);
    }

    /*
    *
    *延长时间
    *
    * */
    @PostMapping("/updateBatch")
    @ResponseBody
    public JsonResponse prolongBatch(@RequestBody Batch batch){
        batchService.updateById(batch);
        return JsonResponse.success(batch);
    }
}

