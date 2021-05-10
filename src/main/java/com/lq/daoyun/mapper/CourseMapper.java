package com.lq.daoyun.mapper;

import com.lq.daoyun.pojo.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-22
 */
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 根据教师ID查询课程
     * @param teacherid
     * @return
     */
    List<Course> getCourseListByTeacherID(Integer teacherid);

    /**
     * 根据学生ID查询课程
     * @param teacherid
     * @return
     */
    List<Course> getCourseListByStudentID(Integer studentid);

}
