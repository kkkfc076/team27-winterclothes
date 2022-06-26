package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.model.domain.Applicationform;
import com.example.mybatisplus.mapper.ApplicationformMapper;
import com.example.mybatisplus.model.domain.Clothes;
import com.example.mybatisplus.model.domain.Student;
import com.example.mybatisplus.service.ApplicationformService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ApplicationformServiceImpl extends ServiceImpl<ApplicationformMapper, Applicationform> implements ApplicationformService {

    @Autowired
    private  ApplicationformMapper applicationformMapper;


    @Override
    public Applicationform getApp(Integer sid, Integer batKey) {
        return applicationformMapper.getApp(sid,batKey);
    }

    @Override
    public Integer updateReason(Applicationform applicationform) {
        return applicationformMapper.updateReason(applicationform);
    }

    @Override
    public Integer updateCid(Applicationform applicationform) {
        return applicationformMapper.updateCid(applicationform);
    }

}
