package com.example.service.Impl;

import com.example.mapper.CourseMapper;
import com.example.mapper.StudentMapper;
import com.example.pojo.Course;
import com.example.pojo.PageBean;
import com.example.service.CourseService;
import com.example.service.TeacherService;
import com.example.utils.ScheduleUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherService teacherService;

    @Override
    public PageBean page(Integer page, Integer pageSize, String courseName, List<Integer> teacherIds, Integer time,
                         Integer date, String type, String university, String college, Integer state) {
        //设置分页参数
        PageHelper.startPage(page, pageSize);

        //执行查询
        List<Course> courseList = courseMapper.pageList(courseName, teacherIds, time, date, type, university, college,state);
        for (Course course : courseList) {
            //找到课程编号对应的课程名称
            Integer courseId = course.getCourseId();
            Integer selected = studentMapper.findSelected(courseId, university);
            course.setSelected(selected);

            //找到老师编号对应的名字
            Integer staffId = course.getTeacherId();
            String teacherName = teacherService.findTeacherByStaffId(staffId, university).getName();
            course.setTeacherName(teacherName);

            //计算每个course的映射时间
            String schedule = ScheduleUtils.schedule(course.getDate(), course.getTime());
            course.setSchedule(schedule);
        }
        Page<Course> p = (Page<Course>) courseList;

        //计算总页数
        Integer pageCount = (int) p.getTotal() % pageSize == 0 ? (int) p.getTotal() / pageSize : (int) p.getTotal() / pageSize + 1;

        //封装pageBean对象
        PageBean pageBean = new PageBean((int) p.getTotal(), p.getResult(), pageCount);
        return pageBean;
    }

    @Override
    public void addCourse(Course course) {
        courseMapper.addCourse(course);
    }

    @Override
    public Course findCourseById(Integer courseId, String university) {
        return courseMapper.findCourseById(courseId, university);
    }

    @Override
    public List<Integer> findTeacherByNameIds(String courseName, String university) {
        return courseMapper.findCourseByNameIds(courseName, university);
    }

    @Override
    public List<Course> findTeacherCourse(String university, Integer teacherId) {
        List<Course> courses = courseMapper.findTeacherCourse(university, teacherId);
        for (Course course : courses) {
            //找到课程编号对应的课程名称
            Integer courseId = course.getCourseId();
            Integer selected = studentMapper.findSelected(courseId, university);
            course.setSelected(selected);

            //计算每个course的映射时间
            String schedule = ScheduleUtils.schedule(course.getDate(), course.getTime());
            course.setSchedule(schedule);
        }
        return courses;
    }
}
