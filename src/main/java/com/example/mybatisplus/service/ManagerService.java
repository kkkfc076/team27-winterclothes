package com.example.mybatisplus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Manager;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplus.model.domain.ManagerApplication;
import com.example.mybatisplus.model.dto.PageDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

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

    Boolean setByIds(List<Serializable> ids);

    Map<String, Object> importStu(MultipartFile file) throws IOException;

    Map<String, Object> importMan(MultipartFile file);
}
