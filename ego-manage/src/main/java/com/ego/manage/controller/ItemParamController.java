package com.ego.manage.controller;

import com.ego.manage.service.ItemParamManageService;
import com.ego.pojo.TbItemParam;
import com.ego.vo.EgoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/18 16:34
 * @Description:
 */
@Controller
public class ItemParamController {
    @Autowired
    private ItemParamManageService itemParamManageService;


    //查询所有商品规格参数并分页显示
    @RequestMapping("/item/param/list")
    @ResponseBody
    public Map<String,Object> showAllItemParams(int page,int rows){
        return itemParamManageService.showAllItemParams(page, rows);
    }


    //根据商品分类id查询对应的商品规格参数
    @RequestMapping("/item/param/query/itemcatid/{catId}")
    @ResponseBody
    public EgoResult showItemParamByCatId(@PathVariable Long catId){
        return itemParamManageService.selectItemParamByCatId(catId);
    }

    //新增
    @RequestMapping("/item/param/save/{catId}")
    @ResponseBody
    public EgoResult insertItemParam(@PathVariable Long catId,String paramData){
        TbItemParam tbItemParam = new TbItemParam();
        tbItemParam.setItemCatId(catId);
        tbItemParam.setParamData(paramData);
        return itemParamManageService.insertItemParam(tbItemParam);
    }


    //删除
    @RequestMapping("/item/param/delete")
    @ResponseBody
    public EgoResult deleteItemParamById(@RequestParam List<Long> ids){
        return itemParamManageService.deleteItemParamById(ids);
    }
}
