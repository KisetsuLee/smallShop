package com.lee.weichatmall.dao;

import com.lee.weichatmall.domain.Shop;

import java.util.List;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-02
 * Time: 11:02
 */
public interface ShopDao {
    Shop findShopById(Long shopId);

    List<Shop> getShopByPage(Long ownerId, int pageNum, int pageSize);

    int getShopCounts(Long ownerId);

    Shop createShop(Shop shop);

    Shop getShopById(Long shopId);

    Shop updateShop(Long shopId, Shop shopInfo);
}
