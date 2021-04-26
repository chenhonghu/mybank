package com.yc.biz;

import com.yc.bean.Accounts;
import com.yc.bean.OpTypes;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@RunWith(
        SpringRunner.class
)
@SpringBootTest
public class AccountServiceImplTest {
    @Autowired
    private AccountService accountService;
    @Test
    @Transactional //在junit中使用，测试完之后 恢复现场
    public void testOpenAccount(){
        Integer accountid=accountService.openAccount(new Accounts(),100);
        System.out.println(accountid);
        Assert.assertNotNull(accountid);
    }
    @Test
    public void testdeposite(){
        Accounts a=new Accounts();
        a.setAccountId(15);
        accountService.deposite(a,100, OpTypes.deposite.getName()," ");

    }
    @Test
    public void testwithdraw(){
        Accounts a=new Accounts();
        a.setAccountId(15);
        accountService.withdraw(a,100,OpTypes.withdraw.getName()," ");

    }
    @Test
    public void testtransfer(){
        Accounts o=new Accounts();
        o.setAccountId(18);
        Accounts i=new Accounts();
        i.setAccountId(19);
        accountService.transfer(i,o,1);
    }
    @Test
    public void testshowBalance(){
        Accounts o=new Accounts();
        o.setAccountId(8);

        accountService.showBalance(o);
    }
}