package com.example.mapper;

import com.example.pojo.Grade;
import com.example.pojo.Score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SelectionMapper {

    List<Score> pageList(Integer page, Integer pageSize, List<Integer> courseIds, List<Integer> stuIds, String university);

    @Select("SELECT AVG(score) AS averageScore, ROUND(AVG(CASE WHEN score >= 60 THEN 1.0 ELSE 0 END) * 100) AS passRate FROM selection WHERE university = #{university} AND course_id = #{courseID} GROUP BY year ORDER BY year ASC;")
    List<Grade> gradeAnalysis(Integer courseID, String university);
}
