package com.ego.manage.service;

import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/11/7 16:10
 * @Description:
 */
public interface ItemConsumerService {
    //查询所有商品并分页
    Map<String,Object> selectAllItemsByPage(int page,int rows);
}
