package com.lee.weichatmall.service.impl;

import com.lee.weichatmall.dao.GoodsDao;
import com.lee.weichatmall.dao.ShopDao;
import com.lee.weichatmall.domain.Goods;
import com.lee.weichatmall.domain.Shop;
import com.lee.weichatmall.domain.User;
import com.lee.weichatmall.service.UserContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-08
 * Time: 13:44
 */
@ExtendWith(MockitoExtension.class)
class GoodsServiceUnitTest {
    @Mock
    private GoodsDao goodsDao;
    @Mock
    private ShopDao shopDao;
    @Mock
    private Shop shop;
    @Mock
    private Goods goods;
    @InjectMocks
    private GoodsServiceImpl goodsService;

    @BeforeEach
    void initUserContext() {
        User user = new User();
        user.setId(1L);
        UserContext.setCurrentUser(user);
    }

    @AfterEach
    void clearUserContext() {
        UserContext.setCurrentUser(null);
    }

    void ifShopIdCorrect() {
        when(shopDao.findShopById(anyLong())).thenReturn(shop);
    }

    @Test
    void ifShopIdInCorrect() {
        when(shopDao.findShopById(anyLong())).thenReturn(null);
    }

    void ifUserHasAuthorized() {
        ifShopIdCorrect();
        when(shop.getOwnerUserId()).thenReturn(1L);
    }

    void ifUserHasUnauthorized() {
        ifShopIdCorrect();
        when(shop.getOwnerUserId()).thenReturn(2L);
    }

    @Test
    void createGoodsSucceed() {
        when(goodsDao.insertGoods(goods)).thenReturn(goods);
        Assertions.assertEquals(goods, goodsDao.insertGoods(goods));
    }
}
