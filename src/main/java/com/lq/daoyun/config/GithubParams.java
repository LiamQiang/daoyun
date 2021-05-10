package com.lq.daoyun.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: LiamQ
 **/
@Component
@Data
@ConfigurationProperties(prefix = "github")
public class GithubParams {
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String token_uri;
    private String user_uri;
}
