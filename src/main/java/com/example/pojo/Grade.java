package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生数据分析的实体类
 * 返回成绩
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
    public Double averageScore;
    public Integer passRate;
}
