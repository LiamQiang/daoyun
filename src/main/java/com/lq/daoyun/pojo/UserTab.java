package com.lq.daoyun.pojo;

import java.time.LocalDateTime;
import java.sql.Blob;
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
@ApiModel(value="UserTab对象", description="")
public class UserTab implements Serializable {

    private static final long serialVersionUID = 1L;

    private String number;

    private String password;

    private String name;

    private String image;

    private String nickname;

    private Integer sex;

    private String school;

    private String department;

    private String master;

    private String phonenumber;

    private String role;

    private LocalDateTime createdate;

    private Integer creator;

    private Integer modifier;

    private LocalDateTime modifyDate;


}
