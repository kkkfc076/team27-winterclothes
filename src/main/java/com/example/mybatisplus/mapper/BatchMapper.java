package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Batch;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author team27
 * @since 2022-06-25
 */
@Repository
public interface BatchMapper extends BaseMapper<Batch> {

    Batch getBidByTime();

    Batch getPermission();
}
