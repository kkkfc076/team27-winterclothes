package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Applicationform;
import com.baomidou.mybatisplus.extension.service.IService;

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


    Applicationform addStukey(Integer sid, String reason);
}
