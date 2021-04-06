package com.example.commons.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class fenlei2 implements Serializable {
    private BigInteger fenlei2id;
    private BigInteger fenlei1id;
    private String name;
    private String icon;
    private String text;
    private int status;
}
