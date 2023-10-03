package com.w2.user.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户大体信息
 */
@Data
public class BaseUser implements Serializable {
    /**
     * 用户id
     */
    private Long uid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;


    /**
     * 用户电话号码
     */
    private String phone;

    private int age;

    private int sex;

    private String profile;

    private String authority;

    private int isEnabled;
    private int isAccountExpire;
    private int isAccountLocked;

}
