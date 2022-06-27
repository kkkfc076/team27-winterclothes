package com.example.mybatisplus.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/*
*
* 用来接受设置权限前端传回来的数据
*
* */
@Data
public class SetpermissionDTO {
    private List<Serializable> ids;
    private Serializable id;
}
