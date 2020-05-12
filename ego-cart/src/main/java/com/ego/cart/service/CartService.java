package com.ego.cart.service;

import com.ego.cart.vo.Cart;
import com.ego.vo.EgoResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/10/10 09:23
 * @Description:
 */
public interface CartService {
    //将商品加入购物车
    void addCart(Long itemId, HttpServletRequest request,int num);

    //显示购物车商品
    List<Cart> showCartDetail(HttpServletRequest request);

    //显示订单购物车商品
    List<Cart> showOrderCartDetail(HttpServletRequest request,List<Long> ids);

    //修改购物车商品购买数量
    EgoResult updateCartNum(Long itemId,int num,HttpServletRequest request);

    //删除购物车中商品
    EgoResult deleteCart(Long itemId,HttpServletRequest request);
}
