/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.sa.dao.impl;

import com.kingmed.dp.sa.dao.SimpleUserDAO;
import com.kingmed.dp.sa.model.SimpleUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zhengjunjie
 */
public class SimpleUserDAOImpl implements SimpleUserDAO {

    private DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleUserDAOImpl.class);

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<SimpleUser> getSimpleUserByRole(String roleName) {
        List<SimpleUser> re = new ArrayList<SimpleUser>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        //String sql = "SELECT t3.name FROM t_role t1,t_user_role_set t2, t_user t3 WHERE t3.id= t2.userid AND t1.id= t2.roleid AND t1.name=?";
        String sql= "SELECT name FROM user";
        try {
            con = this.dataSource.getConnection();
            ps = con.prepareCall(sql);
            //ps.setString(1, roleName);
            rs = ps.executeQuery();
            while(rs.next()){
                String name= rs.getString(1);
                SimpleUser su= new SimpleUser();
                su.setName(name);
                re.add(su);
                System.out.println("name="+name);
                LOGGER.info("name="+name);
            }
        } catch (SQLException e) {
            LOGGER.error("查询数据据异常", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    LOGGER.error("关闭ResultSet出错", ex);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    LOGGER.error("关闭PreparedStatement出错", ex);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    LOGGER.error("关闭Connection出错", ex);
                }
            }
        }
        return re;
    }

}
