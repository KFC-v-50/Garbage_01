package com.example.mapper;

import com.example.pojo.Course;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CourseMapper {

    /**
     * 分页查询课表信息
     * @param courseName 课程名称
     * @param teacherIds 课程老师id列表
     * @param time 1为早八 2为早十 3为下二 4为下四
     * @param date 周一到周五
     * @param type 必修课，选修课
     * @param university 学校
     * @param college 学院
     * @return 课程列表信息
     */
    List<Course> pageList(String courseName, List<Integer> teacherIds, Integer time, Integer date, String type,
                          String university, String college,Integer state);

    /**
     * 导入课程（教秘的功能）
     * @param course 课程信息
     */
    @Insert("insert into course(course_id, course_name, teacher_id, classroom, time, date, type, description, university, college, credit, volume)" +
            "values(#{courseId},#{courseName},#{teacherId},#{classroom},#{time},#{date},#{type},#{description},#{university},#{college}, #{credit}, #{volume})")
    void addCourse(Course course);

    /**
     * 通过课程id找到课表信息（主要寻找是否存在某某课程）
     * @param courseId 课程编号
     * @param university 学校
     * @return
     */
    @Select("select * from course where course_id=#{courseId} and university=#{university}")
    Course findCourseById(Integer courseId, String university);

    @Select("select course_id from course where course_name like concat('%',#{courseName},'%')and university=#{university}")
    List<Integer> findCourseByNameIds(String courseName, String university);

    /**
     * 根据课程名称和老师编号模糊匹配课程编号集合
     * @param courseName 课程名称
     * @param staffId 老师编号
     * @param university 学校
     * @return 课程编号集合
     */
    @Select("select course_id from course where teacher_id=#{staffId} and university=#{university} and course_name like concat('%',#{courseName},'%')")
    List<Integer> findCourseByNameAndStaffIds(String courseName, Integer staffId, String university);

    /**
     * 根据老师编号查询课程
     * @param university 学校
     * @param teacherId 老师编号
     * @return 课程信息
     */
    @Select("select * from course where university=#{university} and teacher_id=#{teacherId}")
    List<Course> findTeacherCourse(String university, Integer teacherId);

    @Update("update course set classroom=#{classroom} where course_id=#{courseID}")
    void updateCourse(String classroom, Integer courseID);
}
