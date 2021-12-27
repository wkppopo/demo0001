package com.atguigu.demo0001.interceptor;

import com.atguigu.demo0001.utils.JwtUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 创建一个类实现 拦截器处理器 HandlerInterceptor 接口
 */
public class JwtInterceptor implements HandlerInterceptor {
    //预先拦截处理
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //String token = (String) request.getAttribute("token");
        String token = request.getParameter("token");
        System.out.println("获取拦截到的客户端的uri："+request.getRequestURI());

//        System.out.println("拦截到从前端发送过来的数据"+token);
        if(JwtUtils.verify(token)){
            return true;
        }
        return false;
    }
}
