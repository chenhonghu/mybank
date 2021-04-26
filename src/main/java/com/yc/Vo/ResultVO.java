package com.yc.Vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: mybank
 * @description:
 * @author: hgdd
 * @create: 2021-04-24 20:42
 */
@Data
public class ResultVO<T> implements Serializable {
    private Integer code;
    private T data;
    private String msg;
}
