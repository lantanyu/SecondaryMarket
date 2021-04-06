package com.example.commons.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class order_operate {
    private BigInteger order_operate_id;
    private BigInteger orderid;
    private int operateman;
    private int oprate;
    private Timestamp time;
    private int ifs;
    private String text;
    private String iftext;
}
