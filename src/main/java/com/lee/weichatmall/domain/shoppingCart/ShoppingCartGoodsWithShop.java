package com.lee.weichatmall.domain.shoppingCart;

import com.lee.weichatmall.domain.Shop;

import java.util.List;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-19
 * Time: 20:03
 */
public class ShoppingCartGoodsWithShop {
    private Shop shop;
    private List<ShoppingCartGoods> goods;

    public ShoppingCartGoodsWithShop() {
    }

    public ShoppingCartGoodsWithShop(Shop shop, List<ShoppingCartGoods> goods) {
        this.shop = shop;
        this.goods = goods;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<ShoppingCartGoods> getGoods() {
        return goods;
    }

    public void setGoods(List<ShoppingCartGoods> goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "ShoppingCartGoodsWithShop{" +
                "shop=" + shop +
                ", goods=" + goods +
                '}';
    }
}
