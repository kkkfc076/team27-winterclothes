package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Applicationform;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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


    Applicationform getByStukey(@Param("sid") Integer sid);


    Integer updateReason(Applicationform applicationform);
}
