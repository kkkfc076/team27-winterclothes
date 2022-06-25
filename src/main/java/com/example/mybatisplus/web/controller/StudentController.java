package com.example.mybatisplus.web.controller;

import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.mapper.StudentMapper;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.StudentService;
import com.example.mybatisplus.model.domain.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.mybatisplus.common.utls.SessionUtils.getCurstu;


/**
 *
 *  前端控制器
 *
 *
 * @author team27
 * @since 2022-06-23
 * @version v1.0
 */
@Controller
@RequestMapping("/api/student")
public class StudentController {

    private final Logger logger = LoggerFactory.getLogger( StudentController.class );

    @Autowired
    private StudentService studentService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Student  student =  studentService.getById(id);
        return JsonResponse.success(student);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        studentService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateStudent(Student  student) throws Exception {
        studentService.updateById(student);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Student
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Student  student) throws Exception {
        studentService.save(student);
        return JsonResponse.success(null);
    }
    /*
     * 描述：学生登录
     */
    @PostMapping("/login")
    @ResponseBody
    public JsonResponse login(@RequestBody Student student){
        Student student1=studentService.stulogin(student);
        if(student1.getId()!=null){
            SessionUtils.saveCurUser(student1);
        }
        return JsonResponse.success(student1);
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
        Student student1= getCurstu();
        String pwd=student1.getPwd();
        if (StringUtils.isNotEmpty(oldPassword)) {
            try {
                if (oldPassword.equals(pwd)) {
                    Student stu1 = new Student();
                    stu1.setId(student1.getId());
                    stu1.setPwd(newPassword);
                    flag =studentService.modifyP(stu1);
                    student1.setPwd(newPassword);
                    SessionUtils.saveCurUser(student1);
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


    //学生信息
    @GetMapping("/getInfo")
    @ResponseBody
    public JsonResponse getInfo() throws Exception {
        Student student1= getCurstu();
        Long id=student1.getId();
        return getById(id);
    }

    }
}

//
