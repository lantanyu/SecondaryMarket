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
public class bycomment implements Serializable {
    private BigInteger bycommentid;
    private BigInteger commentid;
    private BigInteger userid;
    private String name;
    private String icon;
    private String text;
    private Timestamp time;
    private int status;
    private BigInteger byuserid;
}
