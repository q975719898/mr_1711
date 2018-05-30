package com.mr.order.controller;

import com.mr.CookieUtils;
import com.mr.JsonUtils;
import com.mr.OrderVo;
import com.mr.model.TbItem;
import com.mr.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by czl on 2018/5/24.
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    /*http://localhost:8091/order/order-cart.html*/
    @RequestMapping("/order/order-cart")
    public  String  toOrder(ModelMap map, HttpServletRequest request ){
        String value = CookieUtils.getCookieValue(request, "cartcookiename", true);
        map.put("cartList", JsonUtils.jsonToList(value, TbItem.class));
        return "order-cart";
    }
    /*/order/create*/
    @RequestMapping("/order/create")
    public  String   create(OrderVo orderVo){
        orderService.createOrder(orderVo);
        return "success";
    }
}
