package com.aceteam.tm.rest.interceptor;

import com.liang.manage.auth.facade.dto.user.UserDTO;
import com.liang.manage.auth.facade.server.UserService;
import com.liang.nansheng.common.auth.UserContextUtils;
import com.liang.nansheng.common.auth.UserSsoDTO;
import com.liang.nansheng.common.constant.HeaderConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: some desc
 * @author: haoran
 */
@Slf4j
@Component
public class UserPictureInterceptor implements HandlerInterceptor {
    @Reference
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        if (currentUser != null) {
            UserDTO userDTO = userService.getById(currentUser.getUserId());
            response.addHeader(HeaderConstants.USER_PICTURE_HEADER, userDTO.getPicture());
        }
        return true;
    }

}
