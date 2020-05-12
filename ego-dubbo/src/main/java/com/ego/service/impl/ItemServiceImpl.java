package com.ego.service.impl;

import com.ego.mapper.TbItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemExample;
import com.ego.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/11/7 16:01
 * @Description:
 */
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;
    //查询所有商品并分页
    @Override
    public Map<String, Object> selectAllItemsByPage(int page, int rows) {
 Map<String, Object> map = new HashMap<>();
        //1.开启分页插件，并设置分页条件
        PageHelper.startPage(page, rows);
        //2.查询所有数据
        List<TbItem> itemList = tbItemMapper.selectByExample(new TbItemExample());
        //3.将查询结果封装到PageInfo对象中
        PageInfo<TbItem> pageInfo = new PageInfo<>(itemList);
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }
}
