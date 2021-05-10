package com.lq.daoyun.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description: 第三方ID绑定用户ID
 * @author: LiamQ
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ThirdIDBindUserID对象", description="")
public class ThirdIDBindUserID {

    private Integer thirdPartyID;

    private Integer userID;
}
