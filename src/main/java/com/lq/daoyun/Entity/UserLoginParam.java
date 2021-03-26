package com.lq.daoyun.Entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录实体类
 */
@Data
// 此注解会生成equals(Object other) 和 hashCode()方法。
@EqualsAndHashCode(callSuper = false)
// @Accessors 注解用来配置lombok如何产生和显示getters和setters的方法。chain为一个布尔值，如果为true生成的set方法返回this
@Accessors(chain = true)
@ApiModel(value = "UserLoginParam对象", description = "")
public class UserLoginParam {
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    private String password;

}
