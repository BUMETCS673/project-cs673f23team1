package com.aceteam.tm.rest.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: some desc
 * @author: haoran
 */
@Slf4j
@Component
public class RequestMonitor implements HandlerInterceptor {
    @Value("${interceptor.monitor.status}")
    private boolean monitorStatus;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!monitorStatus) {
            return true;
        }
        try {
            log.info("class:" + ((HandlerMethod) handler).getBean().getClass().getName());
            log.info("method:" + ((HandlerMethod) handler).getMethod().getName());
        } catch (Exception e) {
            log.warn("Time-consuming monitoring failure:{}", e.getMessage());
        }
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (monitorStatus) {
            Long start = (Long) request.getAttribute("startTime");
            log.info("Time-consuming:" + (System.currentTimeMillis() - start));
        }
    }
}

