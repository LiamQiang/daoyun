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
@ApiModel(value="TaskMemory对象", description="")
public class TaskMemory implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userid;

    private Integer isParticipate;

    private Integer grade;

    private String task;

    private Integer classid;

    private Integer team;

    private LocalDateTime deadline;


}
