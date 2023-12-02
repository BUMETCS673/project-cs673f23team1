package com.aceteam.tm.rest.interceptor;

import com.aceteam.tm.rest.utils.IpUtil;
import com.liang.manage.concern.facade.dto.visit.VisitDTO;
import com.liang.manage.concern.facade.server.VisitService;
import com.liang.nansheng.common.enums.ProjectEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: User Avatar Interceptor
 * @author: haoran
 */
@Slf4j
@Component
public class VisitInterceptor implements HandlerInterceptor {
    @Reference
    VisitService visitService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            VisitDTO visitDTO = new VisitDTO();
            // Which system the access comes from
            visitDTO.setProjectId(ProjectEnum.NS_BBS.getCode());
            // ip
            visitDTO.setIp(IpUtil.getIP(request));
            // OS
            visitDTO.setOs(IpUtil.getOS(request));
            visitService.create(visitDTO);
        } catch (Exception e) {
            log.error("VisitInterceptor Exception：", e);
        }

        return true;
    }

}
