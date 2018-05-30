package com.mr.order.serviceImpl;

import com.mr.OrderVo;
import com.mr.mapper.TbOrderItemMapper;
import com.mr.mapper.TbOrderMapper;
import com.mr.mapper.TbOrderShippingMapper;
import com.mr.model.TbOrder;
import com.mr.model.TbOrderItem;
import com.mr.model.TbOrderShipping;
import com.mr.order.jedis.JedisClient;
import com.mr.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by czl on 2018/5/24.
 */
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;

    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;

    @Transactional
    @Override
    public void createOrder(OrderVo orderVo) {//利用redis的单线程来生成订单Id
        if(!jedisClient.exists("orderId")){
            jedisClient.set("orderId","10000");
        }
        String orderId = jedisClient.incr("orderId").toString(); //通过incr方法实现订单id递增
        orderVo.setOrderId(orderId);//将orderId赋给订单对象
        orderVo.setPostFee("0.00");//设置邮费
        orderVo.setStatus(1);//付款状态
        orderVo.setCreateTime(new Date());
        orderVo.setUpdateTime(new Date());
        tbOrderMapper.insertSelective(orderVo);//新增到数据库订单表

        //订单详情页
        List<TbOrderItem> tbOrderItems = orderVo.getTbOrderItems();
        for (TbOrderItem tbOrderItem : tbOrderItems) {
            if(!jedisClient.exists("orderItemId")){
                jedisClient.set("orderItemId","10000");
            }   //生成订单详情页id
            String orderItemId = jedisClient.incr("orderItemId").toString();
            tbOrderItem.setItemId(orderItemId);
            tbOrderItem.setOrderId(orderId);
            tbOrderItemMapper.insertSelective(tbOrderItem);
        }

        //物流表
        TbOrderShipping tbOrderShipping = orderVo.getTbOrderShipping();
        tbOrderShipping.setOrderId(orderId);
        tbOrderShipping.setCreated(new Date());
        tbOrderShipping.setUpdated(new Date());
        tbOrderShippingMapper.insertSelective(tbOrderShipping);
    }
}
