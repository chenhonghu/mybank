package com.yc.biz;




import com.yc.bean.Accounts;
import com.yc.bean.Oprecord;

import java.util.List;

/**
 * @program: spring01
 * @description:
 * @author: hgdd
 * @create: 2021-04-17 16:42
 */
public interface AccountService {
    //开户
    public Integer openAccount(Accounts account, double money);
    //存钱
    public Accounts deposite(Accounts account,double money,String optye,String transferid);
    //取钱
    public Accounts withdraw(Accounts account,double money,String optye,String transferid);
    //转账
    public Accounts transfer(Accounts inaccount,Accounts outaccounts,double money);
    //查看余额
    public Accounts showBalance(Accounts account);
   //查看日志
    public List<Oprecord> findById(Accounts account);
}
