package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.mapper.StudentMapper;
import com.example.mybatisplus.model.domain.Manager;
import com.example.mybatisplus.mapper.ManagerMapper;
import com.example.mybatisplus.service.ManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
