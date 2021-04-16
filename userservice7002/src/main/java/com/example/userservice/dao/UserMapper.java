package com.example.userservice.dao;


import com.example.commons.po.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    public Integer yanzhen(@Param("username") String username);
    public Integer createinfo(@Param("userid")BigInteger userid);
    public BigInteger getid(@Param("username") String username);
    public cuser denru(@Param("username") String username,@Param("password") String password);
    public auser gldenru(auser auser);
    public Integer zhuche(@Param("username") String username,@Param("password") String password,@Param("createtime") Timestamp createtime);
    public Integer userupdata(cuser cuser);
    public cuser userbyid(@Param("userid")BigInteger userid);
    public Integer concern(@Param("userid")BigInteger userid,@Param("byuserid")BigInteger byuserid,@Param("createtime") Timestamp createtime);
    public Integer yanzhenconcern(@Param("userid")BigInteger userid,@Param("byuserid")BigInteger byuserid);
    public concern yanzhenconcern2(@Param("userid")BigInteger userid, @Param("byuserid")BigInteger byuserid);
    public Integer upconcern(@Param("status") Integer status, @Param("concernid")BigInteger concernid);
    public Integer addaddress(address address);
    public Integer updataaddress(address address);
    //public address oneaddress(@Param("addressid")BigInteger addressid);
    public List<address> getaddress(@Param("userid")BigInteger userid);
    public address getaddressbyid(@Param("addressid")BigInteger addressid);
    public Integer getattendcount(@Param("userid")BigInteger userid);
    public Integer getfanscount(@Param("userid")BigInteger userid);
    public List<cuser> getcountcuser(@Param("userid")BigInteger userid,@Param("yie") Integer yie);
    public List<cuser> getcountcuser1(@Param("userid")BigInteger userid,@Param("yie") Integer yie);
    public List<cuser> getcountcuser2(@Param("userid")BigInteger userid);
    public List<cuser> getcountcuser3(@Param("userid")BigInteger userid);
    public List<cuser> getfancuser(@Param("userid")BigInteger userid);
    public List<cuser> getuserlist(cuser cuser);
    public Integer fjuser(cuser cuser);
}
