package com.lee.weichatmall.dao.impl;

import com.lee.weichatmall.dao.ShopDao;
import com.lee.weichatmall.dao.mapper.ShopMapper;
import com.lee.weichatmall.domain.Shop;
import org.springframework.stereotype.Repository;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-02
 * Time: 11:04
 */
@Repository
public class ShopDaoImpl implements ShopDao {
    private ShopMapper shopMapper;

    public ShopDaoImpl(ShopMapper shopMapper) {
        this.shopMapper = shopMapper;
    }

    @Override
    public Shop findShopById(Long shopId) {
        return shopMapper.selectByPrimaryKey(shopId);
    }
}
