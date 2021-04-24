package com.lq.daoyun.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.lq.daoyun.pojo.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @description: GitHub第三方登录
 * @author: LiamQ
 **/
@Api(tags = "GitHub第三方登录接口")
@RestController
@RequestMapping("/github")
public class OauthController {
    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String USER_URL = "https://api.github.com/user";

    @Value("${oauth.github.app-id}")
    private String appId;

    @Value("${oauth.github.app-secret}")
    private String appSecret;

    @ApiOperation(value = "github第三方登录", notes = " ")
    @GetMapping("/githubAuth/{code}")
    public RespBean githubLogin(@RequestParam("code") String code){

        /**
         * 使用code获取accessToken
         */
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", this.appId);
        requestBody.add("client_secret", this.appSecret);
        requestBody.add("code", code);

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(requestBody, httpHeaders);

        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(ACCESS_TOKEN_URL, httpEntity, JSONObject.class);
        JSONObject jsonObject = responseEntity.getBody();
        String accessToken = jsonObject.getString("access_token");
        String scope = jsonObject.getString("scope");
        String tokenType = jsonObject.getString("token_type");
        System.out.println(jsonObject);

        /**
         * 通过accessToken获取用户信息
         */
        httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "token " + accessToken);
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        responseEntity = restTemplate.exchange(USER_URL, HttpMethod.GET, new HttpEntity<Void>(null, httpHeaders),
                JSONObject.class);
        jsonObject = responseEntity.getBody();
        return RespBean.success("gihub用户信息获取成功!",jsonObject);
        /**
         * 接下来写第三方信息与用户id对应，没有的话加入third_party表，有的话直接用户id调用快速登录接口
         */
    }

}
