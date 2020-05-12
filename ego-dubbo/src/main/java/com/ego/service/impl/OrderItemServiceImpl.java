package com.ego.service.impl;

import com.ego.mapper.TbOrderItemMapper;
import com.ego.pojo.TbOrderItem;
import com.ego.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Victor Wu
 * @Date: 2019/10/11 15:29
 * @Description:
 */
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;

    //新增
    @Override
    public int insertOrderItem(TbOrderItem tbOrderItem) {
        return tbOrderItemMapper.insertSelective(tbOrderItem);
    }
}
