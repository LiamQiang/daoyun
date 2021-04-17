package com.lq.daoyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lq.daoyun.config.security.JwtTokenUtil;
import com.lq.daoyun.pojo.RegisterParam;
import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.User;
import com.lq.daoyun.mapper.UserMapper;
import com.lq.daoyun.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
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
    public RespBean addUser(RegisterParam registerParam) {
        if (registerParam.getPhonenumber() == null || registerParam.getPhonenumber() == "" || registerParam.getPassword() == null || registerParam.getPassword() == ""){
            return RespBean.error("注册失败！用户名或密码不能为空!");
        }
        else {
            User user = new User();
            // user.setNumber(null);
            user.setPassword(new BCryptPasswordEncoder().encode(registerParam.getPassword()));
            user.setUsername(registerParam.getPhonenumber());
            // user.setImage(null);
            user.setNickname("Nickname");
            // 默认为男
            user.setSex(0);
            user.setSchool("福州大学");
            user.setDepartment("数计院");
            // user.setMaster(null);
            user.setPhonenumber(registerParam.getPhonenumber());
            // role = 0 为学生
            user.setRole(registerParam.getRole());
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
    public User getByPhonenumber(String phonenumber) {
        if (phonenumber == null || phonenumber == ""){
            return null;
        } else {
            User user = new User();
            QueryWrapper<User> userQueryWrapper = Wrappers.query();
            user = userMapper.selectOne(userQueryWrapper.eq("phonenumber", phonenumber));
            if (user != null){
                return user;
            } else {
                return null;
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
        User user = getByPhonenumber(username);
        if (null == user || !passwordEncoder.matches(password, user.getPassword()) || (user.getRole() != 1)){
            return RespBean.error("用户名或密码不正确!");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String token = jwtTokenUtil.generateToken(user);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登录成功！", tokenMap);
    }

    /**
     * 根据请求头的token获取用户信息
     * @param request
     */
    @Override
    public User getUserInfoByToken(HttpServletRequest request) {
        String authHeader = request.getHeader(tokenHeader);
        // 存在token
        if (null != authHeader && authHeader.startsWith(tokenHead)){
            String authToken = authHeader.substring((tokenHead.length()));
            String username = jwtTokenUtil.getUserNameFormToken(authToken);
            if (null == username){
               return null;
            }
            User user = getByPhonenumber(username);
            return user;
        }
        return null;
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
        User user = getByPhonenumber(username);
        if (null == user || !passwordEncoder.matches(password, user.getPassword()) || (user.getRole() != 0)){
            return RespBean.error("用户名或密码不正确!");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String token = jwtTokenUtil.generateToken(user);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登录成功！", tokenMap);
    }


}
