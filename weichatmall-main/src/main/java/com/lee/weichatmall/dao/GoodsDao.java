package com.lee.weichatmall.dao;

import com.lee.weichatmall.domain.Goods;

import java.util.List;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-02
 * Time: 11:01
 */
public interface GoodsDao {
    Goods insertGoods(Goods goods);

    Goods deleteGoodsById(Long goodsId);

    Goods updateGoods(Long goodsId, Goods newGoods);

    Goods findGoodsById(Long goodsId);

    int getGoodsCounts(Long shopId);

    List<Goods> getGoods(Integer pageNum, Integer pageSize, Long shopId);

    List<Goods> findGoodsByIds(List<Long> goodsIds);
}
