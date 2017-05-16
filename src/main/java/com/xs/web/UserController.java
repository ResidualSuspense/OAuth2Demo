package com.xs.web;

import com.xs.common.RestEnum;
import com.xs.common.ResultJson;
import com.xs.common.ResultUtils;
import com.xs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by xs on 2017-04-25.
 */
@RestController
@RequestMapping("/user/")
public class UserController {
    @Resource
    UserService userService;

//    @RequestMapping(value = "findUserByName",method = RequestMethod.GET)
//    public ResultJson findUserByName(@RequestParam("name")String name){
//        if (StringUtils.isEmpty(name)){
//            return ResultUtils.error(RestEnum.PARAMETER_INVALID);
//        }
//        UserDetails userDetails = userService.loadUserByUsername(name);
//        return  ResultUtils.success(userDetails);
//    }

    @GetMapping("{name}")
    public ResultJson findUserByName(@PathVariable("name") String name){
        if (StringUtils.isEmpty(name)){
            return ResultUtils.error(RestEnum.PARAMETER_INVALID);
        }
        UserDetails userDetails = userService.loadUserByUsername(name);
        return  ResultUtils.success(userDetails);
    }
}
