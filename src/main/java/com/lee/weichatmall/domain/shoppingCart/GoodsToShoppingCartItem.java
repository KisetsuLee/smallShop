package com.lee.weichatmall.domain.shoppingCart;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-19
 * Time: 18:09
 */
public class GoodsToShoppingCartItem {
    private long id;
    private int number;

    public GoodsToShoppingCartItem(long id, int number) {
        this.id = id;
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
