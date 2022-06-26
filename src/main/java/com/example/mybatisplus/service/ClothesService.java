package com.example.mybatisplus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.model.domain.Clothes;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplus.model.domain.Student;
import com.example.mybatisplus.model.dto.PageDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author team27
 * @since 2022-06-26
 */
public interface ClothesService extends IService<Clothes> {


    Page<Clothes> pageListtoM(PageDTO pageDTO, Clothes clothes);
    Page<Clothes> pageList(PageDTO pageDTO,Student student1);

}
