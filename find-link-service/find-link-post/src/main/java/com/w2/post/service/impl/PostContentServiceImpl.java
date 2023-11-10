package com.w2.post.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.w2.post.pojo.PostContent;
import com.w2.post.service.PostContentService;
import com.w2.post.mapper.PostContentMapper;
import org.springframework.stereotype.Service;

/**
* @author wuxingyu
* @description 针对表【t_post_content(贴子详情)】的数据库操作Service实现
* @createDate 2023-10-05 13:15:57
*/
@Service
public class PostContentServiceImpl extends ServiceImpl<PostContentMapper, PostContent>
    implements PostContentService{

}




