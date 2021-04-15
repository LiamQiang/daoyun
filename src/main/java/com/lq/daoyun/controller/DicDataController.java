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
    public List<DicData> getAllDicData(){
        return iDicDataService.getAllDicData();
    }

    @ApiOperation(value = "添加一个数据字典", notes = "id不需要传")
    @PostMapping()
    public RespBean addDicData(@RequestBody DicData dicData ){
        return iDicDataService.addDicData(dicData);
    }

    @ApiOperation(value = "修改一个数据字典")
    @PutMapping()
    public RespBean updateDicData(@RequestBody DicData dicData){
        if (iDicDataService.updateById(dicData)){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @ApiOperation(value = "删除数据字典")
    @DeleteMapping("/{id}")
    public RespBean deleteDicData(@PathVariable Integer id) {
        if (iDicDataService.removeById(id)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

}
