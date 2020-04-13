package com.lee.weichatmall.service.impl;

import com.lee.weichatmall.dao.GoodsDao;
import com.lee.weichatmall.dao.ShopDao;
import com.lee.weichatmall.domain.Goods;
import com.lee.weichatmall.domain.Shop;
import com.lee.weichatmall.domain.User;
import com.lee.weichatmall.service.UserContext;
import com.lee.weichatmall.service.exception.HttpException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


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
    void goodsInfoIsCorrectShop() {
        when(shopDao.findShopById(anyLong())).thenReturn(shop);
        Assertions.assertEquals(shop, goodsService.checkGoodsInCorrectShop(anyLong()));
        verify(shopDao).findShopById(anyLong());
    }

    @Test
    void goodsInfoIsNotCorrectShop() {
        when(shopDao.findShopById(anyLong())).thenReturn(null);
        Assertions.assertThrows(HttpException.class, () -> goodsService.checkGoodsInCorrectShop(anyLong()));
        verify(shopDao).findShopById(anyLong());
    }

    @Test
    void userHasAuthorizationForGoods() {
        Assertions.assertDoesNotThrow(() -> goodsService.isUserHasAuthorizedForGoods(1L));
    }

    @Test
    void userHasNotAuthorizationForGoods() {
        Assertions.assertThrows(HttpException.class, () -> goodsService.isUserHasAuthorizedForGoods(2L));
    }

    @Test
    void getGoodsByIdSucceed() {
        when(goodsDao.findGoodsById(anyLong())).thenReturn(goods);
        Assertions.assertEquals(goods, goodsService.getGoodsInfoById(anyLong()));
        verify(goodsDao).findGoodsById(anyLong());
    }

    @Test
    void getGoodsByIdNotSucceed() {
        when(goodsDao.findGoodsById(anyLong())).thenReturn(null);
        Assertions.assertThrows(HttpException.class, () -> goodsService.getGoodsInfoById(anyLong()));
        verify(goodsDao).findGoodsById(anyLong());
    }

    @Test
    void createGoods() {
        when(shopDao.findShopById(anyLong())).thenReturn(shop);
        when(goodsDao.insertGoods(goods)).thenReturn(goods);
        // doAnswer(invocation -> {
        //     Object arg0 = invocation.getArgument(0);
        //     Assertions.assertEquals(1L, arg0);
        //     return null;
        // }).when(goodsService).isUserHasAuthorizedForGoods(1L);
        Assertions.assertEquals(goods, goodsService.createGoods(goods));
        verify(goodsService).isUserHasAuthorizedForGoods(1L);
    }
}
