package com.lee.weichatmall.dao.impl;

import com.lee.weichatmall.dao.ShopDao;
import com.lee.weichatmall.dao.mapper.ShopMapper;
import com.lee.weichatmall.domain.Shop;
import com.lee.weichatmall.domain.ShopExample;
import com.lee.weichatmall.domain.status.ShopStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-02
 * Time: 11:04
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Repository
public class ShopDaoImpl implements ShopDao {
    private ShopMapper shopMapper;

    @Autowired
    public ShopDaoImpl(ShopMapper shopMapper) {
        this.shopMapper = shopMapper;
    }

    @Override
    public Shop findShopById(Long shopId) {
        return shopMapper.selectByPrimaryKey(shopId);
    }

    @Override
    public List<Shop> getShopByPage(Long ownerId, int pageNum, int pageSize) {
        ShopExample shopExample = new ShopExample();
        shopExample.setLimit(pageSize);
        shopExample.setOffset((pageNum - 1) * pageSize);
        shopExample.createCriteria().andOwnerUserIdEqualTo(ownerId);
        return shopMapper.selectByExample(shopExample);
    }

    @Override
    public int getShopCounts(Long ownerId) {
        ShopExample shopExample = new ShopExample();
        shopExample.createCriteria().andOwnerUserIdEqualTo(ownerId);
        return (int) shopMapper.countByExample(shopExample);
    }

    @Override
    public Shop createShop(Shop shop) {
        int insertSelective = shopMapper.insertSelective(shop);
        shop.setStatus(ShopStatus.OK.getValue());
        shop.setId((long) insertSelective);
        return shop;
    }

    @Override
    public Shop getShopById(Long shopId) {
        return shopMapper.selectByPrimaryKey(shopId);
    }

    @Override
    public Shop updateShop(Long shopId, Shop shopInfo) {
        shopInfo.setId(shopId);
        shopMapper.updateByPrimaryKeySelective(shopInfo);
        return shopMapper.selectByPrimaryKey(shopId);
    }
}
