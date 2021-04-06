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
public class byorder implements Serializable {
    private BigInteger byorderid;
    private String sn;
    private double yunprice;
    private int status;
    private int methon;
    private String addressname;
    private String phone;
    private String province;
    private String city;
    private String region;
    private String detailaddresss;
    private Timestamp tijiaotime;
    private Timestamp fahuotime;
    private Timestamp sohuotime;
    private int delectstatus;
    private BigInteger orderid;
    private String tuihuotext;
    private String kuidihao;
    private String kuidi;
    private order order;
}
