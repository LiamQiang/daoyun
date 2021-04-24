package com.lq.daoyun.service;

import com.lq.daoyun.pojo.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lq.daoyun.pojo.RespBean;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-22
 */
public interface ICourseService extends IService<Course> {

    /**
     * 获取所有课表信息
     * @return
     */
    RespBean getAllCourse(HttpServletRequest request);

    /**
     * 教师添加课程
     * @param course
     * @param request
     * @return
     */
    RespBean addCourseByTeacher(Course course, HttpServletRequest request);

    /**
     * 根据id删除课程
     * @param courseid
     * @param request
     * @return
     */
    RespBean deleteCourseByTeacher(Integer courseid, HttpServletRequest request);

    /**
     * 更新课程
     * @param course
     * @param request
     * @return
     */
    RespBean updateCourseByTeacher(Course course, HttpServletRequest request);
}
