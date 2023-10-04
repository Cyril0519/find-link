package com.w2.post.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.w2.result.bean.Constant;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @TableName t_post
 */
@TableName(value ="t_post")
@Data
public class Post implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long uid;

    private String username;

    private String userProfile;

    private String title;

    private String postPosition;

    private String imgUrls;

    private String type;

    private Integer isAudit = Constant.NO;

    @TableLogic(value = "0", delval = "1")
    private Integer isDelete = Constant.NO;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date updateTime;

    private String createBy;

    private String updateBy;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String abstractsContent;

    private Integer likes = 0;

    private Integer comments = 0;

    private Integer collections = 0;

    private static final long serialVersionUID = 1L;
}