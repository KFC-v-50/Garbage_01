package com.example.service;

import com.example.pojo.Course;
import com.example.pojo.PageBean;

import java.util.List;

public interface CourseService{

    /**
     * 分页查询课表信息
     *
     * @param courseName 课程名称
     * @param teacherIds 课程老师id列表
     * @param time       1为早八 2为早十 3为下二 4为下四
     * @param date       周一到周五
     * @param type       必修课，选修课
     * @param university 学校
     * @param state
     * @return 课程列表信息
     */
    PageBean page(Integer page, Integer pageSize, String courseName, List<Integer> teacherIds, Integer time,
                  Integer date, String type, String university, String college, Integer state);

    /**
     * 导入课程（教秘的功能）
     * @param course 课程信息
     */
    void addCourse(Course course);

    Course findCourseById(Integer courseId, String university);

    List<Integer> findTeacherByNameIds(String courseName, String university);

    /**
     * 根据老师编号寻找课程
     *
     * @param university 学校
     * @param teacherId  老师编号
     * @return
     */
    List<Course> findTeacherCourse(String university, Integer teacherId);
}
