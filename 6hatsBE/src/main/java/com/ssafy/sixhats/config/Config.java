package com.ssafy.sixhats.config;

import com.ssafy.sixhats.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://i7a709.p.ssafy.io" ,"http://localhost:8080", "http://localhost:8081")
                .allowedMethods("*")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/user/*")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user")
                .addPathPatterns("/board/*")
                .excludePathPatterns("/board/notice")
                .excludePathPatterns("/board/notice/*")
                .excludePathPatterns("/board/qna")
                .addPathPatterns("/comment/*")
                .addPathPatterns("/room")
                .addPathPatterns("/user_room")
                .addPathPatterns("/user_conference");
    }
}
