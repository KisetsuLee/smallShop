// package com.lee.weichatmall.service.impl;
//
// import com.lee.weichatmall.dao.GoodsDao;
// import com.lee.weichatmall.dao.ShopDao;
// import com.lee.weichatmall.dao.impl.GoodsDaoImpl;
// import com.lee.weichatmall.dao.impl.ShopDaoImpl;
// import com.lee.weichatmall.domain.Goods;
// import com.lee.weichatmall.domain.Shop;
// import org.junit.jupiter.api.Test;
// import org.junit.runner.RunWith;
// import org.mockito.Mockito;
// import org.powermock.api.mockito.PowerMockito;
// import org.powermock.core.classloader.annotations.PrepareForTest;
// import org.powermock.modules.junit4.PowerMockRunner;
//
// import static org.mockito.Mockito.times;
//
//
// /**
//  * Description:
//  * User: Lzj
//  * Date: 2020-04-13
//  * Time: 21:48
//  */
// @RunWith(PowerMockRunner.class)
// @PrepareForTest(GoodsServiceImpl.class)
// class GoodsServiceUnitPrivateMethodTest {
//     @Test
//     void checkGoods() throws Exception {
//         Goods goods = new Goods();
//         Shop shop = new Shop();
//         GoodsDao goodsDao = PowerMockito.mock(GoodsDaoImpl.class);
//         ShopDao shopDao = PowerMockito.mock(ShopDaoImpl.class);
//         GoodsServiceImpl goodsService = PowerMockito.spy(new GoodsServiceImpl(goodsDao, shopDao));
//
//         Mockito.when(shopDao.findShopById(Mockito.anyLong())).thenReturn(shop);
//         shop.setOwnerUserId(1L);
//
//         PowerMockito.doReturn(shop)
//                 .when(goodsService, "checkGoodsInCorrectShop", Mockito.anyLong());
//         PowerMockito.doNothing()
//                 .when(goodsService, "isUserHasAuthorizedForGoods", shop.getOwnerUserId());
//
//         Mockito.when(goodsDao.insertGoods(goods)).thenReturn(goods);
//
//         goodsService.createGoods(goods);
//
//         PowerMockito.verifyPrivate(goodsService, times(1))
//                 .invoke("checkGoodsInCorrectShop", Mockito.anyLong());
//         PowerMockito.verifyPrivate(goodsService, times(1))
//                 .invoke("isUserHasAuthorizedForGoods", shop.getOwnerUserId());
//     }
// }
