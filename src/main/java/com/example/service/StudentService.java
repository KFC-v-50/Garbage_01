package com.example.service;


import com.example.pojo.*;

import java.util.ArrayList;
import java.util.List;

public interface StudentService {
    /**
     * 学生更新密码
     * @param stuId 学号
     * @param password 新密码
     */
    void updatePassword(Integer stuId, String password, String university);

    /**
     * 通过id找学生信息
     * @param stuId 学生id
     * @return 返回学生信息
     */
    Student findStudentByStuId(Integer stuId, String university);

    /**
     * 更新学生账号信息
     * @param student 要更新的学生信息
     */
    void update(Student student);

    /**
     * 查找选课表
     */
    ArrayList<Course> getCourses(String university);

    /**
     * 选择课，更新课表
     * @param sc 课程id
     */
    Integer selectCourse(SC sc);

    /**
     * 根据id返回课表
     */
    ArrayList<Course> getMyCourses(Integer id, String university);

    /**
     * 根据id退课
     */
    void deleteCourse(Integer courseID,Integer id, String university);

    /**
     * 根据id,university查询一门课的信息
     */
    Course getOneCourse(Integer id,String university);

    /**
     * 学生信息分页条件查询
     * @param page 第几页
     * @param pageSize 每页展示数量
     * @param stuId 学生学号
     * @param name 学生名字
     * @param major 专业
     * @param college 学院
     * @param university 学校
     * @param className 班级
     * @param grand 年级
     * @return 学生信息列表
     */
    PageBean page(Integer page, Integer pageSize, Integer stuId, String name, String major, String college, String university, Integer className, Integer grand);

    Integer findClassNumber(Integer stuId, String university);


    List<Score> findScore(Integer stuId, String university);

    /**
     * 学生获取课表信息
     * @param stuId 学号
     * @param university 学校
     * @return 课表信息
     */
    List<Schedule> scheduleResult(Integer stuId, String university);

    List<Grade> gradeAnalysis(Integer courseID, String university);
}
