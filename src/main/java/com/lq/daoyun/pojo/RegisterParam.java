package com.lq.daoyun.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description: 用户注册实体类
 * @author: LiamQ
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "用户注册对象", description = "")
public class RegisterParam {
    @ApiModelProperty(value = "用户角色(学生0/教师1)", required = true)
    private Integer role;
    @ApiModelProperty(value = "用户名/手机号", required = true)
    private String phonenumber;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
