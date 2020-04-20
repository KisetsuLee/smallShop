package com.lee.weichatmall.service;

import com.lee.weichatmall.domain.shoppingCart.GoodsToShoppingCartList;
import com.lee.weichatmall.domain.shoppingCart.ShoppingCartGoodsWithShop;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-19
 * Time: 20:00
 */
public interface ShoppingCartService {
    ShoppingCartGoodsWithShop addShoppingCart(GoodsToShoppingCartList goodsToShoppingCart);
}
