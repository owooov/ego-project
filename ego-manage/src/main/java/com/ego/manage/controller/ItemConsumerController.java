package com.ego.manage.controller;

import com.ego.manage.service.ItemConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/11/7 16:17
 * @Description:
 */
@Controller
public class ItemConsumerController {
    @Autowired
    private ItemConsumerService itemConsumerService;


    //查询所有商品并分页
    @RequestMapping("/item/list")
    @ResponseBody
    public Map<String,Object> showAllItemsByPage(@RequestParam(defaultValue = "1") int page,int rows){
        return itemConsumerService.selectAllItemsByPage(page, rows);
    }

}
