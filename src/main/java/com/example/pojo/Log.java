package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    private LocalDateTime LoginTime;
    private Integer loginUser;
    private Integer userType;
    private Long costTime;
    private String loginStatus;
    private String university;
}
