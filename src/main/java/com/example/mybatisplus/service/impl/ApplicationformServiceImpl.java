package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Applicationform;
import com.example.mybatisplus.mapper.ApplicationformMapper;
import com.example.mybatisplus.model.domain.Clothes;
import com.example.mybatisplus.model.domain.Student;
import com.example.mybatisplus.model.dto.PageDTO;
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
    public Applicationform getByStukey(Integer sid, Integer bid) {
        return applicationformMapper.getApp(sid,bid);
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
