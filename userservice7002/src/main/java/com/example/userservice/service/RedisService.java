package com.example.userservice.service;

import com.example.commons.po.cuser;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * redis操作Service,
 * 对象和数组都以json形式进行存储
 * Created by macro on 2018/8/7.
 */
public interface RedisService<T> {
    /**
     * 存储数据
     */
//    void set(String key, T data,long expire);
//
//    /**
//     * 获取数据
//     */
//    cuser get(String key);

    void set(String key,T data,long expire) throws JsonProcessingException;

    /**
     * 获取数据
     */
    cuser get(String key) throws JsonProcessingException;

    /**
     * 设置超期时间
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     */
    void remove(String key);

    /**
     * 自增操作
     * @param delta 自增步长
     */
    Long increment(String key, long delta);

}