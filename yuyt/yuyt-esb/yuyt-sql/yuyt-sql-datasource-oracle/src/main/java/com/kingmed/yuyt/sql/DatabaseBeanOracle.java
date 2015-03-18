/**
 * Licensed to Kingmed
 */
package com.kingmed.yuyt.sql;

import java.sql.DatabaseMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class DatabaseBeanOracle {

    private DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseBeanOracle.class);

    public DatabaseBeanOracle(){}

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        try {
            DatabaseMetaData md =this.dataSource.getConnection().getMetaData();
            LOGGER.info("url="+md.getURL());
            LOGGER.info("username="+md.getUserName());
            
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DatabaseBeanOracle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void create() throws SQLException{
        Statement sta = dataSource.getConnection().createStatement();
        try {
            sta.executeQuery("select date from dual");
        } catch (SQLException e) {
            LOGGER.info("Sequence seq_orders_id already exists");
        }
    }
    public void destroy() throws SQLException {
        dataSource.getConnection().close();
    }
}
