package com.w2.post.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.w2.post.pojo.PageQuery;
import com.w2.post.pojo.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.w2.post.pojo.PostContent;
import com.w2.post.pojo.bo.PostBo;
import com.w2.post.pojo.vo.PostVo;

/**
* @author wuxingyu
* @description 针对表【t_post(贴子表)】的数据库操作Service
* @createDate 2023-10-04 21:39:13
*/
public interface PostService extends IService<Post> {

    IPage<PostVo> selectPage(PageQuery page);

    void addOne(PostBo postBo, PostContent content);

}
