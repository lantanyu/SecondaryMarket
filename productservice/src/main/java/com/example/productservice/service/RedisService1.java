package com.example.productservice.service;


public interface RedisService1<T> {

    void set(String key,T data,long expire);

    /**
     * 获取数据
     */
    T get(String key);

}
