package com.w2.user.pojo.bo;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
public class UserBo {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
