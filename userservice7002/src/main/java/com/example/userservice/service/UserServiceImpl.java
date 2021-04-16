package com.example.userservice.service;

import com.example.commons.po.*;
import com.example.userservice.dao.UserMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;


    @Override
    public cuser denru(String username, String password) {
        return userMapper.denru(username,password);
    }

    @Override
    public auser gldenru(auser auser) {
        return userMapper.gldenru(auser);
    }

    @Override
    public Integer zhuche(String username, String password) {
        if(userMapper.yanzhen(username)>0){
            return 3;
        }
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        int i = userMapper.zhuche(username,password,timestamp);
//        BigInteger integer = userMapper.getid(username);
//        if (userMapper.createinfo(integer)!=1)throw new  RuntimeException("xxx");
        return i;
    }

    @Override
    public Integer userupdata(cuser cuser) {
        return userMapper.userupdata(cuser);
    }

    @Override
    public cuser userbyid(BigInteger userid) {
        return userMapper.userbyid(userid);
    }

    @Override
    public Integer concern(BigInteger userid, BigInteger byuserid) {
        if(userMapper.yanzhenconcern(userid,byuserid)>0) return 3;
//        Date date = new Date();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String str = df.format(date);
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        int i = userMapper.concern(userid,byuserid,timestamp);
        return i ;
    }

    public Integer concern2(BigInteger userid, BigInteger byuserid) {
        concern concern = userMapper.yanzhenconcern2(userid,byuserid);
        if(concern==null) {
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            userMapper.concern(userid, byuserid, timestamp);
            return 1;
        }else if (concern.getStatus()==0) {
            userMapper.upconcern(1,concern.getConcernid());
            return 1;
        }else {
            userMapper.upconcern(0,concern.getConcernid());
            return 3;
        }
    }

    @Override
    public BigInteger addaddress(address address) {
        if (userMapper.addaddress(address)>0) {
            return address.getAddressid();
        }
        return null;
    }

    @Override
    public Integer updataaddress(address address) {
        return userMapper.updataaddress(address);
    }

    @Override
    public List<address> getaddress(BigInteger userid) {
        return userMapper.getaddress(userid);
    }

    @Override
    public address getaddressbyid(BigInteger addressid) {
        return userMapper.getaddressbyid(addressid);
    }

    @Override
    public List<Integer> getcount(BigInteger userid) {
        List<Integer> count = new ArrayList<Integer>();
        count.add(userMapper.getattendcount(userid));
        count.add(userMapper.getfanscount(userid));
        return count;
    }

    @Override
    public List<cuser> getcountcuser(Integer yie, Integer paixu,BigInteger userid) {
        yie = yie*30;
        if (paixu==0){
            return userMapper.getcountcuser(userid,yie);
        }else {
            return userMapper.getcountcuser1(userid,yie);
        }
    }

    @Override
    public Map getcountcuser2(Integer yie, Integer paixu, BigInteger userid) {
        yie = (yie-1)*30;
        Page<Object> page = PageHelper.offsetPage(yie, 30);
        if (paixu==0){
            List<cuser> list = userMapper.getcountcuser2(userid);
            Map map = new HashMap();
            map.put("list",list);
            map.put("total",page.getTotal());
            return map;
        }else {
            List<cuser> list = userMapper.getcountcuser3(userid);
            Map map = new HashMap();
            map.put("list",list);
            map.put("total",page.getTotal());
            return map;
        }
    }

    @Override
    public Map getfancuser(Integer yie, Integer paixu, BigInteger byuserid) {
        yie = (yie-1)*30;
        Page<Object> page = PageHelper.offsetPage(yie, 30);
        List<cuser> list = userMapper.getfancuser(byuserid);
        Map map = new HashMap();
        map.put("list",list);
        map.put("total",page.getTotal());
        return map;
    }

    @Override
    public Map getuserlist(Integer yie,Integer pianyi,cuser cuser) {
        yie = (yie-1)*pianyi;
        Page<Object> page = PageHelper.offsetPage(yie, pianyi);
        List<cuser> getuserlist = userMapper.getuserlist(cuser);
        Map map = new HashMap();
        map.put("list",getuserlist);
        map.put("total",page.getTotal());
        return map;
    }

    @Override
    public Integer fjuser(cuser cuser) {
        return userMapper.fjuser(cuser);
    }
}
