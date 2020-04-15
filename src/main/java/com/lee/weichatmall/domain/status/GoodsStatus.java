package com.lee.weichatmall.domain.status;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-02
 * Time: 15:46
 */
public enum GoodsStatus {
    OK(),
    DELETE();

    public String getValue() {
        return name().toLowerCase();
    }
}
