package com.w2.auth.handler;

import com.w2.result.bean.Constant;
import com.w2.result.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 */
@RestControllerAdvice(basePackages = "com.w2.auth.controller")
@Slf4j
public class AuthHandler {
    @ExceptionHandler(Exception.class)
    public Result<Void> errorhandler(Exception e) {
        log.error(e.getMessage(), e);
        String message = e.getMessage();
        return new Result<>(Constant.FAIL, message);
    }
}
