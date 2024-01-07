package com.example.controller;

import com.example.pojo.*;
import com.example.service.StudentService;
import com.example.service.TeacherService;
import com.example.utils.BCryptPasswordUtils;
import com.example.utils.RedisCache;
import com.example.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private RedisCache redisCache;

    @GetMapping
    public Result<Student> findStudentByStuId(){
        Map<String, Object> map = ThreadLocalUtil.get();
        String university = (String) map.get("university");
        Integer stuId = (Integer) map.get("id");

        Student student = studentService.findStudentByStuId(stuId, university);
        Integer classNumber = studentService.findClassNumber(stuId, university);
        student.setClassName(classNumber);

        return Result.success(student);
    }

    /**
     * 更新学生账号密码
     *
     * @param params 新旧密码
     * @param token  jwt令牌
     */
    @PatchMapping("/updatePwd")
    public Result updatePassword(@RequestBody Map<String, String> params, @RequestHeader("token") String token) {
        //1. 校验参数
        String oldPwd = params.get("oldPwd");
        String rePwd = params.get("rePwd");
        String newPwd = params.get("newPwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(rePwd) || !StringUtils.hasLength(newPwd)) {
            return Result.error("缺少必要参数");
        }

        //原密码是否正确
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        String university = (String) map.get("university");
        Student student = studentService.findStudentByStuId(id, university);
        if (!BCryptPasswordUtils.matchPassword(oldPwd, student.getPassword())) {
            log.info("原密码填写错误");
            return Result.error("原密码填写错误");
        }

        //newPwd和rePwd是否相同
        if (!newPwd.equals(rePwd)) {
            log.info("两次填写的密码不一样");
            return Result.error("两次填写的密码不一样");
        }
        //2. 调用service完成密码更新
        studentService.updatePassword(id, newPwd, university);

        //删除redis中对应的token
        redisCache.deleteObject(token);

        return Result.success();
    }

    /**
     * 更新学生信息
     *
     * @param student 要更新的学生信息
     */
    @PutMapping("/update")
    public Result<Student> update(@RequestBody @Validated Student student) {
        Map<String, Object> map = ThreadLocalUtil.get();
        student.setUniversity((String) map.get("university"));
        log.info("学生更新信息：{}", student);
        studentService.update(student);
        return Result.success();
    }

    /**
     * 学生信息分页条件查询
     *
     * @param page      第几页
     * @param pageSize  每页展示数量
     * @param stuId     学生学号
     * @param name      学生名字
     * @param major     专业
     * @param college   学院
     * @param className 班级
     * @param grand     年级
     * @return 学生信息列表
     */
    @GetMapping("/page")
    public Result<PageBean> page(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 Integer stuId, String name, String major, String college,
                                 Integer className, Integer grand) {
        log.info("分页查询：参数：{},{},{},{},{},{},{},{}", page, pageSize, stuId, name, major, college, className, grand);
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        String university = (String) map.get("university");
        Integer userType = (Integer) map.get("userType");

        //判断用户是否为学生
        if (userType == 1) {
            return Result.error("学生用户权限不够");
        }

        Teacher teacher = teacherService.findTeacherByStaffId(id, university);

        Integer classNumber = teacherService.findClassNumber(id, university);
        log.info("班级或年级号:{}", classNumber);

        //用户为班主任（固定班级）
        if (userType == 2) {
            //验证是否真为班主任
            if (teacher.getPermission() == 1) {
                return Result.error("暂无班级信息");
            }

            log.info("班主任查询学生信息");
            //年级参数清空，防止额外参数注入
            grand = null;

            //寻找班主任管理的班级号
            className = classNumber;
            college = teacher.getCollege();
        }

        //用户为辅导员（固定年级，年级下的班级任选）
        if (userType == 3) {
            //验证是否真为辅导员
            if (teacher.getPermission() != 3) {
                return Result.error("不是辅导员");
            }

            log.info("辅导员查询学生信息");
            //寻找辅导员管理的年级
            grand = classNumber;
            college = teacher.getCollege();
        }

        //用户为教秘（固定学院,学院下的年级和班级任选）
        if (userType == 4 && teacher.getPermission() == 2) {
            log.info("教秘查询学生信息");
            //寻找教秘管理的学院
            college = teacher.getCollege();
        } else if (userType == 4 && teacher.getPermission() == 5) {
            log.info("管理员查询学生信息");
        }

        PageBean pageBean = studentService.page(page, pageSize, stuId, name, major, college, university, className, grand);
        return Result.success(pageBean);
    }

    /**
     * 选择课，更新课表
     *
     * @param request - courseId 课程编号
     */
    @PostMapping("/selectCourse")
    public Result selectCourse(@RequestBody Map<String, Object> request) {

        //从request中获取courseId
        Integer courseId = (Integer) request.get("courseId");
        log.info("选课id传进：{}", courseId);

        //从线程获取用户id和学校
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        String university = (String) map.get("university");

        //对选课信息操作
        SC sc = new SC(id, courseId, university);
        Integer i = studentService.selectCourse(sc);
        if (i == 0) {
            return Result.error("时间冲突");
        } else if (i == -1) {
            return Result.error("课程无剩余容量");
        }
        return Result.success();
    }

    /**
     * 根据id返回课表
     */
    @GetMapping("/getMyCourses")
    public Result getMyCourses() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        String university = (String) map.get("university");
        ArrayList<Course> courses;
        courses = studentService.getMyCourses(id, university);
        return Result.success(courses);
    }

    /**
     * 退课
     *
     * @param courseId 课程id
     */
    @DeleteMapping("/deleteCourse")
    public Result deleteCourse(@RequestBody Integer courseId) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        String university = (String) map.get("university");
        studentService.deleteCourse(courseId, id, university);
        return Result.success();
    }

    /**
     * 学生查询课程分数
     * @return 分数封装信息
     */
    @GetMapping("/searchScore")
    public Result<List<Score>> searchScore(){
        log.info("学生查询课程分数");

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer stuId = (Integer) map.get("id");
        String university = (String) map.get("university");

        List<Score> scores = studentService.findScore(stuId, university);
        return Result.success(scores);
    }

    /**
     * 学生课表查询
     * @return
     */
    @GetMapping("/schedule")
    public Result schedule(){
        log.info("学生获取课表信息");
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer stuId = (Integer) map.get("id");
        String university = (String) map.get("university");
        List<Schedule> schedules = studentService.scheduleResult(stuId, university);
        return Result.success(schedules);
    }

    /**
     * 根据stuId获取学生信息
     */
    @GetMapping("/{id}")
    public Result<Student> getByStuId(@PathVariable(value = "id") Integer stuId){
        log.info("根据学生id查询学生信息：{}",stuId);

        Map<String, Object> map = ThreadLocalUtil.get();
        String university = (String) map.get("university");

        Student student = studentService.findStudentByStuId(stuId, university);

        return Result.success(student);
    }

    @GetMapping("/gradeAnalysis")
    public Result<Object> gradeAnalysis(@RequestParam Integer courseId){
        log.info("成绩分析");

        Map<String, Object> map = ThreadLocalUtil.get();
        String university = (String) map.get("university");

        List<Grade>grades=studentService.gradeAnalysis(courseId,university);
        return Result.success(grades);
    }

}
