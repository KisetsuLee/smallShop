package com.lee.weichatmall.dao.impl;

import com.lee.weichatmall.dao.ShoppingCartDao;
import com.lee.weichatmall.dao.mapper.ShoppingCartMapper;
import com.lee.weichatmall.dao.mapper.ShoppingCartQueryMapper;
import com.lee.weichatmall.domain.ShoppingCart;
import com.lee.weichatmall.domain.ShoppingCartExample;
import com.lee.weichatmall.domain.shoppingCart.ShoppingCartGoodsWithShop;
import com.lee.weichatmall.domain.status.ShoppingCartStatus;
import com.lee.weichatmall.service.exception.HttpException;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-19
 * Time: 20:15
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Repository
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    private Logger logger = LoggerFactory.getLogger(ShoppingCartDaoImpl.class);

    private SqlSessionFactory sqlSessionFactory;
    private ShoppingCartQueryMapper shoppingCartQueryMapper;
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    public ShoppingCartDaoImpl(SqlSessionFactory sqlSessionFactory, ShoppingCartQueryMapper shoppingCartQueryMapper, ShoppingCartMapper shoppingCartMapper) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.shoppingCartQueryMapper = shoppingCartQueryMapper;
        this.shoppingCartMapper = shoppingCartMapper;
    }

    @Override
    public void addGoodsToShoppingCart(List<ShoppingCart> shoppingCartRows, long userId) {
        // mybatis batch insert
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            ShoppingCartMapper mapper = sqlSession.getMapper(ShoppingCartMapper.class);
            for (ShoppingCart shoppingCartRow : shoppingCartRows) {
                shoppingCartRow.setUserId(userId);
                mapper.insert(shoppingCartRow);
            }
            sqlSession.commit();
        }
    }

    @Override
    public ShoppingCartGoodsWithShop queryShoppingCartByShopAndUser(long shopId, long userId) {
        return shoppingCartQueryMapper.queryShoppingCartByShopAndUser(shopId, userId);
    }

    @Override
    public void deleteShoppingCartGoodsById(long goodsId) {
        ShoppingCartExample shoppingCartExample = new ShoppingCartExample();
        shoppingCartExample.createCriteria().andGoodsIdEqualTo(goodsId).andStatusEqualTo(ShoppingCartStatus.OK.getValue());
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectByExample(shoppingCartExample);
        if (shoppingCarts.size() != 1) {
            logger.error("商品id为 {} 的商品有异常", goodsId);
            throw HttpException.badRequest("购物车此商品异常");
        }
        ShoppingCart shoppingCart = shoppingCarts.get(0);
        shoppingCart.setStatus(ShoppingCartStatus.DELETE.getValue());
        shoppingCartMapper.updateByPrimaryKey(shoppingCart);
    }
}
