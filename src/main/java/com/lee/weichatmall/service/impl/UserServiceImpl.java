package com.lee.weichatmall.service.impl;

import com.lee.weichatmall.dao.UserDao;
import com.lee.weichatmall.domain.User;
import com.lee.weichatmall.service.UserService;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * User: Lzj
 * Date: 2020-03-24
 * Time: 22:37
 */
@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User createUserIfNotExist(String tel) {
        User user = new User();
        user.setPhone(tel);
        try {
            userDao.insertUser(user);
        } catch (PersistenceException e) {
            return userDao.getUserByTel(tel);
        }
        return user;
    }
}
