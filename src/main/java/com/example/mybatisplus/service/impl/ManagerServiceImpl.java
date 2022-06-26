package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.mapper.StudentMapper;
import com.example.mybatisplus.model.domain.Manager;
import com.example.mybatisplus.mapper.ManagerMapper;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.service.ManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


}
