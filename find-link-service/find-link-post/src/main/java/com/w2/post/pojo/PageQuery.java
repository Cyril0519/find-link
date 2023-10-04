package com.w2.post.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PageQuery {
    protected long size;
    protected long current;
}
