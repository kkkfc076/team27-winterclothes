package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.model.domain.Clothes;
import com.example.mybatisplus.mapper.ClothesMapper;
import com.example.mybatisplus.model.domain.Student;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.service.ClothesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author team27
 * @since 2022-06-26
 */
@Service
public class ClothesServiceImpl extends ServiceImpl<ClothesMapper, Clothes> implements ClothesService {


    @Override
    public Page<Clothes> pageList(PageDTO pageDTO, Student student) {
        Page<Clothes> page = new Page<>(pageDTO.getPageNo(),pageDTO.getPageSize());
        String sex=student.getSex();
        QueryWrapper<Clothes> wrapper =new QueryWrapper<>();
        if (sex != null && sex != "") {
            wrapper.eq("sex",sex);
        }
        page=super.page(page,wrapper);
        return page;
    }
}
