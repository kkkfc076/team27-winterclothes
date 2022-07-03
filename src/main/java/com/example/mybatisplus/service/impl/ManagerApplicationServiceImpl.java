package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.utls.SecurityUtils;
import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.mapper.ApplicationformMapper;
import com.example.mybatisplus.mapper.ManagerMapper;
import com.example.mybatisplus.mapper.StudentMapper;
import com.example.mybatisplus.model.domain.*;
import com.example.mybatisplus.mapper.ManagerApplicationMapper;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.model.dto.UserInfoDTO;
import com.example.mybatisplus.service.ManagerApplicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author team27
 * @since 2022-06-24
 */
@Service
public class ManagerApplicationServiceImpl extends ServiceImpl<ManagerApplicationMapper, ManagerApplication> implements ManagerApplicationService {

    @Autowired
    ManagerApplicationMapper managerApplicationMapper;
    @Autowired
    ManagerMapper managerMapper;
    @Autowired
    ApplicationformMapper applicationformMapper;
    @Autowired
    ManagerApplicationService managerApplicationService;
    @Autowired
    StudentMapper studentMapper;
    @Override
    public Page<ManagerApplication> pagelist(PageDTO pageDTO, ManagerApplication mApp) {
        Page<ManagerApplication> page=new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
        QueryWrapper<ManagerApplication> wrapper=new QueryWrapper<>();
        if(mApp.getManKey()!=null)
           wrapper.eq("man_key",mApp.getManKey()) ;
        if(mApp.getId()!=null){
            wrapper.eq("id",mApp.getId());
        }
        Batch batch=SessionUtils.getCurBatch();
        QueryWrapper wrapper1=new QueryWrapper();
        wrapper1.eq("bat_key",batch.getBid());
//        Map<String,Object> map=new HashMap<>();
        List<Applicationform> appf=applicationformMapper.selectList(wrapper1);
        List aid_card=appf.stream().map(Applicationform::getAid).collect(Collectors.toList());
        //遍历获取当前批次内的申请
//        for(int i=0;i<aid_card.size();i++){
//            map.put("app_key",aid_card.get(i));
//        }
        wrapper.in("app_key",aid_card);
        wrapper.eq("result","待审核");
        page= super.page(page,wrapper);
        return page;
    }

    @Override
    public Page<ManagerApplication> checkedPagelist(PageDTO pageDTO, ManagerApplication mApp) {
        Page<ManagerApplication> page=new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
        QueryWrapper<ManagerApplication> wrapper=new QueryWrapper<>();
        if(mApp.getManKey()!=null)
            wrapper.eq("man_key",mApp.getManKey()) ;
        if(mApp.getId()!=null){
            wrapper.eq("id",mApp.getId());
        }
        wrapper.like("result","通过");
        page= super.page(page,wrapper);
        return page;
    }

    /*
    批量审核通过申请
    因为学院和学校默认通过，暂时只有在学校审核过后才会修改学生申请表的结果为1
    之后考虑直接在辅导员通过后就将结果修改为1并通知学生
     */
    @Override
    public void updateAppform(List<Serializable> ids) {
        List<ManagerApplication> managerApplication = managerApplicationMapper.selectBatchIds(ids);
        for (ManagerApplication mApp : managerApplication) {
//          ManagerApplication mApp=managerApplicationMapper.selectById(id);
            if (mApp.getState() == 3) {
                mApp.setResult("已通过");
                mApp.setTime(LocalDateTime.now());
//                mApp.setReason("学校用户默认通过1");
                mApp.setResult("已通过");
                mApp.insertOrUpdate();//更新数据库
                //通知学生
                QueryWrapper<Applicationform> wrapper=new QueryWrapper<>();
                wrapper.eq("aid",mApp.getAppKey());
                Applicationform app=applicationformMapper.selectOne(wrapper);
                app.setResult(true);
                app.insertOrUpdate();
            } else if (mApp.getState() == 2)//学院提交审核
            {
//                mApp.setState(mApp.getState() + 1);
                //为学校用户创建新的记录
                mApp.setResult("已通过");
                ManagerApplication mApp1 = new ManagerApplication();
                mApp.setTime(LocalDateTime.now());
                mApp.insertOrUpdate();
                mApp1.setAppKey(mApp.getAppKey());
                mApp1. setResult("待审核");//默认学校用户通过申请
                mApp1.setState(3);//自动提交
                QueryWrapper<Manager> wrapper = new QueryWrapper();
                wrapper.eq("mname", "学校").eq("mlevel", 3);
                Manager manager = managerMapper.selectOne(wrapper);
                mApp1.setManKey(manager.getMid());

                managerApplicationMapper.insert(mApp1);
                //插入新的审核数据，设置学校管理员为下一级审核员。
            } else if (mApp.getState() == 1)//辅导员提交申请
            {
//                mApp.setState(mApp.getState() + 1);
                mApp.setResult("已通过");
                mApp.setTime(LocalDateTime.now());
                mApp.insertOrUpdate();
                //为学校用户创建新的记录
                ManagerApplication mApp1 = new ManagerApplication();
                mApp1.setAppKey(mApp.getAppKey());
//                mApp1.setReason("学院用户默认通过");
                mApp1.setResult("待审核");//默认学院用户通过申请
                mApp1.setState(2);//自动提交
                QueryWrapper<Manager> manWrapper = new QueryWrapper();
                manWrapper.eq("mid",mApp.getManKey());
                Manager man1=managerMapper.selectOne(manWrapper);
                QueryWrapper<Manager> wrapper = new QueryWrapper();
                wrapper.eq("major", man1.getMajor()).eq("mlevel", 2);
                Manager manager = managerMapper.selectOne(wrapper);
                mApp1.setManKey(manager.getMid());
                managerApplicationMapper.insert(mApp1);
                //插入新的审核数据，设置学院管理员的人为下一级审核员。
            }

        }

    }

