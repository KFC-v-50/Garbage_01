package com.example.controller;

import com.example.pojo.Comment;
import com.example.pojo.Result;
import com.example.service.ForumService;
import com.example.utils.RedisCache;
import com.example.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/forum")
public class ForumController {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ForumService forumService;

    /**
     * 用户发送评论
     *
     * @param comment
     * @return
     */
    @PostMapping("/postcomment")
    public Result postcomment(@RequestBody Comment comment) {
        log.info("发表帖子");
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        Integer userType = (Integer) map.get("userType");
        String university = (String) map.get("university");
        comment.setId(id);
        comment.setUserType(userType);
        comment.setUniversity(university);

        forumService.postcomment(comment);
        return Result.success();
    }

    @GetMapping("/findAllComment")
    public Result findAllComment() {
        log.info("获取帖子");
        ArrayList<Comment> comments = new ArrayList<>();
        comments = forumService.findAllComment();
        return Result.success(comments);
    }
}
