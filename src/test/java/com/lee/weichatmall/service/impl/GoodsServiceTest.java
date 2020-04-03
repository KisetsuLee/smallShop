package com.lee.weichatmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.kevinsawicki.http.HttpRequest;
import com.lee.weichatmall.WeichatmallApplication;
import com.lee.weichatmall.domain.Goods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static java.net.HttpURLConnection.*;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-02
 * Time: 16:57
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WeichatmallApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.yml")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GoodsServiceTest {
    private String COOKIE;
    private Goods normalGoods = new Goods(); // 能够正常添加的商品
    private Goods notFoundGoods = new Goods(); // 找不到的商品
    private Goods noRightGoods = new Goods(); // 无权操作的商品

    @Autowired
    Environment environment;

    private String getUrl(String apiName) {
        return "http://localhost:" + environment.getProperty("local.server.port") + apiName;
    }

    @BeforeAll
    void loginSystem() {
        int code = HttpRequest.post(getUrl("/api/code"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .acceptJson()
                .send(JSON.toJSONString(TelVerifyServiceImplTest.VALID_TEL))
                .code();
        Assertions.assertEquals(HTTP_OK, code);

        final Map<String, List<String>> headers = HttpRequest.post(getUrl("/api/login"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .acceptJson()
                .send(JSON.toJSONString(TelVerifyServiceImplTest.VALID_TEL_CODE))
                .headers();
        final List<String> setCookies = headers.get("Set-Cookie");
        Assertions.assertNotNull(setCookies);
        // access /api/status with cookie, return true and user information
        COOKIE = setCookies.stream()
                .filter(cookie -> cookie.contains("JSESSIONID"))
                .findFirst().get();

        normalGoods.setName("肥皂");
        normalGoods.setDescription("纯天然无污染肥皂");
        normalGoods.setShopId(100L);
        normalGoods.setStock(100);

        notFoundGoods.setName("肥皂");
        notFoundGoods.setDescription("纯天然无污染肥皂");
        notFoundGoods.setShopId(100000L);
        notFoundGoods.setStock(100);

        noRightGoods.setName("肥皂");
        noRightGoods.setDescription("纯天然无污染肥皂");
        noRightGoods.setShopId(200L);
        noRightGoods.setStock(100);
    }

    @Test
    void createGoods() {
        // normal
        int code = createGoodsExact(normalGoods);
        Assertions.assertEquals(HTTP_CREATED, code);

        int code2 = createGoodsExact(notFoundGoods);
        Assertions.assertEquals(HTTP_BAD_REQUEST, code2);

        int code3 = createGoodsExact(noRightGoods);
        Assertions.assertEquals(HTTP_FORBIDDEN, code3);
    }

    @Test
    void updateGoods() {
        normalGoods.setId(1L);
        normalGoods.setStock(1);
        int code = updateGoodsExact(normalGoods);
        Assertions.assertEquals(HTTP_OK, code);

        notFoundGoods.setId(2L);
        notFoundGoods.setStock(2);
        int code2 = updateGoodsExact(notFoundGoods);
        Assertions.assertEquals(HTTP_BAD_REQUEST, code2);

        noRightGoods.setId(3L);
        noRightGoods.setStock(3);
        int code3 = updateGoodsExact(noRightGoods);
        Assertions.assertEquals(HTTP_FORBIDDEN, code3);

        normalGoods.setId(800L);
        int code4 = updateGoodsExact(normalGoods);
        Assertions.assertEquals(HTTP_NOT_FOUND, code4);
    }

    private int updateGoodsExact(Goods goods) {
        return HttpRequest.post(getUrl("/api/goods/" + goods.getId()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .acceptJson()
                .header("Cookie", COOKIE)
                .send(JSON.toJSONString(goods))
                .code();
    }

    private int createGoodsExact(Goods goods) {
        return HttpRequest.post(getUrl("/api/goods"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .acceptJson()
                .header("Cookie", COOKIE)
                .send(JSON.toJSONString(goods))
                .code();
    }

}
