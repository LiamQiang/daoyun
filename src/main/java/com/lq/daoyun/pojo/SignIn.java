package com.lq.daoyun.pojo;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
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

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "教师班课表ID")
    private Integer teacherCourseId;

    @ApiModelProperty(value = "签到类型，一键签到：0,手势签到1")
    private Integer signInType;

    @ApiModelProperty(value = "签到开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "签到限时,单位秒（s）")
    private Integer timeLimit;

    @ApiModelProperty(value = "签到发起经度")
    private Float longitude;

    @ApiModelProperty(value = "签到发起维度")
    private Float latitude;

    @ApiModelProperty(value = "签到记录是否可用")
    private Integer activity;

}
