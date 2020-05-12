package com.ego.item.service;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/28 15:43
 * @Description:
 */
public interface ItemDescConsumerService {
    //根据商品id查询对应的商品描述
    String showItemDescById(Long id);
}
