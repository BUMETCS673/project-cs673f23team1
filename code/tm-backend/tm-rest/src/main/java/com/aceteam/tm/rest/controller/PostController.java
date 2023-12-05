package com.aceteam.tm.rest.controller;

import com.aceteam.tm.post.facade.dto.*;
import com.aceteam.tm.post.facade.server.PostService;
import com.aceteam.tm.rest.config.login.NoNeedLogin;
import com.aceteam.tm.rest.config.swagger.ApiVersion;
import com.aceteam.tm.rest.config.swagger.ApiVersionConstant;
import com.aceteam.tm.rest.utils.FileLengthUtils;
import com.acteam.tm.user.facade.dto.LikeSearchDTO;
import com.aceteam.tm.common.enums.PostStateEnum;
import com.github.pagehelper.PageInfo;
import com.liang.nansheng.common.auth.RoleSsoDTO;
import com.liang.nansheng.common.auth.UserContextUtils;
import com.liang.nansheng.common.auth.UserSsoDTO;
import com.liang.nansheng.common.enums.ResponseCode;
import com.liang.nansheng.common.enums.RoleGradeEnum;
import com.liang.nansheng.common.utils.CommonUtils;
import com.liang.nansheng.common.web.basic.ResponseResult;
import com.liang.nansheng.common.web.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: some desc
 * @author: haoran
 */

@Slf4j
@RestController
@RequestMapping("/bbs/post/")
@Api(tags = "Post Interface")
public class PostController {
    @Reference
    private PostService postService;
    @Autowired
    private FileLengthUtils fileLengthUtils;

    @NoNeedLogin
    @GetMapping("getList")
    @ApiOperation(value = "Get Post")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<PageInfo<PostDTO>> getList(PostSearchDTO postSearchDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(postService.getList(postSearchDTO, currentUser, PostStateEnum.enable));
    }

