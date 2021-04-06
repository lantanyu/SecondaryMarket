package com.example.userservice.service;

import com.alibaba.fastjson.JSON;
import com.example.commons.po.cuser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

/**
 * redis操作Service的实现类
 * Created by macro on 2018/8/7.
 */
@Service
public class RedisServiceImpl<T> implements RedisService<T> {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    @Override
//    public void set(String key, T data,long expire) {
//        String json = JSON.toJSONString(data);
//        System.out.println(json);
//        stringRedisTemplate.opsForValue().set(key, json,expire,TimeUnit.SECONDS);
//    }
//
//    @Override
//    public cuser get(String key) {
////        return stringRedisTemplate.opsForValue().get(key);
//        cuser data = JSON.toJavaObject(JSON.parseObject(stringRedisTemplate.opsForValue().get(key)),cuser.class) ;
//        return data;
//    }

    @Override
    public void set(String key, T data,long expire) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);
        System.out.println(json);
        stringRedisTemplate.opsForValue().set(key, json,expire,TimeUnit.SECONDS);
    }

    @Override
    public cuser get(String key) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = stringRedisTemplate.opsForValue().get(key);
        if(json!=null) {
            cuser cuser = mapper.readValue(json,cuser.class);
            return cuser;
        }

        return null;
    }

    @Override
    public boolean expire(String key, long expire) {
        return stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key,delta);
    }
}