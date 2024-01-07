package com.example.service;

import com.example.pojo.Student;
import com.example.pojo.Teacher;
import com.example.pojo.User;

public interface UserService {

    /**
     * 学生注册
     * @param student 学生信息
     */
    void register(Student student);

    /**
     *用户登录
     * @param user 用户信息
     * @return 返回登录状态码
     */
    Integer login(User user);

    /**
     * 教职工注册
     * @param teacher 教职工信息
     */
    void registerTeacher(Teacher teacher);
}
