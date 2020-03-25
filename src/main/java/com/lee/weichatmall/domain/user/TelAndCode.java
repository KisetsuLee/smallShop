package com.lee.weichatmall.domain.user;

/**
 * Description:
 * User: Lzj
 * Date: 2020-03-24
 * Time: 17:07
 */
public class TelAndCode {
    private String tel;
    private String code;

    public TelAndCode(String tel, String code) {
        this.tel = tel;
        this.code = code;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
