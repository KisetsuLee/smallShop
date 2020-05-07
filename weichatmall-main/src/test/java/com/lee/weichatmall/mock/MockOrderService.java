package com.lee.weichatmall.mock;

import com.lee.weichatmall.service.OrderService;
import org.apache.dubbo.config.annotation.Service;

/**
 * Description:
 * User: Lzj
 * Date: 2020-05-07
 * Time: 14:37
 */
@Service(version = "${weichatmall.service.version}")
public class MockOrderService implements OrderService {
    @Override
    public String say(String msg) {
        return "mock: " + msg;
    }
}
