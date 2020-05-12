package com.ego.cart.service.impl;

import com.ego.cart.service.CartService;
import com.ego.cart.vo.Cart;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbUser;
import com.ego.redis.dao.JedisDao;
import com.ego.utils.CookieUtils;
import com.ego.utils.JsonUtil;
import com.ego.vo.EgoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/10/10 09:28
 * @Description:
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private JedisDao jedisDao;
    @Value("${redis.user.key}")
    private String userKey;
    @Value("${redis.item.key}")
    private String itemKey;
    @Value("${redis.cart.key}")
    private String cartKey;

    //将商品加入购物车
    @Override
    public void addCart(Long itemId, HttpServletRequest request,int num) {
        //从redis中获取用户数据
        String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String userJson = jedisDao.get(userKey + cookieValue);
        TbUser tbUser = JsonUtil.jsonToPojo(userJson, TbUser.class);

        //从redis中获取商品数据
        String itemJson = jedisDao.get(itemKey + itemId);
        TbItem tbItem = JsonUtil.jsonToPojo(itemJson, TbItem.class);

        //判断redis中是否有购物车数据
        if(jedisDao.exists(cartKey+tbUser.getId())){
            //如果有
            String cartJson = jedisDao.get(cartKey + tbUser.getId());
            List<Cart> cartList = JsonUtil.jsonToList(cartJson, Cart.class);
            int index = -1;
            for (int i = 0; i < cartList.size(); i++) {
                if((long)itemId == (long)cartList.get(i).getId()){

                    index = i;
                }
            }
            if(index == -1){
                //购物车没有当前商品
                Cart cart = new Cart();
                cart.setId(itemId);
                cart.setTitle(tbItem.getTitle());
                cart.setImages(tbItem.getImages());
                cart.setNum(num);
                cart.setPrice(tbItem.getPrice());
                cartList.add(cart);

            }else{
                //购物车有当前商品
                Cart cart = cartList.get(index);
                cart.setNum(cartList.get(index).getNum()+num);
            }
            jedisDao.insert(cartKey+tbUser.getId(),JsonUtil.objectToJson(cartList));
        }else{
            //如果没有
            List<Cart> list = new ArrayList<>();
            Cart cart = new Cart();
            cart.setId(itemId);
            cart.setTitle(tbItem.getTitle());
            cart.setImages(tbItem.getImages());
            cart.setNum(num);
            cart.setPrice(tbItem.getPrice());
            list.add(cart);

            //将购物车商品放入redis中
            jedisDao.insert(cartKey+tbUser.getId(),JsonUtil.objectToJson(list));
        }

    }


    //显示购物车商品
    @Override
    public List<Cart> showCartDetail(HttpServletRequest request) {
        //从redis中获取用户数据
        String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String userJson = jedisDao.get(userKey + cookieValue);
        TbUser tbUser = JsonUtil.jsonToPojo(userJson, TbUser.class);



        //从redis中获取购物车商品
        String cartJson = jedisDao.get(cartKey + tbUser.getId());
        List<Cart> cartList = JsonUtil.jsonToList(cartJson, Cart.class);
        for (Cart cart : cartList) {
            String itemJson = jedisDao.get(itemKey + cart.getId());
            TbItem tbItem = JsonUtil.jsonToPojo(itemJson, TbItem.class);
            if(tbItem.getNum() < cart.getNum()){
                return null;
            }
        }

        return cartList;
    }


    //显示订单购物车商品
    @Override
    public List<Cart> showOrderCartDetail(HttpServletRequest request, List<Long> ids) {
        //从redis中获取用户数据
        String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String userJson = jedisDao.get(userKey + cookieValue);
        TbUser tbUser = JsonUtil.jsonToPojo(userJson, TbUser.class);

        //从redis中获取购物车商品
        String cartJson = jedisDao.get(cartKey + tbUser.getId());
        List<Cart> cartList = JsonUtil.jsonToList(cartJson, Cart.class);
        List<Cart> orderCartList = new ArrayList<>();
        for (Cart cart : cartList) {
            //获取redis商品数据
            String itemJson = jedisDao.get(itemKey + cart.getId());
            TbItem tbItem = JsonUtil.jsonToPojo(itemJson, TbItem.class);
            if(tbItem.getNum() < cart.getNum()){
                return null;
            }
            for (Long id : ids) {
                if((long)id == (long)cart.getId()){
                    orderCartList.add(cart);
                }
            }
        }

        return orderCartList;
    }


    //修改购物车商品购买数量
    @Override
    public EgoResult updateCartNum(Long itemId, int num,HttpServletRequest request) {
        EgoResult er = new EgoResult();
        //从redis中获取用户数据
        String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String userJson = jedisDao.get(userKey + cookieValue);
        TbUser tbUser = JsonUtil.jsonToPojo(userJson, TbUser.class);

        //从redis中获取购物车商品
        String cartJson = jedisDao.get(cartKey + tbUser.getId());
        List<Cart> cartList = JsonUtil.jsonToList(cartJson, Cart.class);

        for (Cart cart : cartList) {
            if((long)itemId == (long)cart.getId()){
                cart.setNum(num);
                break;
            }
        }
        String redisResult = jedisDao.insert(cartKey + tbUser.getId(), JsonUtil.objectToJson(cartList));
        if("OK".equalsIgnoreCase(redisResult)){
            er.setStatus(200);
        }

        return er;
    }


    //删除购物车中商品
    @Override
    public EgoResult deleteCart(Long itemId, HttpServletRequest request) {
        EgoResult er = new EgoResult();
        //从redis中获取用户数据
        String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String userJson = jedisDao.get(userKey + cookieValue);
        TbUser tbUser = JsonUtil.jsonToPojo(userJson, TbUser.class);

        //从redis中获取购物车商品
        String cartJson = jedisDao.get(cartKey + tbUser.getId());
        List<Cart> cartList = JsonUtil.jsonToList(cartJson, Cart.class);

        Cart removeCart = null;
        for (Cart cart : cartList) {
            if((long)itemId == (long)cart.getId()){
                removeCart = cart;
                break;
            }
        }
        cartList.remove(removeCart);
        String redisResult = jedisDao.insert(cartKey + tbUser.getId(), JsonUtil.objectToJson(cartList));
        if("OK".equalsIgnoreCase(redisResult)){
            er.setStatus(200);
        }

        return er;
    }
}
