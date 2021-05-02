package com.example.gateway.filter;

import com.example.commons.po.cuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;


import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class GatewayFilter implements GlobalFilter, Ordered {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String name = request.getPath().toString();
        String regex = ".*/all.*";
        if (name.contains("/all")||name.contains("/gldenru")) {
            List<String> tokens = request.getHeaders().get("token");
            if(tokens==null) {
                return chain.filter(exchange);
            }else {
                Long expires = redisTemplate.getExpire(tokens.get(0));
                if (expires>0) {
                    redisTemplate.expire(tokens.get(0),60L, TimeUnit.MINUTES);
                    return chain.filter(exchange);
                }else {
                    return chain.filter(exchange);
                }

            }
        }
        List<String> token = request.getHeaders().get("token");
        if(name.contains("/glbs")){
            if(token==null){
                String json ="{\"code\":100,\"message\":\"请登入\"}";
                byte[] bits = json.getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(bits);
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
                return response.writeWith(Mono.just(buffer));
            }else {
                Long expire = redisTemplate.getExpire(token.get(0));
                if (expire>0) {
                    Object auser = redisTemplate.opsForValue().get(token.get(0));
                    if(auser.getClass().getName().equals("com.example.commons.po.auser")){
                        redisTemplate.expire(token.get(0),60L, TimeUnit.MINUTES);
                        return chain.filter(exchange);
                    }else {
                        String json ="{\"code\":100,\"message\":\"没有管理权限\"}";
                        byte[] bits = json.getBytes(StandardCharsets.UTF_8);
                        DataBuffer buffer = response.bufferFactory().wrap(bits);
                        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                        response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
                        return response.writeWith(Mono.just(buffer));
                    }
                }else {
                    String json ="{\"code\":100,\"message\":\"登入过期\"}";
                    byte[] bits = json.getBytes(StandardCharsets.UTF_8);
                    DataBuffer buffer = response.bufferFactory().wrap(bits);
                    response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                    response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
                    return response.writeWith(Mono.just(buffer));
                }
            }
        }
        if(token==null) {
            String json ="{\"code\":100,\"message\":\"请登入\"}";
            byte[] bits = json.getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bits);
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return response.writeWith(Mono.just(buffer));
//            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
//            return response.setComplete();
        }else {
            Long expire = redisTemplate.getExpire(token.get(0));
            if (expire>0) {
                redisTemplate.expire(token.get(0),60L, TimeUnit.MINUTES);
                return chain.filter(exchange);
            }else {
                String json ="{\"code\":100,\"message\":\"登入过期\"}";
                byte[] bits = json.getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(bits);
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
                return response.writeWith(Mono.just(buffer));
//                 response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
//                 return response.setComplete();
            }

        }

    }



    @Override
    public int getOrder() {
        return 0;
    }
}

//@Component
//public class GatewayFilter implements GlobalFilter, Ordered {
//    @Autowired
//    private RedisTemplate redisTemplate;
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        ServerHttpRequest request = exchange.getRequest();
//        ServerHttpResponse response = exchange.getResponse();
//        String name = request.getPath().toString();
//        String regex = ".*/all.*";
//        if (name.matches(regex)) {
//            List<String> tokens = request.getHeaders().get("token");
//            if(tokens==null) {
//                return chain.filter(exchange);
//            }else {
//                Long expires = redisTemplate.getExpire(tokens.get(0));
//                if (expires>0) {
//                    redisTemplate.expire(tokens.get(0),60L, TimeUnit.MINUTES);
//                    return chain.filter(exchange);
//                }else {
//                    return chain.filter(exchange);
//                }
//
//            }
//        }
////        ServerHttpResponse response = exchange.getResponse();
////        String json ="{\"code\":100,\"message\":\"sdsds\"}";
////        byte[] bits = json.getBytes(StandardCharsets.UTF_8);
////        DataBuffer buffer = response.bufferFactory().wrap(bits);
////        response.setStatusCode(HttpStatus.UNAUTHORIZED);
////        //指定编码，否则在浏览器中会中文乱码
////        response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
////        return response.writeWith(Mono.just(buffer));
//        List<String> token = request.getHeaders().get("token");
//        if(token==null) {
//            String json ="{\"code\":100,\"message\":\"没有权限\"}";
//            byte[] bits = json.getBytes(StandardCharsets.UTF_8);
//            DataBuffer buffer = response.bufferFactory().wrap(bits);
//            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
//            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
//            return response.writeWith(Mono.just(buffer));
////            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
////            return response.setComplete();
//        }else {
//             Long expire = redisTemplate.getExpire(token.get(0));
//             if (expire>0) {
//                 redisTemplate.expire(token.get(0),60L, TimeUnit.MINUTES);
//                 return chain.filter(exchange);
//             }else {
//                 String json ="{\"code\":100,\"message\":\"没有权限\"}";
//                 byte[] bits = json.getBytes(StandardCharsets.UTF_8);
//                 DataBuffer buffer = response.bufferFactory().wrap(bits);
//                 response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
//                 response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
//                 return response.writeWith(Mono.just(buffer));
////                 response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
////                 return response.setComplete();
//             }
//
//        }
//
//    }
//
//
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}


