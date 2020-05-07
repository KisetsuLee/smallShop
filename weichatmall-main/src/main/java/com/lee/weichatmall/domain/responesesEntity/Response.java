package com.lee.weichatmall.domain.responesesEntity;

public class Response<T> {
    private String message;
    private T data;

    public static <T> Response<T> of(String message, T data) {
        return new Response<T>(message, data);
    }

    public static <T> Response<T> of(T data) {
        return new Response<T>(null, data);
    }

    private Response() {

    }

    private Response(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
