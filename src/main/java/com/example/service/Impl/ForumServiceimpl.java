package com.example.service.Impl;

import com.example.mapper.ForumMapper;
import com.example.pojo.Comment;
import com.example.pojo.Student;
import com.example.pojo.Teacher;
import com.example.service.ForumService;
import com.example.service.StudentService;
import com.example.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class ForumServiceimpl implements ForumService {
    @Autowired
    private ForumMapper forumMapper;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @Override
    public void postcomment(Comment comment) {
        if (comment.getUserType() == 1) {
            Student student = studentService.findStudentByStuId(comment.getId(), comment.getUniversity());
            comment.setName(student.getName());
        } else {
            Teacher teacher=teacherService.findTeacherByStaffId(comment.getId(), comment.getUniversity());
            comment.setName(teacher.getName());
        }
        forumMapper.postcomment(comment);
    }

    @Override
    public ArrayList<Comment> findAllComment() {
        return forumMapper.findAllComment();
    }
}
