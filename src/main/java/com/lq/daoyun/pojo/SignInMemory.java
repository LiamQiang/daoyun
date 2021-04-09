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
 * @since 2021-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SignInMemory对象", description="")
public class SignInMemory implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDateTime startdate;

    private LocalDateTime enddate;

    private String signInType;

    private String number;

    private Integer state;

    private String name;


}
