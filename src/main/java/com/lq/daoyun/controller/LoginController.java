package com.lq.daoyun.controller;


import com.lq.daoyun.Entity.RespBean;
import com.lq.daoyun.Entity.UserLoginParam;
import com.lq.daoyun.service.ITUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "LoginController")
/**
 * Spring4之后新加入的注解，原来返回json需要@ResponseBody和@Controller配合。
 * 即@RestController是@ResponseBody和@Controller的组合注解。
 */
@RestController
public class LoginController {

    @Autowired
    private ITUserService userService;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(UserLoginParam userLoginParam, HttpServletRequest request){
        return userService.login(userLoginParam.getUsername(), userLoginParam.getPassword(), request);
    }

}
