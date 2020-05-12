package com.ego.manage.controller;

import com.ego.manage.service.ItemCatManageService;
import com.ego.vo.EgoTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/18 11:42
 * @Description:
 */
@Controller
public class ItemCatController {
    @Autowired
    private ItemCatManageService itemCatManageService;


    //根据父节点id查询所有状态正常的子节点
    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EgoTree> showItemCatByPid(@RequestParam(defaultValue = "0") Long id){
        return itemCatManageService.selectItemCatsByPid(id);
    }
}
