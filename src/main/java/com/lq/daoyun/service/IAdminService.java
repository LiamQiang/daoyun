package com.lq.daoyun.service;

import com.lq.daoyun.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lq.daoyun.pojo.RespBean;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-08
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 管理员，登录之后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    RespBean login(String username, String password, HttpServletRequest request);


    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    Admin getAdminByUserName(String username);

}
