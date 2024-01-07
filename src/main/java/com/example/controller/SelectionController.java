package com.example.controller;

import com.example.pojo.PageBean;
import com.example.pojo.Result;
import com.example.service.SelectionService;
import com.example.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/selection")
public class SelectionController {

    @Autowired
    private SelectionService selectionService;

    @GetMapping("/page")
    public Result<PageBean> page(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 String courseName, String studentName, String className){
        log.info("学生选课分页查询：{}，{}，{}，{}，{}",page,pageSize,courseName,studentName,className);
        Map<String, Object> map = ThreadLocalUtil.get();
        String university = (String) map.get("university");
        Integer id = (Integer) map.get("id");

        PageBean pageBean = selectionService.page(page,pageSize,courseName,studentName,className,university);
        return Result.success(pageBean);
    }
}
