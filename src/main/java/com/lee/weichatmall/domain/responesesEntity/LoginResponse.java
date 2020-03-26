package com.lee.weichatmall.domain.responesesEntity;

import com.lee.weichatmall.domain.User;

/**
 * Description:
 * User: Lzj
 * Date: 2020-03-26
 * Time: 14:52
 */
public class LoginResponse {
    private boolean isLogin;
    private User user;

    private LoginResponse(boolean isLogin, User user) {
        this.isLogin = isLogin;
        this.user = user;
    }

    public static LoginResponse loginResponse(User user) {
        return new LoginResponse(true, user);
    }

    public static LoginResponse notLoginResponse() {
        return new LoginResponse(false, null);
    }
}
