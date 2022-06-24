package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Manager;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * ???еĹ???Ա?˺ţ?????ϵͳ????Ա??????Ա??ѧԺ??ѧУ?û? 服务类
 * </p>
 *
 * @author team27
 * @since 2022-06-24
 */
public interface ManagerService extends IService<Manager> {

    Manager manlogin(Manager manager);
}
