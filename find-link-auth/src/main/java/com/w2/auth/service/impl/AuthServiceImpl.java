package com.w2.auth.service.impl;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.shaded.com.google.protobuf.ServiceException;
import com.w2.auth.service.AuthService;
import com.w2.auth.util.AuthToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Value("${auth.ttl}")
    private Long ttl;

    @Override
    public AuthToken login(String username, String password, String clientId, String ClientSecret) {
        // 申请令牌
        ServiceInstance serviceInstance = loadBalancerClient.choose("auth");
        URI uri = serviceInstance.getUri();
        String url = uri + "/oauth/token";
        // 创建请求体
        HashMap<String, Object> body = new HashMap<>();
        body.put("grant_type", "password");
        body.put("username", username);
        body.put("password", password);
        // 请求对象
        HttpRequest post = HttpUtil.createPost(url);
        post.form(body);
        post.basicAuth("client", "client");
        post.contentType("application/x-www-form-urlencoded");
        HttpResponse execute = post.execute();
        int status = execute.getStatus();
        String resBody = execute.body();
        JSONObject response = new JSONObject(resBody);

        if (status == 400) {
            throw new RuntimeException(response.getStr("error_description"));
        }

        // 获取结果
        String accessToken = response.getStr("access_token");
        String jti = response.getStr("jti");
        String refreshToken = response.getStr("refresh_token");

        // 2.封装结果数据
        AuthToken authToken = new AuthToken();
        authToken.setAccessToken(accessToken);
        authToken.setRefreshToken(refreshToken);
        authToken.setJti(jti);


        stringRedisTemplate.boundValueOps(authToken.getJti())
                .set(authToken.getAccessToken(), ttl, TimeUnit.SECONDS);
        return authToken;
    }

}
