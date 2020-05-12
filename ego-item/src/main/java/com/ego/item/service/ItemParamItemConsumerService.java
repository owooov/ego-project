package com.ego.item.service;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/29 09:20
 * @Description:
 */
public interface ItemParamItemConsumerService {

    //根据商品id查询对应的商品规格参数
    String showItemParamByItemId(Long itemId);
}
