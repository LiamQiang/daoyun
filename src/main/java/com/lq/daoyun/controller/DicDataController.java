package com.lq.daoyun.controller;


import com.lq.daoyun.pojo.DicData;
import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.service.IDicDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-08
 */
@Api(tags = "数据字典相关接口")
@RestController
@RequestMapping("/dic-data")
public class DicDataController {

    @Autowired
    private IDicDataService iDicDataService;


    @ApiOperation(value = "获取所有数据字典")
    @GetMapping()
    public RespBean getAllDicData(){
        return iDicDataService.getAllDicData();
    }

    @ApiOperation(value = "添加一个数据字典", notes = "不需要传入id或者id设置为0，数据库会自动生成主键id")
    @PostMapping()
    public RespBean addDicData(@RequestBody DicData dicData ){
        return iDicDataService.addDicData(dicData);
    }

    @ApiOperation(value = "根据ID修改一个数据字典",notes = "需要传入正确的id以完成数据库的修改")
    @PutMapping()
    public RespBean updateDicData(@RequestBody DicData dicData){
        if (iDicDataService.updateById(dicData)){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @ApiOperation(value = "根据ID删除数据字典",notes = "需要传入正确的id以完成数据库的修改")
    @DeleteMapping("/{id}")
    public RespBean deleteDicData(@PathVariable Integer id) {
        if (iDicDataService.removeById(id)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

}
