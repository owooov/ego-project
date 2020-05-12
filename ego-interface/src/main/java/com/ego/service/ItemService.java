package com.ego.service;

import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/11/7 15:59
 * @Description:
 */
public interface ItemService {
    //查询所有商品并分页
    Map<String,Object> selectAllItemsByPage(int page,int rows);
}
