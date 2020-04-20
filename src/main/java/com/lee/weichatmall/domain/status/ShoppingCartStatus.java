package com.lee.weichatmall.domain.status;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-19
 * Time: 21:23
 */
public enum ShoppingCartStatus {
    OK(),
    DELETE();

    public String getValue() {
        return name().toLowerCase();
    }
}
