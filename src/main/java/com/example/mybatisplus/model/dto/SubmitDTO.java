package com.example.mybatisplus.model.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SubmitDTO {
    private List<Serializable> ids;
    private Serializable id;
}
