package com.lee.weichatmall.dao;

import com.lee.weichatmall.domain.Shop;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-02
 * Time: 11:02
 */
public interface ShopDao {
    Shop findShopById(Long shopId);
}
