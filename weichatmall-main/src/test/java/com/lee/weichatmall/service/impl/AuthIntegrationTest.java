package com.lee.weichatmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.lee.weichatmall.WeichatmallApplication;
import com.lee.weichatmall.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.net.HttpURLConnection.*;

/**
 * Description:
 * User: Lzj
 * Date: 2020-03-25
 * Time: 15:31
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WeichatmallApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthIntegrationTest extends AbstractIntegrationTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private OrderService orderService;

    @Test
    void test() {
        String test = orderService.say("test");
        Assertions.assertEquals("mock: test", test);
        System.out.println(test);
    }

    @Test
    void registerAndLoginAndLogoutTest() {
        // not login, access /api/status return false
        String body = sendRequest("/api/status", null, true, "").body();
        HashMap statusResponse = JSON.parseObject(body, HashMap.class);
        Assertions.assertFalse((Boolean) statusResponse.get("login"));
        // send sms and get code, access /api/login, return set-cookie
        int code = sendRequest("/api/code", TelVerifyServiceTest.VALID_TEL, false, "")
                .code();
        Assertions.assertEquals(HTTP_OK, code);

        Map<String, List<String>> headers = sendRequest("/api/login", TelVerifyServiceTest.VALID_TEL_CODE, false, "")
                .headers();
        List<String> setCookies = headers.get("Set-Cookie");
        Assertions.assertNotNull(setCookies);
        // access /api/status with cookie, return true and user information
        String jSessionId = setCookies.stream()
                .filter(cookie -> cookie.contains("JSESSIONID"))
                .findFirst().get();
        String body2 = sendRequest("/api/status", null, true, jSessionId)
                .body();
        HashMap statusResponse2 = JSON.parseObject(body2, HashMap.class);
        Assertions.assertTrue((Boolean) statusResponse2.get("login"));

        // access /api/logout
        sendRequest("/api/logout", null, true, jSessionId);

        // access /api/status again, return false
        String body3 = sendRequest("/api/status", null, true, "")
                .body();
        HashMap statusResponse3 = JSON.parseObject(body3, HashMap.class);
        Assertions.assertFalse((Boolean) statusResponse3.get("login"));
    }

    @Test
    void returnUnAuthenticationIfNotLogin() {
        int code = sendRequest("/api/any", null, true, "")
                .code();
        Assertions.assertEquals(HTTP_UNAUTHORIZED, code);
    }

    @Test
    void httpSuccessTest() {
        int code = sendRequest("/api/code", TelVerifyServiceTest.VALID_TEL, false, "")
                .code();
        Assertions.assertEquals(HTTP_OK, code);
    }

    @Test
    void httpBadRequestTest() {
        int code = sendRequest("/api/code", TelVerifyServiceTest.INVALID_TEL, false, "")
                .code();
        Assertions.assertEquals(HTTP_BAD_REQUEST, code);
    }
}
