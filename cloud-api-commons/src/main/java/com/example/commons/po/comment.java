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
public class comment implements Serializable {
    private BigInteger commentid;
    private BigInteger productid;
    private BigInteger userid;
    private String name;
    private String icon;
    private String text;
    private Timestamp time;
    private int status;
    private List<bycomment> bycomments;
}
