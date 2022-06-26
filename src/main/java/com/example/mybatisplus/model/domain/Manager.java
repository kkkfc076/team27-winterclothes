package com.example.mybatisplus.model.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * <p>
 * ???еĹ???Ա?˺ţ?????ϵͳ????Ա??????Ա??ѧԺ??ѧУ?û?
 * </p>
 *
 * @author team27
 * @since 2022-06-24
 */
@Getter
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Manager对象", description="???еĹ???Ա?˺ţ?????ϵͳ????Ա??????Ա??ѧԺ??ѧУ?û?")
public class Manager extends Model<Manager> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Integer mid;

    private String pwd;

    private String mname;

    private Integer mlevel;

    private String major;

    private Integer grade;

    public Boolean getPermission() {
        return permission;
    }

    public void setPermission(Boolean permission) {
        this.permission = permission;
    }

    //  private Integer mclass;
    private Boolean permission;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
