package com.lq.daoyun.service;

import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.StudentCourse;
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
public interface IStudentCourseService extends IService<StudentCourse> {


    RespBean getCourseListByStudentID(HttpServletRequest request);

    RespBean getCourseListByStudentID(Integer id);

    RespBean getAllStudentCourse();

    RespBean addStudentCourse(StudentCourse studentCourse);

    RespBean updateStudentCourseById(StudentCourse studentCourse);
}
