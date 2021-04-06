package com.example.userservice.service;

import com.example.commons.po.cuser;


public interface RedisService1<T> {

    void set(String key,T data,long expire);

    /**
     * 获取数据
     */
    T get(String key);

}
