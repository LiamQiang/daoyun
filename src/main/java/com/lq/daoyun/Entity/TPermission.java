package com.lq.daoyun.Entity;

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
@ApiModel(value="TPermission对象", description="")
public class TPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer permId;

    private Integer parId;

    private String menuName;

    private Integer isPage;

    private String icon;

    private String link;


}
