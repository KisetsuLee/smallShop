package com.lee.weichatmall.service;

import com.lee.weichatmall.domain.User;

/**
 * Description:
 * User: Lzj
 * Date: 2020-03-24
 * Time: 22:37
 */
public interface UserService {
    User createUserIfNotExist(String tel);
}
