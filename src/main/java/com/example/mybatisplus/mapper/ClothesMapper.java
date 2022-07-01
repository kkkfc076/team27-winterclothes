package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Clothes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.example.mybatisplus.model.domain.Student;

import java.util.List;

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

    Clothes getByCidAndBatKey(@Param("sid") Integer sid,@Param("bid") Integer bid);

    Clothes getByCid(Integer cid);

    List<Integer> selectCids();

    void setNums(Integer cid);

    List<Clothes> All();
}
