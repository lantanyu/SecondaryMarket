package com.example.orderservice.service;

import com.example.commons.po.CommonResult;
import com.example.commons.po.address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigInteger;

@Component
@FeignClient(value = "user-service7002")
public interface Userservice {
    @GetMapping("/user/getaddressbyid/{addressid}")
    public CommonResult<address> getaddressbyid(@PathVariable(value = "addressid") BigInteger addressid);
}
