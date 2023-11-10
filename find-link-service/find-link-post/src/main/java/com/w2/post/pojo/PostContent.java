package com.w2.post.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName t_post_content
 */
@TableName(value ="t_post_content")
@Data
public class PostContent implements Serializable {
    private Long postId;

    private String content;

    private static final long serialVersionUID = 1L;
}