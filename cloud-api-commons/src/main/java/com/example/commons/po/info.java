package com.example.commons.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class info implements Serializable {
    private BigInteger infoid;
    private BigInteger userid;
    private double consumeamount;
    private double huodeamount;
    private int attendcount;
    private int fanscount;
    private int productcount;
    private int byproductcount;

}
