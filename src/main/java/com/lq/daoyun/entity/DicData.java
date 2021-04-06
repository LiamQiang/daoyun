package com.lq.daoyun.entity;

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
@ApiModel(value="DicData对象", description="")
public class DicData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dicType;

    private String dicKey;

    private String dicValue;

    private Integer defaultvalue;


}
