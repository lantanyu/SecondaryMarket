//package com.example.productservice.controller;
//
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice
//public class MyExceptionHandler {
//    @ResponseBody
//    @ExceptionHandler(Exception.class)
//    public Map<String,Object> handleException(Exception e) {
//        Map<String,Object> map = new HashMap<>();
//        map.put("code",400);
//        map.put("message",e.getMessage());
//        System.out.println(e.toString());
//        return map;
//    }
//}
