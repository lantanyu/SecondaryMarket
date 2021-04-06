package com.example.commons.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class fenlei1 implements Serializable {
    private BigInteger fenlei1id;
    private String name;
    private String icon;
    private String text;
    private int status;
    private List<fenlei2> fenlei2s;
}
