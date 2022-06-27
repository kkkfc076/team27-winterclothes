package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.mapper.ApplicationformMapper;
import com.example.mybatisplus.model.domain.Applicationform;
import com.example.mybatisplus.model.domain.Clothes;
import com.example.mybatisplus.mapper.ClothesMapper;
import com.example.mybatisplus.model.domain.Student;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.service.ClothesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private ClothesMapper clothesMapper;

    @Override
    public Clothes getByCidAndBatKey(Integer sid) {
        return clothesMapper.getClothesMapper(sid);
    }

    @Override
    public Page<Clothes> getDIngo(PageDTO pageDTO, Clothes clothes) {
        Page<Clothes> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
        QueryWrapper<Clothes> wrapper = new QueryWrapper<>();

        if (clothes.getBatKey() != null) {
            wrapper.like("Bid", clothes.getBatKey());
        }

        if (clothes.getCname() != null && clothes.getCname() != "") {
            wrapper.like("Cname", clothes.getCname());
        }

        if (clothes.getPicture() != null) {
            wrapper.like("Picture", clothes.getPicture());
        }
        page = super.page(page, wrapper);
        return page;
    }


    @Override
    public Page<Clothes> pageList( Student student) {
        Page<Clothes> page = new Page<>(1,100000);
        String sex=student.getSex();
        QueryWrapper<Clothes> wrapper =new QueryWrapper<>();
        if (sex != null && sex != "") {
            wrapper.eq("sex",sex);
        }
        page=super.page(page,wrapper);
        return page;
    }


}
