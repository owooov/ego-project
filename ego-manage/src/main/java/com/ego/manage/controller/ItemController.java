package com.ego.manage.controller;

import com.ego.manage.service.ItemManageService;
import com.ego.pojo.TbItem;
import com.ego.vo.EgoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/17 16:21
 * @Description:
 */
@Controller
public class ItemController {
    @Autowired
    private ItemManageService itemManageService;


    //查询所有商品并分页显示
    @RequestMapping("/item/list")
    @ResponseBody
    public Map<String,Object> showAllItemsByPage(int page,int rows){
        return itemManageService.showAllItems(page, rows);
    }

    /*//修改商品状态(删除)
    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public EgoResult updateItemStatusDelete(@RequestParam List<Long> ids){
        return itemManageService.updateItemStatus(ids,(byte)3);
    }
    //修改商品状态(下架)
    @RequestMapping("/rest/item/instock")
    @ResponseBody
    public EgoResult updateItemStatusInstock(@RequestParam List<Long> ids){
        return itemManageService.updateItemStatus(ids,(byte)2);
    }
    //修改商品状态(上架)
    @RequestMapping("/rest/item/reshelf")
    @ResponseBody
    public EgoResult updateItemStatusReshelf(@RequestParam List<Long> ids){
        return itemManageService.updateItemStatus(ids,(byte)1);
    }*/
    //修改商品状态
    @RequestMapping("/rest/item/{status}")
    @ResponseBody
    public EgoResult updateItemStatus(@RequestParam List<Long> ids,@PathVariable String status){
        if("reshelf".equalsIgnoreCase(status)){

            return itemManageService.updateItemStatus(ids,(byte)1);
        }else if("instock".equalsIgnoreCase(status)){
            return itemManageService.updateItemStatus(ids,(byte)2);
        }else{
            return itemManageService.updateItemStatus(ids,(byte)3);
        }
    }

    //文件上传
    @RequestMapping("/pic/upload")
    @ResponseBody
    public Map<String,Object> fileUpload(MultipartFile uploadFile){
        return itemManageService.fileUpload(uploadFile);
    }

    //新增商品
    @RequestMapping("/item/save")
    @ResponseBody
    public EgoResult insertItem(TbItem tbItem,String desc,String itemParams){
        return itemManageService.insertItem(tbItem, desc,itemParams);
    }
}
