/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.kingmed.dp.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Loads sample data for the sample app since it's an in-memory database.
 */
@Component
public class BootstrapDataPopulator implements InitializingBean {

    private DataSource dataSource;
    
    @SuppressWarnings({"FieldCanBeLocal"})
    private SessionFactory sessionFactory;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Session factory is only injected to ensure it is initialized before this runs
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void afterPropertiesSet() throws Exception {
        //because we're using an in-memory hsqldb for the sample app, a new one will be created each time the
        //app starts, so insert the sample admin user at startup:
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        //jdbcTemplate.execute("insert into T_SYS_PROPERTY values (1, 'NDP-UPMC', 'protocol','http','')");
    }
}
