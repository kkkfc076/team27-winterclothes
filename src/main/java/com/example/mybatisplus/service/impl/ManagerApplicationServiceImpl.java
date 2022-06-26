package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.utls.SecurityUtils;
import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.model.domain.Applicationform;
import com.example.mybatisplus.model.domain.Manager;
import com.example.mybatisplus.model.domain.ManagerApplication;
import com.example.mybatisplus.mapper.ManagerApplicationMapper;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.model.dto.UserInfoDTO;
import com.example.mybatisplus.service.ManagerApplicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public Page<ManagerApplication> pagelist(PageDTO pageDTO, ManagerApplication mApp) {
        Page<ManagerApplication> page=new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
        QueryWrapper<ManagerApplication> wrapper=new QueryWrapper<>();
        UserInfoDTO userInfo=SecurityUtils.getUserInfo();
        if(mApp.getManKey()!=null)
           wrapper.eq("mankey",userInfo.getMid()) ;
        if(mApp.getId()!=null){
            wrapper.eq("id",userInfo.getId());
        }
        wrapper.eq("state",0);
        page= super.page(page,wrapper);
        return page;
    }
}
