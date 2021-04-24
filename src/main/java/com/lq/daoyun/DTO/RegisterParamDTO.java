package com.lq.daoyun.DTO;

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
public class RegisterParamDTO {
    @ApiModelProperty(value = "用户角色(学生0/教师1)", required = true)
    private Integer role;
    @ApiModelProperty(value = "用户名/手机号", required = true)
    private String phonenumber;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "性别", required = true)
    private Integer sex;
    @ApiModelProperty(value = "学校", required = true)
    private String school;
    @ApiModelProperty(value = "昵称", required = false)
    private String nickname;
    @ApiModelProperty(value = "学院", required = true)
    private String department;
    @ApiModelProperty(value = "专业", required = false)
    private String master;
}
