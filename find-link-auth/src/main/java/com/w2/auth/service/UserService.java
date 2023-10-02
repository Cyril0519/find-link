package com.w2.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.w2.user.pojo.BaseUser;
import com.w2.user.service.RemoteUserService;

public interface UserService extends RemoteUserService, IService<BaseUser> {
}
