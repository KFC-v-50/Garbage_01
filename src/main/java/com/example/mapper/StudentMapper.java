package com.example.mapper;

import com.example.pojo.*;
import com.example.pojo.Vo.StudentAdmin;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface StudentMapper {
    /**
     * 学生更新密码
     *
     * @param stuId      学号
     * @param password   新密码
     * @param university 学校信息
     */
    @Update("update student set password=#{password} where stu_id=#{stuId} and university=#{university}")
    void updatePassword(Integer stuId, String password, String university);

    /**
     * 通过id找学生信息
     *
     * @param stuId 学号
     * @return 返回学生信息
     */
    @Select("select stu_id, name, major, college, university, class className, password, grand" +
            " from student where stu_id=#{stuId} and university=#{university}")
    Student findStudentByStuId(Integer stuId, String university);

    /**
     * 更新学生账号信息
     *
     * @param student 更新的学生信息
     */
    void update(Student student);

    /**
     * 查找所有选课课程
     */
    @Select("Select * from course where university=#{university}")
    ArrayList<Course> getCourses(String university);

    /**
     * 向选课表中插入选择的课
     */
    @Insert("insert into selection(stu_id, course_id,university) values (#{stuId},#{courseId},#{university})")
    void selectCourse(SC sc);

    /**
     * 从选课表中先查出选的课程id,再根据id查课程
     */
    @Select("select * from course where course_id in (select course_id from selection where stu_id=#{id}) and university=#{university}")
    ArrayList<Course> getMyCourses(Integer id, String university);

    /**
     * 根据id删选的课程
     */
    @Delete("delete from selection where course_id=#{courseID} and university=#{university} and stu_id=#{id}")
    void deleteCourse(Integer courseID,Integer id, String university);


    /**
     * 根据id,university查询一门课的信息
     */
    @Select("select * from course where course_id=#{id} and university=#{university}")
    Course getOneCourse(Integer id, String university);

    /**
     * 学生信息分页条件查询
     * @param stuId 学生学号
     * @param name 学生名字
     * @param major 专业
     * @param college 学院
     * @param university 学校
     * @param className 班级
     * @param grand 年级
     * @return 学生信息列表
     */
    List<StudentAdmin> pageList(Integer stuId, String name, String major, String college, String university, Integer className, Integer grand);

    /**
     * 通过文件批量导入学生信息
     * @param student
     */
    @Insert("insert into student(stu_id, name, major, college, university, grand) values (#{stuId},#{name},#{major},#{college},#{university},#{grand})")
    void addStudent(Student student);

    @Select("select COUNT(*) from selection where university=#{university} and course_id=#{courseId}")
    Integer findSelected(Integer courseId, String university);

    @Select("select class from student where stu_id=#{stuId} and university=#{university}")
    Integer findClassNumber(Integer stuId, String university);

    @Select("select ordinary, ending, score, course_id from selection where stu_id=#{stuId} and university=#{university} and score is not null")
    List<Score> findScore(Integer stuId, String university);

    @Select("select stu_id from student where name like concat('%',#{studentName},'%') and university=#{university}")
    List<Integer> findStudentByNameIds(String studentName, String university);
}
