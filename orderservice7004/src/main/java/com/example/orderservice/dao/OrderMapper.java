package com.example.orderservice.dao;

import com.example.commons.po.byorder;
import com.example.commons.po.ck;
import com.example.commons.po.cuser;
import com.example.commons.po.order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Mapper
@Repository
public interface OrderMapper {

    public order shiyan();

    public List<order> getorder1(@Param("userid")BigInteger userid);

    public List<order> getorder2(@Param("userid")BigInteger userid);

    public byorder getbyorder(@Param("orderid")BigInteger orderid);

    public Integer createorder(order order);

    public Integer zhufu(@Param("orderid") BigInteger orderid, @Param("userid") BigInteger userid, @Param("zhifutime")Timestamp zhifutime);

    public Integer fahuo(order order);

    public Integer queding(@Param("orderid") BigInteger orderid, @Param("userid") BigInteger userid, @Param("sohuotime")Timestamp sohuotime);

    public BigInteger getproductid(@Param("orderid") BigInteger orderid);

    public order getorder(@Param("orderid") BigInteger orderid);

    public Integer createbyorder(byorder byorder);

    public Integer uporder(@Param("orderid") BigInteger orderid);

    public Integer yestuihuo(byorder byorder);

    public Integer notuihuo(@Param("byorderid")BigInteger byorderid);

    public Integer readytuihuo(@Param("byorderid")BigInteger byorderid);

    public BigInteger yanzhenbyuser(@Param("byorderid") BigInteger byorderid);

    public BigInteger yanzhenuser(@Param("byorderid") BigInteger byorderid);

    public order yanzhenbyuserorder(@Param("byorderid") BigInteger byorderid);

    public Integer tuihuofahuo(byorder byorder);

    public Integer tuihuoqueding(@Param("byorderid") BigInteger byorderid, @Param("sohuotime")Timestamp sohuotime);

    public Integer zhifuchaoshi(@Param("orderid") BigInteger orderid);

    public List<order> getorderlist(order order);

    public Integer orderct(ck ck);

    public List<ck> getcklist(ck ck);

    public Integer updateck(ck ck);

    public Integer rengong(@Param("orderid") BigInteger orderid);
}
