package com.lq.daoyun.controller;


import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.SignIn;
import com.lq.daoyun.pojo.TeacherCourse;
import com.lq.daoyun.service.ISignInService;
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
@Api(tags = "签到表相关接口")
@RestController
@RequestMapping("/sign-in")
public class SignInController {
    @Autowired
    private ISignInService iSignInService;

    @ApiOperation(value = "教师用户查询签到表，返回课程列表", notes = "请求体不需要传数据，后台自动根据请求头的token获取教师ID进行查询")
    @GetMapping("/info")
    public RespBean getSignInListByTeacherID(HttpServletRequest request){
        return iSignInService.getSignInListByTeacherID(request);
    }

    @ApiOperation(value = "根据教师ID查询教师开启的签到表，返回签到表列表", notes = "需要传入教师ID")
    @GetMapping("/info/{id}")
    public RespBean getSignInListByTeacherID(@PathVariable Integer id){
        return iSignInService.getSignInListByTeacherID(id);
    }

    @ApiOperation(value = "获取签到表的所有信息")
    @GetMapping()
    public RespBean getAllSignIn(){
        return iSignInService.getAllSignIn();
    }


    @ApiOperation(value = "添加一条信息(教师发起签到)",notes = "不需要传入id或者id设置为0，数据库会自动生成主键id")
    @PostMapping()
    public RespBean addSignIn(@RequestBody SignIn signIn){
        return iSignInService.addSignIn(signIn);
    }

    @ApiOperation(value = "根据ID修改一条信息(教师停止签到)", notes = "需要传入正确的id以完成数据库的修改")
    @PutMapping()
    public RespBean updateSignInById(@RequestBody SignIn signIn){
        return iSignInService.updateSignInById(signIn);
    }

    @ApiOperation(value = "根据ID删除一条信息", notes = "传入正确的id以完成数据库的删除")
    @DeleteMapping("/{id}")
    public RespBean deleteSignIn(@PathVariable Integer id) {
        try {
            boolean success = iSignInService.removeById(id);
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
