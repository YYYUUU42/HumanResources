package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.domain.entity.User;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

/**
* @author Yu
* @description 针对表【user(员工登录信息)】的数据库操作Service
* @createDate 2022-11-28 14:27:46
*/
public interface UserService extends IService<User> {

}
