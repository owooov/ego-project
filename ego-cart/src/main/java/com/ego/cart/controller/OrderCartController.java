package com.ego.cart.controller;

import com.ego.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/10/11 09:23
 * @Description:
 */
@Controller
public class OrderCartController {
    @Autowired
    private CartService cartService;
    @RequestMapping("/order/order-cart.html")
    public String showOrderCartPage(Model model, HttpServletRequest request, @RequestParam List<Long> ids){
        model.addAttribute("cartList",cartService.showOrderCartDetail(request, ids));
        return "/WEB-INF/jsp/order-cart.jsp";
    }
}
