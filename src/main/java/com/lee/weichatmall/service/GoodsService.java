package com.lee.weichatmall.service;

import com.lee.weichatmall.domain.Goods;
import com.lee.weichatmall.domain.responesesEntity.PageResponse;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-02
 * Time: 10:03
 */
public interface GoodsService {
    Goods createGoods(Goods goods);

    Goods deleteGoodsById(Long goodsId);

    Goods updateGoodsById(Long goodsId, Goods goods);

    PageResponse<Goods> getGoodsByPage(Integer pageNum, Integer shopId);

    PageResponse<Goods> getGoodsByPage(Integer num, Integer pageNum, Integer pageSize);
}
