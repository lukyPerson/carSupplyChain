package com.dongtech.dao.impl;

import com.dongtech.dao.CarOrderDao;
import com.dongtech.util.JDBCUtil;
import com.dongtech.vo.CarOrders;
import com.dongtech.vo.TearDownDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    @Override
    public TearDownDetails queryTearDownDetails(String orderId, String produce) {
        Connection conn = null;
        int i = 0;
        ArrayList<TearDownDetails> tearDownDetailsList = new ArrayList<>();
        try {
            //1 加载数据库驱动  2 获取数据库连接
            conn = JDBCUtil.getMysqlConn();
            String sql = "select * from tear_down_details where order_id=? and produce=?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,orderId);
            ps.setObject(2,produce);
            rs = ps.executeQuery();
            while (rs.next()){
                TearDownDetails tearDownDetails = new TearDownDetails();
                tearDownDetails.setId(rs.getLong("id"));
                tearDownDetails.setOrderId(rs.getString("order_id"));
                tearDownDetails.setProduce(rs.getString("produce"));
                tearDownDetails.setCargoods_name(rs.getString("cargoods_name"));
                tearDownDetails.setNum(rs.getInt("num"));
                tearDownDetailsList.add(tearDownDetails);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5 关闭连接
            JDBCUtil.close(rs, ps, conn);
        }
        return tearDownDetailsList.size()==0?null:tearDownDetailsList.get(0);
    }

    public Integer insertTearDownDetails(TearDownDetails tearDownDetails){
        Connection conn = null;
        int i = 0;
        try {
            //1 加载数据库驱动  2 获取数据库连接
            conn = JDBCUtil.getMysqlConn();
            String sql = "insert into tear_down_details(order_id, produce, cargoods_name, num) values (?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,tearDownDetails.getOrderId());
            ps.setObject(2,tearDownDetails.getProduce());
            ps.setObject(3,tearDownDetails.getCargoods_name());
            ps.setInt(4,tearDownDetails.getNum());
            i = ps.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5 关闭连接
            JDBCUtil.close(rs, ps, conn);
        }
        return i;
    }

    public Integer updateTearDownDetails(TearDownDetails tearDownDetails){
        Connection conn = null;
        int i = 0;
        try {
            //1 加载数据库驱动  2 获取数据库连接
            conn = JDBCUtil.getMysqlConn();
            String sql = "update tear_down_details set order_id=?, produce=?, cargoods_name=?, num=? WHERE ID=?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,tearDownDetails.getOrderId());
            ps.setObject(2,tearDownDetails.getProduce());
            ps.setObject(3,tearDownDetails.getCargoods_name());
            ps.setInt(4,tearDownDetails.getNum());
            ps.setObject(5,tearDownDetails.getId());
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
