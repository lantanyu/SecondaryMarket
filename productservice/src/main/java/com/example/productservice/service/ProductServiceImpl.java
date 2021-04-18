package com.example.productservice.service;

import com.example.commons.po.*;
import com.example.productservice.dao.ProductMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public fenlei1 shiyan(){
        fenlei1 fenlei1 = productMapper.shiyan4();
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e){ e.printStackTrace(); }
        return fenlei1 ;
    }
    @Override
    public void shiyan2(){
        productMapper.shiyan();

    }
    @Override
    public Integer creatfenlei1(fenlei1 fenlei1) {
        return productMapper.creatfenlei1(fenlei1);
    }

    @Override
    public Integer creatfenlei2(fenlei2 fenlei2) {
        return productMapper.creatfenlei2(fenlei2);
    }

    @Override
    public Integer creatproduct(product product) {
        return productMapper.creatproduct(product);
    }

    @Override
    public Integer creatcomment(comment comment) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        comment.setTime(timestamp);
        return productMapper.creatcomment(comment);
    }

    @Override
    public Integer creatbycomment(bycomment bycomment) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        bycomment.setTime(timestamp);
        return productMapper.creatbycomment(bycomment);
    }

    @Override
    public List<fenlei1> getfenlei1() {
        return productMapper.getfenlei1();
    }

    @Override
    public List<fenlei1> getfenlei1s() {
        return productMapper.getfenlei1s();
    }

    @Override
    public List<fenlei2> getfenlei2(BigInteger fenlei1id) {
        return productMapper.getfenlei2(fenlei1id);
    }

    @Override
    public Map getproductbyfenlei2(BigInteger fenlei2id, int yie) {
        yie = (yie-1)*10;
        Page<Object> page = PageHelper.offsetPage(yie, 10);
        List<product> products = productMapper.getproductbyfenlei2(fenlei2id);
        Map map = new HashMap();
        map.put("list",products);
        map.put("total",page.getTotal());
        return map;
    }

    @Override
    public List<product> getproductbyname(String name ,int yie) {
        yie = yie*10;
        long total = productMapper.getproductbynamecount(name);
        List<product> products = productMapper.getproductbyname(name,yie);
        if(products.size()>0){
            products.get(0).setTotal(total);
        }
        return products;
    }

    @Override
    public List<product> getproduct(BigInteger userid,int yie) {
        yie = yie*10;
        long total = productMapper.getproductcount(userid);
        List<product> products = productMapper.getproduct(userid,yie);
        if(products.size()>0){
            products.get(0).setTotal(total);
        }
        return products;
    }

    @Override
    public List<product> getproductbyalluser(int yie) {
        yie = yie*10;
        long total = productMapper.getproductbyallusercount();
        List<product> products = productMapper.getproductbyalluser(yie);
        if(products.size()>0){
            products.get(0).setTotal(total);
        }
        return products;
    }

    @Override
    public product getproductxxbyid(BigInteger productid) {
        return productMapper.getproductxxbyid(productid);
    }

    @Override
    public String ifdelect(BigInteger productid,BigInteger userid) {
        Integer method  = productMapper.delecticon(productid,userid);
        if (method==null) {
            return "没找到商品与人";
        } else if (method!=0) {
            return "商品锁定状态不能修改";
        }
        return "正常";
    }

    @Override
    public Integer updataproduct(product product) {
        return productMapper.updataproduct(product);
    }

    @Override
    public Integer updatafenlei1(fenlei1 fenlei1) {
        return productMapper.updatafenlei1(fenlei1);
    }

    @Override
    public Integer updatafenlei1status(fenlei1 fenlei1) {
        return productMapper.updatafenlei1status(fenlei1);
    }

    @Override
    public Integer updatafenlei2status(fenlei2 fenlei2) {
        return productMapper.updatafenlei2status(fenlei2);
    }

    @Override
    public Integer updatafenlei2(fenlei2 fenlei2) {
        if(fenlei2.getIcon()==null){
            return productMapper.updatafenlei2s(fenlei2);
        }else {
            return productMapper.updatafenlei2(fenlei2);
        }
    }

    @Override
    public String ifproductstatus(BigInteger productid) {
        Integer i = productMapper.ifproductstatus(productid);
        if(i==null){
            return "商品不存在";
        }
        if(i==0){
            return "商品正常";
        }
        return "商品已被购买";
    }

    @Override
    public product updataifproductstatus(BigInteger productid, int status) {
        Integer i = productMapper.updataifproductstatus(productid,status);
        if(i==0){
            return null;
        }else{
            return productMapper.getproductifbyid(productid);
        }
    }
    public product updataifproductstatuss(BigInteger productid, int status) {
        Integer i = productMapper.updataifproductstatuss(productid,status);
        if(i==0){
            return null;
        }else{
            return productMapper.getproductifbyid(productid);
        }
    }

    @Override
    public Map getproductlist(Integer yie, Integer pianyi, product product) {
        yie = (yie-1)*pianyi;
        Page<Object> page = PageHelper.offsetPage(yie, pianyi);
        List<product> getproductlist = productMapper.getproductlist(product);
        Map map = new HashMap();
        map.put("list",getproductlist);
        map.put("total",page.getTotal());
        return map;
    }
}
