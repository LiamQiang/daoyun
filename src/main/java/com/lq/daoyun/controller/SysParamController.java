package com.lq.daoyun.controller;


import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.SysParam;
import com.lq.daoyun.service.ISysParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-13
 */
@Api(tags = "系统参数相关接口")
@RestController
@RequestMapping("/sys-param")
public class SysParamController {

    @Autowired
    private ISysParamService iSysParamService;

    @ApiOperation(value = "获取所有系统参数")
    @GetMapping()
    public List<SysParam> getAllSysParams(){
        return iSysParamService.getAllSysParams();
    }

    @ApiOperation(value = "添加一个系统参数")
    @PostMapping()
    public RespBean addSysParam(@RequestBody SysParam sysParam){
        return iSysParamService.addSysParam(sysParam);
    }

    @ApiOperation(value = "修改一个系统参数")
    @PutMapping()
    public RespBean updateSysParam(@RequestBody SysParam sysParam){
        if (iSysParamService.updateById(sysParam)){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @ApiOperation(value = "删除系统参数")
    @DeleteMapping("/{id}")
    public RespBean deleteEmp(@PathVariable Integer id) {
        if (iSysParamService.removeById(id)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }



}
