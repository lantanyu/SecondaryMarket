package com.example.userservice.controller;


import com.example.commons.po.*;
import com.example.userservice.service.ProductSerice;
import com.example.userservice.service.RedisService;
import com.example.userservice.service.RedisService1;
import com.example.userservice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisService1<cuser> redisService1;
    @Autowired
    private RedisService1<auser> redisService2;
    @Autowired
    private ProductSerice productSerice;

    @PostMapping("/glbs/shiyan")
    public String shiyan(@RequestBody String name) {

        return "{\"name\":\"sssss\"}" ;

    }
    @PostMapping("/all/denru")
    public CommonResult<cuser> denru(@RequestBody cuser cuser) {
        cuser cuser1 = userService.denru(cuser.getUsername(),cuser.getPassword());
        if(cuser1!=null) {
            String token = UUID.randomUUID()+"";
            redisService1.set(token,cuser1,60*60);
            cuser1.setToken(token);
            return new CommonResult<cuser>(200,"登入成功",cuser1);
        }
        return  new CommonResult(400,"密码错误");
    }
    @PostMapping("/glbs/gldenru")
    public CommonResult<auser> gldenru(@RequestBody auser auser){
        auser auser1  = userService.gldenru(auser);
        if(auser1!=null){
            String token = UUID.randomUUID()+"";
            redisService2.set(token,auser1,60*60);
            auser1.setToken(token);
            return new CommonResult<auser>(200,"登入成功",auser1);
        }
        return  new CommonResult(400,"密码错误");
    }

    @PostMapping("/all/zhuche")
    public CommonResult<String> zhuche(@RequestBody cuser cuser) {
        String regex = "\\S{6,12}";
        if(cuser.getUsername()==null||!cuser.getUsername().trim().matches(regex))return new CommonResult(400,"用户名格式不正确");
        if(cuser.getPassword()==null||!cuser.getPassword().trim().matches(regex))return new CommonResult(400,"密码格式不正确");
        int i = userService.zhuche(cuser.getUsername().trim(),cuser.getPassword().trim());
        if (i==3) {
            return new CommonResult(402,"已经存在用户名");
        }else if (i==0){
            return new CommonResult(400,"注册失败");
        }else {
            return new CommonResult(200,"注册成功");
        }
    }
    @PostMapping("/userupdata")
    public CommonResult<String> userupdata(@RequestParam("file") List<MultipartFile> file,@RequestParam("json") String json, HttpServletRequest request) throws JsonProcessingException {
        String regex_name = "\\S{1,12}";
        String regex_phone= "\\d{11}";
        String regex_city = "\\S{1,60}";
        if(json==null)return new CommonResult(400,"请传入json");
        ObjectMapper mapper = new ObjectMapper();
        cuser cuser = mapper.readValue(json, cuser.class);
        if(cuser.getName()==null||!cuser.getName().trim().matches(regex_name))return new CommonResult(400,"名字格式不正确");
        if(cuser.getPhone()==null||!cuser.getPhone().trim().matches(regex_phone))return new CommonResult(400,"电话格式不正确");
        if(cuser.getCity()==null||!cuser.getCity().trim().matches(regex_city))return new CommonResult(400,"城市格式不正确");
        if(cuser.getGender()!=0&&cuser.getGender()!=1)return new CommonResult(400,"性别格式不正确");
        String token = request.getHeader("token");
        cuser cuser1 = redisService1.get(token);
        cuser1.setName(cuser.getName().trim());
        cuser1.setPhone(cuser.getPhone().trim());
        cuser1.setGender(cuser.getGender());
        cuser1.setCity(cuser.getCity().trim());
        String icon = "";
        if(!file.isEmpty()&&file.size()>0){
            icon= productSerice.logUpload(file.get(0));
        }
        if (icon.equals("error")) {
            return new CommonResult(400,"图片发送失败");
        }
        if(!icon.equals("")){
            cuser1.setIcon(icon);
        }
        if(userService.userupdata(cuser1)==1) {
            redisService1.set(token,cuser1,60*60);
            return new CommonResult(200,"修改成功",cuser1.getIcon());
        }
        return new CommonResult<String>(400,"修改失败");
    }
    @GetMapping("/getuser")
    public CommonResult<cuser> getuser(HttpServletRequest request) {
        String token = request.getHeader("token");
        cuser cuser = redisService1.get(token);
        return new CommonResult<cuser>(200,"成功",cuser);
    }
    @GetMapping("/all/userbyid/{userid}")
    public CommonResult<cuser> userbyid(@PathVariable(name = "userid") BigInteger id) {
       cuser cuser = userService.userbyid(id);
       if(cuser==null) {
           return new CommonResult<cuser>(400,"没找到用户");
       }
       return new CommonResult<cuser>(200,"成功",cuser);
    }
    @PostMapping("/concern/{byuserid}")
    public CommonResult concern(@PathVariable(name = "byuserid") BigInteger byuserid,HttpServletRequest request) {
        String token = request.getHeader("token");
        cuser cuser = redisService1.get(token);
        int i =userService.concern2(cuser.getUserid(),byuserid);
        if(i==1) {
            return new CommonResult(200,"关注成功");
        }else if(i==3){
            return new CommonResult(201,"取消关注");
        }else {
            return new CommonResult(400,"失败");
        }
    }
    @GetMapping("/getaddress")
    public CommonResult getaddress(HttpServletRequest request) {
        String token = request.getHeader("token");
        cuser cuser = redisService1.get(token);
        return new CommonResult(200,"查询成功",userService.getaddress(cuser.getUserid()));
    };
    @GetMapping("/getaddressbyid/{addressid}")
    public CommonResult<address> getaddressbyid(@PathVariable(value = "addressid") BigInteger addressid) {
        return new CommonResult<address>(200,"查询成功",userService.getaddressbyid(addressid));
    }

    @PostMapping("/addaddress")
    public  CommonResult<address> addaddress(@RequestBody address address,HttpServletRequest request) {
        String regex_name = ".{1,12}";
        String regex_phone= "\\d{11}";
        String regex_province_city_region = "[\\u4e00-\\u9fa5]{1,20}";
        String regex_detailaddresss = ".{1,50}";
        if(address.getName()==null||!address.getName().trim().matches(regex_name))return new CommonResult(400,"名字格式不正确");
        if(address.getPhone()==null||!address.getPhone().trim().matches(regex_phone))return new CommonResult(400,"电话格式不正确");
        if(address.getProvince()==null||!address.getProvince().trim().matches(regex_province_city_region))return new CommonResult(400,"省格式不正确");
        if(address.getCity()==null||!address.getCity().trim().matches(regex_province_city_region))return new CommonResult(400,"市格式不正确");
        if(address.getRegion()==null||!address.getRegion().trim().matches(regex_province_city_region))return new CommonResult(400,"区格式不正确");
        if(address.getDetailaddresss()==null||!address.getDetailaddresss().trim().matches(regex_detailaddresss))return new CommonResult(400,"地址格式不正确");
        String token = request.getHeader("token");
        cuser cuser = redisService1.get(token);
        address.setUserid(cuser.getUserid());
        BigInteger addresssid = userService.addaddress(address);
        if (addresssid!=null){
            address.setAddressid(addresssid);
            address.setIfstatus(1);
            return new CommonResult<address>(200,"成功",address);
        }
        return new CommonResult(400,"添加失败");
    }
    @PostMapping("/updataaddress")
    public CommonResult<address> updataaddress(@RequestBody address address,HttpServletRequest request) {
        String regex_name = ".{1,12}";
        String regex_phone= "\\d{11}";
        String regex_province_city_region = "[\\u4e00-\\u9fa5]{1,20}";
        String regex_detailaddresss = ".{1,50}";
        if(address.getAddressid()==null) return new CommonResult(400,"请传入id");
        if(address.getName()==null||!address.getName().trim().matches(regex_name))return new CommonResult(400,"名字格式不正确");
        if(address.getPhone()==null||!address.getPhone().trim().matches(regex_phone))return new CommonResult(400,"电话格式不正确");
        if(address.getProvince()==null||!address.getProvince().trim().matches(regex_province_city_region))return new CommonResult(400,"省格式不正确");
        if(address.getCity()==null||!address.getCity().trim().matches(regex_province_city_region))return new CommonResult(400,"市格式不正确");
        if(address.getRegion()==null||!address.getRegion().trim().matches(regex_province_city_region))return new CommonResult(400,"区格式不正确");
        if(address.getDetailaddresss()==null||!address.getDetailaddresss().trim().matches(regex_detailaddresss))return new CommonResult(400,"地址格式不正确");
        String token = request.getHeader("token");
        cuser cuser = redisService1.get(token);
        address.setUserid(cuser.getUserid());
        if(userService.updataaddress(address)>0){
            return new CommonResult(200,"成功");
        }
        else {
            return new CommonResult(400,"修改失败");
        }

    }
    @GetMapping("getcount")
    public CommonResult<List<Integer>> getcount(HttpServletRequest request) {
        String token = request.getHeader("token");
        cuser cuser = redisService1.get(token);
        return new CommonResult<List<Integer>>(200,"成功",userService.getcount(cuser.getUserid()));
       // return userService.getcount(cuser.getUserid());
    }
    @GetMapping("/all/getcountid/{concernid}")
    public CommonResult<List<Integer>> getcountid(@PathVariable(name = "concernid") BigInteger concernid) {
        return new CommonResult<List<Integer>>(200,"成功",userService.getcount(concernid));
        //return new CommonResult<List<Integer>>(200,"成功");
    }
    @GetMapping("getcountcuser/{yie}/{paixu}/{userid}")
    public CommonResult<List<cuser>> getcountcuser(@PathVariable(name = "yie") Integer yie,@PathVariable(name = "paixu") Integer paixu,@PathVariable(name = "userid") BigInteger userid ) {
        if(paixu!=0&&paixu!=1) {
            return new CommonResult(400,"排序规则没有");
        }
            return new CommonResult<List<cuser>>(200,"成功",userService.getcountcuser(yie,paixu,userid));
    }
    @GetMapping("getcountcuser3/{yie}/{paixu}/{userid}")
    public CommonResult<Map> getcountcuser3(@PathVariable(name = "yie") Integer yie, @PathVariable(name = "paixu") Integer paixu, @PathVariable(name = "userid") BigInteger userid ) {
        if(paixu!=0&&paixu!=1) {
            return new CommonResult(400,"排序规则没有");
        }
        return new CommonResult<Map>(200,"成功",userService.getcountcuser2(yie,paixu,userid));
    }
    @GetMapping("getcountcuser2/{yie}/{paixu}")
    public CommonResult<List<cuser>> getcountcuser2(@PathVariable(name = "yie") Integer yie,@PathVariable(name = "paixu") Integer paixu,HttpServletRequest request) {
        if(paixu!=0&&paixu!=1) {
            return new CommonResult(400,"排序规则没有");
        }
        String token = request.getHeader("token");
        cuser cuser = redisService1.get(token);
        return new CommonResult<List<cuser>>(200,"成功",userService.getcountcuser(yie,paixu,cuser.getUserid()));
    }
    @GetMapping("getfancuser/{yie}/{paixu}/{userid}")
    public CommonResult<Map> getfancuser(@PathVariable(name = "yie") Integer yie, @PathVariable(name = "paixu") Integer paixu, @PathVariable(name = "userid") BigInteger byuserid ) {
        if(paixu!=0&&paixu!=1) {
            return new CommonResult(400,"排序规则没有");
        }
        return new CommonResult<Map>(200,"成功",userService.getfancuser(yie,paixu,byuserid));
    }
    @GetMapping("/glbs/getuserlist/{yie}/{pianyi}/{name}")
    public CommonResult<Map> getuserlist(@PathVariable(name = "yie") Integer yie, @PathVariable(name = "pianyi") Integer pianyi,@PathVariable(name = "name") String name) {
        cuser cuser = new cuser();
        if(name.equals("没有数据")){
            name="";
        }
        cuser.setName(name);
        return new CommonResult<Map> (200,"成功",userService.getuserlist(yie,pianyi, cuser));
    }
    @GetMapping("/glbs/getuserlistbyid/{userid}")
    public CommonResult<Map> getuserlistbyid(@PathVariable(name = "userid") BigInteger userid) {
        cuser cuser = new cuser();
        cuser.setUserid(userid);
        return new CommonResult<Map> (200,"成功",userService.getuserlist(1,1,cuser));
    }
    @GetMapping("/glbs/getuserlistbystatus/{yie}/{pianyi}/{status}/{name}")
    public CommonResult<Map> getuserlistbystatus(@PathVariable(name = "yie") Integer yie, @PathVariable(name = "pianyi") Integer pianyi,@PathVariable(name = "status") Integer status,@PathVariable(name = "name") String name) {
        cuser cuser = new cuser();
        cuser.setStatus(status);
        if(name.equals("没有数据")){
            name="";
        }
        cuser.setName(name);
        return new CommonResult<Map> (200,"成功",userService.getuserlist(yie,pianyi,cuser));
    }



}
