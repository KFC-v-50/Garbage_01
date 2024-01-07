package com.example.controller;

import com.example.pojo.Course;
import com.example.pojo.PageBean;
import com.example.pojo.Result;
import com.example.service.CourseService;
import com.example.service.TeacherService;
import com.example.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 分页查询课表信息
     * @param courseName 课程名称
     * @param teacherName 课程老师名称
     * @param time 1为早八 2为早十 3为下二 4为下四
     * @param date 周一到周五
     * @param type 必修课，选修课
     * @return 课程列表信息
     */
    @GetMapping
    public Result<PageBean> getCoursePage(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          String courseName, String teacherName, Integer time,
                                          Integer date, String type, String college){
        log.info("课表分页查询，参数：{}，{}，{}，{}，{}，{}，{}, {}", page, pageSize, courseName, teacherName, time, date, type, college);
        Map<String, Object> map = ThreadLocalUtil.get();
        String university = (String) map.get("university");
        Integer id = (Integer) map.get("id");
        Integer userType = (Integer) map.get("userType");

        Integer state = 1;
        if (userType == 4){
            state = null;
        }

        //根据teacherName模糊匹配老师的id列表
        List<Integer> teacherIds = null;
        if (teacherName != null){
            teacherIds = teacherService.findTeacherByNameIds(teacherName, university);
        }
        PageBean pageBean = courseService.page(page, pageSize, courseName, teacherIds, time, date, type, university, college, state);
        return Result.success(pageBean);
    }

    /**
     * 导入课程（教秘的功能）
     * @param course 课程信息
     */
    @PostMapping
    public Result addCourse(@RequestBody Course course){
        log.info("导入课程：{}", course);
        Map<String, Object> map = ThreadLocalUtil.get();
        course.setUniversity((String) map.get("university"));
        //课表信息是否已存在
        Course cou = courseService.findCourseById(course.getCourseId(), course.getUniversity());
        if (cou == null){
            courseService.addCourse(course);
        } else{
            return Result.error("课程信息已存在");
        }
        return Result.success();
    }

    @GetMapping("/teacher")
    public Result<List<Course>> findTeacherCourse(){
        log.info("老师查看自己的课程");
        Map<String, Object> map = ThreadLocalUtil.get();
        String university = (String) map.get("university");
        Integer teacherId = (Integer) map.get("id");
        List<Course> course = courseService.findTeacherCourse(university, teacherId);
        return Result.success(course);
    }

    @GetMapping("/{id}")
    public Result<Course> getCourseById(@PathVariable(value = "id") Integer courseId){
        log.info("管理员根据id获取课程信息：{}",courseId);
        Map<String, Object> map = ThreadLocalUtil.get();
        String university = (String) map.get("university");
        Course course = courseService.findCourseById(courseId, university);

        return Result.success(course);
    }
}
