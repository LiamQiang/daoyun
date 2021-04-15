package com.lq.daoyun.pojo;

import java.time.LocalDateTime;
import java.sql.Blob;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserTab对象", description="")
public class UserTab implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    private String number;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户名")
    private String name;

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

    @ApiModelProperty(value = "创建日期")
    private LocalDateTime createdate;

    @ApiModelProperty(value = "创建者")
    private Integer creator;

    @ApiModelProperty(value = "修改者")
    private Integer modifier;

    @ApiModelProperty(value = "修改日期")
    private LocalDateTime modifyDate;


}
