package com.lq.daoyun.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description:
 * @author: LiamQ
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "新增用户DTO对象", description = "")
public class AddUserDTO {
    @ApiModelProperty(value = "编号")
    private String number;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "头像")
    private String image;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "学校")
    private String school;

    @ApiModelProperty(value = "学院")
    private String department;

    @ApiModelProperty(value = "master")
    private String master;

    @ApiModelProperty(value = "手机号")
    private String phonenumber;

    @ApiModelProperty(value = "角色")
    private Integer role;
}
