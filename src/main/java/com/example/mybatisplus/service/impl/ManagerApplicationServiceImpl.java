package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.utls.SecurityUtils;
import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.mapper.ApplicationformMapper;
import com.example.mybatisplus.mapper.ManagerMapper;
import com.example.mybatisplus.model.domain.Applicationform;
import com.example.mybatisplus.model.domain.Manager;
import com.example.mybatisplus.model.domain.ManagerApplication;
import com.example.mybatisplus.mapper.ManagerApplicationMapper;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.model.dto.UserInfoDTO;
import com.example.mybatisplus.service.ManagerApplicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
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
    @Override
    public Page<ManagerApplication> pagelist(PageDTO pageDTO, ManagerApplication mApp) {
        Page<ManagerApplication> page=new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
        QueryWrapper<ManagerApplication> wrapper=new QueryWrapper<>();
        if(mApp.getManKey()!=null)
           wrapper.eq("man_key",mApp.getManKey()) ;
        if(mApp.getId()!=null){
            wrapper.eq("id",mApp.getId());
        }
        wrapper.eq("state",0);
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
        wrapper.eq("state",1);
        page= super.page(page,wrapper);
        return page;
    }

    @Override
    public void updateAppform(List<Serializable> ids) {
        List<ManagerApplication> managerApplication = managerApplicationMapper.selectBatchIds(ids);
        for (ManagerApplication mApp : managerApplication) {
//          ManagerApplication mApp=managerApplicationMapper.selectById(id);
            if (mApp.getState() == 3) {
                mApp.setTime(LocalDateTime.now());
                mApp.setReason("学校用户默认通过1");
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
                ManagerApplication mApp1 = new ManagerApplication();
                mApp.setTime(LocalDateTime.now());
                mApp.insertOrUpdate();
                mApp1.setAppKey(mApp.getAppKey());
                mApp1. setResult("已通过");//默认学校用户通过申请
                mApp1.setState(3);//自动提交
                QueryWrapper<Manager> wrapper = new QueryWrapper();
                wrapper.eq("mname", "学校测试").eq("mlevel", 3);
                Manager manager = managerMapper.selectOne(wrapper);
                mApp1.setManKey(manager.getMid());
                managerApplicationMapper.insert(mApp1);
                //插入新的审核数据，设置学校管理员为下一级审核员。
            } else if (mApp.getState() == 1)//辅导员提交申请
            {
//                mApp.setState(mApp.getState() + 1);
                mApp.setResult("审核中");
                mApp.setTime(LocalDateTime.now());
                mApp.insertOrUpdate();
                //为学校用户创建新的记录
                ManagerApplication mApp1 = new ManagerApplication();
                mApp1.setAppKey(mApp.getAppKey());
                mApp1.setReason("学院用户默认通过");
                mApp1.setResult("已通过");//默认学院用户通过申请
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
}
