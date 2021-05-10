package com.lq.daoyun.service;

import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.SignIn;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-22
 */
public interface ISignInService extends IService<SignIn> {

    RespBean getSignInListByTeacherID(HttpServletRequest request);

    RespBean getSignInListByTeacherID(Integer id);

    RespBean getAllSignIn();

    RespBean addSignIn(SignIn signIn);

    RespBean updateSignInById(SignIn signIn);
}
