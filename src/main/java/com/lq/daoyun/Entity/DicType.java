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
 * @since 2021-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DicType对象", description="")
public class DicType implements Serializable {

    private static final long serialVersionUID = 1L;

    private String paraname;

    private Integer code;

    private String description;

    private Integer identity;

    private Integer creator;

    private LocalDateTime creatordate;

    private LocalDateTime modificationdate;

    private Integer modifier;


}
