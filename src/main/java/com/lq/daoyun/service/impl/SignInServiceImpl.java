package com.lq.daoyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lq.daoyun.mapper.CourseMapper;
import com.lq.daoyun.mapper.StudentCourseMapper;
import com.lq.daoyun.pojo.*;
import com.lq.daoyun.mapper.SignInMapper;
import com.lq.daoyun.service.ISignInService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lq.daoyun.service.IStudentCourseService;
import com.lq.daoyun.service.ITeacherCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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
public class SignInServiceImpl extends ServiceImpl<SignInMapper, SignIn> implements ISignInService {


    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private SignInMapper signInMapper;

    @Autowired
    private ITeacherCourseService iTeacherCourseService;

    @Autowired
    private ISignInService iSignInService;

    /**
     * 从请求头token里面获取教师ID，以此来查询教师本人发起的签到
     * @param request
     * @return
     */
    @Override
    public RespBean getSignInListByTeacherID(HttpServletRequest request) {
        User userInfoByToken = (User)userService.getUserInfoByToken(request).getObject();
        if (userInfoByToken == null || userInfoByToken.getRole() != 1){
            return RespBean.error("查询失败！用户不是教师！");
        }else{
            try {
                //List<TeacherCourse> teacherCourseList = teacherCourseMapper.selectList(teacherCourseQueryWrapper.eq("teacher_id",userInfoByToken.getId()));
                List<SignIn> signInList = signInMapper.getSignInListByTeacherID(userInfoByToken.getId());
                if (signInList.size() == 0) {
                    return RespBean.success("查询成功！尚未有发起的签到！", signInList);
                } else {
                    return RespBean.success("查询成功！总共发起了"+ signInList.size() + "个签到！", signInList);
                }
            }catch (Exception e){
                return RespBean.error("查询失败！", e);
            }
        }
    }

    /**
     * 根据ID查询对应教师ID发起的签到
     * @param id
     * @return
     */
    @Override
    public RespBean getSignInListByTeacherID(Integer id) {
        int studentRole = userService.getById(id).getRole();
        if (studentRole != 1) return RespBean.error("查询失败！ID为" + id.toString() + "的用户角色不是教师！");
        try {
            List<SignIn> signInList = signInMapper.getSignInListByTeacherID(id);
            if (signInList.size() == 0) {
                return RespBean.success("查询成功！该教师尚未有发起的签到！", signInList);
            } else {
                return RespBean.success("查询成功！该教师总共发起了"+ signInList.size() + "个签到！", signInList);
            }
        } catch (Exception e) {
            return RespBean.error("查询失败！", e);
        }
    }

    @Override
    public RespBean getAllSignIn() {
        try {
            List<SignIn> signInList = signInMapper.selectList(null);
            int courseCount = signInList.size();
            return RespBean.success("查询成功！总共" + courseCount + "条数据!" , signInList);
        } catch (Exception e) {
            return RespBean.success("查询失败！", e);
        }
    }

    @Override
    public RespBean addSignIn(SignIn signIn) {
        TeacherCourse teacherCourse = iTeacherCourseService.getById(signIn.getTeacherCourseId());
        if (teacherCourse == null ) return RespBean.error("插入数据失败！ID为" + signIn.getTeacherCourseId() + "的课程不存在！");
        QueryWrapper<SignIn> signInQueryWrapper = Wrappers.query();
        Map<String, Object> map = new HashMap<>();
        map.put("teacher_course_id", signIn.getTeacherCourseId());
        SignIn signInisExist = signInMapper.selectOne(signInQueryWrapper.allEq(map));
        if (signInisExist != null){
            return RespBean.error("新增数据失败！原因：数据库中已有该数据！");
        }
        signIn.setStartTime(LocalDateTime.now());
        int insert = signInMapper.insert(signIn);
        if (insert > 0){
            return RespBean.success("新增数据成功!", signIn);
        }else {
            return RespBean.error("新增数据失败！");
        }
    }

    @Override
    public RespBean updateSignInById(SignIn signIn) {
        if (iSignInService.updateById(signIn)){
            return RespBean.success("更新成功！", signIn);
        } else {
            return RespBean.error("更新失败！");
        }
    }
}
