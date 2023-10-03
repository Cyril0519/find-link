package com.w2.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.w2.user.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {
    public User getUserByUsername(String username);

}
