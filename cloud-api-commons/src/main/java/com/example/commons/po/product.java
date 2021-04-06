package com.example.commons.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class product implements Serializable {
    private BigInteger productid;
    private BigInteger fenlei2id;
    private BigInteger userid;
    private String username;
    private String usericon;
    private String pic;
    private String productidname;
    private String text;
    private int deletestatus;
    private int productstatus;
    private int method;
    private double price;
    private double baoyouprice;
    private Timestamp fabutime;
    private String city;
    private int sort;
    private String fenlen2name;
    private List<comment> comments;
    private long total;
}
