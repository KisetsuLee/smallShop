package com.lee.weichatmall.service.exception.goodsDao;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-02
 * Time: 11:16
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
