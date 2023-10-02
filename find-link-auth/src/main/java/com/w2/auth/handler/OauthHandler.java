package com.w2.auth.handler;

import bean.Constant;
import bean.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.petsAdoption.oauth.controller")
public class OauthHandler {
    @ExceptionHandler(Exception.class)
    public Result<Void> errorhandler(Exception e) {
        e.printStackTrace();
        String message = e.getMessage();
        return new Result<>(Constant.FAIL, message);
    }
}
