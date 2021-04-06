package com.example.commons.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class order implements Serializable {
    private BigInteger orderid;
    private BigInteger productid;
    private BigInteger userid;
    private String sn;
    private double baoyouprice;
    private double price;
    private String productname;
    private String producticon;
    private int status;
    private int method;
    private String addressname;
    private String province;
    private String city;
    private String region;
    private String detailaddresss;
    private String phone;
    private Timestamp tijiaotime;
    private Timestamp zhifutime;
    private Timestamp fahuotime;
    private Timestamp sohuotime;
    private int delectstatus;
    private String kuidihao;
    private BigInteger byuserid;
    private String kuidi;

}
