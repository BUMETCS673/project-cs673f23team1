package com.aceteam.tm.rest.controller;


import com.aceteam.tm.post.facade.dto.CommentDTO;
import com.aceteam.tm.post.facade.dto.CommentSearchDTO;
import com.aceteam.tm.post.facade.server.CommentService;
import com.aceteam.tm.rest.config.login.NoNeedLogin;
import com.aceteam.tm.rest.config.swagger.ApiVersion;
import com.aceteam.tm.rest.config.swagger.ApiVersionConstant;
import com.github.pagehelper.PageInfo;
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
 * @description:
 * @author: haoran
 */
@Slf4j
@RestController
@RequestMapping("/bbs/comment/")
@Api(tags = "comment interface")
public class CommentController {
    @Reference
    private CommentService commentService;

    @NoNeedLogin
    @GetMapping("getCommentByArticleId")
    @ApiOperation(value = "Getting information about comments on articles")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<List<CommentDTO>> getCommentByArticleId(CommentSearchDTO commentSearchDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(commentService.getCommentByArticleId(commentSearchDTO, currentUser));
    }

    @NoNeedLogin
    @GetMapping("getLatestComment")
    @ApiOperation(value = "Get the latest comment information")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<PageInfo<CommentDTO>> getLatestComment(CommentSearchDTO commentSearchDTO) {
        return ResponseResult.success(commentService.getLatestComment(commentSearchDTO));
    }

    @PostMapping("create")
    @ApiOperation(value = "Create a comment")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> create(@RequestBody CommentDTO commentDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(commentService.create(commentDTO, currentUser));
    }

    @PostMapping("delete/{commentId}")
    @ApiOperation(value = "Delete comment")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> delete(@PathVariable Integer commentId) {
        return ResponseResult.success(commentService.delete(commentId));
    }

}

