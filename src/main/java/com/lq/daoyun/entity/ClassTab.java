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
@ApiModel(value="ClassTab对象", description="")
public class ClassTab implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer classid;

    private String classname;

    private String teachername;

    private Integer teacherid;

    private String type;

    private String master;

    private Integer peopleCount;

    private String location;

    private String classtime;

    private String college;


}
