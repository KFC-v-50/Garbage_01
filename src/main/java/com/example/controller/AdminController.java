package com.example.controller;

import com.example.pojo.ClassroomApply;
import com.example.pojo.Course;
import com.example.pojo.Result;
import com.example.service.AdminService;
import com.example.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * 管理员特有功能控制层
 */
@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 重置分数
     *
     * @param request 请求体
     * @return
     */
    @PutMapping("/resetScore")
    public Result resetScore(@RequestBody Map<String, Object> request) {

        //从request中获取信息
        Integer stuId = (Integer) request.get("stuId");
        Integer courseId = (Integer) request.get("courseId");

        //判断传入参数是否为空
        if (stuId == null || courseId == null) {
            return Result.error("传入的参数有空");
        }

        //从ThreadLocal中获取信息
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userType = (Integer) map.get("userType");
        String university = (String) map.get("university");

        //判断用户是否为管理员
        if (userType != 4) {
            return Result.error("此操作需要管理员权限");
        }

        adminService.resetScore(stuId, courseId, university);

        return Result.success();
    }

    /**
     * 教秘给学生加课
     *
     * @param info
     * @return
     */
    @PostMapping("/addClassToStu")
    public Result addClassToStu(@RequestBody Map<String, Object> info) {
        log.info("给学生加课");
        Integer stuId = (Integer) info.get("stuId");
        Integer courseId = (Integer) info.get("courseId");
        String university = (String) info.get("university");

        //从ThreadLocal中获取信息
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userType = (Integer) map.get("userType");

        if (userType != 4) {
            return Result.error("此操作需要教秘以上权限");
        }

        int bool = adminService.addClassToStu(stuId, courseId, university);
        if (bool == 0) {
            return Result.error("时间冲突");
        }
        return Result.success();
    }

    /**
     * 修改课程信息：时间 教室等
     *
     * @param course
     * @return
     */
    @PutMapping("/resetClass")
    public Result resetClass(@RequestBody Course course) {
        log.info("修改课程信息");

        //从ThreadLocal中获取信息
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userType = (Integer) map.get("userType");
        String university = (String) map.get("university");
        course.setUniversity(university);

        //判断用户是否为管理员
        if (userType != 4) {
            return Result.error("此操作需要管理员/教秘权限");
        }

        adminService.resetClass(course);

        return Result.success();
    }

    /**
     * 删除学生信息
     */
    @DeleteMapping("/student/{stuId}")
    public Result deleteStudent(@PathVariable(value = "stuId") Integer stuId) {
        log.info("管理员删除学生信息:{}", stuId);

        Map<String, Object> map = ThreadLocalUtil.get();
        String university = (String) map.get("university");

        adminService.deleteStudent(stuId, university);

        return Result.success();
    }

    /**
     * 删除老师信息
     *
     * @param staffId
     * @return
     */
    @DeleteMapping("/teacher/{id}")
    public Result<Object> deleteTeacher(@PathVariable(value = "id") Integer staffId) {
        log.info("管理员删除老师信息：{}", staffId);

        Map<String, Object> map = ThreadLocalUtil.get();
        String university = (String) map.get("university");

        adminService.deleteTeacher(staffId, university);

        return Result.success();
    }

    @DeleteMapping("/course/{id}")
    public Result<Object> deleteCourse(@PathVariable(value = "id") Integer courseId) {
        log.info("管理员删除课程信息：{}", courseId);

        Map<String, Object> map = ThreadLocalUtil.get();
        String university = (String) map.get("university");

        adminService.deleteCourse(courseId, university);

        return Result.success();
    }

    @GetMapping("/classroomApply")
    public Result<Object> classroomApply() {
        log.info("获取变更教室的申请");
        ArrayList<ClassroomApply>classroomApplies=adminService.classroomApply();

        return Result.success(classroomApplies);
    }

    @PostMapping("/classroomApplyconfirm")
    public Result<Object> classroomApplyconfirm(@RequestBody Map<String,Integer> params){
        Integer id=params.get("applicationId");
        adminService.classroomApplyconfirm(id);

        return Result.success();
    }


}
