package com.example.service;

import com.example.pojo.ClassroomApply;
import com.example.pojo.Course;

import java.util.ArrayList;

public interface AdminService {
    /**
     * 重置分数
     * @param stuId 学号
     * @param courseId 课程编号
     * @param university 学校
     */

    void resetScore(Integer stuId, Integer courseId, String university);

    /**
     * 教秘给学生加课
     * @param stuId
     * @param courseId
     * @param university
     */

    int addClassToStu(Integer stuId, Integer courseId, String university);

    /**
     * 修改课程信息：时间 教室等
     * @param course
     */
    void resetClass(Course course);

    /**
     * 删除学生信息
     * @param stuId 学号
     * @param university 学校
     */
    void deleteStudent(Integer stuId, String university);

    /**
     * 删除老师信息
     * @param staffId 工号
     * @param university 学校
     */
    void deleteTeacher(Integer staffId, String university);

    /**
     * 删除课程信息
     * @param courseId 课程id
     * @param university 学校
     */
    void deleteCourse(Integer courseId, String university);

    ArrayList<ClassroomApply> classroomApply();

    void classroomApplyconfirm(Integer id);
}
