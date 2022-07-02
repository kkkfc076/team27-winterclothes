package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.Batch;
import com.example.mybatisplus.mapper.BatchMapper;
import com.example.mybatisplus.service.BatchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author team27
 * @since 2022-06-25
 */
@Service
public class BatchServiceImpl extends ServiceImpl<BatchMapper, Batch> implements BatchService {

    @Override
    public Batch newBatch(Batch batch) {
        Batch batch1=new Batch();
        batch1.setBid(batch.getBid());
        batch1.setStartdate(batch.getStartdate());
        batch1.setEnddate(batch.getEnddate());
        return batch1;
    }

    @Override
    public Boolean getPermission() {
        Boolean temp=false;
        Batch batch=baseMapper.getPermission();
        if(batch!=null){
            temp=true;
        }
        return temp;
    }
}
