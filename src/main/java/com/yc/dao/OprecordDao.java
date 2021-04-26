package com.yc.dao;

import com.yc.bean.Oprecord;

import java.util.List;

/**
 * @program: spring01
 * @description:
 * @author: hgdd
 * @create: 2021-04-15 20:48
 */
public interface OprecordDao {
    public void saveOprecord(Oprecord oprecord);
    public List<Oprecord> findAll(Oprecord oprecord);
    public List<Oprecord> findOprecord(int id);
    public void delete(int id);
    public int update(int id);
}
