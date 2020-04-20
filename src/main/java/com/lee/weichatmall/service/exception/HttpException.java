package com.lee.weichatmall.service.exception;

import org.springframework.http.HttpStatus;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-10
 * Time: 10:04
 */
public class HttpException extends RuntimeException {
    private String message;
    private int httpStatusCode;

    public static HttpException resourceNotFound(String message) {
        return new HttpException(message, HttpStatus.NOT_FOUND.value());
    }

    public static HttpException forbidden(String message) {
        return new HttpException(message, HttpStatus.FORBIDDEN.value());
    }

    public static HttpException badRequest(String message) {
        return new HttpException(message, HttpStatus.BAD_REQUEST.value());
    }

    private HttpException(String message, int httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }
}
