package com.lee.weichatmall.domain.shoppingCart;

import java.util.List;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-19
 * Time: 20:03
 */
public class ShoppingCartGoodsWithAllShop {
    private List<ShoppingCartGoodsWithShop> allShoppingCartGoods;

    public ShoppingCartGoodsWithAllShop() {
    }

    public ShoppingCartGoodsWithAllShop(List<ShoppingCartGoodsWithShop> allShoppingCartGoods) {
        this.allShoppingCartGoods = allShoppingCartGoods;
    }

    public List<ShoppingCartGoodsWithShop> getAllShoppingCartGoods() {
        return allShoppingCartGoods;
    }

    public void setAllShoppingCartGoods(List<ShoppingCartGoodsWithShop> allShoppingCartGoods) {
        this.allShoppingCartGoods = allShoppingCartGoods;
    }
}
