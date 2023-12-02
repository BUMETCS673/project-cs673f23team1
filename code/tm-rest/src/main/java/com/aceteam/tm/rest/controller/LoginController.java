package com.aceteam.tm.rest.controller;

import com.aceteam.tm.rest.config.login.NoNeedLogin;
import com.aceteam.tm.rest.config.swagger.ApiVersion;
import com.aceteam.tm.rest.config.swagger.ApiVersionConstant;
import com.aceteam.tm.rest.utils.HttpRequestUtils;
import com.liang.manage.auth.facade.dto.user.UserDTO;
import com.liang.manage.auth.facade.dto.user.UserLoginDTO;
import com.liang.manage.auth.facade.dto.user.UserTokenDTO;
import com.liang.manage.auth.facade.server.UserService;
import com.liang.nansheng.common.constant.AuthSystemConstants;
import com.liang.nansheng.common.constant.TimeoutConstants;
import com.liang.nansheng.common.enums.ResponseCode;
import com.liang.nansheng.common.web.basic.ResponseResult;
import com.liang.nansheng.common.web.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @description: some desc
 * @author: haoran
 */
@Slf4j
@RestController
@RequestMapping("/bbs/sso/")
@Api(tags = "Unified user login interface")
@CrossOrigin(origins = "${crossOrigin.address}", allowCredentials = "true", allowedHeaders = "*", exposedHeaders = "*")
public class LoginController {
    @Reference
    private UserService userService;

    @Value("${cookie.domain}")
    private String domain;

    @NoNeedLogin
    @PostMapping("register")
    @ApiOperation(value = "User Registration")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<UserTokenDTO> register(@Valid @RequestBody UserDTO userDTO, HttpServletResponse response) {
        UserTokenDTO userTokenDTO = userService.register(userDTO);
        // Add cookie
        addCookie(userTokenDTO.getToken(), response);
        return ResponseResult.success(userTokenDTO);
    }

    @NoNeedLogin
    @PostMapping("login")
    @ApiOperation(value = "user login")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<UserTokenDTO> login(@RequestBody UserLoginDTO userLoginDTO, HttpServletResponse response) {
        UserTokenDTO userTokenDTO = userService.login(userLoginDTO);
        // add cookie
        addCookie(userTokenDTO.getToken(), response);
        return ResponseResult.success(userTokenDTO);
    }

    @NoNeedLogin
    @GetMapping("logout")
    @ApiOperation(value = "user logout")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Get information from cookies
            Cookie ssoAccount = HttpRequestUtils.findCookie(request, AuthSystemConstants.NS_ACCOUNT_SSO_COOKIE);
            if (ssoAccount != null) {
                userService.logout(ssoAccount.getValue());
            }
            // Delete cookies
            clearCookie(response);
        } catch (Exception e) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "Exit login failed!");
        }

        return ResponseResult.success(true);
    }

    /**
     * Add cookie
     *
     * @param token
     * @param response
     */
    private void addCookie(String token, HttpServletResponse response) {
        // Set Cookie, The business party can set the name value of the cookie by itself
        ResponseCookie cookie = ResponseCookie.from(AuthSystemConstants.NS_ACCOUNT_SSO_COOKIE, token)
                .maxAge(TimeoutConstants.NS_SSO_TIMEOUT)
                .domain(domain)
                .path("/")
                .httpOnly(true)
//                .secure(true)
//                .sameSite("None")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

    /**
     * 删除cookie
     *
     * @param response
     */
    private void clearCookie(HttpServletResponse response) {
        // Set Cookie, the business party can set the name value of the Cookie by itself
        ResponseCookie cookie = ResponseCookie.from(AuthSystemConstants.NS_ACCOUNT_SSO_COOKIE, "")
                .maxAge(0)
                .domain(domain)
                .path("/")
                .httpOnly(true)
//                .secure(true)
//                .sameSite("None")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

}
