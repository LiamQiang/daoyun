package com.lq.daoyun.controller;


import com.lq.daoyun.DTO.AdminLoginParamDTO;
import com.lq.daoyun.pojo.Course;
import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.SysParam;
import com.lq.daoyun.service.ICourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-22
 */
@Api(tags = "课程表相关接口")
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private ICourseService iCourseService;

    @ApiOperation(value = "添加一个课程", notes = "不需要传入id或者id设置为0，数据库会自动生成主键id")
    @PostMapping()
    public RespBean addCourseByTeacher(@RequestBody Course course, HttpServletRequest request){
        return iCourseService.addCourseByTeacher(course, request);
    }
    @ApiOperation(value = "根据ID删除一个课程", notes = "需要传入正确的id以完成数据库的修改")
    @DeleteMapping("/{courseid}")
    public RespBean deleteCourseByTeacher(@RequestParam Integer courseid, HttpServletRequest request){
        return iCourseService.deleteCourseByTeacher(courseid, request);
    }
    @ApiOperation(value = "根据ID更新一个课程", notes = "需要传入正确的id以完成数据库的修改")
    @PutMapping("/{courseid}")
    public RespBean updateourseByTeacher(@RequestBody Course course, HttpServletRequest request){
        return iCourseService.updateCourseByTeacher(course, request);
    }

    @ApiOperation(value = "获取所有课表信息", notes = "学生用户不能调用该接口")
    @GetMapping()
    public RespBean getAllCourse(HttpServletRequest request){

        return iCourseService.getAllCourse(request);
    }

    @ApiOperation(value = "根据ID获取课程信息", notes = "")
    @GetMapping("/{id}")
    public RespBean getCourseById( @PathVariable Integer id){

        return iCourseService.getCourseById(id);
    }


}
