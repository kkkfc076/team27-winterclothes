package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Applicationform;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplus.model.domain.Clothes;
import com.example.mybatisplus.model.domain.Student;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author team27
 * @since 2022-06-24
 */
public interface ApplicationformService extends IService<Applicationform> {


    Applicationform getByStukey(Integer sid, Integer bid);
    Integer updateReason(Applicationform applicationform);


    Applicationform addStukey(Integer sid, String reason);
    Integer updateCid(Applicationform applicationform);

    Applicationform getApp(Integer sid, Integer batKey);
}
