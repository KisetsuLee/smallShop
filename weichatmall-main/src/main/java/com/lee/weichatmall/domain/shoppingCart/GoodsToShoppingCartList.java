package com.lee.weichatmall.domain.shoppingCart;

import java.util.List;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-19
 * Time: 19:53
 */
public class GoodsToShoppingCartList {
    private List<GoodsToShoppingCartItem> goods;

    public GoodsToShoppingCartList() {
    }

    public GoodsToShoppingCartList(List<GoodsToShoppingCartItem> goods) {
        this.goods = goods;
    }

    public List<GoodsToShoppingCartItem> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsToShoppingCartItem> goods) {
        this.goods = goods;
    }
}
