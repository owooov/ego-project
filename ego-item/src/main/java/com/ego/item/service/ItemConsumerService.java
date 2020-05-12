package com.ego.item.service;

import com.ego.pojo.TbItem;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/28 14:09
 * @Description:
 */
public interface ItemConsumerService {
    //根据商品id查询商品详情
    TbItem showItemDetailByItemId(Long itemId);
}
