package com.lee.weichatmall.service.impl;

import com.lee.weichatmall.service.SmsCodeService;
import org.springframework.stereotype.Service;

/**
 * Description:
 * User: Lzj
 * Date: 2020-03-24
 * Time: 16:31
 */
@Service
public class MockSmsCodeServiceImpl implements SmsCodeService {
    @Override
    public String sendSmsCode(String tel) {
        return "000000";
    }
}
