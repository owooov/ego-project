package com.ego.item.controller;

import com.ego.item.service.ItemParamItemConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/29 09:43
 * @Description:
 */
@Controller
public class ItemParamItemController {
    @Autowired
    private ItemParamItemConsumerService itemParamItemConsumerService;

    //根据商品id查询对应的商品规格参数
    @RequestMapping(value = "/item/param/{itemId}.html",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String showItemParamItemByItemId(@PathVariable Long itemId){
        return itemParamItemConsumerService.showItemParamByItemId(itemId);
    }
}
