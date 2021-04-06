package com.lq.daoyun.controller;


import com.lq.daoyun.entity.UserTab;
import com.lq.daoyun.service.IUserTabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-05
 */
@RestController
@RequestMapping("/user-tab")
public class UserTabController {
    @Autowired
    IUserTabService iUserTabService;
    @GetMapping("/{id}")
    public Object test(@PathVariable("id") Long id){
        return iUserTabService.getById(id);
    }
}
