package com.dongtech.dao;


import com.dongtech.vo.*;

import java.math.BigDecimal;
import java.util.List;

public interface CarGoodsDao {
    List<CarGoods> queryList(CarGoods carGoods) ;

    Integer updateStock(Long id,int opNum);

    List<CarOrders> queryOrders();

    List<CarOrderDetails> queryOrdersDetails(Integer id);

    void saveOrdersDetails(String id, String goods_name, int num, String produce , BigDecimal price, String order_id);

}
