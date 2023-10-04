package com.w2.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.w2.post.pojo.PageQuery;
import com.w2.post.pojo.Post;
import com.w2.post.pojo.vo.PostVo;
import com.w2.post.service.PostService;
import com.w2.post.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author wuxingyu
* @description 针对表【t_post(贴子表)】的数据库操作Service实现
* @createDate 2023-10-04 21:39:13
*/
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService{

    @Autowired
    private PostMapper postMapper;

    @Override
    public IPage<PostVo> selectPage(PageQuery page) {
        Page<Post> postPage = Page.of(page.getCurrent(), page.getSize());
        LambdaQueryWrapper<Post> query = Wrappers.lambdaQuery(Post.class);
        IPage<PostVo> postVoPage = postMapper.selectVoPage(postPage, query);
        return postVoPage;
    }
}




