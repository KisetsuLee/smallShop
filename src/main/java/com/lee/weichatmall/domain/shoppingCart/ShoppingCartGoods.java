package com.lee.weichatmall.domain.shoppingCart;

import com.lee.weichatmall.domain.Goods;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-20
 * Time: 12:13
 */
public class ShoppingCartGoods extends Goods {
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
