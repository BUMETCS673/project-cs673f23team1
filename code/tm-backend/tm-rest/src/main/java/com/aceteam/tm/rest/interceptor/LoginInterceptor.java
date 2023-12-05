package com.aceteam.tm.rest.interceptor;

import com.aceteam.tm.rest.config.login.NoNeedLogin;
import com.aceteam.tm.rest.utils.HttpRequestUtils;
import com.liang.manage.auth.facade.server.UserService;
import com.liang.nansheng.common.auth.UserContextUtils;
import com.liang.nansheng.common.auth.UserSsoDTO;
import com.liang.nansheng.common.constant.AuthSystemConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @description: some desc
 * @author: haoran
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Reference
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserSsoDTO currentUser = null;
        // Get info of cookie
        Cookie ssoAccount = HttpRequestUtils.findCookie(request, AuthSystemConstants.NS_ACCOUNT_SSO_COOKIE);

        // Login from sso with username and password
        if (ssoAccount != null) {
            currentUser = sso(request, ssoAccount);
        }

        // User context setting to add user information (thread variable)
        UserContextUtils.setCurrentUser(currentUser);

        // No login required
        if (handler instanceof HandlerMethod) {
            // Annotations on methods
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            NoNeedLogin methodAnnotation = handlerMethod.getMethodAnnotation(NoNeedLogin.class);
            if (Objects.nonNull(methodAnnotation)) {
                return true;
            }
            // Annotations on classes
            Class<?> clazz = handlerMethod.getBeanType();
            if (Objects.nonNull(AnnotationUtils.findAnnotation(clazz, NoNeedLogin.class))) {
                return true;
            }
        }

        // Fail to get user information, jump to login page
        if (currentUser == null) {
            // Get the current page address
            String referer = request.getHeader("referer");
            if (referer.contains("?")) {
                referer = referer.substring(0, referer.indexOf("?"));
            }
            String redirect = userService.innerLoginUrl(referer);
            log.info("Login Jump[{}]", redirect);
            HttpRequestUtils.redirect(request, response, redirect);
            return false;
        }
        return true;
    }

    /**
     * sso authentication processing, get login user information
     *
     * @param request
     * @return
     * @throws IOException
     */
    private UserSsoDTO sso(HttpServletRequest request, Cookie cookie) throws Exception {
        String token = cookie.getValue();
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        // Verify that the Token is correct
        if (!userService.verifyToken(token)) {
            return null;
        }
        // token has expired
        if (userService.isExpired(token)) {
            return null;
        }

        // Get user information via token
        return userService.getUserSsoDTOByToken(token);
    }

}

