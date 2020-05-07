package com.lee.weichatmall.service.impl;

import com.lee.weichatmall.service.OrderService;
import org.apache.dubbo.config.annotation.Service;

/**
 * Description:
 * User: Lzj
 * Date: 2020-05-07
 * Time: 11:01
 */
@Service(version = "${weichatmall.service.version}")
public class OrderServiceImpl implements OrderService {
    @Override
    public String say(String msg) {
        return msg;
    }
}
