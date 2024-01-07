package com.example.mapper;


import com.example.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface ForumMapper {

    @Insert("insert into forum(id, userType, comment, name, university) values (#{id},#{userType},#{comment},#{name},#{university})")
    void postcomment(Comment comment);

    @Select("select * from forum")
    ArrayList<Comment> findAllComment();
}
