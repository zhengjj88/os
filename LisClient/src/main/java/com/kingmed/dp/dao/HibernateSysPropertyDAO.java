/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingmed.dp.dao;

import com.kingmed.dp.model.SysProperty;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author zhengjunjie
 */
@Repository("sysPropertyDAO")
@SuppressWarnings("unchecked")
public class HibernateSysPropertyDAO extends HibernateDao implements SysPropertyDAO {

    @Override
    public List<SysProperty> getAllSysProperties() {
        return getSession().createQuery("from SysProperty order by id").list();
    }

    @Override
    public List<SysProperty> findSysProperties(String section) {
        Criteria cri = getSession().createCriteria(SysProperty.class);
        cri.add(Restrictions.eq("section", section));
        List<SysProperty> list = cri.list();
        return list;
    }

}
