package com.example.mapper;

import com.example.pojo.SC;
import com.example.pojo.Schedule;
import com.example.pojo.Score;
import com.example.pojo.Teacher;
import com.example.pojo.Vo.TeacherAdmin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TeacherMapper {
    /**
     * 根据staffId查询教职工信息
     *
     * @param staffId 教职工编号
     * @return 教职工信息
     */
    @Select("select staff_id, name, class className, permission, password, university, college" +
            " from teacher where staff_id=#{staffId} and university=#{university}")
    Teacher findTeacherById(Integer staffId, String university);

    /**
     * 更新教职工账号密码
     *
     * @param staffId  教职工编号
     * @param password 更新的密码
     */
    @Update("update teacher set password=#{password} where staff_id=#{staffId} and university=#{university}")
    void updatePassword(Integer staffId, String password, String university);

    /**
     * 更新教职工信息
     *
     * @param teacher 更新的教职工信息
     */
    void update(Teacher teacher);

    @Select("select staff_id from teacher where name like concat('%',#{teacherName},'%') and university=#{university}")
    List<Integer> findTeacherByNameIds(String teacherName, String university);

    @Insert("insert into teacher(staff_id, name, permission, university, college) values (#{staffId},#{name},#{permission},#{university},#{college})")
    void addTeacher(Teacher teacher);

    @Select("select permission from teacher where staff_id=#{id} and university=#{university}")
    int GetPermission(Integer id, String university);

    /**
     * 老师打分
     *
     * @param stuId      学号
     * @param university 学校
     * @param courseId   课程编号
     * @param ordinary   平时分
     * @param ending     期末分
     * @param score      总成绩
     */
    @Update("update selection set ordinary=#{ordinary}, ending=#{ending}, score=#{score}, status=1 where stu_id=#{stuId} and university=#{university} and course_id=#{courseId}")
    void scoring(Integer stuId, String university, Integer courseId, Integer ordinary, Integer ending, Integer score);

    /**
     * 检查是否已经打过分
     *
     * @param stuId      学号
     * @param university 学校
     * @param courseId   课程编号
     */
    @Select("select status from selection where stu_id=#{stuId} and university=#{university} and course_id=#{courseId}")
    Integer checkScored(Integer stuId, String university, Integer courseId);

    @Select("select class from teacher where staff_id=#{id} and university=#{university}")
    Integer findClassNumber(Integer id, String university);

    List<Score> findSelectedStudent(List<Integer> courseIds, List<Integer> stuIds, String university);

    @Select("select * from selection where course_id=#{courseId} and stu_id=#{stuId} and university=#{university}")
    SC scoreInfo(Integer courseId, Integer stuId, String university);

    /**
     * 老师查询自己课程表
     * @param teacherId 老师编号
     * @param university 学校
     * @return 课程表信息
     */
    @Select("select course_name, classroom, date, time from course where teacher_id=#{teacherId} and university=#{university}")
    List<Schedule> scheduleResult(Integer teacherId, String university);

    List<TeacherAdmin> pageList(String name, String college, String university, Integer staffId);
}
