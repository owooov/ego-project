package com.ego.service.impl;

import com.ego.mapper.TbItemDescMapper;
import com.ego.pojo.TbItemDesc;
import com.ego.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/18 15:26
 * @Description:
 */
public class ItemDescServiceImpl implements ItemDescService {
    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    //新增商品描述
    @Override
    public int insertItemDesc(TbItemDesc tbItemDesc) {

        return tbItemDescMapper.insertSelective(tbItemDesc);
    }


    //根据商品id查询对应的商品描述
    @Override
    public TbItemDesc selectDescByItemId(Long itemId) {

        return tbItemDescMapper.selectByPrimaryKey(itemId);
    }
}
