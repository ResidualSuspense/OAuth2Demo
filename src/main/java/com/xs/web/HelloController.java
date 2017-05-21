package com.xs.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xs on 2017-04-25.
 */
@Controller
public class HelloController {



    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/hello")
    public String hello() {
        SecurityContext ctx   =   SecurityContextHolder.getContext();
        Authentication auth   =   ctx.getAuthentication();
        if(auth.getPrincipal()   instanceof UserDetails)
        {
            User user   =   (User) auth.getPrincipal();
            System.out.println(user.getUsername());
        }
        //本段代码演示如何获取登录的用户资料

        return "hello";
    }

    @RequestMapping("/")
    public String root() {
        //如不进行此项配置，从login登录成功后，会提示找不网页
        return "index";
    }


    @RequestMapping("/hasRole")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String hasRole(String name){
        return name;
    }
}
