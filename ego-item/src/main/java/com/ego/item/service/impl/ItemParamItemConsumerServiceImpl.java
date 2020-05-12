package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.item.service.ItemParamItemConsumerService;
import com.ego.item.vo.ItemParamItem;
import com.ego.item.vo.ItemParamItemVo;
import com.ego.pojo.TbItemParamItem;
import com.ego.redis.dao.JedisDao;
import com.ego.service.ItemParamItemService;
import com.ego.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/29 09:21
 * @Description:
 */
@Service
public class ItemParamItemConsumerServiceImpl implements ItemParamItemConsumerService {
    @Reference
    private ItemParamItemService itemParamItemService;
    @Autowired
    private JedisDao jedisDao;
    @Value("${redis.itemParam.key}")
    private String key;

    //根据商品id查询对应的商品规格参数
    @Override
    public String showItemParamByItemId(Long itemId) {
        String itemParamkey = key+itemId;
        //判断redis中是否有对应的数据
        if(jedisDao.exists(itemParamkey)){
            //如果有
            String itemParamJson = jedisDao.get(itemParamkey);
            return itemParamJson;
        }else{
            //如果没有,则查询mysql，并将查询结果放到redis中
            TbItemParamItem tbItemParamItem = itemParamItemService.selectParamItemByItemId(itemId);
            StringBuffer sb = new StringBuffer();
            String paramData = tbItemParamItem.getParamData();
            List<ItemParamItem> itemParamItems = JsonUtil.jsonToList(paramData, ItemParamItem.class);
            sb.append("<table>");
            for (ItemParamItem itemParamItem : itemParamItems) {
                sb.append("<tr>");
                List<ItemParamItemVo> params = itemParamItem.getParams();
                for (int i = 0; i < params.size(); i++) {
                    if(i == 0){
                        sb.append("<tr>");
                        sb.append("<td>");
                        sb.append(itemParamItem.getGroup());
                        sb.append("</td>");
                        sb.append("<td>");
                        sb.append(params.get(i).getK());
                        sb.append("</td>");
                        sb.append("<td>");
                        sb.append(params.get(i).getV());
                        sb.append("</td>");
                        sb.append("</tr>");
                    }else{
                        sb.append("<tr>");
                        sb.append("<td>");
                        sb.append("</td>");
                        sb.append("<td>");
                        sb.append(params.get(i).getK());
                        sb.append("</td>");
                        sb.append("<td>");
                        sb.append(params.get(i).getV());
                        sb.append("</td>");
                        sb.append("</tr>");
                    }
                }
                sb.append("</tr>");
            }
            sb.append("</table>");

            jedisDao.insert(itemParamkey,sb.toString());
            return sb.toString();
        }

    }
}
