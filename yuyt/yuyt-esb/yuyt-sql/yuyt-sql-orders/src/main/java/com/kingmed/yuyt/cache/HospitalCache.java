/**
 * Licensed to Kingmed
 */
package com.kingmed.yuyt.cache;

import com.kingmed.yuyt.model.Hospital;
import com.kingmed.yuyt.util.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zhengjunjie
 */
public class HospitalCache {

    private DataSource dataSource;
    private Map<String, Hospital> hospitals;
    private static final Logger logger = LoggerFactory.getLogger(HospitalCache.class);

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void init() {
        hospitals = new HashMap<String, Hospital>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Long id = null;
        String enable = null;
        String comCode = null;
        String comName = null;
        String hosCode = null;
        String hosName = null;
        String username = null;
        String password = null;

        try {
            con = this.dataSource.getConnection();
            String sql = "select id, enable, com_code, com_name, hos_code, hos_name, username, password from t_hos_token where enable = ?";
            ps = con.prepareCall(sql);
            ps.setString(1, Constants.ENABLE_Y);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getLong(1);
                enable = rs.getString(2);
                comCode = rs.getString(3);
                comName = rs.getString(4);
                hosCode = rs.getString(5);
                hosName = rs.getString(6);
                username = rs.getString(7);
                password = rs.getString(8);
                Hospital hospital = new Hospital();

                hospital.setId(id);
                hospital.setEnable(enable);
                hospital.setCompanyCode(comCode);
                hospital.setComName(comName);
                hospital.setCode(hosCode);
                hospital.setName(hosName);
                hospital.setLisUsername(username);
                hospital.setLisPassword(password);
                hospitals.put(hosCode, hospital);
            }
        } catch (SQLException e) {
            logger.error("��ʼ��hospital����ʧ��", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("�ر�resultʧ��", e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    logger.error("�ر�psʧ��", e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    logger.error("�ر�conʧ��", e);
                }
            }
        }
    }

    public Hospital get(String hosCode) {
        Hospital hospital = this.hospitals.get(hosCode);
        return hospital;
    }
}
