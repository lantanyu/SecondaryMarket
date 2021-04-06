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
public class concern implements Serializable {
    private BigInteger concernid;
    private BigInteger userid;
    private BigInteger byuserid;
    private Timestamp createtime;
    private int status;
}
