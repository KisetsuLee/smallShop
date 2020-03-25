package com.lee.weichatmall.service.impl;

import com.lee.weichatmall.domain.user.TelAndCode;
import com.lee.weichatmall.service.TelVerifyService;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * Description:
 * User: Lzj
 * Date: 2020-03-25
 * Time: 14:37
 */
@Service
public class TelVerifyServiceImpl implements TelVerifyService {
    private static Pattern TEL_PATTERN = Pattern.compile("1\\d{10}");

    @Override
    public boolean verifyTelParam(TelAndCode telAndCode) {
        if (telAndCode == null) {
            return false;
        } else if (telAndCode.getTel() == null) {
            return false;
        } else {
            return TEL_PATTERN.matcher(telAndCode.getTel()).find();
        }
    }
}
