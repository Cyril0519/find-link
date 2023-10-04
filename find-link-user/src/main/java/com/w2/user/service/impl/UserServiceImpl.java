package com.w2.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.w2.user.mapper.UserMapper;
import com.w2.user.pojo.BaseUser;
import com.w2.user.pojo.User;
import com.w2.user.service.RemoteUserService;
import com.w2.user.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DubboService
public class UserServiceImpl implements RemoteUserService,UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public BaseUser queryByUsername(String username) {
        LambdaQueryWrapper<User> query = Wrappers.lambdaQuery();
        query.eq(User::getUsername, username);
        return userMapper.selectOne(query);
    }

}
