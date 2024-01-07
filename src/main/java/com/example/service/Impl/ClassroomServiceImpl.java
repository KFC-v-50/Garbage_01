package com.example.service.Impl;

import com.example.mapper.ClassroomApplyMapper;
import com.example.mapper.ClassroomMapper;
import com.example.pojo.Classroom;
import com.example.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    @Autowired
    private ClassroomMapper classroomMapper;
    @Autowired
    private ClassroomApplyMapper classroomApplyMapper;

    @Override
    public int addClassroom(Classroom classroom) {
        int bool=findClassroom(classroom.getClassroom(),classroom.getUniversity());
        if(bool==0) {
            return 0;
        }
        classroomMapper.addClassroom(classroom);
        return 1;
    }

    @Override
    public void updateClassroom(Classroom classroom) {
        classroomMapper.updateClassroom(classroom);
    }

    @Override
    public ArrayList<Classroom> SelectAllClassroom() {
        return classroomMapper.SelectAllClassroom();
    }

    @Override
    public void deleteClassroom(Classroom classroom) {
        classroomMapper.deleteClassroom(classroom);
    }

    @Override
    public void applyChange(Integer id, String university, Integer courseID, String classroom) {
        classroomApplyMapper.applyChange(id,university,courseID,classroom);
    }

    public int findClassroom(String classroom,String university){
        Classroom classroom1=classroomMapper.findClassroom(classroom,university);
        if(classroom1!=null){
            return 0;
        }
        return 1;
    }
}
