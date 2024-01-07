package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课表信息实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    private String courseName;
    private String teacherName;
    private String classroom;
    private String schedule;
    private Integer date;
    private Integer time;
}
