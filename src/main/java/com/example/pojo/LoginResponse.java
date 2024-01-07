package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录状态响应封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private Boolean loggedIn;
    private Integer userId;
    private String username;
    private Integer userType;
    private Integer permission;
    private String token;
}
