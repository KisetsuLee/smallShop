package com.lee.weichatmall.dao.impl;

import com.lee.weichatmall.dao.ShoppingCartDao;
import com.lee.weichatmall.dao.mapper.ShoppingCartMapper;
import com.lee.weichatmall.dao.mapper.ShoppingCartQueryMapper;
import com.lee.weichatmall.domain.ShoppingCart;
import com.lee.weichatmall.domain.shoppingCart.ShoppingCartGoodsWithShop;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
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
    private SqlSessionFactory sqlSessionFactory;
    private ShoppingCartQueryMapper shoppingCartQueryMapper;

    @Autowired
    public ShoppingCartDaoImpl(SqlSessionFactory sqlSessionFactory, ShoppingCartQueryMapper shoppingCartQueryMapper) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.shoppingCartQueryMapper = shoppingCartQueryMapper;
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
}
