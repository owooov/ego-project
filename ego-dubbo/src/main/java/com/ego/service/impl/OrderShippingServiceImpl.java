package com.ego.service.impl;

import com.ego.mapper.TbOrderShippingMapper;
import com.ego.pojo.TbOrderShipping;
import com.ego.service.OrderShippingService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Victor Wu
 * @Date: 2019/10/11 15:30
 * @Description:
 */
public class OrderShippingServiceImpl implements OrderShippingService {
    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;
    //新增
    @Override
    public int insertOrderShipping(TbOrderShipping tbOrderShipping) {
        return tbOrderShippingMapper.insertSelective(tbOrderShipping);
    }
}
