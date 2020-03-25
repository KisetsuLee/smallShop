package com.lee.weichatmall.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 * User: Lzj
 * Date: 2020-03-24
 * Time: 22:47
 */
@Service
public class VerificationCodeCheckService {
    private Map<String, String> telNumberToCorrectCode = new ConcurrentHashMap<>();

    public void addCode(String tel, String correctCode) {
        telNumberToCorrectCode.put(tel, correctCode);
    }

    public String getCorrectCode(String tel) {
        return telNumberToCorrectCode.get(tel);
    }
}
