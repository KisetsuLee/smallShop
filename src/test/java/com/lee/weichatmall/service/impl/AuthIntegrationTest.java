package com.lee.weichatmall.service.impl;

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

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Description:
 * User: Lzj
 * Date: 2020-03-25
 * Time: 15:31
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WeichatmallApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.yml")
public class AuthIntegrationTest {
    @Autowired
    Environment environment;
    private ObjectMapper mapper = new ObjectMapper();

    private String getUrl(String apiName) {
        return "http://localhost:" + environment.getProperty("local.server.port") + apiName;
    }

    @Test
    public void httpSuccessTest() throws JsonProcessingException {
        final int code = HttpRequest.post(getUrl("/api/code"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .acceptJson()
                .send(mapper.writeValueAsString(TelVerifyServiceImplTest.VALID_TEL))
                .code();
        Assertions.assertEquals(HTTP_OK, code);
    }

    @Test
    public void httpBadRequestTest() throws JsonProcessingException {
        final int code = HttpRequest.post(getUrl("/api/code"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .acceptJson()
                .send(mapper.writeValueAsString(TelVerifyServiceImplTest.INVALID_TEL))
                .code();
        Assertions.assertEquals(HTTP_BAD_REQUEST, code);
    }
}
