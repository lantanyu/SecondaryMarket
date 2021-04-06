package com.example.productservice.controller;


import com.example.commons.po.*;
import com.example.productservice.service.LogService;
import com.example.productservice.service.ProductService;
import com.example.productservice.service.RedisService1;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private RedisService1<product> redisService1;
    @Autowired
    private RedisService1<cuser> redisService2;
    @Autowired
    private LogService logService;

    @PostMapping("/shiyan")
    public CommonResult shiyan(){
        return new CommonResult(200,"chen",productService.shiyan());
    }
    @PostMapping("/shiyan22")
    public CommonResult shiyan2(){
        productService.shiyan2();
        return new CommonResult(200,"chen");
    }

    @PostMapping("/creatfenlei1")
    public CommonResult<fenlei1> creatfenlei1(@RequestBody fenlei1 fenlei1) {
        String regex = "[\\u4e00-\\u9fa5]{1,6}";
        String regex_text = "[\\u4e00-\\u9fa5]{1,50}";
        if(fenlei1.getName()==null||!fenlei1.getName().trim().matches(regex))return new CommonResult(400,"名字格式不正确");
        if(fenlei1.getText()==null||!fenlei1.getText().trim().matches(regex_text))return new CommonResult(400,"text格式不正确");
        if (productService.creatfenlei1(fenlei1)>0){
            fenlei1.setStatus(1);
            return new CommonResult<fenlei1>(200,"创建成功",fenlei1);
        }
        return new CommonResult(400,"创建失败");
    }
    @PostMapping("/creatfenlei2")
    public CommonResult<fenlei2> creatfenlei2(@RequestParam("files")List<MultipartFile> files, @RequestParam("fenlei1id")BigInteger fenlei1id, @RequestParam("name")String name, @RequestParam("text")String text) {
        String regex = "[\\u4e00-\\u9fa5]{1,6}";
        String regex_text = "[\\u4e00-\\u9fa5]{1,50}";
        if(fenlei1id==null)return new CommonResult(400,"id格式不正确");
        if(name==null||!name.trim().matches(regex))return new CommonResult(400,"名字格式不正确");
        if(text==null||!text.trim().matches(regex_text))return new CommonResult(400,"text格式不正确");
        fenlei2 fenlei2 = new fenlei2();
        fenlei2.setFenlei1id(fenlei1id);
        fenlei2.setName(name);
        fenlei2.setText(text);
        fenlei2.setStatus(1);
        if (!files.isEmpty()&&files.size()>0) {
            String icon=logService.Uploads(files);
            if(!icon.equals("error")){
                fenlei2.setIcon(icon);
            }else {
                return new CommonResult(400,"图片发送失败");
            }
        }
        if(productService.creatfenlei2(fenlei2)>0) {
            return new CommonResult<fenlei2>(200,"创建成功",fenlei2);
        }
        return new CommonResult(400,"创建失败");
    }
    @PostMapping("/creatproduct")
    public CommonResult<product> creatproduct(@RequestParam("files")List<MultipartFile> files, @RequestParam("json") String json, HttpServletRequest request) throws JsonProcessingException {
        String regex_name = ".{1,40}";
        String regex_text = "(.|\\s){1,450}";
        String regex_city = "\\S{1,60}";
        ObjectMapper mapper = new ObjectMapper();
        if(files.isEmpty()||files.size()<1)return new CommonResult(400,"需要至少一张图片");
        if(json==null)return new CommonResult(400,"请传入json");
//{"fenlei2id":2,"productidname":"小米10","text":"通缩不去","method":0,"price":300.5,"baoyouprice":20,"city":"上海"}
        product product = mapper.readValue(json, product.class);
        BigInteger fenlei2id= product.getFenlei2id();
        String productidname= product.getProductidname();
        String text = product.getText();
        int method= product.getMethod();
        double price= product.getPrice();
        double baoyouprice= product.getBaoyouprice();
        String city= product.getCity();

        if(fenlei2id==null)return new CommonResult(400,"请输入分类id");
        if(productidname==null||!productidname.matches(regex_name))return new CommonResult(400,"名字格式不正确");
        if(text==null||!text.matches(regex_text))return new CommonResult(400,"文本格式不正确");
//        if(deletestatus!=0&&deletestatus!=1)return new CommonResult(400,"删除状态格式不正确");
//        if(productstatus!=0&&productstatus!=1&&productstatus!=2&&productstatus!=3)return new CommonResult(400,"商品状态格式不正确");//0正常，1在订单，2完成
        if(method!=0&&method!=1&&method!=2&&method!=3)return new CommonResult(400,"交易格式不正确");//0正常，1包邮，2面交，3自提
        if(price<0||price>100000000)return new CommonResult(400,"价格格式不对");
        if(baoyouprice<0||baoyouprice>100000)return new CommonResult(400,"包邮价格格式不对");
        if(city==null||!city.matches(regex_city))return new CommonResult(400,"城市格式不正确");
        if(method==1)baoyouprice=0;

        String token = request.getHeader("token");
        cuser cuser = redisService2.get(token);
        String icon=logService.Uploads(files);
        if(!icon.equals("error")){
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            product.setUserid(cuser.getUserid());
            product.setUsername(cuser.getName());
            product.setUsericon(cuser.getIcon());
            product.setBaoyouprice(baoyouprice);
            product.setPic(icon);
            product.setFabutime(timestamp);

        }else {
            return new CommonResult(400,"图片发送失败");
        }

        if (productService.creatproduct(product)>0){
            return new CommonResult<product>(200,"商品发布成功",product);
        }else {
            return new CommonResult(400,"失败");
        }

    }
    @PostMapping("/creatcomment")
    public CommonResult<comment> creatcomment(@RequestBody comment comment, HttpServletRequest request) {
        //{"productid":1,"text":"真尼玛帅"}
        String regex_text = "\\S{1,450}";
        if(comment.getText()==null||!comment.getText().matches(regex_text))return new CommonResult(400,"文本格式不正确");
        if(comment.getProductid()==null)return new CommonResult(400,"请输入商品id");

        String token = request.getHeader("token");
        cuser cuser = redisService2.get(token);
        comment.setUserid(cuser.getUserid());
        comment.setName(cuser.getName());
        comment.setIcon(cuser.getIcon());
        if (productService.creatcomment(comment)>0){
            return new CommonResult<comment>(200,"成功",comment);
        }
        return new CommonResult(400,"失败");
    }
    @PostMapping("/creatbycomment")
    public CommonResult<bycomment> creatbycomment(@RequestBody bycomment bycomment, HttpServletRequest request) {
        //{"commentid":3,"text":"真尼玛帅","byuserid":8}
        String regex_text = "\\S{1,450}";
        if(bycomment.getText()==null||!bycomment.getText().matches(regex_text))return new CommonResult(400,"文本格式不正确");
        if(bycomment.getCommentid()==null)return new CommonResult(400,"请输入id");
        if(bycomment.getByuserid()==null)return new CommonResult(400,"请输入用户id");

        String token = request.getHeader("token");
        cuser cuser = redisService2.get(token);
        bycomment.setUserid(cuser.getUserid());
        bycomment.setName(cuser.getName());
        bycomment.setIcon(cuser.getIcon());
        if(productService.creatbycomment(bycomment)>0) {
            return new CommonResult<bycomment>(200,"成功",bycomment);
        }
        return new CommonResult(400,"失败");
    }
    @GetMapping("/all/getfenlei1")
    public CommonResult<List<fenlei1>> getfenlei1() {
        return new CommonResult<List<fenlei1>>(200,"成功", productService.getfenlei1());
    }
    @GetMapping("/all/getproductbyfenlei2/{fenlei2id}/{yie}")
    public CommonResult<Map> getproductbyfenlei2(@PathVariable(name = "fenlei2id") BigInteger fenlei2id, @PathVariable(value = "yie") int yie) {
        return new CommonResult<Map>(200,"成功", productService.getproductbyfenlei2(fenlei2id,yie));
    }
    @GetMapping("/all/getproductbyname/{name}/{yie}")
    public CommonResult<List<product>> getproductbyname(@PathVariable(name = "name") String name,@PathVariable(value = "yie") int yie){
//CREATE FULLTEXT INDEX ft_index ON product (productidname,text) WITH PARSER ngram;
        return new CommonResult<List<product>>(200,"成功", productService.getproductbyname(name,yie));
    }
    @GetMapping("/getproduct/{yie}")
    public CommonResult<List<product>> getproduct(@PathVariable(value = "yie") int yie,HttpServletRequest request) {
        String token = request.getHeader("token");
        cuser cuser = redisService2.get(token);
        return new CommonResult<List<product>>(200,"成功", productService.getproduct(cuser.getUserid(),yie));
    }
    @GetMapping("/all/getproductbyid/{yie}/{userid}")
    public CommonResult<List<product>> getproductbyid(@PathVariable(value = "yie") int yie,@PathVariable(name = "userid") BigInteger userid) {
        return new CommonResult<List<product>>(200,"成功", productService.getproduct(userid,yie));
    }
    @GetMapping("/all/getproductbyalluser/{yie}")
    public CommonResult<List<product>> getproductbyalluser(@PathVariable(value = "yie") int yie) {
        return new CommonResult<List<product>>(200,"成功", productService.getproductbyalluser(yie));
    }
    @GetMapping("/all/getproductxxbyid/{productid}")
    public CommonResult<product> getproductxxbyid(@PathVariable(name = "productid") BigInteger productid ){
        return new CommonResult<product>(200,"成功", productService.getproductxxbyid(productid));
    }
    @PostMapping("/updataproduct")
    public CommonResult updataproduct(@RequestParam("files")List<MultipartFile> files, @RequestParam("json") String json, HttpServletRequest request) throws JsonProcessingException {
        //{"productid":1,"fenlei2id":2,"pic":";3b8e7273-dbb3-4dc9-9a7a-eafc967ada5d.jpg;2e296c02-bebf-497d-ad22-c2f1bf7dbf17.jpg","productidname":"小米10","text":"小米10,8g,不刀","method":0,"price":300.5,"baoyouprice":20,"city":"上海"}
        String regex_name = ".{1,40}";
        String regex_text = "(.|\\s){1,450}";
        String regex_city = "\\S{1,60}";
        String regex_pic = "\\S{0,254}";
        if(json==null)return new CommonResult(400,"请传入json");
        ObjectMapper mapper = new ObjectMapper();
        product product = mapper.readValue(json, product.class);
        BigInteger productid=product.getProductid();
        BigInteger fenlei2id= product.getFenlei2id();
        String pic = product.getPic();
        String productidname= product.getProductidname();
        String text = product.getText();
        int method= product.getMethod();
        double price= product.getPrice();
        double baoyouprice= product.getBaoyouprice();
        String city= product.getCity();

        if(productid==null)return new CommonResult(400,"请输入商品id");
        if(fenlei2id==null)return new CommonResult(400,"请输入分类id");
        if(pic==null||!pic.matches(regex_pic))return new CommonResult(400,"图片格式不正确");
        if(productidname==null||!productidname.matches(regex_name))return new CommonResult(400,"名字格式不正确");
        if(text==null||!text.matches(regex_text))return new CommonResult(400,"文本格式不正确");
        if(method!=0&&method!=1&&method!=2&&method!=3)return new CommonResult(400,"交易格式不正确");//0正常，1包邮，2面交，3自提
        if(price<0||price>100000000)return new CommonResult(400,"价格格式不对");
        if(baoyouprice<0||baoyouprice>100000)return new CommonResult(400,"包邮价格格式不对");
        if(city==null||!city.matches(regex_city))return new CommonResult(400,"城市格式不正确");
        if(method==1)baoyouprice=0;

        String token = request.getHeader("token");
        cuser cuser = redisService2.get(token);
        String ifs = productService.ifdelect(productid,cuser.getUserid());
        if(ifs.equals("没找到商品与人")){
            return new CommonResult(400,"请仔细核对商品id或用户异常");
        }else if (ifs.equals("商品锁定状态不能修改")){
            return new CommonResult(400,"商品锁定状态不能修改");
        }

        String icon=logService.Uploads(files);
        String ificon  =icon+pic;
        if(ificon.length()>205) {
            return new CommonResult(400,"图片格式长度异常");
        }
        if(!icon.equals("error")){
            product.setBaoyouprice(baoyouprice);
            product.setPic(ificon);

        }else {
            return new CommonResult(400,"图片发送失败");
        }

        if (productService.updataproduct(product)>0){
            return new CommonResult(200,"修改成功",ificon);
        }
        return new CommonResult(400,"修改失败");
    }
    @PostMapping("/updatafenlei1")
    public CommonResult updatafenlei1(@RequestBody fenlei1 fenlei1) {
        String regex = "[\\u4e00-\\u9fa5]{1,6}";
        String regex_text = "[\\u4e00-\\u9fa5]{1,50}";
        if(fenlei1.getFenlei1id()==null)return new CommonResult(400,"fenleiid格式不正确");
        if(fenlei1.getName()==null||!fenlei1.getName().trim().matches(regex))return new CommonResult(400,"名字格式不正确");
        if(fenlei1.getText()==null||!fenlei1.getText().trim().matches(regex_text))return new CommonResult(400,"text格式不正确");
        if (fenlei1.getStatus()!=1&&fenlei1.getStatus()!=0)return new CommonResult(400,"status格式不正确");
        if (productService.updatafenlei1(fenlei1)>0){
            return new CommonResult<fenlei1>(200,"修改成功",fenlei1);
        }
        return new CommonResult(400,"修改失败");
    }
    @PostMapping("/updatafenlei2")
    public CommonResult updatafenlei2(@RequestParam("files")List<MultipartFile> files, @RequestParam("json") String json, HttpServletRequest request) throws JsonProcessingException {
        String regex = "[\\u4e00-\\u9fa5]{1,6}";
        if(json==null)return new CommonResult(400,"请传入json");
        ObjectMapper mapper = new ObjectMapper();
        fenlei2 fenlei2 = mapper.readValue(json, fenlei2.class);
        if(fenlei2.getFenlei2id()==null)return new CommonResult(400,"fenlei2id格式不正确");
        if(fenlei2.getName()==null||!fenlei2.getName().trim().matches(regex))return new CommonResult(400,"名字格式不正确");
        if (fenlei2.getStatus()!=1&&fenlei2.getStatus()!=0)return new CommonResult(400,"status格式不正确");

        String icon=logService.Uploads(files);
        if (icon.equals("error")) {
            return new CommonResult(400,"图片发送失败");
        }else if(icon.equals("")){
           if(productService.updatafenlei2(fenlei2)>0) return new CommonResult<fenlei2>(200,"成功",fenlei2);
        }else {
            fenlei2.setIcon(icon);
            if(productService.updatafenlei2(fenlei2)>0) return new CommonResult<fenlei2>(200,"成功",fenlei2);
        }
        return new CommonResult(400,"失败");
    }
    @GetMapping("/ifproductstatus/{productid}")
    public String ifproductstatus(@PathVariable(value = "productid") BigInteger productid){

        return productService.ifproductstatus(productid);
    }
    @PostMapping("/updataifproductstatus/{productid}/{status}")
    public product updataifproductstatus(@PathVariable(value = "productid") BigInteger productid,@PathVariable(value = "status") int status){
        return productService.updataifproductstatus(productid,status);
    }
    @PostMapping("/updataifproductstatuss/{productid}/{status}")
    public product updataifproductstatuss(@PathVariable(value = "productid") BigInteger productid,@PathVariable(value = "status") int status){
        return productService.updataifproductstatuss(productid,status);
    }

    @PostMapping(value = "/upload")
    public String logUpload(@RequestParam("file") MultipartFile file)  {
        return logService.logUpload(file);
    }
    @PostMapping(value = "/uploads")
    public String logUploads(@RequestParam("files") List<MultipartFile> files)  {
        return logService.Uploads(files);
    }
}
