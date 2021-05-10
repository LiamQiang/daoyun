package com.lq.daoyun.controller;


import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.SysParam;
import com.lq.daoyun.pojo.ThirdParty;
import com.lq.daoyun.service.ISysParamService;
import com.lq.daoyun.service.IThirdPartyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-24
 */
@Api(tags = "第三方与用户关联表相关接口")
@RestController
@RequestMapping("/third-party")
public class ThirdPartyController {

    @Autowired
    private IThirdPartyService iThirdPartyService;

    @ApiOperation(value = "获取第三方ID与用户ID关联表的所有信息")
    @GetMapping()
    public RespBean getAllThirdParty(){
        return iThirdPartyService.getAllThirdParty();
    }

    @ApiOperation(value = "根据第三方ID获取关联表的一条信息")
    @GetMapping("/{thirdID}")
    public RespBean getByThirdID(@PathVariable Integer thirdID){
        return iThirdPartyService.getByThirdPartyID(thirdID);
    }

    @ApiOperation(value = "添加一条信息",notes = "不需要传入id或者id设置为0，数据库会自动生成主键id")
    @PostMapping()
    public RespBean addThirdParty(@RequestBody ThirdParty thirdParty){
        return iThirdPartyService.addThirdParty(thirdParty);
    }

    @ApiOperation(value = "根据ID修改一条信息", notes = "需要传入正确的id以完成数据库的修改")
    @PutMapping()
    public RespBean updateThirdPartyById(@RequestBody ThirdParty thirdParty){
        return iThirdPartyService.updateThirdPartyById(thirdParty);
    }

    @ApiOperation(value = "根据ID删除一条信息", notes = "传入正确的id以完成数据库的删除")
    @DeleteMapping("/{id}")
    public RespBean deleteById(@PathVariable Integer id) {
        if (iThirdPartyService.removeById(id)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
