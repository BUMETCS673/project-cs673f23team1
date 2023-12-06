package com.aceteam.tm.rest.controller;

import com.aceteam.tm.rest.config.swagger.ApiVersion;
import com.aceteam.tm.rest.config.swagger.ApiVersionConstant;
import com.github.pagehelper.PageInfo;
import com.liang.manage.concern.facade.dto.notify.NotifyOutDTO;
import com.liang.manage.concern.facade.dto.notify.NotifySearchDTO;
import com.liang.manage.concern.facade.server.NotifyService;
import com.liang.nansheng.common.auth.UserContextUtils;
import com.liang.nansheng.common.auth.UserSsoDTO;
import com.liang.nansheng.common.web.basic.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
@Slf4j
@RestController
@RequestMapping("/bbs/notify/")
@Api(tags = "Notification Message Interface")
public class NotifyController {
    @Reference
    private NotifyService notifyService;

    /**
     * Add @Request Param, type must pass value
     *
     * @param type
     * @return
     */
    @PostMapping("haveRead")
    @ApiOperation(value = "All read")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> haveRead(Integer type) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(notifyService.haveRead(currentUser, type));
    }

    @PostMapping("markRead")
    @ApiOperation(value = "Mark as read")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> markRead(@RequestBody List<Integer> notifyIds) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(notifyService.markRead(notifyIds, currentUser));
    }

    @GetMapping("getList")
    @ApiOperation(value = "Get notification information in pages")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<PageInfo<NotifyOutDTO>> getList(NotifySearchDTO notifySearchDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(notifyService.getList(notifySearchDTO, currentUser));
    }

}
