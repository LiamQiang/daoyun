package com.lq.daoyun.controller;


import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.TeacherCourse;
import com.lq.daoyun.pojo.ThirdParty;
import com.lq.daoyun.service.ITeacherCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "教师用户查询自己开设的课程，返回课程列表", notes = "请求体都不需要传数据，后台自动根据请求头的token获取教师ID进行查询")
    @GetMapping("/info")
    public RespBean getCourseListByTeacherID(HttpServletRequest request){
        return iTeacherCourseService.getCourseListByTeacherID(request);
    }

    @ApiOperation(value = "管理员根据教师ID查询教师开设的课程，返回课程列表", notes = "需要传入教师ID")
    @GetMapping("/info/{id}")
    public RespBean getCourseListByTeacherID(@PathVariable Integer id){
        return iTeacherCourseService.getCourseListByTeacherID(id);
    }

    @ApiOperation(value = "获取教师-班课关联表的所有信息")
    @GetMapping()
    public RespBean getAllTeacherCourse(){
        return iTeacherCourseService.getAllTeacherCourse();
    }


    @ApiOperation(value = "添加一条信息",notes = "不需要传入id或者id设置为0，数据库会自动生成主键id")
    @PostMapping()
    public RespBean addTeacherCourse(@RequestBody TeacherCourse teacherCourse){
        return iTeacherCourseService.addTeacherCourse(teacherCourse);
    }

    @ApiOperation(value = "根据ID修改一条信息", notes = "需要传入正确的id以完成数据库的修改")
    @PutMapping()
    public RespBean updateTTeacherCourseById(@RequestBody TeacherCourse teacherCourse){
        return iTeacherCourseService.updateTTeacherCourseById(teacherCourse);
    }

    @ApiOperation(value = "根据ID删除一条信息", notes = "传入正确的id以完成数据库的删除")
    @DeleteMapping("/{id}")
    public RespBean deleteEmp(@PathVariable Integer id) {
        try {
            boolean success = iTeacherCourseService.removeById(id);
            if (success) {
                return RespBean.success("删除成功！");
            } else {
                return RespBean.error("删除失败！数据库中无id为" + id.toString() + "的数据！");
            }
        } catch (Exception e) {
            return RespBean.error("删除失败！", e);
        }
    }
}
