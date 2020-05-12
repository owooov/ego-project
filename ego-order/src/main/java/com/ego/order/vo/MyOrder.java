package com.ego.order.vo;

import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/10/11 15:41
 * @Description:
 */
public class MyOrder {
    private Integer paymentType;
    private String payment;
    private List<TbOrderItem> orderItems;
    private TbOrderShipping orderShipping;

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
