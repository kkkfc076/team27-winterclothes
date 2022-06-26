package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Clothes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author team27
 * @since 2022-06-26
 */
@Repository
public interface ClothesMapper extends BaseMapper<Clothes> {

    Clothes getClothesMapper(@Param("sid") Integer sid);

}
