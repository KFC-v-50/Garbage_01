package com.example.service;

import com.example.pojo.PageBean;

public interface SelectionService {
    PageBean page(Integer page, Integer pageSize, String courseName, String studentName, String className, String university);
}
