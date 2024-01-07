package com.example.pojo;

import com.example.controller.AdminController;
import com.example.controller.ClassroomController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Classroom extends AdminController {
    private String classroom;
    private String type;//教室类型
    private String university;//学校
}
