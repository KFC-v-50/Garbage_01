package com.example.mapper;

import com.example.pojo.Classroom;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface ClassroomMapper {

    @Insert("insert into classroom(classroom, type, university) values (#{classroom},#{type},#{university})")
    void addClassroom(Classroom classroom);

    @Select("select * from classroom where classroom=#{classroom} and university=#{university}")
    Classroom findClassroom(String classroom, String university);

    void updateClassroom(Classroom classroom);

    @Select("select * from classroom")
    ArrayList<Classroom> SelectAllClassroom();

    @Delete("delete from classroom where classroom=#{classroom} and university=#{university}")
    void deleteClassroom(Classroom classroom);
}
