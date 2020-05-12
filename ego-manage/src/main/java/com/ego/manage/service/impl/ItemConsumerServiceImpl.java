package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.manage.service.ItemConsumerService;
import com.ego.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/11/7 16:12
 * @Description:
 */
@Service
public class ItemConsumerServiceImpl implements ItemConsumerService {
    @Reference
    private ItemService itemService;
    //查询所有商品并分页
    @Override
    public Map<String, Object> selectAllItemsByPage(int page, int rows) {
        return  itemService.selectAllItemsByPage(page, rows);
    }
}
