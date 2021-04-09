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
@ApiModel(value="Menu对象", description="")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer parentmenuNumber;

    private String menuname;

    private String buttonEnglishLogo;

    private String buttonChineseLogo;

    private Integer isMenu;

    private String creator;

    private LocalDateTime creationdate;

    private LocalDateTime modificationdate;

    private String modifier;


}
