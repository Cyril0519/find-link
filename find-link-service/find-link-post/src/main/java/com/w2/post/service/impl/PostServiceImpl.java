package com.w2.post.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.w2.post.config.TokenDecode;
import com.w2.post.mapper.PostContentMapper;
import com.w2.post.pojo.PageQuery;
import com.w2.post.pojo.Post;
import com.w2.post.pojo.PostContent;
import com.w2.post.pojo.bo.PostBo;
import com.w2.post.pojo.vo.PostVo;
import com.w2.post.service.PostService;
import com.w2.post.mapper.PostMapper;
import com.w2.result.bean.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
* @author wuxingyu
* @description 针对表【t_post(贴子表)】的数据库操作Service实现
* @createDate 2023-10-04 21:39:13
*/
@Service
@RequiredArgsConstructor
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService{

    private final PostMapper postMapper;
    private final TokenDecode tokenDecode;
    private final PostContentMapper postContentMapper;

    @Override
    public IPage<PostVo> selectPage(PageQuery page) {
        Page<Post> postPage = Page.of(page.getCurrent(), page.getSize());
        LambdaQueryWrapper<Post> query = Wrappers.lambdaQuery(Post.class);
        IPage<PostVo> postVoPage = postMapper.selectVoPage(postPage, query);
        return postVoPage;
    }

    @Override
    @Transactional
    public void addOne(PostBo postBo, PostContent content) {
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        Post post = BeanUtil.toBean(postBo, Post.class);
        post.setComments(0);
        post.setLikes(0);
        post.setCollections(0);
        post.setCreateBy(userInfo.get("username"));
        post.setCreateTime(new Date());
        post.setIsAudit(Constant.NO);
        if (content.getContent().length()>200) {
            post.setAbstractsContent(content.getContent().substring(0, 200));
        }else {
            post.setAbstractsContent(content.getContent());
        }
        int insert = postMapper.insert(post);
        if (insert == 1) {
            content.setPostId(post.getId());
        }
        postContentMapper.insert(content);
    }


}




