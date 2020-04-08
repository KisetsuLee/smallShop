package com.lee.weichatmall.service.impl;

import com.lee.weichatmall.dao.GoodsDao;
import com.lee.weichatmall.dao.ShopDao;
import com.lee.weichatmall.domain.Goods;
import com.lee.weichatmall.domain.Shop;
import com.lee.weichatmall.domain.responesesEntity.PageResponse;
import com.lee.weichatmall.service.GoodsService;
import com.lee.weichatmall.service.UserContext;
import com.lee.weichatmall.service.exception.goodsService.GoodsInfoWrongForShopException;
import com.lee.weichatmall.service.exception.goodsService.NotAuthorizedForShopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GoodsServiceImpl implements GoodsService {
    private GoodsDao goodsDao;
    private ShopDao shopDao;

    @Autowired
    public GoodsServiceImpl(GoodsDao goodsDao, ShopDao shopDao) {
        this.goodsDao = goodsDao;
        this.shopDao = shopDao;
    }

    @Override
    public Goods createGoods(Goods goods) {
        Shop shop = checkGoodsInCorrectShop(goods.getShopId());
        isUserHasAuthorizedForGoods(shop.getOwnerUserId());
        return goodsDao.insertGoods(goods);
    }

    @Override
    public Goods deleteGoodsById(Long goodsId) {
        Goods goods = goodsDao.findGoodsById(goodsId);
        Shop shop = checkGoodsInCorrectShop(goods.getShopId());
        isUserHasAuthorizedForGoods(shop.getOwnerUserId());
        return goodsDao.deleteGoodsById(goodsId);
    }

    @Override
    public Goods updateGoodsById(Long goodsId, Goods newGoods) {
        Goods goods = goodsDao.findGoodsById(goodsId);
        Shop shop = checkGoodsInCorrectShop(goods.getShopId());
        isUserHasAuthorizedForGoods(shop.getOwnerUserId());
        return goodsDao.updateGoods(goodsId, newGoods);
    }

    @Override
    public PageResponse<Goods> getGoodsByPage(Integer pageNum, Integer pageSize) {
        int count = goodsDao.getGoodsCounts();
        List<Goods> goods = goodsDao.getGoods(pageNum, pageSize);
        return PageResponse.newInstance(pageNum, pageSize, goods, count);
    }

    @Override
    public PageResponse<Goods> getGoodsByPage(Integer pageNum, Integer pageSize, Integer shopId) {
        checkGoodsInCorrectShop(Long.valueOf(shopId));
        int count = goodsDao.getGoodsCounts(shopId);
        List<Goods> goods = goodsDao.getGoods(pageNum, pageSize, shopId);
        return PageResponse.newInstance(pageNum, pageSize, goods, count);
    }

    private Shop checkGoodsInCorrectShop(Long shopId) {
        Shop shop = shopDao.findShopById(shopId);
        if (shop == null) {
            throw new GoodsInfoWrongForShopException("商品信息有误！");
        }
        return shop;
    }

    private void isUserHasAuthorizedForGoods(Long ownerUserId) {
        if (!Objects.equals(ownerUserId, UserContext.getCurrentUser().getId())) {
            throw new NotAuthorizedForShopException("无权访问！");
        }
    }

}
