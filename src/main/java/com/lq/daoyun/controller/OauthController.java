package com.lq.daoyun.controller;

import com.alibaba.fastjson.JSONObject;
import com.lq.daoyun.config.security.JwtTokenUtil;
import com.lq.daoyun.pojo.RespBean;
import com.lq.daoyun.pojo.ThirdParty;
import com.lq.daoyun.pojo.User;
import com.lq.daoyun.service.IThirdPartyService;
import com.lq.daoyun.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: GitHub第三方登录
 * @author: LiamQ
 **/
@Api(tags = "GitHub第三方登录相关接口")
@RestController
@RequestMapping("/oauth")
public class OauthController {
    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String USER_URL = "https://api.github.com/user";

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IThirdPartyService iThirdPartyService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${oauth.github.app-id}")
    private String appId;

    @Value("${oauth.github.app-secret}")
    private String appSecret;

//    @ApiOperation(value = "github第三方登录接口", notes = " ")
//    @GetMapping()
//    public RespBean githubURL(){
//        RestTemplate restTemplate = new RestTemplate();
//        String sendUrl = "https://github.com/login/oauth/authorize?client_id="
//                + appId + "&" + "redirect_uri=" + "http://localhost:8081/oauth/redirect&state=1";
//        Object  objectResponseEntity = restTemplate.getForEntity(sendUrl, null);
//        return RespBean.success("successful!", objectResponseEntity);
//    }

    @ApiOperation(value = "github第三方回调接口", notes = "登录成功会返回token以及loginMode，可以通过loginmode是否等于1判断是否第三方登录")
    @PostMapping("/redirect")
    public RespBean githubLogin(@RequestParam("accessToken") String accessToken){

//        /**
//         * 使用code获取accessToken
//         */
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
//        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        /**
         * 通过accessToken获取用户信息
         */
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "token " + accessToken);
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(USER_URL, HttpMethod.GET,
                new HttpEntity<Void>(null, httpHeaders),
                JSONObject.class);
        JSONObject jsonObject = responseEntity.getBody();
        Integer thirdID = (Integer) jsonObject.get("id");
        ThirdParty thirdPartyIsExist = iThirdPartyService.selectByThirdPartyID(thirdID);
        // 第三方表中没有第三方的ID，加入thirdparty表格中,直接到登录界面并弹窗让其绑定手机号,绑定完才返回用户token
        if (thirdPartyIsExist == null) {
            RespBean thirdPartyrespBean = iThirdPartyService.addThirdPartyByID(thirdID);
            ThirdParty thirdParty = (ThirdParty) thirdPartyrespBean.getObject();
            if (thirdPartyrespBean.getCode() == 200) {
                Map<String, String> map = new HashMap<>();
                map.put("loginMode", "1");
                map.put("thirdPartyId", thirdID.toString());
                map.put("id", thirdParty.getId().toString());
                // 前端根据loginMode == 1判断是否弹窗让用户绑定,没绑定的话不给token
                return RespBean.success("第三方登录成功！", map);
            } else {
                return RespBean.success("第三方登录失败！");
            }
            // 有第三方ID，则必定已经绑定了一个用户ID，通过用户ID获取用户从而提供token
        } else {
            // 根据第三方ID获取手机号
            ThirdParty thirdParty = iThirdPartyService.selectByThirdPartyID(thirdID);
            // 根据第三方ID获取用户ID
            Integer userID = thirdParty.getUserid();
            User user = (User) iUserService.getById(userID);
            if (user == null) {
                return RespBean.error("第三方登录失败！");
            } else {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                String token = jwtTokenUtil.generateToken(user);
                Map<String, String> tokenMap = new HashMap<>();
                tokenMap.put("token", token);
                tokenMap.put("tokenHead", tokenHead);
                return RespBean.success("第三方登录成功！", tokenMap);
            }
        }
    }

//    @ApiOperation(value = "github第三方回调接口", notes = " ")
//    @GetMapping("/redirect")
//    public RespBean githubLogin(@RequestParam("code") String code){
//
//        /**
//         * 使用code获取accessToken
//         */
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
//        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
//
//        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
//        requestBody.add("client_id", this.appId);
//        requestBody.add("client_secret", this.appSecret);
//        requestBody.add("code", code);
//
//        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(requestBody, httpHeaders);
//
//        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(ACCESS_TOKEN_URL, httpEntity, JSONObject.class);
//        JSONObject jsonObject = responseEntity.getBody();
//        String accessToken = jsonObject.getString("access_token");
//        String scope = jsonObject.getString("scope");
//        String tokenType = jsonObject.getString("token_type");
//        System.out.println(jsonObject);
//
//        /**
//         * 通过accessToken获取用户信息
//         */
//        httpHeaders = new HttpHeaders();
//        httpHeaders.set(HttpHeaders.AUTHORIZATION, "token " + accessToken);
//        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
//
//        responseEntity = restTemplate.exchange(USER_URL, HttpMethod.GET, new HttpEntity<Void>(null, httpHeaders),
//                JSONObject.class);
//        jsonObject = responseEntity.getBody();
//        try {
//            Integer thirdID = (Integer) jsonObject.get("id");
//            // 第三方信息与用户id对应，没有的话加入third_party表，有的话直接用户id调用快速登录接口
//            RespBean thirdPartyrespBean = iThirdPartyService.addThirdID(thirdID);
//            User thirdPartyUser = new User();
//            thirdPartyUser.setId(thirdID);
//            thirdPartyUser.setRole(0);
//            thirdPartyUser.setUsername(thirdID.toString());
//            boolean isSave = iUserService.save(thirdPartyUser);
//            if(thirdPartyrespBean.getCode() == 200 && isSave){
//                User user = (User) iUserService.getByUsername(thirdID.toString()).getObject();
//                if (user == null){
//                    return RespBean.error("第三方登录失败！");
//                }else {
//                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                    String token = jwtTokenUtil.generateToken(user);
//                    Map<String, String> tokenMap = new HashMap<>();
//                    tokenMap.put("token", token);
//                    tokenMap.put("tokenHead", tokenHead);
//                    return RespBean.success("第三方登录成功！", tokenMap);
//                }
//            } else {
//                return RespBean.error("第三方登录失败！");
//            }
//
//        } catch (Exception e){
//            return RespBean.error("第三方登录失败！", e);
//        }
//
//    }


}
