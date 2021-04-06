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
public class auser implements Serializable {
    private BigInteger auserid;
    private String username;
    private String password;
    private String name;
    private Timestamp createtime;
    private Timestamp htime;
    private int status;
}
