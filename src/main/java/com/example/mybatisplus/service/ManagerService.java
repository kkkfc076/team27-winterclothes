package com.example.mybatisplus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Manager;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplus.model.dto.PageDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    Integer modifyP(Manager man1);


    Page<Manager> pageList(PageDTO pageDTO, Manager manager);
}
