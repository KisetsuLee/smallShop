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
    private long shopId;
    private Shop shop;
    private List<ShoppingCartGoods> goods;

    public ShoppingCartGoodsWithShop() {
    }

    public ShoppingCartGoodsWithShop(long shopId, Shop shop, List<ShoppingCartGoods> goods) {
        this.shopId = shopId;
        this.shop = shop;
        this.goods = goods;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
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
}
