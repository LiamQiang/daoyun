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
    public RespBean getAllSysParams(){
        return iSysParamService.getAllSysParams();
    }

    @ApiOperation(value = "添加一个系统参数",notes = "不需要传入id或者id设置为0，数据库会自动生成主键id")
    @PostMapping()
    public RespBean addSysParam(@RequestBody SysParam sysParam){
        return iSysParamService.addSysParam(sysParam);
    }

    @ApiOperation(value = "根据ID修改一个系统参数", notes = "需要传入正确的id以完成数据库的修改")
    @PutMapping()
    public RespBean updateSysParam(@RequestBody SysParam sysParam){
        if (iSysParamService.updateById(sysParam)){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @ApiOperation(value = "根据ID删除系统参数", notes = "传入正确的id以完成数据库的删除")
    @DeleteMapping("/{id}")
    public RespBean deleteEmp(@PathVariable Integer id) {
        if (iSysParamService.removeById(id)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }



}
