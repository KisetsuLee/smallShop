package com.lee.weichatmall.dao;

import com.lee.weichatmall.domain.User;

/**
 * Description:
 * User: Lzj
 * Date: 2020-03-24
 * Time: 22:52
 */
public interface UserDao {
    void insertUser(User user);

    User getUserByTel(String tel);
}
