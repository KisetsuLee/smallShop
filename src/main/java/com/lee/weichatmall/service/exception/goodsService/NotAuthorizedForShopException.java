package com.lee.weichatmall.service.exception.goodsService;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-02
 * Time: 11:10
 */
public class NotAuthorizedForShopException extends RuntimeException {
    public NotAuthorizedForShopException(String message) {
        super(message);
    }
}
