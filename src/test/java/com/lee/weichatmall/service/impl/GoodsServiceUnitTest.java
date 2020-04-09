package com.lee.weichatmall.service.impl;

import com.lee.weichatmall.dao.GoodsDao;
import com.lee.weichatmall.dao.ShopDao;
import com.lee.weichatmall.domain.Goods;
import com.lee.weichatmall.domain.Shop;
import com.lee.weichatmall.domain.User;
import com.lee.weichatmall.service.UserContext;
import com.lee.weichatmall.service.exception.goodsService.GoodsInfoWrongForShopException;
import com.lee.weichatmall.service.exception.goodsService.NotAuthorizedForShopException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    void createGoodsIfUserIsShopOwner() {
        // Mockito.when(shopDao.findShopById(Mockito.anyLong())).thenReturn(shop);
        ifShopFound();
        Mockito.when(shop.getOwnerUserId()).thenReturn(1L);
        Assertions.assertEquals(UserContext.getCurrentUser().getId(), shop.getOwnerUserId());
        Mockito.when(goodsDao.insertGoods(goods)).thenReturn(goods);
        Assertions.assertEquals(goods, goodsService.createGoods(goods));
    }

    @Test
    void ifUserIsNotShopOwner() {
        Mockito.when(shopDao.findShopById(Mockito.anyLong())).thenReturn(shop);
        Mockito.when(shop.getOwnerUserId()).thenReturn(2L);
        Assertions.assertThrows(NotAuthorizedForShopException.class, () -> {
            goodsService.createGoods(goods);
        });
    }

    @Test
    void ifShopNotFound() {
        Mockito.when(shopDao.findShopById(Mockito.anyLong())).thenReturn(null);
        Assertions.assertThrows(GoodsInfoWrongForShopException.class, () -> {
            goodsService.createGoods(goods);
        });
    }

    void ifShopFound() {
        Mockito.when(shopDao.findShopById(Mockito.anyLong())).thenReturn(shop);
    }
}
