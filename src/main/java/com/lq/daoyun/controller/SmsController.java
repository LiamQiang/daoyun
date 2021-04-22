package com.lq.daoyun.controller;

import com.lq.daoyun.pojo.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 短信服务,由于主要是调用bmob第三方api，因此只写Controller层
 * @author: LiamQ
 **/
@Api(tags = "短信相关接口")
@RestController
@RequestMapping("/sms")
public class SmsController {

    public static final String APPLICATION_ID= "8e7313222f5c46526130dae6a4aeed5e";
    public static final String REST_API_KEY = "a7acfb9aa4715f3ed7c3b48ac92ee238";
    public static final String REQUEST_URL = "https://api2.bmob.cn/1/requestSmsCode";
    public static final String SMS_TEMPLATE_NAME = "LQ";
    public static final String VERIFY_URL = "https://api2.bmob.cn/1/verifySmsCode";

    /**
     * 发送短信，成功时body中的"smsId": smsId（可用于后面使用查询短信状态接口来查询该条短信是否发送成功）
     */
    @ApiOperation(value = "发送短信")
    @GetMapping("/{phonenumber}")
    public RespBean sendSms(@PathVariable("phonenumber") String phonenumber){

        RestTemplate restTemplate = new RestTemplate();
        // 设置请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("X-Bmob-Application-Id", APPLICATION_ID);
        httpHeaders.add("X-Bmob-REST-API-Key", REST_API_KEY);
        // 设置请求体
        //MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("mobilePhoneNumber", phonenumber );
        // requestBody.put("template", SMS_TEMPLATE_NAME);
        HttpEntity httpEntity = new HttpEntity(requestBody, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(REQUEST_URL, httpEntity, String.class);
        } catch (HttpClientErrorException e){
            return RespBean.error("短信发送失败！" + e);
        }

        return  RespBean.success("短信发送成功！", responseEntity.getBody());
    }

    /**
     * 发送短信，成功时body中的"smsId": smsId（可用于后面使用查询短信状态接口来查询该条短信是否发送成功）
     */
    @ApiOperation(value = "验证短信验证码")
    @GetMapping("/{phonenumber}/{smsCode}")
    public static RespBean verifySmsCode(@PathVariable("phonenumber") String phonenumber, @PathVariable("smsCode") String smsCode){
        RestTemplate restTemplate = new RestTemplate();
        // 设置请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("X-Bmob-Application-Id", APPLICATION_ID);
        httpHeaders.add("X-Bmob-REST-API-Key", REST_API_KEY);
        // 设置请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("mobilePhoneNumber", phonenumber);
        HttpEntity httpEntity = new HttpEntity(requestBody, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            String verifyUrl = VERIFY_URL + "/"+ smsCode;
            responseEntity = restTemplate.postForEntity(verifyUrl, httpEntity, String.class);
        } catch (HttpClientErrorException e){
            return RespBean.error("短信验证失败！" + e.getResponseBodyAsString());
        }

        return  RespBean.success("短信验证成功！", responseEntity.getBody());
    }
}
