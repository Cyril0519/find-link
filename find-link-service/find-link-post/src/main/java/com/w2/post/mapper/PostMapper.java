package com.w2.post.mapper;

import com.w2.enhance.mp.EnhanceBaseMapper;
import com.w2.post.pojo.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.w2.post.pojo.vo.PostVo;
import org.apache.ibatis.annotations.Mapper;

/**
* @author wuxingyu
* @description 针对表【t_post(贴子表)】的数据库操作Mapper
* @createDate 2023-10-04 21:39:13
* @Entity com.w2.post.pojo.Post
*/
@Mapper
public interface PostMapper extends EnhanceBaseMapper<Post, PostVo> {

}




