package com.lq.daoyun.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value="CheckTab对象", description="")
public class CheckTab implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userTabCheckTabId;

    private Integer type;

    private Integer thirdparty;

    private String identify;

    private String passwordtoken;

    private LocalDateTime creationdate;

    private Integer creater;

    private LocalDateTime modifydate;

    private Integer modifier;


}
