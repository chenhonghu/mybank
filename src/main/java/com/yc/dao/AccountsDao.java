package com.yc.dao;

import com.yc.bean.Accounts;

import java.util.List;

/**
 * @program: spring01
 * @description:
 * @author: hgdd
 * @create: 2021-04-15 19:10
 */
public interface AccountsDao {
    public int saveAccount(Accounts account);

    public Accounts updateAccount(Accounts accounts);

    public Accounts findAccount(int accountid);

    public List<Accounts> findAll();
    public void delete(int accountid);
}
