package com.yc.dao;

import com.yc.bean.Accounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * @program: spring01
 * @description:
 * @author: hgdd
 * @create: 2021-04-15 19:26
 */
@Repository
public class AccountsDaoImpl implements  AccountsDao{
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate=new JdbcTemplate(dataSource);
    }
    @Override
    public int saveAccount(Accounts account) {
        String sql="insert into accounts(balance) values ( ? )";
        KeyHolder keyHolder=new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement pstmt=connection.prepareStatement(sql,new String[]{"accountid"});
            pstmt.setDouble(1,account.getBalance());
            return pstmt;
        },keyHolder);
       //方案一 用匿名内部类
//        jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//               PreparedStatement pstmt=connection.prepareStatement(sql,new String[]{"accountid"});
//               pstmt.setDouble(1,account.getBalance());
//                return pstmt;
//            }
//        },keyHolder);
        return ((BigInteger) keyHolder.getKey()).intValue();
    }

    @Override
    public Accounts updateAccount(Accounts accounts) {
        String sql="update accounts set balance=? where accountid=? ";
        this.jdbcTemplate.update(sql,accounts.getBalance(),accounts.getAccountId());

        return accounts;
    }

    @Override
    public Accounts findAccount(int accountid) {
        String sql="select * from accounts where accountid=?";
        return this.jdbcTemplate.queryForObject(sql,(resultSet,rowNum)->{
            Accounts a=new Accounts();
            a.setBalance(resultSet.getDouble("balance"));
            a.setAccountId(resultSet.getInt("accountid"));
            return  a;
        },accountid);

    }

    @Override
    public List<Accounts> findAll() {
        String sql="select * from accounts";
       List<Accounts> list=this.jdbcTemplate.query(sql,(resultset,RowNum)->{
                Accounts a=new Accounts();
              a.setAccountId(resultset.getInt("accountid"));
              a.setBalance(resultset.getDouble("balance"));
              return a;
       });
//        List<Accounts> list=this.jdbcTemplate.query(sql, new RowMapper<Accounts>() {
//            @Override
//            public Accounts mapRow(ResultSet resultSet, int i) throws SQLException {
//                Accounts a=new Accounts();
//                a.setBalance(resultSet.getDouble("balance"));
//                a.setAccountId(resultSet.getInt("accountid"));
//
//                return a;
//            }
//        });
        return list;
    }

    @Override
    public void delete(int accountid) {
        String sql="delete  from accounts where accountid=?";
        this.jdbcTemplate.update(sql,accountid);

    }
}
