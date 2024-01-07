package com.example.service;

import com.example.pojo.Apply;
import com.example.pojo.PageBean;

import java.time.LocalDateTime;
import java.util.List;

public interface ApplyService {

    /**
     * 寻找是否存在相同缓考申请表
     * @param stuId 学号
     * @param courseId 课程编号
     * @param university 大学
     * @param year 学年
     * @return 申请表信息
     */
    Apply findApplyById(Integer stuId, Integer courseId, String university, String year);

    /**
     * 添加缓考申请表
     * @param apply 申请表信息
     */
    void addApply(Apply apply);

    /**
     * 分页查询缓考申请表
     *
     * @param page       页码
     * @param pageSize   每页数据量
     * @param begin      申请表创建时间范围（开始时间）
     * @param end        申请表创建时间范围（结束时间）
     * @param year       学年
     * @param trimesters 学期
     * @param courseIds  课程编号集合
     * @param university 大学
     * @param staffId
     * @return 页面封装数据
     */
    PageBean page(Integer page, Integer pageSize, LocalDateTime begin, LocalDateTime end, String year, Integer trimesters,
                  List<Integer> courseIds, String university, Integer stuId, Integer staffId);

    /**
     * 更新缓考申请表
     * @param apply 申请表信息
     * @return 更新后的申请表信息
     */
    void updateApply(Apply apply);

    /**
     * 删除缓考申请表
     * @param courseId 课程编号
     * @param year 学年
     * @param stuId 学号
     * @param university 学校
     */
    void deleteApply(Integer courseId, String year, Integer stuId, String university);

    /**
     * 撤销或重新缓考申请表
     * @param courseId 课程编号
     * @param year 学年
     * @param stuId 学号
     * @param university 学校
     */
    void changeSubmit(Integer courseId, String year, Integer stuId, String university);

    /**
     * 审核缓考申请表
     * @param courseId 课程编号
     * @param year 学年
     * @param stuId 学号
     * @param university 学校
     * @param situation 审核信号量
     */
    void audit(Integer courseId, String year, Integer situation, String university, Integer stuId);
}
