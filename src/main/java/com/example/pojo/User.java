package com.example.pojo;

import com.example.controller.LoginController;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息登录实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends LoginController {
    @NotNull
    private Integer id;
    @NotEmpty
    @Pattern(regexp = "^\\S{1,18}$")
    private String password;
    @NotEmpty
    private String university;
    private Integer userType;// 1为学生，2为老师及其他，3为辅导员，4为管理员和教秘
}
