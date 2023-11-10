package com.w2.post.mapper;

import com.w2.post.pojo.PostContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author wuxingyu
* @description 针对表【t_post_content(贴子详情)】的数据库操作Mapper
* @createDate 2023-10-05 13:15:57
* @Entity com.w2.post.pojo.PostContent
*/
@Mapper
public interface PostContentMapper extends BaseMapper<PostContent> {

}




