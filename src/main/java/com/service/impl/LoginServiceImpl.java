package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.common.AppHttpCodeEnum;
import com.common.ResponseResult;
import com.domain.entity.User;
import com.service.LoginService;
import com.service.UserService;
import com.utils.RedisCache;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ResponseResult login(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmployeeid, user.getEmployeeid());
        User one = userService.getOne(queryWrapper);
        if (Objects.isNull(one)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_ERROR);
        }

        if (!user.getPwd().equals(one.getPwd())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PASSWORD_ERROR);
        }
        String s = JSON.toJSONString(one);

        //将用户信息存储到redis中，并且设置了超时时间为一天
        stringRedisTemplate.opsForValue().set("login",s,24, TimeUnit.HOURS);

        if (one.getPosition().equals("admin")){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.ADMIN);
        }
        if (one.getPosition().equals("人事专员")){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.ARCHIVES_WORKER);
        }
        if (one.getPosition().equals("人事经理")){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.ARCHIVES_MANAGER);
        }
        if (one.getPosition().equals("薪酬专员")){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SALARY_WORKER);
        }
        if (one.getPosition().equals("薪酬经理")) {
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SALARY_MANAGER);
        }


        return ResponseResult.okResult();
    }
}
