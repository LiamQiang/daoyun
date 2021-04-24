package com.lq.daoyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lq.daoyun.DTO.PhoneLoginParamDTO;
import com.lq.daoyun.DTO.QuickRegisterParamsDTO;
import com.lq.daoyun.config.security.JwtTokenUtil;
import com.lq.daoyun.controller.SmsController;
import com.lq.daoyun.DTO.RegisterParamDTO;
import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.User;
import com.lq.daoyun.mapper.UserMapper;
import com.lq.daoyun.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Override
    public RespBean addUser(RegisterParamDTO registerParamDTO) {
        if (registerParamDTO.getPhonenumber() == null || registerParamDTO.getPhonenumber() == "" || registerParamDTO.getPassword() == null || registerParamDTO.getPassword() == ""){
            return RespBean.error("注册失败！用户名或密码不能为空!");
        }
        if (getByPhonenumber(registerParamDTO.getPhonenumber()).getObject() != null){
            return RespBean.error("注册失败！用户已经存在！");
        } else {
            User user = new User();
            // user.setNumber(null);
            user.setPassword(new BCryptPasswordEncoder().encode(registerParamDTO.getPassword()));
            user.setUsername(registerParamDTO.getPhonenumber());
            // user.setImage(null);
            user.setNickname(registerParamDTO.getNickname());
            // 默认为男
            user.setSex(registerParamDTO.getSex());
            user.setSchool(registerParamDTO.getSchool());
            user.setDepartment(registerParamDTO.getDepartment());
            user.setMaster(registerParamDTO.getMaster());
            user.setPhonenumber(registerParamDTO.getPhonenumber());
            // role = 0 为学生
            user.setRole(registerParamDTO.getRole());
            user.setCreatedate(LocalDateTime.now());
            user.setCreator(1);
            user.setModifier(1);
            user.setModifyDate(LocalDateTime.now());
            userMapper.insert(user);
        }
        return RespBean.success("注册成功！");
    }

    /**
     * 根据手机号获取用户
     * @param phonenumber
     * @return
     */
    @Override
    public RespBean getByPhonenumber(String phonenumber) {
        if (phonenumber == null || phonenumber == ""){
            return null;
        } else {
            User user = new User();
            QueryWrapper<User> userQueryWrapper = Wrappers.query();
            user = userMapper.selectOne(userQueryWrapper.eq("phonenumber", phonenumber));
            if (user != null){
                return RespBean.success("查询成功", user);
            } else {
                return RespBean.error("用户不存在!");
            }
        }

    }

    /**
     * 教师用户登录，user.role = 1
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public RespBean teacherLogin(String username, String password, HttpServletRequest request) {
        User user = (User) getByPhonenumber(username).getObject();
        if (null == user || !passwordEncoder.matches(password, user.getPassword()) || (user.getRole() != 1)){
            return RespBean.error("用户名或密码不正确!");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String token = jwtTokenUtil.generateToken(user);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登录成功！用户角色为：教师", tokenMap);
    }

    /**
     * 根据请求头的token获取用户信息
     * @param request
     */
    @Override
    public RespBean getUserInfoByToken(HttpServletRequest request) {
        String authHeader = request.getHeader(tokenHeader);
        // 存在token
        if (null != authHeader && authHeader.startsWith(tokenHead)){
            String authToken = authHeader.substring((tokenHead.length()));
            String username = jwtTokenUtil.getUserNameFormToken(authToken);
            if (null == username){
               return null;
            }
            User user = (User) getByPhonenumber(username).getObject();
            return RespBean.success("查询信息成功！",user);
        }
        return RespBean.error("查询失败!");
    }

    /**
     * 学生用户登录，user.role = 0
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public RespBean studentLogin(String username, String password, HttpServletRequest request) {
        User user = (User) getByPhonenumber(username).getObject();
        if (null == user || !passwordEncoder.matches(password, user.getPassword()) || (user.getRole() != 0)){
            return RespBean.error("用户名或密码不正确!");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String token = jwtTokenUtil.generateToken(user);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登录成功！用户角色为：学生", tokenMap);
    }

    /**
     * 根据手机号验证登录
     * @param phoneLoginParamDTO
     * @return
     */
    @Override
    public RespBean loginByPhone(PhoneLoginParamDTO phoneLoginParamDTO) {
        User user = (User) getByPhonenumber(phoneLoginParamDTO.getPhonenumber()).getObject();
        if (null == user) {
            return RespBean.error("用户不存在!");
        }
        // 短信验证成功code=200，失败code=500
        RespBean respBean = SmsController.verifySmsCode(phoneLoginParamDTO.getPhonenumber(), phoneLoginParamDTO.getSmsCode());
        if (respBean.getCode() == 200) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            String token = jwtTokenUtil.generateToken(user);
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", tokenHead);
            return RespBean.success("登录成功！", tokenMap);
        }else {
            return RespBean.error("验证码不正确！");
        }

    }

    /**
     * 快速注册，只需要填写手机号密码和角色信息
     * @param quickRegisterParamsDTO
     * @return
     */
    @Override
    public RespBean quickRegisterAddUser(QuickRegisterParamsDTO quickRegisterParamsDTO) {
        if (quickRegisterParamsDTO.getPhonenumber() == null || quickRegisterParamsDTO.getPhonenumber() == "" || quickRegisterParamsDTO.getPassword() == null || quickRegisterParamsDTO.getPassword() == ""){
            return RespBean.error("注册失败！用户名或密码不能为空!");
        }
        if (getByPhonenumber(quickRegisterParamsDTO.getPhonenumber()).getObject() != null){
            return RespBean.error("注册失败！用户已经存在！");
        }else {
            User user = new User();
            user.setPhonenumber(quickRegisterParamsDTO.getPhonenumber());
            user.setPassword(new BCryptPasswordEncoder().encode(quickRegisterParamsDTO.getPassword()));
            user.setRole(quickRegisterParamsDTO.getRole());
            try {
                userMapper.insert(user);
                Map<String, String> map = new HashMap<>();
                map.put("username", user.getPhonenumber());
                return RespBean.success("注册成功！", map);
            }catch (Exception e){
                return RespBean.error("注册失败！", e);
            }
        }

    }

    /**
     * 快速注册后通过手机号快速登录
     * @param phonenumber
     * @return
     */
    @Override
    public RespBean quickLogin(String phonenumber) {
        User user = (User) getByPhonenumber(phonenumber).getObject();
        if (user == null){
            return RespBean.error("快速登录失败！用户为空！");
        }else {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            String token = jwtTokenUtil.generateToken(user);
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", tokenHead);
            return RespBean.success("登录成功！", tokenMap);
        }
    }
}
