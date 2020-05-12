package com.ego.item.controller;

import com.ego.item.service.ItemDescConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/28 15:53
 * @Description:
 */
@Controller
public class ItemDescController {
    @Autowired
    private ItemDescConsumerService itemDescConsumerService;


    //根据商品id查询对应的商品描述
    @RequestMapping(value = "/item/desc/{itemId}.html",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String showItemDescById(@PathVariable Long itemId){
        return itemDescConsumerService.showItemDescById(itemId);
    }
}
