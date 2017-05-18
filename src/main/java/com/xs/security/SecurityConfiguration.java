package com.xs.security;

import com.xs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.security.web.access.DefaultWebInvocationPrivilegeEvaluator;

/**
 * Created by xs on 2017-04-25.
 *
 * @EnableWebSecurity: 禁用Boot的默认Security配置，配合@Configuration启用自定义配置（需要扩展WebSecurityConfigurerAdapter）
 * @EnableGlobalMethodSecurity(prePostEnabled = true): 启用Security注解，例如最常用的@PreAuthorize
 * configure(HttpSecurity): Request层面的配置，对应XML Configuration中的<http>元素
 * configure(WebSecurity): Web层面的配置，一般用来配置无需安全检查的路径
 * configure(AuthenticationManagerBuilder): 身份验证配置，用于注入自定义身份验证Bean和密码校验规则
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    //自定义UserDetailsService注入
//    @Autowired
//    private UserService userService;
    @Autowired
    private MyUserDetailService userDetailService;

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;



    //配置全局设置
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //设置UserDetailsService以及密码规则
        auth.userDetailsService(userDetailService);     //.passwordEncoder(bCryptPasswordEncoder());
    }

    //配置匹配用户时密码规则
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }


    //排除/hello路径拦截
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.securityInterceptor(myFilterSecurityInterceptor);
    //    web.privilegeEvaluator(new DefaultWebInvocationPrivilegeEvaluator());
        web.ignoring().antMatchers("/hello");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().
                antMatchers("/user/**")
                .and()
                .authorizeRequests()
                    .antMatchers("/user/**").access("hasRole('ROLE_USER')");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



    //开启全局方法拦截
    @EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
    public static class GlobalSecurityConfiguration extends GlobalMethodSecurityConfiguration {
        @Override
        protected MethodSecurityExpressionHandler createExpressionHandler() {
            return new OAuth2MethodSecurityExpressionHandler();
        }

    }
}
