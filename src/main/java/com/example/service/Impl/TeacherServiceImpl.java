package com.example.service.Impl;

import com.example.mapper.CourseMapper;
import com.example.mapper.LogMapper;
import com.example.mapper.StudentMapper;
import com.example.mapper.TeacherMapper;
import com.example.pojo.*;
import com.example.pojo.Vo.TeacherAdmin;
import com.example.service.TeacherService;
import com.example.utils.BCryptPasswordUtils;
import com.example.utils.ExcelRead;
import com.example.utils.ScheduleUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private LogMapper logMapper;

    @Override
    public Teacher findTeacherByStaffId(Integer staffId, String university) {
        return teacherMapper.findTeacherById(staffId, university);
    }

    @Override
    public void updatePassword(Integer staffId, String password, String university) {
        log.info("对教职工要修改的密码进行进行哈希处理");
        String encode = BCryptPasswordUtils.encodePassword(password);
        teacherMapper.updatePassword(staffId, encode, university);
    }

    @Override
    public void update(Teacher teacher) {
        teacherMapper.update(teacher);
    }

    @Override
    public List<Integer> findTeacherByNameIds(String teacherName, String university) {
        return teacherMapper.findTeacherByNameIds(teacherName, university);
    }

    @Override
    public void addByExcel(InputStream inputStream) {
        ArrayList<Student> excel;
        //方法封装成utils
        ExcelRead excelRead=new ExcelRead();
        excel= excelRead.Read(inputStream);
        for(Student student:excel){
            studentMapper.addStudent(student);
        }
    }

    @Override
    public void addByExcel2(InputStream inputStream) {
        ArrayList<Teacher> excel;
        //方法封装成utils
        ExcelRead excelRead=new ExcelRead();
        excel= excelRead.Read2(inputStream);
        for(Teacher teacher:excel){
            teacherMapper.addTeacher(teacher);
        }
    }

    @Override
    public int GetPermission(Integer id, String university) {
        return teacherMapper.GetPermission(id,university);
    }

    @Override
    public void scoring(Integer stuId, String university, Integer courseId, Integer ordinary, Integer ending, Integer score) {
        teacherMapper.scoring(stuId, university, courseId,  ordinary, ending, score);
    }

    @Override
    public Integer checkScored(Integer stuId, String university, Integer courseId) {
        return teacherMapper.checkScored(stuId, university, courseId);
    }

    @Override
    public Integer findClassNumber(Integer id, String university) {
        return teacherMapper.findClassNumber(id, university);
    }

    @Override
    public PageBean findSelectedStudent(Integer page, Integer pageSize, String courseName, String studentName, Integer staffId, String university) {
        //根据courseName获取courseIds集合
        List<Integer> courseIds = courseMapper.findCourseByNameAndStaffIds(courseName, staffId, university);

        //根据studentName获取stuIds集合
        List<Integer> stuIds = studentMapper.findStudentByNameIds(studentName, university);

        //设置分页参数
        PageHelper.startPage(page, pageSize);

        //执行查询
        List<Score> empList = teacherMapper.findSelectedStudent(courseIds, stuIds, university);

        //查询每条数据对应的学生名字和课程名字
        for (Score score : empList) {
            String couName = courseMapper.findCourseById(score.getCourseId(), university).getCourseName();
            String stuName = studentMapper.findStudentByStuId(score.getStuId(), university).getName();
            score.setCourseName(couName);
            score.setStudentName(stuName);
        }

        Page<Score> p = (Page<Score>) empList;

        //计算总页数
        Integer pageCount = (int)p.getTotal() % pageSize == 0 ? (int)p.getTotal() / pageSize : (int)p.getTotal() / pageSize + 1;

        //封装pageBean对象
        PageBean pageBean = new PageBean((int)p.getTotal(), p.getResult(), pageCount);
        return pageBean;
    }

    @Override
    public SC scoreInfo(Integer courseId, Integer stuId, String university) {

        return teacherMapper.scoreInfo(courseId, stuId, university);
    }

    @Override
    public List<Schedule> scheduleResult(Integer teacherId, String university) {
        List<Schedule> schedules = teacherMapper.scheduleResult(teacherId, university);
        for (Schedule schedule : schedules) {
            String teacherName = teacherMapper.findTeacherById(teacherId, university).getName();
            schedule.setTeacherName(teacherName);
            schedule.setSchedule(ScheduleUtils.scheduleTable(schedule.getDate(), schedule.getTime()));
        }
        return schedules;
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String name, String college, String university, Integer staffId) {
        //设置分页参数
        PageHelper.startPage(page, pageSize);

        //执行查询
        List<TeacherAdmin> empList = teacherMapper.pageList(name, college, university,staffId);

        for (TeacherAdmin teacher : empList) {
            LocalDateTime loginTime = logMapper.getLoginTime(teacher.getStaffId());
            teacher.setLoginTime(loginTime);
        }
        Page<TeacherAdmin> p = (Page<TeacherAdmin>) empList;

        //计算总页数
        Integer pageCount = (int)p.getTotal() % pageSize == 0 ? (int)p.getTotal() / pageSize : (int)p.getTotal() / pageSize + 1;

        //封装pageBean对象
        PageBean pageBean = new PageBean((int)p.getTotal(), p.getResult(), pageCount);
        return pageBean;
    }


}
