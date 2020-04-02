package com.lee.weichatmall.service.impl;

import com.lee.weichatmall.dao.GoodsDao;
import com.lee.weichatmall.dao.ShopDao;
import com.lee.weichatmall.domain.Goods;
import com.lee.weichatmall.domain.Shop;
import com.lee.weichatmall.service.GoodsService;
import com.lee.weichatmall.service.UserContext;
import com.lee.weichatmall.service.exception.goodsService.NotAuthorizedForShopException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GoodsServiceImpl implements GoodsService {
    private GoodsDao goodsDao;
    private ShopDao shopDao;

    public GoodsServiceImpl(GoodsDao goodsDao, ShopDao shopDao) {
        this.goodsDao = goodsDao;
        this.shopDao = shopDao;
    }

    @Override
    public Goods createGoods(Goods goods) {
        Shop shop = shopDao.findShopById(goods.getShopId());

        if (Objects.equals(shop.getOwnerUserId(), UserContext.getCurrentUser().getId())) {
            return goodsDao.insertGoods(goods);
        } else {
            throw new NotAuthorizedForShopException("无权访问！");
        }
    }

    @Override
    public Goods deleteGoodsById(Long goodsId) {
        Shop shop = shopDao.findShopById(goodsId);

        if (Objects.equals(shop.getOwnerUserId(), UserContext.getCurrentUser().getId())) {
            return goodsDao.deleteGoodsById(goodsId);
        } else {
            throw new NotAuthorizedForShopException("无权访问！");
        }
    }

}
