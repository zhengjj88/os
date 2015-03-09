/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.sa.service.impl;

import com.kingmed.dp.sa.dao.SimpleUserDAO;
import com.kingmed.dp.sa.dao.impl.SimpleUserDAOImpl;
import com.kingmed.dp.sa.model.SimpleUser;
import com.kingmed.dp.sa.model.SimpleUserContainer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zhengjunjie
 */
public class SAServiceHandler {
	private SimpleUserDAO simpleUserDAO;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SimpleUserDAOImpl.class);

	public void setSimpleUserDAO(SimpleUserDAO simpleUserDAO) {
		this.simpleUserDAO = simpleUserDAO;
	}

	public String getAllExperts(String country) {
		StringBuffer json = new StringBuffer("{\"users\" : [");
		if (country != null && !country.trim().isEmpty()) {
			if (country.equalsIgnoreCase("gnblzj")) {
				country = "国内病理专家";
			} else if (country.equalsIgnoreCase("gwblzj")) {
				country = "外国病理专家";
			}else{
				country = null;
			}
		}
		List<SimpleUser> SimpleUserList = simpleUserDAO
				.getSimpleUserByRole(country);
		for (int j = 0; j < SimpleUserList.size(); j++) {
			SimpleUser simpleUser = (SimpleUser) SimpleUserList.get(j);
			String name = simpleUser.getName();
			String id = simpleUser.getId();
			String rolename = simpleUser.getLoginno();
			if (j != SimpleUserList.size() - 1) {
				json.append("{\"id\":\"").append(id).append("\",")
						.append("\"name\":\"").append(name).append("\",")
						.append("\"rolename\":\"").append(rolename)
						.append("\"},");
			} else {
				json.append("{\"id\":\"").append(id).append("\",")
						.append("\"name\":\"").append(name).append("\",")
						.append("\"rolename\":\"").append(rolename)
						.append("\"}");
			}
		}
		json.append("]}");
		return json.toString();
	}
}
