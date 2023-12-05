package com.aceteam.tm.rest.controller;

import com.aceteam.tm.common.enums.PostStateEnum;
import com.aceteam.tm.post.facade.server.PostService;
import com.aceteam.tm.rest.config.login.NoNeedLogin;
import com.aceteam.tm.rest.config.swagger.ApiVersion;
import com.aceteam.tm.rest.config.swagger.ApiVersionConstant;
import com.aceteam.tm.rest.utils.FileLengthUtils;
import com.acteam.tm.user.facade.dto.*;
import com.acteam.tm.user.facade.server.FollowService;
import com.acteam.tm.user.facade.server.LikeCommentService;
import com.acteam.tm.user.facade.server.LikeService;
import com.acteam.tm.user.facade.server.UserLevelService;
import com.github.pagehelper.PageInfo;
import com.liang.manage.auth.facade.dto.user.UserDTO;
import com.liang.manage.auth.facade.dto.user.UserEmailDTO;
import com.liang.manage.auth.facade.dto.user.UserPasswordDTO;
import com.liang.manage.auth.facade.server.UserService;
import com.liang.nansheng.common.auth.UserContextUtils;
import com.liang.nansheng.common.auth.UserRightsDTO;
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
@RequestMapping("/bbs/user/")
@Api(tags = "user interface")
public class UserController {
    @Reference
    private UserLevelService userLevelService;

    @Reference
    private FollowService followService;

    @Reference
    private LikeService likeService;

    @Reference
    private LikeCommentService likeCommentService;

    @Reference
    private UserService userService;

    @Reference
    private PostService postService;

    @Autowired
    private FileLengthUtils fileLengthUtils;

    @GetMapping("getCurrentUserRights")
    @ApiOperation(value = "Get current user privileges")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<UserRightsDTO> getCurrentUserRights() {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(userSsoDTOtoUserRightsDTO(currentUser));
    }

    @NoNeedLogin
    @GetMapping("getFollowUsers")
    @ApiOperation(value = "Get information about the users you follow")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<PageInfo<FollowDTO>> getFollowUsers(FollowSearchDTO followSearchDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(followService.getFollowUsers(followSearchDTO, currentUser));
    }

