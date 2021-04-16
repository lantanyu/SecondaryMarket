package com.example.userservice.service;


import com.example.commons.po.address;
import com.example.commons.po.auser;
import com.example.commons.po.cuser;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface UserService {
    public cuser denru(String username,String password);
    public auser gldenru(auser auser);
    public Integer zhuche(String username,String password);
    public Integer userupdata(cuser cuser);
    public cuser userbyid(BigInteger userid);
    public Integer concern(BigInteger userid,BigInteger byuserid);
    public Integer concern2(BigInteger userid,BigInteger byuserid);
    public BigInteger addaddress(address address);
    public Integer updataaddress(address address);
    public List<address> getaddress(BigInteger userid);
    public address getaddressbyid(BigInteger addressid);
    public List<Integer> getcount(BigInteger userid);
    public List<cuser> getcountcuser(Integer yie,Integer paixu,BigInteger userid);
    public Map getcountcuser2(Integer yie, Integer paixu, BigInteger userid);
    public Map getfancuser(Integer yie, Integer paixu, BigInteger userid);
    public Map getuserlist(Integer yie,Integer pianyi,cuser cuser);
    public Integer fjuser(cuser cuser);
}