    /*
    批量拒绝
     */
    @Override
    public void updateAppformDis(List<Serializable> ids) {
        List<ManagerApplication> managerApplication = managerApplicationMapper.selectBatchIds(ids);
        for (ManagerApplication mApp : managerApplication) {
//          ManagerApplication mApp=managerApplicationMapper.selectById(id);
            if (mApp.getState() == 3) {
                mApp.setResult("未通过");
                mApp.setTime(LocalDateTime.now());
                mApp.setReason("管理员批量操作，无法查看拒绝原因");
                mApp.insertOrUpdate();//更新数据库
            } else if (mApp.getState() == 2)//学院提交审核
            {
                mApp.setResult("未通过");
                mApp.setReason("管理员批量操作，无法查看拒绝原因");
                mApp.setTime(LocalDateTime.now());
                mApp.insertOrUpdate();
            } else if (mApp.getState() == 1)//辅导员提交申请
            {
                mApp.setResult("未通过");
                mApp.setReason("管理员批量操作，无法查看拒绝原因");
                mApp.setTime(LocalDateTime.now());
                mApp.insertOrUpdate();
            }
                    //通知学生
            QueryWrapper<Applicationform> wrapper=new QueryWrapper<>();
            wrapper.eq("aid",mApp.getAppKey());
            Applicationform app=applicationformMapper.selectOne(wrapper);
            app.setResult(false);
            app.insertOrUpdate();
        }
    }
    @Override
    public Student getApperInfo(Serializable id) {
        ManagerApplication mApp=managerApplicationMapper.selectById(id);
        QueryWrapper<Applicationform> wrapper1=new QueryWrapper<>();
        wrapper1.eq("aid",mApp.getAppKey());
        Applicationform appf=applicationformMapper.selectOne(wrapper1);
        QueryWrapper<Student> wrapper2=new QueryWrapper<>();
        wrapper2.eq("sid",appf.getStuKey());
        Student student=studentMapper.selectOne(wrapper2);
        return student;
    }

    @Override
    public Applicationform getsAppInfo(Serializable id) {
        ManagerApplication mApp=managerApplicationMapper.selectById(id);
        QueryWrapper<Applicationform> wrapper1=new QueryWrapper<>();
        wrapper1.eq("aid",mApp.getAppKey());
        Applicationform appf=applicationformMapper.selectOne(wrapper1);
        return appf;
    }

    @Override
    public void storeReason(Serializable id,String reason) {
        ManagerApplication mApp=managerApplicationMapper.selectById(id);
        if(mApp!=null){
        mApp.setReason(reason);
        mApp.insertOrUpdate();}
    }

    @Override
    public void updateOneDisApp(List<Serializable> singletonList) {
        List<ManagerApplication> managerApplication = managerApplicationMapper.selectBatchIds(singletonList);
        for (ManagerApplication mApp : managerApplication) {
            if (mApp.getState() == 3) {
                mApp.setResult("未通过");
                mApp.setTime(LocalDateTime.now());
                mApp.insertOrUpdate();//更新数据库
            } else if (mApp.getState() == 2)//学院提交审核
            {
                mApp.setResult("未通过");
                mApp.setTime(LocalDateTime.now());
                mApp.insertOrUpdate();
            } else if (mApp.getState() == 1)//辅导员提交申请
            {
                mApp.setResult("未通过");
                mApp.setTime(LocalDateTime.now());
                mApp.insertOrUpdate();
            }
            //通知学生
            QueryWrapper<Applicationform> wrapper=new QueryWrapper<>();
            wrapper.eq("aid",mApp.getAppKey());
            Applicationform app=applicationformMapper.selectOne(wrapper);
            app.setResult(false);
            app.insertOrUpdate();
        }
    }

    @Override
    public Map<String,Object> getHisInfo(Long id) {
        ManagerApplication mApp=managerApplicationMapper.selectById(id);
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("mid",mApp.getManKey());
        Manager man=managerMapper.selectOne(wrapper);
        Map<String,Object> map=new HashMap<>();
        map.put("mname",man.getMname());
        map.put("mankey",man.getMid());
        map.put("mlevel",man.getMlevel());
        map.put("grade",man.getGrade());
        map.put("time",mApp.getTime());
        map.put("reason",mApp.getReason());
        map.put("result",mApp.getResult());
        return map;
    }

}

