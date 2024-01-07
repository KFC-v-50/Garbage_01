package com.example.aop;

import com.example.mapper.LogMapper;
import com.example.pojo.Log;
import com.example.pojo.LoginResponse;
import com.example.pojo.Result;
import com.example.utils.JwtUtils;
import io.jsonwebtoken.Claims;
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
public class LoginAspect {

    @Autowired
    private LogMapper logMapper;

    @Around("@annotation(com.example.anno.Login)")
    public Object RecordLogin(ProceedingJoinPoint joinPoint) throws Throwable {

        //登录时间
        LocalDateTime loginTime = LocalDateTime.now();

        //原始方法开始时间
        Long begin = System.currentTimeMillis();

        //目标方法名
        String loginStatus = joinPoint.getSignature().getName();

        //调用原始目标方法运行
        Object result = joinPoint.proceed();

        //原始方法结束时间
        Long end = System.currentTimeMillis();

        String token = "";
        // 判断返回值类型
        if (result instanceof Result<?>) {
            Result<LoginResponse> myResponse = (Result<LoginResponse>) result;

            // 获取 UserData 对象
            LoginResponse userData = myResponse.getData();

            // 获取 token
            token = userData.getToken();
        }

        Claims claims = JwtUtils.parseJWT(token);
        Integer loginUser = (Integer) claims.get("id");
        Integer userType = (Integer) claims.get("userType");
        String university = (String) claims.get("university");

        //耗时
        Long costTime = end - begin;

        Log logs = new Log(loginTime, loginUser, userType, costTime, loginStatus, university);
        logMapper.insert(logs);

        log.info("AOP操作日志：{}",logs);

        return result;
    }
}
