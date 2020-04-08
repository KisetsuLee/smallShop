package com.lee.weichatmall.service.impl;

import com.lee.weichatmall.dao.GoodsDao;
import com.lee.weichatmall.dao.ShopDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    @InjectMocks
    private GoodsServiceImpl goodsService;

    @Test
    void createGoodsTest() {

    }
}
