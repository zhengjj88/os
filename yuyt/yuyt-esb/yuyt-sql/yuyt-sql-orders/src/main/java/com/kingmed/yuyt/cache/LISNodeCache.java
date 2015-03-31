/**
 * Lincensed to Kingmed
 */
package com.kingmed.yuyt.cache;

import com.kingmed.yuyt.model.LISNode;
import com.kingmed.yuyt.util.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zhengjunjie
 */
public class LISNodeCache {

    private DataSource dataSource;
    private Map<String, LISNode> lisNodes;
    private static final Logger logger = LoggerFactory.getLogger(LISNodeCache.class);

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void init() {
        lisNodes = new HashMap<String, LISNode>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Long id = null;
        String enable = null;
        String comCode = null;
        String comName = null;
        String address = null;

        try {
            con = this.dataSource.getConnection();
            String sql = "select id, enable, com_code, com_name, address from t_lis_service where enable = ?";
            ps = con.prepareCall(sql);
            ps.setString(1, Constants.ENABLE_Y);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getLong(1);
                enable = rs.getString(2);
                comCode = rs.getString(3);
                comName = rs.getString(4);
                address = rs.getString(5);
                LISNode lisNode = new LISNode();
                lisNode.setId(id);
                lisNode.setEnable(enable);
                lisNode.setComCode(comCode);
                lisNode.setComName(comName);
                lisNode.setAddress(address);
                lisNodes.put(comCode, lisNode);
            }
        } catch (SQLException e) {
            logger.error("初始化LISNode缓存失败", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("关闭result失败", e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    logger.error("关闭ps失败", e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    logger.error("关闭con失败", e);
                }
            }
        }
    }

    public LISNode get(String comCode) {
        LISNode lisNode = this.lisNodes.get(comCode);
        return lisNode;
    }

    public Collection<LISNode> getAll() {
        return this.lisNodes.values();
    }
}
