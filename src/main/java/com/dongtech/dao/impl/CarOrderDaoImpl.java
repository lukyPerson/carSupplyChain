package com.dongtech.dao.impl;

import com.dongtech.dao.CarOrderDao;
import com.dongtech.util.JDBCUtil;
import com.dongtech.vo.CarOrders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarOrderDaoImpl implements CarOrderDao {
    PreparedStatement ps;
    ResultSet rs;
    @Override
    public Integer inserOrder(CarOrders carOrders) {
        Connection conn = null;
        int i = 0;
        try {
            //1 加载数据库驱动  2 获取数据库连接
            conn = JDBCUtil.getMysqlConn();
            String sql = "insert into car_orders( id,number, price) values (?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,carOrders.getId());
            ps.setObject(2,carOrders.getNumber());
            ps.setBigDecimal(3,carOrders.getPrice());
            i = ps.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5 关闭连接
            JDBCUtil.close(rs, ps, conn);
        }
        return i;
    }
}
