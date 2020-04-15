package com.lee.weichatmall.service;

import com.lee.weichatmall.domain.Shop;
import com.lee.weichatmall.domain.responesesEntity.PageResponse;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-14
 * Time: 14:49
 */
public interface ShopService {
    PageResponse<Shop> getShopByPage(int pageNum, int pageSize);

    Shop createShop(Shop shop);

    Shop updateShopById(Long id, Shop shopInfo);

    Shop getShopById(Long shopId);

    Shop deleteShopById(Long shopId);
}

