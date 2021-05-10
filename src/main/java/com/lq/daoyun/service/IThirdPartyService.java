package com.lq.daoyun.service;

import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.ThirdParty;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-24
 */
public interface IThirdPartyService extends IService<ThirdParty> {

    RespBean addThirdPartyByID(Integer  thirdID);

    RespBean getAllThirdParty();

    RespBean addThirdParty(ThirdParty thirdParty);

    ThirdParty selectByThirdPartyID(Integer thirdID);

    RespBean getByThirdPartyID(Integer thirdID);

    RespBean updateThirdPartyById(ThirdParty thirdParty);
}
