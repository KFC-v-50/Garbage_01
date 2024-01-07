package com.example.mapper;

import com.example.pojo.Course;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminMapper {
    /**
     * 重置分数
     *
     * @param stuId      学号
     * @param courseId   课程编号
     * @param university 学校
     */
    @Update("update selection set ordinary=null, ending=null, score=null, status=0 where stu_id=#{stuId} and course_id=#{courseId} and university=#{university}")
    void resetScore(Integer stuId, Integer courseId, String university);

    /**
     * 加课
     *
     * @param stuId
     * @param courseId
     * @param university
     */
    @Insert("insert into selection (stu_id, course_id,university) values(#{stuId},#{courseId},#{university}) ")
    void addClassToStu(Integer stuId, Integer courseId, String university);

    /**
     * 修改课程信息：时间 教室等
     * @param course
     */
    void resetClass(Course course);

    @Delete("delete from student where stu_id=#{stuId} and university=#{university}")
    void deleteStu(Integer stuId, String university);

    @Delete("delete from teacher where staff_id=#{staffId} and university=#{university}")
    void deleteTea(Integer staffId, String university);

    @Delete("delete from course where course_id=#{courseId} and university=#{university}")
    void deleteCourse(Integer courseId, String university);
}
