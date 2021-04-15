package com.lq.daoyun.controller;

import com.lq.daoyun.pojo.Admin;
import com.lq.daoyun.pojo.AdminLoginParam;
import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 登录
 */
@Api(tags = "登录相关接口")
@RestController
public class LoginController {

    private final IAdminService adminService;

    public LoginController(IAdminService adminService) {
        this.adminService = adminService;
    }

    @ApiOperation(value = "管理员成功登录之后，返回token")
    @PostMapping("/adminlogin")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
        return adminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword(), request);
    }

    @ApiOperation(value = "获取当前登录用户的信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){
        if (null == principal){
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUserName(username);
        admin.setPassword(null);
        return admin;
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("注销成功！");
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public RespBean registerUser(@RequestBody String username, String password){

        return RespBean.success("注册成功");

    }

}
