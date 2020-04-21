package com.lee.weichatmall.dao.mapper;

import com.lee.weichatmall.domain.shoppingCart.ShoppingCartGoodsWithShop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-19
 * Time: 20:30
 */
@Mapper
public interface ShoppingCartQueryMapper {
    ShoppingCartGoodsWithShop queryShoppingCartByShopAndUser(@Param("shopId") long shopId,
                                                             @Param("userId") long userId);

    int queryShoppingCartShopCountByUser(@Param("userId") long userId);

    List<ShoppingCartGoodsWithShop> queryAllShoppingCartByShopAndUserAndPage(@Param("offset") int offset,
                                                                             @Param("limit") int limit,
                                                                             @Param("userId") long userId);
}
