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
@ApiModel(value="SignIn对象", description="")
public class SignIn implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userid;

    private Integer signInType;

    private LocalDateTime enddate;

    private String publisher;

    private Integer classid;

    private LocalDateTime startdate;


}
