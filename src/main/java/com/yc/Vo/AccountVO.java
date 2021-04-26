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
public class AccountVO implements Serializable {
    private Integer accountId;
    private Double money;
    private Integer inAccountId;
}
