package com.dongtech.service.impl;


import com.dongtech.dao.CarGoodsDao;
import com.dongtech.dao.CarOrderDao;
import com.dongtech.dao.impl.CarGoodsDaoImpl;
import com.dongtech.dao.impl.CarOrderDaoImpl;
import com.dongtech.service.CarVGoodsService;
import com.dongtech.util.CarUtil;
import com.dongtech.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class CarGoodsServiceImpl implements CarVGoodsService {

    CarGoodsDao dao = new CarGoodsDaoImpl();
    CarOrderDao carOrderDao = new CarOrderDaoImpl();


    @Override
    public List<CarGoods> queryList(CarGoods carGoods) throws SQLException {
        return dao.queryList(carGoods);
    }

    @Override
    public List<CarOrders> queryOrders() {
        return dao.queryOrders();
    }

    @Override
    public List<CarOrderDetails> queryOrdersDetails(String id) {
        return dao.queryOrdersDetails(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrders(List<Cart> cartList){
        String uuid = "O"+CarUtil.getUUID();
        int sumPrice = 0;
        for (Cart cart : cartList) {
            CarGoods queryGoods = new CarGoods();
            queryGoods.setId(cart.getId());
            List<CarGoods> list = dao.queryList(queryGoods);
            if (list.isEmpty()){
                System.out.println("购物车商品未找到");
                throw new RuntimeException("购物车商品未找到");
            }
            CarGoods carGoods = list.get(0);
            int updatedNum = carGoods.getNum()-cart.getNum();
            if (updatedNum < 0){
                System.out.println("库存不足");
                throw new RuntimeException("库存不足");
            }
            if (dao.updateStock(carGoods.getId(),updatedNum) != 1){
                System.out.println("更新库存失败");
                throw new RuntimeException("更新库存失败");
            }
            sumPrice += cart.getPrice() * cart.getNum();

        }
        CarOrders carOrders = new CarOrders(uuid,uuid, BigDecimal.valueOf(sumPrice));
        carOrderDao.inserOrder(carOrders);
        String odUUid = "";
        for (Cart cart : cartList) {
            odUUid= "OD"+CarUtil.getUUID();
            BigDecimal orderprice = BigDecimal.valueOf(cart.getPrice() * cart.getNum());
            dao.saveOrdersDetails(odUUid,cart.getName(),cart.getNum(),cart.getProduce(),orderprice,uuid);
        }
    }

    public TearDownDetails queryOrdersTearDownDetails(CarOrderDetails c){
        return carOrderDao.queryTearDownDetails(c.getOrderId(),c.getProduce());
    }

    public Integer insertTearDownDetails(TearDownDetails tearDownDetails){
        return carOrderDao.insertTearDownDetails(tearDownDetails);
    }

    public Integer updateTearDownDetails(TearDownDetails tearDownDetails){
        return carOrderDao.updateTearDownDetails(tearDownDetails);
    }

    @Override
    public int[] statisticOrders() {
        List<CarOrders> carOrdersList = dao.queryOrders();
        //k为月份，v为订单数
        HashMap<Integer, Integer> map = new HashMap<>();
        for (CarOrders carOrder : carOrdersList) {
            Integer orderNum = map.get(carOrder.getCreateTs().getMonth());
            if (orderNum == null){
                map.put(carOrder.getCreateTs().getMonth(),1);
            }else {
                orderNum++;
                map.put(carOrder.getCreateTs().getMonth(),orderNum);
            }
        }
        int[] ints = new int[12];
        map.forEach((k,v)->{
            ints[k]=v;
        });

        return ints;
    }

    @Override
    public int[] statisticAmt() {
        List<CarOrders> carOrdersList = dao.queryOrders();
        //k为月份，v为金额
        HashMap<Integer, Integer> map = new HashMap<>();
        for (CarOrders carOrder : carOrdersList) {
            Integer price = map.get(carOrder.getCreateTs().getMonth());
            if (price == null){
                map.put(carOrder.getCreateTs().getMonth(),carOrder.getPrice().intValue());
            }else {
                price += carOrder.getPrice().intValue();
                map.put(carOrder.getCreateTs().getMonth(),price);
            }
        }
        int[] ints = new int[12];
        map.forEach((k,v)->{
            ints[k]=v;
        });

        return ints;
    }



}
