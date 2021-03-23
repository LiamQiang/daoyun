package com.lq.daoyun.Entity;

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
 * @since 2021-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TClass对象", description="")
public class TClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer classId;

    private String className;

    private Integer creator;

    private LocalDateTime createTime;


}
