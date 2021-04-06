package com.example.commons.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class address implements Serializable {
    private BigInteger addressid;
    private BigInteger userid;
    private String name;
    private String province;
    private String city;
    private String region;
    private String detailaddresss;
    private String phone;
    private int ifstatus;
}
