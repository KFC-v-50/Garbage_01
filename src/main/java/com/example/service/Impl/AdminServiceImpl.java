package com.example.service.Impl;

import com.example.mapper.*;
import com.example.pojo.ClassroomApply;
import com.example.pojo.Course;
import com.example.pojo.Teacher;
import com.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClassroomApplyMapper classroomApplyMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public void resetScore(Integer stuId, Integer courseId, String university) {
        adminMapper.resetScore(stuId, courseId, university);
    }

    @Override
    public int addClassToStu(Integer stuId, Integer courseId, String university) {
        ArrayList<Course> hasChoosed = getStuCourses(stuId, university);
        Course thisCourse = getOneCourse(courseId, university);
        for (Course course : hasChoosed) {
            //遍历已经选的课程，对比选课时间，有冲突返回0
            if (course.getDate().equals(thisCourse.getDate()) && course.getTime().equals(thisCourse.getTime())) {
                return 0;
            }
        }
        adminMapper.addClassToStu(stuId,courseId,university);
        return 1;
    }

    //这个函数是用来查询教秘指定课程信息
    public Course getOneCourse(Integer id, String university) {
        return studentMapper.getOneCourse(id, university);
    }

    //    这个函数为查询学生选的课程
    public ArrayList<Course> getStuCourses(Integer id, String university) {
        return studentMapper.getMyCourses(id, university);
    }

    @Override
    public void resetClass(Course course) {
        adminMapper.resetClass(course);
    }

    @Override
    public void deleteStudent(Integer stuId, String university) {
        adminMapper.deleteStu(stuId, university);
    }

    @Override
    public void deleteTeacher(Integer staffId, String university) {
        adminMapper.deleteTea(staffId, university);
    }

    @Override
    public void deleteCourse(Integer courseId, String university) {
        adminMapper.deleteCourse(courseId, university);
    }

    @Override
    public ArrayList<ClassroomApply> classroomApply() {
        ArrayList<ClassroomApply>classroomApplies=classroomApplyMapper.classroomApply();

        for (ClassroomApply classroomApply:classroomApplies) {
            Teacher teacher = teacherMapper.findTeacherById(classroomApply.getStaffId(),classroomApply.getUniversity());
            classroomApply.setStaffName(teacher.getName());
        }
        return classroomApplies;
    }

    @Override
    public void classroomApplyconfirm(Integer id) {
        classroomApplyMapper.classroomApplyconfirm(id);
        ClassroomApply classroomApply=classroomApplyMapper.selectClassroomApply(id);
        String classroom=classroomApply.getClassroom();
        Integer courseID=classroomApply.getCourseID();

        courseMapper.updateCourse(classroom,courseID);
    }
}
