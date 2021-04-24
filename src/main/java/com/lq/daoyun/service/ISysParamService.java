package com.lq.daoyun.service;

import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.SysParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-13
 */
public interface ISysParamService extends IService<SysParam> {

    /**
     * 获取所有系统参数
     * @return
     */
    RespBean getAllSysParams();


    /**
     * 添加一个系统参数
     * @param sysParam
     * @return
     */
    RespBean addSysParam(SysParam sysParam);
}
