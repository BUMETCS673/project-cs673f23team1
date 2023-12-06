package com.aceteam.tm.rest.controller;

import com.aceteam.tm.post.facade.dto.SlideshowDTO;
import com.aceteam.tm.post.facade.server.SlideshowService;
import com.aceteam.tm.rest.config.login.NoNeedLogin;
import com.aceteam.tm.rest.config.swagger.ApiVersion;
import com.aceteam.tm.rest.config.swagger.ApiVersionConstant;
import com.liang.nansheng.common.web.basic.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
@Slf4j
@RestController
@RequestMapping("/bbs/carousel/")
@Api(tags = "Slideshow chart interface")
public class SlideshowController {
    @Reference
    private SlideshowService slideshowService;

    @NoNeedLogin
    @GetMapping("getList")
    @ApiOperation(value = "Get Rotating Chart")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<List<SlideshowDTO>> getList() {
        return ResponseResult.success(slideshowService.getList());
    }

}