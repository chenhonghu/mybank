package com.yc.bean;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @program: spring01
 * @description:
 * @author: hgdd
 * @create: 2021-04-15 20:48
 */
@Data
public class Oprecord {
    private Integer id;
    private Integer accountid;
    private Double opmoney;
    private Timestamp optime;
    private String optype ;
    private String transferid;
}
