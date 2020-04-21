package com.lee.weichatmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.kevinsawicki.http.HttpRequest;
import com.lee.weichatmall.WeichatmallApplication;
import com.lee.weichatmall.domain.shoppingCart.GoodsToShoppingCartItem;
import com.lee.weichatmall.domain.shoppingCart.GoodsToShoppingCartList;
import com.lee.weichatmall.domain.shoppingCart.ShoppingCartGoodsWithShop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WeichatmallApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShoppingCartServiceIntegrationTest extends AbstractIntegrationTest {
    private String COOKIE;

    @BeforeAll
    void loginSystem() {
        COOKIE = loginAndGetCookie();
    }

    @Test
    void addGoodsToShoppingCartSucceedTest() {
        GoodsToShoppingCartList goodsToShoppingCartList = new GoodsToShoppingCartList();
        GoodsToShoppingCartItem goodsToShoppingCartItem1 = new GoodsToShoppingCartItem(3L, 10);
        List<GoodsToShoppingCartItem> goodsItems = new ArrayList<>();
        goodsItems.add(goodsToShoppingCartItem1);
        goodsToShoppingCartList.setGoods(goodsItems);

        HttpRequest request = sendRequest("/api/shoppingCart", goodsToShoppingCartList, false, COOKIE);
        Assertions.assertEquals(request.code(), HttpStatus.OK.value());

        String body = request.body();
        String message = getMessageFromResponse(body);
        ShoppingCartGoodsWithShop data = getDataFromResponse(body, ShoppingCartGoodsWithShop.class);
        Assertions.assertNull(message);
        Assertions.assertEquals(1L, data.getShop().getId());
    }

    @Test
    void queryAllShoppingCartShopCountSucceedTest() {
        HttpRequest request = sendRequest("/api/shoppingCart?pageNum=1&pageSize=2", null, true, COOKIE);
        String body = request.body();
        List<ShoppingCartGoodsWithShop> shoppingCartList = JSON.parseObject(body).getJSONArray("data").toJavaList(ShoppingCartGoodsWithShop.class);
        Assertions.assertEquals(2, shoppingCartList.size());
        HttpRequest request1 = sendRequest("/api/shoppingCart?pageNum=1&pageSize=1", null, true, COOKIE);
        String body1 = request1.body();
        List<ShoppingCartGoodsWithShop> shoppingCartList1 = JSON.parseObject(body1).getJSONArray("data").toJavaList(ShoppingCartGoodsWithShop.class);
        Assertions.assertEquals(1, shoppingCartList1.size());
    }
}
