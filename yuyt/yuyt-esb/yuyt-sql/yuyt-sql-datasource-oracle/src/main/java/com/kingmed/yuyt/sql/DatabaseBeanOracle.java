/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
            sta.executeUpdate(
                    "CREATE TABLE orders (id VARCHAR2(50), item VARCHAR2(50), amount VARCHAR2(50), description VARCHAR2(300), processed VARCHAR2(10), consumed VARCHAR2(10))");
        } catch (SQLException e) {
            LOGGER.info("Table orders already exists");
        }
        
        try {
            sta.executeUpdate("create sequence seq_orders_id");
        } catch (SQLException e) {
            LOGGER.info("Sequence seq_orders_id already exists");
        }
    }

    public void destroy() throws SQLException {
        dataSource.getConnection().close();
    }


}
