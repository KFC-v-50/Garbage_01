package com.example.service;

import com.example.pojo.Comment;

import java.util.ArrayList;

public interface ForumService {
    void postcomment(Comment comment);

    ArrayList<Comment> findAllComment();
}
