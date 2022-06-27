package com.example.mybatisplus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Applicationform;
import com.example.mybatisplus.model.domain.ManagerApplication;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplus.model.dto.PageDTO;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author team27
 * @since 2022-06-24
 */
public interface ManagerApplicationService extends IService<ManagerApplication> {

    Page<ManagerApplication> pagelist(PageDTO pageDTO, ManagerApplication mApp);


    Page<ManagerApplication> checkedPagelist(PageDTO pageDTO, ManagerApplication mApp);

//    void updateAppform(List<Serializable> ids);
        void updateAppform(List<Serializable> ids);

    void updateAppformDis(List<Serializable> ids);
}
