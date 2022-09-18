package com.dongtech.service;

import com.dongtech.vo.*;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
@Service
public interface CarVGoodsService {

    List<CarGoods> queryList(CarGoods carGoods) throws SQLException;



    List<CarOrders> queryOrders();

    List<CarOrderDetails> queryOrdersDetails(String id);

    void saveOrders(List<Cart> cartList);

    TearDownDetails queryOrdersTearDownDetails(CarOrderDetails c);

    Integer insertTearDownDetails(TearDownDetails tearDownDetails);

    Integer updateTearDownDetails(TearDownDetails tearDownDetails);

    List<StatisticsOrderNum> statisticOrders();
}