    @NoNeedLogin
    @GetMapping("getPersonalPosts")
    @ApiOperation(value = "Get individual postings (null=all)")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<PageInfo<PostDTO>> getPersonalPosts(PostSearchDTO postSearchDTO, @RequestParam(required = false) PostStateEnum postStateEnum) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        if (currentUser != null && currentUser.getUserId().equals(postSearchDTO.getCreateUser())) {
            postStateEnum = null;
        }
        return ResponseResult.success(postService.getList(postSearchDTO, currentUser, postStateEnum));
    }

    @GetMapping("getPendingReviewPosts")
    @ApiOperation(value = "Get posts for review")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<PageInfo<PostDTO>> getPendingReviewPosts(PostSearchDTO postSearchDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(postService.getPendingReviewPosts(postSearchDTO, currentUser));
    }

    @GetMapping("getDisabledPosts")
    @ApiOperation(value = "Get Disabled Posts")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<PageInfo<PostDTO>> getDisabledPosts(PostSearchDTO postSearchDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(postService.getDisabledPosts(postSearchDTO, currentUser));
    }

    @PostMapping("/updateState")
    @ApiOperation(value = "Modify Post Approval Status")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> updateState(@RequestBody PostDTO postDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(postService.updateState(postDTO, currentUser));
    }

    @NoNeedLogin
    @GetMapping("getLikesPost")
    @ApiOperation(value = "Get Liked Posts")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<PageInfo<PostDTO>> getLikesPost(LikeSearchDTO likeSearchDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(postService.getLikesPost(likeSearchDTO, currentUser));
    }

    @NoNeedLogin
    @GetMapping("getPostCommentVisitTotal")
    @ApiOperation(value = "Get the total number of post comment visits")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<TotalDTO> getPostCommentVisitTotal() {
        return ResponseResult.success(postService.getPostCommentVisitTotal());
    }

    @NoNeedLogin
    @GetMapping("getById")
    @ApiOperation(value = "Get post details")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<PostDTO> getById(@RequestParam Integer id, @RequestParam(required = false) Boolean isPv) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        List<PostDTO> postDTOS = postService.getByIds(Collections.singletonList(id), isPv, currentUser);
        if (CollectionUtils.isNotEmpty(postDTOS)) {
            PostDTO postDTO = postDTOS.get(0);
            // Login - Filter non-approved posts (non-approved posts are not allowed to be viewed by others - except by the administrator and myself)
            if (currentUser != null && !PostStateEnum.enable.getCode().equals(postDTO.getState())) {
                // All character levels of the current user
                List<String> grades = currentUser.getRoles().stream().map(RoleSsoDTO::getGrade).distinct().collect(Collectors.toList());
                // Not the super admin and not me
                if (!grades.contains(RoleGradeEnum.NS_SUPER_ADMIN_ROLE.name()) && !postDTO.getCreateUser().equals(currentUser.getUserId())) {
                    return ResponseResult.build(ResponseCode.NOT_EXISTS, null);
                }
            }
            // Not logged in - Filtering non-approved articles (all)
            if (currentUser == null && !PostStateEnum.enable.getCode().equals(postDTO.getState())) {
                return ResponseResult.build(ResponseCode.NOT_EXISTS, null);
            }
        } else {
            return ResponseResult.build(ResponseCode.NOT_EXISTS, null);
        }
        return ResponseResult.success(postDTOS.get(0));
    }

    @PostMapping("/create")
    @ApiOperation(value = "Write a Post")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> create(@RequestParam(value = "file", required = false) MultipartFile picture,
                                          PostDTO postDTO, @RequestParam List<Integer> labelIds) throws IOException {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        // No pictures
        if (picture == null) {
            return ResponseResult.success(postService.create(postDTO, labelIds, currentUser));
        }

        if (fileLengthUtils.isFileNotTooBig(picture.getBytes())) {
            return ResponseResult.success(postService.create(picture.getBytes(), picture.getOriginalFilename(), postDTO, labelIds, currentUser));
        } else {
            throw BusinessException.build(ResponseCode.EXCEED_THE_MAX, "Please upload no more than " +
                    CommonUtils.byteConversion(fileLengthUtils.getFileMaxLength()) + " MB of pictures!");
        }
    }

    @PostMapping("/update")
    @ApiOperation(value = "Update Post")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> update(@RequestParam(value = "file", required = false) MultipartFile picture,
                                          PostDTO postDTO, @RequestParam List<Integer> labelIds) throws IOException {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        // No pictures
        if (picture == null) {
            return ResponseResult.success(postService.update(postDTO, labelIds, currentUser));
        }

        if (fileLengthUtils.isFileNotTooBig(picture.getBytes())) {
            return ResponseResult.success(postService.update(picture.getBytes(), picture.getOriginalFilename(), postDTO, labelIds, currentUser));
        } else {
            throw BusinessException.build(ResponseCode.EXCEED_THE_MAX, "Please upload no more than " +
                    CommonUtils.byteConversion(fileLengthUtils.getFileMaxLength()) + " MB of pictures!");
        }
    }

    /**
     * Upload a pic - mavonEditor
     *
     * @param picture
     * @return
     */
    @PostMapping("/uploadPicture")
    @ApiOperation(value = "Upload a picture")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<String> uploadPicture(@RequestParam(value = "picture") MultipartFile picture) throws IOException {
        if (fileLengthUtils.isFileNotTooBig(picture.getBytes())) {
            return ResponseResult.success(postService.uploadPicture(picture.getBytes(), picture.getOriginalFilename()));
        } else {
            throw BusinessException.build(ResponseCode.EXCEED_THE_MAX, "Please upload no more than" +
                    CommonUtils.byteConversion(fileLengthUtils.getFileMaxLength()) + " MB of pictures!");
        }
    }

    @NoNeedLogin
    @GetMapping("getCountById")
    @ApiOperation(value = "Get some stats on the article")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<PostCountDTO> getCountById(@RequestParam Integer id) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(postService.getCountById(id, currentUser));
    }

    @GetMapping("postTop")
    @ApiOperation(value = "Post topping/untopping")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> postTop(@RequestParam Integer id, @RequestParam Boolean top) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(postService.postTop(id, top, currentUser));
    }

    @PostMapping("delete/{id}")
    @ApiOperation(value = "Post deleted")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> delete(@PathVariable Integer id) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(postService.delete(id, currentUser));
    }

    @GetMapping("getPostCheckCount")
    @ApiOperation(value = "Post Review Data Volume")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<PostCheckCountDTO> getPostCheckCount(@RequestParam(required = false) String title) {
        return ResponseResult.success(postService.getPostCheckCount(title));
    }

    @NoNeedLogin
    @GetMapping("getGoods")
    @ApiOperation(value = "Get Goods")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<List<GoodsDTO>> getGoods() {
        return ResponseResult.success(postService.getGoods());
    }

}
