package com.example.mybatisplus.model.dto;
import lombok.Data;
//用来分页
@Data
public class PageDTO {
    private Integer pageNo=1;
    private Integer pageSize=10;
}
