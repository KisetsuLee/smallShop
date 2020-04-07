package com.lee.weichatmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import com.lee.weichatmall.WeichatmallApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
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
@TestPropertySource(locations = "classpath:application.yml")
class AuthIntegrationTest {
    @Autowired
    Environment environment;
    private ObjectMapper mapper = new ObjectMapper();

    private String getUrl(String apiName) {
        return "http://localhost:" + environment.getProperty("local.server.port") + apiName;
    }

    @Test
    void registerAndLoginAndLogoutTest() throws JsonProcessingException {
        // not login, access /api/status return false
        String body = HttpRequest.get(getUrl("/api/status"))
                .acceptJson()
                .body();
        HashMap statusResponse = JSON.parseObject(body, HashMap.class);
        Assertions.assertFalse((Boolean) statusResponse.get("login"));
        // send sms and get code, access /api/login, return set-cookie
        int code = HttpRequest.post(getUrl("/api/code"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .acceptJson()
                .send(mapper.writeValueAsString(TelVerifyServiceImplTest.VALID_TEL))
                .code();
        Assertions.assertEquals(HTTP_OK, code);

        Map<String, List<String>> headers = HttpRequest.post(getUrl("/api/login"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .acceptJson()
                .send(mapper.writeValueAsString(TelVerifyServiceImplTest.VALID_TEL_CODE))
                .headers();
        List<String> setCookies = headers.get("Set-Cookie");
        Assertions.assertNotNull(setCookies);
        // access /api/status with cookie, return true and user information
        String jSessionId = setCookies.stream()
                .filter(cookie -> cookie.contains("JSESSIONID"))
                .findFirst().get();
        String body2 = HttpRequest.get(getUrl("/api/status"))
                .header("Cookie", jSessionId)
                .acceptJson()
                .body();
        HashMap statusResponse2 = JSON.parseObject(body2, HashMap.class);
        Assertions.assertTrue((Boolean) statusResponse2.get("login"));

        // access /api/logout
        String body3 = HttpRequest.get(getUrl("/api/logout"))
                .header("Cookie", jSessionId)
                .acceptJson()
                .body();

        // access /api/status again, return false
        String body4 = HttpRequest.get(getUrl("/api/status"))
                .acceptJson()
                .body();
        HashMap statusResponse3 = JSON.parseObject(body, HashMap.class);
        Assertions.assertFalse((Boolean) statusResponse3.get("login"));
    }

    @Test
    void returnUnAuthenticationIfNotLogin() {
        int code = HttpRequest.post(getUrl("/api/any"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .acceptJson()
                .code();
        Assertions.assertEquals(HTTP_UNAUTHORIZED, code);
    }

    @Test
    void httpSuccessTest() throws JsonProcessingException {
        int code = HttpRequest.post(getUrl("/api/code"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .acceptJson()
                .send(mapper.writeValueAsString(TelVerifyServiceImplTest.VALID_TEL))
                .code();
        Assertions.assertEquals(HTTP_OK, code);
    }

    @Test
    void httpBadRequestTest() throws JsonProcessingException {
        int code = HttpRequest.post(getUrl("/api/code"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .acceptJson()
                .send(mapper.writeValueAsString(TelVerifyServiceImplTest.INVALID_TEL))
                .code();
        Assertions.assertEquals(HTTP_BAD_REQUEST, code);
    }
}
