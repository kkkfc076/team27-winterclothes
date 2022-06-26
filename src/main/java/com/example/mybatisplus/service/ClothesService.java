package com.example.mybatisplus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Clothes;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplus.model.dto.PageDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author team27
 * @since 2022-06-26
 */
public interface ClothesService extends IService<Clothes> {

    Clothes getByCidAndBatKey(Integer sid);

    Page<Clothes> getDIngo(PageDTO pageDTO, Clothes clothes);
}
