package com.example.controller;


import com.example.anno.Login;
import com.example.anno.Logout;
import com.example.pojo.*;
import com.example.service.StudentService;
import com.example.service.TeacherService;
import com.example.service.UserService;
import com.example.utils.JwtUtils;
import com.example.utils.RedisCache;
import com.example.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private RedisCache redisCache;

    /**
     * 学生注册
     * @param student 学生信息
     */
    @PostMapping("/stuRegister")
    public Result<Student> register(@RequestBody Student student){

        log.info("学生注册信息：{}",student);
        Integer stuId = student.getStuId();
        String university = student.getUniversity();
        //查询用户
        Student user = studentService.findStudentByStuId(stuId, university);
        if(user == null){
            userService.register(student); //注册
            log.info("学生注册成功");
            return Result.success();
        } else {
            log.info("学生已存在，注册失败");
            return Result.error("用户已存在");
        }
    }

    /**
     * 教职工注册
     * @param teacher 教职工信息
     */
    @PostMapping("/teaRegister")
    public Result<Teacher> register(@RequestBody Teacher teacher) {
        log.info("教职工注册信息：{}",teacher);
        Integer staffId = teacher.getStaffId();

        Map<String, Object> map = ThreadLocalUtil.get();
        String university = (String) map.get("university");
        teacher.setUniversity(university);

        //查询用户
        Teacher user = teacherService.findTeacherByStaffId(staffId, university);
        if(user == null){
            userService.registerTeacher(teacher);
            log.info("教职工注册成功");
            return Result.success();
        } else {
            log.info("教职工已存在，注册失败");
            return Result.error("用户已存在");
        }
    }

    /**
     * 用户登录
     * @param user 用户登录信息
     */
    @Login
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody User user){
        log.info("用户登录：{}", user);
        Integer code = userService.login(user);

        //登录成功，生成令牌，下发令牌
        if (code == 1) {
            log.info("登录成功");
            Map<String, Object> claim = new HashMap<>();
            claim.put("id", user.getId());
            claim.put("university", user.getUniversity());
            claim.put("userType", user.getUserType());
            String jwt = JwtUtils.generateJwt(claim);

            //把token存储到redis中
            redisCache.setCacheObject(jwt, jwt,12, TimeUnit.HOURS);

            //创建登录响应实体
            Student student;
            Teacher teacher;
            LoginResponse loginResponse = new LoginResponse();
            if (user.getUserType() == 1){
                student = studentService.findStudentByStuId(user.getId(), user.getUniversity());
                loginResponse.setUsername(student.getName());
                loginResponse.setPermission(0);
            } else {
                teacher = teacherService.findTeacherByStaffId(user.getId(), user.getUniversity());
                loginResponse.setUsername(teacher.getName());
                loginResponse.setPermission(teacher.getPermission());
            }
            loginResponse.setUserType(user.getUserType());
            loginResponse.setLoggedIn(true);
            loginResponse.setUserId(user.getId());
            loginResponse.setToken(jwt);

            return Result.success(loginResponse);
        } else if (code == 2) {
            //登录失败，返回错误信息
            log.info("登录失败");
            return Result.error("用户名或密码出错！");
        } else {
            log.info("用户不存在");
            return Result.error("用户不存在");
        }
    }

    /**
     * 登录状态确认接口
     * @return LoginResponse对象
     */
    @GetMapping("/login/status")
    public Result<LoginResponse> getLoginStatus(){
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        String university = (String) map.get("university");
        Integer userType = (Integer) map.get("userType");

        //创建登录响应实体
        Student student;
        Teacher teacher;
        LoginResponse loginResponse = new LoginResponse();
        if (userType == 1){
            student = studentService.findStudentByStuId(id, university);
            loginResponse.setUsername(student.getName());
            loginResponse.setPermission(0);
        } else {
            teacher = teacherService.findTeacherByStaffId(id, university);
            loginResponse.setUsername(teacher.getName());
            loginResponse.setPermission(teacher.getPermission());
        }
        loginResponse.setUserType(userType);
        loginResponse.setLoggedIn(true);
        loginResponse.setUserId(id);

        return Result.success(loginResponse);
    }

    /**
     * 登出
     * @return LoginResponse对象
     */
    @Logout
    @GetMapping("/user/logout")
    public Result<LoginResponse> logout(){
        //创建登录响应实体
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserType(0);
        loginResponse.setLoggedIn(false);
        loginResponse.setUserId(-1);
        loginResponse.setPermission(0);
        loginResponse.setUsername("");

        return Result.success(loginResponse);
    }
}
