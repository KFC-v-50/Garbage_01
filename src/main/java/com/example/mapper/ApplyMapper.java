package com.example.mapper;

import com.example.pojo.Apply;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ApplyMapper {

    /**
     * 寻找是否存在相同地缓考申请表
     * @param stuId 学号
     * @param courseId 课程编号
     * @param university 大学
     * @param year 学年
     * @return 申请表信息
     */
    @Select("select * from apply where stu_id=#{stuId} and course_id=#{courseId} and " +
            "university=#{university} and year=#{year}")
    Apply findApplyById(Integer stuId, Integer courseId, String university, String year);

    /**
     * 添加缓考申请表
     * @param apply 申请表信息
     */
    @Insert("insert into apply(stu_id, course_id, situation, administrator, reason, university, year, trimesters, create_time, update_time, submit) " +
            "values(#{stuId},#{courseId},#{situation},#{administrator},#{reason},#{university},#{year},#{trimesters},#{createTime},#{updateTime}, #{submit})")
    void addApply(Apply apply);

    /**
     * 分页查询缓考申请表
     *
     * @param begin      申请表创建时间范围（开始时间）
     * @param end        申请表创建时间范围（结束时间）
     * @param year       学年
     * @param trimesters 学期
     * @param courseIds  课程编号集合
     * @param university 大学
     * @param stuId      学号
     * @param staffId
     * @return 页面封装数据
     */
    List<Apply> pageList(LocalDateTime begin, LocalDateTime end, String year, Integer trimesters, List<Integer> courseIds,
                         String university, Integer stuId, Integer staffId);

    /**
     * 更新缓考申请表
     * @param apply 申请表信息
     */
    @Update("update apply set submit=1, reason=#{reason}, administrator=#{administrator}, update_time=#{updateTime} " +
            "where stu_id=#{stuId} and course_id=#{courseId} and university=#{university} and year=#{year}")
    void updateApply(Apply apply);

    /**
     * 删除缓考申请表
     * @param courseId 课程编号
     * @param year 学年
     * @param stuId 学号
     * @param university 学校
     */
    @Delete("delete from apply where course_id=#{courseId} and stu_id=#{stuId} and year=#{year} and university=#{university}")
    void deleteApply(Integer courseId, String year, Integer stuId, String university);

    /**
     * 撤销或重新提交缓考申请表
     * @param courseId 课程编号
     * @param year 学年
     * @param stuId 学号
     * @param university 学校
     */
    @Update("update apply set submit=0 where stu_id=#{stuId} and course_id=#{courseId} and year=#{year} and university=#{university}")
    void changeSubmit(Integer courseId, String year, Integer stuId, String university);

    /**
     * 审核缓考申请表
     * @param courseId 课程编号
     * @param year 学年
     * @param stuId 学号
     * @param university 学校
     * @param situation 审核信号量
     */
    @Update("update apply set situation=#{situation} where stu_id=#{stuId} and course_id=#{courseId} and year=#{year} and university=#{university}")
    void audit(Integer courseId, String year, Integer situation, String university, Integer stuId);
}
