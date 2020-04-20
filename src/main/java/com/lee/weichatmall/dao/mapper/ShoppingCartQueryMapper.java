package com.lee.weichatmall.dao.mapper;

import com.lee.weichatmall.domain.User;
import com.lee.weichatmall.domain.shoppingCart.ShoppingCartGoodsWithShop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-19
 * Time: 20:30
 */
@Mapper
public interface ShoppingCartQueryMapper {
    ShoppingCartGoodsWithShop queryShoppingCartByShopAndUser(@Param("shopId") long shopId, @Param("userId") long userId);
}
