package com.lq.daoyun.config.security;


import com.lq.daoyun.pojo.Admin;
import com.lq.daoyun.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RestController;

/**
 * Security配置类
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /**
     * 配置放行路径,以下路径将被放行
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/websocket/**",
                "/**.html",
                "/admin/login/**",
                "/logout/**",
                "/css/**",
                "favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/captcha",
                "/hello/**",
                "/users/register/**",
                "/sms/**",
                "/sms/**/**",
                "/teacher/login/**",
                "/student/login/**",
                "/phone/login/**",
                "/oauth/**",
                "/github.com/**",
                "https://github.com/**",
                "/users/quick-register/**",
                "/github/**"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 使用jwt，不需要csrf
       http.csrf()
               .disable()
               // 基于token，不需要session
               .sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .authorizeRequests()
               // 所有请求都要认证
               .anyRequest()
               .authenticated()
               .and()
               // 禁用缓存
               .headers()
               .cacheControl();
       // 添加jwt登录授权过滤器
       http.addFilterBefore(jwtAuthencationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
       // 添加自动以未授权和未登录结果返回
       http.exceptionHandling()
               .accessDeniedHandler(restfulAccessDeniedHandler)
               .authenticationEntryPoint(restAuthorizationEntryPoint);

    }

    @Override
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            Admin admin = adminService.getAdminByUserName(username);
            if (null != admin){
                return admin;
            }
            return null;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthencationTokenFilter jwtAuthencationTokenFilter(){
        return new JwtAuthencationTokenFilter();
    }

}
