package com.example.orderservice.service;

import com.example.commons.po.byorder;
import com.example.commons.po.ck;
import com.example.commons.po.cuser;
import com.example.commons.po.order;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface OrderService {
    public order shiyan();

//    public Integer createorder(order order);

    public Map getorder(Integer yie,Integer pianyi,cuser cuser,int ifs);

    public byorder getbyorder(BigInteger orderid);

    public Integer createorder(BigInteger addressid,BigInteger productid, HttpServletRequest request,order order) throws JsonProcessingException;

    public Integer zhufu(BigInteger orderid, BigInteger userid);

    public Integer fahuo(order order);

    public Integer queding(BigInteger orderid, BigInteger userid);

    public Integer tuihuo(byorder byorder, cuser cuser);

    public Integer yestuihuo(BigInteger addressid,BigInteger byorderid,cuser cuser,byorder byorder);

    public Integer notuihuo(BigInteger byorderid,cuser cuser);

    public Integer readytuihuo(BigInteger byorderid,cuser cuser);

    public Integer tuihuofahuo(byorder byorder,cuser cuser);

    public Integer tuihuoqueding(BigInteger byorderid,cuser cuser);

    public void zhifuchaoshi(String json) throws JsonProcessingException;

    public Map getorderlist(Integer yie,Integer pianyi,order order);

    public Integer orderct(ck ck);

    public Map getcklist(Integer yie,Integer pianyi,ck ck);

    public Integer updateck(ck ck);

    public Integer rengong(BigInteger orderid);
}
