package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @NotNull
    private Integer stuId; //学号
    @Pattern(regexp = "^\\S{1,10}$")
    private String name; //姓名
    private String major; //专业
    private String college; //学院
    private String university; //学校
    private Integer className; //班级
//    @JsonIgnore //让springmvc把当前对象转换成json字符串的时候，忽略password，最终的json字符串中就没password这个属性了
    private String password; //密码
    private Integer grand; //年级
}
