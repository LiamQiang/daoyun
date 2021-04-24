package com.lq.daoyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.lq.daoyun.mapper.CourseMapper;
import com.lq.daoyun.pojo.Course;
import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.TeacherCourse;
import com.lq.daoyun.mapper.TeacherCourseMapper;
import com.lq.daoyun.pojo.User;
import com.lq.daoyun.service.ITeacherCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-22
 */
@Service
public class TeacherCourseServiceImpl extends ServiceImpl<TeacherCourseMapper, TeacherCourse> implements ITeacherCourseService {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TeacherCourseMapper teacherCourseMapper;

    @Autowired
    private CourseMapper courseMapper;
    /**
     * 从请求头token里面获取教师ID，以此来查询班课
     * @param request
     * @return
     */
    @Override
    public RespBean getCourseListByTeacherID(HttpServletRequest request) {
        User userInfoByToken = (User)userService.getUserInfoByToken(request).getObject();
        if (userInfoByToken == null || userInfoByToken.getRole() !=1){
            return RespBean.error("查询失败！用户不是教师！");
        }else{
            QueryWrapper<TeacherCourse> teacherCourseQueryWrapper = Wrappers.query();
            try {
                //List<TeacherCourse> teacherCourseList = teacherCourseMapper.selectList(teacherCourseQueryWrapper.eq("teacher_id",userInfoByToken.getId()));
                List<Course> courseList = courseMapper.getCourseListByTeacherID(userInfoByToken.getId());
                return RespBean.success("查询成功！", courseList);
            }catch (Exception e){
                return RespBean.error("查询失败！", e);
            }
        }

    }
}
