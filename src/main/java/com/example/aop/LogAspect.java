package com.example.aop;

import com.example.mapper.LogMapper;
import com.example.pojo.Log;
import com.example.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@Aspect
public class LogAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LogMapper logMapper;

    @Around("@annotation(com.example.anno.Logout)")
    public Object RecordLog(ProceedingJoinPoint joinPoint) throws Throwable {

        //登录时间
        LocalDateTime loginTime = LocalDateTime.now();

        //原始方法开始时间
        Long begin = System.currentTimeMillis();

        //登录人的id
        String jwt = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(jwt);
        Integer loginUser = (Integer) claims.get("id");
        Integer userType = (Integer) claims.get("userType");
        String university = (String) claims.get("university");

        //目标方法名
        String loginStatus = joinPoint.getSignature().getName();

        //调用原始目标方法运行
        Object result = joinPoint.proceed();

        //原始方法结束时间
        Long end = System.currentTimeMillis();

        //耗时
        Long costTime = end - begin;

        Log logs = new Log(loginTime, loginUser, userType, costTime, loginStatus, university);
        logMapper.insert(logs);

        log.info("AOP操作日志：{}",logs);

        return result;
    }
}
