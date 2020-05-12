package com.ego.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.order.service.OrderConsumerService;
import com.ego.order.vo.MyOrder;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import com.ego.service.OrderItemService;
import com.ego.service.OrderService;
import com.ego.service.OrderShippingService;
import com.ego.utils.IDUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: Victor Wu
 * @Date: 2019/10/11 15:49
 * @Description:
 */
@Service
public class OrderConsumerServiceImpl implements OrderConsumerService {
    @Reference
    private OrderService orderService;
    @Reference
    private OrderItemService orderItemService;
    @Reference
    private OrderShippingService orderShippingService;
    @Override
    public Map<String, Object> addOrder(MyOrder myOrder) {
        Map<String, Object> map = new HashMap<>();
        //新增订单表
        TbOrder tbOrder = new TbOrder();
        long orderId = IDUtils.genItemId();
        tbOrder.setOrderId(orderId+"");
        tbOrder.setPaymentType(myOrder.getPaymentType());
        tbOrder.setPayment(myOrder.getPayment());
        orderService.insertOrder(tbOrder);

        //新增订单发货表
        TbOrderShipping orderShipping = myOrder.getOrderShipping();
        orderShipping.setOrderId(orderId+"");
        Date date = new Date();
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);
        orderShippingService.insertOrderShipping(orderShipping);

        //新增订单商品表
        List<TbOrderItem> orderItems = myOrder.getOrderItems();
        for (TbOrderItem orderItem : orderItems) {
            orderItem.setId(IDUtils.genItemId()+"");
            orderItem.setOrderId(orderId+"");
            orderItemService.insertOrderItem(orderItem);
        }

        map.put("orderId",orderId);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,2);
        map.put("date",calendar.getTime());
        return map;
    }
}
