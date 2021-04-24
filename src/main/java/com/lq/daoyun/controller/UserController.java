package com.lq.daoyun.controller;


import com.lq.daoyun.DTO.QuickRegisterParamsDTO;
import com.lq.daoyun.DTO.RegisterParamDTO;
import com.lq.daoyun.config.security.JwtTokenUtil;
import com.lq.daoyun.pojo.*;
import com.lq.daoyun.pojo.User;
import com.lq.daoyun.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @ApiOperation(value = "学生或者教师用户注册", notes = "role字段：0代表学生，1代表教师")
    @PostMapping("/register")
    public RespBean registerUser(@RequestBody RegisterParamDTO registerParamDTO){
       return UserService.addUser(registerParamDTO);

    }

    @ApiOperation(value = "根据手机号/username查询当前登录用户")
    @GetMapping("/{phonenumber}")
    public RespBean getUserByPhonenumber(@PathVariable("phonenumber") String phonenumber){
        return UserService.getByPhonenumber(phonenumber);
    }

    @ApiOperation(value = "获取当前登录用户的信息")
    @GetMapping("/info")
    public RespBean getUserInfoByToken(HttpServletRequest request){
       return UserService.getUserInfoByToken(request);

    }

    @ApiOperation(value = "快速注册", notes = "role字段：0代表学生，1代表教师,成功后返回用户手机号，前端记得保存用来调用后面的快速登录")
    @PostMapping("/quick-register")
    public RespBean quickRegister(@RequestBody QuickRegisterParamsDTO quickRegisterParamsDTO){
        return UserService.quickRegisterAddUser(quickRegisterParamsDTO);

    }


}
