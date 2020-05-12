package com.ego.order.controller;

import com.ego.order.service.OrderConsumerService;
import com.ego.order.vo.MyOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/10/11 16:27
 * @Description:
 */
@Controller
public class OrderController {
    @Autowired
    private OrderConsumerService orderConsumerService;


    //新增
    @RequestMapping("/order/create.html")
    public String showOrderSuccess(MyOrder myOrder,Model model){
        model.addAttribute("payment",myOrder.getPayment());
        Map<String, Object> map = orderConsumerService.addOrder(myOrder);
        model.addAttribute("orderId",map.get("orderId"));
        model.addAttribute("date",map.get("date"));
        return "/WEB-INF/jsp/success.jsp";
    }
}
