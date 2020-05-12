package com.ego.service.impl;

import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItemParamItem;
import com.ego.pojo.TbItemParamItemExample;
import com.ego.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/19 11:30
 * @Description:
 */
public class ItemParamItemServiceImpl implements ItemParamItemService {
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

    //新增
    @Override
    public int insertItemParamItem(TbItemParamItem tbItemParamItem) {
        return tbItemParamItemMapper.insertSelective(tbItemParamItem);
    }


    //根据商品id查询对应的商品规格参数
    @Override
    public TbItemParamItem selectParamItemByItemId(Long itemId) {
        TbItemParamItemExample example = new TbItemParamItemExample();
        example.createCriteria().andItemIdEqualTo(itemId);
        List<TbItemParamItem> itemParamItemList = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if(itemParamItemList != null && itemParamItemList.size()>0){
            return itemParamItemList.get(0);
        }
        return null;
    }
}
