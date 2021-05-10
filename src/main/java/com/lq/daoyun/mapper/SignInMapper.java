package com.lq.daoyun.mapper;

import com.lq.daoyun.pojo.SignIn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-22
 */
public interface SignInMapper extends BaseMapper<SignIn> {

    List<SignIn> getSignInListByTeacherID(Integer teacherid);
}
