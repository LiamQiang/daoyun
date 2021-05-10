package com.lq.daoyun.service;

import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.TeacherCourse;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-22
 */
public interface ITeacherCourseService extends IService<TeacherCourse> {

    /**
     * 从请求头token里面获取教师ID，以此来查询班课
     * @param request
     * @return
     */
    RespBean getCourseListByTeacherID(HttpServletRequest request);

    RespBean getCourseListByTeacherID(Integer id);

    RespBean getAllTeacherCourse();

    RespBean addTeacherCourse(TeacherCourse teacherCourse);

    RespBean updateTTeacherCourseById(TeacherCourse teacherCourse);
}
