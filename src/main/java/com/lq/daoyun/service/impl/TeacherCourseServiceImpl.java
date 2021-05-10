package com.lq.daoyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lq.daoyun.mapper.CourseMapper;
import com.lq.daoyun.mapper.TeacherCourseMapper;
import com.lq.daoyun.pojo.Course;
import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.TeacherCourse;
import com.lq.daoyun.pojo.User;
import com.lq.daoyun.service.ITeacherCourseService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TeacherCourseServiceImpl extends ServiceImpl<TeacherCourseMapper, TeacherCourse> implements ITeacherCourseService {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TeacherCourseMapper teacherCourseMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ITeacherCourseService iTeacherCourseService;
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
                List<Course> courseList = courseMapper.getCourseListByTeacherID(userInfoByToken.getId());
                if (courseList.size() == 0) {
                    return RespBean.success("查询成功！尚未创建课程!", courseList);
                } else {
                    return RespBean.success("查询成功！总共创建了" + courseList.size() + "个课程", courseList);
                }

            } catch (Exception e) {
                return RespBean.error("查询失败！", e);
            }
        }

    }

    /**
     * 根据ID查询对应教师ID开设的课程
     * @param id
     * @return
     */
    @Override
    public RespBean getCourseListByTeacherID(Integer id) {
        int teacherRole = userService.getById(id).getRole();
        if (teacherRole != 1) return RespBean.error("查询失败！ID为" + id.toString() + "的用户角色不是教师！");
        try {
            List<Course> courseList = courseMapper.getCourseListByTeacherID(id);
            if (courseList.size() == 0) {
                return RespBean.success("查询成功！尚未创建课程!", courseList);
            } else {
                return RespBean.success("查询成功！总共创建了" + courseList.size() + "个课程", courseList);
            }

        } catch (Exception e) {
            return RespBean.error("查询失败！", e);
        }

    }

    @Override
    public RespBean getAllTeacherCourse() {
        List<TeacherCourse> teacherCourseList = teacherCourseMapper.selectList(null);
        return RespBean.success("查询成功！", teacherCourseList);
    }

    @Override
    public RespBean addTeacherCourse(TeacherCourse teacherCourse) {
        User teacherUser = userService.getById(teacherCourse.getTeacherId());
        if (teacherUser == null) return RespBean.error("插入数据失败！该教师不存在！");
        QueryWrapper<TeacherCourse> teacherCourseQueryWrapper = Wrappers.query();
        Map<String, Object> map = new HashMap<>();
        map.put("teacher_id", teacherCourse.getTeacherId());
        map.put("course_id", teacherCourse.getCourseId());
        TeacherCourse teacherCourseIsExist = teacherCourseMapper.selectOne(teacherCourseQueryWrapper.allEq(map));
        if (teacherCourseIsExist != null){
            return RespBean.error("新增数据失败！原因：已有该课程！");
        }
        int insert = teacherCourseMapper.insert(teacherCourse);
        if (insert > 0){
            return RespBean.success("新增数据成功!", teacherCourse);
        }else {
            return RespBean.error("新增数据失败！");
        }
    }

    @Override
    public RespBean updateTTeacherCourseById(TeacherCourse teacherCourse) {
        if (iTeacherCourseService.updateById(teacherCourse)){
            return RespBean.success("更新成功！", teacherCourse);
        } else {
            return RespBean.error("更新失败！");
        }
    }
}
