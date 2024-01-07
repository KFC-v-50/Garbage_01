package com.example.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 教职工信息实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @NotNull
    private Integer staffId;
    private String name;
    private Integer className;
    private Integer permission;//权限 1为教师 2为教秘 3为辅导员 4为班主任 5为管理员
    private String password;
    private String university; //学校
    private String college; //学院
}
