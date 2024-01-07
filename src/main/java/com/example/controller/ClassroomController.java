package com.example.controller;

import com.example.pojo.Classroom;
import com.example.pojo.Result;
import com.example.service.ClassroomService;
import com.example.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    //新增教室的方法
    @PostMapping("/add")
    public Result addClassroom(@RequestBody Classroom classroom) {

        log.info("添加教室：{}", classroom.getClassroom());
        int bool = classroomService.addClassroom(classroom);
        if (bool == 0) {
            return Result.error("已存在该教室");
        }
        return Result.success();
    }

    //修改教室信息
    @PostMapping("/update")
    public Result updateClassroom(@RequestBody Classroom classroom) {

        log.info("修改教室信息：{}", classroom.getClassroom());
        classroomService.updateClassroom(classroom);
        return Result.success();
    }

    //查询教室
    @GetMapping("/find")
    public Result findClassroom() {
        log.info("查询教室信息");
        ArrayList<Classroom> classrooms = classroomService.SelectAllClassroom();
        return Result.success(classrooms);
    }

    @DeleteMapping("/delete")
    public Result deleteClassroom(@RequestBody Classroom classroom) {
        log.info("查询指定教室");
        classroomService.deleteClassroom(classroom);
        return Result.success();
    }

    @PostMapping("/change")
    public Result changeClassroom(@RequestBody Map<String, String> params){
        log.info("老师申请更换教室");
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        String university = (String) map.get("university");
        Integer courseID= Integer.valueOf(params.get("courseID"));
        String classroom= params.get("classroom");

        classroomService.applyChange(id,university,courseID,classroom);

        return Result.success();
    }
}
