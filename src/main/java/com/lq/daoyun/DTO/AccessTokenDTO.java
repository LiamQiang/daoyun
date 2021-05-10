package com.lq.daoyun.DTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description: 第三方登录DTO
 * @author: LiamQ
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "第三方登录对象", description = "")
public class AccessTokenDTO {
        private String client_id; //客户端ID。
        private String client_secret; //客户端密钥
        private String code; //作为对步骤 1 的响应而接收的代码。
        private String redirect_uri; //应用程序中的用户在授权后发送的URL
        private String state; //在步骤 1 中提供的不可猜测的随机字符串


}