    @GetMapping("updateFollowState")
    @ApiOperation(value = "Update Follow Status")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> updateFollowState(@RequestParam Long toUser) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(followService.updateFollowState(currentUser.getUserId(), toUser));
    }

    @GetMapping("updateLikeState")
    @ApiOperation(value = "Updating the status of likes")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> updateLikeState(@RequestParam Integer articleId) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(likeService.updateLikeState(articleId, currentUser));
    }

    @GetMapping("updateLikeCommentState")
    @ApiOperation(value = "Update comment liking status")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> updateLikeCommentState(@RequestParam Integer commentId) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(likeCommentService.updateLikeCommentState(commentId, currentUser));
    }

    @NoNeedLogin
    @GetMapping("getHotAuthorsList")
    @ApiOperation(value = "Get a list of popular authors")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<PageInfo<UserForumDTO>> getHotAuthorsList(UserSearchDTO userSearchDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(userLevelService.getHotAuthorsList(userSearchDTO, currentUser));
    }

    @NoNeedLogin
    @GetMapping("getUserInfo")
    @ApiOperation(value = "Get user information")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<UserForumDTO> getUserInfo(@RequestParam Long userId) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(userLevelService.getUserInfo(userId, currentUser));
    }

    @NoNeedLogin
    @GetMapping("getFollowCount")
    @ApiOperation(value = "Get number of followers/fans")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<FollowCountDTO> getFollowCount(@RequestParam Long userId) {
        return ResponseResult.success(followService.getFollowCount(userId));
    }

    @PostMapping("uploadUserPicture")
    @ApiOperation(value = "Upload user avatar (update)")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> uploadUserPicture(@RequestParam("picture") MultipartFile picture) throws IOException {
        if (fileLengthUtils.isFileNotTooBig(picture.getBytes())) {
            UserSsoDTO currentUser = UserContextUtils.currentUser();
            return ResponseResult.success(userService.uploadUserPicture(picture.getBytes(), picture.getOriginalFilename(), currentUser));
        } else {
            throw BusinessException.build(ResponseCode.EXCEED_THE_MAX, "Please upload no more than " +
                    CommonUtils.byteConversion(fileLengthUtils.getFileMaxLength()) + " pictures!");
        }
    }

    @PostMapping("updateUserBasicInfo")
    @ApiOperation(value = "Updating basic user information")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> updateUserBasicInfo(@RequestBody UserDTO userDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(userService.updateUserBasicInfo(userDTO, currentUser));
    }

    @NoNeedLogin
    @GetMapping("sendEmailVerifyCode")
    @ApiOperation(value = "Send email verification code")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> sendEmailVerifyCode(@RequestParam String email) {
        UserSsoDTO currentUser = new UserSsoDTO();
        if (UserContextUtils.currentUser() == null) {
            // Compatible cell phone reset password
            currentUser.setUserId(userService.getByEmail(email).getId());
        } else {
            currentUser = UserContextUtils.currentUser();
        }
        return ResponseResult.success(userService.sendEmailVerifyCode(email, currentUser));
    }

    @NoNeedLogin
    @GetMapping("sendSmsVerifyCode")
    @ApiOperation(value = "Send SMS verification code")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> sendSmsVerifyCode(@RequestParam String phone) {
        UserSsoDTO currentUser = new UserSsoDTO();
        if (UserContextUtils.currentUser() == null) {

            currentUser.setUserId(userService.getByPhone(phone).getId());
        } else {
            currentUser = UserContextUtils.currentUser();
        }
        return ResponseResult.success(userService.sendSmsVerifyCode(phone, currentUser));
    }

    @PostMapping("bindEmail")
    @ApiOperation(value = "Bind email")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> bindEmail(@RequestBody UserEmailDTO userEmailDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(userService.bindEmail(userEmailDTO, currentUser));
    }

    @PostMapping("bindPhone")
    @ApiOperation(value = "Bind Phone")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> bindPhone(@RequestBody UserEmailDTO userEmailDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(userService.bindPhone(userEmailDTO, currentUser));
    }

    @PostMapping("untieEmail")
    @ApiOperation(value = "Unbind email")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> untieEmail() {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(userService.untieEmail(currentUser));
    }

    @PostMapping("untiePhone")
    @ApiOperation(value = "Unbind phone")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> untiePhone() {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(userService.untiePhone(currentUser));
    }

    @PostMapping("updatePassword")
    @ApiOperation(value = "Update Password")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> updatePassword(@RequestBody UserPasswordDTO passwordDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(userService.updatePassword(passwordDTO, currentUser));
    }

    @GetMapping("isValidEmail")
    @ApiOperation(value = "Mail judgment")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> isValidEmail(@RequestParam String email) {
        return ResponseResult.success(userService.isValidEmail(email));
    }

    @GetMapping("isValidPhone")
    @ApiOperation(value = "Phone judgment")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> isValidPhone(@RequestParam String phone) {
        return ResponseResult.success(userService.isValidPhone(phone));
    }

    @NoNeedLogin
    @GetMapping("isValidUser")
    @ApiOperation(value = "User judgment")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> isValidUser(@RequestParam String username) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(userService.isValidUser(username, currentUser));
    }

    @NoNeedLogin
    @PostMapping("isPhoneExist/{phone}")
    @ApiOperation(value = "Determine if the phone is already bound")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> isPhoneExist(@PathVariable String phone) {
        return ResponseResult.success(userService.isPhoneExist(phone));
    }

    @NoNeedLogin
    @PostMapping("isEmailExist/{email}")
    @ApiOperation(value = "Determine if email is bound")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> isEmailExist(@PathVariable String email) {
        return ResponseResult.success(userService.isEmailExist(email));
    }

    @NoNeedLogin
    @PostMapping("phoneResetPassword")
    @ApiOperation(value = "Mobile phone password reset")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> phoneResetPassword(@RequestBody UserEmailDTO userEmailDTO) {
        return ResponseResult.success(userService.phoneResetPassword(userEmailDTO));
    }

    @NoNeedLogin
    @PostMapping("emailResetPassword")
    @ApiOperation(value = "Mailbox reset password")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> emailResetPassword(@RequestBody UserEmailDTO userEmailDTO) {
        return ResponseResult.success(userService.emailResetPassword(userEmailDTO));
    }

    @NoNeedLogin
    @GetMapping("getUserOperateCount")
    @ApiOperation(value = "Get the number of user actions (posts, follows, likes, etc.)")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<UserOperateCountDTO> getUserOperateCount(@RequestParam Long userId,
                                                                   @RequestParam(required = false) PostStateEnum postStateEnum) {
        UserOperateCountDTO userOperateCountDTO = new UserOperateCountDTO();

        // Get the number of user posts
        userOperateCountDTO.setPostCount(postService.getUserPostCount(userId, postStateEnum));

        // Get number of followers/fans
        FollowCountDTO followCount = followService.getFollowCount(userId);
        userOperateCountDTO.setFanCount(followCount.getFanCount());
        userOperateCountDTO.setFollowCount(followCount.getFollowCount());

        // Get the number of liked posts by user ids
        userOperateCountDTO.setLikeCount(likeService.getUserTheLikeCount(userId));

        return ResponseResult.success(userOperateCountDTO);
    }

    /**
     * userSsoDTO to UserRightsDTO
     *
     * @param currentUser
     * @return
     */
    private UserRightsDTO userSsoDTOtoUserRightsDTO(UserSsoDTO currentUser) {
        UserRightsDTO userRightsDTO = new UserRightsDTO();
        userRightsDTO.setUserId(currentUser.getUserId());
        userRightsDTO.setUserName(currentUser.getUserName());
        userRightsDTO.setRoles(currentUser.getRoles());

        return userRightsDTO;
    }

}
