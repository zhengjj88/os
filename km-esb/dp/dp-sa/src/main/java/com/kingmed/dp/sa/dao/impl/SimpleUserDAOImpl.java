/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.sa.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kingmed.dp.sa.dao.SimpleUserDAO;
import com.kingmed.dp.sa.model.SimpleUser;

/**
 *
 * @author zhengjunjie
 */
public class SimpleUserDAOImpl implements SimpleUserDAO {

	private DataSource dataSource;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SimpleUserDAOImpl.class);

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<SimpleUser> getSimpleUserByRole(String roleName) {
		List<SimpleUser> re = new ArrayList<SimpleUser>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String appID = "11e4-868e-476f6c83-afc0-bf7d9fc712b0";
		String sql = null;
		if (roleName != null && !roleName.trim().isEmpty()) {
			sql = "select tu.name username, tu.id userid, tr.name rolename from t_user tu, t_user_role_set tur,t_role tr where tu.id = tur.userid and tur.roleid = tr.id and tr.applicationid = ? and tr.name=?";
		} else {
			sql = "select tu.name username, tu.id userid, tr.name rolename from t_user tu, t_user_role_set tur,t_role tr where tu.id = tur.userid and tur.roleid = tr.id and tr.applicationid = '11e4-868e-476f6c83-afc0-bf7d9fc712b0' and (tr.name = '国内病理专家' or tr.name= '外国病理专家')";
		}
		try {
			con = this.dataSource.getConnection();
			ps = con.prepareCall(sql);
			if (roleName != null && !roleName.trim().isEmpty()) {
				ps.setString(1, appID);
				ps.setString(2, roleName);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				String name = rs.getString(1);
				String id = rs.getString(2);
				String rolename = rs.getString(3);
				SimpleUser su = new SimpleUser();
				su.setName(name);
				su.setId(id);
				su.setLoginno(rolename);
				re.add(su);
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
		LOGGER.debug("查询到的病理专家数量=" + re.size());
		return re;
	}

}
