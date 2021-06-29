package com.hikvision.jwt.config;

import com.hikvision.jwt.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author mengxiangding
 * @Copyright(C) 2021 杭州海康威视技术有限公司
 * @Description:
 * @create: 2021年06月25日 11:05:40
 */
@Configuration
public class JwtInterceptorConfig implements WebMvcConfigurer {

    @Bean
    public JwtInterceptor getJwtInterceptor() {
        return new JwtInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getJwtInterceptor()).addPathPatterns("/**").excludePathPatterns("/login");
    }
}
