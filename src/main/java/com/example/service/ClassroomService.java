package com.example.service;

import com.example.pojo.Classroom;

import java.util.ArrayList;

public interface ClassroomService {


    int addClassroom(Classroom classroom);

    void updateClassroom(Classroom classroom);

    ArrayList<Classroom> SelectAllClassroom();

    void deleteClassroom(Classroom classroom);

    void applyChange(Integer id, String university, Integer courseID, String classroom);
}
