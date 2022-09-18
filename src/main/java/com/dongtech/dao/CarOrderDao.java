package com.dongtech.dao;

import com.dongtech.vo.CarOrders;
import com.dongtech.vo.TearDownDetails;

public interface CarOrderDao {
    Integer inserOrder(CarOrders carOrders);

    TearDownDetails queryTearDownDetails(String orderId,String produce);

    Integer insertTearDownDetails(TearDownDetails tearDownDetails);

    Integer updateTearDownDetails(TearDownDetails tearDownDetails);
}
