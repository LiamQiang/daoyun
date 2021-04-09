package com.lq.daoyun.service.impl;

import com.lq.daoyun.pojo.Task;
import com.lq.daoyun.mapper.TaskMapper;
import com.lq.daoyun.service.ITaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-08
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

}
