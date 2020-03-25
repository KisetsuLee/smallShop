package com.lee.weichatmall.service.impl;

import com.lee.weichatmall.service.AuthService;
import com.lee.weichatmall.service.SmsCodeService;
import com.lee.weichatmall.service.UserService;
import com.lee.weichatmall.service.VerificationCodeCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * User: Lzj
 * Date: 2020-03-24
 * Time: 17:11
 */
@Service
public class AuthServiceImpl implements AuthService {
    private UserService userService;
    private VerificationCodeCheckService verificationCodeCheckService;
    private SmsCodeService smsCodeService;

    @Autowired
    public AuthServiceImpl(UserService userService,
                           VerificationCodeCheckService verificationCodeCheckService,
                           SmsCodeService smsCodeService) {
        this.userService = userService;
        this.verificationCodeCheckService = verificationCodeCheckService;
        this.smsCodeService = smsCodeService;
    }

    @Override
    public void sendVerificationCode(String tel) {
        userService.createUserIfNotExist(tel);
        String correctCode = smsCodeService.sendSmsCode(tel);
        verificationCodeCheckService.addCode(tel, correctCode);
    }
}
