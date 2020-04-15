package com.lee.weichatmall.domain.status;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-14
 * Time: 15:59
 */
public enum ShopStatus {
    OK(),
    DELETE();

    public String getValue() {
        return name().toLowerCase();
    }
}
