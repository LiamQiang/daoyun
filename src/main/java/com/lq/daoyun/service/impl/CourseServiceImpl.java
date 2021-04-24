package com.lq.daoyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.lq.daoyun.controller.UserController;
import com.lq.daoyun.mapper.TeacherCourseMapper;
import com.lq.daoyun.pojo.Course;
import com.lq.daoyun.mapper.CourseMapper;
import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.TeacherCourse;
import com.lq.daoyun.pojo.User;
import com.lq.daoyun.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-22
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private TeacherCourseMapper teacherCourseMapper;

    /**
     * 获取所有课表
     * @return
     */
    @Override
    public RespBean getAllCourse(HttpServletRequest request) {
        // 通过传过来的token获取用户信息
        User userInfoByToken = (User) userService.getUserInfoByToken(request).getObject();
        // 用户角色为0（学生）
        if (userInfoByToken == null || userInfoByToken.getRole() == 0){
            return RespBean.error("查询失败！没有权限查看！");
        }
        List<Course> courseList = courseMapper.selectList(null);
        return RespBean.success("查询成功！", courseList);
    }

    /**
     * 教师添加一个课程
     * @param course
     * @param request
     * @return
     */
    @Override
    public RespBean addCourseByTeacher(Course course, HttpServletRequest request) {
        // 通过传过来的token获取用户信息
        User teacherUser = (User) userService.getUserInfoByToken(request).getObject();
        // 用户存在且角色为1（教师）
        if (teacherUser == null || teacherUser.getRole() != 1){
            return RespBean.error("添加失败！只有教师才能添加课程！");
        }
        try{
            // course表添加一行信息
            courseMapper.insert(course);
            // 教师班课中间表添加一行信息
            TeacherCourse teacherCourse = new TeacherCourse();
            teacherCourse.setCourseId(course.getId());
            teacherCourse.setTeacherId(teacherUser.getId());
            teacherCourseMapper.insert(teacherCourse);
        }catch (Exception e){
            return RespBean.error("添加失败！" + e);
        }
        return RespBean.success("添加成功！");
    }


    /**
     * 删除一个课程
     * @param courseid
     * @param request
     * @return
     */
    @Override
    public RespBean deleteCourseByTeacher(Integer courseid, HttpServletRequest request) {
        // 通过传过来的token获取用户信息
        User teacherUser = (User) userService.getUserInfoByToken(request).getObject();
        // 用户存在且角色为1（教师）
        if (teacherUser == null || teacherUser.getRole() != 1){
            return RespBean.error("删除失败！只有教师才能删除课程！");
        }
        try{
            // course表删除一行信息，由于外键设置了级联操作，删除会一并删除其他表的数据
            courseMapper.deleteById(courseid);

        }catch (Exception e){
            return RespBean.error("删除失败！" + e);
        }
        return RespBean.success("删除成功！");
    }

    /**
     * 更新课程表
     * @param course
     * @param request
     * @return
     */
    @Override
    public RespBean updateCourseByTeacher(Course course, HttpServletRequest request) {
        // 通过传过来的token获取用户信息
        User teacherUser = (User) userService.getUserInfoByToken(request).getObject();
        // 用户存在且角色为1（教师）
        if (teacherUser == null || teacherUser.getRole() != 1){
            return RespBean.error("更新失败！只有教师才能更新课程！");
        }
        try{
            // course表更新一行信息，由于外键设置了级联操作，更新会一并更新其他表的数据
            courseMapper.updateById(course);

        }catch (Exception e){
            return RespBean.error("更新失败！" + e);
        }
        return RespBean.success("更新成功！");
    }


}
