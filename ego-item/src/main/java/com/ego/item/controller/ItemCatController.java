package com.ego.item.controller;

import com.ego.item.service.ItemCatConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/19 17:40
 * @Description:
 */
@Controller
public class ItemCatController {
    @Autowired
    private ItemCatConsumerService itemCatConsumerService;


    //显示所有的商品分类
    @RequestMapping("/rest/itemcat/all")
    @ResponseBody
    public MappingJacksonValue showAllItemCat(String callback){
        MappingJacksonValue mjv = new MappingJacksonValue(itemCatConsumerService.showAllItemCat());
        mjv.setJsonpFunction(callback);
        return mjv;
    }
}
