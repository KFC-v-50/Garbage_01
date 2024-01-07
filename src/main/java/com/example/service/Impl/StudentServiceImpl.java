package com.example.service.Impl;

import com.example.mapper.*;
import com.example.pojo.*;
import com.example.pojo.Vo.StudentAdmin;
import com.example.service.StudentService;
import com.example.utils.BCryptPasswordUtils;
import com.example.utils.ScheduleUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private LogMapper logMapper;
    @Autowired
    private SelectionMapper selectionMapper;

    @Override
    public void updatePassword(Integer stuId, String password, String university) {
        log.info("对学生要修改的密码进行进行哈希处理");
        String encode = BCryptPasswordUtils.encodePassword(password);
        studentMapper.updatePassword(stuId, encode, university);
    }

    @Override
    public Student findStudentByStuId(Integer stuId, String university) {
        return studentMapper.findStudentByStuId(stuId, university);
    }

    @Override
    public void update(Student student) {
        studentMapper.update(student);
    }

    @Override
    public ArrayList<Course> getCourses(String university) {
        return studentMapper.getCourses(university);
    }

    @Override
    public Integer selectCourse(SC sc) {
        Integer courseId = sc.getCourseId();
        Integer stuId = sc.getStuId();
        String university = sc.getUniversity();

        ArrayList<Course> hasChosen = getMyCourses(stuId, university);
        Course thisCourse = getOneCourse(courseId, university);

        int number=studentMapper.findSelected(courseId,university);//查询选课的数量，对比课程容量
        if(number==thisCourse.getVolume()){
            return -1;
        }
        for (Course course : hasChosen) {
            //遍历已经选的课程，对比选课时间，有冲突返回0
            if (course.getDate().equals(thisCourse.getDate()) && course.getTime().equals(thisCourse.getTime())) {
                return 0;
            }
        }
        studentMapper.selectCourse(sc);
        return 1;
    }

    @Override
    public ArrayList<Course> getMyCourses(Integer id, String university) {
        return studentMapper.getMyCourses(id, university);
    }

    @Override
    public void deleteCourse(Integer courseID, Integer id, String university) {
        studentMapper.deleteCourse(courseID, id, university);
    }

    @Override
    public Course getOneCourse(Integer id, String university) {
        return studentMapper.getOneCourse(id, university);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, Integer stuId, String name, String major, String college, String university, Integer className, Integer grand) {
        //设置分页参数
        PageHelper.startPage(page, pageSize);

        //执行查询
        List<StudentAdmin> empList = studentMapper.pageList(stuId, name, major, college, university, className, grand);

        for (StudentAdmin student : empList) {
            LocalDateTime loginTime = logMapper.getLoginTime(student.getStuId());
            student.setLoginTime(loginTime);
        }
        Page<StudentAdmin> p = (Page<StudentAdmin>) empList;

        //计算总页数
        Integer pageCount = (int)p.getTotal() % pageSize == 0 ? (int)p.getTotal() / pageSize : (int)p.getTotal() / pageSize + 1;

        //封装pageBean对象
        PageBean pageBean = new PageBean((int)p.getTotal(), p.getResult(), pageCount);
        return pageBean;
    }

    @Override
    public Integer findClassNumber(Integer stuId, String university) {
        return studentMapper.findClassNumber(stuId, university);
    }

    @Override
    public List<Score> findScore(Integer stuId, String university) {
        List<Score> scores = studentMapper.findScore(stuId, university);
        for (Score score : scores) {
            Course course = courseMapper.findCourseById(score.getCourseId(), university);
            String teacherName = teacherMapper.findTeacherById(course.getTeacherId(), university).getName();

            score.setCourseName(course.getCourseName());
            score.setTeacherName(teacherName);
            score.setCredit(course.getCredit());
        }
        return scores;
    }

    @Override
    public List<Schedule> scheduleResult(Integer stuId, String university) {
        ArrayList<Course> myCourses = studentMapper.getMyCourses(stuId, university);
        List<Schedule> schedules = new ArrayList<Schedule>();
        for (Course myCourse : myCourses) {
            Schedule schedule = new Schedule();
            schedule.setClassroom(myCourse.getClassroom());
            schedule.setCourseName(myCourse.getCourseName());
            schedule.setSchedule(ScheduleUtils.scheduleTable(myCourse.getDate(), myCourse.getTime()));
            schedule.setTeacherName(teacherMapper.findTeacherById(myCourse.getTeacherId(), university).getName());
            schedules.add(schedule);
        }
        return schedules;
    }

    @Override
    public List<Grade> gradeAnalysis(Integer courseID, String university) {
        return selectionMapper.gradeAnalysis(courseID,university);
    }

}
