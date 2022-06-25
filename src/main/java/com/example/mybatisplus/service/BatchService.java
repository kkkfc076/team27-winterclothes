package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Batch;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author team27
 * @since 2022-06-25
 */
public interface BatchService extends IService<Batch> {

    Batch newBatch(Batch batch);
}
