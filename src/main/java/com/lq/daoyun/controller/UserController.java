package com.lq.daoyun.controller;


import com.lq.daoyun.config.security.JwtTokenUtil;
import com.lq.daoyun.pojo.*;
import com.lq.daoyun.pojo.User;
import com.lq.daoyun.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ResponseHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-08
 */

@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private IUserService UserService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @ApiOperation(value = "学生或者教师用户注册")
    @PostMapping("/register")
    public RespBean registerUser(@RequestBody RegisterParam registerParam){
       return UserService.addUser(registerParam);

    }


    @ApiOperation(value = "根据手机号/username查询当前登录用户")
    @GetMapping("/{phonenumber}")
    public User getUserByPhonenumber(@PathVariable("phonenumber") String phonenumber){
        return UserService.getByPhonenumber(phonenumber);
    }

    @ApiOperation(value = "获取当前登录用户的信息")
    @GetMapping("/info")
    public User getUserInfoByToken(HttpServletRequest request){
       return UserService.getUserInfoByToken(request);

    }


}
