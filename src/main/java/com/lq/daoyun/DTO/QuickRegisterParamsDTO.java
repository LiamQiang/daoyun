package com.lq.daoyun.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description: 快速注册实体类
 * @author: LiamQ
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "快速注册实体类", description = "")
public class QuickRegisterParamsDTO {
    @ApiModelProperty(value = "用户名/手机号", required = true)
    private String phonenumber;

}
