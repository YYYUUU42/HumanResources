package com.service;

import com.common.ResponseResult;
import com.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);
}
