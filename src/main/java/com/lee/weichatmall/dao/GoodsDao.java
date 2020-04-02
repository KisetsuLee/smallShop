package com.lee.weichatmall.dao;

import com.lee.weichatmall.domain.Goods;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-02
 * Time: 11:01
 */
public interface GoodsDao {
    Goods insertGoods(Goods goods);

    Goods deleteGoodsById(Long goodsId);
}
