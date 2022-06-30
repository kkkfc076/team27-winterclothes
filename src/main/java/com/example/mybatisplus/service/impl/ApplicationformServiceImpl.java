package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.mapper.ManagerMapper;
import com.example.mybatisplus.model.domain.*;
import com.example.mybatisplus.mapper.ApplicationformMapper;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.service.ApplicationformService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatisplus.service.ManagerApplicationService;
import com.example.mybatisplus.service.StudentService;
//import javafx.concurrent.Worker;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author team27
 * @since 2022-06-24
 */
@Service
public class ApplicationformServiceImpl extends ServiceImpl<ApplicationformMapper, Applicationform> implements ApplicationformService {

    @Autowired
    private  ApplicationformMapper applicationformMapper;
    @Autowired
    private ApplicationformService applicationformService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private ManagerApplicationService managerApplicationService;


    @Override
    public Applicationform getApp(Integer sid, Integer batKey) {

        return applicationformMapper.getApp(sid,batKey);
    }

    @Override
    public Page<Applicationform> getDIngo(PageDTO pageDTO, Student student) {
        Page<Applicationform> page = new Page<>(pageDTO.getPageNo(),pageDTO.getPageSize());

        Integer sid = student.getSid();

        QueryWrapper<Applicationform> wrapper =new QueryWrapper<>();

        if (sid != null) {
            wrapper.eq("stu_key",sid);
        }
        page=super.page(page,wrapper);
        return page;
    }

    @Override
    public Map<String, Object> dataStatistics() {
        Map<String,Object> map=new HashMap<>();
        QueryWrapper<Applicationform> wrapper=new QueryWrapper<>();
        Integer bid= SessionUtils.getCurBatch().getBid();
        List<Applicationform> list1=applicationformService.list(wrapper.eq("bat_key",bid));//该批次总申请数
        List<Applicationform> list2=applicationformService.list(wrapper.eq("result",1));//已通过
        List<Applicationform> list3=applicationformService.list(wrapper.eq("result",0));//未通过
        Integer unCheck=list1.size()-list2.size()-list3.size();
        map.put("total",list1.size());
        map.put("pass",list2.size());
        map.put("unpass",list3.size());
        map.put("unCheck",unCheck);
        return map;
    }

    @Override
    public void updateD(Long id) {
        applicationformMapper.updateD(id);
    }

    @Override
    public void addMAform(Applicationform applicationform) {
        Student student=SessionUtils.getCurstu();
        ManagerApplication managerApplication1=new ManagerApplication();
        Integer grade=student.getGrade();
        String major=student.getMajor();
        QueryWrapper<Manager> wrapper1=new QueryWrapper<>();
        /**************辅导员****************/
        wrapper1.eq("major",major).eq("grade",grade).eq("mlevel",1);
        Manager manager1=managerMapper.selectOne(wrapper1);
        managerApplication1.setManKey(manager1.getMid());
        managerApplication1.setAppKey(Math.toIntExact(applicationform.getId()));
        managerApplication1.setState(1);
        managerApplication1.setResult("待审核");
        managerApplicationService.save(managerApplication1);
    }


    @Override
    public void export(HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition","attachment; filename=xxx.xls");
        List<Applicationform> applicationforms=applicationformMapper.selectList(null);
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();

        Row row=sheet.createRow(0);
        Cell cell=row.createCell(0);
        cell.setCellValue("学生学号");

        cell=row.createCell(1);
        cell.setCellValue("学生姓名");

        cell=row.createCell(2);
        cell.setCellValue("审核结果");

        cell=row.createCell(3);
        cell.setCellValue("理由");


        for(int i=0;i<applicationforms.size();i++){
            row=sheet.createRow(i+1);

            cell=row.createCell(0);
            cell.setCellValue(applicationforms.get(i).getStuKey());

            cell=row.createCell(1);
            Integer Sid=applicationforms.get(i).getStuKey();
            cell.setCellValue(studentService.getBySid(applicationforms.get(i).getStuKey()));

            cell=row.createCell(2);
            cell.setCellValue(applicationforms.get(i).getResult()==null?"":applicationforms.get(i).getResult().toString());

            cell=row.createCell(3);
            cell.setCellValue(applicationforms.get(i).getReason());
        }
        try {
            ((HSSFWorkbook) wb).write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Applicationform getByStukey(Integer sid, Integer batKey) {
        return applicationformMapper.getApp(sid,batKey);
    }

    @Override
    public Integer updateReason(Applicationform applicationform) {
        return applicationformMapper.updateReason(applicationform);
    }

    @Override
    public Applicationform addStukey(Integer sid, String reason) {
        return applicationformMapper.addStukey(sid,reason);
    }

    @Override
    public Integer updateCid(Applicationform applicationform) {
        return applicationformMapper.updateCid(applicationform);
    }

}
