package com.aceteam.tm.rest.controller;

import com.aceteam.tm.post.facade.dto.ResourceNavigateDTO;
import com.aceteam.tm.post.facade.dto.ResourceNavigateSearchDTO;
import com.aceteam.tm.post.facade.server.ResourceNavigateService;
import com.aceteam.tm.rest.config.login.NoNeedLogin;
import com.aceteam.tm.rest.config.swagger.ApiVersion;
import com.aceteam.tm.rest.config.swagger.ApiVersionConstant;
import com.aceteam.tm.rest.utils.FileLengthUtils;
import com.github.pagehelper.PageInfo;
import com.liang.nansheng.common.auth.UserContextUtils;
import com.liang.nansheng.common.auth.UserSsoDTO;
import com.liang.nansheng.common.enums.ResponseCode;
import com.liang.nansheng.common.utils.CommonUtils;
import com.liang.nansheng.common.web.basic.ResponseResult;
import com.liang.nansheng.common.web.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @description: some desc
 * @author: haoran
 */
@Slf4j
@RestController
@RequestMapping("/bbs/resource/")
@Api(tags = "Resource Navigation Interface")
public class ResourceController {
    @Reference
    private ResourceNavigateService resourceNavigateService;

    @Autowired
    private FileLengthUtils fileLengthUtils;

    @PostMapping("create")
    @ApiOperation(value = "New resource navigation")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> create(@RequestBody ResourceNavigateDTO resourceNavigateDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(resourceNavigateService.create(resourceNavigateDTO, currentUser));
    }

    @PostMapping("/uploadResourceLogo")
    @ApiOperation(value = "Upload resource navigation logo")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<String> uploadResourceLogo(@RequestParam(value = "logo", required = false) MultipartFile logo) throws IOException {
        if (fileLengthUtils.isFileNotTooBig(logo.getBytes())) {
            return ResponseResult.success(resourceNavigateService.uploadResourceNavigateLogo(logo.getBytes(), logo.getOriginalFilename()));
        } else {
            throw BusinessException.build(ResponseCode.EXCEED_THE_MAX, "请上传不超过 " +
                    CommonUtils.byteConversion(fileLengthUtils.getFileMaxLength()) + " 的图片!");
        }
    }

    @PostMapping("update")
    @ApiOperation(value = "Updated resource navigation")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> update(@RequestBody ResourceNavigateDTO resourceNavigateDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(resourceNavigateService.update(resourceNavigateDTO, currentUser));
    }

    @NoNeedLogin
    @GetMapping("getList")
    @ApiOperation(value = "Access to resources navigation")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<PageInfo<ResourceNavigateDTO>> getList(ResourceNavigateSearchDTO resourceNavigateSearchDTO) {
        return ResponseResult.success(resourceNavigateService.getList(resourceNavigateSearchDTO));
    }

    @NoNeedLogin
    @GetMapping("getCategorys")
    @ApiOperation(value = "Get resource navigation for all categories")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<List<String>> getCategorys() {
        return ResponseResult.success(resourceNavigateService.getCategorys());
    }

    @PostMapping("delete/{id}")
    @ApiOperation(value = "Delete resource navigation")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> delete(@PathVariable Integer id) {
        return ResponseResult.success(resourceNavigateService.delete(id));
    }

}
