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
public class cuser implements Serializable {
    private BigInteger userid;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String icon;
    private Timestamp createtime;
    private int gender;
    private int xingyongfen;
    private int status;
    private String city;
    private String token;
}
