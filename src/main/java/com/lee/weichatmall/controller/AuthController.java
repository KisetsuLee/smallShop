package com.lee.weichatmall.controller;

import com.lee.weichatmall.domain.user.TelAndCode;
import com.lee.weichatmall.service.AuthService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("code")
    public String code(@RequestBody TelAndCode telAndCode) {
        authService.sendVerificationCode(telAndCode.getTel());
        return "发送成功，请输入正确的验证码进行验证";
    }

    @PostMapping("login")
    public String login(@RequestBody TelAndCode telAndCode) {
        UsernamePasswordToken token = new UsernamePasswordToken(
                telAndCode.getTel(), telAndCode.getCode());
        SecurityUtils.getSubject().login(token);
        return "登录成功，手机号为" + telAndCode.getTel();
    }
}
