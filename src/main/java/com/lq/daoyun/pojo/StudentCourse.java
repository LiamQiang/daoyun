package com.lq.daoyun.pojo;

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
@ApiModel(value="StudentCourse对象", description="")
public class StudentCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer courseId;

    private Integer studentId;

    @ApiModelProperty(value = "经验值")
    private Integer exp;


}
