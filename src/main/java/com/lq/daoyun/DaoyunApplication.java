package com.lq.daoyun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// 禁用security的自动配置，否则会跳转到login页面
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan(value = "com.lq.daoyun.mapper")
public class DaoyunApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaoyunApplication.class, args);
    }

}
