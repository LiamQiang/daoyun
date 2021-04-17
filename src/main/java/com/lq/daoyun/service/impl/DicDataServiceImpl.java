package com.lq.daoyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lq.daoyun.pojo.DicData;
import com.lq.daoyun.mapper.DicDataMapper;
import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.service.IDicDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * @since 2021-04-08
 */
@Service
public class DicDataServiceImpl extends ServiceImpl<DicDataMapper, DicData> implements IDicDataService {


    @Autowired
    private DicDataMapper dicDataMapper;
    /**
     * 获取所有数据字典
     * @return
     */
    @Override
    public List<DicData> getAllDicData() {
        List<DicData> listDicData= dicDataMapper.selectList(null);
        return listDicData;
    }

    /**
     * 添加一个数据字典
     * @param dicData
     * @return
     */
    @Override
    public RespBean addDicData(DicData dicData) {
        QueryWrapper<DicData> dicDateQueryWrapper = Wrappers.query();
        Map<String, Object> map = new HashMap<>();
        map.put("dic_type", dicData.getDicType());
        map.put("dic_key", dicData.getDicKey());
        DicData dicDateIsExist = dicDataMapper.selectOne(dicDateQueryWrapper.allEq(map));
        if (dicDateIsExist != null){
            return RespBean.error("新增数据失败！原因：已有该系统参数！");
        }
        if (dicData.getDicValue() == null){
            dicData.setDicValue("/");
        } else {
            dicData.setDicValue(dicData.getDicValue());
        }
        int insert = dicDataMapper.insert(dicData);
        if (insert > 0){
            return RespBean.success("新增数据成功!");
        }else {
            return RespBean.error("新增数据失败！");
        }
    }
}
