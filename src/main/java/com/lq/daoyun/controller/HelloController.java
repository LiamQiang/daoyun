package com.lq.daoyun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 用于测试
 * @author: LiamQ
 **/

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Map<String, Object> sayHelloworld() {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "hello world!");
        result.put("message2", "hello world 2!");
        result.put("message3", "hello world 3!");
        return result;
    }
}
