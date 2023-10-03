package com.w2.auth.controller;


import bean.Constant;
import bean.Result;
import com.google.code.kaptcha.Producer;
import com.w2.auth.service.AuthService;
import com.w2.auth.util.AuthToken;
import com.w2.auth.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/oauth")
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private Producer kaptchaProducer;
    @Autowired
    private StringRedisTemplate redisTemplate;
    ;

    @Value("${auth.clientId}")
    private String clientId;
    @Value("${auth.clientSecret}")
    private String clientSecret;
    @Value("${auth.cookieDomain}")
    private String cookieDomain;
    @Value("${auth.cookieMaxAge}")
    private Integer cookieMaxAge;


    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> params, HttpServletResponse response) {
        // 校验参数
        String username = params.get("username");
        String rowPassword = params.get("password");
        String password = new String(Base64Utils.decodeFromString(rowPassword));
        String kaptchaFromUser = params.get("code");
        String uuid = params.get("uuid");
        if (StringUtils.isEmpty(username)) {
            throw new RuntimeException("请输入用户名");
        }
        if (StringUtils.isEmpty(password)) {
            throw new RuntimeException("请输入密码");
        }
        if (StringUtils.isEmpty(kaptchaFromUser)) {
            throw new RuntimeException("请输入验证码");
        }
        if (StringUtils.isEmpty(uuid)) {
            throw new RuntimeException("uuid不存在");
        }
        // 校验验证码
        String kaptchaFromSystem = redisTemplate.boundValueOps("kaptcha." + uuid).get();
        if (kaptchaFromSystem == null) {
            return new Result<Void>(Constant.FAIL, "验证码错误或者已过期");
        }
        if (!kaptchaFromSystem.equalsIgnoreCase(kaptchaFromUser)) {
            return new Result<Void>(Constant.FAIL, "验证码错误或者已过期");
        }

        AuthToken authToken = authService.login(username, password, clientId, clientSecret);
        // 将jti的值存入cookie
        this.saveJtiToCookie(authToken.getJti(), response);
        // 返回结果
        return new Result<>(Constant.SUCCESS, "登录成功", authToken.getJti());
    }


    // 将令牌的短标识jti存入cookie
    private void saveJtiToCookie(String jti, HttpServletResponse response) {
        CookieUtil.addCookie(response, "/", "uid", jti, cookieMaxAge, false);
    }

    //生成验证码图片
    @GetMapping("/kaptcha")
    public void getKaptcha(@RequestParam("uuid") String uuid, HttpServletResponse response) {
        //    生成验证码
        String text = kaptchaProducer.createText();
        //    生成验证码图片
        BufferedImage image = kaptchaProducer.createImage(text);
        //     保存redis中
        redisTemplate.opsForValue().set("kaptcha." + uuid, text, 2 * 60, TimeUnit.SECONDS); // 2分钟过期时间

        response.setContentType("image/png");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            System.out.println("响应验证码失败");
        }
    }


    // 验证码有效性判断
    private boolean isCorrectCaptcha(String uuid, String codeFromUser) {
        String codeFromSystem = redisTemplate.boundValueOps("kaptcha." + uuid).get();
        return codeFromSystem != null && codeFromSystem.equalsIgnoreCase(codeFromUser);
    }

    @GetMapping("test")
    public String test() {
        return "hello world";
    }
}
