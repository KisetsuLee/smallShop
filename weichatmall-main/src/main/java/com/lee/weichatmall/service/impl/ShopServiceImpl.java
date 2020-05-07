package com.lee.weichatmall.service.impl;

import com.lee.weichatmall.dao.ShopDao;
import com.lee.weichatmall.domain.Shop;
import com.lee.weichatmall.domain.responesesEntity.PageResponse;
import com.lee.weichatmall.domain.status.ShopStatus;
import com.lee.weichatmall.service.ShopService;
import com.lee.weichatmall.service.UserContext;
import com.lee.weichatmall.service.exception.HttpException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-14
 * Time: 14:49
 */
@Service
public class ShopServiceImpl implements ShopService {
    private ShopDao shopDao;

    public ShopServiceImpl(ShopDao shopDao) {
        this.shopDao = shopDao;
    }

    @Override
    public Shop getShopById(Long shopId) {
        return shopDao.getShopById(shopId);
    }

    @Override
    public Shop deleteShopById(Long shopId) {
        Shop shop = getShopById(shopId);
        if (shop == null) {
            throw HttpException.resourceNotFound("店铺信息不存在");
        }
        if (Objects.equals(shop.getOwnerUserId(), UserContext.getCurrentUser().getId())) {
            throw HttpException.forbidden("没有操作此店铺的权限");
        }
        shop.setStatus(ShopStatus.DELETE.getValue());
        shopDao.updateShop(shopId, shop);
        return shopDao.getShopById(shopId);
    }

    @Override
    public PageResponse<Shop> getShopByPage(int pageNum, int pageSize) {
        Long ownerId = UserContext.getCurrentUser().getId();
        List<Shop> shops = shopDao.getShopByPage(ownerId, pageNum, pageSize);
        int shopCount = shopDao.getShopCounts(ownerId);
        return PageResponse.newInstance(pageNum, pageSize, shops, shopCount);
    }

    @Override
    public Shop createShop(Shop shop) {
        return shopDao.createShop(shop);
    }

    @Override
    public Shop updateShopById(Long shopId, Shop shopInfo) {
        if (getShopById(shopId) == null) {
            throw HttpException.resourceNotFound("店铺信息不存在");
        }
        if (Objects.equals(shopInfo.getOwnerUserId(), UserContext.getCurrentUser().getId())) {
            throw HttpException.forbidden("没有操作此店铺的权限");
        }
        shopDao.updateShop(shopId, shopInfo);
        return shopDao.getShopById(shopId);
    }
}
