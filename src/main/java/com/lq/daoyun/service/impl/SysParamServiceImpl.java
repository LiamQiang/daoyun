package com.lq.daoyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.SysParam;
import com.lq.daoyun.mapper.SysParamMapper;
import com.lq.daoyun.service.ISysParamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-13
 */
@Service
public class SysParamServiceImpl extends ServiceImpl<SysParamMapper, SysParam> implements ISysParamService {

    @Autowired
    private SysParamMapper sysParamMapper;
    /**
     * 获取所有系统参数
     * @return
     */
    @Override
    public RespBean getAllSysParams() {
        try{
            List<SysParam> sysParamList = sysParamMapper.selectList(null);
            return RespBean.success("查询成功！", sysParamList);
        }catch(Exception e){
            return RespBean.success("查询失败！"+ e);
        }

    }


    /**
     * 添加一个系统参数
     * @param sysParam
     * @return
     */
    @Override
    public RespBean addSysParam(SysParam sysParam) {
        QueryWrapper<SysParam> sysParamQueryWrapper = Wrappers.query();
        sysParamQueryWrapper.eq("sys_param_name", sysParam.getSysParamName());
        SysParam sysParamIsExist = sysParamMapper.selectOne(sysParamQueryWrapper);
        if (sysParamIsExist != null){
            return RespBean.error("新增数据失败！原因：已有该系统参数！");
        }
        int insert = sysParamMapper.insert(sysParam);
        if (insert > 0){
            return RespBean.success("新增数据成功!");
        }else {
            return RespBean.error("新增数据失败！");
        }

    }
}
