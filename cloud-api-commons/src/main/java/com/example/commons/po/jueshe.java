package com.example.commons.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class jueshe implements Serializable {
    private BigInteger juesheid;
    private String name;
    private String text;
    private String createtime;
    private int status;
}
