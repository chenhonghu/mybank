package com.yc.tx;

import com.yc.Vo.AccountVO;
import com.yc.Vo.ResultVO;
import com.yc.bean.Accounts;
import com.yc.bean.OpTypes;
import com.yc.biz.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: mybank
 * @description:
 * @author: hgdd
 * @create: 2021-04-24 20:54
 */
@RestController
@Slf4j//日志
@Api(value = "账户操作接口", tags = {"账户操作接口", "控制层"})

public class AccountsController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value="/openAccounts",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "开户", notes = "根据金额开户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "money", value = "存款金额", required = true, dataType = "Double")
            })
    public @ResponseBody//以json传输
    ResultVO openAccounts(AccountVO ao,ResultVO ro){
        try {
            Accounts a = new Accounts();
            double money = 1;
            if (ao.getMoney() != null && ao.getMoney() > 0) {
                money = ao.getMoney();
            }
            Integer id = accountService.openAccount(a, money);
            a.setAccountId(id);
            a.setBalance(money);
            ro.setCode(1);
            ro.setData(a);
        }catch (Exception ex){
            ex.printStackTrace();
            ro.setCode(0);
            ro.setMsg(ex.getMessage());
        }
        return ro;
    }
    @RequestMapping(value = "/showBalance", method={RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "查看余额", notes = "根据账户编号，查看余额")
    @ApiImplicitParams({@ApiImplicitParam(name = "accountId", value = "自己账户编号", required = true, dataType = "int")
           })
    public @ResponseBody ResultVO showbanlance(AccountVO accountVO,ResultVO resultVO){

        Accounts accounts=new Accounts();
        accounts.setAccountId(accountVO.getAccountId());
        System.out.println(accountVO);
        Accounts accounts1=accountService.showBalance(accounts);
        System.out.println("qweqweq");
      if(accounts1!=null){
          resultVO.setCode(1);//resultVO/
          resultVO.setData(accounts1);
      }else {
          resultVO.setCode(0);
          resultVO.setMsg("没有该账户");
      }
        return resultVO;
    }
    @RequestMapping(value = "/deposite" ,method={RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "存款", notes = "根据账户编号及金额,完成存钱")
    @ApiImplicitParams({@ApiImplicitParam(name = "accountId", value = "自己账户编号", required = true, dataType = "int"),
            @ApiImplicitParam(name = "money", value = "存款金额", required = true, dataType = "Double")
           })
    public @ResponseBody ResultVO deposite(AccountVO accountVO,ResultVO resultVO){
      try {
          Accounts accounts = new Accounts();
          accounts.setAccountId(accountVO.getAccountId());
              Accounts a = accountService.deposite(accounts, accountVO.getMoney(), OpTypes.deposite.getName(), null);
              resultVO.setCode(1);
              resultVO.setData(a);
      }catch (Exception ex){
          resultVO.setCode(0);
          resultVO.setMsg(ex.getMessage());
      }
      return  resultVO;
    }
    @RequestMapping(value = "/withdraw" ,method={RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "取款", notes = "根据账户编号及金额, 完成取款操作")
    @ApiImplicitParams({@ApiImplicitParam(name = "accountId", value = "自己账户编号", required = true, dataType = "int"),
            @ApiImplicitParam(name = "money", value = "取款金额", required = true, dataType = "Double")
           })
    public @ResponseBody ResultVO withdraw(AccountVO accountVO,ResultVO resultVO){
        try {
            Accounts accounts = new Accounts();
            accounts.setAccountId(accountVO.getAccountId());
            if (accountVO.getMoney() > 0 || accountVO.getMoney() != null) {
                Accounts a = accountService.withdraw(accounts, accountVO.getMoney(), OpTypes.withdraw.getName(), null);
                resultVO.setCode(1);
                resultVO.setData(a);
            }
        }catch (Exception ex){
            resultVO.setCode(0);
            resultVO.setMsg(ex.getMessage());
        }
        return  resultVO;
    }
    @RequestMapping(value = "/transfer" ,method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "转账", notes = "根据账户编号及金额, 对方接收账号来完成转账操作，注意此时的金额表示要取的金额")
    @ApiImplicitParams({@ApiImplicitParam(name = "accountId", value = "自己账户编号", required = true, dataType = "int"),
            @ApiImplicitParam(name = "money", value = "转账金额", required = true, dataType = "Double"),
            @ApiImplicitParam(name = "inAccountId", value = "对方接收账号", required = true, dataType = "int")})
    public @ResponseBody ResultVO transfer(AccountVO accountVO,ResultVO resultVO){
        try {
            Accounts ina = new Accounts();
            ina.setAccountId(accountVO.getInAccountId());
            Accounts outa = new Accounts();
            outa.setAccountId(accountVO.getAccountId());
            Accounts a = accountService.transfer(ina, outa, accountVO.getMoney());
            resultVO.setCode(1);
            resultVO.setData(a);
        }catch (Exception ex){
            resultVO.setCode(0);
            resultVO.setMsg(ex.getMessage());
        }
        return resultVO;
    }
}
