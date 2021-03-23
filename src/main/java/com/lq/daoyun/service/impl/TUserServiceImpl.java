package com.lq.daoyun.service.impl;

import com.lq.daoyun.Entity.TUser;
import com.lq.daoyun.mapper.TUserMapper;
import com.lq.daoyun.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiamQ
 * @since 2021-03-22
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

}
