package com.lq.daoyun.service;

import com.lq.daoyun.pojo.DicData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lq.daoyun.pojo.RespBean;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-08
 */
public interface IDicDataService extends IService<DicData> {

    /**
     * 获取所有数据字典
     * @return
     */
    List<DicData> getAllDicData();

    /**
     * 添加一个数据字典
     * @param dicData
     * @return
     */
    RespBean addDicData(DicData dicData);
}
