package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生课表封转类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SC {
    private Integer stuId;
    private Integer courseId;
    private Integer score;      //总成绩
    private String university;
    private Integer ordinary;   //平时成绩
    private Integer ending;     //期末成绩

    public SC(Integer id, Integer courseId, String university) {
        this.courseId = courseId;
        this.stuId = id;
        this.university = university;
    }
}
