package com.ego.item.controller;

import com.ego.item.service.ItemConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/28 10:49
 * @Description:
 */
@Controller
public class ItemController {
    @Autowired
    private ItemConsumerService itemConsumerService;
    //显示商品详情页
    @RequestMapping("/item/{itemId}.html")
    public String showItemPage(@PathVariable Long itemId, Model model){
        model.addAttribute("item",itemConsumerService.showItemDetailByItemId(itemId));
        return "/WEB-INF/jsp/item.jsp";
    }
}
