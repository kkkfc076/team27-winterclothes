package com.example.mybatisplus.mapper;

        import com.example.mybatisplus.model.domain.Manager;
        import com.baomidou.mybatisplus.core.mapper.BaseMapper;
        import org.springframework.stereotype.Repository;

/**
 * <p>
 * ???еĹ???Ա?˺ţ?????ϵͳ????Ա??????Ա??ѧԺ??ѧУ?û? Mapper 接口
 * </p>
 *
 * @author team27
 * @since 2022-06-24
 */
@Repository
public interface ManagerMapper extends BaseMapper<Manager> {

    Integer modifyP(Manager man);
}
