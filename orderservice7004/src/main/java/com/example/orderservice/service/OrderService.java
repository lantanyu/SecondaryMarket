package com.example.orderservice.service;

import com.example.commons.po.byorder;
import com.example.commons.po.cuser;
import com.example.commons.po.order;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

public interface OrderService {
    public order shiyan();

//    public Integer createorder(order order);

    public List<order> getorder(cuser cuser,int ifs);

    public byorder getbyorder(BigInteger orderid);

    public Integer createorder(BigInteger addressid,BigInteger productid, HttpServletRequest request,order order) throws JsonProcessingException;

    public Integer zhufu(BigInteger orderid, BigInteger userid);

    public Integer fahuo(order order);

    public Integer queding(BigInteger orderid, BigInteger userid);

    public Integer tuihuo(byorder byorder, cuser cuser);

    public Integer yestuihuo(BigInteger addressid,BigInteger byorderid,cuser cuser);

    public Integer tuihuofahuo(byorder byorder,cuser cuser);

    public Integer tuihuoqueding(BigInteger byorderid,cuser cuser);

    public void zhifuchaoshi(String json) throws JsonProcessingException;
}
