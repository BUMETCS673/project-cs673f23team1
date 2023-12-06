package com.aceteam.tm.rest.controller;

import com.aceteam.tm.post.facade.dto.LabelDTO;
import com.aceteam.tm.post.facade.dto.LabelSearchDTO;
import com.aceteam.tm.post.facade.server.LabelService;
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

/**
 * @description: some desc
 * @author: haoran
 */
@Slf4j
@RestController
@RequestMapping("/bbs/label/")
@Api(tags = "label interface")
public class LabelController {
    @Reference
    private LabelService labelService;

    @Autowired
    private FileLengthUtils fileLengthUtils;

    @PostMapping("create")
    @ApiOperation(value = "Add Tags")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> create(@RequestBody LabelDTO labelDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(labelService.create(labelDTO, currentUser));
    }

    @PostMapping("/uploadLabelLogo")
    @ApiOperation(value = "Upload label logo")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<String> uploadLabelLogo(@RequestParam(value = "logo", required = false) MultipartFile logo) throws IOException {
        if (fileLengthUtils.isFileNotTooBig(logo.getBytes())) {
            return ResponseResult.success(labelService.uploadLabelLogo(logo.getBytes(), logo.getOriginalFilename()));
        } else {
            throw BusinessException.build(ResponseCode.EXCEED_THE_MAX, "请上传不超过 " +
                    CommonUtils.byteConversion(fileLengthUtils.getFileMaxLength()) + " 的图片!");
        }
    }

    @PostMapping("update")
    @ApiOperation(value = "Update Tags")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> update(@RequestBody LabelDTO labelDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(labelService.update(labelDTO, currentUser));
    }

    @NoNeedLogin
    @GetMapping("getList")
    @ApiOperation(value = "Get Tags")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<PageInfo<LabelDTO>> getList(LabelSearchDTO labelSearchDTO) {
        return ResponseResult.success(labelService.getList(labelSearchDTO));
    }

    @PostMapping("delete/{id}")
    @ApiOperation(value = "Delete Tags")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> delete(@PathVariable Integer id) {
        return ResponseResult.success(labelService.delete(id));
    }

}
