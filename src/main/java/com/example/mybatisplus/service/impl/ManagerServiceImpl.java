package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.example.mybatisplus.common.BaseController;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.common.utls.ExcelUtil;
import com.example.mybatisplus.mapper.*;
import com.example.mybatisplus.model.domain.*;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.service.ApplicationformService;
import com.example.mybatisplus.service.ManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatisplus.service.StudentService;
import lombok.val;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * ???еĹ???Ա?˺ţ?????ϵͳ????Ա??????Ա??ѧԺ??ѧУ?û? 服务实现类
 * </p>
 *
 * @author team27
 * @since 2022-06-24
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    ClothesMapper clothesMapper;
    @Autowired
    ApplicationformMapper applicationformMapper;
    @Autowired
    BatchMapper batchMapper;
    @Override
    public Manager manlogin(Manager manager) {
        QueryWrapper<Manager> wrapper=new QueryWrapper<>();
        wrapper.eq("mid",manager.getMid()).eq("pwd",manager.getPwd());

        Manager manager1= managerMapper.selectOne(wrapper);

        return manager1;
    }

    @Override
    public Integer modifyP(Manager man) {
        return managerMapper.modifyP(man);
    }

    @Override
    public Page<Manager> pageList(PageDTO pageDTO, Manager manager) {
        Page<Manager> page = new Page<>(pageDTO.getPageNo(),pageDTO.getPageSize());
        QueryWrapper<Manager> wrapper = new QueryWrapper<>();

        if(StringUtils.isNotBlank(manager.getMname())){
            wrapper.eq("mname",manager.getMname());
        }
        if(manager.getMlevel()!=null){
            wrapper.eq("mlevel",manager.getMlevel());
        }
        if(manager.getGrade()!=null){
            wrapper.eq("grade",manager.getGrade());
        }
        if(manager.getPermission()!=null){
            wrapper.eq("permission",manager.getPermission());
        }
        page = super.page(page, wrapper);
        return page;
    }

    @Override
    public Boolean setByIds(List<Serializable> ids) {
        return CollectionUtils.isEmpty(ids) ? false : managerMapper.setPermissions(ids);
    }

    @Override
    public Map<String, Object> importStu(MultipartFile file) {
        //验证文件
        Map<String,Object> map=new HashMap<>();
        try {
            ExcelUtil.checkFile(file);

            List<String[]> strings= ExcelUtil.readExcel(file);
            int rowCount = strings.size();//获取总行数
            if(rowCount==1){
                return null;
            }
            //验证第一行
            //如果是，格式化字符串
            String[] row;
            //批量插入
            for(int i=1;i<=rowCount;i++){
                row=strings.get(i);
                Student student=new Student();
                student.setSid(Integer.valueOf(row[0]));
                student.setPwd("123");
                student.setSname(row[1]);
                student.setSex(row[2]);
                student.setSclass(Integer.valueOf(row[3]));
                student.setGrade(Integer.valueOf(row[4]));
                student.setMajor(row[5]);
                student.setPlevel(row[6]);
                student.setPyear(Integer.valueOf(row[7]));
                studentService.save(student);
            }
            //返回信息
            map.put("msg","成功导入");
        } catch (IOException e) {
            map.put("errorMsg","请输入正确的文件");
        }
        return map;
    }

    @Override
    public Map<String, Object> importMan(MultipartFile file) {
        Map<String,Object> map=new HashMap<>();
        try {
            ExcelUtil.checkFile(file);

            List<String[]> strings= ExcelUtil.readExcel(file);
            int rowCount = strings.size();//获取总行数
            if(rowCount==1){
                return null;
            }
            //验证第一行
            //如果是，格式化字符串
            String[] row;
            //批量插入
            for(int i=1;i<rowCount+1;i++){
                row=strings.get(i);
                Manager manager=new Manager();
                manager.setMid(Integer.valueOf(row[0]));
                manager.setPwd("123");
                manager.setMname(row[1]);
                manager.setMlevel(Integer.valueOf(row[2]));
                if(StringUtils.isNotBlank(row[3])){
                    manager.setMajor(row[3]);
                }else {
                    manager.setMajor(null);
                }
                if(StringUtils.isNotBlank(row[4])){
                    manager.setGrade(Integer.valueOf(row[4]));
                }else {
                    manager.setGrade(null);
                }
                manager.setPermission(0);
                managerService.save(manager);
            }
            //返回信息
            map.put("msg","成功导入");
        } catch (IOException e) {
            map.put("errorMsg","请输入正确的文件");
        }
        return map;
    }

    @Override
    public List<Batch> getallBatch() {
        Map<String ,Object> map =new HashMap<>();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.isNotNull("Bid");
        List<Batch> batch=batchMapper.selectList(wrapper);
        return batch;
    }

    @Override
    public Map<String,Object> getSelData(String str, Integer batch) {
        QueryWrapper<Student> wrapper1=new QueryWrapper();
        List<Student> student=studentMapper.selectList(wrapper1.eq("major",str));
        List sid_card=student.stream().map(Student::getSid).collect(Collectors.toList());
        Integer passed=0,total=0,unpass=0,uncheck=0;
        for(int i=0;i<sid_card.size();i++){
            QueryWrapper<Applicationform> wrapper=new QueryWrapper();
            wrapper.eq("stu_key",sid_card.get(i)).eq("bat_key",batch);
            Applicationform appf=applicationformMapper.selectOne(wrapper);
            if(appf!=null) {
                if (appf.getResult() != null) {
                    if (appf.getResult() == true) {
                        passed++;
                        total++;
                    } else if (appf.getResult() == false) {
                        unpass++;
                        total++;
                    } else {
                        uncheck++;
                        total++;
                    }
                }
            }
        }
        Map<String,Object> map=new HashMap<>();
        map.put("total",total);
        map.put("pass",passed);
        map.put("unpass",unpass);
        map.put("unCheck",uncheck);
        return map;

    }

    @Override
    public Map<String, Object> getClo(Integer batch) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("bat_key",batch);
        List<Clothes> clothes=clothesMapper.selectList(wrapper);
        Integer total=0,man=0,woman=0;
        for(Clothes clothes1:clothes){
            System.out.println(clothes1);
            if(clothes1.getSex().equals("男")){
                man++;
                total++;
            }
            if(clothes1.getSex().equals("女")){
                woman++;
                total++;
            }
        }
        Map<String,Object> map= new HashMap<>();
        map.put("total",total);
        map.put("man",man);
        map.put("woman",woman);
        return map;
    }


}
