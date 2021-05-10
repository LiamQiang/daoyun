package com.lq.daoyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lq.daoyun.pojo.DicData;
import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.ThirdParty;
import com.lq.daoyun.mapper.ThirdPartyMapper;
import com.lq.daoyun.service.IThirdPartyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-24
 */
@Service
public class ThirdPartyServiceImpl extends ServiceImpl<ThirdPartyMapper, ThirdParty> implements IThirdPartyService {

    @Autowired
    private ThirdPartyMapper thirdPartyMapper;

    @Autowired
    private IThirdPartyService iThirdPartyService;
    @Override
    public RespBean addThirdPartyByID(Integer thirdID) {
        QueryWrapper<ThirdParty> thirdPartyWrappers = Wrappers.query();
        thirdPartyWrappers.eq("third_party_id", thirdID);
        ThirdParty thirdPartyTmp= thirdPartyMapper.selectOne(thirdPartyWrappers);
        if (thirdPartyTmp == null){
            ThirdParty thirdParty = new ThirdParty();
            thirdParty.setThirdPartyId(thirdID);
            try {
                thirdPartyMapper.insert(thirdParty);
                return RespBean.success("插入数据库third_party成功！", thirdParty);
            } catch (Exception e){
                return RespBean.error("插入数据库third_party失败！", e);
            }
        } else {
            return RespBean.error("插入数据库third_party失败！");
        }

    }

    @Override
    public RespBean getAllThirdParty() {
        try {
            List<ThirdParty> thirdPartyList = thirdPartyMapper.selectList(null);
            return  RespBean.success("查询成功！", thirdPartyList);
        } catch (Exception e){
            return RespBean.error("查询失败！", e);
        }
    }

    @Override
    public RespBean addThirdParty(ThirdParty thirdParty) {
        QueryWrapper<ThirdParty> thirdPartyWrappers = Wrappers.query();
        thirdPartyWrappers.eq("third_party_id", thirdParty.getThirdPartyId());
        ThirdParty thirdPartyIsExist = thirdPartyMapper.selectOne(thirdPartyWrappers);
        if (thirdPartyIsExist != null){
            return RespBean.error("新增数据失败！原因：已有该系统参数！");
        }
        int insert = thirdPartyMapper.insert(thirdParty);
        if (insert > 0){
            return RespBean.success("新增数据成功!", thirdParty);
        }else {
            return RespBean.error("新增数据失败！");
        }
    }

    @Override
    public ThirdParty selectByThirdPartyID(Integer thirdID) {
        QueryWrapper<ThirdParty> thirdPartyWrappers = Wrappers.query();
        thirdPartyWrappers.eq("third_party_id", thirdID);
        ThirdParty thirdPartyTmp= thirdPartyMapper.selectOne(thirdPartyWrappers);
        if (thirdPartyTmp != null){
            return thirdPartyTmp;
        } else{
            return  null;
        }
    }

    @Override
    public RespBean getByThirdPartyID(Integer thirdID) {
        QueryWrapper<ThirdParty> thirdPartyWrappers = Wrappers.query();
        thirdPartyWrappers.eq("third_party_id", thirdID);
        ThirdParty thirdPartyTmp= thirdPartyMapper.selectOne(thirdPartyWrappers);
        if (thirdPartyTmp != null){
            return RespBean.success("查询成功！", thirdPartyTmp);
        } else{
            return RespBean.error("无第三方ID为" + thirdID + "的记录!",null);
        }
    }

    @Override
    public RespBean updateThirdPartyById(ThirdParty thirdParty) {

        if (iThirdPartyService.updateById(thirdParty) ){
            return RespBean.success("更新成功！", thirdParty);
        } else {
            return RespBean.error("更新失败！");
        }

    }
}
