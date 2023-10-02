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
    private String uid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户性别 0：没写，1：男，2：女
     */

    private String gender;

    /**
     * 用户电话号码
     */
    private String phone;

    /**
     * 用户居住地址
     */
    private String address;

    /**
     * 用户邮箱
     */
    private String email;
    private String authority;
    private int isEnabled;
    private int isAccountExpire;
    private int isAccountLocked;

}
