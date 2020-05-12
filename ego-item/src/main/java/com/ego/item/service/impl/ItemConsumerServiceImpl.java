package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.item.service.ItemConsumerService;
import com.ego.pojo.TbItem;
import com.ego.redis.dao.JedisDao;
import com.ego.service.ItemService;
import com.ego.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/28 14:11
 * @Description:
 */
@Service
public class ItemConsumerServiceImpl implements ItemConsumerService {
    @Reference
    private ItemService itemService;
    @Autowired
    private JedisDao jedisDao;

    @Value("${redis.item.key}")
    private String key;

    //根据商品id查询商品详情
    @Override
    public TbItem showItemDetailByItemId(Long itemId) {
        //先判断redis中是否有数据
        String itemKey = key+itemId;
        if(jedisDao.exists(itemKey)){
            //如果有
            String json = jedisDao.get(itemKey);
            TbItem tbItem = JsonUtil.jsonToPojo(json, TbItem.class);
            return  tbItem;
        }else{
            //如果没有.从mysql中查询数据，并将结果放到redis中
            TbItem tbItem = itemService.selectById(itemId);
            String image = tbItem.getImage();
            if(image != null && !"".equalsIgnoreCase(image)){
                tbItem.setImages(image.split(","));
            }else{
                tbItem.setImages(new String[1]);
            }

            jedisDao.insert(itemKey,JsonUtil.objectToJson(tbItem));
            return tbItem;
        }
    }
}
