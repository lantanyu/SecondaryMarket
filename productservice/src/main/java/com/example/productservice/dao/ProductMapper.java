package com.example.productservice.dao;

import com.example.commons.po.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Repository
public interface ProductMapper {
    public fenlei1 shiyan();
    public void  shiyan2();
    public void  shiyan3();
    public fenlei1 shiyan4();
    public fenlei1 shiyan5();
    public Integer creatfenlei1(fenlei1 fenlei1);
    public Integer creatfenlei2(fenlei2 fenlei2);
    public Integer creatproduct(product product);
    public Integer creatcomment(comment comment);
    public Integer creatbycomment(bycomment bycomment);
    public List<fenlei1> getfenlei1();
    public List<product> getproductbyfenlei2(@Param("fenlei2id") BigInteger fenlei2id);
    public List<product> getproductbyname(@Param("name") String name,@Param("yie")int yie);
    public long getproductbynamecount(@Param("name") String name);
    public List<product> getproduct(@Param("userid") BigInteger userid,@Param("yie")int yie);
    public long getproductcount(@Param("userid") BigInteger userid);
    public List<product> getproductbyalluser(@Param("yie")int yie);
    public long getproductbyallusercount();
    public product getproductxxbyid(@Param("productid")BigInteger productid);
    public product getproductifbyid(@Param("productid")BigInteger productid);
    public Integer delecticon(@Param("productid")BigInteger productid,@Param("userid")BigInteger userid);
    public Integer updataproduct(product product);
    public Integer updatafenlei1(fenlei1 fenlei1);
    public Integer updatafenlei2(fenlei2 fenlei2);
    public Integer ifproductstatus(@Param("productid")BigInteger productid);
    public Integer updataifproductstatus(@Param("productid")BigInteger productid,@Param("status")int status);
    public Integer updataifproductstatuss(@Param("productid")BigInteger productid,@Param("status")int status);
}
