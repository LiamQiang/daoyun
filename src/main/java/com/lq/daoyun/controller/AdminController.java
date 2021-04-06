package com.lq.daoyun.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiamQ
 * @since 2021-04-05
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public Map<String, Object> testAdmin(){
        Map<String, Object> result = new HashMap<>();
        result.put("message", "getMapping/admin");
        result.put("message2", "test");
        return result;
    }
}
