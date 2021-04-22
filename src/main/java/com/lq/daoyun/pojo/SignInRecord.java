package com.lq.daoyun.pojo;

import java.time.LocalDateTime;
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
 * @since 2021-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SignInRecord对象", description="")
public class SignInRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer signInId;

    private Integer studentId;

    private LocalDateTime signTime;

    @ApiModelProperty(value = "签到经度")
    private Float longitude;

    @ApiModelProperty(value = "签到纬度")
    private Float latitude;


}
