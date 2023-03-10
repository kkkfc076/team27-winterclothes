package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.mapper.ApplicationformMapper;
import com.example.mybatisplus.model.domain.Applicationform;
import com.example.mybatisplus.model.domain.Batch;
import com.example.mybatisplus.model.domain.Clothes;
import com.example.mybatisplus.mapper.ClothesMapper;
import com.example.mybatisplus.model.domain.Student;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.service.ClothesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private ClothesService clothesService;

    @Override
    public Clothes getByCidAndBatKey(Integer sid,Integer bid)
    {
        return clothesMapper.getByCidAndBatKey(sid,bid);
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
    public Page<Clothes> pageList(PageDTO pageDTO,Student student) {
        Page<Clothes> page = new Page<>(pageDTO.getPageNo(),pageDTO.getPageSize());
        String sex=student.getSex();
        Integer bid=SessionUtils.getCurBatch().getBid();
        QueryWrapper<Clothes> wrapper =new QueryWrapper<>();
        wrapper.select(" distinct style,cname,bat_key,sex,picture");
        if (sex != null && sex != "") {
            wrapper.eq("sex",sex);
        }
            wrapper.eq("bat_key", bid);
        page=super.page(page,wrapper);
        return page;
    }

    @Override
    public Clothes getByCid(Clothes clothes) {
        QueryWrapper<Clothes> wrapper = new QueryWrapper<>();
        wrapper.eq("cid",clothes.getCid());

        Clothes clothes1 = clothesMapper.selectOne(wrapper);
        return clothes1;
    }

    @Override
    public Map<String, Object> cloStatistics() {
        QueryWrapper<Clothes> wrapper = new QueryWrapper<>();
        Map<String,Object> map=new HashMap<>();
        Integer bid=null;
        if(SessionUtils.getCurBatch()!=null){
            bid=SessionUtils.getCurBatch().getBid();
        }
        List<Clothes> list1=clothesService.list(wrapper.eq("bat_key",bid).select("distinct style"));
        List<Clothes> list2=clothesService.list(wrapper.eq("sex","男").select("distinct style"));

        map.put("total",list1.size());
        map.put("man",list2.size());
        map.put("woman",list1.size()-list2.size());
        return map;
    }

    @Override
    public Page<Clothes> styleList(PageDTO pageDTO, Clothes clothes) {
        Page<Clothes> page=new Page<>(pageDTO.getPageNo(),pageDTO.getPageSize());
        QueryWrapper<Clothes> wrapper=new QueryWrapper<>();
        wrapper=wrapper.eq("bat_key",SessionUtils.getCurBatch().getBid());
        Student student = SessionUtils.getCurstu();
        if(StringUtils.isNotBlank(student.getSex())){
            wrapper.eq("sex",student.getSex());
        }
        if(clothes.getStyle()!=null){
            wrapper.eq("style",clothes.getStyle());
        }

        page=super.page(page,wrapper);
        return page;
    }

    @Override
    public Clothes addClothes(Clothes clothes) {
        QueryWrapper<Clothes> wrapper=new QueryWrapper<>();
        clothes.setBatKey(SessionUtils.getCurBatch().getBid());
        wrapper.eq("style",clothes.getStyle());
        List<Clothes>list=clothesService.list(wrapper);
        if (list.size()>0){
            clothes.setPicture(list.get(0).getPicture());
            clothes.setCname(list.get(0).getCname());
            clothes.setSex(list.get(0).getSex());
        }
        clothesService.save(clothes);
        clothes.setCid(Math.toIntExact(clothes.getId()));
        clothesService.updateById(clothes);
        return clothes;
    }

    @Override
    public void Statistics() {
        List<Integer> list=clothesMapper.selectCids();
        for(int i=0;i<list.size();i++){
            clothesMapper.setNums(list.get(i));
        }
    }


    @Override
    public Page<Clothes> pageListtoM(PageDTO pageDTO, Clothes clothes) {
        Page<Clothes> page=new Page<>(pageDTO.getPageNo(),pageDTO.getPageSize());
        QueryWrapper<Clothes> wrapper=new QueryWrapper<>();
        if(StringUtils.isNotBlank(clothes.getCname())){
            wrapper.eq("cname",clothes.getCname());
        }
        if(clothes.getStyle()!=null){
            wrapper.eq("style",clothes.getStyle());
        }
        if(StringUtils.isNotBlank(clothes.getSex())){
            wrapper.eq("sex",clothes.getSex());
        }
        if(clothes.getBatKey()!=null){
            wrapper.eq("bat_key",clothes.getBatKey());
        }
        page=super.page(page,wrapper);
        return page;
    }


}
