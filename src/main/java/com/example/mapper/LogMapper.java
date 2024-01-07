package com.example.mapper;

import com.example.pojo.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface LogMapper {

    @Insert("insert into log(login_time, login_user, user_type, cost_time, login_status, university) " +
            "values(#{loginTime},#{loginUser}, #{userType}, #{costTime}, #{loginStatus}, #{university}) ")
    void insert(Log log);

    @Select("select MAX(login_time) from log where login_user=#{id} and login_status='login'")
    LocalDateTime getLoginTime(Integer id);
}
