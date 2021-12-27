package com.atguigu.demo0001.config;

import com.atguigu.demo0001.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfigurer
 * web的所有配置都可以在这里面完成 取代了以往的 web.xml文件
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) { //添加拦截器
        registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/param").excludePathPatterns("/test");
    }
}
