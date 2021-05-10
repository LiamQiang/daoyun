package com.lq.daoyun.controller;

import com.lq.daoyun.DTO.AccessTokenDTO;
import com.lq.daoyun.config.GithubParams;
import com.lq.daoyun.config.GithubProvider;
import com.lq.daoyun.pojo.GithubUser;
import com.lq.daoyun.pojo.RespBean;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: LiamQ
 **/
//@Api(tags = "(new)GitHub第三方登录接口")
//@RestController
@RequestMapping("/github")
public class AuthoriseController {
    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private GithubParams githubParams;

    @GetMapping("/callback")
    public RespBean callback(@RequestParam("code") String code,
                             @RequestParam("state") String state){
        AccessTokenDTO accessTokenDto = new AccessTokenDTO();
        accessTokenDto.setClient_id(githubParams.getClient_id());
        accessTokenDto.setClient_secret(githubParams.getClient_secret());
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(githubParams.getRedirect_uri());
        accessTokenDto.setState(state);
        //获取access_token
        String access_token = githubProvider.getAccessToken(accessTokenDto);
        //根据accessToken获取用户信息
        GithubUser githubUser = githubProvider.getGithubUser(access_token);
        System.out.println(githubUser.getName());

        return RespBean.success("github获取成功！",githubUser);
    }
}
