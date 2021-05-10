package com.lq.daoyun.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description:
 * @author: LiamQ
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="github对象", description="")
public class GithubUser {

        private Long id;
        private String name;
        private String bio;


}
