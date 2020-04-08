package com.lee.weichatmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.kevinsawicki.http.HttpRequest;
import com.lee.weichatmall.WeichatmallApplication;
import com.lee.weichatmall.domain.Goods;
import com.lee.weichatmall.domain.goods.GoodsStatus;
import com.lee.weichatmall.domain.responesesEntity.PageResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static java.net.HttpURLConnection.*;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-02
 * Time: 16:57
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WeichatmallApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GoodsServiceIntegrationTest extends AbstractIntegrationTest {
    private String COOKIE;
    private Goods normalGoods = new Goods(); // 能够正常添加的商品
    private Goods notFoundGoods = new Goods(); // 找不到的商品
    private Goods noRightGoods = new Goods(); // 无权操作的商品

    @BeforeAll
    void loginSystem() {
        COOKIE = loginAndGetCookie();

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
        Goods newGoods = createNewGoods(normalGoods);
        newGoods.setStock(200);
        int code = updateGoodsExact(newGoods);
        Assertions.assertEquals(HTTP_OK, code);

        Goods newGoods2 = createNewGoods(normalGoods);
        newGoods2.setShopId(12222L);
        int code2 = updateGoodsExact(normalGoods);
        Assertions.assertEquals(HTTP_BAD_REQUEST, code2);

        noRightGoods.setId(2L);
        int code3 = updateGoodsExact(noRightGoods);
        Assertions.assertEquals(HTTP_FORBIDDEN, code3);

        Goods newGoods3 = createNewGoods(normalGoods);
        newGoods3.setId(800L);
        int code4 = updateGoodsExact(newGoods3);
        Assertions.assertEquals(HTTP_NOT_FOUND, code4);
    }

    @Test
    void deleteGoods() {
        // normal
        Goods newGoods = createNewGoods(normalGoods);
        int code = deleteGoodsExact(newGoods);
        Assertions.assertEquals(HTTP_NO_CONTENT, code);
        Assertions.assertEquals(GoodsStatus.DELETED_STATUS, GoodsStatus.DELETED_STATUS);

        int code2 = deleteGoodsExact(2L);
        Assertions.assertEquals(HTTP_FORBIDDEN, code2);

        int code3 = deleteGoodsExact(12345L);
        Assertions.assertEquals(HTTP_NOT_FOUND, code3);
    }

    @Test
    void getGoodsByPageWithoutShopId() {
        int code = sendRequest("/api/goods?pageNum=1&pageSize=3", null, true, "").code();
        Assertions.assertEquals(HTTP_UNAUTHORIZED, code);
        int code1 = sendRequest("/api/goods?pageNum=1&pageSize=3", null, true, COOKIE).code();
        Assertions.assertEquals(HTTP_OK, code1);

        String body = sendRequest("/api/goods?pageNum=1&pageSize=3", null, true, COOKIE).body();
        PageResponse<Goods> pageResponse = JSON.parseObject(body, new TypeReference<PageResponse<Goods>>() {
        });
        Assertions.assertEquals(1, pageResponse.getPageNum());
        Assertions.assertEquals(3, pageResponse.getData().size());
    }

    @Test
    void getGoodsByPageWithShopId() {
        int code = sendRequest("/api/goods?pageNum=1&pageSize=3&shopId=100", null, true, "").code();
        Assertions.assertEquals(HTTP_UNAUTHORIZED, code);
        int code1 = sendRequest("/api/goods?pageNum=1&pageSize=3&shopId=100", null, true, COOKIE).code();
        Assertions.assertEquals(HTTP_OK, code1);

        String body = sendRequest("/api/goods?pageNum=1&pageSize=3&shopId=100", null, true, COOKIE).body();
        PageResponse<Goods> pageResponse = JSON.parseObject(body, new TypeReference<PageResponse<Goods>>() {
        });
        Assertions.assertEquals(1, pageResponse.getPageNum());
        Assertions.assertEquals(2, pageResponse.getData().size());
    }

    private Goods createNewGoods(Goods goods) {
        String body = HttpRequest.post(getUrl("/api/goods"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .acceptJson()
                .header("Cookie", COOKIE)
                .send(JSON.toJSONString(goods))
                .body();
        return JSON.parseObject(body).getObject("data", Goods.class);
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

    private int deleteGoodsExact(Goods goods) {
        return HttpRequest.delete(getUrl("/api/goods/" + goods.getId()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .acceptJson()
                .header("Cookie", COOKIE)
                .code();
    }

    private int deleteGoodsExact(long goodsId) {
        return HttpRequest.delete(getUrl("/api/goods/" + goodsId))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .acceptJson()
                .header("Cookie", COOKIE)
                .code();
    }
}
