package com.lee.weichatmall.controller;

import com.lee.weichatmall.domain.user.TelAndCode;
import com.lee.weichatmall.service.AuthService;
import com.lee.weichatmall.service.TelVerifyService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("code")
    public void code(@RequestBody TelAndCode telAndCode, HttpServletResponse response) {
        if (telVerifyService.verifyTelParam(telAndCode)) {
            authService.sendVerificationCode(telAndCode.getTel());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }

    @PostMapping("login")
    public String login(@RequestBody TelAndCode telAndCode) {
        UsernamePasswordToken token = new UsernamePasswordToken(
                telAndCode.getTel(), telAndCode.getCode());
        SecurityUtils.getSubject().login(token);
        return "登录成功，手机号为" + telAndCode.getTel();
    }
}