package com.ego.cart.controller;

import com.ego.cart.service.CartService;
import com.ego.cart.vo.Cart;
import com.ego.vo.EgoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/10/9 14:18
 * @Description:
 */
@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    //显示购物车成功页
    @RequestMapping("/cart/add/{itemId}.html")
    public String showCartSuccessPage(@PathVariable Long itemId, HttpServletRequest request,int num){
        cartService.addCart(itemId, request,num);
        return "/WEB-INF/jsp/cartSuccess.jsp";
    }


    //显示购物车商品
    @RequestMapping("/cart/cart.html")
    public String showCartDetail(Model model,HttpServletRequest request){
        List<Cart> cartList = cartService.showCartDetail(request);
        if(cartList == null){
            model.addAttribute("message", "亲！当前商品库存不足，请选择其他商品！");
            return "/WEB-INF/jsp/error/info.jsp";
        }
        model.addAttribute("cartList", cartList);
        return "/WEB-INF/jsp/cart.jsp";
    }

    //修改购物车商品购买数量
    @RequestMapping("/cart/update/num/{itemId}/{num}.action")
    @ResponseBody
    public EgoResult updateCartNum(@PathVariable Long itemId,@PathVariable int num,HttpServletRequest request){
        return cartService.updateCartNum(itemId, num, request);
    }

    //删除购物车中商品
    @RequestMapping("/cart/delete/{itemId}.action")
    @ResponseBody
    public EgoResult deleteCart(@PathVariable Long itemId,HttpServletRequest request){
        return cartService.deleteCart(itemId, request);
    }

}
