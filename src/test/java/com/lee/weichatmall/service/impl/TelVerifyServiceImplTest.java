package com.lee.weichatmall.service.impl;

import com.lee.weichatmall.domain.user.TelAndCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Description:
 * User: Lzj
 * Date: 2020-03-25
 * Time: 14:53
 */
class TelVerifyServiceImplTest {
    public static TelAndCode VALID_TEL = new TelAndCode("13127755898", null);
    public static TelAndCode INVALID_TEL = new TelAndCode("1312775589", null);
    public static TelAndCode VALID_TEL_CODE = new TelAndCode("13127755898", "000000");

    @Test
    public void returnTrueIfValid() {
        Assertions.assertTrue(new TelVerifyServiceImpl().verifyTelParam(VALID_TEL));
    }

    @Test
    public void returnFalseIfInvalid() {
        Assertions.assertFalse(new TelVerifyServiceImpl().verifyTelParam(INVALID_TEL));
    }

    @Test
    public void returnFalseIfNull() {
        Assertions.assertFalse(new TelVerifyServiceImpl().verifyTelParam(null));
        Assertions.assertFalse(new TelVerifyServiceImpl().verifyTelParam(new TelAndCode(null, null)));
    }
}
