package com.aceteam.tm.rest.config;

import com.aceteam.tm.rest.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description:
 * @author: haoran
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * Interfaces that do not require login
     */
    private static final String[] NOT_LOGIN_URLS = {
            "/swagger-resources/**",
            "/webjars/**",
            "/v2/**",
            "/doc.html/**",
            "/favicon.ico/**",
            "/error/**"
    };

    @Autowired
    private RequestMonitor requestMonitor;

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private UrlAccessCheckInterceptor urlAccessCheckInterceptor;

    @Autowired
    private UserPictureInterceptor userPictureInterceptor;

    @Autowired
    private VisitInterceptor visitInterceptor;

    @Autowired
    private NotifyInterceptor notifyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Monitor request elapsed time and other information interceptor
        registry.addInterceptor(requestMonitor)
                .addPathPatterns("/**")
                .excludePathPatterns(NOT_LOGIN_URLS);

        // User validation interceptor
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(NOT_LOGIN_URLS);

        // Backend path-level access control
        registry.addInterceptor(urlAccessCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(NOT_LOGIN_URLS);

        // User avatar interceptor
        registry.addInterceptor(userPictureInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(NOT_LOGIN_URLS);

        // Access record interceptor
        registry.addInterceptor(visitInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(NOT_LOGIN_URLS);

        // Message notification interceptor
        registry.addInterceptor(notifyInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(NOT_LOGIN_URLS);

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // doc.html file, will find resources in the path configured below
        registry.addResourceHandler("doc.html")
                // Open Resources
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
