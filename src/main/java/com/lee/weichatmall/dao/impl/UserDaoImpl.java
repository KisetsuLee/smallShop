package com.lee.weichatmall.dao.impl;

import com.lee.weichatmall.dao.UserDao;
import com.lee.weichatmall.dao.mapper.UserMapper;
import com.lee.weichatmall.domain.User;
import com.lee.weichatmall.domain.UserExample;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Description:
 * User: Lzj
 * Date: 2020-03-24
 * Time: 22:52
 */
@Repository
public class UserDaoImpl implements UserDao {
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public void insertUser(User user) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.insert(user);
        }
    }

    @Override
    public User getUserByTel(String tel) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            UserExample userExample = new UserExample();
            userExample.createCriteria().andPhoneEqualTo(tel);
            return mapper.selectByExample(userExample).get(0);
        }
    }
}
