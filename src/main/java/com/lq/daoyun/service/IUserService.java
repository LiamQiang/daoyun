package com.lq.daoyun.service;

import com.lq.daoyun.pojo.RegisterParam;
import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-08
 */
public interface IUserService extends IService<User> {

    /**
     * 添加一个用户完成注册
     * @param registerParam
     * @return
     */
    RespBean addUser(RegisterParam registerParam);

    /**
     * 根据手机号获取用户
     * @param phonenumber
     * @return
     */
    User getByPhonenumber(String phonenumber);

    /**
     * 教师用户登录，成功返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    RespBean teacherLogin(String username, String password, HttpServletRequest request);

    /**
     * 根据token获取用户信息
     * @param request
     */
    User getUserInfoByToken(HttpServletRequest request);
    /**
     * 学生用户登录，成功返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    RespBean studentLogin(String username, String password, HttpServletRequest request);

    /**
     * 根据手机号登录
     * @param phonenumber
     * @param smsCode
     * @return
     */
    RespBean loginByPhone(String phonenumber, String smsCode);
}
