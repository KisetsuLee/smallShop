package com.lee.weichatmall.service;

import com.lee.weichatmall.domain.user.TelAndCode;

/**
 * Description:
 * User: Lzj
 * Date: 2020-03-25
 * Time: 14:13
 */
public interface TelVerifyService {
    boolean verifyTelParam(TelAndCode telAndCode);
}
