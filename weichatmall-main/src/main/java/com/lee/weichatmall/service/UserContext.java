package com.lee.weichatmall.service;

import com.lee.weichatmall.domain.User;

/**
 * Description:
 * User: Lzj
 * Date: 2020-03-26
 * Time: 11:48
 */
public class UserContext {
    private static ThreadLocal<User> currentUsers = new ThreadLocal<>();

    public static User getCurrentUser() {
        return currentUsers.get();
    }

    public static void setCurrentUser(User user) {
        currentUsers.set(user);
    }
}
