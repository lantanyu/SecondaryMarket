package com.example.orderservice.service;

import com.example.commons.po.*;
import com.example.orderservice.Listener.chashong;
import com.example.orderservice.dao.OrderMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private Productservice productservice;
    @Autowired
    private Userservice userservice;
    @Autowired
    private RedisService1<cuser> redisService;
    @Autowired
    chashong chashong;

    @Override
    public order shiyan() {
        new chashong().shiyan();
        return null;
    }

    @Override
    public Map getorder(Integer yie,Integer pianyi,cuser cuser,int ifs) {
        yie = (yie-1)*pianyi;
        Page<Object> page = PageHelper.offsetPage(yie, pianyi);
        if(ifs==1){
            List<order> orders = orderMapper.getorder1(cuser.getUserid());
            Map map = new HashMap();
            map.put("list",orders);
            map.put("total",page.getTotal());
            return map;

        }else {
            List<order> orders2 = orderMapper.getorder2(cuser.getUserid());
            Map map = new HashMap();
            map.put("list",orders2);
            map.put("total",page.getTotal());
            return map;
        }
    }

    @Override
    public byorder getbyorder(BigInteger orderid) {
        return orderMapper.getbyorder(orderid);
    }

//    @Override
//    public Integer createorder(order order) {
//        return orderMapper.createorder(order);
//    }

    @Override
    public Integer createorder(BigInteger addressid, BigInteger productid, HttpServletRequest request,order order) throws JsonProcessingException {
        product product = productservice.updataifproductstatus(productid,1);
        if(product==null){
            return 0;
        }
        CommonResult<address> commonResult = userservice.getaddressbyid(addressid);
        address address = commonResult.getData();
        String token = request.getHeader("token");
        cuser cuser = redisService.get(token);
        order.setProductid(productid);
        order.setUserid(cuser.getUserid());
        order.setSn(UUID.randomUUID()+"");
        order.setBaoyouprice(product.getBaoyouprice());
        order.setPrice(product.getPrice());
        order.setProductname(product.getProductidname());
        order.setProducticon(product.getPic());
        order.setStatus(0);//0待付款，1待发货，2待收货，3待确定，4交易关闭，5交易完成，6交易退货
        order.setMethod(product.getMethod());
        order.setAddressname(address.getName());
        order.setProvince(address.getProvince());
        order.setCity(address.getCity());
        order.setRegion(address.getRegion());
        order.setDetailaddresss(address.getDetailaddresss());
        order.setPhone(address.getPhone());
        order.setByuserid(product.getUserid());
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        order.setTijiaotime(timestamp);
        if(orderMapper.createorder(order)>0){
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(order);
            chashong.fashong(json);
            return 1;
        }
        return 2;
    }

    @Override
    public Integer zhufu(BigInteger orderid, BigInteger userid) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        return orderMapper.zhufu(orderid,userid,timestamp);
    }

    @Override
    public Integer fahuo(order order) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        order.setFahuotime(timestamp);
        return orderMapper.fahuo(order);
    }

    @Override
    public Integer queding(BigInteger orderid, BigInteger userid) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        product product = productservice.updataifproductstatuss(orderMapper.getproductid(orderid),2);
        if (product!=null){
            int i = orderMapper.queding(orderid,userid,timestamp);
            if (i>0){
                log.info("----模拟转账给卖家，转账成功");
                cuser cuser = new cuser();
                cuser.setUserid(userid);
                cuser.setXingyongfen(10);
                userservice.fjuser(cuser);
                return i;
            }else {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer tuihuo(byorder byorder, cuser cuser) {
        if(byorder.getMethon()==1){
            byorder.setYunprice(0);
        }
        order order = orderMapper.getorder(byorder.getOrderid());
        if(!order.getUserid().equals(cuser.getUserid())) {
            return -3;
        }
        if (order.getMethod()==2||order.getMethod()==3){
            return -2;
        }
        if(order.getStatus()!=2){
            return -1;
        }
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        byorder.setSn(order.getSn());
        byorder.setTijiaotime(timestamp);
        if(orderMapper.createbyorder(byorder)>0){
            return orderMapper.uporder(order.getOrderid());
        }
        throw new  RuntimeException("出错回滚");
    }

    @Override
    public Integer yestuihuo(BigInteger addressid, BigInteger byorderid,cuser cuser,byorder byorder) {
        if(!orderMapper.yanzhenbyuser(byorderid).equals(cuser.getUserid())){
            return -1;
        }
        CommonResult<address> commonResult = userservice.getaddressbyid(addressid);
        address address = commonResult.getData();
        byorder.setByorderid(byorderid);
        byorder.setAddressname(address.getName());
        byorder.setProvince(address.getProvince());
        byorder.setCity(address.getCity());
        byorder.setRegion(address.getRegion());
        byorder.setDetailaddresss(address.getDetailaddresss());
        byorder.setPhone(address.getPhone());
        return orderMapper.yestuihuo(byorder);
    }

    @Override
    public Integer notuihuo(BigInteger byorderid, cuser cuser) {
        if(!orderMapper.yanzhenbyuser(byorderid).equals(cuser.getUserid())){
            return -1;
        }
        return orderMapper.notuihuo(byorderid);
    }

    @Override
    public Integer readytuihuo(BigInteger byorderid, cuser cuser) {
        if(!orderMapper.yanzhenuser(byorderid).equals(cuser.getUserid())){
            return -1;
        }
        return orderMapper.readytuihuo(byorderid);
    }

    @Override
    public Integer tuihuofahuo(byorder byorder, cuser cuser) {
        if(!orderMapper.yanzhenuser(byorder.getByorderid()).equals(cuser.getUserid())){
            return -1;
        }
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        byorder.setFahuotime(timestamp);
        return orderMapper.tuihuofahuo(byorder);
    }

    @Override
    public Integer tuihuoqueding(BigInteger byorderid, cuser cuser) {
        order order = orderMapper.yanzhenbyuserorder(byorderid);
        if(!order.getByuserid().equals(cuser.getUserid())){
            return -2;
        }
        product product = productservice.updataifproductstatuss(order.getProductid(),0);
        if (product!=null){
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            int i = orderMapper.tuihuoqueding(byorderid,timestamp);
            if (i>0){
                System.out.println("----模拟退钱给卖家，退钱成功");
                return i;
            }else {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void zhifuchaoshi(String json) throws JsonProcessingException {
        log.info("==处理订单超时");
        ObjectMapper mapper = new ObjectMapper();
        order order = mapper.readValue(json,order.class);
        int i = orderMapper.zhifuchaoshi(order.getOrderid());
        if(i>0){
            log.info("==订单超时处理成功");
            product product = productservice.updataifproductstatuss(order.getProductid(),0);
        }else {
            log.info("==订单没有超时");
        }
    }

    @Override
    public Map getorderlist(Integer yie,Integer pianyi,order order) {
        yie = (yie-1)*pianyi;
        Page<Object> page = PageHelper.offsetPage(yie, pianyi);
        List<com.example.commons.po.order> getorderlist = orderMapper.getorderlist(order);
        Map map = new HashMap();
        map.put("list",getorderlist);
        map.put("total",page.getTotal());
        return map;
    }

    @Override
    public Integer orderct(ck ck) {
        return orderMapper.orderct(ck);
    }

    @Override
    public Map getcklist(Integer yie, Integer pianyi, ck ck) {
        yie = (yie-1)*pianyi;
        Page<Object> page = PageHelper.offsetPage(yie, pianyi);
        List<com.example.commons.po.ck> getcklist = orderMapper.getcklist(ck);
        Map map = new HashMap();
        map.put("list",getcklist);
        map.put("total",page.getTotal());
        return map;
    }

    @Override
    public Integer updateck(ck ck) {
        return orderMapper.updateck(ck);
    }

    @Override
    public Integer rengong(BigInteger orderid) {
        return orderMapper.rengong(orderid);
    }


}
