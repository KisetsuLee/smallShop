package com.lee.weichatmall.service.impl;

import com.lee.weichatmall.dao.GoodsDao;
import com.lee.weichatmall.dao.ShoppingCartDao;
import com.lee.weichatmall.dao.mapper.ShoppingCartQueryMapper;
import com.lee.weichatmall.domain.Goods;
import com.lee.weichatmall.domain.ShoppingCart;
import com.lee.weichatmall.domain.User;
import com.lee.weichatmall.domain.shoppingCart.GoodsToShoppingCartItem;
import com.lee.weichatmall.domain.shoppingCart.GoodsToShoppingCartList;
import com.lee.weichatmall.domain.shoppingCart.ShoppingCartGoodsWithShop;
import com.lee.weichatmall.domain.status.ShoppingCartStatus;
import com.lee.weichatmall.service.ShoppingCartService;
import com.lee.weichatmall.service.UserContext;
import com.lee.weichatmall.service.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-19
 * Time: 20:00
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private ShoppingCartDao shoppingCartDao;
    private GoodsDao goodsDao;
    private ShoppingCartQueryMapper shoppingCartQueryMapper;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartDao shoppingCartDao, GoodsDao goodsDao, ShoppingCartQueryMapper shoppingCartQueryMapper) {
        this.shoppingCartDao = shoppingCartDao;
        this.goodsDao = goodsDao;
        this.shoppingCartQueryMapper = shoppingCartQueryMapper;
    }

    @Override
    public ShoppingCartGoodsWithShop addShoppingCart(GoodsToShoppingCartList goodsToShoppingCart) {
        List<Long> goodsIds = goodsToShoppingCart.getGoods().stream()
                .map(GoodsToShoppingCartItem::getId)
                .collect(Collectors.toList());
        if (goodsIds.isEmpty()) {
            throw HttpException.badRequest("没有添加商品id");
        }

        List<Goods> goods = goodsDao.findGoodsByIds(goodsIds);
        Set<Long> shopIds = goods.stream()
                .map(Goods::getShopId)
                .collect(Collectors.toSet());
        if (shopIds.size() != 1) {
            throw HttpException.badRequest("添加的商品id不是同一个商店");
        }

        long shopId = goods.get(0).getShopId();
        List<ShoppingCart> shoppingCartRows = goodsToShoppingCart.getGoods().stream()
                .map(goodsItem -> convertToShoppingCartItemRow(goodsItem, shopId, UserContext.getCurrentUser()))
                .collect(Collectors.toList());
        shoppingCartDao.addGoodsToShoppingCart(shoppingCartRows, UserContext.getCurrentUser().getId());
        return shoppingCartDao.queryShoppingCartByShopAndUser(goods.get(0).getShopId(), UserContext.getCurrentUser().getId());
    }

    @Override
    public List<ShoppingCartGoodsWithShop> getUserShoppingCartByShopPage(int pageNum, int pageSize) {
        if (pageNum < 1 || pageSize < 1) {
            throw HttpException.badRequest("页码参数不正确");
        }
        return shoppingCartQueryMapper.queryAllShoppingCartByShopAndUserAndPage((pageNum - 1) * pageSize,
                pageSize, UserContext.getCurrentUser().getId());
    }

    @Override
    public int getUserShoppingCartShopCount() {
        return shoppingCartQueryMapper.queryShoppingCartShopCountByUser(UserContext.getCurrentUser().getId());
    }

    private ShoppingCart convertToShoppingCartItemRow(GoodsToShoppingCartItem goodsItem, long shopId, User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setGoodsId(goodsItem.getId());
        shoppingCart.setNumber(goodsItem.getNumber());
        shoppingCart.setShopId(shopId);
        shoppingCart.setStatus(ShoppingCartStatus.OK.getValue());
        shoppingCart.setUserId(user.getId());
        shoppingCart.setCreatedAt(new Date());
        shoppingCart.setUpdatedAt(new Date());
        return shoppingCart;
    }
}
