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
@ApiModel(value="SignIn对象", description="")
public class SignIn implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer courseId;

    @ApiModelProperty(value = "签到类型，一键签到：0,显示签到1")
    private Integer type;

    private LocalDateTime startTime;

    private LocalDateTime timeLimit;

    @ApiModelProperty(value = "签到发起经度")
    private Float longitude;

    @ApiModelProperty(value = "签到发起维度")
    private Float latitude;


}
