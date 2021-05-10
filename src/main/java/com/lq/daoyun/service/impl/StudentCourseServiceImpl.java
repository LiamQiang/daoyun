package com.lq.daoyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lq.daoyun.mapper.CourseMapper;
import com.lq.daoyun.mapper.StudentCourseMapper;
import com.lq.daoyun.pojo.*;
import com.lq.daoyun.service.IStudentCourseService;
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
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse> implements IStudentCourseService {


    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private IStudentCourseService iStudentCourseService;

    /**
     * 从请求头token里面获取学生ID，以此来查询班课
     * @param request
     * @return
     */
    @Override
    public RespBean getCourseListByStudentID(HttpServletRequest request) {
        User userInfoByToken = (User)userService.getUserInfoByToken(request).getObject();
        if (userInfoByToken == null || userInfoByToken.getRole() != 0){
            return RespBean.error("查询失败！用户不是学生！");
        }else{
            try {
                //List<TeacherCourse> teacherCourseList = teacherCourseMapper.selectList(teacherCourseQueryWrapper.eq("teacher_id",userInfoByToken.getId()));
                List<Course> courseList = courseMapper.getCourseListByStudentID(userInfoByToken.getId());
                if (courseList.size() == 0) {
                    return RespBean.success("查询成功！尚未加入班课！", courseList);
                } else {
                    return RespBean.success("查询成功！总共加入"+ courseList.size() + "个班课！", courseList);
                }
            }catch (Exception e){
                return RespBean.error("查询失败！", e);
            }
        }
    }

    /**
     * 根据ID查询对应学生ID加入课程
     * @param id
     * @return
     */
    @Override
    public RespBean getCourseListByStudentID(Integer id) {
        int studentRole = userService.getById(id).getRole();
        if (studentRole != 0) return RespBean.error("查询失败！ID为" + id.toString() + "的用户角色不是学生！");
        try {
            List<Course> courseList = courseMapper.getCourseListByStudentID(id);
            if (courseList.size() == 0) {
                return RespBean.success("查询成功！尚未加入班课！", courseList);
            } else {
                return RespBean.success("查询成功！总共加入"+ courseList.size() + "个班课！", courseList);
            }
        } catch (Exception e) {
            return RespBean.error("查询失败！", e);
        }
    }

    @Override
    public RespBean getAllStudentCourse() {
        try {
            List<StudentCourse> studentCourseList = studentCourseMapper.selectList(null);
            int courseCount = studentCourseList.size();
            return RespBean.success("查询成功！总共" + courseCount + "条数据!" , studentCourseList);
        } catch (Exception e) {
            return RespBean.success("查询失败！", e);
        }
    }

    @Override
    public RespBean addStudentCourse(StudentCourse studentCourse) {
        User studentUser = userService.getById(studentCourse.getStudentId());
        Course course = courseService.getById(studentCourse.getCourseId());
        if (studentUser == null || course == null) return RespBean.error("插入数据失败！学生或者课程不存在！");
        QueryWrapper<StudentCourse> studentCourseQueryWrapper = Wrappers.query();
        Map<String, Object> map = new HashMap<>();
        map.put("student_id", studentCourse.getStudentId());
        map.put("course_id", studentCourse.getCourseId());
        StudentCourse studentCourseisExist = studentCourseMapper.selectOne(studentCourseQueryWrapper.allEq(map));
        if (studentCourseisExist != null){
            return RespBean.error("新增数据失败！原因：数据库中已有该数据！");
        }
        int insert = studentCourseMapper.insert(studentCourse);
        if (insert > 0){
            return RespBean.success("新增数据成功!", studentCourse);
        }else {
            return RespBean.error("新增数据失败！");
        }
    }

    @Override
    public RespBean updateStudentCourseById(StudentCourse studentCourse) {

        if (iStudentCourseService.updateById(studentCourse)){
            return RespBean.success("更新成功！", studentCourse);
        } else {
            return RespBean.error("更新失败！");
        }
    }
}
