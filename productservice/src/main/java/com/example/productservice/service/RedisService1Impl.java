package com.example.productservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService1Impl<T> implements RedisService1<T> {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void set(String key, T data, long expire) {
        redisTemplate.opsForValue().set(key,data,expire, TimeUnit.SECONDS);
    }

    @Override
    public T get(String key) {
        return (T)redisTemplate.opsForValue().get(key);
    }
}
