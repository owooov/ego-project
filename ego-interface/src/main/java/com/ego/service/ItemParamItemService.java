package com.ego.service;

import com.ego.pojo.TbItemParamItem;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/19 11:29
 * @Description:
 */
public interface ItemParamItemService {
    //新增
    int insertItemParamItem(TbItemParamItem tbItemParamItem);

    //根据商品id查询对应的商品规格参数
    TbItemParamItem selectParamItemByItemId(Long itemId);
}
