package com.aceteam.tm.rest.controller;

import com.aceteam.tm.rest.config.login.NoNeedLogin;
import com.aceteam.tm.rest.config.swagger.ApiVersion;
import com.aceteam.tm.rest.config.swagger.ApiVersionConstant;
import com.acteam.tm.user.facade.dto.DynamicDTO;
import com.acteam.tm.user.facade.server.DynamicService;
import com.github.pagehelper.PageInfo;
import com.liang.nansheng.common.web.basic.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: some desc
 * @author: haoran
 */
@Slf4j
@RestController
@RequestMapping("/bbs/dynamic/")
@Api(tags = "dynamic interface")
public class DynamicController {
    @Reference
    private DynamicService dynamicService;

    @NoNeedLogin
    @GetMapping("getList")
    @ApiOperation(value = "Get Dynamic")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<PageInfo<DynamicDTO>> getList(@RequestParam Long userId,
                                                        @RequestParam Integer currentPage,
                                                        @RequestParam Integer pageSize) {
        return ResponseResult.success(dynamicService.getByUserId(userId, currentPage, pageSize));
    }

}
