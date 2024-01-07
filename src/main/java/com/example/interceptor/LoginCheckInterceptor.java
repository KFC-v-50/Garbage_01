package com.example.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.pojo.Result;
import com.example.utils.JwtUtils;
import com.example.utils.RedisCache;
import com.example.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Enumeration;
import java.util.Map;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisCache redisCache;

    @Override //目标资源方法执行前运行，返回true，放行；返回false，不放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equals("OPTIONS")) { // 如果是预检请求
            String headers = request.getHeader("Access-Control-Request-Headers"); // 获取请求头信息
            if (headers != null && headers.contains("token")) { // 检查请求头信息是否包含 Authorization
                response.setHeader("Access-Control-Allow-Headers", "token"); // 在响应中包含 Access-Control-Allow-Headers 头部信息，允许实际请求中包含 Authorization 请求头
                return true;
            }
        }

        // 1.获取请求的url
        String url = request.getRequestURL().toString();
        log.info("请求的url：{}", url);

        // 2.判断请求的url中是否包含login，如果包含，说明是登录操作，放行
        if (url.contains("login") && !url.contains("/login/status")) {
            log.info("登录操作，放行...");
            return true;
        } else if(url.contains("student/register")){
            log.info("学生注册操作，放行...");
            return true;
        }

        // 3.获取请求头中的令牌（token）
        String token = request.getHeader("token");
//        String token = request.getParameter("token");
        log.info("token:{}", token);

        //获取redis中的token
        Object redisToken = redisCache.getCacheObject(token);

        // 4.判断令牌是否存在，如果不存在，返回错误结果（未登录）
        if (!StringUtils.hasLength(token) || redisToken == null) {
            log.info("请求头token为空或失效了，返回未登录的信息");
            Result error = Result.error("NOT_LOGIN");
            // 手动转换 对象--JSON --------> 阿里巴巴fastJSON
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }

        // 5.解析token，如果解析失败，返回错误结果（未登录）
        try {
            Map<String, Object> claims = JwtUtils.parseJWT(token);

            //把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
        } catch (Exception e) { //jwt解析失败
            e.printStackTrace();
            log.info("解析令牌失败，返回为登录的错误信息");
            Result error = Result.error(401,"NOT_LOGIN");
            // 手动转换 对象--JSON --------> 阿里巴巴fastJSON
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }

        // 6.放行
        log.info("令牌合法，放行...");
        return true;
    }

    @Override //目标资源方法运行后运行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override //视图渲染完毕后运行，最后运行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        //清空ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
}
