package com.example.commons.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ck {
    BigInteger ckid;
    String text;
    BigInteger orderid;
    Integer cx;
    Integer status;
    BigInteger userid;
}
