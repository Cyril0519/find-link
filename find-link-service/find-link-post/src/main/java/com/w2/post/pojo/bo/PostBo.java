package com.w2.post.pojo.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PostBo implements Serializable {

    @NotNull
    private Long uid;
    @NotBlank
    private String username;
    @NotBlank
    private String userProfile;
    @NotBlank
    private String title;

    private String postPosition;

    private String imgUrls;
    @NotBlank
    private String type;

    private Date createTime;

    private Date updateTime;

    private String createBy;

    private String updateBy;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String abstractsContent;


    private static final long serialVersionUID = 1L;
}

