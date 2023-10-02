package com.w2.user.service;

import com.w2.user.pojo.BaseUser;

public interface RemoteUserService {
    BaseUser queryByUsername(String username);
}
