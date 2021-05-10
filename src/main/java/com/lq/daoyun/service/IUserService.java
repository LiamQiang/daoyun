package com.lq.daoyun.service;

import com.lq.daoyun.DTO.AddUserDTO;
import com.lq.daoyun.DTO.PhoneLoginParamDTO;
import com.lq.daoyun.DTO.QuickRegisterParamsDTO;
import com.lq.daoyun.DTO.RegisterParamDTO;
import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @param registerParamDTO
     * @return
     */
    RespBean addUser(RegisterParamDTO registerParamDTO);

    /**
     * 根据手机号获取用户
     * @param phonenumber
     * @return
     */
    RespBean getByPhonenumber(String phonenumber);
    /**
     * 根据用户号获取用户
     * @param username
     * @return
     */
    RespBean getByUsername(String username);

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
    RespBean getUserInfoByToken(HttpServletRequest request);
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
    RespBean loginByPhone(PhoneLoginParamDTO phoneLoginParamDTO);

    /**
     * 快速注册，只需要填写手机号密码和角色信息
     * @param quickRegisterParamsDTO
     * @return
     */
    RespBean quickRegisterAddUser(QuickRegisterParamsDTO quickRegisterParamsDTO);

    /**
     * 快速注册后通过手机号快速登录
     * @param phonenumber
     * @return
     */
    RespBean quickLogin(String phonenumber);

    /**
     * 根据token更新用户信息
     * @param user
     * @param request
     * @return
     */
    RespBean updateUser(User user, HttpServletRequest request);

    /**
     * 新增用户
     * @param user
     * @return
     */
    RespBean addNewUser(AddUserDTO addUserDTO);
}
