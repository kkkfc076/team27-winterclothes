package com.example.mybatisplus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Applicationform;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplus.model.domain.Clothes;
import com.example.mybatisplus.model.domain.ManagerApplication;
import com.example.mybatisplus.model.domain.Student;
import com.example.mybatisplus.model.dto.PageDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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

    void export(HttpServletResponse response);

    Page<Applicationform> getDIngo(PageDTO pageDTO, Student student);

    Map<String, Object> dataStatistics();

    void updateD(Long id);

    void addMAform(Applicationform applicationform);

}
