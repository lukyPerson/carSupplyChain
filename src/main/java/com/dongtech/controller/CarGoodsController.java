package com.dongtech.controller;

import com.dongtech.service.CarVGoodsService;
import com.dongtech.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gzl
 * @date 2020-04-15
 * @program: springboot-jsp
 * @description: ${description}
 */
@Controller
@RequestMapping("cargoods")
public class CarGoodsController {


    @Resource
    private  CarVGoodsService carVGoodsService;


    /**
     * @Author gzl
     * @Description：清空购物车
     * @Exception
     */

    /**
     * @Author gzl
     * @Description：查询商品列表
     * @Exception
     */
    @RequestMapping("/queryList")
    public ModelAndView queryList(CarGoods carGoods)  {
        List<CarGoods> list = new ArrayList<>();
        try {
            list = carVGoodsService.queryList(carGoods);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /**
         * 模型和视图
         * model模型: 模型对象中存放了返回给页面的数据
         * view视图: 视图对象中指定了返回的页面的位置
         */
        ModelAndView modelAndView = new ModelAndView();
        //将返回给页面的数据放入模型和视图对象中
        modelAndView.addObject("list", list);
        //指定返回的页面位置
        modelAndView.setViewName("carGoods/list");
        return modelAndView;
    }


    /**
     * @Author gzl
     * @Description：查询下单列表
     * @Exception
     * @Date： 2020/4/19 11:59 PM
     */
    @RequestMapping("/queryorders")
    public ModelAndView QueryOrders()  {
        List<CarOrders> list =carVGoodsService.queryOrders();
        /**
         * 模型和视图
         * model模型: 模型对象中存放了返回给页面的数据
         * view视图: 视图对象中指定了返回的页面的位置
         */
        ModelAndView modelAndView = new ModelAndView();
        //将返回给页面的数据放入模型和视图对象中
        modelAndView.addObject("list", list);
        //指定返回的页面位置
        modelAndView.setViewName("carGoods/orderlist");
        return modelAndView;
    }

    /**
     * @Author gzl
     * @Description：查询下单详情列表
     * @Exception
     * @Date： 2020/4/19 11:59 PM
     */
    @RequestMapping("/queryordersdetails")
    public ModelAndView QueryOrdersDetails(String id)  {
        List<CarOrderDetails> list =carVGoodsService.queryOrdersDetails(id);
        /**
         * 模型和视图
         * model模型: 模型对象中存放了返回给页面的数据
         * view视图: 视图对象中指定了返回的页面的位置
         */
        ModelAndView modelAndView = new ModelAndView();
        //将返回给页面的数据放入模型和视图对象中
        modelAndView.addObject("list", list);
        //指定返回的页面位置
        modelAndView.setViewName("carGoods/orderdetailslist");
        return modelAndView;
    }

    @RequestMapping("addGoodsToCart")
    @ResponseBody
    public String addGoodsToCart(Integer goodsId, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        // 从cookie中获取购物车列表
        List<Cart> carVos = getCartInCookie(response, request);
        Cookie cookie_2st;
        CarGoods carGoods = new CarGoods();
        try {
            CarGoods carGoods1 = new CarGoods();
            carGoods1.setId(Long.parseLong(goodsId+""));
            List<CarGoods> cList = carVGoodsService.queryList(carGoods1);
            carGoods = cList.get(0);
        }catch (SQLException e){
            e.printStackTrace();
        }
        //如果购物车列表为空
        if (carVos.size() <= 0){
            Cart cartVO = new Cart();
            cartVO.setNum(1);
            cartVO.setPrice(carGoods.getPrice().intValue());
            cartVO.setId(carGoods.getId());
            cartVO.setType(carGoods.getType());
            cartVO.setName(carGoods.getName());
            cartVO.setProduce(carGoods.getProduce());
            cartVO.setDescription(carGoods.getDescription());
            //将当前传来的商品添加到购物车列表
            carVos.add(cartVO);
            if (getCookie(request) == null){
                cookie_2st = new Cookie("cart", URLEncoder.encode(makeCookieValue(carVos),"utf-8"));
                cookie_2st.setPath("/");
                cookie_2st.setMaxAge(60*30);
                response.addCookie(cookie_2st);
            }else{
                cookie_2st = getCookie(request);
                cookie_2st.setPath("/");
                cookie_2st.setMaxAge(60*30);
                cookie_2st.setValue(URLEncoder.encode(makeCookieValue(carVos)));
                response.addCookie(cookie_2st);
            }
        }
        //当获取的购物车列表不为空时
        else {
            int bj =0;
            for (Cart cart : carVos) {
                //如果购物车中存在该商品则数量+1
                if (cart.getId().equals(Long.valueOf(goodsId))){
                    cart.setNum(cart.getNum()+1);
                    bj =1;
                    break;
                }
            }
            if (bj == 0){
                Cart cartVO = new Cart();
                cartVO.setPrice(carGoods.getPrice().intValue());
                cartVO.setId(carGoods.getId());
                cartVO.setType(carGoods.getType());
                cartVO.setName(carGoods.getName());
                cartVO.setProduce(carGoods.getProduce());
                cartVO.setDescription(carGoods.getDescription());
                cartVO.setNum(1);
                carVos.add(cartVO);
            }
            cookie_2st = getCookie(request);
            cookie_2st.setPath("/");
            //设置cookie有效时间为30分钟
            cookie_2st.setMaxAge(60*30);
            cookie_2st.setValue(URLEncoder.encode(makeCookieValue(carVos)));
            response.addCookie(cookie_2st);
        }
        return carVos.toString();
    }
    /**
     * 获取购物车列表
     *
     * @param response
     * @param request
     * @return 购物车列表
     * @throws UnsupportedEncodingException 抛出异常
     */
    @RequestMapping("/getCart")
    public ModelAndView getCart(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        List<Cart> cartInCookie = getCartInCookie(response, request);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list",cartInCookie);
        modelAndView.setViewName("carGoods/carlist");
        return modelAndView;
    }

    /**
     * 下订单
     *
     * @param response
     * @param request
     * @return 商品列表
     * @throws UnsupportedEncodingException 抛出异常
     */
    @RequestMapping("/addorders")
    public ModelAndView addOrders(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        List<Cart> cartInCookie = getCartInCookie(response, request);
        carVGoodsService.saveOrders(cartInCookie);

        ModelAndView modelAndView = new ModelAndView();
        List<CarGoods> list = new ArrayList<>();
        try {
            CarGoods carGoods = new CarGoods();
            list = carVGoodsService.queryList(carGoods);
        }catch (SQLException e){
            e.printStackTrace();
        }
        modelAndView.addObject("list",list);
        modelAndView.setViewName("carGoods/list");
        return modelAndView;
    }

    //拆单
    @RequestMapping("/teardowndetails")
    public ModelAndView teardowndetails(HttpServletRequest request,HttpServletResponse response,String orderId) throws UnsupportedEncodingException {
        List<Cart> cartInCookie = getCartInCookie(response, request);
        List<CarOrderDetails> list = carVGoodsService.queryOrdersDetails(orderId);
        //筛选出不同的
        for (CarOrderDetails c : list) {
            TearDownDetails tdd = carVGoodsService.queryOrdersTearDownDetails(c);
            if(tdd != null){
                tdd.setNum(tdd.getNum()+c.getNum());
                tdd.setCargoods_name(tdd.getCargoods_name() + c.getGoodsname());
                Integer integer = carVGoodsService.updateTearDownDetails(tdd);
                if (integer != 1){
                    System.out.println("拆单更新失败");
                    throw  new RuntimeException("拆单更新失败");
                }
            }else {
                TearDownDetails t = new TearDownDetails();
                t.setNum(c.getNum());
                t.setCargoods_name(c.getGoodsname());
                t.setOrderId(c.getOrderId());
                t.setProduce(c.getProduce());
                Integer integer = carVGoodsService.insertTearDownDetails(t);
                if (integer != 1){
                    System.out.println("拆单失败");
                    throw  new RuntimeException("拆单失败");
                }
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return null;
    }

    /**
     * 清空购物车
     */
    @RequestMapping("/clearCart")
    public ModelAndView clearCart(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        Cookie cookie = getCookie(request);
        cookie.setValue("");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        ModelAndView modelAndView = new ModelAndView();
        ModelAndView cart = this.getCart(request, response);
        return cart;
    }

    /**
     * 删除购物车指定商品
     */
    @RequestMapping("/deleteGoodsInCart")
    public ModelAndView deleteGoodsInCart(HttpServletRequest request,HttpServletResponse response,int id) throws UnsupportedEncodingException {
        List<Cart> cartInCookie = getCartInCookie(response, request);
        cartInCookie.removeIf(e->e.getId()==id);
        String value = "";
        if (cartInCookie.size() != 0) {
            value = makeCookieValue(cartInCookie);
        }
        Cookie cookie = getCookie(request);
        cookie.setValue(value);
        cookie.setMaxAge(60*30);
        cookie.setPath("/");
        response.addCookie(cookie);
        ModelAndView modelAndView = new ModelAndView();
        ModelAndView cart = this.getCart(request, response);
        return cart;
    }

    /**
     * 按月统计订单数
     */
    @RequestMapping("/statistic")
    public ModelAndView statisticOrderNum() throws UnsupportedEncodingException {
//        List<StatisticsOrderNum> list = carVGoodsService.statisticOrders();
        int[] list = new int[]{1,2,3,4,5,6,7,66,55,4,44,5};
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list",list);
        modelAndView.setViewName("/carGoods/repertory");
        return modelAndView;
    }



    /**
     * 获取cookie中的购物车列表
     *
     * @param response
     * @param request
     * @return 购物车列表
     * @throws UnsupportedEncodingException 抛出异常
     */
    public List<Cart> getCartInCookie(HttpServletResponse response, HttpServletRequest request) throws
            UnsupportedEncodingException {
        // 定义空的购物车列表
        List<Cart> items = new ArrayList<>();
        String value_1st ;
        // 购物cookie
        Cookie cart_cookie = getCookie(request);
        // 判断cookie是否为空
        if (cart_cookie != null) {
            // 获取cookie中String类型的value,从cookie获取购物车
            value_1st = URLDecoder.decode(cart_cookie.getValue(), "utf-8");
            // 判断value是否为空或者""字符串
            if (value_1st != null && !"".equals(value_1st)) {
                // 解析字符串中的数据为对象并封装至list中返回给上一级
                String[] arr_1st = value_1st.split("==");
                for (String value_2st : arr_1st) {
                    String[] arr_2st = value_2st.split("=");
                    Cart item = new Cart();
                    item.setId(Long.parseLong(arr_2st[0])); //商品id
                    item.setType(arr_2st[1]); //商品类型ID
                    item.setName(arr_2st[2]); //商品名
                    item.setDescription(arr_2st[4]);//商品详情
                    item.setPrice(Integer.parseInt(arr_2st[3])); //商品市场价格
                    item.setNum(Integer.parseInt(arr_2st[5]));//加入购物车数量
                    item.setProduce(arr_2st[6]);//加入购物车数量
                    items.add(item);
                }
            }
        }
        return items;

    }

    /**
     * 获取名为"cart"的cookie
     *
     * @param request
     * @return cookie
     */
    public Cookie getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie cart_cookie = null;
        for (Cookie cookie : cookies) {
            //获取购物车cookie
            if ("cart".equals(cookie.getName())) {
                cart_cookie = cookie;
            }
        }
        return cart_cookie;
    }

    /**
     * 制作cookie所需value
     *
     * @param cartVos 购物车列表
     * @return 解析为字符串的购物车列表，属性间使用"="相隔，对象间使用"=="相隔
     */
    public String makeCookieValue(List<Cart> cartVos) {
        StringBuffer buffer_2st = new StringBuffer();
        for (Cart item : cartVos) {
            buffer_2st.append(item.getId() + "=" + item.getType() + "=" + item.getName() + "="
                    + item.getPrice() + "=" + item.getDescription() + "=" + item.getNum() + "="+item.getProduce()+"==");
        }
        return buffer_2st.toString().substring(0, buffer_2st.toString().length() - 2);
    }

}
