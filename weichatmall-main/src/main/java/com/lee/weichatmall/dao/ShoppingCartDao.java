package com.lee.weichatmall.dao;

import com.lee.weichatmall.domain.ShoppingCart;
import com.lee.weichatmall.domain.shoppingCart.ShoppingCartGoodsWithShop;

import java.util.List;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-19
 * Time: 20:15
 */
public interface ShoppingCartDao {
    void addGoodsToShoppingCart(List<ShoppingCart> shoppingCartRows, long userId);

    ShoppingCartGoodsWithShop queryShoppingCartByShopAndUser(long shopId, long userId);

    void deleteShoppingCartGoodsById(long goodsId);
}
