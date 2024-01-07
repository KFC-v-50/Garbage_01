package com.example.pojo;

import com.example.controller.AdminController;
import com.example.controller.CourseController;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course extends AdminController {
    @NotNull
    private Integer courseId;
    @Pattern(regexp = "^\\S{1,10}$")
    private String courseName;
    private Integer teacherId;
    private String TeacherName;
    private String classroom;
    private Integer time;         //'1为早八 2为早十 3为下二 4为下四'
    private Integer date;         //'周一到周五'
    private String schedule;
    private String type;          //课程类型
    private String description;   //课程描述
    private Integer state;        //'1为开设'
    private String university;
    private String college;       //开课学院
    private Integer credit;       //学分
    private Integer volume;       //课程容量
    private Integer selected;     //已选课人数
}
