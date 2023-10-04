package com.w2.post.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.w2.post.pojo.PageQuery;
import com.w2.post.pojo.vo.PostVo;
import com.w2.post.service.PostService;
import com.w2.result.bean.Constant;
import com.w2.result.bean.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("list")
    public Result<IPage<PostVo>> getList(PageQuery page) {
        IPage<PostVo> postVoIPage = postService.selectPage(page);
        return new Result<>(Constant.SUCCESS, "查询成功", postVoIPage);
    }
}
