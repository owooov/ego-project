package com.ego.service.impl;

import com.ego.mapper.TbOrderMapper;
import com.ego.pojo.TbOrder;
import com.ego.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Victor Wu
 * @Date: 2019/10/11 15:27
 * @Description:
 */
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TbOrderMapper tbOrderMapper;

    //新增
    @Override
    public int insertOrder(TbOrder tbOrder) {
        return tbOrderMapper.insertSelective(tbOrder);
    }
}
