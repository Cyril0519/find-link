package com.w2.auth.util;

import bean.Constant;
import bean.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CheckCaptchaCorrectness {
    @Autowired
    private static StringRedisTemplate redisTemplate;


    public static Result<Void> check(String uuid, String kaptchaFromUser) {
        if (StringUtils.isEmpty(uuid)) {
            throw new RuntimeException("uuid不存在");
        }
        if (StringUtils.isEmpty(kaptchaFromUser)) {
            throw new RuntimeException("验证码不存在");
        }
        String kaptchaFromSystem = redisTemplate.boundValueOps("kaptcha." + uuid).get();
        if (kaptchaFromSystem==null){
            return new Result<>(Constant.FAIL, "验证码已过期");
        }
        if(!kaptchaFromSystem.equalsIgnoreCase(kaptchaFromUser)){
            return new Result<Void>(Constant.FAIL, "验证码错误或者已过期");
        }
        return new Result<>(Constant.FAIL, "正确");
    }
}
