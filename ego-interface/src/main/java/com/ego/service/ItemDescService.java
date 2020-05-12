package com.ego.service;

import com.ego.pojo.TbItemDesc;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/18 15:25
 * @Description:
 */
public interface ItemDescService {
    //新增商品描述
    int insertItemDesc(TbItemDesc tbItemDesc);

    //根据商品id查询对应的商品描述
    TbItemDesc selectDescByItemId(Long itemId);
}
