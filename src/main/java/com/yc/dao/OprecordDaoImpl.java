package com.yc.dao;


import com.yc.bean.Oprecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * @program: spring01
 * @description:
 * @author: hgdd
 * @create: 2021-04-15 20:54
 */
@Repository
public class OprecordDaoImpl implements  OprecordDao{
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private void  setDataSource (DataSource dataSource){
        this.jdbcTemplate= new JdbcTemplate(dataSource);
    }
    @Override
    public void saveOprecord(Oprecord oprecord) {
        String sql="insert into oprecord(accountid,opmoney,optime,optype,transferid) values(?,?,?,?,?)";
       jdbcTemplate.update(connection -> {
            PreparedStatement pstmt=connection.prepareStatement(sql);
           pstmt.setDouble(1,oprecord.getAccountid());
           pstmt.setDouble(2,oprecord.getOpmoney());
           pstmt.setTimestamp(3,oprecord.getOptime());
           pstmt.setString(4,oprecord.getOptype());
           pstmt.setString(5,oprecord.getTransferid());
            return pstmt;
        });

    }

    @Override
    public List<Oprecord> findAll(Oprecord oprecord) {
        String sql="select * from oprecord";
        List<Oprecord> list=this.jdbcTemplate.query(sql,(resulset,rowNum)->{
            Oprecord o=new Oprecord();
          o.setId(resulset.getInt("id"));
          o.setAccountid(resulset.getInt("accountid"));
          o.setOpmoney(resulset.getDouble("opmoney"));
          o.setOptime(resulset.getTimestamp("optime"));
          o.setOptype(resulset.getString("optype"));
          o.setTransferid(resulset.getString("transferid"));
           return o;
        });
        return list;
    }

    @Override
    public List<Oprecord> findOprecord(int accountid) {
      String sql="select * from oprecord where accountid=?";
      List<Oprecord> list=this.jdbcTemplate.query(sql,(resultset,rowNum)->{
          Oprecord o=new Oprecord();
          o.setId(resultset.getInt("id"));
          o.setAccountid(resultset.getInt("accountid"));
          o.setOpmoney(resultset.getDouble("opmoney"));
          o.setOptime(resultset.getTimestamp("optime"));
          o.setOptype(resultset.getString("optype"));
          o.setTransferid(resultset.getString("transferid"));
          return o;
      },accountid);
      return  list;
    }

    @Override
    public void delete(int id) {
        String sql="delete from opreord where id=?";
        this.jdbcTemplate.update(sql,id);
    }

    @Override
    public int update(int id) {
       String sql="update set opmoney=1.0 from oprecord where id=?";
       this.jdbcTemplate.update(sql,id);
       return 0;
    }
}
