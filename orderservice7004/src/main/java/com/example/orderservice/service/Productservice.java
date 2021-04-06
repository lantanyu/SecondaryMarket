package com.example.orderservice.service;

import com.example.commons.po.CommonResult;
import com.example.commons.po.product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigInteger;

@Component
@FeignClient(value = "product-service7003")
public interface Productservice {
    @PostMapping("/product/shiyan")
    public CommonResult shiyan();

    @GetMapping("/product/ifproductstatus/{productid}")
    public String ifproductstatus(@PathVariable(value = "productid") BigInteger productid);

    @PostMapping("/product/updataifproductstatus/{productid}/{status}")
    public product updataifproductstatus(@PathVariable(value = "productid") BigInteger productid,@PathVariable(value = "status") int status);

    @PostMapping("/product/updataifproductstatuss/{productid}/{status}")
    public product updataifproductstatuss(@PathVariable(value = "productid") BigInteger productid,@PathVariable(value = "status") int status);

}
