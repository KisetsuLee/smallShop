package com.lee.weichatmall.dao.impl;

import com.lee.weichatmall.dao.GoodsDao;
import com.lee.weichatmall.dao.mapper.GoodsMapper;
import com.lee.weichatmall.domain.Goods;
import com.lee.weichatmall.domain.GoodsExample;
import com.lee.weichatmall.domain.goods.GoodsStatus;
import com.lee.weichatmall.service.exception.goodsDao.ResourceNotFoundException;
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
public class GoodsDaoImpl implements GoodsDao {
    private GoodsMapper goodsMapper;

    public GoodsDaoImpl(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    @Override
    public Goods insertGoods(Goods goods) {
        goodsMapper.insert(goods);
        return goods;
    }

    @Override
    public Goods deleteGoodsById(Long goodsId) {
        Goods goods = findGoodsById(goodsId);
        goods.setStatus(GoodsStatus.DELETED_STATUS);
        goodsMapper.updateByPrimaryKey(goods);
        return goods;
    }

    @Override
    public Goods updateGoods(Long goodsId, Goods newGoods) {
        findGoodsById(goodsId);
        newGoods.setId(goodsId);
        goodsMapper.updateByPrimaryKey(newGoods);
        return goodsMapper.selectByPrimaryKey(goodsId);
    }

    @Override
    public Goods findGoodsById(Long goodsId) {
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        if (goods == null) {
            throw new ResourceNotFoundException("商品未找到");
        }
        return goods;
    }

    @Override
    public int getGoodsCounts() {
        GoodsExample goodsExample = new GoodsExample();
        return (int) goodsMapper.countByExample(goodsExample);
    }

    @Override
    public List<Goods> getGoods(Integer pageNum, Integer pageSize) {
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.setLimit(pageSize);
        goodsExample.setOffset((pageNum - 1) * pageSize);
        return goodsMapper.selectByExample(goodsExample);
    }

    @Override
    public int getGoodsCounts(Integer shopId) {
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andShopIdEqualTo(Long.valueOf(shopId));
        return (int) goodsMapper.countByExample(goodsExample);

    }

    @Override
    public List<Goods> getGoods(Integer pageNum, Integer pageSize, Integer shopId) {
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.setLimit(pageSize);
        goodsExample.setOffset((pageNum - 1) * pageSize);
        goodsExample.createCriteria().andShopIdEqualTo(Long.valueOf(shopId));
        return goodsMapper.selectByExample(goodsExample);
    }

}
