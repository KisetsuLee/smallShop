package com.lee.weichatmall.service.impl;

import com.github.kevinsawicki.http.HttpRequest;
import com.lee.weichatmall.WeichatmallApplication;
import com.lee.weichatmall.domain.Shop;
import com.lee.weichatmall.domain.status.ShopStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-02
 * Time: 16:57
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WeichatmallApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShopServiceIntegrationTest extends AbstractIntegrationTest {
    private String COOKIE;

    @BeforeAll
    void loginSystem() {
        COOKIE = loginAndGetCookie();
    }

    @Test
    void createShopSuccess() {
        Shop newShop = new Shop();
        newShop.setStatus(ShopStatus.OK.getValue());
        newShop.setOwnerUserId(1L);
        HttpRequest request = sendRequest("/api/shop", newShop, false, COOKIE);
        Assertions.assertEquals(HttpStatus.CREATED.value(), request.code());
    }
}
