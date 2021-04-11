package com.example.productservice.service;

import com.example.commons.po.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface ProductService {

    public void shiyan2();

    public fenlei1 shiyan();

    public Integer creatfenlei1(fenlei1 fenlei1);

    public Integer creatfenlei2(fenlei2 fenlei2);

    public Integer creatproduct(product product);

    public Integer creatcomment(comment comment);

    public Integer creatbycomment(bycomment bycomment);

    public List<fenlei1> getfenlei1();

    public List<fenlei1> getfenlei1s();

    public List<fenlei2> getfenlei2(BigInteger fenlei1id);

    public Map getproductbyfenlei2(BigInteger fenlei2id, int yie);

    public List<product> getproductbyname(String name,int yie);

    public List<product> getproduct(BigInteger userid,int yie);

    public List<product> getproductbyalluser(int yie);

    public product getproductxxbyid(BigInteger productid);

    public String ifdelect(BigInteger productid,BigInteger userid);

    public Integer updataproduct(product product);

    public Integer updatafenlei1(fenlei1 fenlei1);

    public Integer updatafenlei1status(fenlei1 fenlei1);

    public Integer updatafenlei2status(fenlei2 fenlei2);

    public Integer updatafenlei2(fenlei2 fenlei2);

    public String ifproductstatus(BigInteger productid);

    public product updataifproductstatus(BigInteger productid,int status);

    public product updataifproductstatuss(BigInteger productid,int status);


}
