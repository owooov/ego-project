package com.ego.order.service;

import com.ego.order.vo.MyOrder;

import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/10/11 15:44
 * @Description:
 */
public interface OrderConsumerService {
    //新增订单
    Map<String,Object> addOrder(MyOrder myOrder);
}
