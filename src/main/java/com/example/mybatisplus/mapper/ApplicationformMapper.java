package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Applicationform;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.model.domain.Clothes;
import com.example.mybatisplus.model.domain.Student;
import com.example.mybatisplus.model.dto.PageDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author team27
 * @since 2022-06-24
 */
@Repository
public interface ApplicationformMapper extends BaseMapper<Applicationform> {

    Integer updateReason(Applicationform applicationform);

    Integer updateCid(Integer sid,Integer satKey);

    Applicationform getApp(Integer sid, Integer batKey);

    Applicationform addStukey(@Param("sid") Integer sid, @Param("reason") String reason);

    Integer updateCid(Applicationform applicationform);

    Applicationform getByStukey(Integer sid, Integer bid);
}
