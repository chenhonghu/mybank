package com.yc.biz;


import com.yc.bean.Accounts;
import com.yc.bean.OpTypes;
import com.yc.bean.Oprecord;
import com.yc.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * @program: spring01
 * @description:
 * @author: hgdd
 * @create: 2021-04-17 16:42
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountsDao accountsDao;
    @Autowired
    private OprecordDao oprecordDao;
    //开户
    @Override
    public Integer openAccount(Accounts a, double money) {
        a.setBalance(money);
       int accountid=accountsDao.saveAccount(a);
        Oprecord o=new Oprecord();
        o.setAccountid(accountid);
        o.setOpmoney(money);
        o.setOptype(OpTypes.deposite.getName());
        o.setOptime(new Timestamp(System.currentTimeMillis()));
        o.setTransferid(" ");
        oprecordDao.saveOprecord(o);
        return accountid;
    }
    //存钱
    @Override
    public Accounts deposite(Accounts account, double money,String optye,String transferid) {
        Accounts a=this.showBalance(account);

        Oprecord o=new Oprecord();
        o.setAccountid(a.getAccountId());
        o.setOpmoney(money);
        o.setOptype(optye);
        o.setOptime(new Timestamp(System.currentTimeMillis()));
        if(transferid==null){
            o.setTransferid(" ");
        }else {
            o.setTransferid(transferid);
        }
        oprecordDao.saveOprecord(o);
        a.setBalance(a.getBalance()+money);
        accountsDao.updateAccount(a);
        return a;
    }
//取钱
    @Override
    @Transactional
    public Accounts withdraw(Accounts account, double money,String optye,String transferid) {
        Accounts a=this.showBalance(account);

        Oprecord o=new Oprecord();
        o.setAccountid(a.getAccountId());
        o.setOpmoney(money);
        o.setOptype(optye);
        o.setOptime(new Timestamp(System.currentTimeMillis()));
        if(transferid==null){
            o.setTransferid(" ");
        }else {
            o.setTransferid(transferid);
        }
        oprecordDao.saveOprecord(o);
        a.setBalance(a.getBalance()-money);
        accountsDao.updateAccount(a);

        return a;
    }
    @Override
    public Accounts transfer(Accounts inAccount, Accounts outAccount, double money) {
        String uid = UUID.randomUUID().toString();   //转账流水
        this.deposite(inAccount, money, OpTypes.deposite.getName(), uid);
        Accounts newAccounts = this.withdraw(outAccount, money, OpTypes.withdraw.getName(), uid);
        return newAccounts;
    }

    @Override
    @Transactional(readOnly = true)
    public Accounts showBalance(Accounts account) {

        return accountsDao.findAccount(account.getAccountId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Oprecord> findById(Accounts account) {
        return oprecordDao.findOprecord(account.getAccountId());
    }
}
