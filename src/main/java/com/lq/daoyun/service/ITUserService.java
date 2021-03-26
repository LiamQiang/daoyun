package com.lq.daoyun.service;

import com.lq.daoyun.Entity.RespBean;
import com.lq.daoyun.Entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiamQ
 * @since 2021-03-22
 */
public interface ITUserService extends IService<TUser> {


    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    RespBean login(String username, String password, HttpServletRequest request);
}
