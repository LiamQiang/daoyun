package com.lq.daoyun.controller;

import com.lq.daoyun.DTO.AdminLoginParamDTO;
import com.lq.daoyun.DTO.PhoneLoginParamDTO;
import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.service.IAdminService;
import com.lq.daoyun.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录
 */
@Api(tags = "系统登录相关接口")
@RestController
public class LoginController {

    private final IAdminService adminService;
    @Autowired
    private IUserService userService;

    public LoginController(IAdminService adminService) {
        this.adminService = adminService;
    }

    @ApiOperation(value = "[后台管理系统]管理员登录,成功登录之后，返回token")
    @PostMapping("/admin/login")
    public RespBean login(@RequestBody AdminLoginParamDTO adminLoginParamDTO, HttpServletRequest request){
        return adminService.login(adminLoginParamDTO.getUsername(), adminLoginParamDTO.getPassword(), request);
    }

    @ApiOperation(value = "[后台管理系统/移动端]教师登录,成功登录之后，返回token")
    @PostMapping("/teacher/login")
    public RespBean teacherLogin(@RequestBody AdminLoginParamDTO adminLoginParamDTO, HttpServletRequest request){
        return userService.teacherLogin(adminLoginParamDTO.getUsername(), adminLoginParamDTO.getPassword(), request);
    }

    @ApiOperation(value = "[移动端]学生登录,成功登录之后，返回token")
    @PostMapping("/student/login")
    public RespBean studentLogin(@RequestBody AdminLoginParamDTO adminLoginParamDTO, HttpServletRequest request){
        return userService.studentLogin(adminLoginParamDTO.getUsername(), adminLoginParamDTO.getPassword(), request);
    }


    @ApiOperation(value = "退出登录,前端将已经保存的token删除即可")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("注销成功！");
    }


    @ApiOperation(value = "[后台管理系统/移动端]手机登录,成功登录之后，返回token")
    @PostMapping("/phone/login")
    public RespBean loginByPhone(@RequestBody PhoneLoginParamDTO phoneLoginParamDTO){

        return userService.loginByPhone(phoneLoginParamDTO);
    }

    @ApiOperation(value = "自动登录(用于快速注册后)")
    @PostMapping("/quick-login")
    public RespBean quickLogin(@RequestBody String phonenumber){
        return userService.quickLogin(phonenumber);
    }


}
