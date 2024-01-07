package com.example.mapper;

import com.example.pojo.ClassroomApply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

@Mapper
public interface ClassroomApplyMapper {

    @Insert("insert into classroomapply (courseID, classroom, staff_id, university) values (#{courseID},#{classroom},#{id},#{university}) ")
    void applyChange(Integer id, String university, Integer courseID, String classroom);

    @Select("SELECT id, classroom, courseID, staff_id, university FROM classroomapply WHERE state != 1")
    ArrayList<ClassroomApply> classroomApply();

    @Update("update classroomapply set state=1 where id=#{id}")
    void classroomApplyconfirm(Integer id);

    @Select("select * from classroomapply where id=#{id}")
    ClassroomApply selectClassroomApply(Integer id);
}
