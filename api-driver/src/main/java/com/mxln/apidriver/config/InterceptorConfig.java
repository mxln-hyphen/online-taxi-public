package com.mxln.apidriver.config;

import com.mxln.apidriver.Interceptor.JWTInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public JWTInterceptor jwtInterceptor(){
        return new JWTInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // registry.addInterceptor(jwtInterceptor())
        //         .addPathPatterns("/**")
        //         .excludePathPatterns("/verification-code")
        //         .excludePathPatterns("/verification-code-check")
        //         .excludePathPatterns("/refresh-jwt")
        //         .excludePathPatterns("/test");
    }
}
