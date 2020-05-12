package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.item.service.ItemDescConsumerService;
import com.ego.pojo.TbItemDesc;
import com.ego.redis.dao.JedisDao;
import com.ego.service.ItemDescService;
import com.ego.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/28 15:45
 * @Description:
 */
@Service
public class ItemDescConsumerServiceImpl implements ItemDescConsumerService {
    @Reference
    private ItemDescService itemDescService;
    @Autowired
    private JedisDao jedisDao;

    @Value("${redis.itemDesc.key}")
    private String key;

    //根据商品id查询对应的商品描述
    @Override
    public String showItemDescById(Long id) {
        //先判断redis中是否有对应数据
        String itemDescKey = key+id;
        if(jedisDao.exists(itemDescKey)){
            //如果有
            String json = jedisDao.get(itemDescKey);
            TbItemDesc tbItemDesc = JsonUtil.jsonToPojo(json, TbItemDesc.class);
            return tbItemDesc.getItemDesc();
        }else{
            //如果没有
            TbItemDesc tbItemDesc = itemDescService.selectDescByItemId(id);
            jedisDao.insert(itemDescKey,JsonUtil.objectToJson(tbItemDesc));

            return tbItemDesc.getItemDesc();
        }
    }
}
