package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.common.ResponseResult;
import com.domain.entity.User;
import com.service.LoginService;
import com.utils.RedisCache;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.invoke.LambdaConversionException;
import java.util.Objects;

@RestController
@RequestMapping("/login")
public class loginController {

    @Resource
    private LoginService loginService;

    @PostMapping
    public ResponseResult login(String employeeid,String pwd){
        User user = new User();
        user.setEmployeeid(employeeid);
        user.setPwd(pwd);
        return loginService.login(user);
    }


}
