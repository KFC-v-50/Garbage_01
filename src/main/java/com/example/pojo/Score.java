package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 操作成绩响应封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    private String courseName;
    private String teacherName;
    private String studentName;
    private Integer credit;
    private Integer courseId;
    private Integer stuId;
    private Integer className;
    private Integer score;      //总成绩
    private Integer ordinary;   //平时成绩
    private Integer ending;     //期末成绩
}
