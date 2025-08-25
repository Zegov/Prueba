package com.sermaluc.prueba.apiuser.prueba.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sermaluc.prueba.apiuser.prueba.security.TokenAccess;


@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    private TokenAccess tokenFilter;

    public SecurityConfig(TokenAccess tokenFilter) {
        this.tokenFilter = tokenFilter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenFilter);
    }
}
