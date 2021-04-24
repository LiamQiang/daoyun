package com.lq.daoyun.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description: 短信登录属性实体
 * @author: LiamQ
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "短信登录对象", description = "")
public class PhoneLoginParamDTO {
    @ApiModelProperty(value = "手机号", required = true)
    private String phonenumber;
    @ApiModelProperty(value = "短信验证码", required = true)
    private String smsCode;
}
