package com.w2.user.controller;

import com.w2.result.bean.Constant;
import com.w2.result.bean.Result;
import com.w2.user.config.TokenDecode;
import com.w2.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenDecode tokenDecode;

    @RequestMapping("test")
    public Result<Object> test() {
        return new Result<>(Constant.SUCCESS, "hello world", tokenDecode.getUserInfo());
    }

}
