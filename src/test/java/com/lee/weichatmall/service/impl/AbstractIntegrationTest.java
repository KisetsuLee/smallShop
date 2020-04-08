package com.lee.weichatmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.kevinsawicki.http.HttpRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-08
 * Time: 10:14
 */

public abstract class AbstractIntegrationTest {
    @Autowired
    Environment environment;

    @BeforeEach
    protected void cleanDatabaseAndInitial() {

    }

    protected String loginAndGetCookie() {
        int code = sendRequest("/api/code", TelVerifyServiceImplTest.VALID_TEL, false, "")
                .code();
        Assertions.assertEquals(HTTP_OK, code);

        Map<String, List<String>> headers = sendRequest("/api/login", TelVerifyServiceImplTest.VALID_TEL_CODE, false, "")
                .headers();

        final List<String> setCookies = headers.get("Set-Cookie");
        Assertions.assertNotNull(setCookies);
        // access /api/status with cookie, return true and user information
        return setCookies.stream()
                .filter(cookie -> cookie.contains("JSESSIONID"))
                .findFirst().get();
    }

    protected String getUrl(String apiName) {
        return "http://localhost:" + environment.getProperty("local.server.port") + apiName;
    }

    protected <T> HttpRequest sendRequest(String url, T requestBody, boolean isGet, String cookie) {
        HttpRequest request;
        if (isGet) {
            request = HttpRequest.get(getUrl(url))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header("Cookie", cookie)
                    .acceptJson();
        } else {
            request = HttpRequest.post(getUrl(url))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header("Cookie", cookie)
                    .acceptJson()
                    .send(JSON.toJSONString(requestBody));
        }
        return request;
    }
}
