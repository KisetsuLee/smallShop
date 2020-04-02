package com.lee.weichatmall.controller;

import com.lee.weichatmall.domain.responesesEntity.LoginResponse;
import com.lee.weichatmall.domain.user.TelAndCode;
import com.lee.weichatmall.service.AuthService;
import com.lee.weichatmall.service.TelVerifyService;
import com.lee.weichatmall.service.UserContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Description:
 * User: Lzj
 * Date: 2020-03-24
 * Time: 16:35
 */
@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;
    private final TelVerifyService telVerifyService;

    @Autowired
    public AuthController(AuthService authService, TelVerifyService telVerifyService) {
        this.authService = authService;
        this.telVerifyService = telVerifyService;
    }

    /**
     * @api {post} /code 请求验证码
     * @apiName GetCode
     * @apiGroup 登录与鉴权
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParam {String} tel 手机号码
     * @apiParamExample {json} Request-Example:
     *          {
     *              "tel": "13812345678",
     *          }
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     * @apiError 400 Bad Request 若用户的请求包含错误
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *       "message": "Bad Request"
     *     }
     */
    /**
     * @param telAndCode 手机号和收到的验证码
     * @param response   HTTP response
     */
    @PostMapping("code")
    public void code(@RequestBody TelAndCode telAndCode, HttpServletResponse response) {
        if (telVerifyService.verifyTelParam(telAndCode)) {
            authService.sendVerificationCode(telAndCode.getTel());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }

    /**
     * @api {post} /login 登录
     * @apiName Login
     * @apiGroup 登录与鉴权
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParam {String} tel 手机号码
     * @apiParam {String} code 验证码
     * @apiParamExample {json} Request-Example:
     *          {
     *              "tel": "13812345678",
     *              "code": "000000"
     *          }
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     * @apiError 400 Bad Request 若用户的请求包含错误
     * @apiError 403 Forbidden 若用户的验证码错误
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *       "message": "Bad Request"
     *     }
     */
    /**
     * @param telAndCode 手机号
     * @return 一个提示信息
     */
    @PostMapping("login")
    public String login(@RequestBody TelAndCode telAndCode) {
        UsernamePasswordToken token = new UsernamePasswordToken(
                telAndCode.getTel(), telAndCode.getCode());
        SecurityUtils.getSubject().login(token);
        return "登录成功，手机号为" + telAndCode.getTel();
    }

    /**
     * @api {post} /logout 登出
     * @apiName Logout
     * @apiGroup 登录与鉴权
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * @apiError 401 Unauthorized 若用户未登录
     * @apiErrorExample Error-Response:
     * HTTP/1.1 400 Bad Request
     * {
     * "message": "Bad Request"
     * }
     */
    @PostMapping("logout")
    public void logout() {
        SecurityUtils.getSubject().logout();
    }

    /**
     * @api {get} /status 获取登录状态
     * @apiName Status
     * @apiGroup 登录与鉴权
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiSuccess {User} user 用户信息
     * @apiSuccess {Boolean} login 登录状态
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "login": true,
     *       "user": {
     *           "id": 123,
     *           "name": "张三",
     *           "tel": "13812345678",
     *           "avatarUrl": "https://url",
     *           "address": "北京市 西城区 100号",
     *       }
     *     }
     *
     * @apiError 401 Unauthorized 若用户未登录
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     */
    /**
     * @return 登录状态
     */
    @GetMapping("status")
    public Object loginStatus() {
        if (UserContext.getCurrentUser() == null) {
            return LoginResponse.notLoginResponse();
        } else {
            return LoginResponse.loginResponse(UserContext.getCurrentUser());
        }
    }
}
