package com.lq.daoyun.controller;


import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.service.ITeacherCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-22
 */
@Api(tags = "教师-班课表相关接口")
@RestController
@RequestMapping("/teacher-course")
public class TeacherCourseController {

    @Autowired
    private ITeacherCourseService iTeacherCourseService;

    @ApiOperation(value = "根据教师ID查询开设的课程，返回课程列表", notes = "请求体不需要传数据，后台根据请求头的token获取教师ID进行查询")
    @GetMapping()
    public RespBean getCourseListByTeacherID(HttpServletRequest request){
        return iTeacherCourseService.getCourseListByTeacherID(request);
    }
}
